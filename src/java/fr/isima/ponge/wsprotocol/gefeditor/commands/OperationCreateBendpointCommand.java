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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;

import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.OperationEditPart;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;

/**
 * The command to create a bendpoint on an operation.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationCreateBendpointCommand extends Command
{
    // The operation to work on
    private Operation operation;

    // The bendpoint index
    private int index;

    // Distance to start point
    private Dimension toFirst;

    // Distance to end point
    private Dimension toLast;

    /**
     * Constructs a new bendpoint creation command.
     * 
     * @param operation
     *            The operation to operate on.
     * @param index
     *            The bendpoint index.
     * @param toFirst
     *            Distance to the first point.
     * @param toLast
     *            Distance to the last point.
     */
    public OperationCreateBendpointCommand(Operation operation, int index, Dimension toFirst,
            Dimension toLast)
    {
        super();

        this.operation = operation;
        this.index = index;
        this.toFirst = toFirst;
        this.toLast = toLast;

        setLabel(Messages.addBendpoint);
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
        // Get the existing dimensions
        String encoded = (String) operation
                .getExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS);
        List dims;
        if (encoded == null)
        {
            dims = new ArrayList();
        }
        else
        {
            dims = OperationEditPart.decodeDimensionList(encoded);
        }
        dims.add(2 * index, toFirst);
        dims.add(2 * index + 1, toLast);

        // Encode it back
        operation.putExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS,
                OperationEditPart.encodeDimensionList(dims));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo()
    {
        execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo()
    {
        String encoded = (String) operation
                .getExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS);
        List dims = OperationEditPart.decodeDimensionList(encoded);
        dims.remove(2 * index);
        dims.remove(2 * index);
        operation.putExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS,
                OperationEditPart.encodeDimensionList(dims));
    }

}
