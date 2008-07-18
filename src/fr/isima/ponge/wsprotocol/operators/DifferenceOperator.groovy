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

package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*

class DifferenceOperator extends BinaryOperator
{
    private IntersectionOperator intersection;
    private ComplementationOperator complement;

    DifferenceOperator()
    {
        super()
        intersection = new IntersectionOperator()
        complement = new ComplementationOperator()
    }

    DifferenceOperator(BusinessProtocolFactory factory)
    {
        super(factory)
        intersection = new IntersectionOperator(factory)
        complement = new ComplementationOperator(factory)
    }

    @Override
    public BusinessProtocol apply(BusinessProtocol protocol1, BusinessProtocol protocol2)
    {
        def result = intersection.apply(cloneProtocol(protocol1), complement.apply(cloneProtocol(protocol2)))
        result.name = "(${protocol1.name} ||td ${protocol2.name})"
        return result
    }


}