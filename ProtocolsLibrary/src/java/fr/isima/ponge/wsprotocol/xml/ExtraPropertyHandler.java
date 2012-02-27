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
     * @param element The <code>extra-property</code> element to read from.
     * @param keeper  The keeper to set the property.
     */
    public void readExtraProperty(Element element, ExtraPropertiesKeeper keeper);

    /**
     * Writes an extra property.
     *
     * @param keeperElement The keeper DOM element where the keeper must add the <code>extra-property</code>
     *                      element.
     * @param key           The property key.
     * @param value         The property value.
     */
    public void writeExtraProperty(Branch keeperElement, String key, Object value);

}
