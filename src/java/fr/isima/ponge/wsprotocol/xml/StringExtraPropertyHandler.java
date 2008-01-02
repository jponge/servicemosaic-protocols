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
