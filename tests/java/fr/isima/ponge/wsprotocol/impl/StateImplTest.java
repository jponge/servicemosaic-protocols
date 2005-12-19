/*
 * Copyright (c) 2005 Julien Ponge - All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.isima.ponge.wsprotocol.impl;

import junit.framework.TestCase;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.Polarity;

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
        Operation a = factory.createOperation(s1, s2, ma);
        Operation b = factory.createOperation(s2, s3, mb);

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
