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

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

import fr.isima.ponge.wsprotocol.gefeditor.Messages;

/**
 * The figure to represent a state.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateFigure extends Figure
{

    /**
     * The label.
     */
    protected Label label = new Label();

    /**
     * The current foreground color.
     */
    protected Color fgColor;

    /**
     * The current background color.
     */
    protected Color bgColor;

    /**
     * Wether to display a single border (true) or a double border (false).
     */
    protected boolean singleBorder = true;

    /**
     * The initial foreground color.
     */
    protected static Color initialFgColor;

    /**
     * The initial background color.
     */
    protected static Color initialBgColor;

    /**
     * The normal foreground color.
     */
    protected static Color normalFgColor;

    /**
     * The normal background color.
     */
    protected static Color normalBgColor;

    /**
     * The final foreground color.
     */
    protected static Color finalFgColor;

    /**
     * The final background color.
     */
    protected static Color finalBgColor;

    /**
     * The shadows (or sort of ...) color.
     */
    protected static Color shadowColor;

    /**
     * The state text.
     */
    protected String text;

    /**
     * Is the state initial ?
     */
    protected boolean initialState = false;

    /**
     * Is the state final ?
     */
    protected boolean finalState = false;

    /**
     * Constructs a new state figure.
     * 
     * @param text
     *            The text to display on the state figure.
     * @param initialState
     *            Wether this is an initial state or not.
     * @param finalState
     *            Wether this is a final state or not.
     */
    public StateFigure(String text, boolean initialState, boolean finalState)
    {
        super();

        // Add the label
        BorderLayout layout = new BorderLayout();
        layout.setHorizontalSpacing(4);
        layout.setVerticalSpacing(4);
        setLayoutManager(layout);
        label.setForegroundColor(ColorConstants.black);
        add(label, BorderLayout.CENTER);

        // Lazily instanciate the shared colors
        if (initialFgColor == null)
        {
            Device device = Display.getCurrent();
            initialFgColor = new Color(device, 170, 170, 170);
            initialBgColor = new Color(device, 250, 250, 250);
            normalFgColor = new Color(device, 175, 175, 149);
            normalBgColor = new Color(device, 255, 255, 229);
            finalFgColor = new Color(device, 149, 175, 151);
            finalBgColor = new Color(device, 229, 255, 231);
            shadowColor = new Color(device, 230, 230, 230);
        }

        // Init
        setText(text);
        setInitialState(initialState);
        setFinalState(finalState);
    }

    /**
     * Default constructor for a normal state with "N/A" as the label.
     */
    public StateFigure()
    {
        this(Messages.notAvailableAbbr, false, false);
    }

    /**
     * Returns wether this is displayed as a final state or not.
     * 
     * @return Wether this is a final state or not.
     */
    public boolean isFinalState()
    {
        return finalState;
    }

    /**
     * Changes the final state status.
     * 
     * @param finalState
     *            The new status.
     */
    public void setFinalState(boolean finalState)
    {
        this.finalState = finalState;
        updateContext();
    }

    /**
     * Returns wether this is displayed as an initial state or not.
     * 
     * @return Wether this is an initial state or not.
     */
    public boolean isInitialState()
    {
        return initialState;
    }

    /**
     * Changes the initial state status.
     * 
     * @param initialState
     *            The new status.
     */
    public void setInitialState(boolean initialState)
    {
        this.initialState = initialState;
        updateContext();
    }

    /**
     * Returns the displayed text.
     * 
     * @return The text.
     */
    public String getText()
    {
        return text;
    }

    /**
     * Changes the displayed text.
     * 
     * @param text
     *            The new text to display.
     */
    public void setText(String text)
    {
        this.text = text;
        updateContext();
    }

    /**
     * The connection anchor of the state figure.
     * 
     * @return The anchor.
     */
    public ConnectionAnchor getAnchor()
    {
        return new ChopboxAnchor(this) {
            protected Rectangle getBox()
            {
                Rectangle base = super.getBox();
                return base.getResized(-4, -4).getTranslated(4, 4);
            }
        };
    }

    /*
     * Updates the figure context to the model values.
     */
    private void updateContext()
    {
        // Border
        singleBorder = !isFinalState();

        // Colors
        if (isInitialState())
        {
            fgColor = initialFgColor;
            bgColor = initialBgColor;
        }
        else if (isFinalState())
        {
            fgColor = finalFgColor;
            bgColor = finalBgColor;
        }
        else
        {
            fgColor = normalFgColor;
            bgColor = normalBgColor;
        }

        // Text
        label.setText(getText());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics graphics)
    {
        // Inits
        super.paintFigure(graphics);
        Rectangle bounds = getBounds().getCopy().resize(-9, -9).translate(4, 4);
        final int round = 25;
        final int sround = 30;

        // Shadow / experimental
        graphics.setBackgroundColor(shadowColor);
        graphics.fillRoundRectangle(bounds.getTranslated(4, 4), sround, sround);

        // Drawings
        graphics.setForegroundColor(fgColor);
        graphics.setBackgroundColor(bgColor);
        graphics.fillRoundRectangle(bounds, round, round);
        graphics.drawRoundRectangle(bounds, round, round);
        if (!singleBorder)
        {
            bounds.expand(-4, -4);
            graphics.drawRoundRectangle(bounds, round, round);
        }

        // Cleanups
        graphics.restoreState();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.IFigure#getMaximumSize()
     */
    public Dimension getMaximumSize()
    {
        return label.getMaximumSize().getExpanded(10, 10);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.IFigure#getMinimumSize(int, int)
     */
    public Dimension getMinimumSize(int w, int h)
    {
        return label.getMinimumSize(w, h).getExpanded(10, 10);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.IFigure#getPreferredSize(int, int)
     */
    public Dimension getPreferredSize(int wHint, int hHint)
    {
        return label.getPreferredSize(wHint, hHint).getExpanded(10, 10);
    }

}
