/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/Organism.java,v $
$Revision: 1.3 $
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
import java.util.StringTokenizer;

import org.rti.webcgh.deprecated.Cacheable;
import org.rti.webcgh.util.StringUtils;

/**
 * An organism
 */
public class Organism implements Serializable, Cacheable {
    
    
    // ====================================
    //      Constants
    // ====================================
    
    /**
     * Dummy organism
     */
    public static final Organism DUMMY_ORGANISM = new Organism("", "");
    
    
    /**
     * Unknown organism
     */
    public static final Organism UNKNOWN = new Organism("Unknown", "unknown");
    
    
    /**
     * Default genus
     */
    public static final String DEFAULT_GENUS = "Homo";
    
    
    /**
     * Default species
     */
    public static final String DEFAULT_SPECIES = "sapiens";
    
    
    // ======================================================
    //      State variables with accessors and mutators
    // ======================================================
	
	private Long id = null;
    private String genus = null;
    private String species = null;
    
    
    /**
     * Set the id
     * @param id The id
     */
    public void setId(Long id) {
    	this.id = id;
    }
    
    
    /**
     * Return the id
     * @return Id
     */
    public Long getId() {
    	return this.id;
    }
    
    
    /**
     * Set the genus
     * @param genus The genus
     */
    public void setGenus(String genus) {
    	this.genus = genus;
    }
    
    /**
     * Get the genus
     * @return Genus
     */
    public String getGenus() {
        return this.genus;
    }
    
    
    /**
     * Set the species
     * @param species The species
     */
    public void setSpecies(String species) {
    	this.species = species;
    }
    
    
    /**
     * Get the species
     * @return Species
     */
    public String getSpecies() {
        return this.species;
    }
    
    
    // ======================================================
    //               Constructors
    // ======================================================
    
    
    /**
     * Constructor
     */
    public Organism() {}
    
    
    /**
     * Constructor
     * @param genus The genus
     * @param species The species
     */
    public Organism(String genus, String species) {
    	this();
    	this.genus = genus;
    	this.species = species;
    }
    
    
    /**
     * Constructor
     * @param id The id
     * @param genus The genus
     * @param species The species
     */
    public Organism(Long id, String genus, String species) {
    	this(genus, species);
    	this.id = id;
    }
    
    /**
     * Constructor
     * @param id The id   
     */
    public Organism(Long id) {    	
    	this.id = id;
    }
    
    
    // ================================
    //     Cacheable interface
    // ================================
    
	/**
	 * Get a key for the cache
	 * @return A key
	 */
	public Object getCacheKey() {
		return Organism.cacheKey(this.genus, this.species);
	}
    
    
    // =================================================
    //                Public methods
    // =================================================
    
    /**
     * Equals function.  Performs field-by-field comparison on value
     * @param obj An organism
     * @return T/F
     */
    public boolean equals(Object obj) {
    	if (! (obj instanceof Organism))
    		return false;
    	Organism org = (Organism) obj;
    	
    	if (this.id != null && this.id == org.getId()){    		
    			return true;
    	}else{	    	
    		return
				StringUtils.equal(org.getGenus(), this.genus) &&
				StringUtils.equal(org.getSpecies(), this.species);
    	}	
    }
    
    
    /**
     * Return printable string of state
     * @return Printable string
     */
    public String toPrintableString() {
    	return
			this.id + " " + this.genus + " " + this.species;
    }
    
    
    /**
     * Get display nane
     * @return Display name
     */
    public String getDisplayName() {
        return this.genus + " " + this.species;
    }
    
    
    /**
     * Get a hash key
     * @return Hash key
     */
    public String hashKey() {
        return this.genus + this.species;
    }
    
    
    // ================================================
    //             Static methods
    // ================================================
    
    
    /**
     * 
     * Generate species display name by concatenating genus and
     * species
     * @param genus Genus
     * @param species Species
     * @return Display name
     */
    public static String displayName(String genus, String species) {
        return genus + " " + species;
    }
    
    
    /**
     * Parse genus from display name
     * @param displayName Display name
     * @return Genus
     */
    public static String parseGenus(String displayName) {
        StringTokenizer tok = new StringTokenizer(displayName);
        assert tok.hasMoreTokens();
        String genus = tok.nextToken();
        return genus;
    }
    
    
    /**
     * Parse species from display name
     * @param displayName Display name
     * @return species
     */
    public static String parseSpecies(String displayName) {
        StringTokenizer tok = new StringTokenizer(displayName);
        assert tok.hasMoreTokens();
        tok.nextToken();
        assert tok.hasMoreTokens();
        String species = tok.nextToken();
        return species;
    }
    
    
    /**
     * Get printable fields
     * @return Printable fields
     */
    public String[] printableFields() {
        return new String[] {this.genus, this.species};
    }
    
    
    /**
     * Get printable headings
     * @return Printable headings
     */
    public static String[] printableHeadings() {
        return new String[] {"GENUS", "SPECIES"};
    }
    
    
    /**
     * Generate cache key
     * @param genus Genus
     * @param species Species
     * @return Cache key
     */
    public static Object cacheKey(String genus, String species) {
    	return "%og%" + genus + species;
    }

}
