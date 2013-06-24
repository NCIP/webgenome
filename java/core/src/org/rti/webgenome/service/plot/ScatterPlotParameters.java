/*
$Revision: 1.7 $
$Date: 2007-09-11 22:52:24 $


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
    
    /** Default value for minimum expression value on Y-axis. */
    public static final float DEF_EXPRESSION_MIN_Y = Constants.FLOAT_NAN;
    
    /** Default value for maximum expression value on Y-axis. */
    public static final float DEF_EXPRESSION_MAX_Y = Constants.FLOAT_NAN;
    
    /** Default value for minimum copy number value on Y-axis. */
    public static final float DEF_COPY_NUMBER_MIN_Y = Constants.FLOAT_NAN;
    
    /** Default value for maximum copy number value on Y-axis. */
    public static final float DEF_COPY_NUMBER_MAX_Y = Constants.FLOAT_NAN;
    
    /** Default value for drawing data points. */
    public static final boolean DEF_DRAW_POINTS = false;
    
    /** Default value for drawing error bars. */
    public static final boolean DEF_DRAW_ERROR_BARS = false;
    
    /** Default value for drawing expression data stems. */
    public static final boolean DEF_DRAW_STEMS = true;
    
    
    // ============================
    //      Attributes
    // ============================
    
    /** Minimum Y-axis value for copy number or LOH data specified by user. */
    private float copyNumberMinY = DEF_COPY_NUMBER_MIN_Y;

    /** Maximum Y-axis value for copy number of LOH data specified by user. */
    private float copyNumberMaxY = DEF_COPY_NUMBER_MAX_Y;
    
    /** Minimum Y-axis value for expression data specified by user. */
    private float expressionMinY = DEF_EXPRESSION_MIN_Y;
    
    /** Maximum Y-axis value for expression data specified by user. */
    private float expressionMaxY = DEF_EXPRESSION_MAX_Y;
    
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
    
    /** Draw data points. */
    private boolean drawPoints = DEF_DRAW_POINTS;
    
    /** Draw error bars. */
    private boolean drawErrorBars = DEF_DRAW_ERROR_BARS;
    
    /** Draw stems attached to diamond shaped expression points. */
    private boolean drawStems = DEF_DRAW_STEMS;
    
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
