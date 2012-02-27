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
 * Represents a pair of variables in a diagonal constraint (e.g., T1 - T2).
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class DiagonalVariablesPair implements IRootConstraintNode
{
    private VariableNode firstVariable;

    private VariableNode secondVariable;

    public DiagonalVariablesPair(VariableNode firstVariable, VariableNode secondVariable)
    {
        this.firstVariable = firstVariable;
        this.secondVariable = secondVariable;
    }

    public VariableNode getFirstVariable()
    {
        return firstVariable;
    }

    public void setFirstVariable(VariableNode firstVariable)
    {
        this.firstVariable = firstVariable;
    }

    public VariableNode getSecondVariable()
    {
        return secondVariable;
    }

    public void setSecondVariable(VariableNode secondVariable)
    {
        this.secondVariable = secondVariable;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        DiagonalVariablesPair that = (DiagonalVariablesPair) o;

        if (!firstVariable.equals(that.firstVariable))
        {
            return false;
        }
        if (!secondVariable.equals(that.secondVariable))
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        int result;
        result = firstVariable.hashCode();
        result = 31 * result + secondVariable.hashCode();
        return result;
    }

    public IConstraintNode negate()
    {
        return this;
    }

    public IConstraintNode deepCopy()
    {
        return new DiagonalVariablesPair((VariableNode) firstVariable.deepCopy(), (VariableNode) secondVariable.deepCopy());
    }

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild)
    {
        if ((oldChild == firstVariable) && (newChild instanceof VariableNode))
        {
            firstVariable = (VariableNode) newChild;
        }
        else if ((oldChild == secondVariable) && (newChild instanceof VariableNode))
        {
            secondVariable = (VariableNode) newChild;
        }
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append(firstVariable).append(" - ").append(secondVariable).toString();
    }

    public IConstraintNode getLeftChild()
    {
        return firstVariable;
    }

    public IConstraintNode getRightChild()
    {
        return secondVariable;
    }
}
