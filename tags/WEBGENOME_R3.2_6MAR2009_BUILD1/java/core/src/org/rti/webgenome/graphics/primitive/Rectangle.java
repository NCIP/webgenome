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


package org.rti.webgenome.graphics.primitive;

import java.awt.Color;


/**
 * Rectangle.
 */
public class Rectangle extends GraphicPrimitive {
	
	/** X-coordinate of top left point. */
	private int x = 0;
	
	/** Y-coordinate of top left point. */
	private int y = 0;
	
	/** Width in pixels. */
	private int width = 0;
	
	/** Height in pixels. */
	private int height = 0;
	
	/** Should rectangle be filled? */
	private boolean filled = true;
	
	
	/**
	 * Constructor.
	 *
	 */
	public Rectangle() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param width Width
	 * @param height Height
	 * @param color Color
	 */
	public Rectangle(final int x, final int y, final int width,
			final int height, final Color color) {
		super(color);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	

	/**
	 * Get height.
	 * @return Height of rectangle in pixels.
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * Get width.
	 * @return Width of rectangle pin pixels
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Get X-coordinate of top left point.
	 * @return X-coordinate of top left point
	 */
	public final int getX() {
		return x;
	}

	/**
	 * Get Y-coordinate of top left point.
	 * @return Y-coordinate of bottom left point
	 */
	public final int getY() {
		return y;
	}

	/**
	 * Set height of rectangle.
	 * @param i Height in pixels.
	 */
	public final void setHeight(final int i) {
		height = i;
	}

	/**
	 * Set width of rectangle.
	 * @param i Width in pixels
	 */
	public final void setWidth(final int i) {
		width = i;
	}

	/**
	 * Set X-coordinate of top left point.
	 * @param i X-coordinate of top left point
	 */
	public final void setX(final int i) {
		x = i;
	}

	/**
	 * Set Y-coordinate of top left point.
	 * @param i Y-coordinate of top left point
	 */
	public final void setY(final int i) {
		y = i;
	}

	/**
	 * Is rect filled in?
	 * @return T/F
	 */
	public final boolean isFilled() {
		return filled;
	}

	/**
	 * Is rect filled in?
	 * @param b Is rect filled in?
	 */
	public final void setFilled(final boolean b) {
		filled = b;
	}

	
	// ==================================
	//     Implemented abstract methods
	// ==================================
	
	/**
	 * Move graphic primitive.
	 * @param deltaX Change in X-coordinates in pixels
	 * @param deltaY Change in Y-coordinates in pixels
	 */
	public final void move(final int deltaX, final int deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
}
