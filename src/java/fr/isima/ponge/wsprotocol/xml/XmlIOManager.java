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
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class can manage I/O operations of a business protocol from/to an XML representation. By
 * default, extra properties of type <code>java.lang.String</code> have their handler loaded.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class XmlIOManager
{

    /**
     * The factory needed to build the model elements.
     */
    protected BusinessProtocolFactory factory;

    /**
     * The extra properties handlers.
     */
    protected Map<String, ExtraPropertyHandler> extraPropertiesHandlers = new HashMap<String, ExtraPropertyHandler>();

    /**
     * Instanciates a new XML I/O manager.
     *
     * @param factory The factory required to build the model elements.
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
     * @param className The type qualified class name.
     * @param handler   The handler.
     */
    public void setExtraPropertyHandler(String className, ExtraPropertyHandler handler)
    {
        extraPropertiesHandlers.put(className, handler);
    }

    /**
     * Retrieves the extra properties handler for a given type.
     *
     * @param className The type qualified class name.
     * @return The handler.
     */
    protected ExtraPropertyHandler getExtraPropertyHandler(String className)
    {
        return extraPropertiesHandlers.get(className);
    }

    /**
     * Reads a business protocol.
     *
     * @param reader The reader object for the XML source.
     * @return The protocol.
     * @throws DocumentException Thrown if an error occurs.
     */
    @SuppressWarnings("unchecked")
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
        Map<String, State> states = new HashMap<String, State>();
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
        int legacyOperationNameCounter = 0;
        it = document.selectNodes("/business-protocol/operation").iterator(); //$NON-NLS-1$
        while (it.hasNext())
        {
            node = (Node) it.next();
            String opName = node.valueOf("name"); //$NON-NLS-1$
            if ("".equals(opName))
            {
                opName = "T" + legacyOperationNameCounter++;
            }
            String msgName = node.valueOf("message/name"); //$NON-NLS-1$
            String msgPol = node.valueOf("message/polarity"); //$NON-NLS-1$
            State s1 = states.get(node.valueOf("source")); //$NON-NLS-1$
            State s2 = states.get(node.valueOf("target")); //$NON-NLS-1$
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
            Operation op = factory.createOperation(opName, s1, s2, msg, kind);
            readExtraProperties(op, node);
            protocol.addOperation(op);
        }

        return protocol;
    }

    /**
     * Reads extra properties.
     *
     * @param keeper   The object having extra properties.
     * @param rootNode The XML node to gather the properties from.
     */
    protected void readExtraProperties(ExtraPropertiesKeeper keeper, Node rootNode)
    {
        for (Object o : rootNode.selectNodes("extra-property"))
        {
            Node node = (Node) o;
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
     * @param branch The XML node to write the properties to.
     * @param keeper The object having extra properties.
     */
    protected void writeExtraProperties(Branch branch, ExtraPropertiesKeeper keeper)
    {
        for (Object o : keeper.getExtraPropertiesKeys())
        {
            String key = (String) o;
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
     * @param protocol The protocol.
     * @param writer   The writer to use.
     * @throws IOException Thrown in case an I/O error occurs.
     */
    @SuppressWarnings("unchecked")
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
            oel.addElement("name").setText(o.getName()); //$NON-NLS-1$
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
