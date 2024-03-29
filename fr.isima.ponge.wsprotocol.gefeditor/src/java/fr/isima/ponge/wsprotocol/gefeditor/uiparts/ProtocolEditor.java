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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.DocumentException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.CopyTemplateAction;
import org.eclipse.gef.ui.actions.MatchHeightAction;
import org.eclipse.gef.ui.actions.MatchWidthAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.OperationKind;
import fr.isima.ponge.wsprotocol.StandardExtraProperties;
import fr.isima.ponge.wsprotocol.gefeditor.EditorPlugin;
import fr.isima.ponge.wsprotocol.gefeditor.Messages;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.BusinessProtocolEditPartFactory;
import fr.isima.ponge.wsprotocol.gefeditor.editparts.BusinessProtocolTreeEditPartFactory;
import fr.isima.ponge.wsprotocol.gefeditor.figures.layout.DepthLayout;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolImpl;
import fr.isima.ponge.wsprotocol.timed.constraints.CInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode;
import fr.isima.ponge.wsprotocol.timed.constraints.MInvokeNode;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintLexer;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintParser;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintTreeWalker;
import fr.isima.ponge.wsprotocol.xml.XmlIOManager;

/**
 * The protocol editor part. This GEF-based editor supports a flyout palette. It is also able to
 * provide an adapter for an outline view.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class ProtocolEditor extends GraphicalEditorWithFlyoutPalette implements
        ModelExtraPropertiesConstants
{

    /**
     * The model instance.
     */
    protected BusinessProtocol protocol;

    /**
     * The palette.
     */
    protected static PaletteRoot PALETTE;

    /**
     * Constructs a new protocol editor.
     */
    public ProtocolEditor()
    {
        super();
        setEditDomain(new DefaultEditDomain(this));
        getCommandStack().setUndoLimit(-1);
    }

    /**
     * Returns the model.
     * 
     * @return The model instance.
     */
    public BusinessProtocol getModel()
    {
        return protocol;
    }

    /**
     * Chamges the model.
     * 
     * @param protocol
     *            The new model.
     */
    public void setModel(BusinessProtocol protocol)
    {
        this.protocol = protocol;
        if ((protocol.getInitialState() != null)
                && (protocol.getInitialState().getExtraProperty(
                        ModelExtraPropertiesConstants.STATE_X_PROP) == null))
        {
            DepthLayout layout = new DepthLayout();
            layout.layout(protocol);
            doSave(null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
     */
    protected void configureGraphicalViewer()
    {
        super.configureGraphicalViewer();

        ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
        GraphicalViewer viewer = getGraphicalViewer();

        List zoomLevels = new ArrayList(3);
        zoomLevels.add(ZoomManager.FIT_ALL);
        zoomLevels.add(ZoomManager.FIT_WIDTH);
        zoomLevels.add(ZoomManager.FIT_HEIGHT);
        rootEditPart.getZoomManager().setZoomLevelContributions(zoomLevels);

        IAction zoomIn = new ZoomInAction(rootEditPart.getZoomManager());
        IAction zoomOut = new ZoomOutAction(rootEditPart.getZoomManager());
        getActionRegistry().registerAction(zoomIn);
        getActionRegistry().registerAction(zoomOut);
        getSite().getKeyBindingService().registerAction(zoomIn);
        getSite().getKeyBindingService().registerAction(zoomOut);

        viewer.setRootEditPart(rootEditPart);
        viewer.setEditPartFactory(new BusinessProtocolEditPartFactory());
        viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

        ContextMenuProvider provider = new ProtocolEditorContextMenuProvider(viewer,
                getActionRegistry());
        viewer.setContextMenu(provider);
        getSite().registerContextMenu("fr.isima.ponge.wsprotocol.gefeditor.editor.contextmenu", //$NON-NLS-1$
                provider, viewer);

        viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, Boolean.FALSE);
        viewer.setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED, Boolean.FALSE);

        IAction showRulers = new ToggleRulerVisibilityAction(getGraphicalViewer());
        getActionRegistry().registerAction(showRulers);
        IAction snapAction = new ToggleSnapToGeometryAction(getGraphicalViewer());
        getActionRegistry().registerAction(snapAction);
        IAction showGrid = new ToggleGridAction(getGraphicalViewer());
        getActionRegistry().registerAction(showGrid);

        Listener listener = new Listener() {
            public void handleEvent(Event event)
            {
                IAction copy = null;
                if (event.type == SWT.Deactivate)
                    copy = getActionRegistry().getAction(ActionFactory.COPY.getId());
                if (getEditorSite().getActionBars().getGlobalActionHandler(
                        ActionFactory.COPY.getId()) != copy)
                {
                    getEditorSite().getActionBars().setGlobalActionHandler(
                            ActionFactory.COPY.getId(), copy);
                    getEditorSite().getActionBars().updateActionBars();
                }
            }
        };
        getGraphicalControl().addListener(SWT.Activate, listener);
        getGraphicalControl().addListener(SWT.Deactivate, listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#createActions()
     */
    protected void createActions()
    {
        super.createActions();
        ActionRegistry registry = getActionRegistry();
        IAction action;

        action = new CopyTemplateAction(this);
        registry.registerAction(action);

        action = new MatchWidthAction(this);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new MatchHeightAction(this);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.LEFT);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.RIGHT);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.TOP);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.BOTTOM);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.CENTER);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.MIDDLE);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
     */
    protected void initializeGraphicalViewer()
    {
        super.initializeGraphicalViewer();
        GraphicalViewer viewer = getGraphicalViewer();
        viewer.setContents(getModel());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.CommandStackListener#commandStackChanged(java.util.EventObject)
     */
    public void commandStackChanged(EventObject event)
    {
        firePropertyChange(IEditorPart.PROP_DIRTY);
        super.commandStackChanged(event);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#isSaveOnCloseNeeded()
     */
    public boolean isSaveOnCloseNeeded()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
     */
    protected void setInput(IEditorInput input)
    {
        super.setInput(input);

        final IFile file = ((IFileEditorInput) input).getFile();
        if (getModel() == null)
        {
            loadModel(file);
            cleanProblemMarkers(file, TEMPORAL_CONSTRAINTS_PROBLEM_MARKER_ID);
            validateTemporalConstraints(file);

            /*
             * Ugly hook to be notified of file renames... Let me know if there is a better way!
             */
            IResourceChangeListener listener = new IResourceChangeListener() {
                IPath currentPath = file.getFullPath();

                public void resourceChanged(IResourceChangeEvent event)
                {
                    IResourceDelta delta = event.getDelta().findMember(currentPath);
                    if (delta != null && delta.getMovedToPath() != null)
                    {
                        IPath pathInProject = delta.getMovedToPath().makeAbsolute()
                                .removeFirstSegments(1);
                        IFile newFile = (IFile) file.getProject().findMember(pathInProject);
                        setInput(new FileEditorInput(newFile));
                        currentPath = newFile.getFullPath();
                    }
                }
            };
            file.getWorkspace().addResourceChangeListener(listener,
                    IResourceChangeEvent.POST_CHANGE);
        }
        getModel().putExtraProperty(ModelExtraPropertiesConstants.PROTOCOL_IFILE_RESOURCE, file);
        setPartName(file.getName());
    }

    /**
     * Loads the model.
     * 
     * @param input
     *            The file to load the model from.
     */
    protected void loadModel(IFile file)
    {
        try
        {
            InputStreamReader reader = new InputStreamReader(file.getContents());
            XmlIOManager manager = new XmlIOManager(new BusinessProtocolFactoryImpl());
            setModel(manager.readBusinessProtocol(reader));
            reader.close();
        }
        catch (DocumentException e)
        {
            handleLoadTimeException(e);
        }
        catch (CoreException e)
        {
            handleLoadTimeException(e);
        }
        catch (IOException e)
        {
            handleLoadTimeException(e);
        }
    }

    /**
     * Handles load-time exceptions and reports to the user.
     * 
     * @param e
     *            The exception.
     */
    protected void handleLoadTimeException(Exception e)
    {
        reportError(Messages.loadTimeErrorMessage, e);
        setModel(new BusinessProtocolImpl(Messages.newProtocolNameInModel));
    }

    /**
     * Handles save-time exceptions and reports to the user.
     * 
     * @param e
     *            The exception.
     */
    protected void handleSaveTimeException(Exception e)
    {
        reportError(Messages.saveTimeErrorMessage, e);
    }

    /**
     * Reports an error to the end user.
     * 
     * @param message
     *            The message to display.
     * @param e
     *            The exception associated with the error.
     */
    protected void reportError(String message, Exception e)
    {
        ErrorDialog.openError(getSite().getShell(), null, null, new Status(Status.ERROR,
                EditorPlugin.getDefault().getBundle().getSymbolicName(), Status.OK, message, e));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPalettePreferences()
     */
    protected FlyoutPreferences getPalettePreferences()
    {
        return ProtocolEditorPaletteFactory.createPalettePreferences();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPaletteRoot()
     */
    protected PaletteRoot getPaletteRoot()
    {
        if (PALETTE == null)
        {
            PALETTE = ProtocolEditorPaletteFactory.createPalette();
        }
        return PALETTE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    public void doSave(IProgressMonitor monitor)
    {
        if (monitor != null)
        {
            monitor.beginTask(Messages.savingTask, 3);
        }
        XmlIOManager manager = new XmlIOManager(new BusinessProtocolFactoryImpl());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(out);
        try
        {
            manager.writeBusinessProtocol(getModel(), writer);
            writer.close();
            if (monitor != null)
            {
                monitor.worked(1);
            }
            IFile file = ((IFileEditorInput) getEditorInput()).getFile();
            file.setContents(new ByteArrayInputStream(out.toByteArray()), true, false, monitor);
            getCommandStack().markSaveLocation();
            if (monitor != null)
            {
                monitor.worked(1);
            }
            cleanProblemMarkers(file, TEMPORAL_CONSTRAINTS_PROBLEM_MARKER_ID);
            validateTemporalConstraints(file);
            if (monitor != null)
            {
                monitor.worked(1);
            }
        }
        catch (IOException e)
        {
            handleSaveTimeException(e);
        }
        catch (CoreException e)
        {
            handleSaveTimeException(e);
        }
        if (monitor != null)
        {
            monitor.done();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#doSaveAs()
     */
    public void doSaveAs()
    {
        // Show a SaveAs dialog
        Shell shell = getSite().getWorkbenchWindow().getShell();
        SaveAsDialog dialog = new SaveAsDialog(shell);
        dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
        dialog.open();

        IPath path = dialog.getResult();
        if (path != null)
        {
            final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            try
            {
                new ProgressMonitorDialog(shell).run(false, false, new WorkspaceModifyOperation() {

                    public void execute(final IProgressMonitor monitor)
                    {
                        try
                        {
                            XmlIOManager manager = new XmlIOManager(
                                    new BusinessProtocolFactoryImpl());
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            Writer writer = new OutputStreamWriter(out);
                            manager.writeBusinessProtocol(getModel(), writer);
                            writer.close();
                            file.create(new ByteArrayInputStream(out.toByteArray()), true,

                            monitor);
                        }
                        catch (CoreException ce)
                        {
                            handleSaveTimeException(ce);
                        }
                        catch (IOException ioe)
                        {
                            handleSaveTimeException(ioe);
                        }
                    }
                });
                setInput(new FileEditorInput(file));
                getCommandStack().markSaveLocation();
            }
            catch (InterruptedException ie)
            {
                handleSaveTimeException(ie);
            }
            catch (InvocationTargetException ite)
            {
                handleSaveTimeException(ite);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
     */
    public boolean isSaveAsAllowed()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class type)
    {
        if (type == IContentOutlinePage.class)
            return new ProtocolOutlinePage(new TreeViewer());
        if (type == ZoomManager.class)
            return getGraphicalViewer().getProperty(ZoomManager.class.toString());
        return super.getAdapter(type);
    }

    /**
     * The outline page that can be used along with the protocol editor. The view is read-only and
     * selections are synchronized with the GEF editor. The outline shows states and for each state,
     * its outgoing operations.
     * 
     * @author Julien Ponge (ponge@isima.fr)
     */
    public class ProtocolOutlinePage extends ContentOutlinePage
    {

        /**
         * Constructs a new outline page.
         * 
         * @param viewer
         *            The viewer which is associated with the outline.
         */
        public ProtocolOutlinePage(EditPartViewer viewer)
        {
            super(viewer);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
         */
        public void createControl(Composite parent)
        {
            getViewer().createControl(parent);
            getViewer().setEditDomain(getEditDomain());
            getViewer().setEditPartFactory(new BusinessProtocolTreeEditPartFactory());
            getSelectionSynchronizer().addViewer(getViewer());
            getViewer().setContents(getModel());
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.part.IPage#dispose()
         */
        public void dispose()
        {
            getSelectionSynchronizer().removeViewer(getViewer());
            super.dispose();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.part.IPage#getControl()
         */
        public Control getControl()
        {
            return getViewer().getControl();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
         */
        public void init(IPageSite pageSite)
        {
            super.init(pageSite);
            ActionRegistry registry = getActionRegistry();
            IActionBars bars = pageSite.getActionBars();
            String id = ActionFactory.UNDO.getId();
            bars.setGlobalActionHandler(id, registry.getAction(id));
            id = ActionFactory.REDO.getId();
            bars.setGlobalActionHandler(id, registry.getAction(id));
            id = ActionFactory.DELETE.getId();
            bars.setGlobalActionHandler(id, registry.getAction(id));
        }
    }

    /**
     * Id for the temporal constraints problems resource marker.
     */
    protected static final String TEMPORAL_CONSTRAINTS_PROBLEM_MARKER_ID = "gef.editor.temporal.constraints.problem"; //$NON-NLS-1$

    /**
     * Report a temporal constraint probelem.
     * 
     * @param message
     *            The message.
     * @param location
     *            The location of the problem.
     */
    protected void reportTemporalConstraintProblem(IFile protocolFile, String message,
            String location)
    {
        try
        {
            IMarker marker = protocolFile.createMarker(IMarker.PROBLEM);
            if (marker.exists())
            {
                marker.setAttribute(IMarker.TRANSIENT, true);
                marker.setAttribute(IMarker.MESSAGE, message);
                marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
                marker.setAttribute(IMarker.LOCATION, location);
                marker.setAttribute(TEMPORAL_CONSTRAINTS_PROBLEM_MARKER_ID,
                        TEMPORAL_CONSTRAINTS_PROBLEM_MARKER_ID);
            }
        }
        catch (CoreException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Removes the problem markers on the file resource (if any).
     * 
     * @param markerId
     *            The problem marker ID.
     */
    protected void cleanProblemMarkers(IFile protocolFile, String markerId)
    {
        try
        {
            IMarker[] problems = protocolFile.findMarkers(IMarker.PROBLEM, true,
                    IResource.DEPTH_ONE);
            for (int i = 0; i < problems.length; ++i)
            {
                if (problems[i].exists() && problems[i].getAttributes().containsKey(markerId))
                {
                    problems[i].delete();
                }
            }
        }
        catch (CoreException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the temporal constraints are valid or not. Reports problems else.
     */
    private void validateTemporalConstraints(IFile file)
    {
        Set operations = getModel().getOperations();
        Iterator it = operations.iterator();
        while (it.hasNext())
        {
            Operation operation = (Operation) it.next();
            String constraint = (String) operation
                    .getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT);
            if (constraint == null || "".equals(constraint))
            {
                continue;
            }

            TemporalConstraintLexer lexer = new TemporalConstraintLexer(
                    new StringReader(constraint));
            TemporalConstraintParser parser = new TemporalConstraintParser(lexer);
            TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker();

            try
            {
                parser.constraint();
                CommonAST tree = (CommonAST) parser.getAST();
                IConstraintNode constraintNode = walker.constraint(tree);

                if (operation.getOperationKind().equals(OperationKind.EXPLICIT))
                {
                    if (!(constraintNode instanceof CInvokeNode))
                    {
                        reportTemporalConstraintProblem(
                                file,
                                operation.getName()
                                        + ": "
                                        + "Explicit operations only support C-Invoke temporal constraints.",
                                operation.getName());
                    }
                }
                else
                {
                    if (!(constraintNode instanceof MInvokeNode))
                    {
                        reportTemporalConstraintProblem(
                                file,
                                operation.getName()
                                        + ": "
                                        + "Implicit operations only support M-Invoke temporal constraints.",
                                operation.getName());
                    }
                }
            }
            catch (RecognitionException e)
            {
                reportTemporalConstraintProblem(file, operation.getName() + ": " + e.getMessage(),
                        operation.getName());
            }
            catch (TokenStreamException e)
            {
                reportTemporalConstraintProblem(file, operation.getName() + ": " + e.getMessage(),
                        operation.getName());
            }
        }
    }

}
