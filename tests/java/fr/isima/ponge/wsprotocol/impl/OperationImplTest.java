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
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;

/**
 * Test case for the <code>OperationImpl</code> class.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationImplTest extends TestCase
{

    public void testEqualsObject()
    {
        State s1 = new StateImpl("s1", false); //$NON-NLS-1$
        State s2 = new StateImpl("s2", false); //$NON-NLS-1$
        State s3 = new StateImpl("s3", false); //$NON-NLS-1$
        State s4 = new StateImpl("s4", false); //$NON-NLS-1$
        Message m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        Message m2 = new MessageImpl("a", Polarity.NEGATIVE); //$NON-NLS-1$

        OperationImpl o1 = new OperationImpl(s1, s2, m1);
        OperationImpl o2 = new OperationImpl(s3, s4, m2);

        TestCase.assertEquals(o1, o1);
        TestCase.assertNotSame(o1, o2);
    }

    public void testToString()
    {
        State s1 = new StateImpl("s1", false); //$NON-NLS-1$
        State s2 = new StateImpl("s2", false); //$NON-NLS-1$
        Message m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        OperationImpl o1 = new OperationImpl(s1, s2, m1);

        TestCase.assertEquals("((s1),[a](+),(s2),explicit)", o1.toString()); //$NON-NLS-1$
    }

}
