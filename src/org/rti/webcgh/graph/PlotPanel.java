/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/PlotPanel.java,v $
$Revision: 1.2 $
$Date: 2006-03-29 22:26:30 $

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
package org.rti.webcgh.graph;

import java.awt.Color;
import java.awt.Point;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.VerticalAlignment;

/**
 * Panel for containing plot elements
 */
public class PlotPanel implements PlotElement {
    
    
    // ===================================
    //        Attributes
    // ===================================
    private int minX = 0;
    private int maxX = 0;
    private int minY = 0;
    private int maxY = 0;
    private Point topLeftAlignmentPoint = new Point(0, 0);
    private Point bottomLeftAlignmentPoint = new Point(0, 0);
    private Point topRightAlignmentPoint = new Point(0, 0);
    private Point bottomRightAlignmentPoint = new Point(0, 0);
    private int padding = 10;
    private Color color = new Color(235, 235, 235);
    private DrawingCanvas drawingCanvas = null;
    private int numElements = 0;
    private int margins = 0;
    
    
    /**
	 * @param margins The margins to set.
	 */
	public void setMargins(int margins) {
		this.margins = margins;
	}


	/**
     * @param padding The padding to set.
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }
    
    
    /**
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    
    // ================================
    //        Constructors
    // ================================
    
    
    /**
     * Constructor
     * @param drawingCanvas A drawing canvas
     */
    public PlotPanel(DrawingCanvas drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
    }
    
    
    // ======================================
    //     Methods in PlotElement interface
    // ======================================
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
        canvas.add(this.drawingCanvas);
    }
    
    /**
     * Return width of panel
     * @return Width of panel
     */
    public int width() {
        return maxX - minX;
    }
    
    
    /**
     * Return height of panel
     * @return Height of panel
     */
    public int height() {
        return maxY - minY;
    }

    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return this.topLeftAlignmentPoint;
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return this.bottomLeftAlignmentPoint;
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return this.topRightAlignmentPoint;
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return this.bottomRightAlignmentPoint;
    }
    
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    // ========================================
    //        Public methods
    // ========================================
    
    /**
     * Add an element
     * @param element An element
     * @param hAlign Horizontal alignment of element relative to
     * aggregate of elements already added
     * @param vAlign Vertical alignment of element relative to
     * aggregate of elements already added
     * element already added or off to one side?
     */
    public void add(PlotElement element, HorizontalAlignment hAlign,
        VerticalAlignment vAlign) {
        DrawingCanvas tile = this.drawingCanvas.newTile();
        element.paint(tile);
        int x = computeInsertionXCoord(element, hAlign);
        int y = computeInsertionYCoord(element, vAlign);
        this.drawingCanvas.add(tile, x, y);
        this.numElements++;
        this.adjustBoundaries(element, hAlign, vAlign);
    }
    
    
    /**
     * Create new plot panel as child of this
     * @return Plot panel
     */
    public PlotPanel newChildPlotPanel() {
    	return new PlotPanel(this.drawingCanvas.newTile());
    }
    
    
    /**
     * Add extra padding
     * @param numPaddingUnits Num padding units
     * @param location Location
     */
    public void addExtraPadding(int numPaddingUnits, Location location) {
        int delta = this.padding * numPaddingUnits;
        if (location == Location.ABOVE) {
            this.minY -= delta;
            this.topLeftAlignmentPoint.y -= delta;
            this.topRightAlignmentPoint.y -= delta;
        } else if (location == Location.BELOW) {
            this.maxY += delta;
            this.bottomLeftAlignmentPoint.y += delta;
            this.bottomRightAlignmentPoint.y += delta;
        } else if (location == Location.LEFT_OF) {
            this.minX -= delta;
            this.topLeftAlignmentPoint.x -= delta;
            this.bottomLeftAlignmentPoint.x -= delta;
        } else if (location == Location.RIGHT_OF) {
            this.maxX += delta;
            this.topRightAlignmentPoint.x += delta;
            this.bottomRightAlignmentPoint.x += delta;
        }
    }
    
            
    
    // =======================================
    //     Private methods
    // =======================================
    
    private int computeInsertionXCoord(PlotElement element, 
    	HorizontalAlignment hAlign) {
    	if (this.numElements < 1)
    		return 0;
    	int coord = 0;
    	if (hAlign == HorizontalAlignment.LEFT_JUSTIFIED)
    	    coord = this.bottomLeftAlignmentPoint.x - element.bottomLeftAlignmentPoint().x;
    	else if (hAlign == HorizontalAlignment.LEFT_OF)
    	    coord = this.minX - this.padding - element.width();
    	else if (hAlign == HorizontalAlignment.CENTERED)
    	    coord = (this.maxX + this.minX) / 2 - element.width() / 2;
    	else if (hAlign == HorizontalAlignment.RIGHT_JUSTIFIED)
    	    coord = this.bottomRightAlignmentPoint.x - element.bottomRightAlignmentPoint().x;
    	else if (hAlign == HorizontalAlignment.RIGHT_OF)
    	    coord = this.maxX + this.padding;
    	return coord;
    }
    
    
    private int computeInsertionYCoord(PlotElement element, 
    	VerticalAlignment vAlign) {
    	if (this.numElements < 1)
    		return 0;
        int coord = 0;
        if (vAlign == VerticalAlignment.ABOVE)
            coord = this.minY - this.padding - element.height() - element.topLeftPoint().y;
        else if (vAlign == VerticalAlignment.BELOW)
            coord = this.maxY + this.padding - element.topLeftPoint().y;
        else if (vAlign == VerticalAlignment.BOTTOM_JUSTIFIED)
            coord = this.bottomLeftAlignmentPoint.y - element.bottomLeftAlignmentPoint().y;
        else if (vAlign == VerticalAlignment.CENTERED)
            coord = (this.maxY + this.minY) / 2 - element.height() / 2;
        else if (vAlign == VerticalAlignment.TOP_JUSTIFIED)
            coord = this.topLeftAlignmentPoint.y - element.topLeftAlignmentPoint().y;
    	return coord;
    }
    
    
    private void adjustBoundaries(PlotElement element,
    		HorizontalAlignment hAlign, VerticalAlignment vAlign) {
        if (this.numElements == 1) {
            this.bottomLeftAlignmentPoint = element.bottomLeftAlignmentPoint();
            this.bottomRightAlignmentPoint = element.bottomRightAlignmentPoint();
            this.topLeftAlignmentPoint = element.topLeftAlignmentPoint();
            this.topRightAlignmentPoint = element.topRightAlignmentPoint();
            this.minX = element.topLeftPoint().x;
            this.minY = element.topLeftPoint().y;
            this.maxX = this.minX + element.width();
            this.maxY = this.minY + element.height();
        } else {
	        this.adjustXBoundaries(element, hAlign);
	        this.adjustYBoundaries(element, vAlign);
        }
    }
    

    private void adjustXBoundaries(PlotElement element, HorizontalAlignment hAlign) {
    	int elementMinX = 0;
    	if (hAlign == HorizontalAlignment.LEFT_OF)
    		elementMinX = this.minX - this.padding - element.width();
    	else if (hAlign == HorizontalAlignment.LEFT_JUSTIFIED)
    		elementMinX = this.topLeftAlignmentPoint.x - 
			(element.topLeftAlignmentPoint().x - element.topLeftPoint().x);
    	else if (hAlign == HorizontalAlignment.CENTERED)
    		elementMinX = (this.minX + this.maxX) / 2 - element.width() / 2;
    	else if (hAlign == HorizontalAlignment.RIGHT_JUSTIFIED)
    		elementMinX = this.topRightAlignmentPoint.x -
			(element.topRightAlignmentPoint().x - element.topLeftPoint().x);
    	else if (hAlign == HorizontalAlignment.RIGHT_OF)
    		elementMinX = this.maxX + this.padding;
    	int elementMaxX = elementMinX + element.width();
    	if (elementMinX < this.minX)
    		this.minX = elementMinX;
    	if (elementMaxX > this.maxX)
    		this.maxX = elementMaxX;
    }
    
    
    private void adjustYBoundaries(PlotElement element, VerticalAlignment vAlign) {
    	int elementMinY = 0;
    	if (vAlign == VerticalAlignment.ABOVE)
    		elementMinY = this.minY - this.padding - element.height();
    	else if (vAlign == VerticalAlignment.TOP_JUSTIFIED)
    		elementMinY = this.topLeftAlignmentPoint.y -
			(element.topLeftAlignmentPoint().y - element.topLeftPoint().y);
    	else if (vAlign == VerticalAlignment.CENTERED)
    		elementMinY = (this.maxY + this.minY) / 2 - element.height() / 2;
    	else if (vAlign == VerticalAlignment.BOTTOM_JUSTIFIED)
    		elementMinY = this.bottomLeftAlignmentPoint.y -
			(element.bottomLeftAlignmentPoint().y - element.topLeftPoint().y);
    	else if (vAlign == VerticalAlignment.BELOW)
    		elementMinY = this.maxY + this.padding;
    	int elementMaxY = elementMinY + element.height();
    	if (elementMinY < this.minY)
    		this.minY = elementMinY;
    	if (elementMaxY > this.maxY)
    		this.maxY = elementMaxY;
    }
}
