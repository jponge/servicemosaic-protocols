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

/**
 * A temporal constraint node.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface IConstraintNode extends Cloneable
{
    /**
     * Gets the negation of this node.
     *
     * @return The negation.
     */
    public IConstraintNode negate();

    /**
     * Return a deep copy of the node (hence it includes its children).
     * We opted for this mechanism instead of Object#clone() since it is more involving (checked exceptions, casts, ...).
     *
     * @return The deep copy.
     */
    public IConstraintNode deepCopy();

    public void replaceChildWith(IConstraintNode oldChild, IConstraintNode newChild);
}
