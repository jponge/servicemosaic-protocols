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

import java.util.Iterator;

import org.eclipse.gef.commands.Command;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.impl.OperationImpl;

/**
 * The operation to reconnect an operation to another state.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationReconnectCommand extends Command
{

    /*
     * Indicates wether the target state is the source state of the operation or not.
     */
    private boolean source;

    // The protocol
    private BusinessProtocol protocol;

    // The old operation
    private Operation oldOperation;

    // The new (reconnected) operation
    private Operation newOperation;

    // The state which gets the new connection
    private State state;

    /**
     * Constructs a new reconnection command.
     * 
     * @param protocol
     *            The protocol which the operation belongs to.
     * @param operation
     *            The operation to reconnect.
     * @param state
     *            The state which receives the reconnection.
     * @param source
     *            Indicates wether the state is the source of the operation or not.
     */
    public OperationReconnectCommand(BusinessProtocol protocol, Operation operation, State state,
            boolean source)
    {
        super();
        this.protocol = protocol;
        oldOperation = operation;
        this.state = state;
        this.source = source;

        setLabel(Messages.reconnect);
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
        // Make the new operation
        Message m = oldOperation.getMessage();
        State src, target;
        if (source)
        {
            src = state;
            target = oldOperation.getTargetState();
        }
        else
        {
            src = oldOperation.getSourceState();
            target = state;
        }
        newOperation = new OperationImpl(src, target, m);
        Iterator keysIterator = oldOperation.getExtraPropertiesKeys().iterator();
        while (keysIterator.hasNext())
        {
            Object key = keysIterator.next();
            newOperation.putExtraProperty(key, oldOperation.getExtraProperty(key));
        }

        redo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo()
    {
        protocol.removeOperation(oldOperation);
        protocol.addOperation(newOperation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo()
    {
        protocol.removeOperation(newOperation);
        protocol.addOperation(oldOperation);
    }

}
