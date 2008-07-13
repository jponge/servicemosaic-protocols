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

    public IConstraintNode deepCopy()
    {
        return new ConstantNode(getConstant());
    }

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild)
    {
        // nothing...
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
