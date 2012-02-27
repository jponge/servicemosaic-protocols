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

package fr.isima.ponge.wsprotocol.gefeditor.editparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.State;

/**
 * The edit part factory for the GEF editor.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolEditPartFactory implements EditPartFactory
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
     *      java.lang.Object)
     */
    public EditPart createEditPart(EditPart context, Object model)
    {
        EditPart part;
        if (model instanceof BusinessProtocol)
        {
            part = new BusinessProtocolEditPart();
        }
        else if (model instanceof State)
        {
            part = new StateEditPart();
        }
        else if (model instanceof Operation)
        {
            part = new OperationEditPart();
        }
        else
        {
            return null;
        }
        part.setModel(model);
        return part;
    }

}
