/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/GenomeAssembly.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:34:38 $

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
package org.rti.webcgh.deprecated.array;

import java.io.Serializable;

import org.rti.webcgh.deprecated.Cacheable;
import org.rti.webcgh.util.CollectionUtils;
import org.rti.webcgh.util.StringUtils;

/**
 * A genome assembly
 */
public class GenomeAssembly implements Serializable, Cacheable {
    
    
    // =====================================
    //     Constants
    // =====================================
    
    /**
     * Dummy genome assembly
     */
    public static final GenomeAssembly 
    	DUMMY_GENOME_ASSEMBLY = new GenomeAssembly("", Organism.DUMMY_ORGANISM);
    
    private static final String UNSPECIFIED_ASSEMBLY_TAG = "Unspecified";
    
    
    // =======================================================
    //         State variables with accessors and mutators
    // =======================================================
    
	protected Long id = null;
    private String name = null;
    private Organism organism = null;
    
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
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
     * Get name of assembly
     * @return Name of assembly
     */
    public String getName() {
        return this.name;
    }
	
	
	/**
	 * @param organism The organism to set.
	 */
	public void setOrganism(Organism organism) {
		this.organism = organism;
	}
	
    /**
     * Get name of organism
     * @return Organism name
     */
    public Organism getOrganism() {
        return this.organism;
    }
    
    
    // =============================================================
    //                  Constructors
    // =============================================================
    
    
    /**
     * Constructor
     */
    public GenomeAssembly() {}
    
    
    /**
     * Constructor
     * @param name Name of genome assembly
     * @param organism Organism
     */
    public GenomeAssembly(String name, Organism organism) {
        this();
        this.name = name;
        this.organism = organism;
    }
    
    /**
     * Constructor
     * @param id Assembly id
     * @param name Name of genome assembly
     * @param organism Organism
     */
    public GenomeAssembly(Long id, String name, Organism organism) {
        this(name, organism);
    	this.id = id;
    }
    
    /**
     * Constructor
     * @param id
     */
    public GenomeAssembly(Long id){
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
		return "%ga%" + this.organism.getCacheKey() + this.name;
	}
    
        
    
    // ============================================================
    //              Public methods
    // ============================================================
    
    /**
     * Value-based equals
     * @param genome A genome
     * @return T/F
     */
    public boolean equals(GenomeAssembly genome) {
        return
        	StringUtils.equal(genome.name, this.name) &&
        	genome.organism.equals(this.organism);
    }
    
    
    /**
     * Return display name
     * @return Display name
     */
    public String getDisplayName() {
    	return this.organism.getDisplayName() + " " + this.name;
    }
    
    
    /**
     * Get printable fields
     * @return Printable fields
     */
    public String[] printableFields() {
        return CollectionUtils.concatenate(this.organism.printableFields(), new String[] {this.name});
    }
    
    
    /**
     * Get a hash key
     * @return Hash key
     */
    public String hashKey() {
        return this.name + this.organism.hashKey();
    }
    
    
    /**
     * Is assembly unspecified?
     * @return T/F
     */
    public boolean unspecified() {
    	return GenomeAssembly.UNSPECIFIED_ASSEMBLY_TAG.equals(this.name);
    }
    
    
    // ========================================
    //      Static methods
    // ========================================
    
    /**
     * Get printable headings
     * @return Printable headings
     */
    public static String[] printableHeadings() {
        return CollectionUtils.concatenate(Organism.printableHeadings(), new String[] {"ASSEMBLY"});
    }
    
    /**
     * @return true if object is equal to the one passed as parameter
     */
    public boolean equals(Object other) {
    	if ( !(other instanceof GenomeAssembly) ) return false;
        if ( (this.getId() == ((GenomeAssembly)other).getId() ) ) return true;
        
        return false;
    }


}
