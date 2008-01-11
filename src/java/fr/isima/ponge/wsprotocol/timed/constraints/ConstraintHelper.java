package fr.isima.ponge.wsprotocol.timed.constraints;

import static fr.isima.ponge.wsprotocol.timed.constraints.ComparisonConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Various utilities for constraints manipulation.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ConstraintHelper
{
    /**
     * Computes the set of disjunction-free constraints from a given constraint.
     *
     * @param constraintNode The original constraint that can contain disjunctions.
     * @return The list of disjunction-free constraints from <code>constraintNode</code>.
     */
    public List<IConstraintNode> makeDisjunctionFree(IConstraintNode constraintNode)
    {
        List<IConstraintNode> disjunctions = new ArrayList<IConstraintNode>();
        disjunctions.add(constraintNode);

        boolean allDisjointFree = false;
        while (!allDisjointFree)
        {
            allDisjointFree = true;
            for (IConstraintNode node : disjunctions)
            {
                if (!isDisjunctionFree(node))
                {
                    allDisjointFree = false;
                    IConstraintNode copy = node.deepCopy();
                    disjunctions.add(copy);
                    breakDisjunction(node, copy, null, null);
                    break;
                }
            }
        }

        return disjunctions;
    }

    private void breakDisjunction(IConstraintNode node, IConstraintNode copy, IConstraintNode nodeFather, IConstraintNode copyFather)
    {
        if (node instanceof IConstraintFunctionNode)
        {
            IConstraintFunctionNode fnode = (IConstraintFunctionNode) node;
            IConstraintFunctionNode fcopy = (IConstraintFunctionNode) copy;
            breakDisjunction(fnode.getNode(), fcopy.getNode(), fnode, fcopy);
        }
        else if ((node instanceof BooleanNode) && ((BooleanNode) node).getSymbol().equals(BooleanNode.OR))
        {
            BooleanNode bnode = (BooleanNode) node;
            BooleanNode bcopy = (BooleanNode) copy;
            nodeFather.replaceChildWith(bnode, bnode.getLeftChild());
            copyFather.replaceChildWith(bcopy, bcopy.getRightChild());
        }
        else if (node instanceof IRootConstraintNode)
        {
            IRootConstraintNode rnode = (IRootConstraintNode) node;
            IRootConstraintNode rcopy = (IRootConstraintNode) node;
            if (isDisjunctionFree(rnode.getLeftChild()))
            {
                breakDisjunction(rnode.getRightChild(), rcopy.getRightChild(), rnode, rcopy);
            }
            else
            {
                breakDisjunction(rnode.getLeftChild(), rcopy.getLeftChild(), rnode, rcopy);
            }
        }
    }

    /**
     * Tests wether a constraint is disjunction-free or not.
     *
     * @param constraintNode The constraint to test.
     * @return <code>true</code> if <code>constraintNode</code> is disjunction-free, <code>false</code> otherwise.
     */
    public boolean isDisjunctionFree(IConstraintNode constraintNode)
    {
        if (constraintNode instanceof BooleanNode)
        {
            BooleanNode bnode = (BooleanNode) constraintNode;
            if (bnode.getSymbol().equals(BooleanNode.OR))
            {
                return false;
            }
        }
        else if (constraintNode instanceof IRootConstraintNode)
        {
            IRootConstraintNode rnode = (IRootConstraintNode) constraintNode;
            return isDisjunctionFree(rnode.getLeftChild()) && isDisjunctionFree(rnode.getRightChild());
        }
        else if (constraintNode instanceof IConstraintFunctionNode)
        {
            IConstraintFunctionNode fnode = (IConstraintFunctionNode) constraintNode;
            return isDisjunctionFree(fnode.getNode());
        }
        return true;
    }

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

    /**
     * Returns the negation of an operator.
     *
     * @param operator The operator.
     * @return The operator negation.
     */
    public String operatorNegation(String operator)
    {
        return NEGATIONS.get(operator);
    }
}
