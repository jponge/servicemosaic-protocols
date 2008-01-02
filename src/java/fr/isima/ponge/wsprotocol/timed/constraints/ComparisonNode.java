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

import java.util.Map;
import java.util.TreeMap;

/**
 * A comparision node between a variable and a constant. They can either be placed on the left or
 * the right of the expression.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ComparisonNode implements IRootConstraintNode
{
    /**
     * The LESS symbol.
     */
    public static final String LESS = "<";

    /**
     * The LESS_EQ symbol.
     */
    public static final String LESS_EQ = "<=";

    /**
     * The EQ symbol.
     */
    public static final String EQ = "=";

    /**
     * The NEQ symbol.
     */
    public static final String NEQ = "!=";

    /**
     * The GREATER symbol.
     */
    public static final String GREATER = ">";

    /**
     * The GREATER_EQ symbol.
     */
    public static final String GREATER_EQ = ">=";

    private static final Map<String, String> NEGATIONS;

    static
    {
        NEGATIONS = new TreeMap<String, String>();
        NEGATIONS.put(LESS, GREATER_EQ);
        NEGATIONS.put(LESS_EQ, GREATER);
        NEGATIONS.put(EQ, NEQ);
        NEGATIONS.put(NEQ, EQ);
        NEGATIONS.put(GREATER, LESS_EQ);
        NEGATIONS.put(GREATER_EQ, LESS);
    }

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

        return new ComparisonNode(leftChild, rightChild, NEGATIONS.get(symbol));
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
