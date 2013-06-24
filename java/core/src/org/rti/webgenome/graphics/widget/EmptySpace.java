/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/graphics/widget/EmptySpace.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/

package org.rti.webgenome.graphics.widget;

import java.awt.Point;

import org.rti.webgenome.graphics.DrawingCanvas;


/**
 * Empty space used for padding in plots
 *
 */
public class EmptySpace implements PlotElement {
	
    // ==============================
    //     Attributes
    // ==============================
    
    private final int width;
    private final int height;
    private int minX = 0;
    private int minY = 0;
    
    
    // ==============================
    //    Constructors
    // ==============================
    
    /**
     * Constructor
     * @param width Width in pixels
     * @param height Height in pixels
     * @param color Color
     */
    public EmptySpace(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    
    // =======================================
    //    Methods in PlotElement interface
    // =======================================
    
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {}
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return new Point(this.minX, this.minY + this.height);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.minX + this.width, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.minX + this.width, this.minY + this.height);
    }
    
    
    /**
     * Width in pixels
     * @return Width in pixels
     */
    public int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels
     * @return Height in pixels
     */
    public int height() {
        return this.height;
    }
    
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Move element
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(int deltaX, int deltaY) {
    	this.minX += deltaX;
    	this.minY += deltaY;
    }

}
