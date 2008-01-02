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

package fr.isima.ponge.wsprotocol;

import junit.framework.TestCase;

/**
 * Test case for the <code>Polarity</code> class.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class PolarityTest extends TestCase
{

    public void testEqualsObject()
    {
        TestCase.assertEquals(Polarity.POSITIVE, Polarity.POSITIVE);
        TestCase.assertEquals(Polarity.NEGATIVE, Polarity.NEGATIVE);
        TestCase.assertEquals(Polarity.NULL, Polarity.NULL);

        TestCase.assertNotSame(Polarity.POSITIVE, Polarity.NEGATIVE);
        TestCase.assertNotSame(Polarity.POSITIVE, Polarity.NULL);
        TestCase.assertNotSame(Polarity.NEGATIVE, Polarity.NULL);
    }

    public void testToString()
    {
        TestCase.assertEquals("(+)", Polarity.POSITIVE.toString()); //$NON-NLS-1$
        TestCase.assertEquals("(-)", Polarity.NEGATIVE.toString()); //$NON-NLS-1$
        TestCase.assertEquals("( )", Polarity.NULL.toString()); //$NON-NLS-1$
    }

}
