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

package fr.isima.ponge.wsprotocol.gefeditor.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import fr.isima.ponge.wsprotocol.gefeditor.editparts.propertysources.BusinessProtocolPropertySource;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;

/**
 * The edit part for a <code>BusinessProtocol</code>, to be used in the outline view.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolTreeEditPart extends AbstractTreeEditPart implements
        PropertyChangeListener
{

    /*
     * Returns a casted model.
     */
    private BusinessProtocolImpl getCastedModel()
    {
        return (BusinessProtocolImpl) getModel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.EditPart#activate()
     */
    public void activate()
    {
        if (!isActive())
        {
            super.activate();
            getCastedModel().addPropertyChangeListener(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
     */
    protected void createEditPolicies()
    {
        if (getParent() instanceof RootEditPart)
        {
            installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.EditPart#deactivate()
     */
    public void deactivate()
    {
        if (isActive())
        {
            super.deactivate();
            getCastedModel().removePropertyChangeListener(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
     */
    protected List getModelChildren()
    {
        return Collections.list(Collections.enumeration(getCastedModel().getStates()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
     */
    protected String getText()
    {
        return getCastedModel().getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        refresh();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class key)
    {
        if (key == IPropertySource.class)
        {
            return new BusinessProtocolPropertySource(getCastedModel());
        }
        return super.getAdapter(key);
    }

}
