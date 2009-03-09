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
