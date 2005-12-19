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
