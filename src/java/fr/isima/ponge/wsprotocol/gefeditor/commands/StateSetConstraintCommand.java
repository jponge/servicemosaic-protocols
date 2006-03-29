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

package fr.isima.ponge.wsprotocol.gefeditor.commands;

import org.eclipse.draw2d.geometry.Rectangle;
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

        this.state = state;
        this.newBounds = bounds.getCopy();

        setLabel(Messages.moveResize);
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
