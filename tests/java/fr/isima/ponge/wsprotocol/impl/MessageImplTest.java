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
import fr.isima.ponge.wsprotocol.Polarity;

/**
 * Test case for the <code>MessageImpl</code> class.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class MessageImplTest extends TestCase
{

    public void testEqualsObject()
    {
        MessageImpl m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        MessageImpl m2 = new MessageImpl("a", Polarity.NEGATIVE); //$NON-NLS-1$
        MessageImpl m3 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        MessageImpl m4 = new MessageImpl("b", Polarity.POSITIVE); //$NON-NLS-1$

        TestCase.assertEquals(m1, m3);
        TestCase.assertNotSame(m1, m2);
        TestCase.assertNotSame(m1, m4);
    }

    public void testToString()
    {
        MessageImpl m = new MessageImpl("message", Polarity.POSITIVE); //$NON-NLS-1$
        TestCase.assertEquals("[message](+)", m.toString()); //$NON-NLS-1$
    }

}
