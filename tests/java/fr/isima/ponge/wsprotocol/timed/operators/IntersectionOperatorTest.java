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
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;

public class IntersectionOperatorTest extends TestCase
{

    /*
     * Test method for
     * 'fr.isima.ponge.wsprotocol.timed.operators.IntersectionOperator.apply(BusinessProtocol,
     * BusinessProtocol)'
     */
    public void testApply() throws DocumentException
    {
        IntersectionOperator operator = new IntersectionOperator(new BusinessProtocolFactoryImpl());
        BusinessProtocol p1 = TestUtils.loadProtocol("intersection/p1.wsprotocol");
        BusinessProtocol p2 = TestUtils.loadProtocol("intersection/p2.wsprotocol");
        BusinessProtocol expected = TestUtils.loadProtocol("intersection/p1-inter-p2.wsprotocol");
        BusinessProtocol result = operator.apply(p1, p2);

        TestCase.assertEquals(expected, result);

        Operation o1 = TestUtils.getOperationNamed(result, "T1_T1");
        Operation o2 = TestUtils.getOperationNamed(expected, "T1_T1");

        TestCase.assertEquals(o2.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT), o1
                .getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT));
    }
}
