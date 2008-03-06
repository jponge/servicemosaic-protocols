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
}