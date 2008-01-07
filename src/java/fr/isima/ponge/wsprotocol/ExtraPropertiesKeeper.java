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
