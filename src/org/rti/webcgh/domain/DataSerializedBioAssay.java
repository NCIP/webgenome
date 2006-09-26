/*
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
    private static final long serialVersionUID = (long) 1;
    
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
    
    
    // ==============================
    //    Getters/setters
    // ==============================
    
    
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
        this.minValues.put(chrom, chromosomeArrayData.getMinValue());
        this.maxValues.put(chrom, chromosomeArrayData.getMaxValue());
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
	    		if (Float.isNaN(candidateMin) || candidateMin < min) {
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
	    		if (Float.isNaN(candidateMax) || candidateMax > max) {
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
    		if (!Float.isNaN(candidateMin)) {
	    		if (candidateMin != null) {
	    			if (Float.isNaN(min) || candidateMin < min) {
	    				min = candidateMin;
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
    		if (!Float.isNaN(candidateMax)) {
	    		if (candidateMax != null) {
	    			if (Float.isNaN(max) || candidateMax > max) {
	    				max = candidateMax;
	    			}
	    		}
    		}
    	}
    	return max;
    }
}
