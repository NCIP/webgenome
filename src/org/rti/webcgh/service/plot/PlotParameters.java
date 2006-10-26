/*
$Revision: 1.6 $
$Date: 2006-10-26 21:52:33 $

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

import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.units.BpUnits;


/**
 * Plotting parameters set by user.
 * @author dhall
 *
 */
public class PlotParameters {
	
	// =======================
	//   Constants
	// =======================
	
	/** Default number of plots per row. */
	public static final int DEF_NUM_PLOTS_PER_ROW = 5;
	
	/**
	 * Default threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 */
	public static final float DEF_LOH_THRESHOLD = (float) 0.5;
    
    // ==========================
    //       Attributes
    // ==========================
    
    /**
     * Units for <code>startLocation</code>
     * and <code>endLocation</code> fields.
     */
    private BpUnits units = BpUnits.BP;
    
    /** Genome intervals to plot. */
    private List<GenomeInterval> genomeIntervals =
    	new ArrayList<GenomeInterval>();
    
    /** Number of plots in a row of plots. */
    private int numPlotsPerRow = DEF_NUM_PLOTS_PER_ROW;
    
    /** Quantitation type to plot. */
    private QuantitationType quantitationType = QuantitationType.LOG_2_RATIO;
    
    /** Plot name. */
    private String plotName = null;
    
    /**
     * Threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
     */
    private float lohThreshold = DEF_LOH_THRESHOLD;
    
    /**
     * Interpolate the endpoints of LOH regions.  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
     */
    private boolean interpolateLohEndpoints = false;
    
    /** Draw raw LOH probabilities along with scored data? */
    private boolean drawRawLohProbabilities = true;
    
    // ===========================
    //    Getters/setters
    // ===========================

    /**
     * Get quantitation type.
     * @return Quantitation type.
     */
    public final QuantitationType getQuantitationType() {
		return quantitationType;
	}


    /**
     * Set quantitation type.
     * @param quantitationType Quantitation type
     */
	public final void setQuantitationType(
			final QuantitationType quantitationType) {
		this.quantitationType = quantitationType;
	}


	/**
	 * Draw raw LOH probabilities?
	 * @return T/F
	 */
	public final boolean isDrawRawLohProbabilities() {
		return drawRawLohProbabilities;
	}


	/**
	 * Set whether to draw raw LOH probabilities.
	 * @param drawRawLohProbabilities Draw raw LOH probabilities?
	 */
	public final void setDrawRawLohProbabilities(
			final boolean drawRawLohProbabilities) {
		this.drawRawLohProbabilities = drawRawLohProbabilities;
	}


	/**
	 * Interpolate the endpoints of LOH regions?  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @return T/F
	 */
	public final boolean isInterpolateLohEndpoints() {
		return interpolateLohEndpoints;
	}


	/**
	 * Set whether to interpolate the endpoints of LOH regions.  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @param interpolateLohEndpoints Interpolate LOH endpoints?
	 */
	public final void setInterpolateLohEndpoints(
			final boolean interpolateLohEndpoints) {
		this.interpolateLohEndpoints = interpolateLohEndpoints;
	}


	/**
	 * Get threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @return LOH threshold probability.
	 */
	public final float getLohThreshold() {
		return lohThreshold;
	}


	/**
	 * Set threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @param lohThreshold LOH threshold probability.
	 */
	public final void setLohThreshold(final float lohThreshold) {
		this.lohThreshold = lohThreshold;
	}


	/**
	 * Get plot name.
	 * @return Plot name.
	 */
	public final String getPlotName() {
		return plotName;
	}


	/**
	 * Set plot name.
	 * @param plotName Plot name.
	 */
	public final void setPlotName(final String plotName) {
		this.plotName = plotName;
	}


	/**
     * Get number of plots in a row of plots.
     * @return Number of plots in a row of plots.
     */
    public final int getNumPlotsPerRow() {
		return numPlotsPerRow;
	}


    /**
     * Set number of plots in a row of plots.
     * @param numPlotsPerRow Number of plots in a row of plots.
     */
	public final void setNumPlotsPerRow(final int numPlotsPerRow) {
		this.numPlotsPerRow = numPlotsPerRow;
	}


	/**
     * Get genome intervals to plot.
     * @return Genome intervals to plot
     */
    public final List<GenomeInterval> getGenomeIntervals() {
		return genomeIntervals;
	}


    /**
     * Set genome intervals to plot.
     * @param genomeIntervals Genome intervals to plot
     */
	public final void setGenomeIntervals(
			final List<GenomeInterval> genomeIntervals) {
		this.genomeIntervals = genomeIntervals;
	}


	/**
     * Get units of <code>startLocation</code>
     * and <code>endLocation</code> fields.
     * @return Units
     */
    public final BpUnits getUnits() {
        return units;
    }

    
    /**
     * Set units of <code>startLocation</code>
     * and <code>endLocation</code> fields.
     * @param units Units of <code>startLocation</code>
     * and <code>endLocation</code> fields
     */
    public final void setUnits(final BpUnits units) {
        this.units = units;
    }

    
    // ==================================
    //       Constructors
    // ==================================
    
    /**
     * Constructor.
     */
    public PlotParameters() {
        
    }
    
    
    /**
     * Constructor that performs a deep copy of input
     * plot parameters.
     * @param params Plot parameters
     */
    public PlotParameters(final PlotParameters params) {
    	this();
    	for (GenomeInterval interval : params.genomeIntervals) {
    		this.add(new GenomeInterval(interval));
    	}
    	this.numPlotsPerRow = params.numPlotsPerRow;
    	this.plotName = params.plotName;
    	this.quantitationType = params.quantitationType;
    	this.units = params.units;
    	this.lohThreshold = params.lohThreshold;
    	this.interpolateLohEndpoints = params.interpolateLohEndpoints;
    	this.drawRawLohProbabilities = params.drawRawLohProbabilities;
    }
    
    
    // ===========================
    //     Business methods
    // ===========================
    
    /**
     * Add genome interval.
     * @param genomeInterval Genome interval to add
     */
    public final void add(final GenomeInterval genomeInterval) {
    	this.genomeIntervals.add(genomeInterval);
    }
}
