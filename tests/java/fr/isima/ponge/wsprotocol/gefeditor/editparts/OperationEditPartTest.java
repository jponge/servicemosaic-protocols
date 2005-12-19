/*
 * Copyright (c) 2005 Julien Ponge - All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
