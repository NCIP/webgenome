/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.domain;

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
