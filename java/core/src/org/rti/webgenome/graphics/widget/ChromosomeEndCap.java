/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;

import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.primitive.Arc;
import org.rti.webgenome.units.Direction;


/**
 * Represents the graphical end cap of a chromosome
 * ideogram (i.e., a telomere).
 */
public final class ChromosomeEndCap implements PlotElement {
	
	// ==============================
	//       Constants
	// ==============================
	
	/**
	 * Euclidian distance in pixels between the apogee
	 * of the arc and the midpoint of the caps base.
	 */
	private static final int APOGEE = 20;
    
	// =====================================
	//     Attributes
	// =====================================
	
	/** Thickness of cap in pixels. */
	private final int thickness;
	
	/** Color of border line. */
	private final Color lineColor;
	
	/** Direction cap is pointing. */
	private final Direction direction;
	
	/**
	 * X-coordinate of <code>Arc</code> object
	 * representing cap.
	 */
	private int x = -1;
	
	/**
	 * Y-coordinate of <code>Arc</code> object
	 * representing cap.
	 */
	private int y = -1;
	
	/** Minimum X-coordinate in cap. */
	private int minX = 0;
	
	/** Minimum Y-coordinate in cap. */
	private int minY = 0;
	
	/** Left or top alignment point, depending on direction. */
	private Point alignmentPoint1 = null;
	
	/** Right or bottom alignment point, depending on direction. */
	private Point alignmentPoint2 = null;
    
	// ==================================
	//     Constructors
	// ==================================
	
	/**
	 * Constructor.
	 * @param thickness Thickness of cap across its base in pixels.
	 * When the direction is up or down, this is the height.  When
	 * the direction is left or right, this is the width. 
	 * @param lineColor Color of border line
	 * @param direction Direction cap points
	 */
	public ChromosomeEndCap(final int thickness, final Color lineColor,
			final Direction direction) {
		this.thickness = thickness;
		this.lineColor = lineColor;
		this.direction = direction;
		if (direction == Direction.UP) {
			this.x = thickness / 2;
			this.y = APOGEE;
			this.alignmentPoint1 = new Point(0, APOGEE);
			this.alignmentPoint2 = new Point(thickness, APOGEE);
		} else if (direction == Direction.DOWN) {
			this.x = thickness / 2;
			this.y = 0;
			this.alignmentPoint1 = new Point(0, 0);
			this.alignmentPoint2 = new Point(thickness, 0);
		} else if (direction == Direction.LEFT) {
			this.x = APOGEE;
			this.y = thickness / 2;
			this.alignmentPoint1 = new Point(APOGEE, 0);
			this.alignmentPoint2 = new Point(APOGEE, thickness);
		} else if (direction == Direction.RIGHT) {
			this.x = 0;
			this.y = thickness / 2;
			this.alignmentPoint1 = new Point(0, 0);
			this.alignmentPoint2 = new Point(0, thickness);
		}
	}
	
	
	// ============================================
	//   Implementation of plot element interface
	// ============================================
	
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public void paint(final DrawingCanvas canvas) {
        canvas.add(new Arc(this.x, this.y, this.thickness, APOGEE,
        		this.direction, this.lineColor));
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
    	return this.alignmentPoint1;
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	Point p = null;
    	if (this.direction == Direction.UP
    			|| this.direction == Direction.DOWN) {
    		p = this.alignmentPoint1;
    	} else if (this.direction == Direction.LEFT
    			|| this.direction == Direction.RIGHT) {
    		p = this.alignmentPoint2;
    	}
    	return p;
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	Point p = null;
    	if (this.direction == Direction.UP
    			|| this.direction == Direction.DOWN) {
    		p = this.alignmentPoint2;
    	} else if (this.direction == Direction.LEFT
    			|| this.direction == Direction.RIGHT) {
    		p = this.alignmentPoint1;
    	}
    	return p;
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
    	return this.alignmentPoint2;
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public int width() {
    	int width = 0;
    	if (this.direction == Direction.UP
    			|| this.direction == Direction.DOWN) {
    		width = this.thickness;
    	} else if (this.direction == Direction.LEFT
    			|| this.direction == Direction.RIGHT) {
    		width = APOGEE;
    	}
    	return width;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public int height() {
    	int height = 0;
    	if (this.direction == Direction.UP
    			|| this.direction == Direction.DOWN) {
    		height = APOGEE;
    	} else if (this.direction == Direction.LEFT
    			|| this.direction == Direction.RIGHT) {
    		height = this.thickness;
    	}
    	return height;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public Point topLeftPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(final int deltaX, final int deltaY) {
    	this.minX += deltaX;
    	this.minY += deltaY;
    	this.x += deltaX;
    	this.y += deltaY;
    	this.alignmentPoint1.x += deltaX;
    	this.alignmentPoint1.y += deltaY;
    	this.alignmentPoint2.x += deltaX;
    	this.alignmentPoint2.y += deltaY;
    }

}
