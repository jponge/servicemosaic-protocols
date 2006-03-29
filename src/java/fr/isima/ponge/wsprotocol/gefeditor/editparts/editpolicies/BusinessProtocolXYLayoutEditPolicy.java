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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.commands.StateCreateCommand;
import fr.isima.ponge.wsprotocol.gefeditor.commands.StateSetConstraintCommand;

/**
 * The layout edit policy for business protocols.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolXYLayoutEditPolicy extends XYLayoutEditPolicy
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart,
     *      java.lang.Object)
     */
    protected Command createAddCommand(EditPart child, Object constraint)
    {
        // Not used
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest,
     *      org.eclipse.gef.EditPart, java.lang.Object)
     */
    protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child,
            Object constraint)
    {
        return new StateSetConstraintCommand((State) child.getModel(), request,
                (Rectangle) constraint);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart,
     *      java.lang.Object)
     */
    protected Command createChangeConstraintCommand(EditPart child, Object constraint)
    {
        // Not used
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
     */
    protected Command getCreateCommand(CreateRequest request)
    {
        Object clazz = request.getNewObjectType();
        if (clazz == State.class)
        {
            Point location = request.getLocation();
            Dimension size = request.getSize();
            Rectangle bounds = (size == null) ? new Rectangle(location, new Dimension(100, 60))
                    : new Rectangle(location, size);
            return new StateCreateCommand((State) request.getNewObject(),
                    (BusinessProtocol) getHost().getModel(), bounds);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
     */
    protected Command getDeleteDependantCommand(Request request)
    {
        // Not used
        return null;
    }

}
