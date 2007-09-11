/*
$Revision: 1.1 $
$Date: 2007-09-11 22:52:24 $

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

package org.rti.webgenome.service.plot;

import java.io.Serializable;
import java.util.Collection;

import org.rti.webgenome.core.Constants;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.util.SystemUtils;

/**
 * Parameters for genomic snapsot plots.  These types
 * plots are really just a special case of a scatter
 * plot.  Several
 * attributes are not used from scatter plot parameters.
 * @author dhall
 *
 */
public class GenomeSnapshopPlotParameters extends BaseGenomicPlotParameters
implements Serializable {
	
	//
	//  C O N S T A N T S
	//
	
	/** Default minimum Y-axis value. */
	public static final float DEF_MIN_Y = Constants.FLOAT_NAN;
	
	/** Default maximum Y-axis value. */
	public static final float DEF_MAX_Y = Constants.FLOAT_NAN;

	/** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    //
    //  A T T R I B U T E S
    //
    
    /** Minimum Y-axis value. */
    private float minY = DEF_MIN_Y;
    
    /** Maximum Y-axis value. */
    private float maxY = DEF_MAX_Y;
    
    /**
     * Width of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     */
    private int width = DEF_WIDTH;
    
    /**
     * Height of plot area in pixels.  Plot area
     * does not include axes, legend, etc.
     */
    private int height = -1;
    
    /** Draw horizontal grid lines? */
    private boolean drawHorizGridLines = DEF_DRAW_HORIZ_GRID_LINES;
    
    /** Draw vertical grid lines? */
    private boolean drawVertGridLines = DEF_DRAW_VERT_GRID_LINES;

    //
    //  G E T T E R S / S E T T E R S
    //
    
    /**
     * Get maximum Y-axis value.
     * @return Maximum Y-axis value.
     */
	public float getMaxY() {
		return maxY;
	}

	/**
	 * Set maximum Y-axis value.
	 * @param maxY Maximum Y-axis value.
	 */
	public void setMaxY(final float maxY) {
		this.maxY = maxY;
	}

	/**
	 * Get minimum Y-axis value.
	 * @return Minimum Y-axis value.
	 */
	public float getMinY() {
		return minY;
	}

	/**
	 * Set minimum Y-axis value.
	 * @param minY Minimum Y-axis value.
	 */
	public void setMinY(final float minY) {
		this.minY = minY;
	}
	
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
    
    
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public GenomeSnapshopPlotParameters() {
		
	}
	
	/**
	 * Constructor that performs a deep copy.
	 * @param params Parameters from which to deep copy
	 */
	public GenomeSnapshopPlotParameters(
			final GenomeSnapshopPlotParameters params) {
		super(params);
		this.minY = params.minY;
		this.maxY = params.maxY;
		this.height = params.height;
    	this.width = params.width;
    	this.drawHorizGridLines = params.drawHorizGridLines;
    	this.drawVertGridLines = params.drawVertGridLines;
	}
	
	
	//
	//  O V E R R I D E S
	//
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlotParameters deepCopy() {
		return new GenomeSnapshopPlotParameters(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deriveMissingAttributes(
			final Collection<Experiment> experiments) {
		super.deriveMissingAttributes(experiments);
		if (this.minY == Constants.FLOAT_NAN) {
			this.minY = Experiment.findMinValue(experiments);
		}
		if (this.maxY == Constants.FLOAT_NAN) {
			this.maxY = Experiment.findMaxValue(experiments);
		}
	}
}
