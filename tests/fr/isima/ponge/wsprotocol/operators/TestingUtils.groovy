package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*
import fr.isima.ponge.wsprotocol.impl.*
import fr.isima.ponge.wsprotocol.xml.*
import org.dom4j.DocumentException

class TestingUtils
{
    static final XmlIOManager xmlIOManager = new XmlIOManager(new BusinessProtocolFactoryImpl())

    public static BusinessProtocol loadProtocol(String path) throws DocumentException
    {
        return xmlIOManager.readBusinessProtocol(new FileReader(path))
    }

    public static void dumpOperations(BusinessProtocol p)
    {
        def list = p.operations.collect { "${it} - ${it.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)}" }
        println "${list.size()} operations:"
        println list.join("\n")
    }

}