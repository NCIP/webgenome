/*
$Revision: 1.2 $
$Date: 2008-10-23 16:17:07 $


*/

package org.rti.webgenome.domain;

import java.io.Serializable;

import org.rti.webgenome.util.SystemUtils;

/**
 * Represents an organism.
 * @author dhall
 *
 */
public class Organism implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    
    // ============================
    //       Constants
    // ============================
    
    /** Unknown organism. */
    public static final Organism UNKNOWN_ORGANISM =
    	new Organism("Unknown", "Unknown");
    
    
    // ======================================
    //         Attributes
    // ======================================
    
    /** Identifier used as primary key in persistence. */
    private Long id = null;
    
    /** Taxinomic genus. */
    private String genus = null;
    
    /** Taxinomic species. */
    private String species = null;
    
    /**
     * Get identifier (used as primary key in persistence).
     * @return Identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set identifier (used as primary key in persistence).
     * @param id Identifier
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Set taxonimic genus.
     * @param genus Genus
     */
    public final void setGenus(final String genus) {
        this.genus = genus;
    }

    /**
     * Set taxonomic species.
     * @param species Species
     */
    public final void setSpecies(final String species) {
        this.species = species;
    }

    /**
     * Get the genus.
     * @return Genus
     */
    public final String getGenus() {
        return genus;
    }

    /**
     * Get the species.
     * @return Species
     */
    public final String getSpecies() {
        return species;
    }

    
    // ==================================
    //      Constructors
    // ==================================
    
    
    /**
     * Default constructor.
     */
    public Organism() {
        
    }
    
    /**
     * Constructor.
     * @param genus The genus
     * @param species The species
     */
    public Organism(final String genus, final String species) {
        this.genus = genus;
        this.species = species;
    }
    
    
    // ===============================
    //     Other business methods
    // ===============================
    
    /**
     * Get display name.
     * @return Display name
     */
    public final String getDisplayName() {
    	return this.genus + " " + this.species;
    }
    
    
    // =========================
    //       Overrides
    // =========================

    /**
     * Equals methods.
     * @param obj An object
     * @return See java.lang.Object.equals()
     * @see java.lang.Object#equals(Object)
     */
	@Override
	public final boolean equals(final Object obj) {
		boolean equals = false;
		if (obj instanceof Organism) {
			Organism org = (Organism) obj;
			equals = this.getGenus().equals(org.getGenus())
			&& this.getSpecies().equals(org.getSpecies());
		}
		return equals;
	}

	/**
	 * Generate hash code.
	 * @return Hash code
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		String concatenation = this.getGenus() + this.getSpecies();
		return concatenation.hashCode();
	}
    
	public String print2Buff(){
		StringBuffer buff = new StringBuffer();
		buff.append("Printing Organism START\n");
		buff.append("displayName = " + this.getDisplayName() + "\n");
		buff.append("genus = " +this.getGenus() + "\n");
		buff.append("Id = " +this.getId() + "\n");
		buff.append("species = " +this.getSpecies() + "\n");
		buff.append("Printing Organism END\n");
		return buff.toString();
	}
    
}
