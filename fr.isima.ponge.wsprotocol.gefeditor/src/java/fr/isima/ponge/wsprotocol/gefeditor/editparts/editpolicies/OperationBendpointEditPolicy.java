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
