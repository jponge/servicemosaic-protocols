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
import java.util.List;

/**
 * Defines an interface for a protocol state. The operations that return collections are expected to
 * return unmodifiable ones.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface State extends ExtraPropertiesKeeper , Serializable
{

    /**
     * Gets the state name.
     * 
     * @return The state name.
     */
    public String getName();

    /**
     * Tells wether the state is an initial state or not.
     * 
     * @return <code>true</code> if it is an initial state, <code>false</code> otherwise.
     */
    public boolean isInitialState();

    /**
     * Changes the initial state status.
     * 
     * @param initialState
     *            <code>true</code> is the state has to be initial, <code>false</code>
     *            otherwise.
     */
    public void setInitialState(boolean initialState);

    /**
     * Tells wether the state is a final state or not.
     * 
     * @return <code>true</code> if it is a final state, <code>false</code> otherwise.
     */
    public boolean isFinalState();

    /**
     * Gets the list of <code>State</code> references that are before the state (there exists an
     * operation where the state is the target state).
     * 
     * @return The predecessors list.
     */
    public List getPredecessors();

    /**
     * Gets the list of the <code>State</code> references that are after the state (there exists
     * an operation where the state is the source state).
     * 
     * @return The successors list.
     */
    public List getSuccessors();

    /**
     * Gets the list of the <code>Operation</code> references where the state is the target state.
     * 
     * @return The incoming operations list.
     */
    public List getIncomingOperations();

    /**
     * Gets the list of the <code>Operation</code> references where the state is the source state.
     * 
     * @return The outgoing operations list.
     */
    public List getOutgoingOperations();

}
