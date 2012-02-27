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

package fr.isima.ponge.wsprotocol;

import java.util.Set;

/**
 * Interface for an object that supports extra properties in a generic way. Properties are not
 * expected to be persistent. If a persistence manager wants to make some types persistent, then it
 * has to implement such a functionnality at its own discretion. For instance a persistence manager
 * could decide to store <code>String/String</code> for certain types.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface ExtraPropertiesKeeper
{
    /**
     * Gets an extra property.
     *
     * @param key The key.
     * @return The value or <code>null</code>.
     */
    public Object getExtraProperty(Object key);

    /**
     * Puts an extra property.
     *
     * @param key   The key.
     * @param value The value.
     */
    public void putExtraProperty(Object key, Object value);

    /**
     * Removes an extra property.
     *
     * @param key The property key.
     */
    public void removeExtraProperty(Object key);

    /**
     * Gets the keys of all the extra properties.
     *
     * @return The set of keys.
     */
    public Set<Object> getExtraPropertiesKeys();

}
