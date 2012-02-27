/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Université Blaise Pascal, LIMOS, Clermont-Ferrand, France.
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


package fr.isima.ponge.wsprotocol.impl;

import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;
import junit.framework.TestCase;

/**
 * Test case for the <code>OperationImpl</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationImplTest extends TestCase
{

    public void testEqualsObject()
    {
        State s1 = new StateImpl("s1", false); //$NON-NLS-1$
        State s2 = new StateImpl("s2", false); //$NON-NLS-1$
        State s3 = new StateImpl("s3", false); //$NON-NLS-1$
        State s4 = new StateImpl("s4", false); //$NON-NLS-1$
        Message m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        Message m2 = new MessageImpl("a", Polarity.NEGATIVE); //$NON-NLS-1$

        OperationImpl o1 = new OperationImpl("T1", s1, s2, m1);
        OperationImpl o2 = new OperationImpl("T2", s3, s4, m2);

        TestCase.assertEquals(o1, o1);
        TestCase.assertNotSame(o1, o2);
    }

    public void testToString()
    {
        State s1 = new StateImpl("s1", false); //$NON-NLS-1$
        State s2 = new StateImpl("s2", false); //$NON-NLS-1$
        Message m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        OperationImpl o1 = new OperationImpl("T1", s1, s2, m1);

        TestCase.assertEquals("T1: ((s1),[a](+),(s2),explicit)", o1.toString()); //$NON-NLS-1$
    }

}
