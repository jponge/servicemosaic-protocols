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

        // Variables rewriting stuff
        p1.operations.findAll { !isOperationConstraintEmpty(it) }.each { rewriteConstraintVariables(it) { name -> "_${name}" } }
        p2.operations.findAll { !isOperationConstraintEmpty(it) }.each { rewriteConstraintVariables(it) { name -> "${name}_" } }
        p1.operations.each { operationMapping["_${it.name}"] = [] }
        p2.operations.each { operationMapping["${it.name}_"] = [] }

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
                ["_${o1.name}", "${o2.name}_"].each {
                    if (!operationMapping[it].contains(operation.name))
                    {
                        operationMapping[it] << operation.name
                    }
                }
            }
        }

        // Prune!
        def removedOperations = pruneProtocol(result)
        operationMapping.each { k, v ->
            operationMapping[k] = operationMapping[k].findAll { !removedOperations.contains(it) }
        }

        // Constraints rewriting
        result.operations.each { Operation op ->
            if (isOperationConstraintEmpty(op)) { return }
            String originalConstraint = op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)
            def originalVars = listVariablesInOperationConstraint(op)
            def mappedCombinations = originalVars.collect { operationMapping[it] }.combinations()
            def fakeOperations = []
            mappedCombinations.size().times {
                Operation fakeOperation = factory.createOperation("FAKE", null, null, null)
                fakeOperation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, originalConstraint)
                fakeOperations << fakeOperation
            }
            Iterator<Operation> fakeIterator = fakeOperations.iterator()
            mappedCombinations.each { combination ->
                def varPairs = [originalVars, combination].transpose()
                Operation fakeOp = fakeIterator.next()
                varPairs.each { pair ->
                    rewriteConstraintVariables(fakeOp) { name -> (name == pair[0]) ? pair[1] : name }
                }
            }
            def disjunction = fakeOperations.inject(null) { current, next ->
                if (current == null)
                {
                    return next.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)
                }
                else
                {
                    Operation fakeOperation = factory.createOperation("FAKE", null, null, null)
                    fakeOperation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, current)
                    return constraintDisjunction(fakeOperation, next)
                }
            }
            op.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, disjunction)
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
