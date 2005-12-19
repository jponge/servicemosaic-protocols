/*
 * Copyright (c) 2005 Julien Ponge - All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

    /** The positive message polarity value. */
    protected static final int VAL_POSITIVE = 0;

    /** The negative message polarity value. */
    protected static final int VAL_NEGATIVE = 1;

    /** The null message polarity value. */
    protected static final int VAL_NULL = 2;

    /** The instance polarity value. */
    protected int polarity;

    /** The positive polarity. */
    public static final Polarity POSITIVE = new Polarity(VAL_POSITIVE);

    /** The negative polarity. */
    public static final Polarity NEGATIVE = new Polarity(VAL_NEGATIVE);

    /** The null polarity. */
    public static final Polarity NULL = new Polarity(VAL_NULL);

    /**
     * Constructs a new <code>Polarity</code> instance.
     * 
     * @param value
     *            The polarity value.
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
