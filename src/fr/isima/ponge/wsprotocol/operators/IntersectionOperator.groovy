package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*

class IntersectionOperator extends BinaryOperator
{
    IntersectionOperator()
    {
        super()
    }

    IntersectionOperator(BusinessProtocolFactory factory)
    {
        super(factory)
    }

    @Override
    BusinessProtocol apply(BusinessProtocol protocol1, BusinessProtocol protocol2)
    {
        BusinessProtocol p1 = cloneProtocol(protocol1)
        BusinessProtocol p2 = cloneProtocol(protocol2)

        BusinessProtocol result = getFactory().createBusinessProtocol(protocolName(p1, p2))
        def resultStates = [:]
        def operationMapping = [:]

        // Walk each state combination 
        def stateSpace = [p1.states.asList(), p2.states.asList()].combinations()
        stateSpace.each {State s1, State s2 ->

            // Compute the common outgoing operations
            if (s1.outgoingOperations.isEmpty() || s2.outgoingOperations.isEmpty())
            {
                return
            }
            def common = [s1.outgoingOperations, s2.outgoingOperations].combinations().findAll {o1, o2 -> match(o1, o2)}

            // Add each of them to the intersection protocol
            common.each {Operation o1, Operation o2 ->

                def sourceState = [s1, s2]
                def targetState = [o1.targetState, o2.targetState]

                // Ensure each merger state exists...
                [sourceState, targetState].each {s ->
                    if (!resultStates.containsKey(s))
                    {
                        def state = getFactory().createState(stateName(s[0], s[1]), s[0].finalState && s[1].finalState)
                        state.setInitialState(s[0].initialState && s[1].initialState)
                        resultStates[s] = state
                        result.addState(state)
                        if (state.isInitialState())
                        {
                            result.setInitialState(state)
                        }
                    }
                }

                // Temporary variables rewriting
                if (!isOperationConstraintEmpty(o1))
                {
                    rewriteConstraintVariables(o1) { name -> "_${name}" }
                }
                if (!isOperationConstraintEmpty(o2))
                {
                    rewriteConstraintVariables(o2) { name -> "${name}_" }
                }

                // Add the operation
                def Operation operation = getFactory().createOperation(
                        operationName(o1, o2),
                        resultStates[sourceState],
                        resultStates[targetState],
                        getFactory().createMessage(o1.message.name, polarity(o1.message.polarity)),
                        o1.operationKind)
                def conjunction = constraintConjunction(o1, o2)
                if (conjunction != "")
                {
                    operation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, conjunction)
                }
                result.addOperation(operation)

                // Keep the mapping for rewriting the constraints
                operationMapping["_${o1.name}"] = operation.name
                operationMapping["${o2.name}_"] = operation.name    
            }
        }

        // Constraints rewriting
        result.operations.each { Operation op ->
            if (!isOperationConstraintEmpty(op))
            {
                rewriteConstraintVariables(op) { name -> operationMapping[name] }
            }
        }

        return result;
    }

    protected boolean match(Operation o1, Operation o2)
    {
        (o1.message.name == o2.message.name) && (o1.message.polarity == o2.message.polarity)
    }

    protected String operationName(Operation o1, Operation o2)
    {
        "${o1.name}_${o2.name}"
    }

    protected String stateName(State s1, State s2)
    {
        "(${s1.name},${s2.name})"
    }

    protected String protocolName(BusinessProtocol p1, BusinessProtocol p2)
    {
        "(${p1.name} ||ti ${p2.name})"
    }

    protected Polarity polarity(Polarity p)
    {
        p 
    }
}