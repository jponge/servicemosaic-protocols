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

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.State;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Implementation for <code>BusinessProtocol</code> interface.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolImpl implements BusinessProtocol
{

    /**
     * Name property change.
     */
    public static final String NAME_PROPERTY_CHANGE = "name"; //$NON-NLS-1$

    /**
     * Initial state property change.
     */
    public static final String INITIAL_STATE_PROPERTY_CHANGE = "initialState"; //$NON-NLS-1$

    /**
     * States property change.
     */
    public static final String STATES_PROPERTY_CHANGE = "states"; //$NON-NLS-1$

    /**
     * Operations property change.
     */
    public static final String OPERATIONS_PROPERTY_CHANGE = "operations"; //$NON-NLS-1$

    /**
     * Extra property change.
     */
    public static final String EXTRA_PROPERTY_CHANGE = "extraProperty"; //$NON-NLS-1$

    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(BusinessProtocolImpl.class);

    /**
     * Model events support.
     */
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /**
     * The protocol name.
     */
    protected String name;

    /**
     * The states.
     */
    protected Set<State> states = new HashSet<State>();

    /**
     * The initial state.
     */
    protected State initialState;

    /**
     * The final states.
     */
    protected Set<State> finalStates = new HashSet<State>();

    /**
     * The messages.
     */
    protected Set<Message> messages = new HashSet<Message>();

    /**
     * The operations.
     */
    protected Set<Operation> operations = new HashSet<Operation>();

    /**
     * The extra properties.
     */
    protected Map<Object, Object> extraProperties = new HashMap<Object, Object>();

    /**
     * Instanciates a new empty business protocol (i.e. with no states and no operations).
     *
     * @param name The protocol name.
     */
    public BusinessProtocolImpl(String name)
    {
        super();
        setName(name);
    }

    /**
     * Changes the protocol name.
     *
     * @param name The new name.
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
     * Adds a property change listener.
     *
     * @param listener The listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }

    /**
     * Adds a property change listener.
     *
     * @param propertyName The property.
     * @param listener     The listener.
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a property change listener.
     *
     * @param listener The listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     *
     * @param propertyName The property.
     * @param listener     The listener.
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
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#getName()
     */
    public String getName()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#getStates()
     */
    public Set<State> getStates()
    {
        return Collections.unmodifiableSet(states);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#getInitialState()
     */
    public State getInitialState()
    {
        return initialState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#setInitialState(fr.isima.ponge.wsprotocol.State)
     */
    public void setInitialState(State newInitialState)
    {
        State oldState = this.initialState;
        initialState = newInitialState;
        if (oldState != null)
        {
            oldState.setInitialState(false);
        }
        if (newInitialState != null)
        {
            newInitialState.setInitialState(true);
        }
        listeners.firePropertyChange(INITIAL_STATE_PROPERTY_CHANGE, oldState, newInitialState);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": changing the initial state from " + oldState + " to " //$NON-NLS-1$ //$NON-NLS-2$
                    + newInitialState);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#getFinalStates()
     */
    public Set<State> getFinalStates()
    {
        finalStates.clear();
        for (Object state : states)
        {
            State s = (State) state;
            if (s.isFinalState())
            {
                finalStates.add(s);
            }
        }
        return Collections.unmodifiableSet(finalStates);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#getMessages()
     */
    public Set<Message> getMessages()
    {
        return Collections.unmodifiableSet(messages);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#getOperations()
     */
    public Set<Operation> getOperations()
    {
        return Collections.unmodifiableSet(operations);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#addState(fr.isima.ponge.wsprotocol.State)
     */
    public void addState(State newState)
    {
        states.add(newState);
        if (newState.isFinalState())
        {
            finalStates.add(newState);
        }
        listeners.firePropertyChange(STATES_PROPERTY_CHANGE, null, newState);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": adding " + newState); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#removeState(fr.isima.ponge.wsprotocol.State)
     */
    public void removeState(State state)
    {
        states.remove(state);
        if (state.isFinalState())
        {
            finalStates.remove(state);
        }
        if (state.equals(initialState))
        {
            initialState = null;
        }
        listeners.firePropertyChange(STATES_PROPERTY_CHANGE, state, null);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": removing " + state); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#addOperation(fr.isima.ponge.wsprotocol.Operation)
     */
    public void addOperation(Operation newOperation)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Adding " + newOperation); //$NON-NLS-1$
        }

        operations.add(newOperation);
        messages.add(newOperation.getMessage());

        // Ensures the model integrity unless you make hazardous instanciations
        if (newOperation.getSourceState() instanceof StateImpl
                && newOperation.getTargetState() instanceof StateImpl)
        {
            StateImpl s1 = (StateImpl) newOperation.getSourceState();
            StateImpl s2 = (StateImpl) newOperation.getTargetState();
            s1.addSuccessor(s2);
            s2.addPredecessor(s1);
            s1.addOutgoingOperation(newOperation);
            s2.addIncomingOperation(newOperation);
        }

        listeners.firePropertyChange(OPERATIONS_PROPERTY_CHANGE, null, newOperation);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": adding " + newOperation); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.BusinessProtocol#removeOperation(fr.isima.ponge.wsprotocol.Operation)
     */
    public void removeOperation(Operation operation)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Removing " + operation); //$NON-NLS-1$
        }

        operations.remove(operation);

        int counter = 0;
        for (Object operation1 : operations)
        {
            Operation op = (Operation) operation1;
            if (op.getMessage().equals(operation.getMessage()))
            {
                counter = counter + 1;
            }
        }
        if (counter == 0)
        {
            messages.remove(operation.getMessage());
        }

        // Ensures the model integrity unless you make hazardous instanciations
        if (operation.getSourceState() instanceof StateImpl
                && operation.getTargetState() instanceof StateImpl)
        {
            StateImpl s1 = (StateImpl) operation.getSourceState();
            StateImpl s2 = (StateImpl) operation.getTargetState();
            s1.removeSuccessor(s2);
            s2.removePredecessor(s1);
            s1.removeOutgoingOperation(operation);
            s2.removeIncomingOperation(operation);
        }

        listeners.firePropertyChange(OPERATIONS_PROPERTY_CHANGE, operation, null);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": removing " + operation); //$NON-NLS-1$
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (log.isDebugEnabled())
        {
            log.debug("equals() on " + this + " and " + obj); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (obj != null && obj instanceof BusinessProtocolImpl)
        {
            BusinessProtocolImpl b = (BusinessProtocolImpl) obj;
            return name.equals(b.name)
                    && ((initialState != null) ? (initialState.equals(b.initialState))
                    : (b.initialState == null)) && states.equals(b.states)
                    && getFinalStates().equals(b.getFinalStates()) && messages.equals(b.messages)
                    && operations.equals(b.operations);
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
        return name;
    }

}
