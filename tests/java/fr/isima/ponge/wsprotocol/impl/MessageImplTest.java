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

import fr.isima.ponge.wsprotocol.Polarity;
import junit.framework.TestCase;

/**
 * Test case for the <code>MessageImpl</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class MessageImplTest extends TestCase
{

    public void testEqualsObject()
    {
        MessageImpl m1 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        MessageImpl m2 = new MessageImpl("a", Polarity.NEGATIVE); //$NON-NLS-1$
        MessageImpl m3 = new MessageImpl("a", Polarity.POSITIVE); //$NON-NLS-1$
        MessageImpl m4 = new MessageImpl("b", Polarity.POSITIVE); //$NON-NLS-1$

        TestCase.assertEquals(m1, m3);
        TestCase.assertNotSame(m1, m2);
        TestCase.assertNotSame(m1, m4);
    }

    public void testToString()
    {
        MessageImpl m = new MessageImpl("message", Polarity.POSITIVE); //$NON-NLS-1$
        TestCase.assertEquals("[message](+)", m.toString()); //$NON-NLS-1$
    }

}
