/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2008-02-22 03:54:10 $


*/

package org.rti.webgenome.service.analysis;

import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.service.util.ChromosomeArrayDataIterator;

/**
 * Transforms data where chromosome array
 * data have been serialized to disk.
 * @author dhall
 *
 */
public final class SerializedDataTransformer
	extends DataTransformer {
	
    // ============================================
    //          Attributes
    // ============================================
    
    /**
     * Data file manager used to serialize/de-serialize data.
     * This property should be injected.
     */
    private DataFileManager dataFileManager = null;
    
    
    // ======================================
    //        Getters/setters
    // ======================================
    
    
    /**
     * Set data file manager used to serialize/de-serialize
     * data.  This property should be injected.
     * @return Data file manager
     */
    public DataFileManager getDataFileManager() {
        return dataFileManager;
    }


    /**
     * Set data file manager used to serialize/de-serialize data.
     * This property should be injected.
     * @param dataFileManager Data file manager
     */
    public void setDataFileManager(
            final DataFileManager dataFileManager) {
        this.dataFileManager = dataFileManager;
    }
    
    //
    //  C O N S T R U C T O R S
    //
    
    /**
     * Constructor.
     */
    public SerializedDataTransformer() {
    	
    }
    
    /**
     * Constructor.
     * @param dataFileManager Manages files containing serialized
     * data (i.e., {@code ChromosomeArrayData} objects.
     */
    public SerializedDataTransformer(final DataFileManager dataFileManager) {
    	this.dataFileManager = dataFileManager;
    }
    
	
    /**
     * Create new bioassay of same type as given bioassay
     * and copy some of the properties.
     * @param bioAssay A bioassay
     * @return A new bioassay of same type as given bioassay
     * and copy some of the properties
     */
    protected BioAssay clone(final BioAssay bioAssay) {
        BioAssay newBa = null;
        if (bioAssay instanceof DataSerializedBioAssay) {
            newBa = new DataSerializedBioAssay(bioAssay.getName(),
                    bioAssay.getOrganism());
        } else {
        	throw new IllegalArgumentException(
			"Bioassay must be of type DataSerializedBioAssay");
        }
        newBa.setArray(bioAssay.getArray());
        return newBa;
    }
    
    
    /**
     * {@inheritDoc}
     */
    protected Set<String> addChromosomeArrayData(final BioAssay bioAssay,
            final ChromosomeArrayData chromosomeArrayData) {
    	Set<String> fNames = new HashSet<String>();
    	if (bioAssay instanceof DataSerializedBioAssay) {
    		DataSerializedBioAssay dbsa =
    			(DataSerializedBioAssay) bioAssay;
    		
    		// If necessary, delete old value
    		String fileName = dbsa.getFileName(
    				chromosomeArrayData.getChromosome());
    		if (fileName != null) {
    			fNames.add(fileName);
    		}
    		
    		// Add new value
            this.dataFileManager.saveChromosomeArrayData(
                    dbsa, chromosomeArrayData);
        } else {
        	throw new IllegalArgumentException(
			"Bioassay must be of type DataSerializedBioAssay");
        }
    	return fNames;
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
        if (bioAssay instanceof DataSerializedBioAssay) {
            cad = this.dataFileManager.loadChromosomeArrayData(
                    (DataSerializedBioAssay) bioAssay, chromosome);
        } else {
        	throw new IllegalArgumentException(
			"Bioassay must be of type DataSerializedBioAssay");
        }
        return cad;
    }

    
    /**
     * Get a chromosome data iterator.
     * @param bioAssay Bioassay to iterate over
     * @return Chromosome data iterator
     */
    protected ChromosomeArrayDataIterator
    	getChromosomeArrayDataIterator(final BioAssay bioAssay) {
    	return new DefChromosomeArrayDataIterator(bioAssay);
    }
    
    /**
     * Finalize experiment before it sent to garbage collector.
     * @param experiment Experiment to finalize
     */
    protected void finalize(final Experiment experiment) {
    	this.dataFileManager.deleteDataFiles(experiment, false);
    }
    
    
    /**
     * Implementation of <code>ChromosomeArrayDataIterator</code>
     * for in-memory data.
     * @author dhall
     *
     */
    class DefChromosomeArrayDataIterator
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
    		if (bioAssay instanceof DataSerializedBioAssay) {
                cad = dataFileManager.loadChromosomeArrayData((
                        DataSerializedBioAssay) bioAssay, chromosome);
            } else {
            	throw new IllegalArgumentException(
    				"Bioassay must be of type DataSerializedBioAssay");
            }
    		return cad;
		}
    }
}
