/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/Reporter.java,v $
$Revision: 1.4 $
$Date: 2006-06-19 19:37:42 $

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

import org.rti.webcgh.graph.DataPoint;
import org.rti.webcgh.service.Cacheable;

/**
 * A reporter
 */
public class Reporter implements Cacheable, Locatable, Serializable {
	
	// ==============================
	//    Attributes
	// ==============================
	
	private Long id = null;
	private String name = null;
	private ReporterMapping reporterMapping = null;
    private String [] associatedGenes = null;
    private String [] annotations = null;
    private boolean selected = false;
	

    /**
     * @return Returns the reporterMapping.
     */
    public ReporterMapping getReporterMapping() {
        return reporterMapping;
    }
    
    
    /**
     * @param reporterMapping The reporterMapping to set.
     */
    public void setReporterMapping(ReporterMapping reporterMapping) {
        this.reporterMapping = reporterMapping;
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
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
    

    /**
     * Sets the display properties
     * @param annotations
     * @param associatedGenes
     * @param selected Is reporter selected?
     */
	public void setDisplayProperties(String [] annotations, String [] associatedGenes, boolean selected) {
            this.annotations = annotations;
            this.associatedGenes = associatedGenes;
            this.selected = selected;
    }

    // ==================================
    //      Constructors
    // ==================================
    

    /**
	 * Constructor
	 */
	public Reporter() {}
	
	/**
	 * Constructor
	 * @param name Name
	 */
	public Reporter(String name) {
		this.name = name;
	}
	
	
    // ================================
    //     Cacheable interface
    // ================================
    
	/**
	 * Get a key for the cache
	 * @return A key
	 */
	public Object getCacheKey() {
		return Reporter.cacheKey(this.name);
	}
	
	
	// ===============================
	//   Comparable interface
	// ===============================
	
    /**
     * Comparison method
     * @param obj An object
     * @return -1 (less than), 0 (equals), +1 (greater than)
     */
    public int compareTo(Object obj) {
    	if (obj == null)
    		throw new IllegalArgumentException("Locatable object cannot be null");
        if (! (obj instanceof Locatable)) {
        	String msg = "Expecting a 'Locatable' object.  " +
				"This is type '" + this.getClass().getName() + "'.  " +
				"Comparison object is type '" + obj.getClass().getName() + "'.";
        	throw new IllegalArgumentException(msg);
        }
        return this.reporterMapping.compareTo((Locatable)obj);
    }
    
    
    // ============================
    //   Methods in Locatable
    // ============================
    
    /**
     * Get genome location
     * @return Genome location
     */
    public GenomeLocation getGenomeLocation() {
    	if (this.reporterMapping == null)
    		return null;
    	return this.reporterMapping.getGenomeLocation();
    }
    
    
    /**
     * Is locatable to right of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean rightOf(Locatable locatable) {
    	return this.reporterMapping.rightOf(locatable);
    }
    
    
    /**
     * Is locatable to left of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean leftOf(Locatable locatable) {
    	return this.reporterMapping.leftOf(locatable);
    }
    
    
    /**
     * Is locatable in same location as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameLocation(Locatable locatable) {
    	return this.reporterMapping.sameChromosome(locatable);
    }
    
    
    /**
     * Is locatable on same chromosome as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameChromosome(Locatable locatable) {
    	return this.reporterMapping.sameChromosome(locatable);
    }
	
	
	// =======================================
	//       Public methods
	// =======================================
	
    /**
     * Set properties in given data point corresponding to location
     * @param dataPoint A data point
     */
    public void initializeDataPointLocation(DataPoint dataPoint) {
    	this.reporterMapping.initializeDataPointLocation(dataPoint);
    	if (this.associatedGenes != null && this.associatedGenes.length > 0) {
    		int count = 0;
    		StringBuffer addendum = new StringBuffer(" - GENES:");
    		for (int i = 0; i < this.associatedGenes.length; i++) {
    			String name = this.associatedGenes[i];
    			if (name != null && name.length() > 0) {
    				addendum.append(" " + name);
    				count++;
    			}
    		}
    		if (count > 0)
    			dataPoint.setLabel(dataPoint.getLabel() + addendum.toString());
    	}
    }
    
    
    /**
     * Does this reporter have a position in the genome?
     * @return T/F
     */
    public boolean hasLocation() {
        return this.reporterMapping != null;
    }
    
    
    /**
     * Place in location in genome
     * @param reporterMapping Reporter mapping
     */
    public void locateInGenome(ReporterMapping reporterMapping) {
        this.reporterMapping = reporterMapping;
    }
    
    
    /**
     * Is reporter mapped to given genome assembly?
     * @param genomeAssembly A genome assembly
     * @return T/F
     */
    public boolean isMapped(GenomeAssembly genomeAssembly) {
    	boolean mapped = false;
    	if (this.reporterMapping != null)
    		mapped = this.reporterMapping.isIn(genomeAssembly);
    	return mapped;
    }
    
    
    /**
     * Get chromosome
     * @return Chromosome
     */
    public Chromosome chromosome() {
    	Chromosome c = null;
    	if (this.reporterMapping != null)
    		c = this.reporterMapping.chromosome();
    	return c;
    }
    
    
    /**
     * Minimum chromosome length possible given data contained within
     * @return Minimum chromosome length possible given data contained within
     */
    public long minChromosomeLength () {	
    	long min = 0;
    	if (this.reporterMapping != null)
    		min = this.reporterMapping.minChromosomeLength();
    	return min;
    }
    
    
    /**
     * Expand range given by DTO if this falls outside
     * @param dto A DTO
     */
    public void expand(GenomeIntervalDto dto) {
    	if (this.reporterMapping != null)
    		this.reporterMapping.expand(dto);
    }
    
    
    /**
     * Set the genome assembly.  If the reporter has not been mapped to
     * a physical location, the method does nothing.
     * @param genomeAssembly A genome assembly
     */
    public void setGenomeAssembly(GenomeAssembly genomeAssembly) {
    	if (this.reporterMapping != null)
    		this.reporterMapping.setGenomeAssembly(genomeAssembly);
    }
    
    
    // =====================================
    //    Static methods
    // =====================================
    
    /**
     * Generate cache key
     * @param reporterName Reporter name
     * @return Cache key
     */
    public static Object cacheKey(String reporterName) {
    	return "%rp%" + reporterName;
    }
    
}
