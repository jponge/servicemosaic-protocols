/* 
 * CDDL HEADER START 
 * 
 * The contents of this file are subject to the terms of the 
 * Common Development and Distribution License (the "License"). 
 * You may not use this file except in compliance with the License. 
 * 
 * You can obtain a copy of the license at LICENSE.txt 
 * or at http://www.opensource.org/licenses/cddl1.php. 
 * See the License for the specific language governing permissions 
 * and limitations under the License. 
 * 
 * When distributing Covered Code, include this CDDL HEADER in each 
 * file and include the License file at LICENSE.txt. 
 * If applicable, add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your own identifying 
 * information: Portions Copyright [yyyy] [name of copyright owner] 
 * 
 * CDDL HEADER END 
 */

/* 
* Copyright 2005, 2006 Julien Ponge. All rights reserved.
* Use is subject to license terms.
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
