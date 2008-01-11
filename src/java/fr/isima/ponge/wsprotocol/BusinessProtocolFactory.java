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
