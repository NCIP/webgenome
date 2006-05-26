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
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.VerticalAlignment;

/**
 * Panel for containing plot elements
 */
public class PlotPanel implements ScalePlotElement {
    
    
    // ===================================
    //        Attributes
    // ===================================
	
	private PlotElement topElement = null;
	private PlotElement bottomElement = null;
	private PlotElement leftElement = null;
	private PlotElement rightElement = null;
	private PlotElement referenceElement = null;
	private Point zeroPoint = new Point(0, 0);
    private int padding = 10;
    private Color color = new Color(235, 235, 235);
    protected DrawingCanvas drawingCanvas = null;
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
        return this.maxX() - this.minX();
    }
    
    
    /**
     * Return height of panel
     * @return Height of panel
     */
    public int height() {
        return this.maxY() - this.minY();
    }

    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        if (this.referenceElement != null)
        	return this.referenceElement.topLeftAlignmentPoint();
        int x = 0, y = 0;
        if (this.topElement != null)
        	y = this.topElement.topLeftAlignmentPoint().y;
        if (this.leftElement != null)
        	x = this.leftElement.topLeftAlignmentPoint().x;
        return new Point(x, y);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        if (this.referenceElement != null)
        	return this.referenceElement.bottomLeftAlignmentPoint();
        int x = 0, y = 0;
        if (this.bottomElement != null)
        	y = this.bottomElement.bottomLeftAlignmentPoint().y;
        if (this.leftElement != null)
        	x = this.leftElement.topLeftAlignmentPoint().x;
        return new Point(x, y);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        if (this.referenceElement != null)
        	return this.referenceElement.topRightAlignmentPoint();
        int x = 0, y = 0;
        if (this.topElement != null)
        	y = this.topElement.topRightAlignmentPoint().y;
        if (this.rightElement != null)
        	x = this.rightElement.topRightAlignmentPoint().x;
        return new Point(x, y);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        if (this.referenceElement != null)
        	return this.referenceElement.bottomRightAlignmentPoint();
        int x = 0, y = 0;
        if (this.bottomElement != null)
        	y = this.bottomElement.bottomRightAlignmentPoint().y;
        if (this.rightElement != null)
        	x = this.rightElement.bottomRightAlignmentPoint().x;
        return new Point(x, y);
    }
    
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
    	return new Point(this.minX(), this.minY());
    }
    
    
    /**
     * Move element
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(int deltaX, int deltaY) {
    	if (this.bottomElement != null) {
    		this.bottomElement.move(deltaX, deltaY);
    		if (this.rightElement != this.bottomElement)
    			this.rightElement.move(deltaX, deltaY);
    		if (this.topElement != this.bottomElement && this.topElement != this.rightElement)
    			this.topElement.move(deltaX, deltaY);
    		if (this.leftElement != this.bottomElement && this.leftElement != this.rightElement && this.leftElement != this.topElement)
    			this.leftElement.move(deltaX, deltaY);
    		this.zeroPoint.x += deltaX;
    		this.zeroPoint.y += deltaY;
    		if (this.referenceElement != null && this.referenceElement != this.bottomElement && 
    				this.referenceElement != this.rightElement && this.referenceElement != this.topElement
    				&& this.referenceElement != this.leftElement)
    			this.referenceElement.move(deltaX, deltaY);
    	}
    }
    
    
    // =================================
    //      ScalePlotElement interface
    // =================================
    
	/**
	 * Return point in pixels corresponding to the zero point
	 * in the native units of measurement represented by
	 * element.
	 * @return A point or <code>null</code> if the element
	 * does not contain a zero point
	 */
	public Point zeroPoint() {
		return this.zeroPoint;
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
     * @param makeReferenceElement Make this element the reference
     * for justifying subsequently added elements?
     */
    public void add(PlotElement element, HorizontalAlignment hAlign,
        VerticalAlignment vAlign, boolean makeReferenceElement) {
    	if (element instanceof Axis)
    		System.out.println("Before: minX = " + this.minX());
        DrawingCanvas tile = this.drawingCanvas.newTile();
        element.paint(tile);
        int x = computeInsertionXCoord(element, hAlign);
        int y = computeInsertionYCoord(element, vAlign);
        this.drawingCanvas.add(tile, x, y);
        
        // Move element's points to parent's coordinate system
        int deltaX = x;
        int deltaY = y;
        element.move(deltaX, deltaY);
        
        if (makeReferenceElement)
        	this.referenceElement = element;
        if (this.topElement == null)
        	this.topElement = this.bottomElement = this.leftElement = this.rightElement = element;
        else {
        	if (element.topLeftPoint().x < this.minX())
        		this.leftElement = element;
        	if (element.topLeftPoint().x + element.width() > this.maxX())
        		this.rightElement = element;
        	if (element.topLeftPoint().y < this.minY())
        		this.topElement = element;
        	if (element.topLeftPoint().y + element.height() > this.maxY())
        		this.bottomElement = element;
        }
        if (element instanceof Axis)
    		System.out.println("After: minX = " + this.minX());
    }
    
    
    private int minX() {
    	int min = 0;
    	if (this.leftElement != null)
    		min = this.leftElement.topLeftPoint().x;
    	return min;
    }
    
    
    private int maxX() {
    	int max = 0;
    	if (this.rightElement != null)
    		max = this.rightElement.topLeftPoint().x + this.rightElement.width();
    	return max;
    }
    
    
    private int minY() {
    	int min = 0;
    	if (this.topElement != null)
    		min = this.topElement.topLeftPoint().y;
    	return min;
    }
    
    
    private int maxY() {
    	int max = 0;
    	if (this.bottomElement != null)
    		max = this.bottomElement.topLeftPoint().y + this.bottomElement.height();
    	return max;
    }
    
    
    /**
     * Add an element
     * @param element An element
     * @param hAlign Horizontal alignment of element relative to
     * aggregate of elements already added
     * @param vAlign Vertical alignment of element relative to
     * aggregate of elements already added
     * element already added or off to one side?
     * @param makeReferenceElement Make this element the reference
     * for justifying subsequently added elements?
     */
    public void add(PlotElement element, HorizontalAlignment hAlign,
        VerticalAlignment vAlign) {
    	this.add(element, hAlign, vAlign, false);
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
        int width = 0, height = 0;
        if (location == Location.ABOVE || location == Location.BELOW) {
        	width = this.width();
        	height = delta;
        } else if (location == Location.LEFT_OF || location == Location.RIGHT_OF) {
        	width = delta;
        	height = this.height();
        }
        EmptySpace es = new EmptySpace(width, height);
        if (location == Location.ABOVE)
        	this.add(es, HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
        else if (location == Location.BELOW)
        	this.add(es, HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
        if (location == Location.LEFT_OF)
        	this.add(es, HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
        if (location == Location.RIGHT_OF)
        	this.add(es, HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
    }
    
    
    /**
     * Find rendered width of given text on this panel
     * @param text Text
     * @param fontSize Font size
     * @return Width of rendered text in pixels
     */
    public int renderedWidth(String text, int fontSize) {
    	return this.drawingCanvas.renderedWidth(text, fontSize);
    }
    
            
    
    // =======================================
    //     Private methods
    // =======================================
    
    private int computeInsertionXCoord(PlotElement element, 
    	HorizontalAlignment hAlign) {
    	if (this.topElement == null)
    		return 0;
    	int coord = 0;
    	if (hAlign == HorizontalAlignment.LEFT_JUSTIFIED)
    	    coord = this.bottomLeftAlignmentPoint().x - element.bottomLeftAlignmentPoint().x;
    	else if (hAlign == HorizontalAlignment.LEFT_OF)
    	    coord = this.minX() - this.padding - element.width();
    	else if (hAlign == HorizontalAlignment.CENTERED)
    	    coord = (this.maxX() + this.minX()) / 2 - element.width() / 2;
    	else if (hAlign == HorizontalAlignment.RIGHT_JUSTIFIED)
    	    coord = this.bottomRightAlignmentPoint().x - element.bottomRightAlignmentPoint().x;
    	else if (hAlign == HorizontalAlignment.RIGHT_OF)
    	    coord = this.maxX() + this.padding;
    	else if (hAlign == HorizontalAlignment.ON_ZERO) {
    		int alignmentX = (element instanceof ScalePlotElement)?
    				((ScalePlotElement)element).zeroPoint().x :
    					(element.width() + element.topLeftAlignmentPoint().x) / 2; 
    		coord = this.zeroPoint.x - alignmentX;
    	}
    	return coord;
    }
    
    
    private int computeInsertionYCoord(PlotElement element, 
    	VerticalAlignment vAlign) {
    	if (this.topElement == null)
    		return 0;
        int coord = 0;
        if (vAlign == VerticalAlignment.ABOVE)
            coord = this.minY() - this.padding - element.height() - element.topLeftPoint().y;
        else if (vAlign == VerticalAlignment.BELOW)
            coord = this.maxY() + this.padding - element.topLeftPoint().y;
        else if (vAlign == VerticalAlignment.BOTTOM_JUSTIFIED)
            coord = this.bottomLeftAlignmentPoint().y - element.bottomLeftAlignmentPoint().y;
        else if (vAlign == VerticalAlignment.CENTERED)
            coord = (this.maxY() + this.minY()) / 2 - element.height() / 2;
        else if (vAlign == VerticalAlignment.TOP_JUSTIFIED)
            coord = this.topLeftAlignmentPoint().y - element.topLeftAlignmentPoint().y;
        else if (vAlign == VerticalAlignment.ON_ZERO) {
        	int alignY = (element instanceof ScalePlotElement)?
        			((ScalePlotElement)element).zeroPoint().y :
        				(element.height() + element.bottomLeftAlignmentPoint().y) / 2;
        	coord = this.zeroPoint.y - alignY;
        }
    	return coord;
    }
}
