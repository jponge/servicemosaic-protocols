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

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;

import fr.isima.ponge.wsprotocol.Polarity;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.EditorPlugin;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;

/**
 * The protocol editor palette factory.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ProtocolEditorPaletteFactory
{
    /** Preference ID used to persist the palette location. */
    protected static final String PALETTE_DOCK_LOCATION = "WS-ProtocolEditorPaletteFactory.Location"; //$NON-NLS-1$

    /** Preference ID used to persist the palette size. */
    protected static final String PALETTE_SIZE = "WS-ProtocolEditorPaletteFactory.Size"; //$NON-NLS-1$

    /** Preference ID used to persist the flyout palette's state. */
    protected static final String PALETTE_STATE = "WS-ProtocolEditorPaletteFactory.State"; //$NON-NLS-1$

    /**
     * Returns the palette.
     * 
     * @return The palette.
     */
    public static PaletteRoot createPalette()
    {
        PaletteRoot palette = new PaletteRoot();
        palette.add(createBasicToolsGroup(palette));
        palette.add(createStatesDrawer(palette));
        palette.add(createOperationsDrawer(palette));
        return palette;
    }

    /**
     * Creates the basic tools group.
     * 
     * @param palette
     *            The palette to add the tools to.
     * @return The tools.
     */
    protected static PaletteContainer createBasicToolsGroup(PaletteRoot palette)
    {
        // Basic tools
        PaletteDrawer drawer = new PaletteDrawer(Messages.toolsPaletteGroup);

        // Add a selection tool to the group
        ToolEntry tool = new PanningSelectionToolEntry();
        drawer.add(tool);
        palette.setDefaultEntry(tool);

        // Add a marquee tool to the group
        drawer.add(new MarqueeToolEntry());

        return drawer;
    }

    /**
     * Creates the states drawer group.
     * 
     * @param palette
     *            The palette to add the tools to.
     * @return The tools.
     */
    protected static PaletteContainer createStatesDrawer(PaletteRoot palette)
    {
        PaletteDrawer drawer = new PaletteDrawer(Messages.statesPaletteGroup);
        CombinedTemplateCreationEntry component;

        component = new CombinedTemplateCreationEntry(Messages.initialState, Messages.createInitialState,
                State.class, new StateCreationFactory(true, false), ImageDescriptor.createFromFile(
                        EditorPlugin.class, "icons/state_ini_16.png"), ImageDescriptor //$NON-NLS-1$
                        .createFromFile(EditorPlugin.class, "icons/state_ini_24.png")); //$NON-NLS-1$
        drawer.add(component);

        component = new CombinedTemplateCreationEntry(Messages.normalState, Messages.createNormalState,
                State.class, new StateCreationFactory(false, false), ImageDescriptor
                        .createFromFile(EditorPlugin.class, "icons/state_nor_16.png"), //$NON-NLS-1$
                ImageDescriptor.createFromFile(EditorPlugin.class, "icons/state_nor_24.png")); //$NON-NLS-1$
        drawer.add(component);

        component = new CombinedTemplateCreationEntry(Messages.finalState, Messages.createFinalState,
                State.class, new StateCreationFactory(false, true), ImageDescriptor.createFromFile(
                        EditorPlugin.class, "icons/state_fin_16.png"), ImageDescriptor //$NON-NLS-1$
                        .createFromFile(EditorPlugin.class, "icons/state_fin_24.png")); //$NON-NLS-1$
        drawer.add(component);

        return drawer;
    }

    /**
     * Creates the operations drawer group.
     * 
     * @param palette
     *            The palette to add the tools to.
     * @return The tools.
     */
    protected static PaletteContainer createOperationsDrawer(PaletteRoot palette)
    {
        PaletteDrawer drawer = new PaletteDrawer(Messages.operationsPaletteGroup);
        ConnectionCreationToolEntry tool;

        tool = new ConnectionCreationToolEntry(Messages.inputOperation, Messages.createInputOperation,
                new CreationFactory() {
                    public Object getNewObject()
                    {
                        return null;
                    }

                    public Object getObjectType()
                    {
                        return Polarity.POSITIVE;
                    }
                }, ImageDescriptor
                        .createFromFile(EditorPlugin.class, "icons/operation_plus_16.png"), //$NON-NLS-1$
                ImageDescriptor.createFromFile(EditorPlugin.class, "icons/operation_plus_24.png")); //$NON-NLS-1$
        drawer.add(tool);

        tool = new ConnectionCreationToolEntry(Messages.outputOperation, Messages.createOutputOperation,
                new CreationFactory() {
                    public Object getNewObject()
                    {
                        return null;
                    }

                    public Object getObjectType()
                    {
                        return Polarity.NEGATIVE;
                    }
                }, ImageDescriptor.createFromFile(EditorPlugin.class,
                        "icons/operation_minus_16.png"), ImageDescriptor.createFromFile( //$NON-NLS-1$
                        EditorPlugin.class, "icons/operation_minus_24.png")); //$NON-NLS-1$
        drawer.add(tool);

        tool = new ConnectionCreationToolEntry(Messages.implicitOperation,
                Messages.createImplicitOperation, new CreationFactory() {
                    public Object getNewObject()
                    {
                        return null;
                    }

                    public Object getObjectType()
                    {
                        return Polarity.NULL;
                    }
                }, ImageDescriptor
                        .createFromFile(EditorPlugin.class, "icons/operation_none_16.png"), //$NON-NLS-1$
                ImageDescriptor.createFromFile(EditorPlugin.class, "icons/operation_none_24.png")); //$NON-NLS-1$
        drawer.add(tool);

        return drawer;
    }

    /**
     * The flyout palette preferences (location, state, etc).
     * 
     * @return The preferences.
     */
    public static FlyoutPreferences createPalettePreferences()
    {
        return new FlyoutPreferences() {
            private IPreferenceStore getPreferenceStore()
            {
                return EditorPlugin.getDefault().getPreferenceStore();
            }

            public int getDockLocation()
            {
                return getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
            }

            public int getPaletteState()
            {
                return getPreferenceStore().getInt(PALETTE_STATE);
            }

            public int getPaletteWidth()
            {
                return getPreferenceStore().getInt(PALETTE_SIZE);
            }

            public void setDockLocation(int location)
            {
                getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
            }

            public void setPaletteState(int state)
            {
                getPreferenceStore().setValue(PALETTE_STATE, state);
            }

            public void setPaletteWidth(int width)
            {
                getPreferenceStore().setValue(PALETTE_SIZE, width);
            }
        };
    }

}
