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

package fr.isima.ponge.wsprotocol.gefeditor.editparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.State;

/**
 * The edit part factory for the outline view supporting the GEF editor.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class BusinessProtocolTreeEditPartFactory implements EditPartFactory
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
            part = new BusinessProtocolTreeEditPart();
        }
        else if (model instanceof State)
        {
            part = new StateTreeEditPart();
        }
        else if (model instanceof Operation)
        {
            part = new OperationTreeEditPart();
        }
        else
        {
            return null;
        }
        part.setModel(model);
        return part;
    }

}
