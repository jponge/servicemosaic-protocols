package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.BusinessProtocol
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory
import fr.isima.ponge.wsprotocol.State
import fr.isima.ponge.wsprotocol.Operation

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
        def statesMap = [:]

        // Copy states with reversed final state
        protocol.states.each { State state ->
            State newState = getFactory().createState(state.name, !state.finalState)
            complement.addState(newState)
            if (state.initialState)
            {
                complement.setInitialState(newState)
            }
            statesMap[state] = newState
        }

        // Add error state
        State q = getFactory().createState("_q_", true);
        complement.addState(q)

        // Add operations
        protocol.states.each { State state ->
            def operationsMap = [:]
            state.outgoingOperations.collect { it.message.name }.unique().each { operationsMap[it] = [] } 

            state.outgoingOperations.each { Operation operation ->
                Operation newOperation = getFactory().createOperation(
                        statesMap[operation.sourceState],
                        statesMap[operation.targetState],
                        getFactory().createMessage(operation.message.name, operation.message.polarity),
                        operation.operationKind)
                complement.addOperation(newOperation)
                operationsMap[newOperation.message.name] << newOperation
            }

            operationsMap.each { messageName, operations ->
                // Compute the negation
            }
        }

        return complement;
    }

}