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

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;
import fr.isima.ponge.wsprotocol.impl.StateImpl;

/**
 * The properties source for states.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StatePropertySource implements IPropertySource
{

    /**
     * The protocol which the state belongs to.
     */
    protected BusinessProtocolImpl protocol;

    /**
     * The state.
     */
    protected StateImpl state;

    /**
     * The state final status property name.
     */
    protected static final String STATE_FINAL_PROPERTY = "state.final"; //$NON-NLS-1$

    /**
     * The state initial status property name.
     */
    protected static final String STATE_INITIAL_PROPERTY = "state.initial"; //$NON-NLS-1$

    /**
     * The state name property name.
     */
    protected static final String STATE_NAME_PROPERTY = "state.name"; //$NON-NLS-1$

    /**
     * The properties descriptors.
     */
    protected static final IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[3];

    static
    {
        String[] choices = { "false", "true" }; //$NON-NLS-1$ //$NON-NLS-2$
        propertyDescriptors[0] = new TextPropertyDescriptor(STATE_NAME_PROPERTY,
                Messages.stateNameProperty);
        propertyDescriptors[1] = new ComboBoxPropertyDescriptor(STATE_INITIAL_PROPERTY,
                Messages.initialStateProperty, choices);
        propertyDescriptors[2] = new ComboBoxPropertyDescriptor(STATE_FINAL_PROPERTY,
                Messages.finalStateProperty, choices);
    }

    /**
     * Constructs a new states properties source.
     * 
     * @param protocol
     *            The protocol.
     * @param state
     *            The state.
     */
    public StatePropertySource(BusinessProtocolImpl protocol, StateImpl state)
    {
        super();
        this.protocol = protocol;
        this.state = state;
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
        if (id.equals(STATE_NAME_PROPERTY))
        {
            return state.getName();
        }
        else if (id.equals(STATE_INITIAL_PROPERTY))
        {
            return state.isInitialState() ? Integer.valueOf("1") : Integer.valueOf("0"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        else if (id.equals(STATE_FINAL_PROPERTY))
        {
            return state.isFinalState() ? Integer.valueOf("1") : Integer.valueOf("0"); //$NON-NLS-1$ //$NON-NLS-2$
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
        // All properties are mandatory in the model
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
        if (id.equals(STATE_NAME_PROPERTY))
        {
            state.setName((String) value);
        }
        else if (id.equals(STATE_INITIAL_PROPERTY))
        {
            state.setInitialState(value.equals(Integer.valueOf("1"))); //$NON-NLS-1$
            if (state.isInitialState())
            {
                protocol.setInitialState(state);
            }
            else
            {
                protocol.setInitialState(null);
            }

        }
        else if (id.equals(STATE_FINAL_PROPERTY))
        {
            state.setFinalState(value.equals(Integer.valueOf("1"))); //$NON-NLS-1$
        }
    }

}
