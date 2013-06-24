/*
$Revision: 1.3 $
$Date: 2008-02-22 03:54:10 $


*/

package org.rti.webgenome.service.analysis;

import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataContainingBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.service.util.ChromosomeArrayDataIterator;

/**
 * A data transformer where all data are in memory.
 * @author dhall
 *
 */
public final class InMemoryDataTransformer
	extends DataTransformer {
	
    /**
     * Create new bioassay of same type as given bioassay
     * and copy some of the properties.
     * @param bioAssay A bioassay
     * @return A new bioassay of same type as given bioassay
     * and copy some of the properties
     */
    protected BioAssay clone(final BioAssay bioAssay) {
        BioAssay newBa = null;
        if (bioAssay instanceof DataContainingBioAssay) {
            newBa = new DataContainingBioAssay(bioAssay.getName(),
                    bioAssay.getOrganism());
        } else {
        	throw new IllegalArgumentException(
        			"Bioassay must be of type DataContainingBioAssay");
        }
        newBa.setArray(bioAssay.getArray());
        return newBa;
    }
    
    
    /**
     * {@inheritDoc}
     */
    protected Set<String> addChromosomeArrayData(final BioAssay bioAssay,
            final ChromosomeArrayData chromosomeArrayData) {
        if (bioAssay instanceof DataContainingBioAssay) {
            ((DataContainingBioAssay) bioAssay).put(chromosomeArrayData);
        } else {
        	throw new IllegalArgumentException(
			"Bioassay must be of type DataContainingBioAssay");
        }
        return new HashSet<String>();
    }
    

	/**
     * Get chromosome array data from given bioassay and chromosome.
     * This method handles the two cases where we are
     * keeping all data in memory or are serializing
     * data when not being used.
     * @param bioAssay Bioassay
     * @param chromosome Chromosome
     * @return Chromosome array data
     */
    protected ChromosomeArrayData getChromosomeArrayData(
            final BioAssay bioAssay, final short chromosome) {
        ChromosomeArrayData cad = null;
        if (bioAssay instanceof DataContainingBioAssay) {
            cad = ((DataContainingBioAssay)
                    bioAssay).getChromosomeArrayData(chromosome);
        } else {
        	throw new IllegalArgumentException(
			"Bioassay must be of type DataContainingBioAssay");
        }
        return cad;
    }
    
    
    /**
     * Get a chromosome data iterator.
     * @param bioAssay Bioassay containing data
     * @return Chromosome data iterator
     */
    protected ChromosomeArrayDataIterator
    	getChromosomeArrayDataIterator(final BioAssay bioAssay) {
    	return new DefChromosomeArrayDataIterator(bioAssay);
    }
    
    /**
     * Finalize experiment before it sent to garbage collector.
     * This implementation does nothing.
     * @param experiment Experiment to finalize
     */
    protected void finalize(final Experiment experiment) {
    	
    }

    
    /**
     * Implementation of <code>ChromosomeArrayDataIterator</code>
     * for in-memory data.
     * @author dhall
     *
     */
    static class DefChromosomeArrayDataIterator
    	extends ChromosomeArrayDataIterator {
    	
    	/**
    	 * Constructor.
    	 * @param bioAssay A bioassay to iterate over
    	 */
    	public DefChromosomeArrayDataIterator(final BioAssay bioAssay) {
			super(bioAssay);
		}

		/**
         * Get chromosome array data from given bioassay.
         * @param bioAssay Bioassay containing data
         * @param chromosome Chromosome number
         * @return Chromosome data iterator
         */
    	protected ChromosomeArrayData getChromosomeArrayData(
	    		final BioAssay bioAssay, final short chromosome) {
    		ChromosomeArrayData cad = null;
    		if (bioAssay instanceof DataContainingBioAssay) {
                cad = ((DataContainingBioAssay)
                        bioAssay).getChromosomeArrayData(chromosome);
            } else {
            	throw new IllegalArgumentException(
            			"Bioassay must be of type DataContainingBioAssay");
            }
    		return cad;
		}
    }
}
