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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.commands.OperationCreateCommand;
import fr.isima.ponge.wsprotocol.gefeditor.commands.OperationReconnectCommand;

/**
 * The graphical node edit policy for states.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
     */
    protected Command getConnectionCompleteCommand(CreateConnectionRequest request)
    {
        OperationCreateCommand command = (OperationCreateCommand) request.getStartCommand();
        command.setTarget((State) getHost().getModel());
        return command;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
     */
    protected Command getConnectionCreateCommand(CreateConnectionRequest request)
    {
        BusinessProtocol protocol = (BusinessProtocol) getHost().getParent().getModel();
        State source = (State) getHost().getModel();
        Polarity polarity = (Polarity) request.getNewObjectType();
        OperationCreateCommand command = new OperationCreateCommand(protocol, source, polarity);
        request.setStartCommand(command);
        return command;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
     */
    protected Command getReconnectSourceCommand(ReconnectRequest request)
    {
        BusinessProtocol protocol = (BusinessProtocol) getHost().getParent().getModel();
        return new OperationReconnectCommand(protocol, (Operation) request.getConnectionEditPart()
                .getModel(), (State) request.getTarget().getModel(), true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
     */
    protected Command getReconnectTargetCommand(ReconnectRequest request)
    {
        BusinessProtocol protocol = (BusinessProtocol) getHost().getParent().getModel();
        return new OperationReconnectCommand(protocol, (Operation) request.getConnectionEditPart()
                .getModel(), (State) request.getTarget().getModel(), false);
    }

}
