/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

package org.rti.webgenome.client;


import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.util.SystemUtils;


/**
 * Implementation of <code>ReporterDTO</code> used primarily for
 * testing.
 * @author dhall
 *
 */
public class DefReporterDTOImpl implements ReporterDTO {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/** Reporter name. */
    private String name = null;
    
    /** Chromosome number. */
    private String chromosome = null;
    
    /** Chromosome location in base pairs. */
    private Long chromosomeLocation = null;
    
    /** Associated genes. */
    private Collection<String> associatedGenes = new ArrayList<String>();
    
    /** Annotations. */
    private Collection<String> annotations = new ArrayList<String>();
    
    /** Is reporter selected? */
    private Boolean selected = null;
    
    /**
     * Constructor.
     * @param name Name
     * @param chromosome Chromsome
     * @param chromosomeLocation Chromsome Location
     * @param selected Is reporter selected?
     */
    public DefReporterDTOImpl(final String name,
    		final String chromosome, final Long chromosomeLocation,
    		boolean selected) {
        this.name = name;
        this.chromosome = chromosome;
        this.chromosomeLocation = chromosomeLocation;
        this.selected = new Boolean(false);
        this.selected = selected;
    }
    
    /**
     * Get reporter name.
     * @return Reporter name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Get chromosome number.
     * @return Chromosome number.
     */
    public final String getChromosome() {
        return chromosome;
    }

    /**
     * Get chromosome location of reporter.
     * @return Chromosome location of reporter in base pairs.
     */
    public final Long getChromosomeLocation() {
        return chromosomeLocation;
    }

    /**
     * Get genes associated with reporter.
     * @return Genese associated with reporter.
     */
    public final String[] getAssociatedGenes() {
        String[] names = new String[0];
        names = (String[]) this.associatedGenes.toArray(names);
        return names;
    }

    /**
     * Get annotations associated with reporter.
     * @return Annotations associated with reporter.
     */
    public final String[] getAnnotations() {
        String[] annotationsArray = new String[0];
        annotationsArray = (String[])
        	this.annotations.toArray(annotationsArray);
        return annotationsArray;
    }

    /**
     * Is reporter selected?
     * @return T/F
     */
    public final Boolean isSelected() {
        return selected;
    }
    
    
    /**
     * Add associated gene.
     * @param associatedGene Associated gene name.
     */
    public final void addAssociatedGene(final String associatedGene) {
        this.associatedGenes.add(associatedGene);
    }
    
    
    /**
     * Add associated annotation.
     * @param annotationStr Annotation.
     */
    public final void addAnnotation(final String annotationStr) {
        this.annotations.add(annotationStr);
    }

}
