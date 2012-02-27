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
