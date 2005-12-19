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
import fr.isima.ponge.wsprotocol.Polarity;

/**
 * Implementation of the <code>Message</code> interface.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class MessageImpl implements Message
{

    /** Name property change. */
    public static final String NAME_PROPERTY_CHANGE = "name"; //$NON-NLS-1$

    /** Polarity property change. */
    public static final String POLARITY_PROPERTY_CHANGE = "polarity"; //$NON-NLS-1$

    /** Extra property change. */
    public static final String EXTRA_PROPERTY_CHANGE = "extraProperty"; //$NON-NLS-1$

    /** Logger. */
    private static Log log = LogFactory.getLog(MessageImpl.class);

    /** Model events support. */
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /** The message name. */
    protected String name;

    /** The message polarity. */
    protected Polarity polarity;

    /** The extra properties. */
    protected Map extraProperties = new HashMap();

    /**
     * Constructs a new instance.
     * 
     * @param name
     *            The message name.
     * @param polarity
     *            The message polarity.
     */
    public MessageImpl(String name, Polarity polarity)
    {
        super();
        setName(name);
        setPolarity(polarity);
    }

    /**
     * Changes the message name.
     * 
     * @param name
     *            The new message name.
     */
    public void setName(String name)
    {
        String oldName = this.name;
        this.name = name;
        listeners.firePropertyChange(NAME_PROPERTY_CHANGE, oldName, name);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": changing name from " + oldName + " to " + name); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Changes the message polarity.
     * 
     * @param polarity
     *            The new message polarity.
     */
    public void setPolarity(Polarity polarity)
    {
        Polarity oldPolarity = this.polarity;
        this.polarity = polarity;
        listeners.firePropertyChange(POLARITY_PROPERTY_CHANGE, oldPolarity, polarity);

        if (log.isDebugEnabled())
        {
            log.debug(getName() + ": changing polarity from " + oldPolarity + " to " + polarity); //$NON-NLS-1$ //$NON-NLS-2$
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
     * @see fr.isima.ponge.wsprotocol.Message#getName()
     */
    public String getName()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.Message#getPolarity()
     */
    public Polarity getPolarity()
    {
        return polarity;
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

        if (arg0 != null && arg0 instanceof MessageImpl)
        {
            MessageImpl m = (MessageImpl) arg0;
            return name.equals(m.name) && polarity.equals(m.polarity);
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
        return name.hashCode() + polarity.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[").append(name).append("]").append(polarity.toString()); //$NON-NLS-1$ //$NON-NLS-2$
        return buffer.toString();
    }
}
