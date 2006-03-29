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
