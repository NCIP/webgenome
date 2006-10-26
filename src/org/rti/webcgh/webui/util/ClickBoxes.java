/*
$Revision: 1.8 $
$Date: 2006-10-26 21:32:50 $

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
package org.rti.webcgh.webui.util;

import java.awt.Point;
import java.io.Serializable;

import org.rti.webcgh.util.SystemUtils;


/**
 * This class is used by the web tier to construct a map
 * of event responses that is overlaid on a graphic.
 * Clicking in a region of the graphic corresponding
 * to an individual box, will invoke a Javascript event
 * handler that does something with the text value in that
 * box.
 */
public final class ClickBoxes implements Serializable {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
    // =============================
    //       Attributes
    // =============================
	
	/** Origin of click box region with respect to overall graphic. */
	private final Point origin;

    /**
     * Individual box width.
     */
    private final int boxWidth;

    /**
     * Individual box height.
     */
    private final int boxHeight;

    /**
     * Two-dimensional array of click boxes,
     * Indexes represent x,y box position,
     * Value is plot image key.
     */
    private final String[][] clickBox;
    
    /** Width of entire click box region in pixels. */
    private final int width;
    
    /** Height of entire click box region in pixels. */
    private final int height;
    
    /** Number of rows in clickBox matrix. */
    private final int numRows;
    
    /** Number of columns in clickBox matrix. */
    private final int numCols;


	// =========================================
    //      Constructors
    // =========================================

    /**
     * Constructor.
     * @param width Width of entire click box area in pixels.
     * @param height Height of entire click box area in pixels.
     * @param boxWidth Width of a single click box in pixels.
     * @param boxHeight Height of a single click box in pixels.
     */
    public ClickBoxes(final int width, final int height, final int boxWidth,
    		final int boxHeight) {
    	this(width, height, boxWidth, boxHeight, new Point(0, 0));
    }

    /**
     * Constructor.
     * @param width Width of entire click box area in pixels.
     * @param height Height of entire click box area in pixels.
     * @param boxWidth Width of a single click box in pixels.
     * @param boxHeight Height of a single click box in pixels.
	 * @param origin Origin of clickboxes area relative to
	 * entire graphic
     */
    public ClickBoxes(final int width, final int height, final int boxWidth,
    		final int boxHeight, final Point origin) {
    	this.width = width;
    	this.height = height;
    	this.boxWidth = boxWidth;
    	this.boxHeight = boxHeight;
    	this.numRows = (int) Math.ceil((double) height
    			/ (double) boxHeight) + 1;
    	this.numCols = (int) Math.ceil((double) width
    			/ (double) boxWidth) + 1;
    	this.clickBox = new String[this.numCols][];
    	for (int i = 0; i < this.numCols; i++) {
    		clickBox[i] = new String[this.numRows];
    	}
    	this.origin = origin;
    }


    // =========================================
    //      Getters and Setters
    // =========================================
    
    /**
     * SGet height of click boxes.
     * @return Height in pixels.
     */
    public int getHeight() {
		return height;
	}
    
    
	/**
	 * Get origin of click boxes with regards to the
	 * underlying graphic.
	 * @return Origin of click boxes.
	 */
	public Point getOrigin() {
		return origin;
	}
	
		
	/**
	 * Get width of click boxes.
	 * @return Width of click boxes in pixels.
	 */
	public int getWidth() {
		return width;
	}
	
	
    /**
	 * @return Returns the clickBox.
	 */
	public String[][] getClickBox() {
		return clickBox;
	}
	
	
	/**
	 * @return Returns the height.
	 */
	public int getBoxHeight() {
		return boxHeight;
	}

	
	/**
	 * @return Returns the width.
	 */
	public int getBoxWidth() {
		return boxWidth;
	}

	
	// ===================================
	//       Business methods
	// ===================================
	
	/**
	 * Add click box text.
	 * @param text Text to add
	 * @param x X-coordinate of some point in the click boxes
	 * @param y Y-coordinate of some point in the click boxes
	 */
	public void addClickBoxText(final String text, final int x, final int y) {
		int row = this.getRowNum(y);
		int col = this.getColNum(x);
		if (row < this.numRows && col < this.numCols && row >= 0 && col >= 0) {
			this.clickBox[col][row] = text;
		}
	}
	
	/**
	 * Get click box text.
	 * @param x X-coordinate of some point in the click boxes
	 * @param y Y-coordinate of some point in the click boxes
	 * @return Click box text
	 */
	public String getClickBoxText(final int x, final int y) {
		String text = null;
		int row = this.getRowNum(y);
		int col = this.getColNum(x);
		if (row < this.numRows && col < this.numCols && row > 0 && col > 0) {
			text = this.clickBox[col][row];
		}
		return text;
	}
	
	/**
	 * Get row number that y-pixel value falls into.
	 * @param y Y-pixel value
	 * @return Row
	 */
	private int getRowNum(final int y) {
		return (int) Math.floor((double) y / (double) this.boxHeight);
	}
	
	
	/**
	 * Get column number that x-pixel value false into.
	 * @param x X-pixel value
	 * @return Column
	 */
	private int getColNum(final int x) {
		return (int) Math.floor((double) x / (double) this.boxWidth);
	}
}
