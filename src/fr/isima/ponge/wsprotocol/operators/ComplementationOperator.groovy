package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.BusinessProtocol
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory
import fr.isima.ponge.wsprotocol.State

class ComplementationOperator extends UnaryOperator
{
    ComplementationOperator()
    {
        super()
    }

    ComplementationOperator(BusinessProtocolFactory factory)
    {
        super(factory)
    }

    public BusinessProtocol apply(BusinessProtocol protocol)
    {
        BusinessProtocol complement = getFactory().createBusinessProtocol("^${protocol.name}")

        // Copy states with reversed final state
        protocol.states.each { State state ->
            State newState = getFactory().createState(state.name, !state.finalState)
            complement.addState(newState)
            if (state.initialState)
            {
                complement.setInitialState(newState)
            }
        }

        // Add error state
        State q = getFactory().createState("_q_", true);
        complement.addState(q)

        

        return complement;
    }

}