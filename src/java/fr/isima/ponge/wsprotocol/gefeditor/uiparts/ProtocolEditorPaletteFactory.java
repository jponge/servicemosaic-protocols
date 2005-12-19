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
