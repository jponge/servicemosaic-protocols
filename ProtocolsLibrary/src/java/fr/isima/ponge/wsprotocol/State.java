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

import java.io.Serializable;
import java.util.List;

/**
 * Defines an interface for a protocol state. The operations that return collections are expected to
 * return unmodifiable ones.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface State extends ExtraPropertiesKeeper, Serializable
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
     * @param initialState <code>true</code> is the state has to be initial, <code>false</code>
     *                     otherwise.
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
    public List<State> getPredecessors();

    /**
     * Gets the list of the <code>State</code> references that are after the state (there exists
     * an operation where the state is the source state).
     *
     * @return The successors list.
     */
    public List<State> getSuccessors();

    /**
     * Gets the list of the <code>Operation</code> references where the state is the target state.
     *
     * @return The incoming operations list.
     */
    public List<Operation> getIncomingOperations();

    /**
     * Gets the list of the <code>Operation</code> references where the state is the source state.
     *
     * @return The outgoing operations list.
     */
    public List<Operation> getOutgoingOperations();

}
