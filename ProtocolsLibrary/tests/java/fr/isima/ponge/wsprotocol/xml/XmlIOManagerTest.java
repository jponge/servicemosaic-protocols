/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Université Blaise Pascal, LIMOS, Clermont-Ferrand, France.
 * Copyright 2005-2008 The University of New South Wales, Sydney, Australia.
 * 
 * This file is part of ServiceMosaic Protocols <http://servicemosaic.isima.fr/>.
 * 
 * ServiceMosaic Protocols is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ServiceMosaic Protocols is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with ServiceMosaic Protocols.  If not, see <http://www.gnu.org/licenses/>.
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
