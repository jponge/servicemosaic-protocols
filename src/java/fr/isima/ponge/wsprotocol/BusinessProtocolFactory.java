/*
 * Copyright (c) 2005 Julien Ponge - All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
     * @param name
     *            The protocol name.
     * @return A new business protocol instance.
     */
    public BusinessProtocol createBusinessProtocol(String name);

    /**
     * Instanciates a message with a given name and a polarity.
     * 
     * @param name
     *            The message name
     * @param polarity
     *            The message polarity
     * @return A new message instance.
     */
    public Message createMessage(String name, Polarity polarity);

    /**
     * Instanciates an explicit operation.
     * 
     * @param sourceState
     *            The source state.
     * @param targetState
     *            The target state.
     * @param message
     *            The operation message.
     * @return A new operation instance.
     */
    public Operation createOperation(State sourceState, State targetState, Message message);
    
    /**
     * Instanciates an operation.
     * 
     * @param sourceState
     *            The source state.
     * @param targetState
     *            The target state.
     * @param message
     *            The operation message.
     * @return A new operation instance.
     */
    public Operation createOperation(State sourceState, State targetState, Message message, OperationKind kind);

    /**
     * Instanciates a state.
     * 
     * @param name
     *            The state name.
     * @param isFinal
     *            <code>true</code> if the state has to be final, <code>false</code> otherwise.
     * @return A new state instance.
     */
    public State createState(String name, boolean isFinal);

}
