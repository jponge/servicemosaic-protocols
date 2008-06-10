package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl
import fr.isima.ponge.wsprotocol.timed.constraints.*
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintLexer
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintParser
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintTreeWalker

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
}

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

    abstract BusinessProtocol apply(BusinessProtocol protocol)
}

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

    abstract BusinessProtocol apply(BusinessProtocol protocol1, BusinessProtocol protocol2)
}