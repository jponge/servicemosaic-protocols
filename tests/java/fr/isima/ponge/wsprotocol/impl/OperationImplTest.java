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
 * Copyright 2005, 2006 Julien Ponge. All rights reserved. 
 * Use is subject to license terms. 
 */ 

package fr.isima.ponge.wsprotocol.impl;

import junit.framework.TestCase;
import fr.isima.ponge.wsprotocol.Message;
import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;

/**
 * Test case for the <code>OperationImpl</code> class.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationImplTest extends TestCase
{

    public void testEqualsObject()
    {
        State s1 = new StateImpl("s1", false); //$NON-NLS-1$
        State s2 = new StateImpl("s2", false); //$NON-NLS-1$
        State s3 = new StateImpl("s3", false); //$NON-NLS-1$
        State s4 = new StateImpl("s4", false); //$NON-NLS-1$
        Message m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        Message m2 = new MessageImpl("a", Polarity.NEGATIVE); //$NON-NLS-1$

        OperationImpl o1 = new OperationImpl("T1", s1, s2, m1);
        OperationImpl o2 = new OperationImpl("T2", s3, s4, m2);

        TestCase.assertEquals(o1, o1);
        TestCase.assertNotSame(o1, o2);
    }

    public void testToString()
    {
        State s1 = new StateImpl("s1", false); //$NON-NLS-1$
        State s2 = new StateImpl("s2", false); //$NON-NLS-1$
        Message m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        OperationImpl o1 = new OperationImpl("T1", s1, s2, m1);

        TestCase.assertEquals("T1: ((s1),[a](+),(s2),explicit)", o1.toString()); //$NON-NLS-1$
    }

}
