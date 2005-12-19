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
