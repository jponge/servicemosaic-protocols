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
