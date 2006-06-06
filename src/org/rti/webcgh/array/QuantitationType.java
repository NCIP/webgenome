/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/QuantitationType.java,v $
$Revision: 1.3 $
$Date: 2006-06-06 20:08:28 $

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
import org.rti.webcgh.util.StringUtils;

/**
 * Enumerated type representing type of quantitation
 */
public class QuantitationType implements Cacheable {
	
	// ====================================
	//     Constants
	// ====================================
	
	/**
	 * Log2 ratio
	 */
	public static final QuantitationType LOG_2_RATIO = 
		new QuantitationType("Log2 Ratio");
	
	
	/**
	 * Unknown
	 */
	public static final QuantitationType UNKNOWN =
		new QuantitationType("Unknown");
	
	/**
	 * LOH
	 */
	public static final QuantitationType LOH =
		new QuantitationType("LOH");
	
	
	/**
	 * Percent
	 */
	public static final QuantitationType PERCENT =
		new QuantitationType("Percent");
    
    
    // ========================================
    //        Attributes
    // ========================================
    
    private Long id = null;
    private String name;
    
    /**
     * Get name
     * @return Name
     */
    public String getName() {
    	return name;
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
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    // =================================
    //        Constructors
    // =================================
    
    /**
     * Constructor
     */
    public QuantitationType() {}
    
    /**
     * Constructor
     * @param name Name of quantitation type
     */
    public QuantitationType(String name) {
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
		return QuantitationType.cacheKey(this.name);
	}
	
	// ==============================
	//     Methods from Object
	// ==============================
	
    /**
     * Equals methods
     * @param obj An object
     * @return T/F
     */
    public boolean equals(Object obj) {
        if (! (obj instanceof QuantitationType))
            return false;
        return StringUtils.equal(this.name, ((QuantitationType)obj).name);
    }
    
    
    /**
     * Generate hash code
     * @return Hash code
     */
    public int hashCode() {
        if (this.name == null)
            return super.hashCode();
        return this.name.hashCode();
    }
    
    
    /**
     * To string
     * @return String
     */
    public String toString() {
    	return this.name;
    }
    
    // ================================
    //     Static methods
    // ================================
    
    /**
     * Cache key
     * @param name Quantitation type name
     * @return Cache key
     */
    public static Object cacheKey(String name) {
        return "%qt%" + name;
    }

}
