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
