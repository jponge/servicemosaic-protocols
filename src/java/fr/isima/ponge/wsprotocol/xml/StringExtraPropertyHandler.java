
package fr.isima.ponge.wsprotocol.xml;

import org.dom4j.Branch;
import org.dom4j.Element;

import fr.isima.ponge.wsprotocol.ExtraPropertiesKeeper;

/**
 * Extra properties handler for <code>java.lang.String</code>.
 * 
 * @author Julien Ponge <ponge@isima.fr>
 */
public class StringExtraPropertyHandler implements ExtraPropertyHandler
{

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.xml.ExtraPropertyHandler#getTypeId()
     */
    public String getTypeId()
    {
        return String.class.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.xml.ExtraPropertyHandler#readExtraProperty(org.dom4j.Element,
     *      fr.isima.ponge.wsprotocol.ExtraPropertiesKeeper)
     */
    public void readExtraProperty(Element element, ExtraPropertiesKeeper keeper)
    {
        keeper.putExtraProperty(element.valueOf("name"), element.valueOf("value"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.xml.ExtraPropertyHandler#writeExtraProperty(org.dom4j.Branch,
     *      java.lang.String, java.lang.Object)
     */
    public void writeExtraProperty(Branch keeperElement, String key, Object value)
    {
        Element prop = keeperElement.addElement("extra-property").addAttribute("type", getTypeId()); //$NON-NLS-1$ //$NON-NLS-2$
        prop.addElement("name").setText(key); //$NON-NLS-1$
        prop.addElement("value").setText((String) value); //$NON-NLS-1$
    }

}
