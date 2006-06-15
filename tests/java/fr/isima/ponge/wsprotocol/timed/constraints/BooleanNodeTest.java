/* 
 * CDDL HEADER START 
 * 
 * The contents of this file are subject to the terms of the 
 * Common Development and Distribution License (the "License"). 
 * You may not use this file except in compliance with the License. 
 * 
 * You can obtain a copy of the license at LICENSE.txt 
 * or at http://www.opensource.org/licenses/cddl1.php. 
 * See the License for the specific language governing permissions 
 * and limitations under the License. 
 * 
 * When distributing Covered Code, include this CDDL HEADER in each 
 * file and include the License file at LICENSE.txt. 
 * If applicable, add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your own identifying 
 * information: Portions Copyright [yyyy] [name of copyright owner] 
 * 
 * CDDL HEADER END 
 */ 

/* 
 * Copyright 2006 Julien Ponge. All rights reserved. 
 * Use is subject to license terms. 
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
