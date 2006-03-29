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

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.EditorPlugin;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.propertysources.StatePropertySource;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;
import fr.isima.ponge.wsprotocol.impl.StateImpl;

/**
 * The edit part for <code>State</code> instances, to be used in outline views.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener
{

    // Image code for a state that is initial.
    private static final String INITIAL_IMG = "initial"; //$NON-NLS-1$

    // Image code for a state that is normal.
    private static final String NORMAL_IMG = "normal"; //$NON-NLS-1$

    // Image code for a state that is final.
    private static final String FINAL_IMG = "final"; //$NON-NLS-1$

    // The images registry, shared to save resources.
    private static ImageRegistry imageRegistry = new ImageRegistry();

    static
    {
        imageRegistry.put(INITIAL_IMG, ImageDescriptor.createFromFile(EditorPlugin.class,
                "icons/state_ini_16.png")); //$NON-NLS-1$
        imageRegistry.put(NORMAL_IMG, ImageDescriptor.createFromFile(EditorPlugin.class,
                "icons/state_nor_16.png")); //$NON-NLS-1$
        imageRegistry.put(FINAL_IMG, ImageDescriptor.createFromFile(EditorPlugin.class,
                "icons/state_fin_16.png")); //$NON-NLS-1$
    }

    /*
     * Returns a casted model.
     */
    private StateImpl getCastedModel()
    {
        return (StateImpl) getModel();
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
     * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
     */
    protected Image getImage()
    {
        Image img;
        State s = getCastedModel();
        if (s.isInitialState())
        {
            img = imageRegistry.get(INITIAL_IMG);
        }
        else if (s.isFinalState())
        {
            img = imageRegistry.get(FINAL_IMG);
        }
        else
        {
            img = imageRegistry.get(NORMAL_IMG);
        }
        return img;
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
     * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
     */
    protected List getModelChildren()
    {
        return Collections.list(Collections.enumeration(getCastedModel().getOutgoingOperations()));
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
            return new StatePropertySource((BusinessProtocolImpl) getParent().getModel(),
                    getCastedModel());
        }
        return super.getAdapter(key);
    }

}
