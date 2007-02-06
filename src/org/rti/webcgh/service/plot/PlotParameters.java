/*
$Revision: 1.15 $
$Date: 2007-02-06 16:12:28 $

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
import org.rti.webcgh.units.BpUnits;

/**
 * Represents parameters for a particular type of plot.
 * @author dhall
 *
 */
public class PlotParameters {
	
	//
	//     STATICS
	//
	
	/** Default number of plots per row. */
	public static final int DEF_NUM_PLOTS_PER_ROW = 5;

	
	//
	//     ATTRIBUTES
	//
	
    /** Plot name. */
    private String plotName = null;
    
    /** Number of plots in a row of plots. */
    private int numPlotsPerRow = DEF_NUM_PLOTS_PER_ROW;
    
    
    /**
     * Units for <code>startLocation</code>
     * and <code>endLocation</code> fields.
     */
    private BpUnits units = BpUnits.BP;
    
    /** Genome intervals to plot. */
    private List<GenomeInterval> genomeIntervals =
    	new ArrayList<GenomeInterval>();
    
    
    //
    //     GETTERS/SETTERS
    //

    
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

	
	//
	//     CONSTRUCTORS
	//
	
	
	/**
	 * Constructor.
	 */
	public PlotParameters() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param params Plot parameters
	 */
	public PlotParameters(final PlotParameters params) {
		this.plotName = params.plotName;
		this.numPlotsPerRow = params.numPlotsPerRow;
		for (GenomeInterval interval : params.genomeIntervals) {
    		this.add(new GenomeInterval(interval));
    	}
    	this.units = params.units;
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
