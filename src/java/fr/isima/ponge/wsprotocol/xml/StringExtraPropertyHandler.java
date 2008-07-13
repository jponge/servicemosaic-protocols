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

import fr.isima.ponge.wsprotocol.ExtraPropertiesKeeper;
import org.dom4j.Branch;
import org.dom4j.Element;

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
