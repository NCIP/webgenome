/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.rti.webgenome.client.BioAssayDTO;
import org.rti.webgenome.client.BioAssayDatumDTO;

/**
 * Concrete implementation of <code>BioAssay</code>
 * that includes data.  This class will be used
 * by clients when the amount of data being
 * manipulated is relatively small such that it
 * can all be kept in main memory during the
 * lifetime of the user session without denigrating
 * the performance of the system.
 * @author dhall
 *
 */
public class DataContainingBioAssay extends BioAssay {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    /** Logger. */
    private static final Logger LOGGER =
    	Logger.getLogger(DataContainingBioAssay.class);
    
    // =================================
    //        Attributes
    // =================================
    
    /** Map of chromosome number to associated chromosome array data. */
    private SortedMap<Short, ChromosomeArrayData> chromosomeArrayDataIndex =
        new TreeMap<Short, ChromosomeArrayData>();
        
    
    // ================================
    //     Getters/setters
    // ================================
    
    /**
     * Get map of chromosome number to associated chromosome array data.
     * @return Map of chromosome number to associated chromosome array data
     */
    public final SortedMap<Short, ChromosomeArrayData>
        getChromosomeArrayDataIndex() {
        return chromosomeArrayDataIndex;
    }


    /**
     * Set map of chromosome number to associated chromosome array data.
     * @param chromosomeArrayDataIndex Map of chromosome number to associated
     * chromosome array data
     */
    public final void setChromosomeArrayDataIndex(
            final SortedMap<Short, ChromosomeArrayData>
            chromosomeArrayDataIndex) {
        this.chromosomeArrayDataIndex = chromosomeArrayDataIndex;
    }
    
    /**
     * Get chromosome array data from given chromosome.
     * @param chromosome Chromosome number
     * @return Chromosome array data
     */
    public final ChromosomeArrayData
        getChromosomeArrayData(final short chromosome) {
        return this.chromosomeArrayDataIndex.get(chromosome);
    }
    
    
    // ===========================
    //     Constructors
    // ===========================
    
    /**
     * Constructor.
     */
    public DataContainingBioAssay() {
        super();
    }


    /**
     * Constructor.
     * @param name Bioassay name
     * @param organism Organism that was subject of bioassay
     */
    public DataContainingBioAssay(final String name, final Organism organism) {
        super(name, organism);
    }
    
    
    /**
     * Constructor.
     * @param bioAssayDto Bioassay data transfer object.
     */
    public DataContainingBioAssay(final BioAssayDTO bioAssayDto) {
    	if (bioAssayDto == null) {
    		throw new IllegalArgumentException("BioAssayDTO is null");
    	}
    	if (bioAssayDto.getName() == null) {
    		throw new IllegalArgumentException("BioAssayDTO.name is null");
    	}
    	this.setName(bioAssayDto.getName());
    	this.setOrganism(Organism.UNKNOWN_ORGANISM);
    	this.setArray(Array.UNKNOWN_ARRAY);
    	this.add(bioAssayDto.getBioAssayData());
    }
    
    
    /**
     * Add bioassay data from given data transfer objects.
     * @param bioAssayData Bioassay data transfer objects.
     */
    private void add(final BioAssayDatumDTO[] bioAssayData) {
    	if (bioAssayData != null) {
    		for (int i = 0; i < bioAssayData.length; i++) {
    			try {
					this.add(new ArrayDatum(bioAssayData[i]));
				} catch (BadChromosomeFormat e) {
					LOGGER.warn(e.getMessage());
				}
    		}
    	}
    }
    
    // =================================
    //     Implemented abstract methods
    // =================================

    /**
     * Get set of chromosomes.
     * @return Chromosomes
     */
    public final SortedSet<Short> getChromosomes() {
        return new TreeSet<Short>(this.chromosomeArrayDataIndex.keySet());
    }
    
