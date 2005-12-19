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

package fr.isima.ponge.wsprotocol.xml;

import java.io.StringReader;
import java.io.StringWriter;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;

/**
 * Test case for the <code>XmlIOManage</code> class.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class XmlIOManagerTest extends TestCase
{

    /** Logger. */
    private static Log log = LogFactory.getLog(XmlIOManagerTest.class);

    /*
     * Tests the whole I/O process.
     */
    public void testIO() throws Exception
    {

        // First, get a protocol up
        BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl();
        BusinessProtocol bp;
        State s0, s1;
        Message m;
        bp = factory.createBusinessProtocol("BP"); //$NON-NLS-1$
        bp.putExtraProperty("proto-prop", "something"); //$NON-NLS-1$ //$NON-NLS-2$
        s0 = factory.createState("s0", false); //$NON-NLS-1$
        s0.putExtraProperty("some.editor.position", "(3,4)"); //$NON-NLS-1$ //$NON-NLS-2$
        bp.addState(s0);
        bp.setInitialState(s0);
        s1 = factory.createState("s1", true); //$NON-NLS-1$
        s1.putExtraProperty("some.editor.position", "(10,9)"); //$NON-NLS-1$ //$NON-NLS-2$
        s1.putExtraProperty("some.property", "foo.bar"); //$NON-NLS-1$ //$NON-NLS-2$
        bp.addState(s1);
        m = factory.createMessage("a", Polarity.POSITIVE); //$NON-NLS-1$
        m.putExtraProperty("some.thing", "is.here"); //$NON-NLS-1$ //$NON-NLS-2$
        bp.addOperation(factory.createOperation(s0, s1, m));
        m = factory.createMessage("b", Polarity.NEGATIVE); //$NON-NLS-1$
        bp.addOperation(factory.createOperation(s0, s0, m));

        // Make an XML representation of it
        StringWriter writer = new StringWriter();
        XmlIOManager manager = new XmlIOManager(factory);
        manager.writeBusinessProtocol(bp, writer);

        if (log.isDebugEnabled())
        {
            log.debug("XML representation:\n" + writer.toString()); //$NON-NLS-1$
        }

        // Round trip
        StringReader reader = new StringReader(writer.toString());
        BusinessProtocol readProtocol = manager.readBusinessProtocol(reader);
        StringWriter newWriter = new StringWriter();
        manager.writeBusinessProtocol(readProtocol, newWriter);

        TestCase.assertEquals(writer.toString(), newWriter.toString());
        TestCase.assertEquals(bp, readProtocol);
    }

}
