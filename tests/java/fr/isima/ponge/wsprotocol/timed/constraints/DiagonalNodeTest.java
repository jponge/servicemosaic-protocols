/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Université Blaise Pascal, LIMOS, Clermont-Ferrand, France.
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

package fr.isima.ponge.wsprotocol.timed.constraints;

import junit.framework.TestCase;

public class DiagonalNodeTest extends TestCase
{
    public void testEquals()
    {
        DiagonalNode d1 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));
        DiagonalNode d2 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));
        DiagonalNode d3 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));

        assertEquals(d1, d2);
        assertNotSame(d1, d3);
    }

    public void testToString()
    {
        DiagonalNode d1 = new DiagonalNode(
                new DiagonalVariablesPair(new VariableNode("T1"), new VariableNode("T2")),
                ComparisonConstants.LESS,
                new ConstantNode(10));

        assertEquals("(T1 - T2 < 10)", d1.toString());
        assertEquals("(T1 - T2 >= 10)", d1.negate().toString());
    }
}
