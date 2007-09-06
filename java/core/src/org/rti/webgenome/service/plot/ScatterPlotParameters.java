/*
$Revision: 1.5 $
$Date: 2007-09-06 21:51:48 $

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
import java.util.Set;

import org.rti.webgenome.core.Constants;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.util.SystemUtils;

/**
 * Plot parameters specific to scatter plots.
 * @author dhall
 *
 */
public class ScatterPlotParameters
extends BaseGenomicPlotParameters implements Serializable {
	
	/** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // ============================
    //      Attributes
    // ============================
    
    /** Minimum Y-axis value for copy number or LOH data specified by user. */
    private float copyNumberMinY = Constants.FLOAT_NAN;

    /** Maximum Y-axis value for copy number of LOH data specified by user. */
    private float copyNumberMaxY = Constants.FLOAT_NAN;
    
    /** Minimum Y-axis value for expression data specified by user. */
    private float expressionMinY = Constants.FLOAT_NAN;
    
    /** Maximum Y-axis value for expression data specified by user. */
    private float expressionMaxY = Constants.FLOAT_NAN;
    
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
    
    /** Draw data points. */
    private boolean drawPoints = false;
    
    /** Draw error bars. */
    private boolean drawErrorBars = false;
    
    /** Draw stems attached to diamond shaped expression points. */
    private boolean drawStems = false;
    
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
	 * Will stems attached to diamond shaped expression points
	 * be drawn?
	 * @return T/F
	 */
    public boolean isDrawStems() {
		return drawStems;
	}


    /**
     * Set if stems attached to diamond shaped expression points
     * will be drawn.
     * @param drawStems Whether stems are drawn
     */
	public void setDrawStems(final boolean drawStems) {
		this.drawStems = drawStems;
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
	 * Draw error bars?
	 * @return T/F
	 */
	public boolean isDrawErrorBars() {
		return drawErrorBars;
	}


	/**
	 * Set whether error bars should be drawn.
	 * @param drawErrorBars Draw error bars?
	 */
	public void setDrawErrorBars(final boolean drawErrorBars) {
		this.drawErrorBars = drawErrorBars;
	}


	/**
	 * Draw data points?
	 * @return T/F
	 */
	public boolean isDrawPoints() {
		return drawPoints;
	}


	/**
	 * Set whether data points should be drawn.
	 * @param drawPoints Draw data points?
	 */
	public void setDrawPoints(final boolean drawPoints) {
		this.drawPoints = drawPoints;
	}

	/**
	 * Get maximum Y-axis value for copy number data.
	 * @return Maximum Y-axis value
	 */
	public float getCopyNumberMaxY() {
		return copyNumberMaxY;
	}


	/**
	 * Set maximum Y-axis value for copy number data.
	 * @param copyNumberMaxY Maximum Y-axis value
	 */
	public void setCopyNumberMaxY(final float copyNumberMaxY) {
		this.copyNumberMaxY = copyNumberMaxY;
	}


	/**
	 * Get minimum Y-axis value for copy number data.
	 * @return Minimum Y-axis value
	 */
	public float getCopyNumberMinY() {
		return copyNumberMinY;
	}


	/**
	 * Set minimum Y-axis value for copy number data.
	 * @param copyNumberMinY Minimum Y-axis value
	 */
	public void setCopyNumberMinY(final float copyNumberMinY) {
		this.copyNumberMinY = copyNumberMinY;
	}


	/**
	 * Get maximum Y-axis value for expression data.
	 * @return Maximum Y-axis value
	 */
	public float getExpressionMaxY() {
		return expressionMaxY;
	}


	/**
	 * Set maximum Y-axis value for expression data.
	 * @param expressionMaxY Maximum Y-axis value
	 */
	public void setExpressionMaxY(final float expressionMaxY) {
		this.expressionMaxY = expressionMaxY;
	}


	/**
	 * Get minimum Y-axis value for expression data.
	 * @return Minimum Y-axis value
	 */
	public float getExpressionMinY() {
		return expressionMinY;
	}


	/**
	 * Set minimum Y-axis value for expression data.
	 * @param expressionMinY Minimum Y-axis value
	 */
	public void setExpressionMinY(final float expressionMinY) {
		this.expressionMinY = expressionMinY;
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
    	this.copyNumberMaxY = params.copyNumberMaxY;
    	this.copyNumberMinY = params.copyNumberMinY;
    	this.expressionMaxY = params.expressionMaxY;
    	this.expressionMinY = params.expressionMinY;
    	this.drawHorizGridLines = params.drawHorizGridLines;
    	this.drawVertGridLines = params.drawVertGridLines;
    	this.drawErrorBars = params.drawErrorBars;
    	this.drawPoints = params.drawPoints;
    	this.drawStems = params.drawStems;
    }
    
    
    //
    //     OVERRIDES
    //
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deriveMissingAttributes(
    		final Collection<Experiment> experiments) {
    	super.deriveMissingAttributes(experiments);
    	Set<Short> chromosomes = GenomeInterval.getChromosomes(
				this.getGenomeIntervals());
		if (Float.isNaN(this.getExpressionMinY())
				|| this.getExpressionMinY() == Constants.FLOAT_NAN) {
			float min = Experiment.findMinExpressionValue(
					experiments, chromosomes);
			this.setExpressionMinY(min);
		}
		if (Float.isNaN(this.getExpressionMaxY())
				|| this.getExpressionMaxY() == Constants.FLOAT_NAN) {
			float max = Experiment.findMaxExpressionValue(
					experiments, chromosomes);
			this.setExpressionMaxY(max);
		}
		
		if (Float.isNaN(this.getCopyNumberMinY())
				|| this.getCopyNumberMinY() == Constants.FLOAT_NAN) {
			float min = Experiment.findMinCopyNumberValue(
					experiments, chromosomes);
			this.setCopyNumberMinY(min);
		}
		if (Float.isNaN(this.getCopyNumberMaxY())
				|| this.getCopyNumberMaxY() == Constants.FLOAT_NAN) {
			float max = Experiment.findMaxCopyNumberValue(
					experiments, chromosomes);
			this.setCopyNumberMaxY(max);
		}
    }
    
    
	//
	//     ABSTRACTS
	//
	
    /**
     * Return clone of this object derived by deep copy of
     * all attributes.
     * @return Clone of this object
     */
    public PlotParameters deepCopy() {
    	return new ScatterPlotParameters(this);
    }
}
