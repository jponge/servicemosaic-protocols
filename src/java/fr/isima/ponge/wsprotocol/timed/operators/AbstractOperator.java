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

import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.timed.constraints.BooleanNode;
import fr.isima.ponge.wsprotocol.timed.constraints.CInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintLexer;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintParser;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintTreeWalker;

public abstract class AbstractOperator implements IOperator
{
    protected BusinessProtocolFactory factory;

    public AbstractOperator(BusinessProtocolFactory factory)
    {
        super();
        this.factory = factory;
    }

    public abstract BusinessProtocol apply(BusinessProtocol p1, BusinessProtocol p2);

    protected BusinessProtocol pruneIsolatedStates(BusinessProtocol result)
    {
        Set visited = new HashSet();
        Stack toVisit = new Stack();

        // Mark the states that are reachable from the initial state
        toVisit.push(result.getInitialState());
        while (!toVisit.isEmpty())
        {
            State s = (State) toVisit.pop();
            visited.add(s);
            Iterator it = s.getSuccessors().iterator();
            while (it.hasNext())
            {
                State succ = (State) it.next();
                if (!visited.contains(succ))
                {
                    toVisit.add(succ);
                }
            }
        }

        // Remove the others
        Iterator it = result.getStates().iterator();
        while (it.hasNext())
        {
            State s = (State) it.next();
            if (!visited.contains(s))
            {
                Iterator oit = s.getOutgoingOperations().iterator();
                while (oit.hasNext())
                {
                    Operation op = (Operation) oit.next();
                    result.removeOperation(op); // invalidates oit
                    oit = s.getOutgoingOperations().iterator();
                }
                result.removeState(s); // invalidates it
                it = result.getStates().iterator();
            }
            else if (s.getOutgoingOperations().size() == 0 && (!s.isFinalState()))
            {
                Iterator oit = s.getIncomingOperations().iterator();
                while (oit.hasNext())
                {
                    Operation op = (Operation) oit.next();
                    result.removeOperation(op);
                    oit = s.getOutgoingOperations().iterator(); // invalidates oit
                }
                result.removeState(s); // invalidates it
                it = result.getStates().iterator();
            }
        }

        // Empty protocols
        if (result.getInitialState() != null)
        {
            if (result.getInitialState().getOutgoingOperations().size() == 0)
            {
                result.removeState(result.getInitialState());
            }
        }

        return result;
    }

    protected String generateMergerStateName(State s1, State s2)
    {
        return s1.getName() + "," + s2.getName();
    }

    protected String temporalConstraintsConjunction(Operation op1, Operation op2)
    {
        String constraint1 = (String) op1
                .getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
        String constraint2 = (String) op2
                .getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
        ConstraintRewritingWalker rewritingWalker = new ConstraintRewritingWalker();

        // Simple cases
        if (isConstraintEmpty(constraint1) && isConstraintEmpty(constraint2))
        {
            return "";
        }
        else if (isConstraintEmpty(constraint1))
        {
            IConstraintNode node = parseConstraint(constraint2);
            if (node == null)
            {
                return "";
            }
            rewritingWalker.rewriteTemporaryOnRight(node);
            return node.toString();
        }
        else if (isConstraintEmpty(constraint2))
        {
            IConstraintNode node = parseConstraint(constraint1);
            if (node == null)
            {
                return "";
            }
            rewritingWalker.rewriteTemporaryOnLeft(node);
            return node.toString();
        }

        // Needs join
        String conjunction = "";
        try
        {
            CInvokeNode c1 = (CInvokeNode) parseConstraint(constraint1);
            rewritingWalker.rewriteTemporaryOnLeft(c1);

            CInvokeNode c2 = (CInvokeNode) parseConstraint(constraint2);
            rewritingWalker.rewriteTemporaryOnRight(c2);

            BooleanNode andNode = new BooleanNode(BooleanNode.AND, c1.getNode(), c2.getNode());
            CInvokeNode ciNode = new CInvokeNode(andNode);
            conjunction = ciNode.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return conjunction;
    }

    protected BusinessProtocol rewriteFinalConstraints(BusinessProtocol protocol, Map nameMappings)
    {
        ConstraintRewritingWalker walker = new ConstraintRewritingWalker();
        Iterator it = protocol.getOperations().iterator();
        while (it.hasNext())
        {
            Operation operation = (Operation) it.next();
            String constraint = (String) operation
                    .getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
            if (!isConstraintEmpty(constraint))
            {
                IConstraintNode cstNode = parseConstraint(constraint);
                if (cstNode == null)
                {
                    continue;
                }
                walker.rewriteFromMapping(cstNode, nameMappings);
                operation.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, cstNode
                        .toString());
            }
        }
        return protocol;
    }

    protected IConstraintNode parseConstraint(String constraint)
    {
        TemporalConstraintLexer lexer;
        TemporalConstraintParser parser;
        TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker();

        lexer = new TemporalConstraintLexer(new StringReader(constraint));
        parser = new TemporalConstraintParser(lexer);
        try
        {
            parser.constraint();
            return walker.constraint(parser.getAST());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    protected boolean isConstraintEmpty(String constraint)
    {
        return constraint == null || "".equals(constraint);
    }

    protected String generateMergerOperationName(Operation op1, Operation op2)
    {
        return op1.getName() + "_" + op2.getName();
    }
}
