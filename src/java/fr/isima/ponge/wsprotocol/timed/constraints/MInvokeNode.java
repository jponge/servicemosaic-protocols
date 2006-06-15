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
 * A M-Invoke constraint node.
 * @author Julien Ponge (ponge@isima.fr)
 *
 */
public class MInvokeNode implements IConstraintNode
{
    private IRootConstraintNode node;

    /**
     * Instanciates a new M-Invoke constraint node.
     * @param node The expression constraint node.
     */
    public MInvokeNode(IRootConstraintNode node)
    {
        super();
        this.node = node;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof MInvokeNode)
        {
            MInvokeNode other = (MInvokeNode) arg0;
            return other.node.equals(node);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return "M-Invoke".hashCode() + node.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "M-Invoke" + node.toString();
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode#negate()
     */
    public IConstraintNode negate()
    {
        return new MInvokeNode((IRootConstraintNode) node.negate());
    }

    /**
     * Gets the expression constraint node.
     * @return The expression.
     */
    public IRootConstraintNode getNode()
    {
        return node;
    }

    /**
     * Sets the expression node.
     * @param node The new expression.
     */
    public void setNode(IRootConstraintNode node)
    {
        this.node = node;
    }

}
