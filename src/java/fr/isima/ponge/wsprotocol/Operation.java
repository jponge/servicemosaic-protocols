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

import java.io.Serializable;

/**
 * Defines an interface for a protocol operation.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface Operation extends ExtraPropertiesKeeper, Serializable
{
    /**
     * Gets the operation name;
     *
     * @return The operation name;
     */
    public String getName();

    /**
     * Gets the operation message.
     *
     * @return The operation message.
     */
    public Message getMessage();

    /**
     * Gets the operation source state.
     *
     * @return The source state.
     */
    public State getSourceState();

    /**
     * Gets the operation target state.
     *
     * @return The target state.
     */
    public State getTargetState();

    /**
     * Gets the operation kind.
     *
     * @return The operation kind.
     */
    public OperationKind getOperationKind();

}
