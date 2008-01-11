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
