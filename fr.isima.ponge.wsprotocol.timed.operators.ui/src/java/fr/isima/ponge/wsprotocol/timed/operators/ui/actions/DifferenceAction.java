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

package fr.isima.ponge.wsprotocol.timed.operators.ui.actions;

import org.eclipse.core.resources.IFile;

import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;
import fr.isima.ponge.wsprotocol.timed.operators.DifferenceOperator;
import fr.isima.ponge.wsprotocol.timed.operators.IOperator;

/**
 * The difference operation action.
 * @author Julien Ponge (ponge@isima.fr)
 *
 */
public class DifferenceAction extends TimedOperatorAction
{
    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.ui.actions.TimedOperatorAction#getOperator()
     */
    protected IOperator getOperator()
    {
        return new DifferenceOperator(new BusinessProtocolFactoryImpl());
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.ui.actions.TimedOperatorAction#getResultingFilename(org.eclipse.core.resources.IFile, org.eclipse.core.resources.IFile)
     */
    protected String getResultingFilename(IFile f1, IFile f2)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(f1.getName().replaceAll(".wsprotocol", "")).append("-td-").append(
                f2.getName().replaceAll(".wsprotocol", "")).append(".wsprotocol");
        return buffer.toString();
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.ui.actions.TimedOperatorAction#getFirstFileIndex()
     */
    protected int getFirstFileIndex()
    {
        return 1;
    }

    /* (non-Javadoc)
     * @see fr.isima.ponge.wsprotocol.timed.operators.ui.actions.TimedOperatorAction#getSecondFileIndex()
     */
    protected int getSecondFileIndex()
    {
        return 0;
    }

}
