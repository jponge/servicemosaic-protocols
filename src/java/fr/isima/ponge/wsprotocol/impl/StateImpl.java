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

package fr.isima.ponge.wsprotocol.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.State;

/**
 * Implementation for the <code>State</code> interface.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateImpl implements State
{

    /** Name property change. */
    public static final String NAME_PROPERTY_CHANGE = "name"; //$NON-NLS-1$

    /** Initial status property change. */
    public static final String INITIAL_PROPERTY_CHANGE = "initial"; //$NON-NLS-1$

    /** Final status property change. */
    public static final String FINAL_PROPERTY_CHANGE = "final"; //$NON-NLS-1$

    /** Incoming operation property change. */
    public static final String IN_OPERATION_PROPERTY_CHANGE = "inOperation"; //$NON-NLS-1$

    /** Outgoing operation property change. */
    public static final String OUT_OPERATION_PROPERTY_CHANGE = "outOperation"; //$NON-NLS-1$

    /** Extra property change. */
    public static final String EXTRA_PROPERTY_CHANGE = "extraProperty"; //$NON-NLS-1$

    /** Logger. */
    private static Log log = LogFactory.getLog(StateImpl.class);

    /** Model events support. */
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /** The state name. */
    protected String name;

    /** The initial state status. */
    protected boolean initialState = false;

    /** The final state status. */
    protected boolean finalState;

    /** The predecessors list. */
    protected List predecessors = new ArrayList();

    /** The successors list. */
    protected List successors = new ArrayList();

    /** The incoming operations list. */
    protected List incomingOperations = new ArrayList();

    /** The outgoing operations list. */
    protected List outgoingOperations = new ArrayList();

    /** The extra properties. */
    protected Map extraProperties = new HashMap();

    /**
     * Constructs a new instance.
     * 
     * @param name
     *            The state name.
     * @param finalState
     *            Wether the state is final or not.
     */
    public StateImpl(String name, boolean finalState)
    {
        super();
        setName(name);
        setFinalState(finalState);
    }

    /**
     * Changes the initial state status.
     * 
     * @param initialState
     *            <code>true</code> is the state has to be initial, <code>false</code>
     *            otherwise.
     */
    public void setInitialState(boolean initialState)
    {
        boolean oldState = this.initialState;
        this.initialState = initialState;
        listeners.firePropertyChange(INITIAL_PROPERTY_CHANGE, oldState, initialState);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": changing the initial state status from " + oldState + " to " //$NON-NLS-1$ //$NON-NLS-2$
                    + initialState);
        }
    }

    /**
     * Changes the final state status.
     * 
     * @param finalState
     *            <code>true</code> is the state has to be final, <code>false</code> otherwise.
     */
    public void setFinalState(boolean finalState)
    {
        boolean oldState = this.finalState;
        this.finalState = finalState;
        listeners.firePropertyChange(FINAL_PROPERTY_CHANGE, oldState, finalState);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": changing the final state status from " + oldState + " to " //$NON-NLS-1$ //$NON-NLS-2$
                    + finalState);
        }
    }

    /**
     * Changes the state name.
     * 
     * @param name
     *            The new state name.
     */
    public void setName(String name)
    {
        String oldName = this.name;
        this.name = name;
        listeners.firePropertyChange(NAME_PROPERTY_CHANGE, oldName, name);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": changing the name from " + oldName + " to " + name); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Adds a predecessor to the state.
     * 
     * @param s
     *            A new predecessor.
     */
    public void addPredecessor(State s)
    {
        predecessors.add(s);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": adding " + s + " as a predecessor"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Removes a predecessor.
     * 
     * @param s
     *            A predecessor.
     */
    public void removePredecessor(State s)
    {
        predecessors.remove(s);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": removing " + s + " as a predecessor"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Adds a successor.
     * 
     * @param s
     *            A new successor.
     */
    public void addSuccessor(State s)
    {
        successors.add(s);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": adding " + s + " as a successor"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Removes a successor.
     * 
     * @param s
     *            A successor.
     */
    public void removeSuccessor(State s)
    {
        successors.remove(s);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": removing " + s + " as a successor"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Adds a new incoming operation.
     * 
     * @param op
     *            A new incoming operation.
     */
    public void addIncomingOperation(Operation op)
    {
        incomingOperations.add(op);
        listeners.firePropertyChange(IN_OPERATION_PROPERTY_CHANGE, null, op);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": adding " + op + " as an incoming operation"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Removes an incoming operation.
     * 
     * @param op
     *            The operation to remove.
     */
    public void removeIncomingOperation(Operation op)
    {
        incomingOperations.remove(op);
        listeners.firePropertyChange(IN_OPERATION_PROPERTY_CHANGE, op, null);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": removing " + op + " as an incoming operation"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Adds a new incoming operation.
     * 
     * @param op
     *            A new incoming operation.
     */
    public void addOutgoingOperation(Operation op)
    {
        outgoingOperations.add(op);
        listeners.firePropertyChange(OUT_OPERATION_PROPERTY_CHANGE, null, op);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": adding " + op + " as an outgoing operation"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Removes an incoming operation.
     * 
     * @param op
     *            The operation to remove.
     */
    public void removeOutgoingOperation(Operation op)
    {
        outgoingOperations.remove(op);
        listeners.firePropertyChange(OUT_OPERATION_PROPERTY_CHANGE, op, null);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": removing " + op + " as an outgoing operation"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Adds a property change listener.
     * 
     * @param listener
     *            The new property change listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }

    /**
     * Adds a property change listener.
     * 
     * @param listener
     *            The new property change listener.
     * @param propertyName
     *            The property to monitor.
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a property change listener.
     * 
     * @param listener
     *            The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     * 
     * @param propertyName
     *            The property to remove the listener from.
     * @param listener
     *            The listener to remove from.
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Gets an extra property.
     * 
     * @param key
     *            The key.
     * @return The value or <code>null</code>.
     */
    public Object getExtraProperty(Object key)
    {
        return extraProperties.get(key);
    }

    /**
     * Puts an extra property.
     * 
     * @param key
     *            The key.
     * @param value
     *            The value.
     */
    public void putExtraProperty(Object key, Object value)
    {
        extraProperties.put(key, value);
        listeners.firePropertyChange(EXTRA_PROPERTY_CHANGE, null, key);
    }

    /**
     * Removes an extra property.
     * 
     * @param key
     *            The property key.
     */
    public void removeExtraProperty(Object key)
    {
        extraProperties.remove(key);
        listeners.firePropertyChange(EXTRA_PROPERTY_CHANGE, key, null);
    }

    /**
     * Gets the keys of all the extra properties.
     * 
     * @return The set of keys.
     */
    public Set getExtraPropertiesKeys()
    {
        return extraProperties.keySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getName()
     */
    public String getName()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#isFinal()
     */
    public boolean isFinalState()
    {
        return finalState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#isInitialState()
     */
    public boolean isInitialState()
    {
        return initialState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getPredecessors()
     */
    public List getPredecessors()
    {
        return Collections.unmodifiableList(predecessors);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getSuccessors()
     */
    public List getSuccessors()
    {
        return Collections.unmodifiableList(successors);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getIncomingOperations()
     */
    public List getIncomingOperations()
    {
        return Collections.unmodifiableList(incomingOperations);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getOutgoingOperations()
     */
    public List getOutgoingOperations()
    {
        return Collections.unmodifiableList(outgoingOperations);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0)
    {
        if (log.isDebugEnabled())
        {
            log.debug("equals() on " + this + " and " + arg0); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (arg0 != null && arg0 instanceof StateImpl)
        {
            StateImpl other = (StateImpl) arg0;
            return name.equals(other.name) && finalState == other.finalState
                    && initialState == other.initialState;
        }
        else
        {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return name.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(finalState ? "((" : "(").append(name).append(finalState ? "))" : ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        return buffer.toString();
    }
}
