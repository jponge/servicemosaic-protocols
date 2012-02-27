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


package fr.isima.ponge.wsprotocol.timed.constraints;

import junit.framework.TestCase;

public class ComparisonNodeTest extends TestCase
{
    ComparisonNode node;

    public ComparisonNodeTest()
    {
        VariableNode var = new VariableNode("T1");
        ConstantNode cst = new ConstantNode(5);
        node = new ComparisonNode(ComparisonNode.LESS, var, cst);
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.ComparisonNode.negate()'
     */
    public void testNegate()
    {
        VariableNode var = new VariableNode("T1");
        ConstantNode cst = new ConstantNode(5);
        ComparisonNode otherNode = new ComparisonNode(ComparisonNode.GREATER_EQ, var, cst);

        assertEquals(otherNode, node.negate());
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.ComparisonNode.equals(Object)'
     */
    public void testEqualsObject()
    {
        VariableNode var = new VariableNode("T1");
        ConstantNode cst = new ConstantNode(5);
        ComparisonNode otherNode = new ComparisonNode(ComparisonNode.LESS, var, cst);

        assertEquals(otherNode, node);
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.ComparisonNode.toString()'
     */
    public void testToString()
    {
        assertEquals("(T1 < 5)", node.toString());
    }

}
