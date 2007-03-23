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
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
    			this.add(new ArrayDatum(bioAssayData[i]));
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
}
