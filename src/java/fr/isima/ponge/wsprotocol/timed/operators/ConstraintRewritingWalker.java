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

import java.util.Map;

import fr.isima.ponge.wsprotocol.timed.constraints.CInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode;
import fr.isima.ponge.wsprotocol.timed.constraints.IRootConstraintNode;
import fr.isima.ponge.wsprotocol.timed.constraints.MInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.VariableNode;

public class ConstraintRewritingWalker
{
    public void rewriteTemporaryOnLeft(IConstraintNode node)
    {
        rewriteTemporary(node, true);
    }
    
    public void rewriteTemporaryOnRight(IConstraintNode node)
    {
        rewriteTemporary(node, false);
    }
    
    public void rewriteFromMapping(IConstraintNode node, Map mappings)
    {
        if (node instanceof CInvokeNode)
        {       
            CInvokeNode ci = (CInvokeNode) node;
            rewriteFromMapping(ci.getNode(), mappings);
        }
        else if (node instanceof MInvokeNode)
        {       
            MInvokeNode mi = (MInvokeNode) node;
            rewriteFromMapping(mi.getNode(), mappings);
        }
        else if (node instanceof IRootConstraintNode)
        {
            IRootConstraintNode root = (IRootConstraintNode) node;
            rewriteFromMapping(root.getLeftChild(), mappings);
            rewriteFromMapping(root.getRightChild(), mappings);
        }
        else if (node instanceof VariableNode)
        {
            VariableNode var = (VariableNode) node;
            var.setVariableName((String) mappings.get(var.getVariableName()));
        }
    }
    
    private void rewriteTemporary(IConstraintNode node, boolean onLeft)
    {
        if (node instanceof CInvokeNode)
        {       
            CInvokeNode ci = (CInvokeNode) node;
            rewriteTemporary(ci.getNode(), onLeft);
        }
        else if (node instanceof MInvokeNode)
        {       
            MInvokeNode mi = (MInvokeNode) node;
            rewriteTemporary(mi.getNode(), onLeft);
        }
        else if (node instanceof IRootConstraintNode)
        {
            IRootConstraintNode root = (IRootConstraintNode) node;
            rewriteTemporary(root.getLeftChild(), onLeft);
            rewriteTemporary(root.getRightChild(), onLeft);
        }
        else if (node instanceof VariableNode)
        {
            VariableNode var = (VariableNode) node;
            String name = var.getVariableName();
            if (onLeft)
            {
                name = name + "_";                
            }
            else
            {
                name = "_" + name;
            }
            var.setVariableName(name);
        }
    }
}
