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
* Copyright 2006 Julien Ponge. All rights reserved.
* Use is subject to license terms.
*/

package fr.isima.ponge.wsprotocol.timed.constraints;

/**
 * A constant node.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ConstantNode implements IConstraintNode
{
    private int constant;

    /**
     * Instantiates a new constant node.
     *
     * @param constant The constant value.
     */
    public ConstantNode(int constant)
    {
        super();
        this.constant = constant;
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode#negate()
     */
    public IConstraintNode negate()
    {
        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof ConstantNode)
        {
            ConstantNode other = (ConstantNode) arg0;
            return constant == other.constant;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return constant;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return Integer.toString(constant);
    }

    /**
     * Gets the constant value.
     *
     * @return The constant value.
     */
    public int getConstant()
    {
        return constant;
    }

    /**
     * Sets the constant value.
     *
     * @param constant The new constant value.
     */
    public void setConstant(int constant)
    {
        this.constant = constant;
    }
}
