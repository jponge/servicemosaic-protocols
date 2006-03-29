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
