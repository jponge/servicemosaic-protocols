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

import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.State;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Implementation for the <code>State</code> interface.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateImpl implements State
{

    /**
     * Name property change.
     */
    public static final String NAME_PROPERTY_CHANGE = "name"; //$NON-NLS-1$

    /**
     * Initial status property change.
     */
    public static final String INITIAL_PROPERTY_CHANGE = "initial"; //$NON-NLS-1$

    /**
     * Final status property change.
     */
    public static final String FINAL_PROPERTY_CHANGE = "final"; //$NON-NLS-1$

    /**
     * Incoming operation property change.
     */
    public static final String IN_OPERATION_PROPERTY_CHANGE = "inOperation"; //$NON-NLS-1$

    /**
     * Outgoing operation property change.
     */
    public static final String OUT_OPERATION_PROPERTY_CHANGE = "outOperation"; //$NON-NLS-1$

    /**
     * Extra property change.
     */
    public static final String EXTRA_PROPERTY_CHANGE = "extraProperty"; //$NON-NLS-1$

    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(StateImpl.class);

    /**
     * Model events support.
     */
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /**
     * The state name.
     */
    protected String name;

    /**
     * The initial state status.
     */
    protected boolean initialState = false;

    /**
     * The final state status.
     */
    protected boolean finalState;

    /**
     * The predecessors list.
     */
    protected List<State> predecessors = new ArrayList<State>();

    /**
     * The successors list.
     */
    protected List<State> successors = new ArrayList<State>();

    /**
     * The incoming operations list.
     */
    protected List<Operation> incomingOperations = new ArrayList<Operation>();

    /**
     * The outgoing operations list.
     */
    protected List<Operation> outgoingOperations = new ArrayList<Operation>();

    /**
     * The extra properties.
     */
    protected Map<Object, Object> extraProperties = new HashMap<Object, Object>();

    /**
     * Constructs a new instance.
     *
     * @param name       The state name.
     * @param finalState Wether the state is final or not.
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
     * @param initialState <code>true</code> is the state has to be initial, <code>false</code>
     *                     otherwise.
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
     * @param finalState <code>true</code> is the state has to be final, <code>false</code> otherwise.
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
     * @param name The new state name.
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
     * @param s A new predecessor.
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
     * @param s A predecessor.
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
     * @param s A new successor.
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
     * @param s A successor.
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
     * @param op A new incoming operation.
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
     * @param op The operation to remove.
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
     * @param op A new incoming operation.
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
     * @param op The operation to remove.
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
     * @param listener The new property change listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }

    /**
     * Adds a property change listener.
     *
     * @param listener     The new property change listener.
     * @param propertyName The property to monitor.
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a property change listener.
     *
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     *
     * @param propertyName The property to remove the listener from.
     * @param listener     The listener to remove from.
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Gets an extra property.
     *
     * @param key The key.
     * @return The value or <code>null</code>.
     */
    public Object getExtraProperty(Object key)
    {
        return extraProperties.get(key);
    }

    /**
     * Puts an extra property.
     *
     * @param key   The key.
     * @param value The value.
     */
    public void putExtraProperty(Object key, Object value)
    {
        extraProperties.put(key, value);
        listeners.firePropertyChange(EXTRA_PROPERTY_CHANGE, null, key);
    }

    /**
     * Removes an extra property.
     *
     * @param key The property key.
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
    public Set<Object> getExtraPropertiesKeys()
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
    public List<State> getPredecessors()
    {
        return Collections.unmodifiableList(predecessors);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getSuccessors()
     */
    public List<State> getSuccessors()
    {
        return Collections.unmodifiableList(successors);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getIncomingOperations()
     */
    public List<Operation> getIncomingOperations()
    {
        return Collections.unmodifiableList(incomingOperations);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.State#getOutgoingOperations()
     */
    public List<Operation> getOutgoingOperations()
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
