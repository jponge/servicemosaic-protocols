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
import fr.isima.ponge.wsprotocol.timed.operators.IOperator;
import fr.isima.ponge.wsprotocol.timed.operators.IntersectionOperator;

public class IntersectionAction extends TimedOperatorAction
{

    protected IOperator getOperator()
    {
        return new IntersectionOperator(new BusinessProtocolFactoryImpl());
    }

    protected String getResultingFilename(IFile f1, IFile f2)
    {
        return f1.getName() + "-ti-" + f2.getName();
    }

    protected int getFirstFileIndex()
    {
        return 1;
    }

    protected int getSecondFileIndex()
    {
        return 0;
    }

}
