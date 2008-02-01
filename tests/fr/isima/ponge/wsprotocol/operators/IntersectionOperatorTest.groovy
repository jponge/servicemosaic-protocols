package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.BusinessProtocol
import junit.framework.TestCase

class IntersectionOperatorTest extends TestCase
{
    BusinessProtocol p1
    BusinessProtocol p2
    BusinessProtocol p3

    @Override
    void setUp()
    {
        def base = "tests/fr/isima/ponge/wsprotocol/operators/"
        p1 = TestingUtils.loadProtocol(base + "intersection/p1.wsprotocol")
        p2 = TestingUtils.loadProtocol(base + "intersection/p2.wsprotocol")
        p3 = TestingUtils.loadProtocol(base + "intersection/p1-inter-p2.wsprotocol")
    }

    void testIntersection()
    {
        def IntersectionOperator operator = new IntersectionOperator()
        def result = operator.apply(p1, p2)

        assert result == p3

        def t1_t1 = result.operations.find {it.name == "T1_T1"}
        assert t1_t1 != null
    }
}