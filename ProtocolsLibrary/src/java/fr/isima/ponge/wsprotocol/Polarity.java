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
 * Defines a message polarity.
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public class Polarity implements Serializable
{

    /**
     * The positive message polarity value.
     */
    protected static final int VAL_POSITIVE = 0;

    /**
     * The negative message polarity value.
     */
    protected static final int VAL_NEGATIVE = 1;

    /**
     * The null message polarity value.
     */
    protected static final int VAL_NULL = 2;

    /**
     * The instance polarity value.
     */
    protected int polarity;

    /**
     * The positive polarity.
     */
    public static final Polarity POSITIVE = new Polarity(VAL_POSITIVE);

    /**
     * The negative polarity.
     */
    public static final Polarity NEGATIVE = new Polarity(VAL_NEGATIVE);

    /**
     * The null polarity.
     */
    public static final Polarity NULL = new Polarity(VAL_NULL);

    /**
     * Constructs a new <code>Polarity</code> instance.
     *
     * @param value The polarity value.
     */
    protected Polarity(int value)
    {
        polarity = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0)
    {
        if (arg0 != null && arg0 instanceof Polarity)
        {
            Polarity p = (Polarity) arg0;
            return p.polarity == polarity;
        }
        else
        {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return super.hashCode() + polarity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        switch (polarity)
        {
            case VAL_POSITIVE:
                return "(+)"; //$NON-NLS-1$
            case VAL_NEGATIVE:
                return "(-)"; //$NON-NLS-1$
            default:
                return "( )"; //$NON-NLS-1$
        }
    }
}
