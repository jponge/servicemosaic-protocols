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

package fr.isima.ponge.wsprotocol.gefeditor.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;

/**
 * The command to chamge the graphical bounds of a state.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateSetConstraintCommand extends Command
{

    // The old bounds
    private Rectangle oldBounds;

    // The new bounds
    private Rectangle newBounds;

    // The request to change the bounds
    private ChangeBoundsRequest request;

    // The state whose bounds have to be changed
    private State state;

    /**
     * Constructs a new command to change the bounds of a state.
     * 
     * @param state
     *            The state.
     * @param request
     *            The bounds change request.
     * @param bounds
     *            The new bounds.
     */
    public StateSetConstraintCommand(State state, ChangeBoundsRequest request, Rectangle bounds)
    {
        super();

        this.request = request;
        this.state = state;
        this.newBounds = bounds.getCopy();

        setLabel(Messages.moveResize);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#canExecute()
     */
    public boolean canExecute()
    {
        Object type = request.getType();
        return (RequestConstants.REQ_MOVE.equals(type)
                || RequestConstants.REQ_MOVE_CHILDREN.equals(type)
                || RequestConstants.REQ_RESIZE.equals(type) || RequestConstants.REQ_RESIZE_CHILDREN
                .equals(type));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#canUndo()
     */
    public boolean canUndo()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#execute()
     */
    public void execute()
    {
        // Save the old bounds
        String x = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_X_PROP);
        String y = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_Y_PROP);
        String w = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_WIDTH_PROP);
        String h = (String) state.getExtraProperty(ModelExtraPropertiesConstants.STATE_HEIGHT_PROP);
        oldBounds = new Rectangle(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(w),
                Integer.parseInt(h));

        redo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo()
    {
        // Put the new bounds in the model
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_X_PROP, Integer
                .toString(newBounds.x));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_Y_PROP, Integer
                .toString(newBounds.y));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_WIDTH_PROP, Integer
                .toString(newBounds.width));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_HEIGHT_PROP, Integer
                .toString(newBounds.height));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo()
    {
        // Put the old bounds in the model
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_X_PROP, Integer
                .toString(oldBounds.x));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_Y_PROP, Integer
                .toString(oldBounds.y));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_WIDTH_PROP, Integer
                .toString(oldBounds.width));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_HEIGHT_PROP, Integer
                .toString(oldBounds.height));
    }

}
