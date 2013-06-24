/*
$Revision: 1.4 $
$Date: 2008-02-22 03:54:09 $


*/

package org.rti.webgenome.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.SortedSet;

import org.rti.webgenome.util.StringUtils;
import org.rti.webgenome.util.SystemUtils;


/**
 * Abstract base class representing
 * a bioassay (i.e., the hybridization of one microarray
 * against one biological sample).
 * @author dhall
 *
 */
public abstract class BioAssay implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    

    // =====================================
    //         Attributes
    // =====================================
    
    /** Identifier used for persistence. */
    private Long id = null;
    
    /** Name of bioassay. */
    private String name = null;
    
    /** Organism that was tested. */
    private Organism organism = null;
    
    /** Microarray model used in bioassay. */
    private Array array = null;
    
    /** Color of bioassay in plots. */
    private Color color = null;
    
    /** Is bioassay selected? */
    private boolean selected = false;
    
    /**
     * If this bioassay is derived from
     * another through an analytic operation,
     * this property gives the ID of the parent
     * bioassay.
     */
    private Long parentBioAssayId = null;
    
    // ===============================
    //       Getters/setters
    // ===============================
    
    /**
     * Is bioassay selected?
     * @return T/F
     */
    public final boolean isSelected() {
		return selected;
	}


    /**
     * Set selection.
     * @param selected Is bioassay selected?
     */
	public final void setSelected(final boolean selected) {
		this.selected = selected;
	}


	/**
	 * Get ID of parent bioassay.
	 * If this bioassay is derived from
     * another through an analytic operation,
     * this property gives the ID of the parent
     * bioassay.
	 * @return ID of parent bioassay.
	 */
	public final Long getParentBioAssayId() {
		return parentBioAssayId;
	}


	/**
	 * Set ID of parent bioassay.
	 * If this bioassay is derived from
     * another through an analytic operation,
     * this property gives the ID of the parent
     * bioassay.
	 * @param parentBioAssayId ID of parent bioassay
	 */
	public final void setParentBioAssayId(
			final Long parentBioAssayId) {
		this.parentBioAssayId = parentBioAssayId;
	}


	/**
     * Get color used in plots.
     * @return Color used in plots
     */
    public final Color getColor() {
        return color;
    }


    /**
     * Set color used in plots.
     * @param color Color used in plots
     */
    public final void setColor(final Color color) {
        this.color = color;
    }
    
    /**
     * Get microarray used in bioassay.
     * @return Array
     */
    public final Array getArray() {
        return array;
    }


    /**
     * Set microarray used in bioassay.
     * @param array An array
     */
    public final void setArray(final Array array) {
        this.array = array;
    }


    /**
     * Get identifier used for persistence.
     * @return Identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set identifier used for persistence.
     * @param id Identifer
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get name of bioassay.
     * @return Name
     */
    public final String getName() {
        return name;
    }

    /**
     * Set name of bioassay.
     * @param name Name
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Get organism that was tested.
     * @return An organism
     */
    public final Organism getOrganism() {
        return organism;
    }

    /**
     * Set organism that was tested.
     * @param organism An organism
     */
    public final void setOrganism(final Organism organism) {
        this.organism = organism;
    }
    

    // =======================================
    //         Constructors
    // =======================================
    
    /**
     * Default constructor.
     */
    public BioAssay() {
        
    }

    /**
     * Constructor.
     * @param name Name of bioassay
     * @param organism Organism that was tested
     */
    public BioAssay(final String name, final Organism organism) {
        this.name = name;
        this.organism = organism;
    }
    
    
    /**
     * Constructor.
     * @param name Name of bioassay
     * @param organism Organism that was tested
     * @param array Array
     */
    public BioAssay(final String name, final Organism organism,
            final Array array) {
        this.name = name;
        this.organism = organism;
        this.array = array;
    }
    
    
    // ===================================
    //    Business methods
    // ===================================
    
    /**
     * Bulk set all properties not related to data
     * (i.e. data points to plot).  Note, that the primary
     * key values is not set.
     * @param bioAssay Source bioassay for property values
     */
    public final void bulkSetNonDataProperties(final BioAssay bioAssay) {
    	this.array = bioAssay.array;
    	this.color = bioAssay.color;
    	this.name = bioAssay.name;
    	this.organism = bioAssay.organism;
    	this.parentBioAssayId = bioAssay.parentBioAssayId;
    	this.selected = bioAssay.selected;
    }
    
    /**
     * Is given bioassay "synonymous" with this?
     * Synonymous means that the two bioassays share
     * common properties with the exception of
     * id.
     * @param ba A bioassay
     * @return T/F
     */
    public final boolean synonymousWith(final BioAssay ba) {
        return
            StringUtils.equal(this.name, ba.name)
            && this.organism == ba.organism;
    }
    
    
    /**
     * Return the number of datum in bioassay.
     * @return Number of datum (i.e., individual data points).
     */
    public int numDatum() {
    	int num = 0;
    	for (short chrom : this.getChromosomes()) {
    		num += this.numDatum(chrom);
    	}
    	return num;
    }
    
    /**
     * Get color property as RGB integer equivalent.
     * This method is used for persisting the color.
     * @return Integer equivalent of color property.
     */
    public final int getColorAsInt() {
    	return this.color.getRGB();
    }
    
    
    /**
     * Set color using RGB integer equivalent.
     * @param rgb RGB integer equivalent
     * of color.
     */
    public final void setColorAsInt(final int rgb) {
    	this.color = new Color(rgb);
    }
    
    
    // ==============================
    //      Abstract methods
    // ==============================

    
    /**
     * Get set of chromosomes.
     * @return Chromosomes
     */
    public abstract SortedSet<Short> getChromosomes();
    
    /**
     * Get size of chromosome implied by the data.
     * @param chromosome Chromosome number
     * @return Size of chromosome implied by data
     */
    public abstract long inferredChromosomeSize(short chromosome);
    
    /**
     * Get minimum value in the bioassay.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @return Minimum value in bioassay
     */
    public abstract float minValue();
    
    
    /**
     * Get maximum value in the bioassay.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @return Maximum value in bioassay
     */
    public abstract float maxValue();
    
    
    /**
     * Get minimum value from the given chromosomes.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @param chromosomes Chromosome numbers
     * @return Minimum value from the given chromosomes
     */
    public abstract float minValue(Collection<Short> chromosomes);
    
    
    /**
     * Get maximum value from the given chromosomes.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @param chromosomes Chromosome numbers
     * @return Maximum value from the given chromosomes
     */
    public abstract float maxValue(Collection<Short> chromosomes);
    
    
    /**
     * Return number of datum on given chromosome.
     * @param chromosome Chromosome number.
     * @return Number of datum on given chromosome.
     */
    public abstract int numDatum(short chromosome);
}
