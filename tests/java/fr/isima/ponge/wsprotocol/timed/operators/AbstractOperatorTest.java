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
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;
import fr.isima.ponge.wsprotocol.impl.OperationImpl;

public class AbstractOperatorTest extends TestCase
{
    private MockOperator operator = new MockOperator(new BusinessProtocolFactoryImpl());

    class MockOperator extends AbstractOperator
    {
        public MockOperator(BusinessProtocolFactory factory)
        {
            super(factory);
        }

        public BusinessProtocol apply(BusinessProtocol p1, BusinessProtocol p2)
        {
            return null;
        }
    }

    /*
     * Test method for 'fr.isima.ponge.wsprotocol.timed.operators.AbstractOperator.pruneIsolatedStates(BusinessProtocol)'
     */
    public void testPruneIsolatedStates() throws DocumentException
    {
        BusinessProtocol emptyProtocol = TestUtils.loadProtocol("abstractoperator/empty.wsprotocol");
        
        operator.pruneIsolatedStates(emptyProtocol);
        TestCase.assertEquals(0, emptyProtocol.getStates().size());
        TestCase.assertEquals(0, emptyProtocol.getOperations().size());
        
        BusinessProtocol p1 = TestUtils.loadProtocol("abstractoperator/p1.wsprotocol");
        BusinessProtocol p1_expected = TestUtils.loadProtocol("abstractoperator/p1-expected.wsprotocol");
        operator.pruneIsolatedStates(p1);
        TestCase.assertEquals(p1_expected, p1);
    }
    
    public void testTemporalConstraintsConjunction()
    {
        OperationImpl o = new OperationImpl("T", null, null, null);
        
        OperationImpl o1 = new OperationImpl("T", null, null, null);
        o1.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, "C-Invoke(T1 < 3)");
        
        OperationImpl o2 = new OperationImpl("T", null, null, null);
        o2.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, "C-Invoke(T2 >= 5)");
        
        TestCase.assertEquals("", operator.temporalConstraintsConjunction(o, o));
        TestCase.assertEquals("C-Invoke(_T1 < 3)", operator.temporalConstraintsConjunction(o, o1));
        TestCase.assertEquals("C-Invoke(T1_ < 3)", operator.temporalConstraintsConjunction(o1, o));
        TestCase.assertEquals("C-Invoke((T1_ < 3) && (_T2 >= 5))", operator.temporalConstraintsConjunction(o1, o2));
    }

}
