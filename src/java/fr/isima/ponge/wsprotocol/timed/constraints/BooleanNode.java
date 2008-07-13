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

import java.util.Map;
import java.util.TreeMap;

/**
 * A boolean node between two comparison nodes.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BooleanNode implements IRootConstraintNode, BooleanConstants
{
    private static final Map<String, String> NEGATIONS;

    static
    {
        NEGATIONS = new TreeMap<String, String>();
        NEGATIONS.put("&&", "||");
        NEGATIONS.put("||", "&&");
    }

    private IRootConstraintNode leftChild;

    private IRootConstraintNode rightChild;

    private String symbol;

    /**
     * Instantiates a new boolean node.
     *
     * @param symbol     The symbol (AND, OR).
     * @param leftChild  The left child.
     * @param rightChild The right child.
     */
    public BooleanNode(String symbol, IRootConstraintNode leftChild, IRootConstraintNode rightChild)
    {
        super();
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.symbol = symbol;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.timed.constraints.IRootConstraintNode#getLeftChild()
     */
    public IConstraintNode getLeftChild()
    {
        return leftChild;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.timed.constraints.IRootConstraintNode#getRightChild()
     */
    public IConstraintNode getRightChild()
    {
        return rightChild;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode#negate()
     */
    public IConstraintNode negate()
    {
        return new BooleanNode(NEGATIONS.get(symbol), (IRootConstraintNode) leftChild
                .negate(), (IRootConstraintNode) rightChild.negate());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof BooleanNode)
        {
            BooleanNode other = (BooleanNode) arg0;
            return symbol.equals(other.symbol) && leftChild.equals(other.leftChild)
                    && rightChild.equals(other.rightChild);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return symbol.hashCode() + leftChild.hashCode() + rightChild.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(").append(leftChild).append(" ").append(symbol).append(" ").append(
                rightChild).append(")");
        return buffer.toString();
    }

    /**
     * Gets the symbol.
     *
     * @return The symbol.
     */
    public String getSymbol()
    {
        return symbol;
    }

    /**
     * Changes the symbol.
     *
     * @param symbol The new symbol.
     */
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    /**
     * Sets the left child.
     *
     * @param leftChild The new left child.
     */
    public void setLeftChild(IRootConstraintNode leftChild)
    {
        this.leftChild = leftChild;
    }

    /**
     * Sets the right child.
     *
     * @param rightChild The new right child.
     */
    public void setRightChild(IRootConstraintNode rightChild)
    {
        this.rightChild = rightChild;
    }

    public IConstraintNode deepCopy()
    {
        return new BooleanNode(new String(getSymbol()), (IRootConstraintNode) getLeftChild().deepCopy(), (IRootConstraintNode) getRightChild().deepCopy());
    }

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild)
    {
        if (!(newChild instanceof IRootConstraintNode))
        {
            return;
        }

        if (oldChild == leftChild)
        {
            leftChild = (IRootConstraintNode) newChild;
        }
        else if (oldChild == rightChild)
        {
            rightChild = (IRootConstraintNode) newChild;
        }
    }

}
