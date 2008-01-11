package fr.isima.ponge.wsprotocol.timed.constraints;

import junit.framework.TestCase;

public class DiagonalNodeTest extends TestCase
{
    public void testEquals()
    {
        DiagonalNode d1 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));
        DiagonalNode d2 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));
        DiagonalNode d3 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));

        assertEquals(d1, d2);
        assertNotSame(d1, d3);
    }

    public void testToString()
    {
        DiagonalNode d1 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));

        assertEquals("(T1 - T2 < 10)", d1.toString());
        assertEquals("(T1 - T2 >= 10)", d1.negate().toString());
    }
}
