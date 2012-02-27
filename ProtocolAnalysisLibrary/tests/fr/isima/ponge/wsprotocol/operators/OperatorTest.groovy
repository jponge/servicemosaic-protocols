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
import fr.isima.ponge.wsprotocol.impl.*

private class MockOperator extends Operator {}

class OperatorTest extends TestCase
{
    void testVariablesRewriting()
    {
        BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl()
        def c = "C-Invoke((T1 >= 3) && (T3 < 4))"

        State s = factory.createState("s", false)
        Message m = factory.createMessage("m", Polarity.POSITIVE)
        Operation op = factory.createOperation("op", s, s, m)
        op.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, c)

        Operator operator = new MockOperator()
        operator.rewriteConstraintVariables(op) { name -> "_${name}" }

        assertEquals "C-Invoke((_T1 >= 3) && (_T3 < 4))", op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT) 
    }

    void testListVariablesInOperationConstraint()
    {
        BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl()
        Operation fakeOperation = factory.createOperation("FAKE", null, null, null)
        fakeOperation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, "C-Invoke( (T0 - T2 < 10) || ((T1 < 3) && (T2 < 4)) )")

        Operator operator = new MockOperator()
        def vars = operator.listVariablesInOperationConstraint(fakeOperation)
        assertEquals (["T0", "T1", "T2"], vars.sort())
    }

    void testClone()
    {
        def base = "tests/fr/isima/ponge/wsprotocol/operators/"
        BusinessProtocol p1 = TestingUtils.loadProtocol(base + "intersection/p1.wsprotocol")

        Operator operator = new MockOperator()
        def clone = operator.cloneProtocol(p1)

        assertEquals p1.states.size(), clone.states.size()
        assertEquals p1.operations.size(), clone.operations.size()
        assertEquals p1, clone
    }

    void testConstraintNegation()
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

        Operator operator = new MockOperator()

        assertEquals "", operator.negateConstraint(op0)
        assertEquals "C-Invoke(T1 >= 2)", operator.negateConstraint(op1)
        assertEquals "C-Invoke((T1 < 3) || (T3 >= 4))", operator.negateConstraint(op2)
    }
}