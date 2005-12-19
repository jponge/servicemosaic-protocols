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

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.ExtraPropertiesKeeper;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;

/**
 * This class can manage I/O operations of a business protocol from/to an XML representation. By
 * default, extra properties of type <code>java.lang.String</code> have their handler loaded.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class XmlIOManager
{

    /** The factory needed to build the model elements. */
    protected BusinessProtocolFactory factory;

    /**
     * The extra properties handlers.
     */
    protected Map extraPropertiesHandlers = new HashMap();

    /**
     * Instanciates a new XML I/O manager.
     * 
     * @param factory
     *            The factory required to build the model elements.
     */
    public XmlIOManager(BusinessProtocolFactory factory)
    {
        super();
        this.factory = factory;

        setExtraPropertyHandler(String.class.getName(), new StringExtraPropertyHandler());
    }

    /**
     * Sets the extra properties handler for a given type.
     * 
     * @param className
     *            The type qualified class name.
     * @param handler
     *            The handler.
     */
    public void setExtraPropertyHandler(String className, ExtraPropertyHandler handler)
    {
        extraPropertiesHandlers.put(className, handler);
    }

    /**
     * Retrieves the extra properties handler for a given type.
     * 
     * @param className
     *            The type qualified class name.
     * @return The handler.
     */
    protected ExtraPropertyHandler getExtraPropertyHandler(String className)
    {
        ExtraPropertyHandler handler = (ExtraPropertyHandler) extraPropertiesHandlers
                .get(className);
        /*
         * if (handler == null) { handler = (ExtraPropertyHandler)
         * extraPropertiesHandlers.get(String.class.getName()); }
         */
        return handler;
    }

    /**
     * Reads a business protocol.
     * 
     * @param reader
     *            The reader object for the XML source.
     * @return The protocol.
     * @throws DocumentException
     *             Thrown if an error occurs.
     */
    public BusinessProtocol readBusinessProtocol(Reader reader) throws DocumentException
    {
        // Vars
        Iterator it;
        Node node;

        // Parsing
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(reader);

        // Protocol
        BusinessProtocol protocol = factory.createBusinessProtocol(document
                .valueOf("/business-protocol/name")); //$NON-NLS-1$
        readExtraProperties(protocol, document.selectSingleNode("/business-protocol")); //$NON-NLS-1$

        // States
        Map states = new HashMap();
        it = document.selectNodes("/business-protocol/state").iterator(); //$NON-NLS-1$
        while (it.hasNext())
        {
            node = (Node) it.next();
            State s = factory.createState(node.valueOf("name"), "true" //$NON-NLS-1$ //$NON-NLS-2$
                    .equals(node.valueOf("final"))); //$NON-NLS-1$
            readExtraProperties(s, node);
            protocol.addState(s);
            states.put(s.getName(), s);
            if (node.selectSingleNode("initial-state") != null) //$NON-NLS-1$
            {
                protocol.setInitialState(s);
            }
        }

        // Operations & messages
        it = document.selectNodes("/business-protocol/operation").iterator(); //$NON-NLS-1$
        while (it.hasNext())
        {
            node = (Node) it.next();
            String msgName = node.valueOf("message/name"); //$NON-NLS-1$
            String msgPol = node.valueOf("message/polarity"); //$NON-NLS-1$
            State s1 = (State) states.get(node.valueOf("source")); //$NON-NLS-1$
            State s2 = (State) states.get(node.valueOf("target")); //$NON-NLS-1$
            Polarity pol;
            if (msgPol.equals("positive")) //$NON-NLS-1$
            {
                pol = Polarity.POSITIVE;
            }
            else if (msgPol.equals("negative")) //$NON-NLS-1$
            {
                pol = Polarity.NEGATIVE;
            }
            else
            {
                pol = Polarity.NULL;
            }
            String opKindValue = node.valueOf("kind");
            OperationKind kind;
            if ("".equals(opKindValue) || "explicit".equals(opKindValue))
            {
                kind = OperationKind.EXPLICIT;
            }
            else
            {
                kind = OperationKind.IMPLICIT;
            }
            Message msg = factory.createMessage(msgName, pol);
            readExtraProperties(msg, node.selectSingleNode("message")); //$NON-NLS-1$
            Operation op = factory.createOperation(s1, s2, msg, kind);
            readExtraProperties(op, node);
            protocol.addOperation(op);
        }

        return protocol;
    }

    /**
     * Reads extra properties.
     * 
     * @param keeper
     *            The object having extra properties.
     * @param rootNode
     *            The XML node to gather the properties from.
     */
    protected void readExtraProperties(ExtraPropertiesKeeper keeper, Node rootNode)
    {
        Iterator it = rootNode.selectNodes("extra-property").iterator(); //$NON-NLS-1$
        while (it.hasNext())
        {
            Node node = (Node) it.next();
            String className = node.valueOf("@type");
            if (className == null)
            {
                className = String.class.getName();
            }
            ExtraPropertyHandler handler = getExtraPropertyHandler(className);
            if (handler != null)
            {
                handler.readExtraProperty((Element) node, keeper);
            }
        }
    }

    /**
     * Writes extra properties.
     * 
     * @param branch
     *            The XML node to write the properties to.
     * @param keeper
     *            The object having extra properties.
     */
    protected void writeExtraProperties(Branch branch, ExtraPropertiesKeeper keeper)
    {
        Iterator keysIt = keeper.getExtraPropertiesKeys().iterator();
        while (keysIt.hasNext())
        {
            String key = (String) keysIt.next();
            Object value = keeper.getExtraProperty(key);
            ExtraPropertyHandler handler = getExtraPropertyHandler(value.getClass().getName());
            if (handler != null)
            {
                handler.writeExtraProperty(branch, key, value);
            }
        }
    }

    /**
     * Writes a business protocol as an XML representation to a writer.
     * 
     * @param protocol
     *            The protocol.
     * @param writer
     *            The writer to use.
     * @throws IOException
     *             Thrown in case an I/O error occurs.
     */
    public void writeBusinessProtocol(BusinessProtocol protocol, Writer writer) throws IOException
    {
        // Root
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("business-protocol"); //$NON-NLS-1$

        // Protocol
        root.addElement("name").setText(protocol.getName()); //$NON-NLS-1$
        writeExtraProperties(root, protocol);

        // States
        Iterator it = protocol.getStates().iterator();
        while (it.hasNext())
        {
            State s = (State) it.next();
            Element el = root.addElement("state"); //$NON-NLS-1$
            el.addElement("name").setText(s.getName()); //$NON-NLS-1$
            el.addElement("final").setText(s.isFinalState() ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            if (s.isInitialState())
            {
                el.addElement("initial-state"); //$NON-NLS-1$
            }
            writeExtraProperties(el, s);
        }

        // Operations
        it = protocol.getOperations().iterator();
        while (it.hasNext())
        {
            Operation o = (Operation) it.next();
            Message m = o.getMessage();
            String pol;
            if (m.getPolarity().equals(Polarity.POSITIVE))
            {
                pol = "positive"; //$NON-NLS-1$
            }
            else if (m.getPolarity().equals(Polarity.NEGATIVE))
            {
                pol = "negative"; //$NON-NLS-1$

            }
            else
            {
                pol = "null"; //$NON-NLS-1$
            }
            Element oel = root.addElement("operation"); //$NON-NLS-1$
            Element mel = oel.addElement("message"); //$NON-NLS-1$
            mel.addElement("name").setText(m.getName()); //$NON-NLS-1$
            mel.addElement("polarity").setText(pol); //$NON-NLS-1$
            oel.addElement("source").setText(o.getSourceState().getName()); //$NON-NLS-1$
            oel.addElement("target").setText(o.getTargetState().getName()); //$NON-NLS-1$
            oel.addElement("kind").setText(o.getOperationKind().toString()); //$NON-NLS-1$
            writeExtraProperties(mel, m);
            writeExtraProperties(oel, o);
        }

        // Write
        XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
        xmlWriter.write(document);
    }

}
