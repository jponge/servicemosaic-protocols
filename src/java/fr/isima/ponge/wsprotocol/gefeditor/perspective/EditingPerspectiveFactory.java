/*
 * Copyright (c) 2005, 2006 Julien Ponge - All rights reserved.
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
