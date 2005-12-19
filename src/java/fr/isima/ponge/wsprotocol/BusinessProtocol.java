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

import java.io.Serializable;
import java.util.Set;

/**
 * Defines an interface for a business protocol. A definition of <em>business protocols</em> can
 * be found in <em>Boualem Benatallah,
 * Fabio Casati, and Farouk Toumani. Analysis and Management of Web Services
 * Protocols. Procs of ER 2004. Shanghai, China. Nov 2004.</em>.
 * The operations that return collections are expected to return unmodifiable ones. A class that
 * implements this interface must be reponsible for ensuring that the various protocol integrity
 * constraints are met.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface BusinessProtocol extends ExtraPropertiesKeeper, Serializable
{

    /* ---- Canonical operations (i.e., defined in the ER paper). ---- */

    /**
     * Gets the protocol name.
     * 
     * @return The protocol name.
     */
    public String getName();

    /**
     * Gets the set of states (<code>State</code> references).
     * 
     * @return The states of the protocol.
     */
    public Set getStates();

    /**
     * Gets the protocol initial state.
     * 
     * @return The initial state of the protocol.
     */
    public State getInitialState();

    /**
     * Gets the protocol final states (<code>State</code> references).
     * 
     * @return The final states of the protocol.
     */
    public Set getFinalStates();

    /**
     * Gets the messages of the protocol (<code>Message</code> references).
     * 
     * @return The messages of the protocol.
     */
    public Set getMessages();

    /**
     * Gets the operations of the protocol (<code>Operation</code> instances).
     * 
     * @return The protocol operations.
     */
    public Set getOperations();

    /* ---- Expected protocol operations. ---- */

    /**
     * Adds a new state to the protocol.
     * 
     * @param newState
     *            The new state.
     */
    public void addState(State newState);

    /**
     * Removes a state from the protocol.
     * 
     * @param state
     *            The state to remove.
     */
    public void removeState(State state);

    /**
     * Changes the protocol initial state. If <code>newInitialState</code> does not belong to the
     * protocol states, then the protocol initial state is not changed.
     * 
     * @param newInitialState
     *            The new initial state.
     */
    public void setInitialState(State newInitialState);

    /**
     * Adds an operation to the protocol.
     * 
     * @param newOperation
     *            The new operation.
     */
    public void addOperation(Operation newOperation);

    /**
     * Removes an operation from the protocol.
     * 
     * @param operation
     *            The operation to remove.
     */
    public void removeOperation(Operation operation);

}
