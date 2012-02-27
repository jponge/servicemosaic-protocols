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

/**
 * Models an operation kind.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationKind
{

    /**
     * Value for an explicit operation.
     */
    protected static final int EXPLICIT_VALUE = 1;

    /**
     * Value for an implicit operation.
     */
    protected static final int IMPLICIT_VALUE = 2;

    /**
     * The kind value.
     */
    protected int kind;

    /**
     * The explicit kind instance.
     */
    public static final OperationKind EXPLICIT = new OperationKind(EXPLICIT_VALUE);

    /**
     * The implicit kind instance.
     */
    public static final OperationKind IMPLICIT = new OperationKind(IMPLICIT_VALUE);

    /**
     * Instanciates a new operation kind.
     *
     * @param kind The kind value.
     */
    protected OperationKind(int kind)
    {
        this.kind = kind;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof OperationKind)
        {
            OperationKind kind = (OperationKind) obj;
            return kind.kind == this.kind;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return kind;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        if (kind == EXPLICIT_VALUE)
        {
            return "explicit";
        }
        else
        {
            return "implicit";
        }
    }


}
