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

package fr.isima.ponge.wsprotocol;

/**
 * Interface for a factory that instanciates the various parts of a business protocol.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface BusinessProtocolFactory
{

    /**
     * Instanciates an empty (no states, no operations) business protocol with a given name.
     *
     * @param name The protocol name.
     * @return A new business protocol instance.
     */
    public BusinessProtocol createBusinessProtocol(String name);

    /**
     * Instanciates a message with a given name and a polarity.
     *
     * @param name     The message name
     * @param polarity The message polarity
     * @return A new message instance.
     */
    public Message createMessage(String name, Polarity polarity);

    /**
     * Instanciates an explicit operation.
     *
     * @param sourceState The source state.
     * @param targetState The target state.
     * @param message     The operation message.
     * @return A new operation instance.
     * @see BusinessProtocolFactory#createOperation(String, State, State, Message)
     * @deprecated Operations should be explicitely created with a name.
     */
    public Operation createOperation(State sourceState, State targetState, Message message);

    /**
     * Instanciates an operation.
     *
     * @param name        The operation name.
     * @param sourceState The source state.
     * @param targetState The target state.
     * @param message     The operation message.
     * @param kind        The kind of message
     * @return A new operation instance.
     */
    public Operation createOperation(String name, State sourceState, State targetState, Message message, OperationKind kind);

    /**
     * Instanciates an explicit operation.
     *
     * @param name        The operation name.
     * @param sourceState The source state.
     * @param targetState The target state.
     * @param message     The operation message.
     * @return A new operation instance.
     */
    public Operation createOperation(String name, State sourceState, State targetState, Message message);

    /**
     * Instanciates an operation.
     *
     * @param sourceState The source state.
     * @param targetState The target state.
     * @param message     The operation message.
     * @param kind        The operation kind.
     * @return A new operation instance.
     * @see BusinessProtocolFactory#createOperation(String, State, State, Message, OperationKind)
     * @deprecated Operations should be explicitely created with a name.
     */
    public Operation createOperation(State sourceState, State targetState, Message message, OperationKind kind);

    /**
     * Instanciates a state.
     *
     * @param name    The state name.
     * @param isFinal <code>true</code> if the state has to be final, <code>false</code> otherwise.
     * @return A new state instance.
     */
    public State createState(String name, boolean isFinal);

}
