/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/Chromosome.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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

import org.rti.webcgh.service.Cacheable;

/**
 * A chromosome
 */
public class Chromosome implements Comparable, Cacheable {
    
    
    // ==============================
    //    Attributes with accessors
    // ==============================
    
	protected Long id = null;
    protected short number = -1;
    protected long length =  -1;
    protected GenomeAssembly genomeAssembly = null;
    
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
     * @return Returns the length.
     */
    public long getLength() {
        return length;
    }
    
    
	/**
	 * @return Returns the number.
	 */
	public short getNumber() {
		return number;
	}
	
	
	/**
	 * @param number The number to set.
	 */
	public void setNumber(short number) {
		this.number = number;
	}
	
	
	/**
	 * @param length The length to set.
	 */
	public void setLength(long length) {
		this.length = length;
	}
	
	
    /**
     * @return Returns the genomeAssembly.
     */
    public GenomeAssembly getGenomeAssembly() {
        return genomeAssembly;
    }
    
    
    /**
     * @param genomeAssembly The genomeAssembly to set.
     */
    public void setGenomeAssembly(GenomeAssembly genomeAssembly) {
        this.genomeAssembly = genomeAssembly;
    }
    
    
    // ===================================
    //     Constructors
    // ===================================
    
    
	/**
	 * Constructor
	 */
	public Chromosome() {}
	
	
    /**
     * Constructor
     * @param assembly Genome assembly
     * @param number Chromosome number
     */
    public Chromosome(GenomeAssembly assembly, short number) {
    	this(assembly, number, 0);
    }
    
    /**
     * Constructor
     * @param assembly Genome assembly
     * @param number Chromosome number
     * @param length Chromosome lengths
     */
    public Chromosome(GenomeAssembly assembly, short number, long length) {
    	this.genomeAssembly = assembly;
        this.number = number;
        this.length = length;
    }
    
    
    /**
     * Constructor
     * @param chromosome A chromosome
     */
    public Chromosome(Chromosome chromosome) {
    	this.genomeAssembly = chromosome.genomeAssembly;
    	this.length = chromosome.length;
    	this.number = chromosome.number;
    }
    
    
    // ===================================
    //    Comparable interface methods
    // ===================================
    
    /**
     * Comparison
     * @param obj An object
     * @return -1 (less than), 0 (equals), +1 (greater than)
     */
    public int compareTo(Object obj) {
        if (! (obj instanceof Chromosome))
            throw new IllegalArgumentException("Expecting type 'Chromosome'");
        int value = 0;
        Chromosome chrom = (Chromosome)obj;
        if (this.number < chrom.number)
            value = -1;
        else if (this.number == chrom.number)
            value = 0;
        else if (this.number > chrom.number)
            value = 1;
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
		return Chromosome.cacheKey(this.genomeAssembly, this.number);
	}
    
    
    // =========================================
    //   Methods overridden from Object class
    // =========================================
    
    /**
     * Equals methods
     * @param obj An object
     * @return T/F
     */
    public boolean equals(Object obj) {
        if (! (obj instanceof Chromosome))
            return false;
        return this.compareTo(obj) == 0;
    }
    
    
    /**
     * Return string representation for display
     * @return A string
     */
    public String toPrettyString() {
    	return String.valueOf(this.number);
    }
    
    
    // ===============================
    //     Other public methods
    // ===============================
    
    
    /**
     * Is this in given genome assembly?
     * @param genomeAssembly Genome assembly
     * @return T/F
     */
    public boolean isIn(GenomeAssembly genomeAssembly) {
    	return this.genomeAssembly.equals(genomeAssembly);
    }
    
    
    /**
     * Is length of chromosome known?
     * @return T/F
     */
    public boolean lengthKnown() {
    	return this.length > 0;
    }
    
    
    /**
     * Convert chromosome to a genome interval
     * @return A genome interval;
     */
    public GenomeInterval toGenomeInterval() {
    	return new GenomeInterval(new GenomeLocation(this, 0), 
    			new GenomeLocation(this, this.length));
    }
    
    
    /**
     * Is this chromosome synonymous with given number?
     * @param number A number
     * @return T/F
     */
    public boolean synonymous(short number) {
    	return this.number == number;
    }
    
    
    // =============================
    //    Static methods
    // =============================
    
    /**
     * Generate cache key
     * @param assembly Genome assembly
     * @param number Chromosome number
     * @return Cache key
     */
    public static Object cacheKey(GenomeAssembly assembly, short number) {
    	return "%ch%" + assembly.getCacheKey() + number;
    }
}
