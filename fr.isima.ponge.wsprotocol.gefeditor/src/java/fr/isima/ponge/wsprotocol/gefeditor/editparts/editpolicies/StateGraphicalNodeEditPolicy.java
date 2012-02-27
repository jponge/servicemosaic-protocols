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
