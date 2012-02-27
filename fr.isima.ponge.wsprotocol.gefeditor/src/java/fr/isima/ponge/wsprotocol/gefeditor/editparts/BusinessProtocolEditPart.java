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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.ui.views.properties.IPropertySource;

import fr.isima.ponge.wsprotocol.gefeditor.editparts.editpolicies.BusinessProtocolXYLayoutEditPolicy;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.propertysources.BusinessProtocolPropertySource;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;

/**
 * The graphical edit part for a <code>BusinessProtocol</code>.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolEditPart extends AbstractGraphicalEditPart implements
        PropertyChangeListener
{
    /*
     * Returns a casted model
     */
    private BusinessProtocolImpl getCastedModel()
    {
        return (BusinessProtocolImpl) getModel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
     */
    protected List getModelChildren()
    {
        List list = Collections.list(Collections.enumeration(getCastedModel().getStates()));
        return list;
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
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
     */
    protected IFigure createFigure()
    {
        FreeformLayer layer = new FreeformLayer();
        layer.setBorder(new MarginBorder(10));
        layer.setLayoutManager(new FreeformLayout());

        ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
        FanRouter router = new FanRouter();
        router.setSeparation(40);
        connLayer.setConnectionRouter(router);
        BendpointConnectionRouter brouter = new BendpointConnectionRouter();
        router.setNextRouter(brouter);
        connLayer.setConnectionRouter(router);

        return layer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
     */
    protected void createEditPolicies()
    {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new BusinessProtocolXYLayoutEditPolicy());
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
        else if (key == SnapToHelper.class)
        {
            List snapStrategies = new ArrayList();
            Boolean val = (Boolean) getViewer()
                    .getProperty(RulerProvider.PROPERTY_RULER_VISIBILITY);
            if (val != null && val.booleanValue())
                snapStrategies.add(new SnapToGuides(this));
            val = (Boolean) getViewer().getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED);
            if (val != null && val.booleanValue())
                snapStrategies.add(new SnapToGeometry(this));
            val = (Boolean) getViewer().getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
            if (val != null && val.booleanValue())
                snapStrategies.add(new SnapToGrid(this));

            if (snapStrategies.size() == 0)
                return null;
            if (snapStrategies.size() == 1)
                return (SnapToHelper) snapStrategies.get(0);

            SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
            for (int i = 0; i < snapStrategies.size(); i++)
                ss[i] = (SnapToHelper) snapStrategies.get(i);
            return new CompoundSnapToHelper(ss);
        }
        return super.getAdapter(key);
    }

}
