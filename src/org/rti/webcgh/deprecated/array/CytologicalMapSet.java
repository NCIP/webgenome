/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/CytologicalMapSet.java,v $
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Set of cytological maps
 */
public class CytologicalMapSet {
    
    
    // =============================================
    //      Attributes
    // =============================================
    
    private Long id = null;
    protected Collection cytologicalMaps = new ArrayList();
    private GenomeAssembly genomeAssembly = null;
    private Date uploadDate = null;
    

	/**
	 * @return Returns the uploadDate.
	 */
	public Date getUploadDate() {
		return uploadDate;
	}
	
	
	/**
	 * @param uploadDate The uploadDate to set.
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	
    /**
     * @return Returns the assembly.
     */
    public GenomeAssembly getGenomeAssembly() {
        return genomeAssembly;
    }
    
    
    /**
     * @param assembly The assembly to set.
     */
    public void setGenomeAssembly(GenomeAssembly assembly) {
        this.genomeAssembly = assembly;
    }
    
    
    /**
     * @return Returns the cytologicalMaps.
     */
    public Collection getCytologicalMaps() {
        return cytologicalMaps;
    }
    
    
    /**
     * @param cytologicalMaps The cytologicalMaps to set.
     */
    public void setCytologicalMaps(Collection cytologicalMaps) {
        this.cytologicalMaps = cytologicalMaps;
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
    
    
    // ===========================================
    //        Constructors
    // ===========================================
    
    /**
     * Constructor
     */
    public CytologicalMapSet() {}
    
    
    
    /**
     * Constructor
     * @param assembly Genome assembly
     * @param uploadDate Upload date
     */
    public CytologicalMapSet(GenomeAssembly assembly, Date uploadDate) {
        this.genomeAssembly = assembly;
        this.uploadDate = uploadDate;
    }
    
    
    // ==============================================
    //        Public methods
    // ==============================================
    
    /**
     * Add a cytological map ID
     * @param cytologicalMapId A cytological map ID
     */
    public void add(CytologicalMap cytologicalMapId) {
        this.cytologicalMaps.add(cytologicalMapId);
    }
    
    
    /**
     * Get printable fields
     * @return Printable fields
     */
    public String[] printableFields() {
        return this.genomeAssembly.printableFields();
    }
    
    
    /**
     * Get number of cytological maps
     * @return Number of cytological maps
     */
    public int numCytologicalMaps() {
    	int size = 0;
    	if (this.cytologicalMaps != null)
    		size = this.cytologicalMaps.size();
    	return size;
    }
    
    
    // ========================================
    //     Static methods
    // ========================================
    
    /**
     * Get printable headings
     * @return Printable headings
     */
    public static String[] printableHeadings() {
        return GenomeAssembly.printableHeadings();
    }
}
