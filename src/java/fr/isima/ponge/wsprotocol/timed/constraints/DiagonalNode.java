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
 * Represents a diagonal constraint (x - y # k).
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class DiagonalNode implements IRootConstraintNode
{
    private static final ConstraintHelper CONSTRAINT_HELPER = new ConstraintHelper();

    private DiagonalVariablesPair variablesPair;

    private String operator;

    private ConstantNode constant;

    public DiagonalNode(DiagonalVariablesPair variablesPair, String operator, ConstantNode constant)
    {
        this.variablesPair = variablesPair;
        this.operator = operator;
        this.constant = constant;
    }

    public DiagonalVariablesPair getClocksPair()
    {
        return variablesPair;
    }

    public void setClocksPair(DiagonalVariablesPair variablesPair)
    {
        this.variablesPair = variablesPair;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public ConstantNode getConstant()
    {
        return constant;
    }

    public void setConstant(ConstantNode constant)
    {
        this.constant = constant;
    }

    public VariableNode getFirstVariable()
    {
        return variablesPair.getFirstVariable();
    }

    public void setFirstVariable(VariableNode firstVariable)
    {
        variablesPair.setFirstVariable(firstVariable);
    }

    public VariableNode getSecondVariable()
    {
        return variablesPair.getSecondVariable();
    }

    public void setSecondVariable(VariableNode secondVariable)
    {
        variablesPair.setSecondVariable(secondVariable);
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiagonalNode that = (DiagonalNode) o;

        if (!variablesPair.equals(that.variablesPair)) return false;
        if (!constant.equals(that.constant)) return false;
        if (!operator.equals(that.operator)) return false;

        return true;
    }

    public int hashCode()
    {
        int result;
        result = variablesPair.hashCode();
        result = 31 * result + operator.hashCode();
        result = 31 * result + constant.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("(").append(variablesPair).append(" ").append(operator).append(" ").append(constant).append(")").toString();
    }

    public IConstraintNode getLeftChild()
    {
        return variablesPair;
    }

    public IConstraintNode getRightChild()
    {
        return constant;
    }

    public IConstraintNode negate()
    {
        return new DiagonalNode(variablesPair, CONSTRAINT_HELPER.operatorNegation(operator), constant);
    }

    public IConstraintNode deepCopy()
    {
        return new DiagonalNode((DiagonalVariablesPair) variablesPair.deepCopy(), new String(operator), (ConstantNode) constant.deepCopy());
    }

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild)
    {
        if ((oldChild == variablesPair) && (newChild instanceof DiagonalVariablesPair))
        {
            variablesPair = (DiagonalVariablesPair) newChild;
        }
        else if ((oldChild == constant) && (newChild instanceof ConstantNode))
        {
            constant = (ConstantNode) newChild;
        }
    }
}
