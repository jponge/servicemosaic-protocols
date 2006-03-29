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

package fr.isima.ponge.wsprotocol.gefeditor.editparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.draw2d.geometry.Dimension;

/**
 * Test case for some special methods of <code>OperationEditPart</code>.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationEditPartTest extends TestCase
{

    /**
     * Test method for
     * 'fr.isima.ponge.wsprotocol.gefeditor.editparts.OperationEditPart.encodeDimensionList(List)'
     */
    public void testEncodeDimensionList()
    {
        List dims = new ArrayList();
        TestCase.assertEquals("", OperationEditPart.encodeDimensionList(dims)); //$NON-NLS-1$

        dims.add(new Dimension(1, 2));
        TestCase.assertEquals("1:2", OperationEditPart.encodeDimensionList(dims)); //$NON-NLS-1$

        dims.add(new Dimension(3, 4));
        TestCase.assertEquals("1:2:3:4", OperationEditPart.encodeDimensionList(dims)); //$NON-NLS-1$
    }

    /**
     * Test method for
     * 'fr.isima.ponge.wsprotocol.gefeditor.editparts.OperationEditPart.decodeDimensionList(List)'
     */
    public void testDecodeDimensionList()
    {
        List dims = new ArrayList();
        TestCase.assertEquals(Collections.EMPTY_LIST, OperationEditPart.decodeDimensionList("")); //$NON-NLS-1$

        dims.add(new Dimension(1, 2));
        TestCase.assertEquals(dims, OperationEditPart.decodeDimensionList("1:2")); //$NON-NLS-1$

        dims.add(new Dimension(3, 4));
        TestCase.assertEquals(dims, OperationEditPart.decodeDimensionList("1:2:3:4")); //$NON-NLS-1$
    }

}
