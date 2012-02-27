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
        IConstraintNode startNode = constraintNode.deepCopy();
        disjunctions.add(startNode);

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
            breakDisjunction(fnode.getNode(), fcopy.getNode(), node, copy);
        }
        else if ((node instanceof BooleanNode) && BooleanNode.OR.equals(((BooleanNode) node).getSymbol()))
        {
            BooleanNode bnode = (BooleanNode) node;
            BooleanNode bcopy = (BooleanNode) copy;
            nodeFather.replaceChildWith(bnode, bnode.getLeftChild());
            copyFather.replaceChildWith(bcopy, bcopy.getRightChild());
        }
        else if (node instanceof IRootConstraintNode)
        {
            IRootConstraintNode rnode = (IRootConstraintNode) node;
            IRootConstraintNode rcopy = (IRootConstraintNode) copy;
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

        if (constraintNode instanceof IRootConstraintNode)
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

    /**
     * Checks wether a MInvoke constraint is valid.
     *
     * @param node The MInvoke constraint.
     * @return <code>true</code> if the constraint is valid, <code>false</code> otherwise.
     */
    public boolean isValidMInvoke(IConstraintNode node)
    {
        List<IConstraintNode> disjunctions = makeDisjunctionFree(node);
        for (IConstraintNode disjunction : disjunctions)
        {
            if (!containsEqualityCheck(disjunction))
            {
                return false;
            }
        }
        return true;
    }

    private boolean containsEqualityCheck(IConstraintNode node)
    {
        if (node instanceof ComparisonNode)
        {
            ComparisonNode comparisonNode = (ComparisonNode) node;
            if (ComparisonConstants.EQ.equals(comparisonNode.getSymbol()))
            {
                return true;
            }
        }

        if (node instanceof IRootConstraintNode)
        {
            IRootConstraintNode rootConstraintNode = (IRootConstraintNode) node;
            return containsEqualityCheck(rootConstraintNode.getLeftChild())
                    || containsEqualityCheck(rootConstraintNode.getRightChild());
        }
        else if (node instanceof IConstraintFunctionNode)
        {
            return containsEqualityCheck(((IConstraintFunctionNode) node).getNode());
        }

        return false;
    }
}
