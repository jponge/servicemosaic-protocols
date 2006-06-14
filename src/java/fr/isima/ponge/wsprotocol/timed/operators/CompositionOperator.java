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

public class CompositionOperator extends IntersectionOperator
{

    public CompositionOperator(BusinessProtocolFactory factory)
    {
        super(factory);
    }

    protected Message createMessage(String name, Polarity polarity)
    {
        return factory.createMessage(name, Polarity.NULL);
    }

    protected String generateProtocolName(BusinessProtocol p1, BusinessProtocol p2)
    {
        return p1.getName() + " ||tc " + p2.getName();
    }

    protected boolean match(Operation op1, Operation op2)
    {
        return op1.getMessage().getName().equals(op2.getMessage().getName())
                && (!op1.getMessage().getPolarity().equals(op2.getMessage().getPolarity()));
    }

}
