/*
 * Copyright (c) 2005 Julien Ponge - All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
