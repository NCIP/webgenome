/*
$Revision: 1.5 $
$Date: 2007-09-29 05:24:19 $


*/

package org.rti.webgenome.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.rti.webgenome.util.SystemUtils;

/**
 * Concrete implementation of <code>BioAssay</code> where
 * associated data are serialized to file.  This class
 * will be used by clients when large data sets are
 * manipulaed.  These data sets will likely be larger
 * than the amount of available RAM.
 * @author dhall
 *
 */
public class DataSerializedBioAssay extends BioAssay {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // ==============================
    //     Attributes
    // ==============================
    
    /**
     * Map to names of binary files of serialized
     * <code>ChromosomeArrayData</code> objects.
     * These file names are relative -- not absolute
     * paths.  Clients that reference <code>BioAssay</code>
     * objects must know how to construct the corresponding
     * absolute paths to recover the data.
     */
    private SortedMap<Short, String> chromosomeArrayDataFileIndex =
        new TreeMap<Short, String>();
    
    /** Maps chromosome numbers to inferred chromosome sizes. */
    private Map<Short, Long> chromosomeSizes = new HashMap<Short, Long>();
    
    /**
     * Maps chromosome numbers to minimum values.  These values
     * are the sum of <code>value</code> and <code>error</code>
     * for some <code>ArrayDatum</code> object.
     */
    private Map<Short, Float> minValues = new HashMap<Short, Float>();
    
    /**
     * Maps chromosome numbers to minimum values.  These values
     * are the sum of <code>value</code> and <code>error</code>
     * for some <code>ArrayDatum</code> object.
     */
    private Map<Short, Float> maxValues = new HashMap<Short, Float>();
    
    /**
     * Maps chromosome numbers to the number of
     * {@code ArrayDatum} objects on that chromosome.
     */
    private Map<Short, Integer> numDatum =
    	new HashMap<Short, Integer>();
    
    
    // ==============================
    //    Getters/setters
    // ==============================
    
    /**
     * Get map of chromosome numbers to chromosome sizes.
     * @return Map of chromosome numbers to chromosome sizes.
     */
    public final Map<Short, Long> getChromosomeSizes() {
		return chromosomeSizes;
	}


    /**
     * Set map of chromosome numbers to chromosome sizes.
     * @param chromosomeSizes Map of chromosome numbers
     * to chromosome sizes.
     */
	public final void setChromosomeSizes(
			final Map<Short, Long> chromosomeSizes) {
		this.chromosomeSizes = chromosomeSizes;
	}


	/**
	 * Get map of chromosome numbers to maximum data
	 * values on corresponding chromosomes.
	 * @return Map of chromosome numbers to maximum data
	 * values on corresponding chromosomes.
	 */
	public final Map<Short, Float> getMaxValues() {
		return maxValues;
	}


	/**
	 * Set map of chromosome numbers to maximum data
	 * values on corresponding chromosomes.
	 * @param maxValues Map of chromosome numbers to maximum data
	 * values on corresponding chromosomes.
	 */
	public final void setMaxValues(
			final Map<Short, Float> maxValues) {
		this.maxValues = maxValues;
	}


	/**
	 * Get map of chromosome numbers to minimum data
	 * values on corresponding chromosomes.
	 * @return Map of chromosome numbers to minimum data
	 * values on corresponding chromosomes.
	 */
	public final Map<Short, Float> getMinValues() {
		return minValues;
	}


	/**
	 * Set map of chromosome numbers to minimum data
	 * values on corresponding chromosomes.
	 * @param minValues Map of chromosome numbers to minimum data
	 * values on corresponding chromosomes.
	 */
	public final void setMinValues(
			final Map<Short, Float> minValues) {
		this.minValues = minValues;
	}


	/**
	 * Get map of chromosome numbers to number of
	 * data elements on corresponding chromosomes.
	 * @return Map of chromosome numbers to number of
	 * data elements on corresponding chromosomes.
	 */
	public final Map<Short, Integer> getNumDatum() {
		return numDatum;
	}


	/**
	 * Set map of chromosome numbers to number of
	 * data elements on corresponding chromosomes.
	 * @param numDatum Map of chromosome numbers to number of
	 * data elements on corresponding chromosomes.
	 */
	public final void setNumDatum(
			final Map<Short, Integer> numDatum) {
		this.numDatum = numDatum;
	}


