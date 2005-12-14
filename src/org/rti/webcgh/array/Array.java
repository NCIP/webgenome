/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/Array.java,v $
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

import java.util.ArrayList;
import java.util.List;


/**
 * A microarray
 */
public class Array {
	
	
	// =========================
	//      Constants
	// =========================
	
	public static final Array UNKNOWN = new Array("Unknown", "unknown", Organism.UNKNOWN);
	
	
	// =============================
	//     Attributes
	// =============================
	
	private Long id = null;
	private String vendor = null;
	private String name = null;
	protected List reporters = new ArrayList();
	private Organism organism = null;

	
    /**
     * @return Returns the organism.
     */
    public Organism getOrganism() {
        return organism;
    }
    
    
    /**
     * @param organism The organism to set.
     */
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }
    
    
	/**
	 * @return Returns the reporters.
	 */
	public List getReporters() {
		return reporters;
	}
	
	
	/**
	 * @param reporters The reporters to set.
	 */
	public void setReporters(List reporters) {
		this.reporters = reporters;
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
	 * @return Returns the vendor.
	 */
	public String getVendor() {
		return vendor;
	}
	
	
	/**
	 * @param vendor The vendor to set.
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	
	// ============================
	//     Constructors
	// ============================
	
	
	/**
	 * Constructor
	 */
	public Array() {
		super();
	}
	
	
	/**
	 * Constructor
	 * @param vendor Vendor
	 * @param name Array name
	 * @param organism Organism
	 */
	public Array(String vendor, String name, Organism organism) {
		super();
		this.vendor = vendor;
		this.name = name;
		this.organism = organism;
	}
	
	
	// =============================
	//      Public methods
	// =============================
	
	/**
	 * Add a reporter
	 * @param reporter A reporter
	 */
	public void add(Reporter reporter) {
		this.reporters.add(reporter);
	}
	
	
	/**
	 * Get printable field values
	 * @return Printable field values
	 */
	public String[] printableFields() {
	    return new String[] {this.vendor, this.name};
	}
	
	
	/**
	 * Number of reporters
	 * @return Number of reporters
	 */
	public int numReporters() {
		int num = 0;
		if (this.reporters != null)
			num = this.reporters.size();
		return num;
	}
	
	
	/**
	 * Display name
	 * @return Display name
	 */
	public String getDisplayName() {
		return this.vendor + " " + this.name;
	}
	
	
	// ====================================
	//       Static methods
	// ====================================
	
	/**
	 * Get printable headings
	 * @return Printable headings
	 */
	public static String[] printableHeadings() {
	    return new String[] {"ARRAY VENDOR", "ARRAY"};
	}

}
