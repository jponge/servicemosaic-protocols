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
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.gefeditor.commands.OperationDeleteCommand;

/**
 * The connection edit policy for operations.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationConnectionEditPolicy extends ConnectionEditPolicy
{
    /**
     * The protocol whose the connection belongs to.
     */
    protected BusinessProtocol protocol;

    /**
     * The operation to work on.
     */
    protected Operation operation;

    /**
     * Constructs a new connection edit policy for operations.
     * 
     * @param protocol
     *            The protocol.
     * @param operation
     *            The operation.
     */
    public OperationConnectionEditPolicy(BusinessProtocol protocol, Operation operation)
    {
        super();
        this.protocol = protocol;
        this.operation = operation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConnectionEditPolicy#getDeleteCommand(org.eclipse.gef.requests.GroupRequest)
     */
    protected Command getDeleteCommand(GroupRequest request)
    {
        return new OperationDeleteCommand(protocol, operation);
    }

}
