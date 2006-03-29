/* 
 * CDDL HEADER START 
 * 
 * The contents of this file are subject to the terms of the 
 * Common Development and Distribution License (the "License"). 
 * You may not use this file except in compliance with the License. 
 * 
 * You can obtain a copy of the license at LICENSE.txt 
 * or at http://www.opensource.org/licenses/cddl1.php. 
 * See the License for the specific language governing permissions 
 * and limitations under the License. 
 * 
 * When distributing Covered Code, include this CDDL HEADER in each 
 * file and include the License file at LICENSE.txt. 
 * If applicable, add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your own identifying 
 * information: Portions Copyright [yyyy] [name of copyright owner] 
 * 
 * CDDL HEADER END 
 */ 

/* 
 * Copyright 2005, 2006 Julien Ponge. All rights reserved. 
 * Use is subject to license terms. 
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
