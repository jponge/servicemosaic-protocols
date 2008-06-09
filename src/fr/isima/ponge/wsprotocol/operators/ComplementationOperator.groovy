package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*

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

    public BusinessProtocol apply(BusinessProtocol p)
    {
        BusinessProtocol protocol = cloneProtocol(p)
        BusinessProtocol complement = getFactory().createBusinessProtocol("^${protocol.name}")
        def statesMap = [:]

        // Copy states with reversed final state
        protocol.states.each {State state ->
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
        protocol.states.each {State state ->
            def operationsMap = [:]
            state.outgoingOperations.collect { it.message }.unique().each { operationsMap[it] = [] }

            // Copy the operations
            state.outgoingOperations.each {Operation operation ->
                Operation newOperation = getFactory().createOperation(
                        operation.name,
                        statesMap[operation.sourceState],
                        statesMap[operation.targetState],
                        getFactory().createMessage(operation.message.name, operation.message.polarity),
                        operation.operationKind)
                newOperation.putExtraProperty(
                        StandardExtraProperties.TEMPORAL_CONSTRAINT,
                        operation.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT))
                complement.addOperation(newOperation)
                if (newOperation.operationKind == OperationKind.EXPLICIT)
                {
                    operationsMap[newOperation.message] << newOperation
                }
            }

            // Add the negation operation, if any
            operationsMap.each {message, operations ->
                Operation fakeOperation = factory.createOperation("FAKE", null, null, null)
                Operation negationOperation = getFactory().createOperation(
                        statesMap[state], q,
                        getFactory().createMessage(message.name, message.polarity),
                        OperationKind.EXPLICIT)
                operations.each {Operation operation ->
                    def constraintNegation = negateConstraint(operation)
                    fakeOperation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, constraintNegation)
                    negationOperation.putExtraProperty(
                            StandardExtraProperties.TEMPORAL_CONSTRAINT,
                            constraintConjunction(negationOperation, fakeOperation))
                }
                if (!isOperationConstraintEmpty(negationOperation))
                {
                    complement.addOperation(negationOperation)
                }
            }

            // Add the remaining negation operations
            (protocol.messages - operationsMap.keySet()).each {message ->
                Operation negationOperation = getFactory().createOperation(
                        statesMap[state], q,
                        getFactory().createMessage(message.name, message.polarity),
                        OperationKind.EXPLICIT)
                complement.addOperation(negationOperation)
            }
        }

        // Add q -> q operations
        protocol.messages.each {message ->
            Operation negationOperation = getFactory().createOperation(
                    q, q,
                    getFactory().createMessage(message.name, message.polarity),
                    OperationKind.EXPLICIT)
            complement.addOperation(negationOperation)
        }

        return complement;
    }

}