	/**
     * Set map of chromosome numbers to relative names
     * of files containg chromosome array data.
     * @return Map
     */
    public final SortedMap<Short, String> getChromosomeArrayDataFileIndex() {
        return chromosomeArrayDataFileIndex;
    }


    /**
     * Get map of chromosome numbers to relative
     * names of files containing chromosome array data.
     * @param chromosomeArrayDataFileIndex Map
     */
    public final void setChromosomeArrayDataFileIndex(
            final SortedMap<Short, String> chromosomeArrayDataFileIndex) {
        this.chromosomeArrayDataFileIndex = chromosomeArrayDataFileIndex;
    }
    
    
    // ==========================
    //     Constructors
    // ==========================
    
    
    /**
     * Constructor.
     */
    public DataSerializedBioAssay() {
        super();
    }


    /**
     * Constructor.
     * @param name Name of bioassay
     * @param organism Organism that was subject of bioassay
     */
    public DataSerializedBioAssay(final String name, final Organism organism) {
        super(name, organism);
    }

    
    // =============================
    //     Business methods
    // =============================
    
    
    /**
     * Get relative name of file (i.e., not absolute path)
     * containing chromosome array data from this bioassay
     * and from the given chromosome.  Clients calling
     * this method should know how to convert the relative
     * file name into an absolute path.
     * @param chromosome A chromosome number
     * @return Relative name of file, not absolute path.
     */
    public final String getFileName(final short chromosome) {
        return this.chromosomeArrayDataFileIndex.get(chromosome);
    }
    
    
    /**
     * This methods registers a chromosome array data set.
     * @param chromosomeArrayData Chromosome array data to register.
     * @param fileName Relative name of file, not absolute path.
     * Client classes should know how to convert the relative
     * file name into an absolute path.
     */
    public final void registerChromosomeArrayData(
    		final ChromosomeArrayData chromosomeArrayData,
            final String fileName) {
    	short chrom = chromosomeArrayData.getChromosome();
        this.chromosomeArrayDataFileIndex.put(chrom, fileName);
        this.chromosomeSizes.put(chrom,
        		chromosomeArrayData.inferredChromosomeSize());
        float min = chromosomeArrayData.getMinValue();
        if (Float.isNaN(min)) {
        	min = (float) 0.0;
        }
        this.minValues.put(chrom, min);
        float max = chromosomeArrayData.getMaxValue();
        if (Float.isNaN(max)) {
        	max = (float) 0.0;
        }
        this.maxValues.put(chrom, max);
        this.numDatum.put(chrom, chromosomeArrayData.numDatum());
    }
    
    
    // ==================================
    //    Implemented abstract methods
    // ==================================
    
    
    /**
     * Get set of chromosomes.
     * @return Chromosomes
     */
    public final SortedSet<Short> getChromosomes() {
        return new TreeSet<Short>(
                this.chromosomeArrayDataFileIndex.keySet());
    }
    
    
    /**
     * Get size of chromosome implied by the data.
     * @param chromosome Chromosome number
     * @return Size of chromosome implied by data
     */
    public final long inferredChromosomeSize(final short chromosome) {
    	long size = 0;
    	if (this.chromosomeSizes.containsKey(chromosome)) {
    		size = this.chromosomeSizes.get(chromosome);
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
    	for (float candidateMin : this.minValues.values()) {
    		if (!Float.isNaN(candidateMin)) {
	    		if (Float.isNaN(min) || candidateMin < min) {
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
    	for (float candidateMax : this.maxValues.values()) {
    		if (!Float.isNaN(candidateMax)) {
	    		if (Float.isNaN(max) || candidateMax > max) {
	    			max = candidateMax;
	    		}
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
    		Float candidateMin = this.minValues.get(chrom);
    		if (candidateMin != null) {
	    		if (!Float.isNaN(candidateMin)) {
		    		if (candidateMin != null) {
		    			if (Float.isNaN(min) || candidateMin < min) {
		    				min = candidateMin;
		    			}
		    		}
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
    		Float candidateMax = this.maxValues.get(chrom);
    		if (candidateMax != null) {
	    		if (!Float.isNaN(candidateMax)) {
		    		if (candidateMax != null) {
		    			if (Float.isNaN(max) || candidateMax > max) {
		    				max = candidateMax;
		    			}
		    		}
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
    	Integer numInt = this.numDatum.get(chromosome);
    	if (numInt != null) {
    		num = numInt.intValue();
    	}
    	return num;
    }
}
