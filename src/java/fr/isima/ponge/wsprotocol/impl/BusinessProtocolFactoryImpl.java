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
 * Copyright 2005, 2006 Julien Ponge. All rights reserved. 
 * Use is subject to license terms. 
 */ 

package fr.isima.ponge.wsprotocol.impl;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;

/**
 * An implementation for the <code>BusinessProtocolFactory</code> interface.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolFactoryImpl implements BusinessProtocolFactory
{

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocolFactory#createBusinessProtocol(java.lang.String)
     */
    public BusinessProtocol createBusinessProtocol(String name)
    {
        return new BusinessProtocolImpl(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocolFactory#createMessage(java.lang.String,
     *      fr.isima.ponge.wsprotocol.Polarity)
     */
    public Message createMessage(String name, Polarity polarity)
    {
        return new MessageImpl(name, polarity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocolFactory#createOperation(fr.isima.ponge.wsprotocol.State,
     *      fr.isima.ponge.wsprotocol.State, fr.isima.ponge.wsprotocol.Message)
     */
    public Operation createOperation(State sourceState, State targetState, Message message)
    {
        return new OperationImpl(sourceState, targetState, message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocolFactory#createState(java.lang.String, boolean)
     */
    public State createState(String name, boolean isFinal)
    {
        return new StateImpl(name, isFinal);
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.BusinessProtocolFactory#createOperation(fr.isima.ponge.wsprotocol.State, fr.isima.ponge.wsprotocol.State, fr.isima.ponge.wsprotocol.Message, fr.isima.ponge.wsprotocol.OperationKind)
     */
    public Operation createOperation(State sourceState, State targetState, Message message, OperationKind kind)
    {
        return new OperationImpl(sourceState, targetState, message, kind);
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.BusinessProtocolFactory#createOperation(java.lang.String, fr.isima.ponge.wsprotocol.State, fr.isima.ponge.wsprotocol.State, fr.isima.ponge.wsprotocol.Message, fr.isima.ponge.wsprotocol.OperationKind)
     */
    public Operation createOperation(String name, State sourceState, State targetState, Message message, OperationKind kind)
    {
        return new OperationImpl(name, sourceState, targetState, message, kind);
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.BusinessProtocolFactory#createOperation(java.lang.String, fr.isima.ponge.wsprotocol.State, fr.isima.ponge.wsprotocol.State, fr.isima.ponge.wsprotocol.Message)
     */
    public Operation createOperation(String name, State sourceState, State targetState, Message message)
    {
        return new OperationImpl(name, sourceState, targetState, message);
    }

}
