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

package fr.isima.ponge.wsprotocol.timed.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.impl.StateImpl;
import fr.isima.ponge.wsprotocol.timed.constraints.BooleanNode;
import fr.isima.ponge.wsprotocol.timed.constraints.CInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.ComparisonNode;
import fr.isima.ponge.wsprotocol.timed.constraints.ConstantNode;
import fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode;
import fr.isima.ponge.wsprotocol.timed.constraints.IRootConstraintNode;
import fr.isima.ponge.wsprotocol.timed.constraints.MInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.VariableNode;

// TODO: cleanup / refactor some portions of code

public class Normalizer extends AbstractOperator
{

    public Normalizer(BusinessProtocolFactory factory)
    {
        super(factory);
    }

    public BusinessProtocol apply(BusinessProtocol p1, BusinessProtocol p2)
    {
        return null;
    }
    
    public BusinessProtocol normalizeProtocol(BusinessProtocol p)
    {
        /*
         * 1. Find all implicit operations
         * 2. Infer temporal constraints
         * 3. Merge states
         */
        Map inferredMoreMap = new HashMap();
        Iterator it = new ArrayList(p.getOperations()).iterator();
        while (it.hasNext())
        {
            // Identify implicit operations
            Operation op = (Operation) it.next();
            State source = op.getSourceState();
            State target = op.getTargetState();
            if (op.getOperationKind().equals(OperationKind.EXPLICIT))
            {
                continue;
            }
            
            // Infer a constraint for C-Invoke
            String minvokeCst = (String) op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
            if (isConstraintEmpty(minvokeCst))
            {
                continue;
            }
            MInvokeNode minvoke = (MInvokeNode) parseConstraint(minvokeCst);
            if (minvoke == null)
            {
                continue;
            }
            ComparisonNode comparison = (ComparisonNode) minvoke.getNode();
            VariableNode var = (VariableNode) ((comparison.getLeftChild() instanceof VariableNode) ? comparison.getLeftChild() : comparison.getRightChild());
            ConstantNode cst = (ConstantNode) ((comparison.getLeftChild() instanceof ConstantNode) ? comparison.getLeftChild() : comparison.getRightChild());
            ComparisonNode inferredLess = new ComparisonNode(ComparisonNode.LESS, var, cst);
            ComparisonNode inferredMore = new ComparisonNode(ComparisonNode.GREATER_EQ, var, cst);
            inferredMoreMap.put(op.getName(), inferredMore);
            
            // Apply the inferred constraints
            Iterator opIt = source.getOutgoingOperations().iterator();
            while (opIt.hasNext())
            {
                Operation o = (Operation) opIt.next();
                if (o == op || o.getOperationKind().equals(OperationKind.IMPLICIT))
                {
                    continue;
                }
                
                String oCst = (String) o.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
                if (isConstraintEmpty(oCst))
                {
                    CInvokeNode cinvoke = new CInvokeNode(inferredLess);
                    o.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cinvoke.toString());
                }
                else
                {
                    CInvokeNode cinvoke = (CInvokeNode) parseConstraint(oCst);
                    if (cinvoke == null)
                    {
                        cinvoke = new CInvokeNode(inferredLess);
                        o.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cinvoke.toString());
                    }
                    else
                    {
                        BooleanNode andNode = new BooleanNode(BooleanNode.AND, cinvoke.getNode(), inferredLess);
                        cinvoke.setNode(andNode);
                        o.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cinvoke.toString());
                    }
                }
            }

            // Merge states
            opIt = new ArrayList(target.getOutgoingOperations()).iterator();
            while (opIt.hasNext())
            {
                Operation o = (Operation) opIt.next();
                Operation newOp = factory.createOperation(o.getName(), source, o.getTargetState(), o.getMessage());
                                

                String oCst = (String) o.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
                if (isConstraintEmpty(oCst))
                {
                    CInvokeNode cinvoke = new CInvokeNode(inferredMore);
                    newOp.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cinvoke.toString());
                }
                else
                {
                    CInvokeNode cinvoke = (CInvokeNode) parseConstraint(oCst);
                    if (cinvoke == null)
                    {
                        cinvoke = new CInvokeNode(inferredMore);
                        newOp.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cinvoke.toString());
                    }
                    else
                    {
                        BooleanNode andNode = new BooleanNode(BooleanNode.AND, cinvoke.getNode(), inferredMore);
                        cinvoke.setNode(andNode);
                        newOp.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cinvoke.toString());
                    }
                }
                
                p.removeOperation(o);
                p.addOperation(newOp);
            }
            p.removeOperation(op);
            if (target.isFinalState())
            {
                if (source instanceof StateImpl)
                {
                    StateImpl src = (StateImpl) source;
                    src.setFinalState(true);
                }
            }
            p.removeState(target);
        }
        
        // Rewrite constraints that referenced an implicit operation
        Iterator keyIt = inferredMoreMap.keySet().iterator();
        while (keyIt.hasNext())
        {
            String opName = (String) keyIt.next();
            Iterator opIt = p.getOperations().iterator();
            while (opIt.hasNext())
            {
                Operation op = (Operation) opIt.next(); 
                String oCst = (String) op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
                if (isConstraintEmpty(oCst) || !oCst.contains(opName))
                {
                    continue;
                }
                CInvokeNode cinvoke = (CInvokeNode) parseConstraint(oCst);
                if (cinvoke == null)
                {
                    continue;
                }
                ImplicitConstraintRewriteWalker walker = new ImplicitConstraintRewriteWalker();
                walker.walk(cinvoke, opName, (ComparisonNode) inferredMoreMap.get(opName));
                BooleanNode andNode = new BooleanNode(BooleanNode.AND, cinvoke.getNode(), (IRootConstraintNode) inferredMoreMap.get(opName));
                cinvoke.setNode(andNode);
                op.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cinvoke.toString());
            }
        }

        return p;
    }
    
    private class ImplicitConstraintRewriteWalker
    {
        public void walk(IConstraintNode node, String varName, ComparisonNode inferredMore)
        {
            if (node instanceof CInvokeNode)
            {
                CInvokeNode ci = (CInvokeNode) node;
                walk(ci.getNode(), varName, inferredMore);
            }
            else if (node instanceof BooleanNode)
            {
                BooleanNode bo = (BooleanNode) node;
                walk(bo.getLeftChild(), varName, inferredMore);
                walk(bo.getRightChild(), varName, inferredMore);
            }
            else if (node instanceof ComparisonNode)
            {
                ComparisonNode comp = (ComparisonNode) node;
                
                VariableNode infVar = (VariableNode) inferredMore.getLeftChild();
                ConstantNode infCst = (ConstantNode) inferredMore.getRightChild();
                
                IConstraintNode left = comp.getLeftChild();
                IConstraintNode right = comp.getRightChild();
                if (left instanceof VariableNode)
                {
                    VariableNode var = (VariableNode) left;
                    if (!var.getVariableName().equals(varName))
                    {
                        return;
                    }
                    ConstantNode cst = (ConstantNode) right;
                    var.setVariableName(infVar.getVariableName());
                    cst.setConstant(cst.getConstant() + infCst.getConstant());
                }
                else
                {
                    VariableNode var = (VariableNode) right;
                    if (!var.getVariableName().equals(varName))
                    {
                        return;
                    }
                    ConstantNode cst = (ConstantNode) left;
                    var.setVariableName(infVar.getVariableName());
                    cst.setConstant(cst.getConstant() + infCst.getConstant());
                }
            }
        }
    }

}
