/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/GenomeFeatureType.java,v $
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

import java.net.URL;

/**
 * Type of genome feature
 */
public class GenomeFeatureType {
    
    
    // ===========================================
    //    Attributes
    // ===========================================
    
    private Long id = null;
    private String name = null;
    private boolean representsGene = false;
    private URL url = null;
    

	/**
	 * @return Returns the url.
	 */
	public URL getUrl() {
		return url;
	}
	
	
	/**
	 * @param url The url to set.
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	
	
	/**
	 * @return Returns the representsGene.
	 */
	public boolean isRepresentsGene() {
		return representsGene;
	}
	
	
	/**
	 * @param representsGene The representsGene to set.
	 */
	public void setRepresentsGene(boolean representsGene) {
		this.representsGene = representsGene;
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
    
    
    // ====================================
    //      Constructors
    // ====================================
    
    
    /**
     * Constructor
     */
    public GenomeFeatureType() {
        super();
    }
    
    
    
    /**
     * Constructor
     * @param name Name of feature type
     * @param representsGene Is feature type a gene?
     */
    public GenomeFeatureType(String name, boolean representsGene) {
        super();
        this.name = name;
        this.representsGene = representsGene;
    }
    
    
    // =====================================
    //      Public methods
    // =====================================
    
    /**
     * Is this type equivalent to given name?
     * @param name Name
     * @return T/F
     */
    public boolean equivalentTo(String name) {
    	return this.name.equals(name);
    }
    
    
    /**
     * Get URL of feature in UCSC genome browser
     * @return URL
     */
    public URL ucscUrl() {
    	return null;
    }
}
