/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;

import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.event.GraphicEvent;
import org.rti.webgenome.graphics.primitive.Rectangle;

/**
 * Widget that provides a background for
 * other widgets.
 */
public class Background implements PlotElement {
    
    
    // ==============================
    //     Attributes
    // ==============================
    
	/** Width of background in pixels. */
    private final int width;
    
    /** Height of background in pixels. */
    private final int height;
    
    /** Mimimum X-coordinate. */
    private int minX = 0;
    
    /** Minimum Y-coordinate. */
    private int minY = 0;
    
    /** Color of background. */
    private final Color color;
    
    
    // ==============================
    //    Constructors
    // ==============================
    
    /**
     * Constructor.
     * @param width Width in pixels
     * @param height Height in pixels
     * @param color Color
     */
    public Background(final int width, final int height,
    		final Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    
    // =======================================
    //    Methods in PlotElement interface
    // =======================================
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public final void move(final int deltaX, final int deltaY) {
    	this.minX += deltaX;
    	this.minY += deltaY;
    }
    
    
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {
        Rectangle rect = new Rectangle(this.minX,
        		this.minY, width, height, this.color);
        rect.addGraphicEventResponse(
        		GraphicEvent.mouseMoveEvent, "hideToolTip()");
        canvas.add(rect);
    }
    
    
    /**
     * Point at top left used to align with other plot
     * elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other
     * plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
        return new Point(this.minX, this.minY + height);
    }
    
    
    /**
     * Point at top right used to align with other plot
     * elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
        return new Point(this.minX + width, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other
     * plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
        return new Point(this.minX + width, this.minY + height);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public final int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public final int height() {
        return this.height;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public final Point topLeftPoint() {
        return new Point(this.minX, this.minY);
    }
}
