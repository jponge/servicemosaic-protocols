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
    public Set<State> getStates();

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
    public Set<State> getFinalStates();

    /**
     * Gets the messages of the protocol (<code>Message</code> references).
     *
     * @return The messages of the protocol.
     */
    public Set<Message> getMessages();

    /**
     * Gets the operations of the protocol (<code>Operation</code> instances).
     *
     * @return The protocol operations.
     */
    public Set<Operation> getOperations();

    /* ---- Expected protocol operations. ---- */

    /**
     * Adds a new state to the protocol.
     *
     * @param newState The new state.
     */
    public void addState(State newState);

    /**
     * Removes a state from the protocol.
     *
     * @param state The state to remove.
     */
    public void removeState(State state);

    /**
     * Changes the protocol initial state. If <code>newInitialState</code> does not belong to the
     * protocol states, then the protocol initial state is not changed.
     *
     * @param newInitialState The new initial state.
     */
    public void setInitialState(State newInitialState);

    /**
     * Adds an operation to the protocol.
     *
     * @param newOperation The new operation.
     */
    public void addOperation(Operation newOperation);

    /**
     * Removes an operation from the protocol.
     *
     * @param operation The operation to remove.
     */
    public void removeOperation(Operation operation);

}
