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
import fr.isima.ponge.wsprotocol.Polarity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the <code>Message</code> interface.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class MessageImpl implements Message
{

    /**
     * Name property change.
     */
    public static final String NAME_PROPERTY_CHANGE = "name"; //$NON-NLS-1$

    /**
     * Polarity property change.
     */
    public static final String POLARITY_PROPERTY_CHANGE = "polarity"; //$NON-NLS-1$

    /**
     * Extra property change.
     */
    public static final String EXTRA_PROPERTY_CHANGE = "extraProperty"; //$NON-NLS-1$

    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(MessageImpl.class);

    /**
     * Model events support.
     */
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /**
     * The message name.
     */
    protected String name;

    /**
     * The message polarity.
     */
    protected Polarity polarity;

    /**
     * The extra properties.
     */
    protected Map<Object, Object> extraProperties = new HashMap<Object, Object>();

    /**
     * Constructs a new instance.
     *
     * @param name     The message name.
     * @param polarity The message polarity.
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
     * @param name The new message name.
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
     * @param polarity The new message polarity.
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
