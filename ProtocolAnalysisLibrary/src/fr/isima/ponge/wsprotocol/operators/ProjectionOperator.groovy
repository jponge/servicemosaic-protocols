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

/**
 * Polarity projection operator.
 *
 * @author Julien Ponge
 */
class ProjectionOperator extends BinaryOperator
{
    ProjectionOperator()
    {
        super()
    }

    ProjectionOperator(BusinessProtocolFactory factory)
    {
        super(factory)
    }

    /**
     * Projects the polarities of one protocol on the other one.
     *
     * @param protocol1 The protocol to apply the polarities on
     * @param protocol2 The protocol to take the polarities from
     */
    @Override
    public BusinessProtocol apply(BusinessProtocol protocol1, BusinessProtocol protocol2)
    {
        BusinessProtocol result = cloneProtocol(protocol1)

        def polarities = [:]
        protocol2.operations.each { Operation op -> polarities[op.message.name] = op.message.polarity }

        result.operations.each { Operation op ->
            if (polarities.containsKey(op.message.name))
            {
                op.message.polarity = polarities[op.message.name]
            }
        }

        return result
    }

}