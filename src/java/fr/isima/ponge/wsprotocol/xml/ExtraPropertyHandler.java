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
