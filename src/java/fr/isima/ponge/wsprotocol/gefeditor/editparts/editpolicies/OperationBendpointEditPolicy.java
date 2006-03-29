/* 
 * CDDL HEADER START 
 * 
 * The contents of this file are subject to the terms of the 
 * Common Development and Distribution License (the "License"). 
 * You may not use this file except in compliance with the License. 
 * 
 * You can obtain a copy of the license at LICENSE.txt 
 * or at http://www.opensource.org/licenses/cddl1.php. 
 * See the License for the specific language governing permissions 
 * and limitations under the License. 
 * 
 * When distributing Covered Code, include this CDDL HEADER in each 
 * file and include the License file at LICENSE.txt. 
 * If applicable, add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your own identifying 
 * information: Portions Copyright [yyyy] [name of copyright owner] 
 * 
 * CDDL HEADER END 
 */ 

/* 
 * Copyright 2005, 2006 Julien Ponge. All rights reserved. 
 * Use is subject to license terms. 
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
