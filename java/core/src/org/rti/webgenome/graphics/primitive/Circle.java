/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/


package org.rti.webgenome.graphics.primitive;

import java.awt.Color;


/**
 * A circle.
 */
public class Circle extends GraphicPrimitive {
	
	/** X-coordinate of center of circle. */
	private int x = 0;
	
	/** Y-coordinate of center of circle. */
	private int y = 0;
	
	/** Radius of circle. */
	private int radius = 0;
	
	/** Is circle filled? */
	private boolean filled = true;
	
	
	/**
	 * Constructor.
	 * @param x X-coordinate of center
	 * @param y Y-coordinate of center
	 * @param radius Radius
	 * @param color Color
	 */
	public Circle(final int x, final int y, final int radius,
			final Color color) {
		super(color);
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	
	/**
	 * Constructor.
	 * @param x X-coordinate of center
	 * @param y Y-coordinate of center
	 * @param radius Radius
	 * @param color Color
	 * @param filled Is circle filled?
	 */
	public Circle(final int x, final int y, final int radius,
			final Color color, final boolean filled) {
		this(x, y, radius, color);
		this.filled = filled;
	}

	/**
	 * Get radius.
	 * @return Radius
	 */
	public final int getRadius() {
		return radius;
	}

	/**
	 * Get X-coordinate of center point.
	 * @return X-coordinate of center point
	 */
	public final int getX() {
		return x;
	}

	/**
	 * Get Y-coordinate of center point.
	 * @return Y-coordinate of center point
	 */
	public final int getY() {
		return y;
	}

	/**
	 * Is circle filled?
	 * @return T/F
	 */
	public final boolean isFilled() {
		return filled;
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
