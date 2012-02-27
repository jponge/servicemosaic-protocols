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
 * A comparision node between a variable and a constant. They can either be placed on the left or
 * the right of the expression.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ComparisonNode implements IRootConstraintNode, ComparisonConstants
{
    private static final ConstraintHelper CONSTRAINT_HELPER = new ConstraintHelper();

    private IConstraintNode leftChild;

    private IConstraintNode rightChild;

    private String symbol;

    /**
     * Instantiates a new comparison node.
     *
     * @param symbol The symbol.
     * @param var    The variable.
     * @param cst    The constant.
     */
    public ComparisonNode(String symbol, VariableNode var, ConstantNode cst)
    {
        this(var, cst, symbol);
    }

    /**
     * Instantiates a new comparision node.
     *
     * @param symbol The symbol.
     * @param cst    The constant.
     * @param var    The variable.
     */
    public ComparisonNode(String symbol, ConstantNode cst, VariableNode var)
    {
        this(cst, var, symbol);
    }

    /**
     * Instantiates a new comparision node.
     *
     * @param leftChild  The left child.
     * @param rightChild The right child.
     * @param symbol     The symbol.
     */
    protected ComparisonNode(IConstraintNode leftChild, IConstraintNode rightChild, String symbol)
    {
        super();

        this.symbol = symbol;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
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

        return new ComparisonNode(leftChild, rightChild, CONSTRAINT_HELPER.operatorNegation(symbol));
    }

    public IConstraintNode deepCopy()
    {
        return new ComparisonNode(getLeftChild().deepCopy(), getRightChild().deepCopy(), new String(getSymbol()));
    }

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild)
    {
        if (oldChild == leftChild)
        {
            leftChild = newChild;
        }
        else if (oldChild == rightChild)
        {
            rightChild = newChild;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof ComparisonNode)
        {
            ComparisonNode other = (ComparisonNode) arg0;
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
     * Sets the symbol.
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
    public void setLeftChild(IConstraintNode leftChild)
    {
        this.leftChild = leftChild;
    }

    /**
     * Sets the new right child.
     *
     * @param rightChild The new right child.
     */
    public void setRightChild(IConstraintNode rightChild)
    {
        this.rightChild = rightChild;
    }
}
