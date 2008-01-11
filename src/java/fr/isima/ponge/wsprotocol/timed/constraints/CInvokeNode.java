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
 * A C-Invoke constraint node.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class CInvokeNode implements IConstraintFunctionNode
{
    private IRootConstraintNode node;

    /**
     * Instantiates a new C-Invoke node.
     *
     * @param node The expression associated with the constraint.
     */
    public CInvokeNode(IRootConstraintNode node)
    {
        super();
        this.node = node;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof CInvokeNode)
        {
            CInvokeNode other = (CInvokeNode) arg0;
            return other.node.equals(node);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return "C-Invoke".hashCode() + node.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "C-Invoke" + node.toString();
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode#negate()
     */
    public IConstraintNode negate()
    {
        return new CInvokeNode((IRootConstraintNode) node.negate());
    }

    public IConstraintNode deepCopy()
    {
        return new CInvokeNode((IRootConstraintNode) getNode().deepCopy());
    }

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild)
    {
        if ((oldChild == node) && (newChild instanceof IRootConstraintNode))
        {
            node = (IRootConstraintNode) newChild;
        }
    }

    /**
     * Gets the expression node.
     *
     * @return The expression node.
     */
    public IRootConstraintNode getNode()
    {
        return node;
    }

    /**
     * Sets the expression node.
     *
     * @param node The new expression node.
     */
    public void setNode(IRootConstraintNode node)
    {
        this.node = node;
    }

}
