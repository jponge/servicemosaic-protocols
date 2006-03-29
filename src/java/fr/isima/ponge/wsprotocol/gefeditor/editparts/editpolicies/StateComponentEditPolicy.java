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
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.commands.StateDeleteCommand;

/**
 * The component edit policy for states.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateComponentEditPolicy extends ComponentEditPolicy
{
    /**
     * The protocol whose the state belongs to.
     */
    protected BusinessProtocol protocol;

    /**
     * The state to work on.
     */
    protected State state;

    /**
     * Constructs a new component edit policy for states.
     * 
     * @param protocol
     *            The protocol.
     * @param state
     *            The state.
     */
    public StateComponentEditPolicy(BusinessProtocol protocol, State state)
    {
        super();
        this.protocol = protocol;
        this.state = state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
     */
    protected Command createDeleteCommand(GroupRequest deleteRequest)
    {
        return new StateDeleteCommand(protocol, state);
    }
}
