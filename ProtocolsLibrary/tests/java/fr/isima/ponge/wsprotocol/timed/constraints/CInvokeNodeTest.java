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
import junit.framework.Test;

public class CInvokeNodeTest extends TestCase
{

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.CInvokeNode.toString()'
     */
    public void testToString()
    {
        VariableNode var = new VariableNode("T1");
        ConstantNode cst = new ConstantNode(5);
        ComparisonNode node = new ComparisonNode(ComparisonNode.LESS, var, cst);
        CInvokeNode ciNode = new CInvokeNode(node);

        TestCase.assertEquals("C-Invoke(T1 < 5)", ciNode.toString());

        ComparisonNode node2 = new ComparisonNode(ComparisonNode.GREATER_EQ, new VariableNode("T2"), new ConstantNode(10));
        ciNode = new CInvokeNode(new BooleanNode(BooleanNode.AND, node, node2));
        TestCase.assertEquals("C-Invoke((T1 < 5) && (T2 >= 10))", ciNode.toString());

        CInvokeNode copy = (CInvokeNode) ciNode.deepCopy();
        TestCase.assertEquals(ciNode.toString(), copy.toString());
    }

}
