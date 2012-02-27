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

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;

/**
 * The command to create a new state.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class StateCreateCommand extends Command
{
    // The state
    private State state;

    // The previous initial state, used if state is initial
    private State oldInitialState;

    // The protocol
    private BusinessProtocol protocol;

    // The bounds given graphically
    private Rectangle bounds;

    /**
     * Constructs a new command to create a state.
     * 
     * @param state
     *            The state to create.
     * @param protocol
     *            The protocol which the state belongs to.
     * @param bounds
     *            The state graphical bounds.
     */
    public StateCreateCommand(State state, BusinessProtocol protocol, Rectangle bounds)
    {
        super();
        this.state = state;
        this.protocol = protocol;
        this.bounds = bounds;

        setLabel(Messages.createState);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#canExecute()
     */
    public boolean canExecute()
    {
        return true;
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
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_X_PROP, Integer
                .toString(bounds.x));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_Y_PROP, Integer
                .toString(bounds.y));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_WIDTH_PROP, Integer
                .toString(bounds.width));
        state.putExtraProperty(ModelExtraPropertiesConstants.STATE_HEIGHT_PROP, Integer
                .toString(bounds.height));
        if (state.isInitialState())
        {
            oldInitialState = protocol.getInitialState();
        }
        redo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo()
    {
        protocol.addState(state);
        if (state.isInitialState())
        {
            protocol.setInitialState(state);
            if (oldInitialState != null)
            {
                oldInitialState.setInitialState(false);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo()
    {
        protocol.removeState(state);
        if (state.isInitialState())
        {
            protocol.setInitialState(oldInitialState);
            if (oldInitialState != null)
            {
                oldInitialState.setInitialState(true);
            }
        }
    }

}
