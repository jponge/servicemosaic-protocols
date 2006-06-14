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

public class NormalizerTest extends TestCase
{

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.operators.Normaliser.normalizeProtocol(BusinessProtocol)'
     */
    public void testNormalizeProtocol() throws DocumentException
    {
        Normalizer normaliser = new Normalizer(new BusinessProtocolFactoryImpl());
        BusinessProtocol p1 = TestUtils.loadProtocol("normalization/p1.wsprotocol");
        BusinessProtocol expected = TestUtils.loadProtocol("normalization/norm-p1.wsprotocol");
        BusinessProtocol result = normaliser.normalizeProtocol(p1);
        
        TestCase.assertEquals(expected, result);
        
        Operation o = TestUtils.getOperationNamed(result, "T1");
        TestCase.assertEquals("C-Invoke(T0 < 10)", o.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT));
        
        o = TestUtils.getOperationNamed(result, "T2");
        TestCase.assertEquals("C-Invoke(((T0 < 15) && (T0 >= 10)) && (T0 >= 10))", o.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT));
        
        BusinessProtocol p2 = TestUtils.loadProtocol("normalization/p2.wsprotocol");
        expected = TestUtils.loadProtocol("normalization/norm-p2.wsprotocol");
        result = normaliser.normalizeProtocol(p2);

        TestCase.assertEquals(expected, result);
        
        o = TestUtils.getOperationNamed(result, "T1");
        TestCase.assertEquals("C-Invoke(T0 < 10)", o.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT));
    }

}
