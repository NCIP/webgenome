/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.domain;


/**
 * Represents a cytoband on a cytological map.
 * @author dhall
 */
public class Cytoband implements Comparable {

    // =================================
    //     Attributes
    // =================================
    
	/** Primary key identifier used for persistence. */
	private Long id = null;
	
	/** Name of cytoband. */
	private String name = null;
	
	/** Chromosome start point of cytoband. */
	private long start = -1;
	
	/** Chromosome end point of cytoband. */
	private long end = -1;
	
	/** Staining intensity of this cytoband. */
    private String stain;
    
    // ===========================
    //      Getters/setters
    // ===========================
    
	/**
	 * Get chromosome end point of cytoband.
	 * @return Returns chromosome end point of cytoband.
	 */
	public final long getEnd() {
		return this.end;
	}
	
	
	/**
	 * Set chromosome end point of cytoband.
	 * @param end Chromosome end point of cytoband.
	 */
	public final void setEnd(final long end) {
		this.end = end;
	}
	
	
	/**
	 * Get primary key identifier of cytoband
	 * used for persistence.
	 * @return Returns primary key identifier of cytoband
	 * used for persistence.
	 */
	public final Long getId() {
		return this.id;
	}
	
	
	/**
	 * Set primary key identifier of cytoband
	 * used for persistence.
	 * @param id The primary key identifier of cytoband
	 * used for persistence.
	 */
	public final void setId(final Long id) {
		this.id = id;
	}
	
	
	/**
	 * Get cytoband name.
	 * @return Returns the cytoband name.
	 */
	public final String getName() {
		return this.name;
	}
	
	
	/**
	 * Set cytoband name.
	 * @param name The cytoband name.
	 */
	public final void setName(final String name) {
		this.name = name;
	}
	
	
	/**
	 * Get chromosome start point of cytoband.
	 * @return Returns the chromosome start point of cytoband.
	 */
	public final long getStart() {
		return this.start;
	}
	
	
	/**
	 * Set chromosome start point of cytoband.
	 * @param start The chromosome start point of cytoband.
	 */
	public final void setStart(final long start) {
		this.start = start;
	}
	
	
    /**
     * Get staining intensity of cytoband.
     * @return Returns the staining intensity of cytoband.
     */
    public final String getStain() {
        return this.stain;
    }
    
    
    /**
     * Set staining intensity of cytoband.
     * @param stain The staining intensity of cytoband.
     */
    public final void setStain(final String stain) {
        this.stain = stain;
    }
        
    
    // ===============================
    //       Constructors
    // ===============================
    
    /**
     * Contructor.
     */
    public Cytoband() {
    	
    }
    
    /**
     * Constructor.
     * @param name Name
     * @param start Start point on chromosome
     * @param end End point on chromosome
     * @param stain Stain intensity
     */
    public Cytoband(final String name, final long start,
    		final long end, final String stain) {
    	this.name = name;
    	this.start = start;
    	this.end = end;
        this.stain = stain;
    }
    
    // ==========================
    //    Comparable interface
    // ==========================
    
    /**
     * Comparison method.
     * @param cytoband A cytoband to compare to this.
     * @return
     * <ul>
     * 	<li>
     * 		-1 : This to left of given cytoband.
     * 	</li>
     * 	<li>
     * 		0 : This on top of given cytoband.
     * 	</li>
     * 	<li>
     * 		1 : This to right of this given cytoband.
     * 	</li>
     * </ul>
     */
    public final int compareTo(final Object cytoband) {
		int val = 0;
		if (!(cytoband instanceof Cytoband)) {
			throw new IllegalArgumentException(
					"Argument must be of type Cytoband");
		}
		Cytoband c = (Cytoband) cytoband;
		if (this.start < c.start) {
			val = -1;
		} else if (this.start == c.start) {
			if (this.end < c.end) {
				val = -1;
			} else if (this.end == c.end) {
				val = 0;
			} else if (this.end > c.end) {
				val = 1;
			}
		} else if (this.start > c.start) {
			val = 1;
		}
		return val;
	}
    
    // =================================
    //      Business methods
    // =================================


	/**
     * Is cytoband in centromere?
     * @return T/F
     */
    public final boolean inCentromere() {
        return "acen".equals(this.stain);
    }
}
