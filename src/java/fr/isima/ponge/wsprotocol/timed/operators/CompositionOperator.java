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

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.Polarity;

/**
 * The timed composition operator.
 * @author Julien Ponge (ponge@isima.fr)
 *
 */
public class CompositionOperator extends IntersectionOperator
{

    /**
     * Instantiates a new operator.
     * @param factory The factory to create protocol elements.
     */
    public CompositionOperator(BusinessProtocolFactory factory)
    {
        super(factory);
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.IntersectionOperator#createMessage(java.lang.String, fr.isima.ponge.wsprotocol.Polarity)
     */
    protected Message createMessage(String name, Polarity polarity)
    {
        return factory.createMessage(name, Polarity.NULL);
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.IntersectionOperator#generateProtocolName(fr.isima.ponge.wsprotocol.BusinessProtocol, fr.isima.ponge.wsprotocol.BusinessProtocol)
     */
    protected String generateProtocolName(BusinessProtocol p1, BusinessProtocol p2)
    {
        return p1.getName() + " ||tc " + p2.getName();
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.IntersectionOperator#match(fr.isima.ponge.wsprotocol.Operation, fr.isima.ponge.wsprotocol.Operation)
     */
    protected boolean match(Operation op1, Operation op2)
    {
        return op1.getMessage().getName().equals(op2.getMessage().getName())
                && (!op1.getMessage().getPolarity().equals(op2.getMessage().getPolarity()));
    }

}
