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
