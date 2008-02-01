package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.BusinessProtocol
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl
import fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode
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

    protected boolean isConstraintEmpty(String constraint)
    {
        (constraint == null) || ("" == constraint)
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