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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;
import fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode;

/**
 * The timed difference operator.
 * @author Julien Ponge (ponge@isima.fr)
 *
 */
public class DifferenceOperator extends AbstractOperator
{
    /**
     * Instantiates a new operator.
     * @param factory The factory to create protocol elements.
     */
    public DifferenceOperator(BusinessProtocolFactory factory)
    {
        super(factory);

    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.IOperator#apply(fr.isima.ponge.wsprotocol.BusinessProtocol, fr.isima.ponge.wsprotocol.BusinessProtocol)
     */
    public BusinessProtocol apply(BusinessProtocol p1, BusinessProtocol p2)
    {
        IntersectionOperator interOp = new IntersectionOperator(factory);
        BusinessProtocol p2Compl = computeComplement(p2);
        BusinessProtocol diff = interOp.apply(p1, p2Compl);
        if (diff instanceof BusinessProtocolImpl)
        {
            BusinessProtocolImpl protocol = (BusinessProtocolImpl) diff;
            protocol.setName(p1 + " ||td " + p2);            
        }
        
        return diff;
    }

    /**
     * Compute the complement of a protocol.
     * @param p The protocol .
     * @return The completement of <code>p</code>
     */
    protected BusinessProtocol computeComplement(BusinessProtocol p)
    {
        int opCounter = 0;
        BusinessProtocol pc = factory.createBusinessProtocol("^" + p.getName());

        // Copy the protocol and turn final states into normal states
        Map statesMapping = new HashMap();
        Iterator iter = p.getStates().iterator();
        while (iter.hasNext())
        {
            State s = (State) iter.next();
            State sc = factory.createState(s.getName(), false);
            pc.addState(sc);
            if (s.isInitialState())
            {
                pc.setInitialState(sc);
            }
            statesMapping.put(s, sc);
        }
        iter = p.getOperations().iterator();
        while (iter.hasNext())
        {
            Operation op = (Operation) iter.next();
            Message m = factory.createMessage(op.getMessage().getName(), op.getMessage()
                    .getPolarity());
            State src = (State) statesMapping.get(op.getSourceState());
            State trg = (State) statesMapping.get(op.getTargetState());
            Operation oc = factory.createOperation(op.getName(), src, trg, m, op.getOperationKind());
            if (op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT) != null)
            {
                oc.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT));
            }
            pc.addOperation(oc);
        }

        // Create mu
        State mu = factory.createState("mu", true);
        pc.addState(mu);
        iter = pc.getMessages().iterator();
        while (iter.hasNext())
        {
            Message msg = (Message) iter.next();
            Message m = factory.createMessage(msg.getName(), msg.getPolarity());
            pc.addOperation(factory.createOperation("Tmu" + opCounter++, mu, mu, m));
        }

        // Complete
        opCounter = 0;
        Iterator statesIt = pc.getStates().iterator();
        while (statesIt.hasNext())
        {
            State s = (State) statesIt.next();
            if (s == mu)
            {
                continue;
            }
            Set remainingMessages = new HashSet(p.getMessages());
            
            Iterator outIt = new ArrayList(s.getOutgoingOperations()).iterator();
            while (outIt.hasNext())
            {
                Operation op = (Operation) outIt.next();
                remainingMessages.remove(op.getMessage());
                
                // s ---> mu with the negation of the constraint 
                String constraint = (String) op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
                if (!isConstraintEmpty(constraint))
                {
                    IConstraintNode cstNode = parseConstraint(constraint);
                    if (cstNode == null)
                    {
                        continue;
                    }
                    Message m = factory.createMessage(op.getMessage().getName(), op.getMessage().getPolarity());
                    Operation o = factory.createOperation("Tcomp" + opCounter++, s, mu, m);
                    o.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cstNode.negate().toString());
                    pc.addOperation(o);
                }
            }
            
            // s ---> mu for the remaining messages
            Iterator msgIt = remainingMessages.iterator();
            while (msgIt.hasNext())
            {
                Message msg = (Message) msgIt.next();
                Message m = factory.createMessage(msg.getName(), msg.getPolarity());
                pc.addOperation(factory.createOperation("TComp" + opCounter, s, mu, m));
            }
        }

        return pc;
    }
}
