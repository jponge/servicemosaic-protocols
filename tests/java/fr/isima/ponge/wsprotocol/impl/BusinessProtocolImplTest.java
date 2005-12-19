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

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;

import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;

/**
 * Test case for the <code>BusinessProtocolImpl</code> class.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolImplTest extends TestCase
{

    BusinessProtocolImpl bp1;

    BusinessProtocolImpl bp2;

    BusinessProtocolImpl bp3;

    BusinessProtocolFactory factory;

    public BusinessProtocolImplTest()
    {
        super();

        factory = new BusinessProtocolFactoryImpl();
        State s0, s1;
        Message m;

        bp1 = (BusinessProtocolImpl) factory.createBusinessProtocol("BP1"); //$NON-NLS-1$
        s0 = factory.createState("s0", false); //$NON-NLS-1$
        bp1.addState(s0);
        bp1.setInitialState(s0);
        s1 = factory.createState("s1", true); //$NON-NLS-1$
        bp1.addState(s1);
        m = factory.createMessage("a", Polarity.POSITIVE); //$NON-NLS-1$
        bp1.addOperation(factory.createOperation(s0, s1, m));

        bp2 = (BusinessProtocolImpl) factory.createBusinessProtocol("BP2"); //$NON-NLS-1$
        s0 = factory.createState("s0", false); //$NON-NLS-1$
        bp2.addState(s0);
        bp2.setInitialState(s0);
        s1 = factory.createState("s1", true); //$NON-NLS-1$
        bp2.addState(s1);
        m = factory.createMessage("a", Polarity.POSITIVE); //$NON-NLS-1$
        bp2.addOperation(factory.createOperation(s0, s1, m));
        m = factory.createMessage("b", Polarity.NEGATIVE); //$NON-NLS-1$
        bp2.addOperation(factory.createOperation(s0, s0, m));

        bp3 = (BusinessProtocolImpl) factory.createBusinessProtocol("BP1"); //$NON-NLS-1$
        s0 = factory.createState("s0", false); //$NON-NLS-1$
        bp3.addState(s0);
        bp3.setInitialState(s0);
        s1 = factory.createState("s1", true); //$NON-NLS-1$
        bp3.addState(s1);
        m = factory.createMessage("a", Polarity.POSITIVE); //$NON-NLS-1$
        bp3.addOperation(factory.createOperation(s0, s1, m));
    }

    public void testAddRemoveOperationLogic()
    {
        Iterator it;
        String str;
        JXPathContext ctx = JXPathContext.newContext(bp2);

        TestCase.assertEquals("s0", bp2.getInitialState().getName()); //$NON-NLS-1$
        it = ctx.iterate("finalStates/name"); //$NON-NLS-1$
        TestCase.assertTrue(it.hasNext());
        TestCase.assertEquals("s1", (String) it.next()); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s0']/successors/name"); //$NON-NLS-1$
        TestCase.assertTrue(it.hasNext());
        TestCase.assertEquals("s1", (String) it.next()); //$NON-NLS-1$
        TestCase.assertEquals("s0", (String) it.next()); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s0']/predecessors/name"); //$NON-NLS-1$
        TestCase.assertTrue(it.hasNext());
        TestCase.assertEquals("s0", (String) it.next()); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s1']/predecessors/name"); //$NON-NLS-1$
        TestCase.assertTrue(it.hasNext());
        TestCase.assertEquals("s0", (String) it.next()); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s1']/successors/name"); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("messages/name"); //$NON-NLS-1$
        // Strange, sometimes b is before a and vice-versa ...
        TestCase.assertTrue(it.hasNext());
        str = (String) it.next();
        if ("a".equals(str)) //$NON-NLS-1$
        {
            TestCase.assertEquals("b", (String) it.next()); //$NON-NLS-1$
        }
        else if ("b".equals(str)) //$NON-NLS-1$
        {
            TestCase.assertEquals("a", (String) it.next()); //$NON-NLS-1$
        }
        else
        {
            TestCase.fail();
        }
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s0']/outgoingOperations/message/name"); //$NON-NLS-1$
        TestCase.assertTrue(it.hasNext());
        TestCase.assertEquals("a", (String) it.next()); //$NON-NLS-1$
        TestCase.assertEquals("b", (String) it.next()); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s0']/incomingOperations/message/name"); //$NON-NLS-1$
        TestCase.assertTrue(it.hasNext());
        TestCase.assertEquals("b", (String) it.next()); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s1']/incomingOperations/message/name"); //$NON-NLS-1$
        TestCase.assertTrue(it.hasNext());
        TestCase.assertEquals("a", (String) it.next()); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        it = ctx.iterate("states[name='s1']/outgoingOperations/message/name"); //$NON-NLS-1$
        TestCase.assertFalse(it.hasNext());

        Operation toRemove = (Operation) ctx.getValue("operations[message/name='a']"); //$NON-NLS-1$
        bp2.removeOperation(toRemove);

        List remainingOps = (List) ctx.getValue("states[name='s0']/incomingOperations"); //$NON-NLS-1$
        TestCase.assertTrue(remainingOps.size() == 1);

        List remainingSucc = (List) ctx.getValue("states[name='s0']/successors"); //$NON-NLS-1$
        TestCase.assertTrue(remainingSucc.size() == 1);

        State s0 = bp2.getInitialState();
        bp2.removeState(s0);
        TestCase.assertNull(bp2.getInitialState());
    }

    public void testEqualsObject()
    {
        TestCase.assertEquals(bp1, bp1);
        TestCase.assertEquals(bp2, bp2);
        TestCase.assertEquals(bp1, bp3);
        TestCase.assertNotSame(bp1, bp2);
    }

}
