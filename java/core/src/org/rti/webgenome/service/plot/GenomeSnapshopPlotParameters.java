/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-09-11 22:52:24 $


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
