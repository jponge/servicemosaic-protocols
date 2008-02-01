package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.BusinessProtocol
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl
import fr.isima.ponge.wsprotocol.xml.XmlIOManager
import org.dom4j.DocumentException

class TestingUtils
{
    static final XmlIOManager xmlIOManager = new XmlIOManager(new BusinessProtocolFactoryImpl())

    public static BusinessProtocol loadProtocol(String path) throws DocumentException
    {
        return xmlIOManager.readBusinessProtocol(new FileReader(path))
    }

}