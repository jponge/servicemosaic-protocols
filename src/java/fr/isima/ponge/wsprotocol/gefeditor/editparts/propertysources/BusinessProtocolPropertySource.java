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
