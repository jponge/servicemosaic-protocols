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

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchHeightRetargetAction;
import org.eclipse.gef.ui.actions.MatchWidthRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

import fr.isima.ponge.wsprotocol.gefeditor.Messages;

/**
 * The actions contributor for the protocol editor.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ProtocolEditorActionBarContributor extends ActionBarContributor
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.actions.ActionBarContributor#buildActions()
     */
    protected void buildActions()
    {
        addRetargetAction(new DeleteRetargetAction());
        addRetargetAction(new UndoRetargetAction());
        addRetargetAction(new RedoRetargetAction());
        
        addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
        addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
        addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
        addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
        addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
        addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));

        addRetargetAction(new ZoomInRetargetAction());
        addRetargetAction(new ZoomOutRetargetAction());

        addRetargetAction(new MatchWidthRetargetAction());
        addRetargetAction(new MatchHeightRetargetAction());

        addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY,
                Messages.snapToGrid, IAction.AS_CHECK_BOX));
        addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY,
                Messages.showGrid, IAction.AS_CHECK_BOX));       
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
     */
    public void contributeToToolBar(IToolBarManager toolBarManager)
    {
        toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
        toolBarManager.add(getAction(ActionFactory.REDO.getId()));

        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(GEFActionConstants.ALIGN_LEFT));
        toolBarManager.add(getAction(GEFActionConstants.ALIGN_CENTER));
        toolBarManager.add(getAction(GEFActionConstants.ALIGN_RIGHT));
        
        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(GEFActionConstants.ALIGN_TOP));
        toolBarManager.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
        toolBarManager.add(getAction(GEFActionConstants.ALIGN_BOTTOM));

        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(GEFActionConstants.MATCH_WIDTH));
        toolBarManager.add(getAction(GEFActionConstants.MATCH_HEIGHT));

        toolBarManager.add(new Separator());
        String[] zoomStrings = new String[] { ZoomManager.FIT_ALL, ZoomManager.FIT_HEIGHT,
                ZoomManager.FIT_WIDTH };
        toolBarManager.add(new ZoomComboContributionItem(getPage(), zoomStrings));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void contributeToMenu(IMenuManager menuManager)
    {
        MenuManager viewMenu = new MenuManager(Messages.viewMenu);

        viewMenu.add(getAction(GEFActionConstants.ZOOM_IN));
        viewMenu.add(getAction(GEFActionConstants.ZOOM_OUT));
        
        viewMenu.add(new Separator());
        
        viewMenu.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
        viewMenu.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));
        
        viewMenu.add(new Separator());
        
        viewMenu.add(getAction(GEFActionConstants.MATCH_WIDTH));
        viewMenu.add(getAction(GEFActionConstants.MATCH_HEIGHT));

        menuManager.insertAfter(IWorkbenchActionConstants.M_EDIT, viewMenu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.actions.ActionBarContributor#declareGlobalActionKeys()
     */
    protected void declareGlobalActionKeys()
    {
        addGlobalActionKey(ActionFactory.PRINT.getId());
        addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
        addGlobalActionKey(ActionFactory.DELETE.getId());
    }

}
