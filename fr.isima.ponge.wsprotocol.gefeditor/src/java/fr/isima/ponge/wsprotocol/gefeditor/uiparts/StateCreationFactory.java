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
