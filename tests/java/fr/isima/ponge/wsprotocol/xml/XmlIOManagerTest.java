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

package fr.isima.ponge.wsprotocol.xml;

import fr.isima.ponge.wsprotocol.*;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Test case for the <code>XmlIOManage</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class XmlIOManagerTest extends TestCase
{

    /**
     * Logger.
     */
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
        bp.addOperation(factory.createOperation("T1", s0, s1, m));
        m = factory.createMessage("b", Polarity.NEGATIVE); //$NON-NLS-1$
        bp.addOperation(factory.createOperation("T2", s0, s0, m));

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
