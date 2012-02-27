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


package fr.isima.ponge.wsprotocol;

import java.io.Serializable;

/**
 * Defines an interface for a protocol operation.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface Operation extends ExtraPropertiesKeeper, Serializable
{
    /**
     * Gets the operation name;
     *
     * @return The operation name;
     */
    public String getName();

    /**
     * Gets the operation message.
     *
     * @return The operation message.
     */
    public Message getMessage();

    /**
     * Gets the operation source state.
     *
     * @return The source state.
     */
    public State getSourceState();

    /**
     * Gets the operation target state.
     *
     * @return The target state.
     */
    public State getTargetState();

    /**
     * Gets the operation kind.
     *
     * @return The operation kind.
     */
    public OperationKind getOperationKind();

}
