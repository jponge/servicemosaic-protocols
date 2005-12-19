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
