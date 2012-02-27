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
