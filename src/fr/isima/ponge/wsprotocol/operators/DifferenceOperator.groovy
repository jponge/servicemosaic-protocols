package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*

class DifferenceOperator extends BinaryOperator
{
    private IntersectionOperator intersection;
    private ComplementationOperator complement;

    DifferenceOperator()
    {
        super()
        intersection = new IntersectionOperator()
        complement = new ComplementationOperator()
    }

    DifferenceOperator(BusinessProtocolFactory factory)
    {
        super(factory)
        intersection = new IntersectionOperator(factory)
        complement = new ComplementationOperator(factory)
    }

    @Override
    public BusinessProtocol apply(BusinessProtocol protocol1, BusinessProtocol protocol2)
    {
        def result = intersection.apply(cloneProtocol(protocol1), complement.apply(cloneProtocol(protocol2)))
        result.name = "(${protocol1.name} ||td ${protocol2.name})"
        return result
    }


}