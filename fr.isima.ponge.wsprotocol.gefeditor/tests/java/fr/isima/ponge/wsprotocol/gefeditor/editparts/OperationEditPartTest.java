/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Universite Blaise Pascal, LIMOS, Clermont-Ferrand, France.
 * Copyright 2005-2008 The University of New South Wales, Sydney, Australia.
 * 
 * This file is part of ServiceMosaic Protocols <http://servicemosaic.isima.fr/>.
 * 
 * ServiceMosaic Protocols is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ServiceMosaic Protocols is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with ServiceMosaic Protocols.  If not, see <http://www.gnu.org/licenses/>.
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
