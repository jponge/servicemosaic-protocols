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

import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.State;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation for the <code>Operation</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationImpl implements Operation
{

    /**
     * Name property change.
     */
    public static final String NAME_PROPERTY_CHANGE = "name"; //$NON-NLS-1$

    /**
     * Message property change.
     */
    public static final String MESSAGE_PROPERTY_CHANGE = "message"; //$NON-NLS-1$

    /**
     * Source state property change.
     */
    public static final String SOURCE_PROPERTY_CHANGE = "source"; //$NON-NLS-1$

    /**
     * Target state property change.
     */
    public static final String TARGET_PROPERTY_CHANGE = "target"; //$NON-NLS-1$

    /**
     * Operation kind property change.
     */
    public static final String KIND_PROPERTY_CHANGE = "kind"; //$NON-NLS-1$

    /**
     * Extra property change.
     */
    public static final String EXTRA_PROPERTY_CHANGE = "extraProperty"; //$NON-NLS-1$

    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(OperationImpl.class);

    /**
     * Model events support.
     */
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /**
     * The operation name.
     */
    protected String name;

    /**
     * The operation message.
     */
    protected Message message;

    /**
     * The operation source state.
     */
    protected State sourceState;

    /**
     * The operation target state.
     */
    protected State targetState;

    /**
     * The operation kind.
     */
    protected OperationKind operationKind;

    /**
     * The extra properties.
     */
    protected Map<Object, Object> extraProperties = new HashMap<Object, Object>();

    /**
     * Name generator counter for temporary backward compatibility.
     */
    private static int nameGeneratorCounter = 0;

    /**
     * Creates a new instance.
     *
     * @param sourceState   The source state.
     * @param targetState   The target state.
     * @param message       The message.
     * @param operationKind The operation kind.
     * @see OperationImpl#OperationImpl(String, State, State, Message, OperationKind)
     * @deprecated Use a constructor that takes a name for the operation as a parameter. This
     *             constructor will provide an identifier for backward compatibility.
     */
    @Deprecated
    public OperationImpl(State sourceState, State targetState, Message message,
                         OperationKind operationKind)
    {
        this(generateOperationName(), sourceState, targetState, message, operationKind);
    }

    /**
     * Generate an operation name.
     *
     * @return The operation name;
     */
    private static String generateOperationName()
    {
        return "T" + nameGeneratorCounter++;
    }

    /**
     * Creates a new instance.
     *
     * @param name          The operation name.
     * @param sourceState   The source state.
     * @param targetState   The target state.
     * @param message       The message.
     * @param operationKind The operation kind.
     */
    public OperationImpl(String name, State sourceState, State targetState, Message message,
                         OperationKind operationKind)
    {
        super();
        setName(name);
        setSourceState(sourceState);
        setTargetState(targetState);
        setMessage(message);
        setOperationKind(operationKind);
    }

    /**
     * Convenience constructor to instanciate an explicit operation.
     *
     * @param sourceState The source state.
     * @param targetState The target state.
     * @param message     The message.
     * @see OperationImpl#OperationImpl(String, State, State, Message)
     * @deprecated Use a constructor that takes a name for the operation as a parameter. This
     *             constructor will provide an identifier for backward compatibility.
     */
    @Deprecated
    public OperationImpl(State sourceState, State targetState, Message message)
    {
        this(generateOperationName(), sourceState, targetState, message, OperationKind.EXPLICIT);
    }

    /**
     * Convenience constructor to instanciate an explicit operation.
     *
     * @param name        The operation name.
     * @param sourceState The source state.
     * @param targetState The target state.
     * @param message     The message.
     */
    public OperationImpl(String name, State sourceState, State targetState, Message message)
    {
        this(name, sourceState, targetState, message, OperationKind.EXPLICIT);
    }

    /**
     * Changes the message.
     *
     * @param message The new message.
     */
    public void setMessage(Message message)
    {
        Message oldMessage = this.message;
        this.message = message;
        listeners.firePropertyChange(MESSAGE_PROPERTY_CHANGE, oldMessage, message);

        if (log.isDebugEnabled())
        {
            log.debug("Changing the message from " + oldMessage + " to " + message); //$NON-NLS-1$ //$NON-NLS-2$
        }

    }

    /**
     * Changes the source state.
     *
     * @param sourceState The new source state.
     */
    public void setSourceState(State sourceState)
    {
        State oldState = this.sourceState;
        this.sourceState = sourceState;
        listeners.firePropertyChange(SOURCE_PROPERTY_CHANGE, oldState, sourceState);

        if (log.isDebugEnabled())
        {
            log.debug("Changing the source state from " + oldState + " to " + sourceState); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Changes the target state.
     *
     * @param targetState The new target state.
     */
    public void setTargetState(State targetState)
    {
        State oldState = this.targetState;
        this.targetState = targetState;
        listeners.firePropertyChange(TARGET_PROPERTY_CHANGE, oldState, targetState);

        if (log.isDebugEnabled())
        {
            log.debug("Changing the target state from " + oldState + " to " + targetState); //$NON-NLS-1$ //$NON-NLS-2$
        }
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

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.Operation#getMessage()
     */
    public Message getMessage()
    {
        return message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.Operation#getSourceState()
     */
    public State getSourceState()
    {
        return sourceState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.Operation#getTargetState()
     */
    public State getTargetState()
    {
        return targetState;
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

        if (arg0 != null && arg0 instanceof OperationImpl)
        {
            // Note: the operation name doesn't matter
            OperationImpl op = (OperationImpl) arg0;
            boolean eval; // We have to take care of tricky null references (seen with
            // WS-Operations)
            eval = (sourceState != null) ? (sourceState.equals(op.sourceState))
                    : (op.sourceState == null);
            eval = eval && (targetState != null) ? (targetState.equals(op.targetState))
                    : (op.targetState == null);
            eval = eval && (message != null) ? (message.equals(op.message)) : (op.message == null);
            eval = eval && (operationKind.equals(op.operationKind));
            return eval;
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
        if (log.isDebugEnabled())
        {
            log.debug("hashCode() with msg=" + message + ", src=" + sourceState + ", target="
                    + targetState + "operationKind=" + operationKind);
        }
        return (message != null ? message.hashCode() : 1)
                - (sourceState != null ? sourceState.hashCode() : 2)
                + (targetState != null ? targetState.hashCode() : 3) + operationKind.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getName()).append(": (").append(sourceState).append(",").append(message) //$NON-NLS-1$ //$NON-NLS-2$
                .append(",").append(targetState).append(",").append(operationKind).append(")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

        return buffer.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.Operation#getOperationKind()
     */
    public OperationKind getOperationKind()
    {
        return operationKind;
    }

    /**
     * Sets the operation kind.
     *
     * @param operationKind The new operation kind.
     */
    public void setOperationKind(OperationKind operationKind)
    {
        OperationKind oldKind = this.operationKind;
        this.operationKind = operationKind;
        listeners.firePropertyChange(KIND_PROPERTY_CHANGE, oldKind, operationKind);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.Operation#getName()
     */
    public String getName()
    {
        return name;
    }

    /**
     * Changes the operation name.
     *
     * @param name The new operation name.
     */
    public void setName(String name)
    {
        String oldName = this.name;
        this.name = name;
        listeners.firePropertyChange(NAME_PROPERTY_CHANGE, oldName, name);
    }

}
