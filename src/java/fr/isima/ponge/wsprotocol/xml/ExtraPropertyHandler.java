
package fr.isima.ponge.wsprotocol.xml;

import org.dom4j.Branch;
import org.dom4j.Element;

import fr.isima.ponge.wsprotocol.ExtraPropertiesKeeper;

/**
 * Interface for extra properties handlers. These handlers are able to read extra properties of a
 * given type as well as write them back as a DOM subtree.
 * 
 * @author Julien Ponge <ponge@isima.fr>
 */
public interface ExtraPropertyHandler
{

    /**
     * Gets the type qualified class name (for example <code>java.lang.String</code>).
     * 
     * @return The type name.
     */
    public String getTypeId();

    /**
     * Reads an extra property.
     * 
     * @param element
     *            The <code>extra-property</code> element to read from.
     * @param keeper
     *            The keeper to set the property.
     */
    public void readExtraProperty(Element element, ExtraPropertiesKeeper keeper);

    /**
     * Writes an extra property.
     * 
     * @param keeperElement
     *            The keeper DOM element where the keeper must add the <code>extra-property</code>
     *            element.
     * @param key
     *            The property key.
     * @param value
     *            The property value.
     */
    public void writeExtraProperty(Branch keeperElement, String key, Object value);

}
