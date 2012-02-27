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

package fr.isima.ponge.wsprotocol.analysis

import fr.isima.ponge.wsprotocol.*
import fr.isima.ponge.wsprotocol.operators.*

/**
 * This class leverages the protocol operator to implement the full range of protocol
 * compatibility and replaceability analysis.
 *
 * @author Julien Ponge
 */
class ProtocolAnalysis extends Operator
{
    ComplementationOperator complementationOperator = new ComplementationOperator()

    CompositionOperator compositionOperator = new CompositionOperator()

    DifferenceOperator differenceOperator = new DifferenceOperator()

    EmptinessOperator emptinessOperator = new EmptinessOperator()

    IntersectionOperator intersectionOperator = new IntersectionOperator()

    ProjectionOperator projectionOperator = new ProjectionOperator()

    boolean isPartiallyCompatible(BusinessProtocol p1, BusinessProtocol p2)
    {
        return !emptinessOperator.isEmpty(compositionOperator.apply(p1, p2))        
    }

    boolean isFullyCompatible(BusinessProtocol p1, BusinessProtocol p2)
    {
        BusinessProtocol temp = projectionOperator(compositionOperator.apply(p1, p2), p1)
        temp.name = p1.name
        return temp.equals(p1)        
    }

    boolean isReplaceable(BusinessProtocol p1, BusinessProtocol p2)
    {
        BusinessProtocol temp = intersectionOperator.apply(complementationOperator.apply(p1), p2)
        return emptinessOperator.isEmpty(temp)
    }

    boolean isEquivalent(BusinessProtocol p1, BusinessProtocol p2)
    {
        BusinessProtocol cloneP1 = cloneProtocol(p1)
        cloneP1.name = p2.name
        return cloneP1.equals(p2)
    }

    boolean isReplaceableWithClientProtocol(BusinessProtocol p1, BusinessProtocol p2, BusinessProtocol pc)
    {
        BusinessProtocol diff = differenceOperator.apply(p2, p1)
        BusinessProtocol comp = compositionOperator.apply(pc, diff)
        return emptinessOperator.isEmpty(comp)
    }

    boolean isReplaceableWithRole(BusinessProtocol p1, BusinessProtocol p2, BusinessProtocol pr)
    {
        BusinessProtocol compl = complementationOperator.apply(p1)
        BusinessProtocol inter = intersectionOperator.apply(pr, p2)
        return emptinessOperator.isEmpty(intersectionOperator.apply(inter, compl))
    }
}