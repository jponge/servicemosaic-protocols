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
