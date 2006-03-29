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

package fr.isima.ponge.wsprotocol.gefeditor.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * The perspective for editing business protocols. Specifically, the required views to properly edit
 * business protocols are:
 * <ul>
 * <li>the navigator</li>
 * <li>the outline</li>
 * <li>tasks</li>
 * <li>problems</li>
 * <li>properties</li>
 * <li>the protocols validation view</li>
 * </ul>
 * 
 * @author Julien Ponge (ponge@isima.fr)
 * 
 */
public class EditingPerspectiveFactory implements IPerspectiveFactory
{
    /** The protocol validation view identifier. */
    public static final String ID_VALIDATION_VIEW = "fr.isima.ponge.wsprotocol.validation.views.MainValidationView";

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
     */
    public void createInitialLayout(IPageLayout layout)
    {
        // The editor acts as the reference
        String editorAreaId = layout.getEditorArea();

        // Navigator
        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f,
                editorAreaId);
        topLeft.addView(IPageLayout.ID_RES_NAV);

        // Outline, below the navigator
        IFolderLayout middleLeft = layout.createFolder("middleLeft", IPageLayout.BOTTOM, 0.50f,
                IPageLayout.ID_RES_NAV);
        middleLeft.addView(IPageLayout.ID_OUTLINE);

        // Validation, below the outline
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.60f,
                IPageLayout.ID_OUTLINE);
        bottomLeft.addView(ID_VALIDATION_VIEW);

        // Complementary views below the editor
        IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f,
                editorAreaId);
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_TASK_LIST);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
    }

}
