/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $

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

import java.util.Collection;
import java.util.Set;

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
	public static final float DEF_MIN_MASK = (float) Float.MAX_VALUE;
	
	/**
	 * Default maximum mask value.
	 * Values between
	 * <code>minMask</code> and <code>maxMask</code>
	 * are filtered out of plot.
	 */
	public static final float DEF_MAX_MASK = (float) Float.MIN_VALUE;
	
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Maximum color saturation value.  Data values
	 * greater than or equal to this will be mapped to pure red
	 * (#FF0000) in the plot.
	 */
	private float maxSaturation = DEF_MAX_SATURATION;
	
	/**
	 * Minium color saturation value.  Data values
	 * less than or equal to this will be mapped to
	 * pure green (#00FF00) in the plot.
	 */
	private float minSaturation = DEF_MIN_SATURATION;
	
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
	 * Get maximum color saturation value.
	 * Data values
	 * greater than or equal to this will be mapped to pure red.
	 * (#FF0000) in the plot.
	 * @return Maximum color saturation value.
	 */
	public final float getMaxSaturation() {
		return maxSaturation;
	}

	
	/**
	 * Set maximum color saturation value.
	 * Data values
	 * greater than or equal to this will be mapped to pure red.
	 * @param maxSaturation Maximum color saturation value.
	 */
	public final void setMaxSaturation(final float maxSaturation) {
		this.maxSaturation = maxSaturation;
	}

	
	/**
	 * Get minimum color saturation value.
	 * Data values
	 * less than or equal to this will be mapped to pure green.
	 * (#00FF00) in the plot.
	 * @return Minimum color saturation value.
	 */
	public final float getMinSaturation() {
		return minSaturation;
	}

	
	/**
	 * Set minimum color saturation value.
	 * Data values
	 * less than or equal to this will be mapped to pure green.
	 * (#00FF00) in the plot.
	 * @param minSaturation Minimum color saturation value.
	 */
	public final void setMinSaturation(final float minSaturation) {
		this.minSaturation = minSaturation;
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
		this.maxSaturation = params.maxSaturation;
		this.minMask = params.minMask;
		this.minSaturation = params.minSaturation;
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
		if (Float.isNaN(this.getMinSaturation())) {
			float min = Experiment.findMinValue(experiments, chromosomes);
			this.setMinSaturation(min);
		}
		if (Float.isNaN(this.getMaxSaturation())) {
			float max = Experiment.findMaxValue(experiments, chromosomes);
			this.setMaxSaturation(max);
		}
    }
}
