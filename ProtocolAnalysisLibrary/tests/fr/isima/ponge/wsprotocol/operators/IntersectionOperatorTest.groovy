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

import fr.isima.ponge.wsprotocol.BusinessProtocol
import junit.framework.TestCase
import fr.isima.ponge.wsprotocol.Operation
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl
import fr.isima.ponge.wsprotocol.Message
import fr.isima.ponge.wsprotocol.State
import fr.isima.ponge.wsprotocol.Polarity
import fr.isima.ponge.wsprotocol.StandardExtraProperties

class IntersectionOperatorTest extends TestCase
{
    void testIntersection()
    {
        def base = "tests/fr/isima/ponge/wsprotocol/operators/"
        BusinessProtocol p1 = TestingUtils.loadProtocol(base + "intersection/p1.wsprotocol")
        BusinessProtocol p2 = TestingUtils.loadProtocol(base + "intersection/p2.wsprotocol")
        BusinessProtocol p3 = TestingUtils.loadProtocol(base + "intersection/p1-inter-p2.wsprotocol")

        def IntersectionOperator operator = new IntersectionOperator()
        def result = operator.apply(p1, p2)

        assertEquals result, p3

        def t1_t1 = result.operations.find {it.name == "T1_T1"}

        assertNotSame t1_t1,  null
        assertEquals "C-Invoke(T0_T0 < 10)", t1_t1.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)

        p2.states.find { it.name == "s1" }.finalState = true
        result = operator.apply(p1, p2)

        assertFalse result.states.find { it.name == "(s1,s1)" }.finalState
        assertTrue result.states.find { it.name == "(s2,s2)" }.finalState 
    }

    void testConstraintConjunction()
    {
        BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl()

        def c1 = "C-Invoke(T1 < 2)"
        def c2 = "C-Invoke((T1 >= 3) && (T3 < 4))"

        State s = factory.createState("s", false)
        Message m = factory.createMessage("m", Polarity.POSITIVE)
        Operation op0 = factory.createOperation("op0", s, s, m)
        Operation op1 = factory.createOperation("op1", s, s, m)
        Operation op2 = factory.createOperation("op2", s, s, m)

        op1.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, c1)
        op2.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, c2)

        def IntersectionOperator operator = new IntersectionOperator()

        assertEquals "", operator.constraintConjunction(op0, op0)
        assertEquals c1, operator.constraintConjunction(op0, op1)
        assertEquals c2, operator.constraintConjunction(op2, op0)

        assertEquals "C-Invoke((T1 < 2) && ((T1 >= 3) && (T3 < 4)))", operator.constraintConjunction(op1, op2)
    }
}