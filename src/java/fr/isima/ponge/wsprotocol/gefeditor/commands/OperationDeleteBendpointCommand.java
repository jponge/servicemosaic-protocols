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
 * The command to delete an operation bendpoint.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class OperationDeleteBendpointCommand extends Command
{
    // The operation to operate on
    Operation operation;

    // The bendpoint index
    int index;

    // Distance to the first point
    Dimension toFirst;

    // Distance to the last point
    Dimension toLast;

    /**
     * Constructs a new operation to delete a bendpoint.
     * 
     * @param operation
     *            The operation to work on.
     * @param index
     *            The bendpoint index.
     */
    public OperationDeleteBendpointCommand(Operation operation, int index)
    {
        super();
        this.operation = operation;
        this.index = index;

        setLabel(Messages.deleteBendpoint);
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
     * Gets the bendpoints distances.
     */
    private List getDimensions()
    {
        String encoded = (String) operation
                .getExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS);
        return OperationEditPart.decodeDimensionList(encoded);
    }

    /*
     * Sets the bendpoints distances.
     */
    private void setDimensions(List points)
    {
        operation.putExtraProperty(ModelExtraPropertiesConstants.OPERATION_BENDPOINTS,
                OperationEditPart.encodeDimensionList(points));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#execute()
     */
    public void execute()
    {
        toFirst = (Dimension) getDimensions().get(2 * index);
        toLast = (Dimension) getDimensions().get(2 * index + 1);
        redo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo()
    {
        List dims = getDimensions();
        dims.remove(2 * index);
        dims.remove(2 * index);
        setDimensions(dims);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo()
    {
        List dims = getDimensions();
        dims.add(2 * index, toFirst);
        dims.add(2 * index + 1, toLast);
        setDimensions(dims);
    }

}
