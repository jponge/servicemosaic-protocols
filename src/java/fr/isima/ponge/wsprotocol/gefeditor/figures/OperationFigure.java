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

package fr.isima.ponge.wsprotocol.gefeditor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Color;

/**
 * The figure for representing an operation.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationFigure extends PolylineConnection
{

    /**
     * Constant for input operations.
     */
    public static final int INPUT = 0;

    /**
     * Constant for output operations.
     */
    public static final int OUTPUT = 1;

    /**
     * Constant for implicit operations.
     */
    public static final int IMPLICIT = 2;
    
    /**
     * Constant for unpolarized explicit operations.
     */
    public static final int UNPOLARIZED = 3;

    /**
     * The color used for input operations.
     */
    protected static Color inputColor;

    /**
     * The color used for output operations.
     */
    protected static Color outputColor;

    /**
     * The color used for implicit operations.
     */
    protected static Color implicitColor;
    
    /**
     * The color used for null-polarized explicit operations.
     */
    protected static Color noneColor;

    /**
     * The operation current type.
     */
    protected int type = INPUT;

    /**
     * The operation current label.
     */
    protected String text;

    /**
     * The decoration on the target side.
     */
    protected RotatableDecoration targetDecoration;

    /**
     * The label used to display some informations.
     */
    protected Label label;

    /**
     * The current color used to draw the operation.
     */
    protected Color color;

    /**
     * Constructs a new operation figure.
     * 
     * @param type
     *            The operation type.
     * @param text
     *            The operation text.
     */
    public OperationFigure(int type, String text)
    {
        super();

        // UI
        targetDecoration = getTargetDecoration();
        setTargetDecoration(targetDecoration);
        setLineWidth(2);
        label = new Label();
        label.setOpaque(true);
        MidpointLocator locator = new MidpointLocator(this, 0);
        add(label, locator);

        // Colors
        if (inputColor == null)
        {
            inputColor = ColorConstants.blue;
            outputColor = ColorConstants.red;
            noneColor = ColorConstants.black;
            implicitColor = ColorConstants.gray;
        }

        // Data
        setType(type);
        setText(text);
    }

    /**
     * Returns the operation type.
     * 
     * @return The operation type.
     */
    public int getType()
    {
        return type;
    }

    /**
     * Sets the operation type.
     * 
     * @param type
     *            The new operation type.
     */
    public void setType(int type)
    {
        if (type < INPUT || type > UNPOLARIZED)
        {
            return;
        }
        this.type = type;

        switch (type)
        {
        case INPUT:
            setForegroundColor(inputColor);
            setLineStyle(Graphics.LINE_SOLID);
            break;
        case OUTPUT:
            setForegroundColor(outputColor);
            setLineStyle(Graphics.LINE_SOLID);
            break;
        case IMPLICIT:
            setForegroundColor(implicitColor);
            setLineStyle(Graphics.LINE_DASH);
            break;
        case UNPOLARIZED:
            setForegroundColor(noneColor);
            setLineStyle(Graphics.LINE_SOLID);
            break;
        default:
            break;
        }
    }

    /**
     * Gets the current displayed text.
     * 
     * @return The operation text.
     */
    public String getText()
    {
        return text;
    }

    /**
     * Changes the displayed operation label text.
     * 
     * @param text
     *            The new text.
     */
    public void setText(String text)
    {
        this.text = text;
        label.setText(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.PolylineConnection#getTargetDecoration()
     */
    protected RotatableDecoration getTargetDecoration()
    {
        if (targetDecoration == null)
        {
            PointList points = new PointList();
            points.addPoint(-2, 2);
            points.addPoint(0, 0);
            points.addPoint(-2, -2);
            targetDecoration = new PolygonDecoration();
            ((PolygonDecoration) targetDecoration).setTemplate(points);
        }
        return targetDecoration;
    }

}
