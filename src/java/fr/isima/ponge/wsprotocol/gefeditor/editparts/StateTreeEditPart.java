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
