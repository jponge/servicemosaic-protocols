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
