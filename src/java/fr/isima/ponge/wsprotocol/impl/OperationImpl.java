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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.State;

/**
 * Implementation for the <code>Operation</code> class.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationImpl implements Operation
{

    /** Name property change. */
    public static final String NAME_PROPERTY_CHANGE = "name"; //$NON-NLS-1$

    /** Message property change. */
    public static final String MESSAGE_PROPERTY_CHANGE = "message"; //$NON-NLS-1$

    /** Source state property change. */
    public static final String SOURCE_PROPERTY_CHANGE = "source"; //$NON-NLS-1$

    /** Target state property change. */
    public static final String TARGET_PROPERTY_CHANGE = "target"; //$NON-NLS-1$

    /** Operation kind property change. */
    public static final String KIND_PROPERTY_CHANGE = "kind"; //$NON-NLS-1$
    
    /** Extra property change. */
    public static final String EXTRA_PROPERTY_CHANGE = "extraProperty"; //$NON-NLS-1$

    /** Logger. */
    private static Log log = LogFactory.getLog(OperationImpl.class);

    /** Model events support. */
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /** The operation message. */
    protected Message message;

    /** The operation source state. */
    protected State sourceState;

    /** The operation target state. */
    protected State targetState;
    
    /** The operation kind. */
    protected OperationKind operationKind;

    /** The extra properties. */
    protected Map extraProperties = new HashMap();

    /**
     * Creates a new instance.
     * 
     * @param sourceState
     *            The source state.
     * @param targetState
     *            The target state.
     * @param message
     *            The message.
     */
    public OperationImpl(State sourceState, State targetState, Message message, OperationKind operationKind)
    {
        super();
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
     * @param message The message.
     */
    public OperationImpl(State sourceState, State targetState, Message message)
    {
        this(sourceState, targetState, message, OperationKind.EXPLICIT);
    }

    /**
     * Changes the message.
     * 
     * @param message
     *            The new message.
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
     * @param sourceState
     *            The new source state.
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
     * @param targetState
     *            The new target state.
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

    /**
     * Adds a property change listener.
     * 
     * @param listener
     *            The listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }

    /**
     * Adds a property change listener.
     * 
     * @param propertyName
     *            The property.
     * @param listener
     *            The listener.
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a property change listener.
     * 
     * @param listener
     *            The listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.removePropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     * 
     * @param propertyName
     *            The property.
     * @param listener
     *            The listener.
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
            OperationImpl op = (OperationImpl) arg0;
            boolean eval; // We have to take care of tricky null references (seen with WS-Operations)
            eval = (sourceState != null) ? (sourceState.equals(op.sourceState)) : (op.sourceState == null);
            eval = eval && (targetState != null) ? (targetState.equals(op.targetState)) : (op.targetState == null);
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
        buffer.append("(").append(sourceState).append(",").append(message) //$NON-NLS-1$ //$NON-NLS-2$
                .append(",").append(targetState).append(",").append(operationKind).append(")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

        return buffer.toString();
    }

    /* (non-Javadoc)
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

}
