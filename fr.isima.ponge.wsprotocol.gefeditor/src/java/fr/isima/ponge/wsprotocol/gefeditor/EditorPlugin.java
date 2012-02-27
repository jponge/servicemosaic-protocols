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

package fr.isima.ponge.wsprotocol.gefeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class EditorPlugin extends AbstractUIPlugin
{

    // The shared instance.
    private static EditorPlugin plugin;

    /**
     * The constructor.
     */
    public EditorPlugin()
    {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     * 
     * @param context
     *            The bundle context.
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     * 
     * @param context
     *            The bundle context.
     */
    public void stop(BundleContext context) throws Exception
    {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     * 
     * @return The shared instance of the plug-in.
     */
    public static EditorPlugin getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path.
     * 
     * @param path
     *            The path
     * @return The image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path)
    {
        return AbstractUIPlugin.imageDescriptorFromPlugin("fr.isima.ponge.wsprotocol.editor", path); //$NON-NLS-1$
    }
}
