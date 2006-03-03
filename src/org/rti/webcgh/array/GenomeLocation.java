/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/GenomeLocation.java,v $
$Revision: 1.2 $
$Date: 2006-03-03 15:29:47 $

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

import org.rti.webcgh.graph.DataPoint;
import org.rti.webcgh.service.Cacheable;

/**
 * Location in the genome
 */
public class GenomeLocation implements Comparable, Locatable, Cacheable {
    
    
    // =============================
    //     Attributes
    // =============================
    
	private Long id = null;
    private Chromosome chromosome = null;
    private long location = -1;
    
    
	/**
	 * @return Returns the chromosome.
	 */
	public Chromosome getChromosome() {
		return chromosome;
	}
	
	
	/**
	 * @param chromosome The chromosome to set.
	 */
	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
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
	 * @return Returns the location.
	 */
	public long getLocation() {
		return location;
	}
	
	
	/**
	 * @param location The location to set.
	 */
	public void setLocation(long location) {
		this.location = location;
	}
	
	
	
    // ==============================
    //    Constructors
    // ==============================
	
	
	/**
	 * Constructor
	 */
	public GenomeLocation() {}
    
    /**
     * Constructor
     * @param chromosome Chromosome
     * @param location Position on chromosome
     */
    public GenomeLocation(Chromosome chromosome, long location) {
        this.chromosome = chromosome;
        this.location = location;
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
        Locatable locatable = (Locatable)obj;
        GenomeLocation genLoc = locatable.getGenomeLocation();
        int value = this.chromosome.compareTo(genLoc.chromosome);
        if (value == 0) {
            if (this.location < genLoc.location)
                value = -1;
            else if (this.location > genLoc.location)
                value = 1;
        }
        return value;
    }
    
    
    // ================================
    //     Cacheable interface
    // ================================
    
	/**
	 * Get a key for the cache
	 * @return A key
	 */
	public Object getCacheKey() {
		return "%gl%" + this.chromosome.getCacheKey() + this.location;
	}
    
    
    // ==================================
    //     Methods in Locatable
    // ==================================
    
    
    /**
     * Get genome location
     * @return Genome location
     */
    public GenomeLocation getGenomeLocation() {
    	return this;
    }
    
    
    /**
     * Is locatable to right of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean rightOf(Locatable locatable) {
    	return this.compareTo(locatable) > 0;
    }
    
    
    /**
     * Is locatable to left of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean leftOf(Locatable locatable) {
    	return this.compareTo(locatable) < 0;
    }
    
    
    /**
     * Is locatable in same location as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameLocation(Locatable locatable) {
    	return this.compareTo(locatable) == 0;
    }
    
    
    /**
     * Is locatable on same chromosome as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameChromosome(Locatable locatable) {
    	return this.chromosome.equals(locatable.getGenomeLocation().chromosome);
    }
    
    
    /**
     * Minimum chromosome length possible given data contained within
     * @return Minimum chromosome length possible given data contained within
     */
    public long minChromosomeLength() {
    	long min = this.location;
    	if (this.chromosome.getLength() > min)
    		min = this.chromosome.getLength();
    	return min;
    }
    
    
    // =============================
    //   Public methods
    // =============================
    
    /**
     * Is location on same chromosome as this?
     * @param location A genome location
     * @return T/F
     */
    public boolean sameChromosome(GenomeLocation location) {
    	return this.chromosome.equals(location.chromosome);
    }
    
    /**
     * Is this equal to given locatable object?
     * @param object An object
     * @return T/F
     */
    public boolean equals(Object object) {
    	if (! (object instanceof GenomeLocation))
    		throw new IllegalArgumentException("Expecting a 'GenomeLocation'");
    	return
			this.compareTo((GenomeLocation)object) == 0;
    }
            
    
    /**
     * Initialize data point location
     * @param dataPoint A data point
     */
    public void initalizeDataPointLocation(DataPoint dataPoint) {
        dataPoint.setValue1(this.location);
    }
    
    
    /**
     * Create new data point
     * @param y Y-axis value
     * @return Data point
     */
    public DataPoint newDataPoint(double y) {
    	return new DataPoint((double)this.location, y);
    }
    
    
    /**
     * Get distance in BP separating this from given location
     * @param genomeLocation A genome location
     * @return Distance in BP
     * @throws IllegalArgumentException if given location is not
     * on the same chromosome as this
     */
    public int distanceFrom(GenomeLocation genomeLocation) {
    	if (! this.chromosome.equals(genomeLocation.chromosome))
    		throw new IllegalArgumentException("Comparison of genome " +
    				"locations not allowed if they are from different chromosomes");
    	return (int)Math.abs(this.location - genomeLocation.location);
    }
    
    
    /**
     * Convert to string for display
     * @return String
     */
    public String toPrettyString() {
    	return this.chromosome.toPrettyString();
    }
    
    
    /**
     * Is this in given genome assembly?
     * @param genomeAssembly Genome assembly
     * @return T/F
     */
    public boolean isIn(GenomeAssembly genomeAssembly) {
    	return this.chromosome.isIn(genomeAssembly);
    }
    
    
    /**
     * Expand range denoted by DTO if this outside
     * @param dto A DTO
     */
    public void expand(GenomeIntervalDto dto) {
    	if (this.chromosome.synonymous((short)dto.getChromosome())) {
    		if (this.location < dto.getStart())
    			dto.setStart(this.location);
    		if (this.location > dto.getEnd())
    			dto.setEnd(this.location);
    	}
    }
    
    
    /**
     * Set genome assembly
     * @param genomeAssembly A genome assembly
     */
    public void setGenomeAssembly(GenomeAssembly genomeAssembly) {
    	this.chromosome.setGenomeAssembly(genomeAssembly);
    }
}
