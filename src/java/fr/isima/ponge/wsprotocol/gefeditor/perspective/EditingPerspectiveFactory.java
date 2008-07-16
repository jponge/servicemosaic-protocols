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

        // Complementary views below the editor
        IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.725f,
                editorAreaId);
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_TASK_LIST);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
    }

}
