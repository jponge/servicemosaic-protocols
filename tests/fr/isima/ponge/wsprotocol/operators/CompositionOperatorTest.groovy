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