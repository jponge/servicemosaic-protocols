package fr.isima.ponge.wsprotocol.timed.constraints;

import junit.framework.TestCase;

public class DiagonalClocksPairTest extends TestCase
{
    public void testEquals()
    {
        DiagonalVariablesPair d1 = new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2"));
        DiagonalVariablesPair d2 = new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2"));
        DiagonalVariablesPair d3 = new DiagonalVariablesPair(new VariableNode("T2"), new VariableNode("T3"));

        assertEquals(d1, d2);
        assertNotSame(d2, d3);
    }

    public void testToString()
    {
        DiagonalVariablesPair d1 = new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2"));

        assertEquals("T1 - T2", d1.toString());
    }
}
