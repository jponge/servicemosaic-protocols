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

package fr.isima.ponge.wsprotocol.gefeditor.uiparts;


/**
 * The properties keys used to store informations in the models extra properties.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface ModelExtraPropertiesConstants
{

    /**
     * Property for a state X coordinate.
     */
    public static final String STATE_X_PROP = "gef.wsprotocol.editor.x"; //$NON-NLS-1$

    /**
     * Property for a state Y coordinate.
     */
    public static final String STATE_Y_PROP = "gef.wsprotocol.editor.y"; //$NON-NLS-1$

    /**
     * Property for a state width.
     */
    public static final String STATE_WIDTH_PROP = "gef.wsprotocol.editor.width"; //$NON-NLS-1$

    /**
     * Property for a state height.
     */
    public static final String STATE_HEIGHT_PROP = "gef.wsprotocol.editor.height"; //$NON-NLS-1$

    /**
     * Property for a list of bendpoints of an operation.
     */
    public static final String OPERATION_BENDPOINTS = "gef.wsprotocol.editor.relative.bendpoints"; //$NON-NLS-1$
    
    /**
     * Property for the protocol <code>IFile</code> instance.
     */
    public static final String PROTOCOL_IFILE_RESOURCE = "gef.wsprotocol.editor.ifile.resource"; //$NON-NLS-1$

}
