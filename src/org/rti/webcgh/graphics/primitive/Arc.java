/*
$Revision: 1.2 $
$Date: 2006-09-09 18:41:52 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/


package org.rti.webcgh.graphics.primitive;

import java.awt.Color;

import org.rti.webcgh.units.Direction;


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
	
	
}
