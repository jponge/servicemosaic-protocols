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

package fr.isima.ponge.wsprotocol.gefeditor.uiparts;

import org.eclipse.gef.requests.CreationFactory;

import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;

/**
 * The factory to create new states from the user interaction.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateCreationFactory implements CreationFactory
{

    // Internal counter to give the states different names.
    private static int counter = 0;

    // Is the state final ?
    private boolean isFinal;

    // Is the state initial ?
    private boolean isInitial;

    // The factory used to instanciate the states.
    private static final BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl();

    /**
     * Creates a new states creation factory.
     * 
     * @param initial
     *            Wether the state should be initial or not.
     * @param final1
     *            Wether the state should be final or not.
     */
    public StateCreationFactory(boolean initial, boolean final1)
    {
        super();
        isInitial = initial;
        isFinal = final1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
     */
    public Object getNewObject()
    {
        State s = factory.createState("s" + counter++, isFinal); //$NON-NLS-1$
        s.setInitialState(isInitial);
        return s;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
     */
    public Object getObjectType()
    {
        return State.class;
    }

}
