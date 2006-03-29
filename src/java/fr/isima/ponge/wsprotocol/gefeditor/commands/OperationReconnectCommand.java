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
