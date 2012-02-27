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
import java.util.List;
import java.util.Random;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.editpolicies.StateComponentEditPolicy;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.editpolicies.StateGraphicalNodeEditPolicy;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.propertysources.StatePropertySource;
import fr.isima.ponge.wsprotocol.gefeditor.figures.StateFigure;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;
import fr.isima.ponge.wsprotocol.impl.StateImpl;

/**
 * The edit part for <code>State</code> instances.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateEditPart extends AbstractGraphicalEditPart implements NodeEditPart,
        PropertyChangeListener
{

    /**
     * The figure.
     */
    protected StateFigure figure;

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
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
     */
    protected IFigure createFigure()
    {
        StateImpl state = getCastedModel();
        figure = new StateFigure(state.getName(), state.isInitialState(), state.isFinalState());
        return figure;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
     */
    protected void createEditPolicies()
    {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateComponentEditPolicy(
                (BusinessProtocol) getParent().getModel(), getCastedModel()));
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new StateGraphicalNodeEditPolicy());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection)
    {
        return figure.getAnchor();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection)
    {
        return figure.getAnchor();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
     */
    public ConnectionAnchor getSourceConnectionAnchor(Request request)
    {
        return figure.getAnchor();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
     */
    public ConnectionAnchor getTargetConnectionAnchor(Request request)
    {
        return figure.getAnchor();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
     */
    protected List getModelSourceConnections()
    {
        return getCastedModel().getOutgoingOperations();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
     */
    protected List getModelTargetConnections()
    {
        return getCastedModel().getIncomingOperations();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        String event = evt.getPropertyName();
        if (event.equals(StateImpl.IN_OPERATION_PROPERTY_CHANGE)
                || event.equals(StateImpl.OUT_OPERATION_PROPERTY_CHANGE))
        {
            refresh();
        }
        else
        {
            refreshVisuals();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
     */
    protected void refreshVisuals()
    {
        super.refreshVisuals();

        // Update the location in the parent container
        StateImpl state = getCastedModel();
        String x = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_X_PROP);
        String y = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_Y_PROP);
        String w = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_WIDTH_PROP);
        String h = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_HEIGHT_PROP);
        Rectangle bounds;
        if (x != null || y != null || w != null || h != null)
        {
            bounds = new Rectangle(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(w),
                    Integer.parseInt(h));
        }
        else
        {
            Random rand = new Random();
            bounds = new Rectangle(rand.nextInt(800), rand.nextInt(600), 100, 60);
            state.putExtraProperty(ModelExtraPropertiesConstants.STATE_X_PROP, Integer
                    .toString(bounds.x));
            state.putExtraProperty(ModelExtraPropertiesConstants.STATE_Y_PROP, Integer
                    .toString(bounds.y));
            state.putExtraProperty(ModelExtraPropertiesConstants.STATE_WIDTH_PROP, Integer
                    .toString(bounds.width));
            state.putExtraProperty(ModelExtraPropertiesConstants.STATE_HEIGHT_PROP, Integer
                    .toString(bounds.height));
        }
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);

        // Update the displayed informations
        figure.setText(getCastedModel().getName());
        figure.setInitialState(getCastedModel().isInitialState());
        figure.setFinalState(getCastedModel().isFinalState());
        figure.repaint();
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
