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
