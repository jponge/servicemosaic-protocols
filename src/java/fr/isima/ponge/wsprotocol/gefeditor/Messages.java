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

package fr.isima.ponge.wsprotocol.gefeditor;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
    private static final String BUNDLE_NAME = "fr.isima.ponge.wsprotocol.gefeditor.messages"; //$NON-NLS-1$

    private Messages()
    {
    }

    static
    {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    public static String deleteState;

    public static String addBendpoint;

    public static String moveResize;

    public static String reconnect;

    public static String createState;

    public static String deleteBendpoint;

    public static String createOperation;

    public static String moveBendpoint;

    public static String messageName;

    public static String messagePolarity;

    public static String input;

    public static String output;

    public static String operationKind;
    
    public static String implicit;
    
    public static String explicit;
    
    public static String none;

    public static String notAvailableAbbr;

    public static String snapToGrid;

    public static String showGrid;

    public static String viewMenu;

    public static String loadTimeErrorMessage;

    public static String newProtocolNameInModel;

    public static String saveTimeErrorMessage;

    public static String savingTask;

    public static String toolsPaletteGroup;

    public static String statesPaletteGroup;

    public static String initialState;

    public static String createInitialState;

    public static String normalState;

    public static String createNormalState;

    public static String finalState;

    public static String createFinalState;

    public static String operationsPaletteGroup;

    public static String inputOperation;

    public static String createInputOperation;

    public static String outputOperation;

    public static String createOutputOperation;

    public static String implicitOperation;

    public static String createImplicitOperation;

    public static String newProtocolWizardPageTitle;

    public static String newProtocolWizardPageDescription;

    public static String containerEntry;

    public static String browse;

    public static String fileNameEntry;

    public static String newProtocolFilename;

    public static String selectNewFileContainer;

    public static String fileContainerMustBeSpecified;

    public static String fileContainerMustExist;

    public static String projectMustBeWritable;

    public static String filenameMustBeSpecified;

    public static String filenameMustBeValid;

    public static String fileExtensionMustBeValid;

    public static String error;

    public static String creatingTask;

    public static String containerException1;

    public static String containerException2;

    public static String openingFileForEditingTask;

    public static String newProtocolModelName;

    public static String protocolNameProperty;

    public static String wsdlUrlProperty;

    public static String stateNameProperty;

    public static String initialStateProperty;

    public static String finalStateProperty;

    public static String alignMenuEntry;
}
