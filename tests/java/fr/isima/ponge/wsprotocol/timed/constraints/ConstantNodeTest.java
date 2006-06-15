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

public class ConstantNodeTest extends TestCase
{
    ConstantNode node = new ConstantNode(666);

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.ConstantNode.negate()'
     */
    public void testNegate()
    {
        assertEquals(node, node.negate());
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.ConstantNode.equals(Object)'
     */
    public void testEqualsObject()
    {
        ConstantNode otherNode = new ConstantNode(666);
        assertEquals(node, otherNode);
        
        otherNode = new ConstantNode(555);
        assertNotSame(node, otherNode);
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.constraints.ConstantNode.toString()'
     */
    public void testToString()
    {
        assertEquals("666", node.toString());
    }

}
