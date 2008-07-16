/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Universite Blaise Pascal, LIMOS, Clermont-Ferrand, France.
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

package fr.isima.ponge.wsprotocol.gefeditor.editparts.propertysources;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;

/**
 * Properties source for a <code>BusinessProtocolImpl</code> instance.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolPropertySource implements IPropertySource
{

    /**
     * The protocol.
     */
    protected BusinessProtocolImpl protocol;

    /**
     * Property for the protocol name.
     */
    protected static final String PROTOCOL_NAME_PROPERTY = "protocol.name"; //$NON-NLS-1$

    /**
     * The properties descriptors.
     */
    protected static final IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[2];

    static
    {
        propertyDescriptors[0] = new TextPropertyDescriptor(PROTOCOL_NAME_PROPERTY, Messages.protocolNameProperty); 
        propertyDescriptors[1] = new TextPropertyDescriptor(StandardExtraProperties.PROTOCOL_WSDL_URL, Messages.wsdlUrlProperty);
    }

    /**
     * Constructs a new properties source for a given protocol.
     * 
     * @param protocol
     *            The protocol.
     */
    public BusinessProtocolPropertySource(BusinessProtocolImpl protocol)
    {
        super();
        this.protocol = protocol;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    public Object getEditableValue()
    {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
     */
    public IPropertyDescriptor[] getPropertyDescriptors()
    {
        return propertyDescriptors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(Object id)
    {
        if (id.equals(PROTOCOL_NAME_PROPERTY))
        {
            return protocol.getName();
        }
        else if (id.equals(StandardExtraProperties.PROTOCOL_WSDL_URL))
        {
            Object value = protocol.getExtraProperty(StandardExtraProperties.PROTOCOL_WSDL_URL);
            return (value == null) ? "" : value;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(Object id)
    {
        if (id.equals(StandardExtraProperties.PROTOCOL_WSDL_URL))
        {
            return protocol.getExtraProperty(StandardExtraProperties.PROTOCOL_WSDL_URL) != null;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(Object id)
    {
        // Nothing to do here ...
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
     *      java.lang.Object)
     */
    public void setPropertyValue(Object id, Object value)
    {
        if (id.equals(PROTOCOL_NAME_PROPERTY))
        {
            protocol.setName((String) value);
        }
        else if (id.equals(StandardExtraProperties.PROTOCOL_WSDL_URL))
        {
            protocol.putExtraProperty(StandardExtraProperties.PROTOCOL_WSDL_URL, value);
        }
    }

}
