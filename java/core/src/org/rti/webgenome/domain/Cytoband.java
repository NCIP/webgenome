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
