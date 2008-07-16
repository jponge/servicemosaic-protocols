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
