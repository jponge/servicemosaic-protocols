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

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.gefeditor.EditorPlugin;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.propertysources.OperationPropertySource;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;
import fr.isima.ponge.wsprotocol.impl.MessageImpl;
import fr.isima.ponge.wsprotocol.impl.OperationImpl;

/**
 * The edit part for <code>Operation</code> to be used in outline views.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener
{

    // Image code for outgoing operations
    private static final String MINUS_IMG = "minus"; //$NON-NLS-1$

    // Image code for incoming operations
    private static final String PLUS_IMG = "plus"; //$NON-NLS-1$

    // Image code for null-polarized operations
    private static final String NONE_IMG = "none"; //$NON-NLS-1$
    
    // Image code for implicit operations
    private static final String IMPLICIT_IMG = "implicit"; //$NON-NLS-1$

    // The image registry, saves some resources
    private static ImageRegistry imageRegistry = new ImageRegistry();

    static
    {
        imageRegistry.put(MINUS_IMG, ImageDescriptor.createFromFile(EditorPlugin.class,
                "icons/operation_minus_16.png")); //$NON-NLS-1$
        imageRegistry.put(PLUS_IMG, ImageDescriptor.createFromFile(EditorPlugin.class,
                "icons/operation_plus_16.png")); //$NON-NLS-1$
        imageRegistry.put(NONE_IMG, ImageDescriptor.createFromFile(EditorPlugin.class,
                "icons/operation_none_16.png")); //$NON-NLS-1$
        imageRegistry.put(IMPLICIT_IMG, ImageDescriptor.createFromFile(EditorPlugin.class,
            "icons/operation_implicit_16.png")); //$NON-NLS-1$
    }

    /*
     * Returns a casted model.
     */
    private OperationImpl getCastedModel()
    {
        return (OperationImpl) getModel();
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
            ((MessageImpl) getCastedModel().getMessage()).addPropertyChangeListener(this);
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
            ((MessageImpl) getCastedModel().getMessage()).removePropertyChangeListener(this);
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
        Polarity p = getCastedModel().getMessage().getPolarity();
        if (p.equals(Polarity.POSITIVE))
        {
            img = imageRegistry.get(PLUS_IMG);
        }
        else if (p.equals(Polarity.NEGATIVE))
        {
            img = imageRegistry.get(MINUS_IMG);
        }
        else if (getCastedModel().getOperationKind().equals(OperationKind.IMPLICIT))
        {
            img = imageRegistry.get(IMPLICIT_IMG);
        }
        else
        {
            img = imageRegistry.get(NONE_IMG);
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
        return getCastedModel().getMessage().getName();
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
            EditPart toParent = getParent();
            BusinessProtocol p = (BusinessProtocol) toParent.getParent().getModel();
            String wsdl = (String) p.getExtraProperty(StandardExtraProperties.PROTOCOL_WSDL_URL);
            IFile file = (IFile) p.getExtraProperty(ModelExtraPropertiesConstants.PROTOCOL_IFILE_RESOURCE);
            return new OperationPropertySource(getCastedModel(), wsdl, file);
        }
        return super.getAdapter(key);
    }

}
