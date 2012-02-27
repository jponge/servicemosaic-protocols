/* 
 * CDDL HEADER START 
 * 
 * The contents of this file are subject to the terms of the 
 * Common Development and Distribution License (the "License"). 
 * You may not use this file except in compliance with the License. 
 * 
 * You can obtain a copy of the license at LICENSE.txt 
 * or at http://www.opensource.org/licenses/cddl1.php. 
 * See the License for the specific language governing permissions 
 * and limitations under the License. 
 * 
 * When distributing Covered Code, include this CDDL HEADER in each 
 * file and include the License file at LICENSE.txt. 
 * If applicable, add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your own identifying 
 * information: Portions Copyright [yyyy] [name of copyright owner] 
 * 
 * CDDL HEADER END 
 */ 

/* 
 * Copyright 2006 Julien Ponge. All rights reserved. 
 * Use is subject to license terms. 
 */ 

package fr.isima.ponge.wsprotocol.timed.operators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.State;

/**
 * The timed intersection operator.
 * @author Julien Ponge (ponge@isima.fr)
 *
 */
public class IntersectionOperator extends AbstractOperator
{

    /**
     * Instantiates a new operator.
     * @param factory The factory to create the protocol elements.
     */
    public IntersectionOperator(BusinessProtocolFactory factory)
    {
        super(factory);
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.IOperator#apply(fr.isima.ponge.wsprotocol.BusinessProtocol, fr.isima.ponge.wsprotocol.BusinessProtocol)
     */
    public BusinessProtocol apply(BusinessProtocol p1, BusinessProtocol p2)
    {
        // Result protocol
        BusinessProtocol result = factory.createBusinessProtocol(generateProtocolName(p1, p2));

        // First we need the product of the states
        Set states1 = p1.getStates();
        Set states2 = p2.getStates();
        Map mapping = new HashMap();
        Iterator sit1 = states1.iterator();
        while (sit1.hasNext())
        {
            State s1 = (State) sit1.next();
            Iterator sit2 = states2.iterator();
            while (sit2.hasNext())
            {
                State s2 = (State) sit2.next();
                State merger = factory.createState(generateMergerStateName(s1, s2), s1
                        .isFinalState()
                        && s2.isFinalState());
                merger.setInitialState(s1.isInitialState() && s2.isInitialState());
                result.addState(merger);
                mapping.put(generateMergerStateName(s1, s2), merger);
                if (merger.isInitialState())
                {
                    result.setInitialState(merger);
                }
            }
        }

        // Transitions names mappings
        Map tnameMap = new HashMap();

        // Compute the matching operations
        Set operations1 = p1.getOperations();
        Set operations2 = p2.getOperations();
        Iterator oit1 = operations1.iterator();
        while (oit1.hasNext())
        {
            Operation op1 = (Operation) oit1.next();
            Iterator oit2 = operations2.iterator();
            while (oit2.hasNext())
            {
                Operation op2 = (Operation) oit2.next();
                if (match(op1, op2))
                {
                    State merger1 = (State) mapping.get(generateMergerStateName(op1
                            .getSourceState(), op2.getSourceState()));
                    State merger2 = (State) mapping.get(generateMergerStateName(op1
                            .getTargetState(), op2.getTargetState()));
                    Message message = createMessage(op1.getMessage().getName(), op1.getMessage()
                            .getPolarity());

                    String name = generateMergerOperationName(op1, op2);
                    tnameMap.put(op1.getName() + "_", name);
                    tnameMap.put("_" + op2.getName(), name);

                    Operation operation = factory.createOperation(name, merger1, merger2, message);
                    operation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT,
                            temporalConstraintsConjunction(op1, op2));
                    result.addOperation(operation);
                }
            }
        }

        return rewriteFinalConstraints(pruneIsolatedStates(result), tnameMap);
    }

    /**
     * Template method to create a message.
     * @param name The message name.
     * @param polarity The message polarity.
     * @return The message.
     */
    protected Message createMessage(String name, Polarity polarity)
    {
        return factory.createMessage(name, polarity);
    }

    /**
     * Template method for operations matching.
     * @param op1 The first operation.
     * @param op2 The second operation.
     * @return Wether the 2 operations match according to this operator semantics or not.
     */
    protected boolean match(Operation op1, Operation op2)
    {
        return op1.getMessage().getName().equals(op2.getMessage().getName())
                && op1.getMessage().getPolarity().equals(op2.getMessage().getPolarity());
    }

    /**
     * Template method for generating protocol names.
     * @param p1 The first protocol.
     * @param p2 The second protocol.
     * @return The name of the resulting protocol of applying this operator to the 2 protocols.
     */
    protected String generateProtocolName(BusinessProtocol p1, BusinessProtocol p2)
    {
        return p1.getName() + " ||ti " + p2.getName();
    }

}
