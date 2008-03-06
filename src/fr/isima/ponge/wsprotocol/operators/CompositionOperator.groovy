package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.BusinessProtocol
import fr.isima.ponge.wsprotocol.Operation
import fr.isima.ponge.wsprotocol.Polarity

class CompositionOperator extends IntersectionOperator
{
    protected boolean match(Operation o1, Operation o2)
    {
        (o1.message.name == o2.message.name) && (o1.message.polarity != o2.message.polarity)
    }

    protected String protocolName(BusinessProtocol p1, BusinessProtocol p2)
    {
        "(${p1.name} ||tc ${p2.name})"
    }

    protected Polarity polarity(Polarity p)
    {
        Polarity.NULL 
    }
}