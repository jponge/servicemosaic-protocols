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
