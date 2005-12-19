/*
 * Copyright (c) 2005 Julien Ponge - All rights reserved.
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
