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

import fr.isima.ponge.wsprotocol.Polarity;
import junit.framework.TestCase;

/**
 * Test case for the <code>MessageImpl</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class MessageImplTest extends TestCase
{

    public void testEqualsObject()
    {
        MessageImpl m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        MessageImpl m2 = new MessageImpl("a", Polarity.NEGATIVE); //$NON-NLS-1$
        MessageImpl m3 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        MessageImpl m4 = new MessageImpl("b", Polarity.POSITIVE); //$NON-NLS-1$

        TestCase.assertEquals(m1, m3);
        TestCase.assertNotSame(m1, m2);
        TestCase.assertNotSame(m1, m4);
    }

    public void testToString()
    {
        MessageImpl m = new MessageImpl("message", Polarity.POSITIVE); //$NON-NLS-1$
        TestCase.assertEquals("[message](+)", m.toString()); //$NON-NLS-1$
    }

}
