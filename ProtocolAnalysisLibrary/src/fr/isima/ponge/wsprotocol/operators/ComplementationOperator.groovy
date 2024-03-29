/*
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Universite Blaise Pascal, LIMOS, Clermont-Ferrand, France.
 * Copyright 2005-2008 The University of New South Wales, Sydney, Australia.
 *
 * This file is part of ServiceMosaic Protocols <http://servicemosaic.isima.fr/>.
 *
 * ServiceMosaic Protocols is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ServiceMosaic Protocols is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ServiceMosaic Protocols.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*

/**
 * Computes the complement of a business protocol.
 * The resulting protocol recognizes all the timed conversations that
 * are not recognized by the input one.
 *
 * @author Julien Ponge
 */
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
        int newOperationsCounter = 0;

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
                        "nT${newOperationsCounter++}",
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
                        "nT${newOperationsCounter++}",
                        statesMap[state], q,
                        getFactory().createMessage(message.name, message.polarity),
                        OperationKind.EXPLICIT)
                complement.addOperation(negationOperation)
            }
        }

        // Add q -> q operations
        protocol.messages.each {message ->
            Operation negationOperation = getFactory().createOperation(
                    "nT${newOperationsCounter++}",
                    q, q,
                    getFactory().createMessage(message.name, message.polarity),
                    OperationKind.EXPLICIT)
            complement.addOperation(negationOperation)
        }

        return complement;
    }

}