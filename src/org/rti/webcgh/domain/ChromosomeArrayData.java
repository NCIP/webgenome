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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.util.SystemUtils;

/**
 * Aggregation of <code>ArrayDatum</code> objects from same chromosome.
 * @author dhall
 *
 */
public class ChromosomeArrayData implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // ======================================
    //         Attributes
    // ======================================
    
    /** Identifier used for persistence. */
    private Long id = null;
    
    /**
     * Identifier of bioassay data to which this belongs.
     * A referece to the actual bioassay data object is not included
     * to support controling memory usage.  Chromosome array
     * data may be loaded apart from the bioassay data object
     * that owns it.
     */
    private Long bioAssayDataId = null;
    
    /** Array data from chromosome. This list is kept sorted. */
    private SortedSet<ArrayDatum> arrayData = new TreeSet<ArrayDatum>(
    		new ArrayDatum.ChromosomeLocationComparator());
    
    /** Chromosome number. */
    private short chromosome = -1;
    
    /**
     * Minimum value.  This value is the sum of
     * the value and error of some <code>ArrayDatum</code>
     * object.
     */
    private float minValue = Float.NaN;
    
    /**
     * Maximum value.  This value is the sum of
     * the value and error of some <code>ArrayDatum</code>
     * object.
     */
    private float maxValue = Float.NaN;
    
    // =================================
    //    Getters/setters
    // =================================
    
    /**
     * Get maximum value.  This value is the sum of
     * the value and error of some <code>ArrayDatum</code>
     * object.
     * @return Maximum value
     */
    public final float getMaxValue() {
		return maxValue;
	}

    
    /**
     * Get minimum value.  This value is the sum of
     * the value and error of some <code>ArrayDatum</code>
     * object.
     * @return Minimum value
     */
	public final float getMinValue() {
		return minValue;
	}

	/**
     * Get identifier of bioassay data
     * to which this chromosome array data belongs.
     * @return Bioassay identifier
     */
    public final Long getBioAssayDataId() {
        return bioAssayDataId;
    }

    /**
     * Set identifier of bioassay data
     * to which this chromosome array data belongs.
     * @param bioAssayDataId Bioassay identifier
     */
    public final void setBioAssayDataId(final Long bioAssayDataId) {
        this.bioAssayDataId = bioAssayDataId;
    }

    /**
     * Get array data from chromosome.  The list
     * returned will be sorted.
     * @return Array data
     */
    public final List<ArrayDatum> getArrayData() {
        return new ArrayList<ArrayDatum>(this.arrayData);
    }

    /**
     * Set array data from chromosome.
     * @param arrayData Array data
     */
    public final void setArrayData(final List<ArrayDatum> arrayData) {
        for (ArrayDatum ad : arrayData) {
        	this.add(ad);
        }
    }

    /**
     * Get chromosome number.
     * @return Chromosome number.
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
     * Get identifier used for persistence.
     * @return Identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set identifier used for persistence.
     * @param id Identifier
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    
    // ==================================
    //         Constructors
    // ==================================
    
    /**
     * Default constructor.
     */
    public ChromosomeArrayData() {
        
    }
    
    /**
     * Constructor.
     * @param chromosome Chromosome number.
     */
    public ChromosomeArrayData(final short chromosome) {
        this.chromosome = chromosome;
    }
    
    
    // ===========================================
    //      Other public methods
    // ===========================================
    
    /**
     * Add an array datum.
     * @param arrayDatum An array datum
     */
    public final void add(final ArrayDatum arrayDatum) {
        if (!this.onChromosome(arrayDatum.getReporter())) {
            throw new IllegalArgumentException(
                    "Array datum not on same chromosome");
        }
        this.arrayData.add(arrayDatum);
        this.updateMinAndMax(arrayDatum);
    }
    
    
    /**
     * Return all array data between the given chromosomal
     * location endpoints inclusive.
     * @param from Left endpoint
     * @param to Right endpoint
     * @return Array data
     */
    public final List<ArrayDatum> getArrayData(
            final long from, final long to) {
        
        // Check args
        if (from > to) {
            throw new IllegalArgumentException("From cannot be larget than to");
        }
        
        // Find indices of endpoints of data that fall within given
        // range
        Reporter r1 = new Reporter(null, this.chromosome, from);
        Reporter r2 = new Reporter(null, this.chromosome, to);
        ArrayDatum a1 = new ArrayDatum(Float.NaN, r1);
        ArrayDatum a2 = new ArrayDatum(Float.NaN, r2);
        Comparator<ArrayDatum> c =
        	new ArrayDatum.ChromosomeLocationComparator();
        List<ArrayDatum> arrayDataList =
        	new ArrayList<ArrayDatum>(this.arrayData);
        int p = Collections.binarySearch(arrayDataList, a1, c);
        int q = Collections.binarySearch(arrayDataList, a2, c);
        if (p < 0) {
            p = -p - 1;
        }
        if (q < 0) {
            q = -q - 2;
        }
        assert p <= q && p >= 0 && q < this.arrayData.size();
        
        // Fill set to be returned
        List<ArrayDatum> included = new ArrayList<ArrayDatum>();
        for (int i = p; i <= q; i++) {
            included.add(arrayDataList.get(i));
        }
        
        return included;
    }
    
    
    /**
     * Is given reporter on this chromosome?
     * @param reporter A reporter
     * @return T/F
     */
    public final boolean onChromosome(final Reporter reporter) {
        return reporter.getChromosome() == this.chromosome;
    }
    
    
    /**
     * Get all reporters.
     * @return Reporters
     */
    public final SortedSet<Reporter> getReporters() {
        SortedSet<Reporter> reporters = new TreeSet<Reporter>();
        for (ArrayDatum d : this.arrayData) {
            reporters.add(d.getReporter());
        }
        return reporters;
    }
    
    
    /**
     * Get inferred chromosome size.  This will be equal to
     * the position of the rightmost reporter.
     * @return Inferred chromosome size
     */
    public final long inferredChromosomeSize() {
        long size = (long) 0;
        int n = this.arrayData.size();
        if (n > 0) {
        	size = this.arrayData.last().getReporter().getLocation();
        }
        return size;
    }
    
    
    /**
     * Find minimum value.  If <code>includeError</code>
     * is <code>true</code>, then method looks for minimum
     * <code>value</code> - <code>error</code>.
     * @param includeError Should errors be subtracted
     * from values?
     * @return Minimum value or NaN if data are empty
     */
    public final float minValue(final boolean includeError) {
        if (this.arrayData.size() < 1) {
            return Float.NaN;
        }
        float min = Float.MAX_VALUE;
        for (ArrayDatum datum : this.arrayData) {
            float candidateMin = datum.getValue();
            if (includeError) {
                candidateMin -= datum.getError() / (float) 2.0;
            }
            if (candidateMin < min) {
                min = candidateMin;
            }
        }
        return min;
    }
    
    
    /**
     * Find maximum value.  If <code>includeError</code>
     * is <code>true</code>, then method looks for maximum
     * <code>value</code> + <code>error</code>.
     * @param includeError Should errors be added
     * to values?
     * @return Maximum value or NaN if data are empty
     */
    public final float maxValue(final boolean includeError) {
        if (this.arrayData.size() < 1) {
            return (float) Float.NaN;
        }
        float max = Float.MIN_VALUE;
        for (ArrayDatum datum : this.arrayData) {
            float candidateMax = datum.getValue();
            if (includeError) {
                candidateMax += datum.getError() / (float) 2.0;
            }
            if (candidateMax > max) {
                max = candidateMax;
            }
        }
        return max;
    }
    
    
    /**
     * Update <code>minValue</code> and
     * <code>maxValue</code>.
     * @param datum An array datum that may
     * or may not contain the maximum or
     * minimum value.
     */
    private void updateMinAndMax(final ArrayDatum datum) {
    	if (!Float.isNaN(datum.getValue())) {
    		float candidateMin = datum.getValue();
    		float candidateMax = candidateMin;
    		if (!Float.isNaN(datum.getError())) {
    			float error = datum.getError();
    			candidateMin -= error;
    			candidateMax += error;
    		}
    		if (Float.isNaN(this.minValue)
    				|| candidateMin < this.minValue) {
    			this.minValue = candidateMin;
    		}
    		if (Float.isNaN(this.maxValue)
    				|| candidateMax > this.maxValue) {
    			this.maxValue = candidateMax;
    		}
    	}
    }
}
