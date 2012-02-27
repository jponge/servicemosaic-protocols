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

    public static String operationName;

    public static String temporalConstraint;

    public static String invalidWSDL;
}
