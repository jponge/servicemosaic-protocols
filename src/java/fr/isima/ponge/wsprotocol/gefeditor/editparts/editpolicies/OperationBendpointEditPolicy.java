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

package fr.isima.ponge.wsprotocol.gefeditor.editparts.editpolicies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.gefeditor.commands.OperationCreateBendpointCommand;
import fr.isima.ponge.wsprotocol.gefeditor.commands.OperationDeleteBendpointCommand;
import fr.isima.ponge.wsprotocol.gefeditor.commands.OperationMoveBendpointCommand;
import fr.isima.ponge.wsprotocol.gefeditor.figures.OperationFigure;

/**
 * The edit policy for operation bendpoints.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationBendpointEditPolicy extends BendpointEditPolicy
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getCreateBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
     */
    protected Command getCreateBendpointCommand(BendpointRequest request)
    {
        int index = request.getIndex();
        Operation op = (Operation) request.getSource().getModel();
        OperationFigure figure = (OperationFigure) request.getSource().getFigure();
        Point location = request.getLocation();
        Point first = figure.getSourceAnchor().getReferencePoint();
        Point last = figure.getTargetAnchor().getReferencePoint();
        figure.translateToRelative(first);
        figure.translateToRelative(last);
        figure.translateToRelative(location);
        return new OperationCreateBendpointCommand(op, index, location.getDifference(first),
                location.getDifference(last));

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getDeleteBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
     */
    protected Command getDeleteBendpointCommand(BendpointRequest request)
    {
        int index = request.getIndex();
        Operation op = (Operation) request.getSource().getModel();
        return new OperationDeleteBendpointCommand(op, index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getMoveBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
     */
    protected Command getMoveBendpointCommand(BendpointRequest request)
    {
        int index = request.getIndex();
        Operation op = (Operation) request.getSource().getModel();
        OperationFigure figure = (OperationFigure) request.getSource().getFigure();
        Point location = request.getLocation();
        Point first = figure.getSourceAnchor().getReferencePoint();
        Point last = figure.getTargetAnchor().getReferencePoint();
        figure.translateToRelative(first);
        figure.translateToRelative(last);
        figure.translateToRelative(location);
        return new OperationMoveBendpointCommand(op, index, location.getDifference(first), location
                .getDifference(last));
    }

}
