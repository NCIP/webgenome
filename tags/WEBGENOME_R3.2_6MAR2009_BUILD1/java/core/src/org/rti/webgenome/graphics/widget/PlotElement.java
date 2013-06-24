/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $


*/


package org.rti.webgenome.graphics.widget;

import java.awt.Point;

import org.rti.webgenome.graphics.DrawingCanvas;

/**
 * A graphical element in a plot.
 */
public interface PlotElement {
    
    
    /**
     * Paint element.
     * @param canvas A canvas
     */
    void paint(DrawingCanvas canvas);
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    Point topLeftAlignmentPoint();
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    Point bottomLeftAlignmentPoint();
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    Point topRightAlignmentPoint();
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    Point bottomRightAlignmentPoint();
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    int width();
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    int height();
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    Point topLeftPoint();
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    void move(int deltaX, int deltaY);
}
