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

import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.Line;
import org.rti.webcgh.graphics.widget.PlotElement;


/**
 * Widget that simply draws a horizontal line across the
 * screen.  An example usage is to create a baseline
 * for bar plots.
 */
public class HorizontalLine implements PlotElement {
	
	// ====================================
	//         Attributes
	// ====================================
	
	// Vertical width of line across screen in pixels
	private final int width;
	
	// Thickness of line in pixels
	private int lineThickness = 3;
	
	// Color of line
	private Color color = Color.BLACK;
	
	private int x = 0;
	private int y = 0;
	

	/**
	 * @return Color of line
	 */
	public Color getColor() {
		return color;
	}

	
	/**
	 * @param color Color of line
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	
	/**
	 * @return Thickness of line in pixels
	 */
	public int getLineThickness() {
		return lineThickness;
	}

	
	/**
	 * @param lineThickness Thickness of line in pixels
	 */
	public void setLineThickness(int lineThickness) {
		this.lineThickness = lineThickness;
	}
	
	
	// ====================================
	//        Constructors
	// ====================================

	/**
	 * Constructor
	 * @param width Vertical width of line across the screen
	 * in pixels
	 */
	public HorizontalLine(int width) {
		this.width = width;
	}

	
	// ======================================
	//       PlotElement interface
	// ======================================

	
	/**
	 * Paint widget on canvas
	 * @param canvas A drawing canvas
	 */
	public void paint(DrawingCanvas canvas) {
		canvas.add(new Line(this.x, this.y + this.lineThickness,  this.x + this.width, this.y + this.lineThickness, this.lineThickness, 
				this.color));
	}

	/**
	 * Get top left alignment point
	 * @return A point
	 */
	public Point topLeftAlignmentPoint() {
		return new Point(this.x, this.y);
	}

	
	/**
	 * Get bottom left alignment point
	 * @return A point
	 */
	public Point bottomLeftAlignmentPoint() {
		return new Point(this.x, this.y + this.lineThickness);
	}

	
	/**
	 * Get top right alignment point
	 * @return A point
	 */
	public Point topRightAlignmentPoint() {
		return new Point(this.x + this.width, this.y);
	}

	
	/**
	 * Get bottom right alignment point
	 * @return A point
	 */
	public Point bottomRightAlignmentPoint() {
		return new Point(this.x + this.width, this.y + this.lineThickness);
	}

	/**
	 * Get width across screen in pixels
	 * @return Width in pixels
	 */
	public int width() {
		return this.width;
	}

	/**
	 * Get height in pixels
	 * @return Height in pixels
	 */
	public int height() {
		return this.lineThickness;
	}

	
	/**
	 * Get top left point
	 * @return A point
	 */
	public Point topLeftPoint() {
		return new Point(this.x, this.y);
	}
	
	
    /**
     * Move element
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(int deltaX, int deltaY) {
    	this.x += deltaX;
    	this.y += deltaY;
    }
}
