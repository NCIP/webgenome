/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/Cytoband.java,v $
$Revision: 1.8 $
$Date: 2006-09-23 05:02:23 $

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

import org.rti.webcgh.graphics.util.ColorMapper;
import org.rti.webcgh.graphics.widget.ChromosomeIdeogram;

/**
 * A band within a cytogenetic map
 */
public class Cytoband {
    
    
    // =================================
    //     Attributes
    // =================================
    
	private Long id = null;
	private String name = null;
	private long start = -1;
	private long end = -1;
    private String stain;
    
	/**
	 * @return Returns the end.
	 */
	public long getEnd() {
		return end;
	}
	
	
	/**
	 * @param end The end to set.
	 */
	public void setEnd(long end) {
		this.end = end;
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
	 * @return Returns the start.
	 */
	public long getStart() {
		return start;
	}
	
	
	/**
	 * @param start The start to set.
	 */
	public void setStart(long start) {
		this.start = start;
	}
	
	
    /**
     * @return Returns the stain.
     */
    public String getStain() {
        return stain;
    }
    
    
    /**
     * @param stain The stain to set.
     */
    public void setStain(String stain) {
        this.stain = stain;
    }
        
    
    // ===============================
    //       Constructors
    // ===============================
    
    /**
     * 
     */
    public Cytoband() {}
    
    /**
     * Constructor
     * @param name Name
     * @param start Start point on chromosome
     * @param end End point on chromosome
     * @param stain Stain
     */
    public Cytoband(String name, long start, long end, String stain) {
    	this.name = name;
    	this.start = start;
    	this.end = end;
        this.stain = stain;
    }
    
    
    // =================================
    //      Public methods
    // =================================
    
    /**
     * Graph cytoband
     * @param map A map
     * @param colorMapper A color mapper
     */
    public void graph(ChromosomeIdeogram map, ColorMapper colorMapper) {
    	long startMb = this.start / 1000000;
    	long endMb = this.end / 1000000;
    	String mouseOver = this.name + " [" + startMb + "MB-" + endMb + "MB]";
    	//map.plotFeature(this.start, this.end, mouseOver, null, false, colorMapper.getColor(this.stain));
    }
    
    
    /**
     * Is cytoband in centromere?
     * @return T/F
     */
    public boolean inCentromere() {
        return "acen".equals(this.stain);
    }
}
