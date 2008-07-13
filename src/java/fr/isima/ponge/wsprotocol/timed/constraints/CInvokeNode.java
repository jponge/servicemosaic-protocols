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
