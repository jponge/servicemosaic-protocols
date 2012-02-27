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

package fr.isima.ponge.wsprotocol.timed.operators;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import fr.isima.ponge.wsprotocol.timed.constraints.CInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.ComparisonNode;
import fr.isima.ponge.wsprotocol.timed.constraints.ConstantNode;
import fr.isima.ponge.wsprotocol.timed.constraints.VariableNode;

public class ConstraintRewritingWalkerTest extends TestCase
{

    private CInvokeNode ciNode;
    
    private ConstraintRewritingWalker walker = new ConstraintRewritingWalker();
    
    public void setUp()
    {        
        VariableNode var = new VariableNode("T1");
        ConstantNode cst = new ConstantNode(5);
        ComparisonNode node = new ComparisonNode(ComparisonNode.LESS, var, cst);
        ciNode = new CInvokeNode(node);
    }
    
    public void tearDown()
    {
        ciNode = null;
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.operators.ConstraintRewritingWalker.rewriteTemporaryOnLeft(IConstraintNode)'
     */
    public void testRewriteTemporaryOnLeft()
    {
        walker.rewriteTemporaryOnLeft(ciNode);
        TestCase.assertEquals("C-Invoke(T1_ < 5)", ciNode.toString());
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.operators.ConstraintRewritingWalker.rewriteTemporaryOnRight(IConstraintNode)'
     */
    public void testRewriteTemporaryOnRight()
    {
        walker.rewriteTemporaryOnRight(ciNode);
        TestCase.assertEquals("C-Invoke(_T1 < 5)", ciNode.toString());
    }
    
    public void testRewriteConstraints()
    {
        Map mapping = new HashMap();
        mapping.put("T1_", "T1_T2");
        walker.rewriteTemporaryOnLeft(ciNode);
        walker.rewriteFromMapping(ciNode, mapping);
        
        TestCase.assertEquals("C-Invoke(T1_T2 < 5)", ciNode.toString());
    }

}
