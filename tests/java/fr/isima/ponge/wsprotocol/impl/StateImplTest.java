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
* Copyright 2005, 2006 Julien Ponge. All rights reserved.
* Use is subject to license terms.
*/

package fr.isima.ponge.wsprotocol.impl;

import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.Polarity;
import junit.framework.TestCase;

/**
 * Test case for the <code>StateImpl</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateImplTest extends TestCase
{

    StateImpl s1;

    StateImpl s1f;

    StateImpl s2;

    StateImpl s3;

    protected void setUp() throws Exception
    {
        s1 = new StateImpl("s1", false); //$NON-NLS-1$
        s1f = new StateImpl("s1", true); //$NON-NLS-1$
        s2 = new StateImpl("s2", false); //$NON-NLS-1$
        s3 = new StateImpl("s3", true); //$NON-NLS-1$
    }

    protected void tearDown() throws Exception
    {
        s1 = null;
        s1f = null;
        s2 = null;
        s3 = null;
    }

    public void testEquals()
    {
        TestCase.assertNotSame(s1, s1f);
        TestCase.assertNotSame(s1, s2);

        s1f.setFinalState(false);
        TestCase.assertEquals(s1, s1f);
    }

    public void testToString()
    {
        TestCase.assertEquals("(s1)", s1.toString()); //$NON-NLS-1$
        TestCase.assertEquals("((s1))", s1f.toString()); //$NON-NLS-1$
    }

    public void testSuccPredLogic()
    {
        s2.addPredecessor(s1);
        s2.addSuccessor(s3);

        TestCase.assertTrue(s2.getPredecessors().contains(s1));
        TestCase.assertTrue(s2.getSuccessors().contains(s3));

        s2.removePredecessor(s1);
        s2.removeSuccessor(s3);

        TestCase.assertFalse(s2.getPredecessors().contains(s1));
        TestCase.assertFalse(s2.getSuccessors().contains(s3));
    }

    public void testInOutLogic()
    {
        BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl();
        Message ma = factory.createMessage("a", Polarity.POSITIVE); //$NON-NLS-1$
        Message mb = factory.createMessage("b", Polarity.POSITIVE); //$NON-NLS-1$
        Operation a = factory.createOperation("T1", s1, s2, ma);
        Operation b = factory.createOperation("T2", s2, s3, mb);

        s2.addIncomingOperation(a);
        s2.addOutgoingOperation(b);

        TestCase.assertTrue(s2.getIncomingOperations().contains(a));
        TestCase.assertTrue(s2.getOutgoingOperations().contains(b));

        s2.removeIncomingOperation(a);
        s2.removeOutgoingOperation(b);

        TestCase.assertFalse(s2.getIncomingOperations().contains(a));
        TestCase.assertFalse(s2.getOutgoingOperations().contains(b));
    }

}
