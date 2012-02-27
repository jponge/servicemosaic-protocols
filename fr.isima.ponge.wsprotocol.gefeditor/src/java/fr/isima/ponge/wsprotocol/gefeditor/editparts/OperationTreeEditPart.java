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
        StringBuffer buffer = new StringBuffer();
        buffer.append(getCastedModel().getName()).append(": ").append(getCastedModel().getMessage().getName());
        return buffer.toString();
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
