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

public class BooleanNodeTest extends TestCase
{
    BooleanNode bnode1;
    BooleanNode bnode2;
    ComparisonNode node1;
    ComparisonNode node2;
    ComparisonNode node3;

    public BooleanNodeTest()
    {
        VariableNode var = new VariableNode("T1");
        ConstantNode cst = new ConstantNode(5);
        node1 = new ComparisonNode(ComparisonNode.LESS, var, cst);

        var = new VariableNode("T2");
        cst = new ConstantNode(10);
        node2 = new ComparisonNode(ComparisonNode.GREATER_EQ, var, cst);

        var = new VariableNode("T3");
        cst = new ConstantNode(7);
        node3 = new ComparisonNode(ComparisonNode.EQ, var, cst);

        bnode1 = new BooleanNode(BooleanNode.AND, node1, node2);
        bnode2 = new BooleanNode(BooleanNode.OR, bnode1, node3);
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.BooleanNode.toString()'
     */
    public void testToString()
    {
        TestCase.assertEquals("((T1 < 5) && (T2 >= 10))", bnode1.toString());
        TestCase.assertEquals("((T1 >= 5) || (T2 < 10))", bnode1.negate().toString());

        TestCase.assertEquals("(((T1 < 5) && (T2 >= 10)) || (T3 = 7))", bnode2.toString());
        TestCase.assertEquals("(((T1 >= 5) || (T2 < 10)) && (T3 != 7))", bnode2.negate().toString());
    }

}
