/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/


package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.awt.Point;


/**
 * Represents a line.
 */
public class Line extends GraphicPrimitive {
	
	/** X-coordinate of one end of line. */
	private int x1 = 0;
	
	/** Y-coordinate of one end of line. */
	private int y1 = 0;
	
	/** X-coordinate of other end of line. */
	private int x2 = 0;
	
	/** Y-coordinate of other end of line. */
	private int y2 = 0;
	
	/** Width of line (i.e., stroke). */
	private int width = 2;
	
	
	/**
	 * Constructor.
	 *
	 */
	public Line() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param x1 X-coordinate of first end
	 * @param y1 Y-coordinate of first end
	 * @param x2 X-coordinate of second end
	 * @param y2 X-coordinate of second end
	 * @param width Width of line
	 * @param color Color of line
	 */
	public Line(final int x1, final int y1, final int x2,
			final int y2, final int width, final Color color) {
		super(color);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = width;
	}
	
	
	/**
	 * Constructor.
	 * @param x1 X-coordinate of first end
	 * @param y1 Y-coordinate of first end
	 * @param x2 X-coordinate of second end
	 * @param y2 X-coordinate of second end
	 */
	public Line(final int x1, final int y1, final int x2,
			final int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	
	/**
	 * Constructor.
	 * @param p1 One endpoint of the line.
	 * @param p2 The other endpoint of the line.
	 */
	public Line(final Point p1, final Point p2) {
		this(p1.x, p1.y, p2.x, p2.y);
	}

	/**
	 * Get width of line (i.e., stroke).
	 * @return Width width of line in pixels
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Get X-coordinate of first endpoint.
	 * @return X-coordinate of first endpoint
	 */
	public final int getX1() {
		return x1;
	}

	/**
	 * Get X-coordinate of second endpoint.
	 * @return X-coordinate of second endpoint
	 */
	public final int getX2() {
		return x2;
	}

	/**
	 * Get Y-coordinate of first endpoint.
	 * @return Y-coordinate of first endpoint
	 */
	public final int getY1() {
		return y1;
	}

	/**
	 * Get Y-coordinate of second endpoint.
	 * @return Y-coordinate of second endpoint
	 */
	public final int getY2() {
		return y2;
	}

	/**
	 * Set width (i.e., stroke) of line.
	 * @param i Width of line
	 */
	public final void setWidth(final int i) {
		width = i;
	}

	/**
	 * Set X-coordinate of first endpoint.
	 * @param i X-coordinate of first endpoint
	 */
	public final void setX1(final int i) {
		x1 = i;
	}

	/**
	 * Set X-coordinate of second endpoint.
	 * @param i X-coordinate of second endpoint
	 */
	public final void setX2(final int i) {
		x2 = i;
	}

	/**
	 * Set Y-coordinate of first endpoint.
	 * @param i Y-coordinate of first endpoint
	 */
	public final void setY1(final int i) {
		y1 = i;
	}

	/**
	 * Set Y-coordinate of second endpoint.
	 * @param i Y-coordinate of second endpoint
	 */
	public final void setY2(final int i) {
		y2 = i;
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
		this.x1 += deltaX;
		this.y1 += deltaY;
		this.x2 += deltaX;
		this.y2 += deltaY;
	}
}
