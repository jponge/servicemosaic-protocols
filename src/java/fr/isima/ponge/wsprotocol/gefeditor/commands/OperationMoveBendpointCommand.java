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
