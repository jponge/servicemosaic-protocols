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

package fr.isima.ponge.wsprotocol.operators

import junit.framework.TestCase

import fr.isima.ponge.wsprotocol.*

class CompositionOperatorTest extends TestCase
{
    void testComposition()
    {
        def base = "tests/fr/isima/ponge/wsprotocol/operators/"
        BusinessProtocol p1 = TestingUtils.loadProtocol(base + "composition/p1.wsprotocol")
        BusinessProtocol p2 = TestingUtils.loadProtocol(base + "composition/p2.wsprotocol")
        BusinessProtocol p3 = TestingUtils.loadProtocol(base + "composition/p1-comp-p2.wsprotocol")

        def CompositionOperator operator = new CompositionOperator()
        def result = operator.apply(p1, p2)

        assertEquals result, p3

        def t1_t1 = result.operations.find {it.name == "T1_T1"}

        assertNotSame t1_t1, null
        assertEquals "C-Invoke((T0_T0 < 20) && (T0_T0 < 10))", t1_t1.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)
    }
}