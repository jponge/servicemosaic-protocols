/*
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Universite Blaise Pascal, LIMOS, Clermont-Ferrand, France.
 * Copyright 2005-2008 The University of New South Wales, Sydney, Australia.
 *
 * This file is part of ServiceMosaic Protocols <http://servicemosaic.isima.fr/>.
 *
 * ServiceMosaic Protocols is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ServiceMosaic Protocols is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ServiceMosaic Protocols.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl
import fr.isima.ponge.wsprotocol.timed.constraints.*
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintLexer
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintParser
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintTreeWalker

/**
 * Abstract operator class.
 *
 * @author Julien Ponge
 */
abstract class Operator
{
    BusinessProtocolFactory factory

    Operator()
    {
        factory = new BusinessProtocolFactoryImpl()
    }

    Operator(BusinessProtocolFactory factory)
    {
        this.factory = factory
    }

    protected BusinessProtocolFactory getProtocolFactory()
    {
        factory
    }

    protected boolean isConstraintEmpty(String constraint)
    {
        (constraint == null) || ("" == constraint)
    }

    protected boolean isOperationConstraintEmpty(Operation operation)
    {
        return isConstraintEmpty(operation.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT))
    }

    protected IConstraintNode parseConstraint(String constraint)
    {
        TemporalConstraintLexer lexer
        TemporalConstraintParser parser
        TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker()

        lexer = new TemporalConstraintLexer(new StringReader(constraint))
        parser = new TemporalConstraintParser(lexer)
        try
        {
            parser.constraint()
            return walker.constraint(parser.getAST())
        }
        catch (Exception e)
        {
            return null
        }
    }

    protected void rewriteConstraintVariables(Operation operation, Closure rewriter)
    {
        IConstraintNode root = parseConstraint(operation.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT))
        findAndRewriteVariables(root, rewriter)
        operation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, root.toString())
    }

    private List collectVariableNames(IConstraintNode node)
    {
        if (node instanceof IRootConstraintNode)
        {
            return [collectVariableNames(node.leftChild), collectVariableNames(node.rightChild)]
        }
        else if (node instanceof VariableNode)
        {
            return [node.variableName]
        }
        else if (node instanceof IConstraintFunctionNode)
        {
            return [collectVariableNames(node.node)]
        }
    }

    protected List listVariablesInOperationConstraint(Operation operation)
    {
        IConstraintNode constraintNode = this.parseConstraint(operation.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT))
        return collectVariableNames(constraintNode).flatten().unique() - null
    }

    private String constraintCombination(Operation o1, Operation o2, symbol)
    {
        def c1 = o1.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
        def c2 = o2.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);

        // Simple cases
        if (isConstraintEmpty(c1) && isConstraintEmpty(c2))
        {
            return ""
        }
        else if (isConstraintEmpty(c2))
        {
            return c1
        }
        else if (isConstraintEmpty(c1))
        {
            return c2
        }

        // "true" intersection
        def ast = parseConstraint(c1)
        def root1 = ast.node
        def root2 = parseConstraint(c2).node
        BooleanNode andNode = new BooleanNode(symbol, root1, root2)
        ast.node = andNode
        return ast.toString()
    }

    protected String constraintConjunction(Operation o1, Operation o2)
    {
        constraintCombination(o1, o2, BooleanNode.AND)
    }

    protected String constraintDisjunction(Operation o1, Operation o2)
    {
        constraintCombination(o1, o2, BooleanNode.OR)
    }

    protected String negateConstraint(Operation op)
    {
        def constraint = op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)
        if (isConstraintEmpty(constraint))
        {
            return ""
        }

        def NEGATIONS = [
                (BooleanNode.AND): BooleanNode.OR,
                (BooleanNode.OR): BooleanNode.AND,
                (ComparisonNode.LESS): ComparisonNode.GREATER_EQ,
                (ComparisonNode.LESS_EQ): ComparisonNode.GREATER,
                (ComparisonNode.GREATER): ComparisonNode.LESS_EQ,
                (ComparisonNode.GREATER_EQ): ComparisonNode.LESS,
                (ComparisonNode.EQ): ComparisonNode.NEQ,
                (ComparisonNode.NEQ): ComparisonNode.EQ
        ]

        def ast = parseConstraint(constraint)
        findAndRewriteOperators(ast.node) {operator -> NEGATIONS[operator] }
        return ast.toString()
    }

    private void findAndRewriteOperators(IConstraintNode node, Closure rewriter)
    {
        if ((node instanceof ComparisonNode) || (node instanceof BooleanNode))
        {
            node.symbol = rewriter(node.symbol)
            findAndRewriteOperators(node.leftChild, rewriter)
            findAndRewriteOperators(node.rightChild, rewriter)
        }
        else if (node instanceof DiagonalNode)
        {
            node.operator = rewriter(node.operator)
        }
        else if (node instanceof IConstraintFunctionNode)
        {
            findAndRewriteVariables(node.node, rewriter)
        }
    }

    private void findAndRewriteVariables(IConstraintNode node, Closure rewriter)
    {
        if (node instanceof IRootConstraintNode)
        {
            findAndRewriteVariables(node.leftChild, rewriter)
            findAndRewriteVariables(node.rightChild, rewriter)
        }
        else if (node instanceof VariableNode)
        {
            node.setVariableName(rewriter(node.variableName))
        }
        else if (node instanceof IConstraintFunctionNode)
        {
            findAndRewriteVariables(node.node, rewriter)
        }
    }

    protected BusinessProtocol cloneProtocol(BusinessProtocol protocol)
    {
        BusinessProtocolFactory factory = getFactory()
        BusinessProtocol clone = factory.createBusinessProtocol(protocol.name)

        def statesMap = [:]
        protocol.states.each {State state ->
            State newState = factory.createState(state.name, state.finalState)
            clone.addState(newState)
            if (state.initialState)
            {
                clone.setInitialState(newState)
            }
            statesMap[state] = newState
        }

        protocol.operations.each {Operation operation ->
            Operation newOperation = factory.createOperation(
                    operation.name,
                    statesMap[operation.sourceState],
                    statesMap[operation.targetState],
                    getFactory().createMessage(operation.message.name, operation.message.polarity),
                    operation.operationKind)
            newOperation.putExtraProperty(
                    StandardExtraProperties.TEMPORAL_CONSTRAINT,
                    operation.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT))
            clone.addOperation(newOperation)
        }

        return clone
    }

    protected List<String> pruneProtocol(BusinessProtocol protocol)
    {
        def removedOperationsNames = []
        
        def deadlockStates = [1]
        while (!deadlockStates.empty)
        {
            deadlockStates = protocol.states.findAll { State s -> (!s.finalState) && (s.outgoingOperations.size() == 0) }
            deadlockStates.each { State s ->
                def incomingOperations = s.incomingOperations.findAll { true }
                incomingOperations.each { Operation o ->
                    protocol.removeOperation(o)
                    removedOperationsNames << o.name
                }
                protocol.removeState(s)
            }
        }

        def isolatedStates = [1]
        while (!isolatedStates.empty)
        {
            isolatedStates = protocol.states.findAll { State s -> (!s.initialState) && (s.incomingOperations.size() == 0) }
            isolatedStates.each { State s ->
                def outgoingOperations = s.outgoingOperations.findAll { true }
                outgoingOperations.each { Operation o ->
                    protocol.removeOperation(o)
                    removedOperationsNames << o.name
                }
                protocol.removeState(s)
            }
        }

        return removedOperationsNames
    }
}

/**
 * Unary operator abstract class.
 *
 * @author Julien Ponge
 */
abstract class UnaryOperator extends Operator
{
    UnaryOperator()
    {
        super()
    }

    UnaryOperator(BusinessProtocolFactory factory)
    {
        super(factory)
    }

    /**
     * Method that applies the operator effect on a business protocol.
     *
     * @param protocol the input business protocol
     * @return the resulting business protocol
     */
    abstract BusinessProtocol apply(BusinessProtocol protocol)
}

/**
 * Binary operator abstract class.
 *
 * @author Julien Ponge
 */
abstract class BinaryOperator extends Operator
{
    BinaryOperator()
    {
        super()
    }

    BinaryOperator(BusinessProtocolFactory factory)
    {
        super(factory)
    }

    /**
     * Applies the operator effect on two business protocols.
     *
     * @param protocol1 the first protocol
     * @param protocol2 the second protocol
     * @return the resulting business protocol
     */
    abstract BusinessProtocol apply(BusinessProtocol protocol1, BusinessProtocol protocol2)
}