/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/ReporterMapping.java,v $
$Revision: 1.4 $
$Date: 2006-09-05 14:06:45 $

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

import java.io.Serializable;

import org.rti.webcgh.deprecated.Cacheable;
import org.rti.webcgh.graph.DataPoint;

/**
 * Array probe
 */
public class ReporterMapping implements Comparable, Locatable, Cacheable, Serializable {
    
    
    // =====================================
    //         Attributes with accessors
    // =====================================
    
    private Long id = null;
    private GenomeLocation genomeLocation = null;
    private Reporter reporter = null;
    
    
	/**
	 * @return Returns the reporter.
	 */
	public Reporter getReporter() {
		return reporter;
	}
	
	
	/**
	 * @param reporter The reporter to set.
	 */
	public void setReporter(Reporter reporter) {
		this.reporter = reporter;
	}
	
	
    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @param genomeLocation The genomeLocation to set.
     */
    public void setGenomeLocation(GenomeLocation genomeLocation) {
        this.genomeLocation = genomeLocation;
    }
    
        
    
    // ======================
    //     Constructors
    // ======================
    
    /**
     * Constructor
     */
    public ReporterMapping() {}
    
    /**
     * Constructor
     * @param reporter a reporter
     * @param genomeLocation Genome location
     */
    public ReporterMapping(Reporter reporter,
    		GenomeLocation genomeLocation) {
        this.reporter = reporter;
        this.genomeLocation = genomeLocation;
    }
    
    
    // ================================
    //   Methods in Comparable
    // ================================
    
    
    /**
     * Comparison method
     * @param obj An object
     * @return -1 (less than), 0 (equals), +1 (greater than)
     */
    public int compareTo(Object obj) {
        if (! (obj instanceof Locatable))
            throw new IllegalArgumentException("Expecting 'Locatable' object");
        return this.genomeLocation.compareTo(((Locatable)obj).getGenomeLocation());
    }
    
    
    // ======================================
    //      Methods in Locatable
    // ======================================
    
    /**
     * Get genome location
     * @return Genome location
     */
    public GenomeLocation getGenomeLocation() {
    	return this.genomeLocation;
    }
    
    
    /**
     * Is locatable to right of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean rightOf(Locatable locatable) {
    	return this.genomeLocation.rightOf(locatable);
    }
    
    
    /**
     * Is locatable to left of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean leftOf(Locatable locatable) {
    	return this.genomeLocation.leftOf(locatable);
    }
    
    
    /**
     * Is locatable in same location as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameLocation(Locatable locatable) {
    	return this.genomeLocation.sameLocation(locatable);
    }
    
    
    /**
     * Is locatable on same chromosome as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameChromosome(Locatable locatable) {
    	return this.genomeLocation.sameChromosome(locatable);
    }
    
    
    /**
     * Minimum chromosome length possible given data contained within
     * @return Minimum chromosome length possible given data contained within
     */
    public long minChromosomeLength() {
    	return this.genomeLocation.minChromosomeLength();
    }
    
    
    // ================================
    //     Cacheable interface
    // ================================
    
	/**
	 * Get a key for the cache
	 * @return A key
	 */
	public Object getCacheKey() {
		return "%rm%" + this.reporter.getCacheKey();
	}
    
    
    // =========================================
    //      Public methods
    // =========================================
    
    
    /**
     * Initialize data point location
     * @param dataPoint A data point
     */
    public void initializeDataPointLocation(DataPoint dataPoint) {
        dataPoint.setLabel(this.reporter.getName());
        this.genomeLocation.initalizeDataPointLocation(dataPoint);
    }
    
    
    /**
     * Is this in given genome assembly?
     * @param genomeAssembly Genome assembly
     * @return T/F
     */
    public boolean isIn(GenomeAssembly genomeAssembly) {
    	return this.genomeLocation.isIn(genomeAssembly);
    }
    
    
    /**
     * Get chromosme
     * @return Chromosome
     */
    public Chromosome chromosome() {
    	return this.genomeLocation.getChromosome();
    }
    
    
    /**
     * Expand range denoted by DTO if this outside
     * @param dto A DTO
     */
    public void expand(GenomeIntervalDto dto) {
    	this.genomeLocation.expand(dto);
    }
    
    
    /**
     * Set genome assembly
     * @param genomeAssembly A genome assembly
     */
    public void setGenomeAssembly(GenomeAssembly genomeAssembly) {
    	this.genomeLocation.setGenomeAssembly(genomeAssembly);
    }
    
    
    // =========================================
    //      Static methods
    // =========================================
    
    
    /**
     * Generate cache key
     * @param reporter a reporter
     * @return Cache key
     */
    public static Object cacheKey(Reporter reporter) {
        return "%rm%" + reporter.getCacheKey();
    }

}
