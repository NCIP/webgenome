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

import org.rti.webgenome.units.Direction;


/**
 * Represents an arc shape that point in four possible
 * directions: up, down, left, and right.
 */
public class Arc extends GraphicPrimitive {

	// ============================
	//       Attributes
	// ============================
	
	/**
	 * X-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 */
	private int x = -1;
	
	/**
	 * Y-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 */
	private int y = -1;
	
	/**
	 * Width of arc, defined as the Euclidian distance
	 * in pixels between the two end points.
	 */
	private int width = -1;
	
	/**
	 * Height of arc, defined as the Euclidian distance
	 * in pixels between the point at the apogee and the orientation
	 * point, i.e., the midpoint of a line connecting the two
	 * ends of the arc.
	 */
	private int height = -1;
	
	/** Direction the arc points. */
	private Direction direction = null;
	
	// =========================
	//    Getters/setters
	// =========================

	/**
	 * Get direction the arc points.
	 * @return Direction the arc points.
	 */
	public final Direction getDirection() {
		return direction;
	}

	/**
	 * Set direction the arc points.
	 * @param direction Direction the arc points.
	 */
	public final void setDirection(final Direction direction) {
		this.direction = direction;
	}

	/**
	 * Get height of the arc.
	 * The height of the arc is defined as the Euclidian distance
	 * in pixels between the point at the apogee and the orientation
	 * point, i.e., the midpoint of a line connecting the two
	 * ends of the arc.
	 * @return Height of the arc in pixels.
	 */
	public final int getHeight() {
		return height;
	}

	
	/**
	 * Set height of the arc.
	 * The height of the arc is defined as the Euclidian distance
	 * in pixels between the point at the apogee and the orientation
	 * point, i.e., the midpoint of a line connecting the two
	 * ends of the arc.
	 * @param height Height of the arc in pixels.
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * Get width of arc, defined as the Euclidian distance
	 * between the two end points.
	 * @return Width of the arc in pixels.
	 */
	public final int getWidth() {
		return width;
	}

	
	/**
	 * Set width of arc, defined as the Euclidian distance
	 * between the two end points.
	 * @param width Width of arc in pixels.
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}

	
	/**
	 * Get X-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 * @return X-coordinate of orientation point.
	 */
	public final int getX() {
		return x;
	}

	
	/**
	 * Set X-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 * @param x X-coordinate of orientation point.
	 */
	public final void setX(final int x) {
		this.x = x;
	}

	
	/**
	 * Get Y-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 * @return Y-coordinate of orientation point.
	 */
	public final int getY() {
		return y;
	}

	
	/**
	 * Set Y-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 * @param y Y-coordinate of orientation point.
	 */
	public final void setY(final int y) {
		this.y = y;
	}

	
	// ============================
	//      Constructors
	// ============================
	
	/**
	 * Constructor.
	 */
	public Arc() {
		
	}
	
	/**
	 * Constructor.
	 * @param x X-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 * @param y Y-coordinate of orientation point.  This point
	 * is the midpoint of a line connecting the two
	 * ends of the arc.
	 * @param width Width of arc, defined as the Euclidian distance
	 * in pixels between the two end points.
	 * @param height Height of arc, defined as the Euclidian distance
	 * in pixels between the point at the apogee and the orientation
	 * point, i.e., the midpoint of a line connecting the two
	 * ends of the arc.
	 * @param direction Direction the arc points.
	 * @param color Color of arc.
	 */
	public Arc(final int x, final int y, final int width,
			final int height, final Direction direction,
			final Color color) {
		super(color);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.direction = direction;
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
