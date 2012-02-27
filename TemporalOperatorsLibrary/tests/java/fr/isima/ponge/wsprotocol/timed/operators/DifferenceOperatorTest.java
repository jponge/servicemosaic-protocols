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

import junit.framework.TestCase;

import org.dom4j.DocumentException;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;

public class DifferenceOperatorTest extends TestCase
{

    private DifferenceOperator operator = new DifferenceOperator(new BusinessProtocolFactoryImpl());

    public void testApply() throws DocumentException
    {
        BusinessProtocol p1 = TestUtils.loadProtocol("difference/p1.wsprotocol");
        BusinessProtocol p2 = TestUtils.loadProtocol("difference/p2.wsprotocol");
        BusinessProtocol result = operator.apply(p2, p1);
        BusinessProtocol expected = TestUtils.loadProtocol("difference/p2-diff-p1.wsprotocol");
                
        TestCase.assertEquals(expected, result);
    }
    
    public void testComputeComplement() throws DocumentException
    {
        BusinessProtocol p1 = TestUtils.loadProtocol("difference/p1.wsprotocol");
        BusinessProtocol expected = TestUtils.loadProtocol("difference/compl-p1.wsprotocol");
        BusinessProtocol result = operator.computeComplement(p1);
        
        TestCase.assertEquals(expected, result);
    }
}
