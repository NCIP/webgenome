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

package org.rti.webcgh.graph.widgit;

import java.awt.Color;
import java.awt.Point;

import org.rti.webcgh.drawing.Direction;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicCurve;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.graph.PlotElement;


/**
 * Chromosome end cap
 */
public class ChromosomeEndCap implements PlotElement {
    
	// =====================================
	//     Attributes
	// =====================================
	
	private final int width;
	private final Color lineColor;
	private final Direction direction;
	
	private int lineWidth = 1;
	private int height = 20;
	

    /**
     * @param lineWidth The lineWidth to set.
     */
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
    
    
    /**
     * @param height The height to set.
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    
	// ==================================
	//     Constructors
	// ==================================
	
	/**
	 * Constructor
	 * @param width Width of cap
	 * @param lineColor Color of line
	 * @param direction Direction cap points
	 */
	public ChromosomeEndCap(int width, Color lineColor,
			Direction direction) {
		this.width = width;
		this.lineColor = lineColor;
		this.direction = direction;
	}
	
	
	// ============================================
	//   Implementation of plot element interface
	// ============================================
	
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
        GraphicCurve curve = null;        
        if (this.direction == Direction.UP)
            curve = new GraphicCurve(0, 0, this.width, 0, -this.height / 2, Orientation.VERTICAL, this.lineColor);
        else if (this.direction == Direction.DOWN)
            curve = new GraphicCurve(0, 0, this.width, 0, this.height / 2, Orientation.VERTICAL, this.lineColor);
        else if (this.direction == Direction.LEFT)
            curve = new GraphicCurve(this.height, 0, this.height, this.width, -this.height, Orientation.HORIZONTAL, this.lineColor);
        else if (this.direction == Direction.RIGHT)
            curve = new GraphicCurve(0, 0, 0, this.width, this.height, Orientation.HORIZONTAL, this.lineColor);
        canvas.add(curve);
    }
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
    	return new Point(0, 0);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	return new Point(0, 0);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	return new Point(this.width, 0);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
    	return new Point(this.width, 0);
    }
    
    
    /**
     * Width in pixels
     * @return Width in pixels
     */
    public int width() {
    	return this.width;
    }
    
    
    /**
     * Height in pixels
     * @return Height in pixels
     */
    public int height() {
    	return this.height;
    }
    
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
        if (this.direction == Direction.UP)
            return new Point(0, -this.height);
    	return new Point(0, 0);
    }

}
