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

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

import fr.isima.ponge.wsprotocol.gefeditor.Messages;

/**
 * The context menu provider for the editor.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ProtocolEditorContextMenuProvider extends ContextMenuProvider
{

    /**
     * The actions registry.
     */
    protected ActionRegistry actionRegistry;

    /**
     * Constructs a new context menu provider.
     * 
     * @param viewer
     *            The associated viewer.
     * @param registry
     *            The actions registry.
     */
    public ProtocolEditorContextMenuProvider(EditPartViewer viewer, ActionRegistry registry)
    {
        super(viewer);
        this.actionRegistry = registry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void buildContextMenu(IMenuManager menu)
    {
        // Add standard action groups to the menu
        GEFActionConstants.addStandardActionGroups(menu);

        IAction action;
        action = getAction(ActionFactory.UNDO.getId());
        menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

        action = getAction(ActionFactory.REDO.getId());
        menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

        action = getAction(ActionFactory.DELETE.getId());
        if (action.isEnabled())
            menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

        // Alignment actions
        MenuManager submenu = new MenuManager(Messages.alignMenuEntry);

        action = getAction(GEFActionConstants.ALIGN_LEFT);
        if (action.isEnabled())
            submenu.add(action);

        action = getAction(GEFActionConstants.ALIGN_CENTER);
        if (action.isEnabled())
            submenu.add(action);

        action = getAction(GEFActionConstants.ALIGN_RIGHT);
        if (action.isEnabled())
            submenu.add(action);

        submenu.add(new Separator());

        action = getAction(GEFActionConstants.ALIGN_TOP);
        if (action.isEnabled())
            submenu.add(action);

        action = getAction(GEFActionConstants.ALIGN_MIDDLE);
        if (action.isEnabled())
            submenu.add(action);

        action = getAction(GEFActionConstants.ALIGN_BOTTOM);
        if (action.isEnabled())
            submenu.add(action);

        if (!submenu.isEmpty())
            menu.appendToGroup(GEFActionConstants.GROUP_REST, submenu);

        action = getAction(ActionFactory.SAVE.getId());
        menu.appendToGroup(GEFActionConstants.GROUP_SAVE, action);
    }

    /**
     * Returns an action of the registry from its ID.
     * 
     * @param actionId
     *            The ID.
     * @return The action or <code>null</code> if no action is associated to this ID.
     */
    protected IAction getAction(String actionId)
    {
        return actionRegistry.getAction(actionId);
    }

}
