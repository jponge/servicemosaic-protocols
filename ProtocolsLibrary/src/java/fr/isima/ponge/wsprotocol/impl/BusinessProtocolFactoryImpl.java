/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Université Blaise Pascal, LIMOS, Clermont-Ferrand, France.
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


package fr.isima.ponge.wsprotocol.impl;

import fr.isima.ponge.wsprotocol.*;

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
    @Deprecated
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
    @Deprecated
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
