/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $

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
