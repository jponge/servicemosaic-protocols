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


package fr.isima.ponge.wsprotocol;

import junit.framework.TestCase;

/**
 * Test case for the <code>Polarity</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class PolarityTest extends TestCase
{

    public void testEqualsObject()
    {
        TestCase.assertEquals(Polarity.POSITIVE, Polarity.POSITIVE);
        TestCase.assertEquals(Polarity.NEGATIVE, Polarity.NEGATIVE);
        TestCase.assertEquals(Polarity.NULL, Polarity.NULL);

        TestCase.assertNotSame(Polarity.POSITIVE, Polarity.NEGATIVE);
        TestCase.assertNotSame(Polarity.POSITIVE, Polarity.NULL);
        TestCase.assertNotSame(Polarity.NEGATIVE, Polarity.NULL);
    }

    public void testToString()
    {
        TestCase.assertEquals("(+)", Polarity.POSITIVE.toString()); //$NON-NLS-1$
        TestCase.assertEquals("(-)", Polarity.NEGATIVE.toString()); //$NON-NLS-1$
        TestCase.assertEquals("( )", Polarity.NULL.toString()); //$NON-NLS-1$
    }

}
