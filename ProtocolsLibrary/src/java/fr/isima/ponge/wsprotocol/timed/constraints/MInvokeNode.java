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


package fr.isima.ponge.wsprotocol.timed.constraints;

/**
 * A M-Invoke constraint node.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class MInvokeNode implements IConstraintFunctionNode
{
    private IRootConstraintNode node;

    /**
     * Instanciates a new M-Invoke constraint node.
     *
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

    public IConstraintNode deepCopy()
    {
        return new MInvokeNode((IRootConstraintNode) getNode().deepCopy());
    }

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild)
    {
        if ((oldChild == node) && (newChild instanceof IRootConstraintNode))
        {
            node = (IRootConstraintNode) newChild;
        }
    }

    /**
     * Gets the expression constraint node.
     *
     * @return The expression.
     */
    public IRootConstraintNode getNode()
    {
        return node;
    }

    /**
     * Sets the expression node.
     *
     * @param node The new expression.
     */
    public void setNode(IRootConstraintNode node)
    {
        this.node = node;
    }

}
