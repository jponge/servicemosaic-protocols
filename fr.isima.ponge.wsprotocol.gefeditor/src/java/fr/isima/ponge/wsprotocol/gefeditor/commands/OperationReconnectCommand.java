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
        String name = oldOperation.getName();
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
        newOperation = new OperationImpl(name, src, target, m);
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
