/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2006-09-19 02:09:30 $

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
