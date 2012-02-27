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

package fr.isima.ponge.wsprotocol.timed.operators.ui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.dom4j.DocumentException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;
import fr.isima.ponge.wsprotocol.timed.operators.IOperator;
import fr.isima.ponge.wsprotocol.timed.operators.Normalizer;
import fr.isima.ponge.wsprotocol.xml.XmlIOManager;

/**
 * The abstract class for the operators action delegates.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 * 
 */
public abstract class TimedOperatorAction implements IObjectActionDelegate
{
    /**
     * The current selection.
     */
    private ISelection selection;

    /**
     * Gets the actual operator to use to perform the action.
     * 
     * @return The operatorn.
     */
    protected abstract IOperator getOperator();

    /**
     * Computes a default resulting filename from the ones of the 2 protocols.
     * 
     * @param f1
     *            The first protocol file.
     * @param f2
     *            The second protocol file.
     * @return The filename.
     */
    protected abstract String getResultingFilename(IFile f1, IFile f2);

    /**
     * Gets the first file index in the selection.
     * 
     * @return The index.
     */
    protected abstract int getFirstFileIndex();

    /**
     * Gets the second file index in the selection.
     * 
     * @return The index.
     */
    protected abstract int getSecondFileIndex();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart)
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action)
    {
        Object[] resources = ((IStructuredSelection) selection).toArray();
        IFile p1File = (IFile) resources[getFirstFileIndex()];
        IFile p2File = (IFile) resources[getSecondFileIndex()];

        BusinessProtocolFactoryImpl factory = new BusinessProtocolFactoryImpl();
        XmlIOManager xmlManager = new XmlIOManager(factory);
        try
        {
            BusinessProtocol p1 = xmlManager.readBusinessProtocol(new InputStreamReader(p1File
                    .getContents()));
            BusinessProtocol p2 = xmlManager.readBusinessProtocol(new InputStreamReader(p2File
                    .getContents()));
            Normalizer normalizer = new Normalizer(factory);
            BusinessProtocol result = getOperator().apply(normalizer.normalizeProtocol(p1), normalizer.normalizeProtocol(p2));

            Path resultPath = new Path(getResultingFilename(p1File, p2File));
            IFile resultFile = ((IContainer) p1File.getParent()).getFile(resultPath);
            File temp = File.createTempFile("protocol.operation.result", "tmp");
            FileWriter writer = new FileWriter(temp);
            xmlManager.writeBusinessProtocol(result, writer);
            writer.close();

            resultFile.create(new FileInputStream(temp), true, null);
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage();
            IDE.openEditor(page, resultFile, true);
            
            temp.delete();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        catch (CoreException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection)
    {
        this.selection = selection;
    }

}
