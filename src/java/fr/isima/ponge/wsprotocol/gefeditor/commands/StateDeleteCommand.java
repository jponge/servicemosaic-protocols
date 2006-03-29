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

package fr.isima.ponge.wsprotocol.gefeditor.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;

/**
 * The command to delete a state.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateDeleteCommand extends Command
{

    // The protocol
    private BusinessProtocol protocol;

    // The state
    private State state;

    // The incoming operations of the state
    private ArrayList incomingOperations;

    // The outgoing operations of the state
    private ArrayList outgoingOperations;

    /**
     * Constructs a new command to delete a state.
     * 
     * @param protocol
     *            The protocol which the state belongs to.
     * @param state
     *            The state to delete.
     */
    public StateDeleteCommand(BusinessProtocol protocol, State state)
    {
        super();
        this.protocol = protocol;
        this.state = state;

        // Work on copies to avoid conccurency exceptions
        incomingOperations = new ArrayList(state.getIncomingOperations());
        outgoingOperations = new ArrayList(state.getOutgoingOperations());

        setLabel(Messages.deleteState);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#canExecute()
     */
    public boolean canExecute()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#canUndo()
     */
    public boolean canUndo()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#execute()
     */
    public void execute()
    {
        redo();
    }

    /*
     * Injects back the operation when undoing the deletion.
     */
    private void injectOperations(List operations)
    {
        Iterator it = operations.iterator();
        while (it.hasNext())
        {
            Operation op = (Operation) it.next();
            protocol.addOperation(op);
        }
    }

    /*
     * Removes the operations from the state before deleting it.
     */
    private void removeOperations(List operations)
    {
        Iterator it = operations.iterator();
        while (it.hasNext())
        {
            Operation op = (Operation) it.next();
            protocol.removeOperation(op);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo()
    {
        removeOperations(incomingOperations);
        removeOperations(outgoingOperations);
        protocol.removeState(state);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo()
    {
        injectOperations(incomingOperations);
        injectOperations(outgoingOperations);
        protocol.addState(state);
    }

}
