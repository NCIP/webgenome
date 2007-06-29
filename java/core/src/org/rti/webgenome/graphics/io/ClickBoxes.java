/*
$Revision: 1.2 $
$Date: 2007-06-29 21:47:51 $

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
package org.rti.webgenome.graphics.io;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.StringTokenizer;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.util.DbUtils;
import org.rti.webgenome.util.SystemUtils;


/**
 * This class is used by the web tier to construct a map
 * of event responses that is overlaid on a graphic.
 * Clicking in a region of the graphic corresponding
 * to an individual box, will invoke a Javascript event
 * handler that does something with the text value in that
 * box.
 */
public class ClickBoxes implements Serializable {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
    // =============================
    //       Attributes
    // =============================
	
	/** Primary key value for persistence. */
	private Long id = null;
	
	/** Origin of click box region with respect to overall graphic. */
	private Point origin = new Point();

    /**
     * Individual box width.
     */
    private int boxWidth = 0;

    /**
     * Individual box height.
     */
    private int boxHeight = 0;

    /**
     * Two-dimensional array of click boxes,
     * Indexes represent x,y box position,
     * Value is plot image key.
     */
    private String[][] clickBox = null;
    
    /** Number of rows in clickBox matrix. */
    private int numRows = 0;
    
    /** Number of columns in clickBox matrix. */
    private int numCols = 0;


	// =========================================
    //      Constructors
    // =========================================
    
    /**
     * Constructor.
     */
    public ClickBoxes() {
    	
    }

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
     * Get primary key value used for persistence.
     * @return Primary key value
     */
    public Long getId() {
		return id;
	}

    /**
     * Set primary key value used for persistence.
     * @param id Primary key value
     */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
     * Set height of individual boxes.
     * @param boxHeight Height of boxes in pixels.
     */
	public void setBoxHeight(final int boxHeight) {
		this.boxHeight = boxHeight;
	}

	/**
	 * Set width of individual boxes.
	 * @param boxWidth Width of boxes in pixels.
	 */
	public void setBoxWidth(final int boxWidth) {
		this.boxWidth = boxWidth;
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
	 * Get click boxes in a text format for storing
	 * as a CLOB.  This text can be decoded by the method
	 * {@code setClickBoxesAsClob}.
	 * @return CLOB representation for attribute
	 * {@code clickBox}
	 */
	public String getClickBoxAsClob() {
		return DbUtils.encodeClob(this.clickBox);
	}
	
	
	/**
	 * Set the values of the {@code clickBox} matrix property
	 * with a CLOB-encoded varsion generated by method
	 * {@code getClickBoxAsClob}.
	 * @param clob CLOB-encoding of {@code clickBox}
	 * property value.
	 */
	public void setClickBoxAsClob(final String clob) {
		try {
			this.clickBox = DbUtils.decodeClob(clob);
		} catch (WebGenomeApplicationException e) {
			throw new WebGenomeSystemException(
					"Error un-persisting click box text", e);
		}
	}
	
	
	/**
	 * Get X-coordinate of click boxes origin.
	 * @return X-coordinate of origin in pixels.
	 */
	public int getOriginX() {
		return this.origin.x;
	}
	
	/**
	 * Set X-coordinate of click boxes origin.
	 * @param x X-coordinate of origin in pixels
	 */
	public void setOriginX(final int x) {
		this.origin.x = x;
	}
	
	/**
	 * Get Y-coordinate of click boxes origin.
	 * @return Y-coordinate of origin in pixels.
	 */
	public int getOriginY() {
		return this.origin.y;
	}
	
	/**
	 * Set Y-coordinate of click boxes origin.
	 * @param y Y-coordinate of origin in pixels
	 */
	public void setOriginY(final int y) {
		this.origin.y = y;
	}
	
	/**
	 * Get height of entire click box area in pixels.
	 * @return Height in pixels
	 */
	public int getHeight() {
		int height = 0;
		if (this.clickBox != null) {
			height = this.boxHeight * this.clickBox.length;
		}
		return height;
	}
	
	/**
	 * Get width of entire click box area in pixels.
	 * @return Width in pixels
	 */
	public int getWidth() {
		int width = 0;
		if (this.clickBox != null) {
			if (this.clickBox[0] != null && this.clickBox[0].length > 0) {
				width = this.boxWidth * this.clickBox[0].length; 
			}
		}
		return width;
	}
	
	
	/**
	 * Debug method that generates readable string
	 * of click box text.
	 * @return Readable string of click box text.
	 */
	public String toPrettyString() {
		StringBuffer buff = new StringBuffer();
		if (this.clickBox != null) {
			for (int i = 0; i < this.clickBox.length; i++) {
				if (this.clickBox[i] != null) {
					for (int j = 0; j < this.clickBox[i].length; j++) {
						buff.append(this.clickBox[i][j]);
						if (j < this.clickBox[i].length - 1) {
							buff.append(",");
						}
					}
				}
				buff.append("\n");
			}
		}
		return buff.toString();
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
