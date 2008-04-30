package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.BusinessProtocol
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory
import fr.isima.ponge.wsprotocol.Operation
import fr.isima.ponge.wsprotocol.StandardExtraProperties
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

    protected String constraintConjunction(Operation o1, Operation o2)
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
        BooleanNode andNode = new BooleanNode(BooleanNode.AND, root1, root2)
        ast.node = andNode
        return ast.toString()
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