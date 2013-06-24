/*
$Revision: 1.4 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.service.plot;

import java.util.Collection;
import java.util.Set;

import org.rti.webgenome.core.Constants;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;

/**
 * Parameters that are relevant to plots where data values
 * are represented by a color coding system.
 * @author dhall
 *
 */
public abstract class HeatMapPlotParameters extends BaseGenomicPlotParameters {
	
	//
	//     STATICS
	//
	
	/**
	 * Default minimum color saturation value.
	 * Data values
	 * less than or equal to <code>minSaturation</code>
	 * will be mapped to
	 * pure green (#00FF00) in the plot.
	 */
	public static final float DEF_MIN_SATURATION = (float) -1.0;
	
	/**
	 * Default maximum color saturation value.
	 * Data values
	 * greater than or equal to <code>maxSaturation</code>
	 * will be mapped to pure red
	 * (#FF0000) in the plot.
	 */
	public static final float DEF_MAX_SATURATION = (float) 1.0;
	
	/**
	 * Default minimum mask value.  Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 */
	public static final float DEF_MIN_MASK = Constants.BIG_FLOAT;
	
	/**
	 * Default maximum mask value.
	 * Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 */
	public static final float DEF_MAX_MASK = Constants.SMALL_FLOAT;
	
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Maximum color saturation value for expression data.  Data values
	 * greater than or equal to this will be mapped to pure red
	 * (#FF0000) in the plot.
	 */
	private float expressionMaxSaturation = DEF_MAX_SATURATION;
	
	/**
	 * Minium color saturation value for expression data.  Data values
	 * less than or equal to this will be mapped to
	 * pure green (#00FF00) in the plot.
	 */
	private float expressionMinSaturation = DEF_MIN_SATURATION;
	
	/**
	 * Maximum color saturation value for copy number data.  Data values
	 * greater than or equal to this will be mapped to pure red
	 * (#FF0000) in the plot.
	 */
	private float copyNumberMaxSaturation = DEF_MAX_SATURATION;
	
	/**
	 * Minium color saturation value for copy number data.  Data values
	 * less than or equal to this will be mapped to
	 * pure green (#00FF00) in the plot.
	 */
	private float copyNumberMinSaturation = DEF_MIN_SATURATION;
	
	/**
	 * Minimum mask value.  Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 */
	private float minMask = DEF_MIN_MASK;
	
	/**
	 * Maximum mask value.  Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 */
	private float maxMask = DEF_MAX_MASK;
	
	
	//
	//     GETTERS/SETTERS
	//
	
	/**
	 * Set maximum mask.  Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 * @return Maximum mask value
	 */
	public final float getMaxMask() {
		return maxMask;
	}

	
	/**
	 * Set maximum mask.  Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 * @param maxMask Maximum mask
	 */
	public final void setMaxMask(final float maxMask) {
		this.maxMask = maxMask;
	}


	/**
	 * Get minimum mask.  Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 * @return Minimum mask
	 */
	public final float getMinMask() {
		return minMask;
	}


	/**
	 * Set minimum mask.  Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 * @param minMask Minimum
	 */
	public final void setMinMask(final float minMask) {
		this.minMask = minMask;
	}


	/**
	 * Get maximum color saturation value for expression data.
	 * Data values
	 * greater than or equal to this will be mapped to pure red.
	 * (#FF0000) in the plot.
	 * @return Maximum color saturation value.
	 */
	public final float getExpressionMaxSaturation() {
		return expressionMaxSaturation;
	}

	
	/**
	 * Set maximum color saturation value for expression data.
	 * Data values
	 * greater than or equal to this will be mapped to pure red.
	 * @param maxSaturation Maximum color saturation value.
	 */
	public final void setExpressionMaxSaturation(final float maxSaturation) {
		this.expressionMaxSaturation = maxSaturation;
	}

	
	/**
	 * Get minimum color saturation value for expression data.
	 * Data values
	 * less than or equal to this will be mapped to pure green.
	 * (#00FF00) in the plot.
	 * @return Minimum color saturation value.
	 */
	public final float getExpressionMinSaturation() {
		return expressionMinSaturation;
	}

	
	/**
	 * Set minimum color saturation value for expression data.
	 * Data values
	 * less than or equal to this will be mapped to pure green.
	 * (#00FF00) in the plot.
	 * @param minSaturation Minimum color saturation value.
	 */
	public final void setExpressionMinSaturation(final float minSaturation) {
		this.expressionMinSaturation = minSaturation;
	}
	
	
	/**
	 * Get maximum color saturation value for copy number data.
	 * Data values
	 * greater than or equal to this will be mapped to pure red.
	 * (#FF0000) in the plot.
	 * @return Maximum color saturation value.
	 */
	public final float getCopyNumberMaxSaturation() {
		return copyNumberMaxSaturation;
	}

	
	/**
	 * Set maximum color saturation value for copy number data.
	 * Data values
	 * greater than or equal to this will be mapped to pure red.
	 * @param maxSaturation Maximum color saturation value.
	 */
	public final void setCopyNumberMaxSaturation(final float maxSaturation) {
		this.copyNumberMaxSaturation = maxSaturation;
	}

	
	/**
	 * Get minimum color saturation value for copy number data.
	 * Data values
	 * less than or equal to this will be mapped to pure green.
	 * (#00FF00) in the plot.
	 * @return Minimum color saturation value.
	 */
	public final float getCopyNumberMinSaturation() {
		return copyNumberMinSaturation;
	}

	
	/**
	 * Set minimum color saturation value for copy number data.
	 * Data values
	 * less than or equal to this will be mapped to pure green.
	 * (#00FF00) in the plot.
	 * @param minSaturation Minimum color saturation value.
	 */
	public final void setCopyNumberMinSaturation(final float minSaturation) {
		this.copyNumberMinSaturation = minSaturation;
	}

	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 */
	public HeatMapPlotParameters() {
		super();
	}
	
	
	/**
	 * Constructor that performs deep copy of given parameter.
	 * @param params Parameter whose properties will be deep copied.
	 */
	public HeatMapPlotParameters(final HeatMapPlotParameters params) {
		super(params);
		this.maxMask = params.maxMask;
		this.copyNumberMaxSaturation = params.copyNumberMaxSaturation;
		this.copyNumberMinSaturation = params.copyNumberMinSaturation;
		this.expressionMaxSaturation = params.expressionMaxSaturation;
		this.expressionMinSaturation = params.expressionMinSaturation;
		this.minMask = params.minMask;
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
    /**
     * Derive any attributes not supplied by the user
     * from the given experiments.
     * @param experiments Experiments from which to derive
     * attributes not supplied by user.
     */
    public void deriveMissingAttributes(
    		final Collection<Experiment> experiments) {
		super.deriveMissingAttributes(experiments);
		Set<Short> chromosomes = GenomeInterval.getChromosomes(
				this.getGenomeIntervals());
		if (Float.isNaN(this.getExpressionMinSaturation())
				|| this.getExpressionMinSaturation() == Constants.FLOAT_NAN) {
			float min = Experiment.findMinExpressionValue(
					experiments, chromosomes);
			this.setExpressionMinSaturation(min);
		}
		if (Float.isNaN(this.getExpressionMaxSaturation())
				|| this.getExpressionMaxSaturation() == Constants.FLOAT_NAN) {
			float max = Experiment.findMaxExpressionValue(
					experiments, chromosomes);
			this.setExpressionMaxSaturation(max);
		}
		if (Float.isNaN(this.getCopyNumberMinSaturation())
				|| this.getCopyNumberMinSaturation() == Constants.FLOAT_NAN) {
			float min = Experiment.findMinCopyNumberValue(
					experiments, chromosomes);
			this.setCopyNumberMinSaturation(min);
		}
		if (Float.isNaN(this.getCopyNumberMaxSaturation())
				|| this.getCopyNumberMaxSaturation() == Constants.FLOAT_NAN) {
			float max = Experiment.findMaxCopyNumberValue(
					experiments, chromosomes);
			this.setCopyNumberMaxSaturation(max);
		}
    }
}
