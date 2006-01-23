
package fr.isima.ponge.wsprotocol.gefeditor.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class EditingPerspectiveFactory implements IPerspectiveFactory
{

    public void createInitialLayout(IPageLayout layout)
    {
        // The editor acts as the reference
        String editorAreaId = layout.getEditorArea();

        // Navigator
        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f,
                editorAreaId);
        topLeft.addView(IPageLayout.ID_RES_NAV);

        // Outline, below the navigator
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.50f, IPageLayout.ID_RES_NAV);
        bottomLeft.addView(IPageLayout.ID_OUTLINE);
        
        // Complementary views below the editor
        IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f, editorAreaId);
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_TASK_LIST);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
    }

}