    /**
     * Get size of chromosome implied by the data.
     * @param chromosome Chromosome number
     * @return Size of chromosome implied by data
     */
    public final long inferredChromosomeSize(final short chromosome) {
    	long size = 0;
    	ChromosomeArrayData cad = this.getChromosomeArrayData(chromosome);
    	if (cad != null) {
    		size = cad.inferredChromosomeSize();
    	}
    	return size;
    }
    
    
    /**
     * Get minimum value in the bioassay.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @return Minimum value in bioassay
     */
    public final float minValue() {
    	float min = Float.NaN;
    	for (ChromosomeArrayData cad : this.chromosomeArrayDataIndex.values()) {
    		float candidateMin = cad.getMinValue();
    		if (!Float.isNaN(candidateMin)) {
	    		if (Float.isNaN(min) || min < candidateMin) {
	    			min = candidateMin;
	    		}
    		}
    	}
    	return min;
    }
    
    
    /**
     * Get maximum value in the bioassay.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @return Maximum value in bioassay
     */
    public final float maxValue() {
    	float max = Float.NaN;
    	for (ChromosomeArrayData cad : this.chromosomeArrayDataIndex.values()) {
    		float candidateMax = cad.getMaxValue();
    		if (Float.isNaN(max) || max > candidateMax) {
    			max = candidateMax;
    		}
    	}
    	return max;
    }
    
    
    /**
     * Get minimum value from the given chromosomes.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @param chromosomes Chromosome numbers
     * @return Minimum value from the given chromosomes
     */
    public final float minValue(final Collection<Short> chromosomes) {
    	float min = Float.NaN;
    	for (short chrom : chromosomes) {
    		ChromosomeArrayData cad = this.chromosomeArrayDataIndex.get(chrom);
    		if (cad != null) {
    			float candidateMin = cad.getMinValue();
    			if (Float.isNaN(min) || candidateMin < min) {
    				min = candidateMin;
    			}
    		}
    	}
    	return min;
    }
    
    
    /**
     * Get maximum value from the given chromosomes.  This value will be
     * the sum of <code>value</code> and <code>error</code>
     * in some <code>ArrayDatum</code> object.
     * @param chromosomes Chromosome numbers
     * @return Maximum value from the given chromosomes
     */
    public final float maxValue(final Collection<Short> chromosomes) {
       	float max = Float.NaN;
    	for (short chrom : chromosomes) {
    		ChromosomeArrayData cad = this.chromosomeArrayDataIndex.get(chrom);
    		if (cad != null) {
    			float candidateMax = cad.getMaxValue();
    			if (Float.isNaN(max) || candidateMax > max) {
    				max = candidateMax;
    			}
    		}
    	}
    	return max;
    }
    
    
    /**
     * Return number of datum on given chromosome.
     * @param chromosome Chromosome number.
     * @return Number of datum on given chromosome.
     */
    public int numDatum(final short chromosome) {
    	int num = 0;
    	ChromosomeArrayData data =
    		this.chromosomeArrayDataIndex.get(chromosome);
    	if (data != null) {
    		num = data.getArrayData().size();
    	}
    	return num;
    }
    
    
    // =================================
    //    Additional business methods
    // =================================
    
    /**
     * Add given array datum.
     * @param datum A datum
     */
    public final void add(final ArrayDatum datum) {
        short chromosome = datum.getReporter().getChromosome();
        ChromosomeArrayData cad = this.getChromosomeArrayData(chromosome);
        if (cad == null) {
            cad = new ChromosomeArrayData(chromosome);
            this.chromosomeArrayDataIndex.put(chromosome, cad);
        }
        cad.add(datum);
    }
    
    
    /**
     * Add given chromosome array data replacing any data.
     * from the same chromosome
     * @param chromosomeArrayData Chromosome array data
     */
    public final void put(final ChromosomeArrayData chromosomeArrayData) {
        this.chromosomeArrayDataIndex.put(
                chromosomeArrayData.getChromosome(), chromosomeArrayData);
    }

    
    /**
     * Add data from given data transfer object.
     * @param bioAssayDto Bioassay data transfer object.
     */
    public final void addData(final BioAssayDTO bioAssayDto) {
    	if (bioAssayDto == null) {
    		throw new IllegalArgumentException("Bioassay DTO is null");
    	}
    	if (bioAssayDto.getName() == null) {
    		throw new IllegalArgumentException("BioAssayDTO.name is null");
    	}
    	if (!this.getName().equals(bioAssayDto.getName())) {
    		throw new IllegalArgumentException(
    				"Cannot add data from data transfer object.  "
    				+ "Bioassay names do not match.");
    	}
    	this.add(bioAssayDto.getBioAssayData());
    }
    
    
    /**
     * Extract reporters from this object.
     * @return List of reporters
     */
    public final List<Reporter> extractReporters() {
    	List<Reporter> reporters = new ArrayList<Reporter>();
    	for (Short chrom : this.chromosomeArrayDataIndex.keySet()) {
    		ChromosomeArrayData cad = this.getChromosomeArrayData(chrom);
    		List<ArrayDatum> arrayData = cad.getArrayData();
    		for (ArrayDatum ad : arrayData) {
    			reporters.add(ad.getReporter());
    		}
    	}
    	return reporters;
    }
}
