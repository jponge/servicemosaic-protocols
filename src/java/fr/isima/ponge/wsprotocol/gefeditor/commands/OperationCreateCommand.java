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

import org.eclipse.gef.commands.Command;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;

public class OperationCreateCommand extends Command
{

    private static int counter = 0;

    private BusinessProtocol protocol;

    private Operation operation;

    private Polarity polarity;

    private State source;

    private State target;

    public OperationCreateCommand(BusinessProtocol protocol, State source, Polarity polarity)
    {
        super();
        this.protocol = protocol;
        this.source = source;
        this.polarity = polarity;

        setLabel(Messages.createOperation);
    }

    public void setTarget(State target)
    {
        this.target = target;
    }

    public boolean canExecute()
    {
        // Allow any kind of connection, including self-connection
        return true;
    }

    public boolean canUndo()
    {
        return true;
    }

    public void execute()
    {
        BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl();
        Message m = factory.createMessage("m" + counter, polarity); //$NON-NLS-1$
        OperationKind kind = (polarity.equals(Polarity.NULL)) ? OperationKind.IMPLICIT : OperationKind.EXPLICIT;
        operation = factory.createOperation("T" + counter, source, target, m, kind);
        counter = counter + 1;
        redo();
    }

    public void redo()
    {
        protocol.addOperation(operation);
    }

    public void undo()
    {
        protocol.removeOperation(operation);
    }

}
