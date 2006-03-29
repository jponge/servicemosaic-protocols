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

import org.eclipse.gef.commands.Command;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
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
        Message m = factory.createMessage("m" + counter++, polarity); //$NON-NLS-1$
        operation = factory.createOperation(source, target, m);
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
