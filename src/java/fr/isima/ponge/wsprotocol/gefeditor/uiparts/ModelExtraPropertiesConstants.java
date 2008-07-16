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
