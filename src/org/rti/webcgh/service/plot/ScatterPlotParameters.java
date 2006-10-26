/*
$Revision: 1.7 $
$Date: 2006-10-26 14:47:06 $

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

package org.rti.webcgh.service.plot;

import java.io.Serializable;

import org.rti.webcgh.util.SystemUtils;

/**
 * Plot parameters specific to scatter plots.
 * @author dhall
 *
 */
public final class ScatterPlotParameters
extends PlotParameters implements Serializable {
	
	/** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // ============================
    //      Attributes
    // ============================
    
    /** Minimum Y-axis value specified by user. */
    private float minY = Float.NaN;

    /** Maximum Y-axis value specified by user. */
    private float maxY = Float.NaN;
    
    /**
     * Width of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     */
    private int width = -1;
    
    /**
     * Height of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     */
    private int height = -1;
    
    /** Draw horizontal grid lines? */
    private boolean drawHorizGridLines = true;
    
    /** Draw vertical grid lines? */
    private boolean drawVertGridLines = true;
    
    // ==========================
    //      Getters/setters
    // ==========================

    /**
     * Get height of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     * @return Height of plot area in pixels.
     */
    public int getHeight() {
		return height;
	}


    /**
     * Set height of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     * @param height Height of plot area in pixels.
     */
	public void setHeight(final int height) {
		this.height = height;
	}


    /**
     * Get width of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     * @return Width of plot area in pixels.
     */
	public int getWidth() {
		return width;
	}


    /**
     * Set width of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     * @param width Width of plot area in pixels.
     */
	public void setWidth(final int width) {
		this.width = width;
	}


	/**
	 * Draw horizontal grid lines?
	 * @return T/F
	 */
	public boolean isDrawHorizGridLines() {
		return drawHorizGridLines;
	}


	/**
	 * Sets whether horizontal grid lines should be drawn.
	 * @param drawHorizGridLines Draw horizontal grid lines?
	 */
	public void setDrawHorizGridLines(final boolean drawHorizGridLines) {
		this.drawHorizGridLines = drawHorizGridLines;
	}


	/**
	 * Draw vertical grid lines?
	 * @return T/F
	 */
	public boolean isDrawVertGridLines() {
		return drawVertGridLines;
	}


	/**
	 * Sets whether vertical grid lines should be drawn.
	 * @param drawVertGridLines Draw vertical grid lines?
	 */
	public void setDrawVertGridLines(final boolean drawVertGridLines) {
		this.drawVertGridLines = drawVertGridLines;
	}


	/**
     * Get maximum Y-axis value specified by user.
     * @return Maximum Y-axis value specified by user
     */
    public float getMaxY() {
        return maxY;
    }

    
    /**
     * Set maximum Y-axis value specified by user.
     * @param maxY Maximum Y-axis value specified by user
     */
    public void setMaxY(final float maxY) {
        this.maxY = maxY;
    }

    
    /**
     * Get minimum Y-axis value specified by user.
     * @return Minimum Y-axis value specified by user
     */
    public float getMinY() {
        return minY;
    }

    
    /**
     * Set minimum Y-axis value specified by user.
     * @param minY Minimum Y-axis value specified by user
     */
    public void setMinY(final float minY) {
        this.minY = minY;
    }
    
    // ==============================
    //        Constructors
    // ==============================

    /**
     * Constructor.
     */
    public ScatterPlotParameters() {
        super();
    }
    
    
    /**
     * Constructor.
     * @param params Scatter plot parameters.
     */
    public ScatterPlotParameters(final ScatterPlotParameters params) {
    	super(params);
    	this.height = params.height;
    	this.width = params.width;
    	this.maxY = params.maxY;
    	this.minY = params.minY;
    	this.drawHorizGridLines = params.drawHorizGridLines;
    	this.drawVertGridLines = params.drawVertGridLines;
    }
}
