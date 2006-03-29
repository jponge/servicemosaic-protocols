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

package fr.isima.ponge.wsprotocol.gefeditor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Proof-of-concept SWT/Draw2D application. This is used to quickly test the figures developped for
 * this editor.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 */
public class FiguresPOC
{

    /**
     * Displays the testing window.
     * 
     * @param args
     *            Command-line arguments, simply ignored.
     */
    public static void main(String[] args)
    {
        // Window + display context
        Shell shell = new Shell();
        shell.setSize(640, 480);
        shell.open();
        shell.setText("Figures POC"); //$NON-NLS-1$
        LightweightSystem lws = new LightweightSystem(shell);
        IFigure panel = new Figure();
        panel.setOpaque(true);
        panel.setBackgroundColor(ColorConstants.white);
        lws.setContents(panel);

        // State 1
        StateFigure s1 = new StateFigure("State #1", true, false); //$NON-NLS-1$
        s1.setBounds(new Rectangle(10, 10, 100, 60));
        panel.add(s1);
        new Dragger(s1);

        // State 2
        StateFigure s2 = new StateFigure("State #2", false, false); //$NON-NLS-1$
        s2.setBounds(s1.getBounds().getTranslated(new Point(200, 100)));
        panel.add(s2);
        new Dragger(s2);

        // State 3
        StateFigure s3 = new StateFigure("State #3", false, true); //$NON-NLS-1$
        s3.setBounds(s1.getBounds().getTranslated(new Point(200, 250)));
        panel.add(s3);
        new Dragger(s3);

        // Router
        FanRouter router = new FanRouter();
        router.setSeparation(40);

        // T1
        OperationFigure t1 = new OperationFigure(OperationFigure.INPUT, "T1"); //$NON-NLS-1$
        t1.setSourceAnchor(s1.getAnchor());
        t1.setTargetAnchor(s2.getAnchor());
        t1.setConnectionRouter(router);
        panel.add(t1);

        // T2
        OperationFigure t2 = new OperationFigure(OperationFigure.OUTPUT, "T2"); //$NON-NLS-1$
        t2.setSourceAnchor(s2.getAnchor());
        t2.setTargetAnchor(s3.getAnchor());
        t2.setConnectionRouter(router);
        panel.add(t2);

        // T3
        OperationFigure t3 = new OperationFigure(OperationFigure.IMPLICIT, "T3"); //$NON-NLS-1$
        t3.setSourceAnchor(s2.getAnchor());
        t3.setTargetAnchor(s1.getAnchor());
        t3.setConnectionRouter(router);
        panel.add(t3);

        // T4
        OperationFigure t4 = new OperationFigure(OperationFigure.INPUT, "T4"); //$NON-NLS-1$
        t4.setSourceAnchor(s2.getAnchor());
        t4.setTargetAnchor(s2.getAnchor());
        t4.setConnectionRouter(router);
        // panel.add(t4);

        // T5
        OperationFigure t5 = new OperationFigure(OperationFigure.INPUT, "T5"); //$NON-NLS-1$
        t5.setSourceAnchor(s2.getAnchor());
        t5.setTargetAnchor(s2.getAnchor());
        t5.setConnectionRouter(router);
        // panel.add(t5);

        // T6
        OperationFigure t6 = new OperationFigure(OperationFigure.OUTPUT, "T6"); //$NON-NLS-1$
        t6.setSourceAnchor(s1.getAnchor());
        t6.setTargetAnchor(s2.getAnchor());
        t6.setConnectionRouter(router);
        panel.add(t6);

        // Run
        Display display = Display.getDefault();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    /**
     * Utility class to enable figures motion, stolen from the Eclipse Draw2D examples.
     * 
     * @author The Eclipse Project
     */
    static class Dragger extends MouseMotionListener.Stub implements MouseListener
    {
        /**
         * Creates a new dragger.
         * 
         * @param figure
         *            The figure to add dragging support to.
         */
        public Dragger(IFigure figure)
        {
            figure.addMouseMotionListener(this);
            figure.addMouseListener(this);
            last = figure.getBounds().getLocation();
        }

        /**
         * The last location.
         */
        Point last;

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.MouseListener#mouseReleased(org.eclipse.draw2d.MouseEvent)
         */
        public void mouseReleased(MouseEvent e)
        {
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.MouseListener#mouseDoubleClicked(org.eclipse.draw2d.MouseEvent)
         */
        public void mouseDoubleClicked(MouseEvent e)
        {
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.MouseListener#mousePressed(org.eclipse.draw2d.MouseEvent)
         */
        public void mousePressed(MouseEvent e)
        {
            last = e.getLocation();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.MouseMotionListener#mouseDragged(org.eclipse.draw2d.MouseEvent)
         */
        public void mouseDragged(MouseEvent e)
        {
            Point p = e.getLocation();
            Dimension delta = p.getDifference(last);
            last = p;
            Figure f = ((Figure) e.getSource());
            f.setBounds(f.getBounds().getTranslated(delta.width, delta.height));
        }
    };

}
