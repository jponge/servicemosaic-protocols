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

import junit.framework.TestCase;

import org.dom4j.DocumentException;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.State;
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
    
    public void testNormalizeAndIntersectProtocols() throws DocumentException
    {
        BusinessProtocolFactoryImpl factory = new BusinessProtocolFactoryImpl();
        Normalizer normaliser = new Normalizer(factory);
        IntersectionOperator inter = new IntersectionOperator(factory);
        BusinessProtocol p2 = TestUtils.loadProtocol("normalization/p2.wsprotocol");
        BusinessProtocol p3 = TestUtils.loadProtocol("normalization/p3.wsprotocol");
        BusinessProtocol expected = TestUtils.loadProtocol("normalization/p2-inter-norm-p3.wsprotocol");
        BusinessProtocol result = inter.apply(normaliser.normalizeProtocol(p2), p3);
        
        TestCase.assertEquals(expected, result);
        
        Operation o = TestUtils.getOperationNamed(result, "T1_T1");
        TestCase.assertEquals("C-Invoke(T0_T0 < 10)", o.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT));
    }
    
    public void testComputeStatesDepth()
    {
        BusinessProtocolFactoryImpl factory = new BusinessProtocolFactoryImpl();
        BusinessProtocol p = factory.createBusinessProtocol("P");
        
        State s1 = factory.createState("s1", false);
        State s2 = factory.createState("s2", false);
        State s3 = factory.createState("s3", false);
        State s4 = factory.createState("s4", false);

        p.addState(s1);
        p.setInitialState(s1);
        p.addState(s2);
        p.addState(s3);
        p.addState(s4);
        
        p.addOperation(factory.createOperation(s1, s2, factory.createMessage("m", Polarity.POSITIVE)));
        p.addOperation(factory.createOperation(s1, s3, factory.createMessage("m", Polarity.POSITIVE)));
        p.addOperation(factory.createOperation(s3, s1, factory.createMessage("m", Polarity.POSITIVE)));
        p.addOperation(factory.createOperation(s3, s2, factory.createMessage("m", Polarity.POSITIVE)));
        p.addOperation(factory.createOperation(s2, s4, factory.createMessage("m", Polarity.POSITIVE)));
        
        Normalizer normalizer = new Normalizer(factory);
        Map depths = normalizer.computeStatesDepth(p);
        
        TestCase.assertEquals(0, ((Integer)depths.get(s1)).intValue());
        TestCase.assertEquals(1, ((Integer)depths.get(s2)).intValue());
        TestCase.assertEquals(1, ((Integer)depths.get(s3)).intValue());
        TestCase.assertEquals(2, ((Integer)depths.get(s4)).intValue());
    }

}
