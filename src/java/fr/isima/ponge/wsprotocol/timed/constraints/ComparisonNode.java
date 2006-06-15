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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ComparisonNode implements IRootConstraintNode
{
    public static final String LESS = "<";

    public static final String LESS_EQ = "<=";

    public static final String EQ = "=";

    public static final String NEQ = "!=";

    public static final String GREATER = ">";

    public static final String GREATER_EQ = ">=";

    private static final Set SYMBOLS;

    private static final Map NEGATIONS;

    static
    {
        SYMBOLS = new TreeSet();
        SYMBOLS.add(LESS);
        SYMBOLS.add(LESS_EQ);
        SYMBOLS.add(EQ);
        SYMBOLS.add(NEQ);
        SYMBOLS.add(GREATER);
        SYMBOLS.add(GREATER_EQ);

        NEGATIONS = new TreeMap();
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

    public ComparisonNode(String symbol, VariableNode var, ConstantNode cst)
    {
        this(var, cst, symbol);
    }

    public ComparisonNode(String symbol, ConstantNode cst, VariableNode var)
    {
        this(cst, var, symbol);
    }

    protected ComparisonNode(IConstraintNode leftChild, IConstraintNode rightChild, String symbol)
    {
        super();

        this.symbol = symbol;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public IConstraintNode getLeftChild()
    {
        return leftChild;
    }

    public IConstraintNode getRightChild()
    {
        return rightChild;
    }

    public IConstraintNode negate()
    {

        return new ComparisonNode(leftChild, rightChild, (String) NEGATIONS.get(symbol));
    }

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

    public int hashCode()
    {
        return symbol.hashCode() + leftChild.hashCode() + rightChild.hashCode();
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(").append(leftChild).append(" ").append(symbol).append(" ").append(
                rightChild).append(")");
        return buffer.toString();
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public void setLeftChild(IConstraintNode leftChild)
    {
        this.leftChild = leftChild;
    }

    public void setRightChild(IConstraintNode rightChild)
    {
        this.rightChild = rightChild;
    }
};
