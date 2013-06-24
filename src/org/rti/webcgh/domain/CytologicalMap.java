/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2006-10-22 02:06:04 $

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

package org.rti.webcgh.domain;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents a cytological map of a chromosome
 * consisting of cytobands.
 * @author dhall
 *
 */
public class CytologicalMap {

	
    // ============================================
    //         Attributes
    // ============================================
    
	/** Primary key identifier used for persistence. */
    private Long id = null;
    
    /** Cytobands. */
    private SortedSet<Cytoband> cytobands = new TreeSet<Cytoband>();
    
    /** Chromosome number. */
    private short chromosome = (short) -1;
    
    /** Chromosomal start point of centromere. */
    private long centromereStart = -1;
    
    /** Chromosomal end point of centromere. */
    private long centromereEnd = -1;
    
    /** Length of chromosome in base pairs. */
    private long length = 0;
    
    /** Organism. */
    private Organism organism = null;
    
    
    // ==============================
    //     Getters/setters
    // ==============================
    
    /**
     * Get organism.
     * @return Organism.
     */
    public final Organism getOrganism() {
		return organism;
	}


    /**
     * Set organism.
     * @param organism An organism.
     */
	public final void setOrganism(final Organism organism) {
		this.organism = organism;
	}


	/**
     * Get chromosomal end point of centromere.
     * @return Returns the chromosomal end point of centromere.
     */
    public final long getCentromereEnd() {
        return this.centromereEnd;
    }
    
    
    /**
     * Set chromosomal end point of centromere.
     * @param centromereEnd The chromosomal end point of centromere.
     */
    public final void setCentromereEnd(final long centromereEnd) {
        this.centromereEnd = centromereEnd;
    }
    
    
    /**
     * Get chromosomal start point of centromere.
     * @return Returns the chromosomal start point of centromere.
     */
    public final long getCentromereStart() {
        return this.centromereStart;
    }
    
    
    /**
     * Set chromosomal start point of centromere.
     * @param centromereStart The
     * chromosomal start point of centromere.
     */
    public final void setCentromereStart(final long centromereStart) {
        this.centromereStart = centromereStart;
    }
    
    
    /**
     * Get chromosome number.
     * @return Returns the chromosome number.
     */
    public final short getChromosome() {
        return this.chromosome;
    }
    
    
    /**
     * Set chromosome number.
     * @param chromosome The chromosome number.
     */
    public final void setChromosome(final short chromosome) {
        this.chromosome = chromosome;
    }
    
    
    /**
     * Get cytobands.
     * @return Returns cytobands.
     */
    public final SortedSet<Cytoband> getCytobands() {
        return cytobands;
    }
    
    
    /**
     * Set cytobands.
     * @param cytobands Cytobands.
     */
    public final void setCytobands(final SortedSet<Cytoband> cytobands) {
        this.cytobands = cytobands;
        for (Cytoband cytoband : cytobands) {
            if (cytoband.getEnd() > this.length) {
                this.length = cytoband.getEnd();
            }
        }
    }
    
    
    /**
     * Get primary key identifier used for persistence.
     * @return Returns the primary key identifier used for persistence.
     */
    public final Long getId() {
        return id;
    }
    
    
    /**
     * Set primary key identifier used for persistence.
     * @param id The primary key identifier used for persistence.
     */
    public final void setId(final Long id) {
        this.id = id;
    }
    
    
    // ============================================
    //         Constructors
    // ============================================
    
    /**
     * Constructor.
     */
    public CytologicalMap() {
    	
    }
    
    /**
     * Constructor.
     * @param chromosome Chromosome number
     * @param centromereStart Centromere start point
     * @param centromereEnd Centromere end point
     * @param organism Organism
     */
    public CytologicalMap(final short chromosome,
    		final long centromereStart, final long centromereEnd,
    		final Organism organism) {
        this(chromosome);
        this.centromereStart = centromereStart;
        this.centromereEnd = centromereEnd;
        this.organism = organism;
    }
    
    
    /**
     * Constructor.
     * @param chromosome Chromosome number
     */
    public CytologicalMap(final short chromosome) {
        this.chromosome = chromosome;
    }
    
    
    // ==================================================
    //      Business methods
    // ==================================================
    
    /**
     * Add a cytoband.
     * @param cytoband A cytoband
     */
    public final void addCytoband(final Cytoband cytoband) {
    	this.cytobands.add(cytoband);
    	if (cytoband.inCentromere()) {
	    	if (this.centromereStart < 0) {
	    	    this.centromereStart = cytoband.getStart();
	    	} else if (cytoband.getStart() < this.centromereStart) {
	    	    this.centromereStart = cytoband.getStart();
	    	}
	    	if (this.centromereEnd < 0) {
	    	    this.centromereEnd = cytoband.getEnd();
	    	} else if (cytoband.getEnd() > this.centromereEnd) {
	    	    this.centromereEnd = cytoband.getEnd();
	    	}
    	}
    	if (cytoband.getEnd() > this.length) {
    	    this.length = cytoband.getEnd();
    	}
    }

    
    /**
     * Get length in base pairs.
     * @return Length in base pairs.
     */
    public final long length() {
        return this.length;
    }
}
