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

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;

import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.OperationEditPart;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;

/**
 * The operation to move a bendpoint.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationMoveBendpointCommand extends Command
{

    // The operation
    Operation operation;

    // The bendpoint index
    int index;

    // Previous distance to the first point
    Dimension oldToFirst;

    // Previous distance to the last point
    Dimension oldToLast;

    // New distance to the first point
    Dimension newToFirst;

    // New distance to the last point
    Dimension newToLast;

    /**
     * Constructs a new command to move a bendpoint.
     * 
     * @param operation
     *            The operation to work on.
     * @param index
     *            The bendpoint index.
     * @param toFirst
     *            New distance to the first point.
     * @param toLast
     *            New distance to the last point.
     */
    public OperationMoveBendpointCommand(Operation operation, int index, Dimension toFirst,
            Dimension toLast)
    {
        super();
        this.operation = operation;
        this.index = index;
        this.newToFirst = toFirst;
        this.newToLast = toLast;

        setLabel(Messages.moveBendpoint);
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
        oldToFirst = (Dimension) getDimensions().get(2 * index);
        oldToLast = (Dimension) getDimensions().get(2 * index + 1);
        redo();
    }

    /*
     * Returns the new points distances list.
     */
    private List getDimensions()
    {
        String encoded = (String) operation
                .getExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS);
        return OperationEditPart.decodeDimensionList(encoded);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo()
    {
        List dims = getDimensions();
        dims.set(2 * index, newToFirst);
        dims.set(2 * index + 1, newToLast);
        setDimensions(dims);
    }

    /*
     * Sets the new points distances list.
     */
    private void setDimensions(List points)
    {
        operation.putExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS,
                OperationEditPart.encodeDimensionList(points));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo()
    {
        List dims = getDimensions();
        dims.set(2 * index, oldToFirst);
        dims.set(2 * index + 1, oldToLast);
        setDimensions(dims);
    }

}
