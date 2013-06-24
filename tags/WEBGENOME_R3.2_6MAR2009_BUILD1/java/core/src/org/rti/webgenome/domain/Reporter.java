/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2008-02-05 23:28:35 $


*/

package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.client.ReporterDTO;
import org.rti.webgenome.util.SystemUtils;

/**
 * Represents a microarray reporter (i.e., a probe).
 * @author dhall
 *
 */
public class Reporter implements Serializable, Comparable<Reporter> {

    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    
    // ======================================
    //       Attributes
    // ======================================
    
    /** Identifier used for persisting object. */
    private Long id = null;
    
    /** Name of reporter. */
    private String name = null;
    
    /** Chromosome number. */
    private short chromosome = -1;
    
    /** Chromosome location. */
    private long location = (long) -1;
    
    /** Reporter annotations. */
    private Collection<String> annotations = new ArrayList<String>();
    
    /** Associated genes. */
    private Collection<String> associatedGenes = new ArrayList<String>();
    
    /** Is reporter selected? */
    private boolean selected = false;

    
    /**
     * Get chromosome number.
     * @return Chromosome number
     */
    public final short getChromosome() {
        return chromosome;
    }

    /**
     * Set chromosome number.
     * @param chromosome Chromosome number
     */
    public final void setChromosome(final short chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * Get identifier (used for persisting object).
     * @return Identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set identifier (used for persisting object).
     * @param id Identifier
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get chromosomal location.
     * @return Chromosomal location
     */
    public final long getLocation() {
        return location;
    }

    /**
     * Set chromosomal location.
     * @param location Chromosomal location
     */
    public final void setLocation(final long location) {
        this.location = location;
    }
    

    /**
     * Get annotations.
     * @return Annotations.
     */
    public final Collection<String> getAnnotations() {
		return annotations;
	}

    /**
     * Get associated genes.
     * @return Associated genes.
     */
	public final Collection<String> getAssociatedGenes() {
		return associatedGenes;
	}

	/**
	 * Is reporter selected?
	 * @return T/F
	 */
	public final boolean isSelected() {
		return selected;
	}

	/**
	 * Specify that reporter is selected.
	 * @param selected Is reporter selected?
	 */
	public final void setSelected(final boolean selected) {
		this.selected = selected;
	}

	/**
     * Get name of reporter.
     * @return Name of reporter
     */
    public final String getName() {
        return name;
    }

    /**
     * Set name of reporter.
     * @param name Name of reporter
     */
    public final void setName(final String name) {
        this.name = name;
    }
    
    
    // ========================================
    //        Constructors
    // ========================================
    
    /**
     * Default constructor.
     */
    public Reporter() {
        
    }

    /**
     * Constructor.
     * @param name Name of reporter
     * @param chromosome Chromosome number
     * @param location Chromosome location of reporter
     */
    public Reporter(final String name, final short chromosome,
            final long location) {
        this.name = name;
        this.chromosome = chromosome;
        this.location = location;
    }
    
    
    /**
     * Constructor.
     * @param dto Data transfer object.
     * @throws BadChromosomeFormat if the chromosome number
     * in the given dto is not numeric, "X" or "Y" (case insensitive).
     */
    public Reporter(final ReporterDTO dto) throws BadChromosomeFormat {
    	this.chromosome = ChromosomeFormatter.chromosomeNumber(
    			dto.getChromosome());
    	this.location = dto.getChromosomeLocation();
    	this.name = dto.getName();
    	String[] a = dto.getAnnotations();
    	if (a != null) {
	    	for (int i = 0; i < a.length; i++) {
	    		if (a[i] != null) {
	    			this.annotations.add(a[i]);
	    		}
	    	}
    	}
    	String[] g = dto.getAssociatedGenes();
    	if (g != null) {
    		for (int i = 0; i < g.length; i++) {
    			if (g[i] != null) {
    				this.associatedGenes.add(g[i]);
    			}
    		}
    	}
    	this.selected = dto.isSelected();
    }

    // ==================================
    //     Comparable interface
    // ==================================
    
    /**
     * Comparison method.  Sorts by chromosome number and
     * chromosome position.
     * @param r A reporter
     * @return
     *  <ul>
     *      <li>-1 If this reporter has a lower number
     *      chromosome or same chromosome and lower location
     *      than given reporter object</li>
     *      <li>0 If this reporter on same chromosome
     *      at some location than given reporter object</li>
     *      <li>1 If this reporter on higher number
     *      chromosome or same chromosome and higher location
     *      than given reporter.
     *  </ul>
     */
    public final int compareTo(final Reporter r) {
        int val = 0;
        if (this.chromosome < r.chromosome) {
            val = -1;
        } else if (this.chromosome == r.chromosome) {
            if (this.location < r.location) {
                val = -1;
            } else if (this.location == r.location) {
                val = 0;
            } else if (this.location > r.location) {
                val = 1;
            }
        } else if (this.chromosome > r.chromosome) {
            val = 1;
        }
        return val;
    }
    
    // ==============================
    //    Overrides
    // ==============================
    
    /**
     * {@inheritDoc}
     */
	@Override
	public final boolean equals(final Object obj) {
		boolean equal = false;
		if (obj instanceof Reporter) {
			equal = this.compareTo((Reporter) obj) == 0;
		}
		return equal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return (String.valueOf(this.chromosome)
				+ String.valueOf(this.location)).hashCode();
	}
	
	
}
