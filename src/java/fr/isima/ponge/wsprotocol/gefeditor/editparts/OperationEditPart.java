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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.editpolicies.OperationBendpointEditPolicy;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.editpolicies.OperationConnectionEditPolicy;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.propertysources.OperationPropertySource;
import fr.isima.ponge.wsprotocol.gefeditor.figures.OperationFigure;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;
import fr.isima.ponge.wsprotocol.impl.MessageImpl;
import fr.isima.ponge.wsprotocol.impl.OperationImpl;

/**
 * The <code>Operation</code> edit part.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationEditPart extends AbstractConnectionEditPart implements PropertyChangeListener
{

    /**
     * The figure.
     */
    protected OperationFigure figure;

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
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
     */
    protected IFigure createFigure()
    {
        figure = new OperationFigure(getPolarityCode(), getMessageLabel());

        // Handle loops
        if (getCastedModel().getSourceState() == getCastedModel().getTargetState()
                && getCastedModel().getExtraProperty(
                        ModelExtraPropertiesConstants.OPERATION_BENDPOINTS) == null)
        {
            List l = new ArrayList();
            l.add(new Dimension(-20, -70));
            l.add(new Dimension(-30, -80));
            l.add(new Dimension(20, -70));
            l.add(new Dimension(30, -80));
            getCastedModel().putExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS,
                    encodeDimensionList(l));
        }

        return figure;
    }

    /*
     * Makes a representation of the operation message.
     */
    private String getMessageLabel()
    {
        StringBuffer buffer = new StringBuffer();
        Message m = getCastedModel().getMessage();
        if (m.getPolarity() == Polarity.POSITIVE)
        {
            buffer.append(">"); //$NON-NLS-1$
        }
        buffer.append("[").append(m.getName()).append("]"); //$NON-NLS-1$ //$NON-NLS-2$
        if (m.getPolarity() == Polarity.NEGATIVE)
        {
            buffer.append(">"); //$NON-NLS-1$
        }
        return buffer.toString();
    }

    /*
     * Matches the figure polarity code with the one from the model.
     */
    private int getPolarityCode()
    {
        Polarity pol = getCastedModel().getMessage().getPolarity();
        int code;
        if (pol.equals(Polarity.POSITIVE))
        {
            code = OperationFigure.INPUT;
        }
        else if (pol.equals(Polarity.NEGATIVE))
        {
            code = OperationFigure.OUTPUT;
        }
        else if (getCastedModel().getOperationKind().equals(OperationKind.IMPLICIT))
        {
            code = OperationFigure.IMPLICIT;
        }
        else
        {
            code = OperationFigure.UNPOLARIZED;
        }

        return code;
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
     * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
     */
    protected void refreshVisuals()
    {
        super.refreshVisuals();

        figure.setType(getPolarityCode());
        figure.setText(getMessageLabel());
        refreshBendpoints();
        figure.repaint();
    }

    /*
     * Updates the bendpoints on the figure from the extra properties stored in the model.
     */
    private void refreshBendpoints()
    {
        String encoded = (String) getCastedModel().getExtraProperty(
                ModelExtraPropertiesConstants.OPERATION_BENDPOINTS);
        List dims;
        if (encoded == null)
        {
            return;
        }
        dims = OperationEditPart.decodeDimensionList(encoded);
        List constraint = new ArrayList();
        Iterator it = dims.iterator();
        while (it.hasNext())
        {
            Dimension d1 = (Dimension) it.next();
            Dimension d2 = (Dimension) it.next();
            RelativeBendpoint bp = new RelativeBendpoint(figure);
            bp.setRelativeDimensions(d1, d2);
            constraint.add(bp);
        }
        figure.setRoutingConstraint(constraint);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
     */
    protected void createEditPolicies()
    {
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
        EditPart toParent = (getSource() != null) ? getSource() : getTarget();
        installEditPolicy(EditPolicy.CONNECTION_ROLE, new OperationConnectionEditPolicy(
                (BusinessProtocol) toParent.getParent().getModel(), getCastedModel()));
        installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new OperationBendpointEditPolicy());

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        refreshVisuals();
    }

    /**
     * Encodes a list of dimensions as a string of the form <code><pre>
     *         (1, 2), (3, 4) -&gt; 1:2:3:4
     * </code></pre>
     * 
     * @param dims
     *            The dimensions list.
     * @return The string representation.
     * @see #decodeDimensionList(String)
     */
    public static String encodeDimensionList(List dims)
    {
        StringBuffer buffer = new StringBuffer();
        Iterator it = dims.iterator();
        while (it.hasNext())
        {
            Dimension d = (Dimension) it.next();
            buffer.append(Integer.toString(d.width)).append(":").append(d.height); //$NON-NLS-1$
            if (it.hasNext())
            {
                buffer.append(":"); //$NON-NLS-1$
            }
        }
        return buffer.toString();
    }

    /**
     * Decodes a list of dimensions encoded in a string.
     * 
     * @param dims
     *            The string representing the dimensions.
     * @return The dimensions as a string.
     * @see #encodeDimensionList(List)
     */
    public static List decodeDimensionList(String dims)
    {
        List list = new ArrayList();
        String[] tokens = dims.split(":"); //$NON-NLS-1$
        for (int i = 0; i + 1 < tokens.length; i = i + 2)
        {
            int width = Integer.parseInt(tokens[i]);
            int height = Integer.parseInt(tokens[i + 1]);
            list.add(new Dimension(width, height));
        }
        return list;
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
            EditPart toParent = (getSource() != null) ? getSource() : getTarget();
            BusinessProtocol p = (BusinessProtocol) toParent.getParent().getModel();
            String wsdl = (String) p.getExtraProperty(StandardExtraProperties.PROTOCOL_WSDL_URL);
            IFile file = (IFile) p
                    .getExtraProperty(ModelExtraPropertiesConstants.PROTOCOL_IFILE_RESOURCE);
            return new OperationPropertySource(getCastedModel(), wsdl, file);
        }
        return super.getAdapter(key);
    }
}
