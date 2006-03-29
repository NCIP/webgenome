/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/GenomeInterval.java,v $
$Revision: 1.3 $
$Date: 2006-03-29 22:26:30 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package org.rti.webcgh.array;

import org.rti.webcgh.graph.PlotBoundaries;
import org.rti.webcgh.service.Cacheable;

/**
 * An interval within the genome
 */
public class GenomeInterval {
    
    
    // ==============================
    //       Attributes
    // ==============================
    
    /**
     * Starting point
     */
    public GenomeLocation start = null;
    
    /**
     * Ending point
     */
    public GenomeLocation end = null;
    
    
    // ==================================
    //      Constructors
    // ==================================
    
    
    public GenomeInterval(){}
    
    /**
     * Constructor
     * @param start Start
     * @param end End
     */
    public GenomeInterval(GenomeLocation start, GenomeLocation end) {
        if (! start.sameChromosome(end))
            throw new IllegalArgumentException("Genome interval cannot span different chromosomes");
        this.start = start;
        this.end = end;
    }
    
    
    // ===================================
    //      Public methods
    // ===================================
    
    /**
     * Create new plot boundaries
     * @param minY Minimum Y-axis value
     * @param maxY Maximum Y-axis value
     * @return new Plot boundaries
     */
    public PlotBoundaries newPlotBoundaries(double minY, double maxY) {
    	return new PlotBoundaries(this.start.newDataPoint(minY), 
    			this.end.newDataPoint(maxY));
    }
    
    
    /**
     * Span in base pairs represented by interval
     * @return Span in base pairs represented by interval
     */
    public long span() {
    	return this.end.distanceFrom(this.start);
    }
    
    
    /**
     * String representation for display
     * @return String
     */
    public String toPrettyString() {
    	return this.start.toPrettyString();
    }
    
    
    /**
     * Get chromosome
     * @return Chromosome
     */
    public Chromosome chromosome() {
        return this.start.getChromosome();
    }
    
    
    /**
     * Start BP
     * @return Start BP
     */
    public long startBp() {
        return this.start.getLocation();
    }
    
    
    /**
     * End BP
     * @return End BP
     */
    public long endBp() {
        return this.end.getLocation();
    }
    
    
    /**
     * Union genome interval
     * @param ival Genome interval
     */
    public void union(GenomeInterval ival) {
    	if (ival.start.leftOf(this.start))
    		this.start = ival.start;
    	if (ival.end.rightOf(this.end))
    		this.end = ival.end;
    }
    
    
    /**
     * Find intersection and set this equal to it.
     * @param ival An interval
     */
    public void intersection(GenomeInterval ival) {
    	if (this.overlap(ival)) {
	    	if (ival.start.rightOf(this.start))
	    		this.start = ival.start;
	    	if (ival.end.leftOf(this.end))
	    		this.end = ival.end;
    	} else
    		throw new IllegalArgumentException(
    				"Intervals must be overlapping to compute intersection");
    }
    
    
    /**
     * Do intervals overlap?
     * @param ival An interval
     * @return T/F
     */
    public boolean overlap(GenomeInterval ival) {
    	return this.start.leftOf(ival.end) && this.end.rightOf(ival.start);
    }
    
    
    /**
     * Bulk set properties
     * @param ival Source of properties
     */
    public void bulkSet(GenomeInterval ival) {
    	this.start = ival.start;
    	this.end = ival.end;
    }
}
