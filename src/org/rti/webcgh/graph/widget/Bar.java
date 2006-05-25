/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.graph.widget;

import java.awt.Color;
import java.awt.Point;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Rectangle;
import org.rti.webcgh.graph.DataPoint;


/**
 * This class implements a horizontal bar in a bar graph.  When rendered,
 * the height (or depth) of the bar will indicate the magnitude of
 * the data point (i.e., the value2 property).  If the data point
 * includes an error, then an error bar will be drawn.  Examples:
 * <pre>
 *                    ___
 *                     | 
 *      ___           _|_          
 *     |   |         |   |
 *     |   |         |   |
 *     |___|         |___|         ___
 *                                |   |
 *                                |   |
 *                                |___|
 *
 *     positive      positive    negative
 *      value         value        value
 *                     with
 *                    error
 *  </pre>
 */
public class Bar implements ScalePlotElement {
	
	enum Direction {UP, DOWN};
	
	// ================================
	//       Attributes
	// ================================
	
	private final int minBarY;
	private final int maxBarY;
	private final int errorBarHeight;
	private final boolean drawErrorBar;
	private final int minY;
	private final int maxY;
	private final Color color;
	private final Direction direction;
	
	private int barWidth = 15;
	private int errorBarWidth = 2;
		
	
	/**
	 * @return Width of bar
	 */
	public int getBarWidth() {
		return barWidth;
	}


	/**
	 * @param barWidth Width of bar
	 */
	public void setBarWidth(int barWidth) {
		this.barWidth = barWidth;
	}


	/**
	 * @return Width of error bar line
	 */
	public int getErrorBarWidth() {
		return errorBarWidth;
	}


	/**
	 * @param errorBarWidth Width of error bar line
	 */
	public void setErrorBarWidth(int errorBarWidth) {
		this.errorBarWidth = errorBarWidth;
	}

	
	// ==================================
	//          Constructors
	// ==================================

	/**
	 * Constructor
	 * @param dataPoint A data point
	 * @param color Color of bar
	 * @param scale Number of pixels per data point unit
	 */
	public Bar(DataPoint dataPoint, Color color, double scale) {
		
		// Make sure arguments okay
		if (dataPoint == null)
			throw new IllegalArgumentException("Data point cannot be null");
		if (color == null)
			throw new IllegalArgumentException("Color cannot be null");
		if (Double.isInfinite(scale) || Double.isNaN(scale))
			throw new IllegalArgumentException("Invalid scale: " + scale);
		
		// Set properties
		this.color = color;
		double value = dataPoint.getValue2();
		double error = dataPoint.getError();
		if (value >= 0.0) {
			this.minBarY = -(int)(value * scale);
			this.maxBarY = 0;
			this.direction = Direction.UP;
		} else {
			this.minBarY = 0;
			this.maxBarY = -(int)(value * scale);
			this.direction = Direction.DOWN;
		}
		if (Double.isNaN(error)) {
			this.drawErrorBar = false;
			this.errorBarHeight = 0;
			this.minY = this.minBarY;
			this.maxY = this.maxBarY;
		} else {
			this.drawErrorBar = true;
			this.errorBarHeight = (int)(error * scale);
			if (value >= 0) {
				this.minY = this.minBarY - this.errorBarHeight;
				this.maxY = this.maxBarY;
			} else {
				this.minY = this.minBarY;
				this.maxY = this.maxBarY + this.errorBarHeight;
			}
		}
	}

	
	// ==================================
	//       PlotElement interface
	// ==================================
	
	/**
	 * Paint widget
	 * @param canvas A drawing canvas
	 */
	public void paint(DrawingCanvas canvas) {
		this.paintBar(canvas);
		if (this.drawErrorBar)
			this.paintErrorBar(canvas);
	}

	
	/**
	 * Get top left alignment point
	 * @return A point
	 */
	public Point topLeftAlignmentPoint() {
		return new Point(0, this.minY);
	}

	/**
	 * Get bottom left alignment point
	 * @return A point
	 */
	public Point bottomLeftAlignmentPoint() {
		return new Point(0, this.maxY);
	}

	/**
	 * Get top right alignment point
	 * @return A point
	 */
	public Point topRightAlignmentPoint() {
		return new Point(this.barWidth, this.minY);
	}

	/**
	 * Get bottom right alignment point
	 * @return A point
	 */
	public Point bottomRightAlignmentPoint() {
		return new Point(this.barWidth, this.maxY);
	}

	/**
	 * Get width
	 * @return Width in pixels
	 */
	public int width() {
		return this.barWidth;
	}

	/**
	 * Get height
	 * @return Height in pixels
	 */
	public int height() {
		return this.maxY - this.minY;
	}

	/**
	 * Get top left point
	 * @return A point
	 */
	public Point topLeftPoint() {
		return new Point(0, this.minY);
	}
	
	
	// ================================
	//    ScalePlotElement interface
	// ================================
	
	/**
	 * Return point in pixels corresponding to the zero point
	 * in the native units of measurement represented by
	 * element.
	 * @return A point or <code>null</code> if the element
	 * does not contain a zero point
	 */
	public Point zeroPoint() {
		return new Point(0, 0);
	}
	
	
	// ===============================
	//      Private methods
	// ===============================
	
	private void paintBar(DrawingCanvas canvas) {
		Rectangle rect = new Rectangle(0, this.minBarY, this.barWidth, 
				this.maxBarY - this.minBarY, this.color);
		canvas.add(rect);
	}
	
	
	private void paintErrorBar(DrawingCanvas canvas) {
		int stemX = this.barWidth / 2;
		int hatchY, stemY1, stemY2;
		if (this.direction == Direction.UP) {
			hatchY = stemY1 = this.minY;
			stemY2 = this.minBarY;
		} else {
			hatchY = stemY2 = this.maxY;
			stemY1 = this.maxBarY;
		}
		
		// Add stem
		canvas.add(new Line(stemX, stemY1, stemX, stemY2, 
				this.errorBarWidth, this.color));
		
		// Add hatch
		canvas.add(new Line(0, hatchY, this.barWidth, hatchY, 
				this.errorBarWidth, this.color));
	}

}
