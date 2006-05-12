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
import java.io.Serializable;
import java.net.URL;

import org.rti.webcgh.drawing.Cursor;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicLine;
import org.rti.webcgh.drawing.GraphicText;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Hyperlink;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;

/**
 * Tic mark on a plot axis
 */
public class AxisTicMark implements Serializable {
    
    
    // ===========================
    //    Static variables
    // ===========================
    
    private static final Color HYPERLINK_COLOR = Color.blue;
	
    
    // ========================================
    //        Attributes
    // ========================================
    
    private final Point alignmentPoint;
	private final String label;
	private final URL link;
	private final Orientation orientation;
	private final Location labelLocation;
	
	private int lineX1 = 0;
	private int lineY1 = 0;
	private int lineX2 = 0;
	private int lineY2 = 0;
	private int textX = 0;
	private int textY = 0;
	private int minX = 0;
	private int maxX = 0;
	private int minY = 0;
	private int maxY = 0;
	
	private int lineThickness = 3;
	private int length = 20;
	private int fontSize = 12;
	private int padding = 5;
	private Color color = Color.black;
	
	
	
    /**
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    
    /**
     * @param fontSize The fontSize to set.
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    
    /**
     * @param lineThickness The lineWidth to set.
     */
    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }
    
    
    /**
     * @param padding The padding to set.
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }
    
    
    /**
     * @param length The length to set.
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    
	// =======================================
	//       Constructors
	// =======================================
	
	/**
	 * Constructor
	 * @param alignmentPoint Alignment point
	 * @param label Tic mark label
	 * @param link Link associated with tic mark
	 * @param orientation Orientation of tic mark
	 * @param labelLocation Relative location of label to hatch mark
	 */
	public AxisTicMark(Point alignmentPoint, String label, URL link, Orientation orientation, 
	    Location labelLocation) {
		this.alignmentPoint = alignmentPoint;
		this.label = label;
		this.link = link;
		this.orientation = orientation;
		this.labelLocation = labelLocation;
	}
	
	
	/**
	 * Constructor
	 * @param alignmentPoint Alignment point
	 * @param label Tic mark label
	 * @param orientation Orientation of tic mark
	 * @param labelLocation Relative location of label to hatch mark
	 */
	public AxisTicMark(Point alignmentPoint, String label, Orientation orientation, 
	    Location labelLocation) {
	    this(alignmentPoint, label, null, orientation, labelLocation);
	}
	
	
	// ===================================================
	//         Public methods
	// ===================================================
	
	
	/**
	 * Draw
	 * @param canvas A drawing canvas
	 * @param drawLabel Draw label?
	 */
	public void paint(DrawingCanvas canvas, boolean drawLabel) {
	    this.setDrawingCoordinates(canvas);
	    this.drawLine(canvas);
	    if (drawLabel && this.label != null && this.label.length() > 0)
	        this.drawText(canvas);
	}
	
	
	/**
	 * Does this overlap given tic mark?
	 * @param axisTicMark An axis tic mark
	 * @param padding Padding that should be between given tic mark and this
	 * @return T/F
	 */
	public boolean overlapsWith(AxisTicMark axisTicMark, int padding) {
	    return this.overlapsHorizontally(axisTicMark, padding) && this.overlapsVertically(axisTicMark, padding);
	}
	
	
	/**
	 * Get minimum X-coordinate
	 * @return Minimum X-coordinate
	 */
	public int minX() {
	    return this.minX;
	}
	
	
	/**
	 * Get maximum X-coordinate
	 * @return Maximum X-coordinate
	 */
	public int maxX() {
	    return this.maxX;
	}
	
	
	/**
	 * Get minimum Y-coordinate
	 * @return Minimum Y-coordinate
	 */
	public int minY() {
	    return this.minY;
	}
	
	
	/**
	 * Get maximum Y-coordinate
	 * @return Maximum Y-coordinate
	 */
	public int maxY() {
	    return this.maxY;
	}
	
	
	// =============================
	//    Private methods
	// =============================
	
	
	private void setDrawingCoordinates(DrawingCanvas canvas) {
	    int textLength = 0;
	    int textHeight = 0;
	    int textPadding = 0;
	    if (this.label != null && this.label.length() > 0) {
	        textLength = canvas.renderedWidth(this.label, this.fontSize);
	        textHeight = this.fontSize;
	        textPadding = this.padding;
	    }
	    
	    // Horizontal orientation
	    if (this.orientation == Orientation.HORIZONTAL) {
	        this.lineX1 = this.alignmentPoint.x - this.length / 2;
	        this.lineY1 = this.alignmentPoint.y;
	        this.lineX2 = this.lineX1 + length;
	        this.lineY2 = this.lineY1;
	        this.textY = this.lineY1 + this.fontSize / 3;
	        this.minY = this.lineY1 - textHeight / 2;
	        this.maxY = this.lineY1 + textHeight / 2;
	        if (this.labelLocation == Location.LEFT_OF) {
	            this.textX = this.lineX1 - textLength - textPadding;
	            this.minX = this.textX;
	            this.maxX = this.lineX2;
	        } else if (this.labelLocation == Location.RIGHT_OF) {
	            this.textX = this.lineX2 + textPadding;
	            this.minX = this.lineX1;
	            this.maxX = this.lineX2 + textPadding + textLength;
	        } else
	            throw new IllegalArgumentException("Illegal combination of orientation and label location");
	    }
	    
	    // Vertical orientation
	    else if (this.orientation == Orientation.VERTICAL) {
	        this.lineX1 = this.alignmentPoint.x;
	        this.lineY1 = this.alignmentPoint.y - this.length / 2;
	        this.lineX2 = this.lineX1;
	        this.lineY2 = this.lineY1 + this.length;
	        this.textX = this.lineX1 - textLength / 2;
	        this.minX = this.lineX1 - textLength / 2;
	        this.maxX = this.lineX2 + textLength / 2;
	        if (this.labelLocation == Location.ABOVE) {
	            this.textY = this.lineY1 - textPadding;
	            this.minY = this.lineY1 - textPadding - textHeight;
	            this.maxY = this.lineY2;
	        } else if (this.labelLocation == Location.BELOW) {
	            this.textY = this.lineY2 + textPadding + textHeight;
	            this.minY = this.lineY1;
	            this.maxY = this.lineY2 + textPadding + textHeight;
	        } else
	            throw new IllegalArgumentException("Illegal combination of orientation and label location");
	    }
	}
	
	
	private void drawLine(DrawingCanvas canvas) {
	    GraphicLine line = new GraphicLine(this.lineX1, this.lineY1, this.lineX2, this.lineY2,
	        this.lineThickness, this.color);
	    canvas.add(line);
	}
	
	
	private void drawText(DrawingCanvas canvas) {
	    GraphicText text = canvas.newGraphicText(this.label, this.textX, this.textY,
	        this.fontSize, HorizontalAlignment.LEFT_JUSTIFIED, this.color);
	    if (this.link != null) {
	        text.setHyperlink(new Hyperlink(this.link));
	        text.setColor(HYPERLINK_COLOR);
	        text.setCursor(Cursor.POINTER);
	    }
	    canvas.add(text);
	}
	
	
	private boolean overlapsHorizontally(AxisTicMark ticMark, int padding) {
	    if (this.label == null || this.label.length() < 1 || ticMark.label == null || ticMark.label.length() < 1)
	        return false;
	    int leftEdge = this.minX - padding;
	    int rightEdge = this.maxX + padding;
	    return leftEdge <= ticMark.maxX && rightEdge >= ticMark.minX;
	}
	
	
	private boolean overlapsVertically(AxisTicMark ticMark, int padding) {
	    if (this.label == null || this.label.length() < 1 || ticMark.label == null || ticMark.label.length() < 1)
	        return false;
	    int topEdge = this.minY - padding;
	    int bottomEdge = this.maxY + padding;
	    return topEdge <= ticMark.maxY && bottomEdge >= ticMark.minY;
	}
}
