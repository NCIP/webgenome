/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Aggregation of <code>ArrayDatum</code> objects from same chromosome.
 * @author dhall
 *
 */
public class ChromosomeArrayData implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
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
    
    /** Array data from chromosome. */
    private SortedSet<ArrayDatum> arrayData = new TreeSet<ArrayDatum>(
            new ArrayDatum.ChromosomeLocationComparator());
    
    /** Chromosome number. */
    private short chromosome = -1;

    
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
    public final void setBioAssayDataId(Long bioAssayDataId) {
        this.bioAssayDataId = bioAssayDataId;
    }

    /**
     * Get array data from chromosome.
     * @return Array data
     */
    public final SortedSet<ArrayDatum> getArrayData() {
        return arrayData;
    }

    /**
     * Set array data from chromosome.
     * @param arrayData Array data
     */
    public final void setArrayData(final SortedSet<ArrayDatum> arrayData) {
        this.arrayData = arrayData;
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
     * @throws IllegalArgumentException if given array datum
     * is on a difference chromosome.
     */
    public final void add(final ArrayDatum arrayDatum) {
        if (!this.onChromosome(arrayDatum.getReporter())) {
            throw new IllegalArgumentException(
                    "Array datum not on same chromosome");
        }
        this.arrayData.add(arrayDatum);
    }
    
    
    /**
     * Return all array data between the given chromosomal
     * location endpoints inclusive.
     * @param from Left endpoint
     * @param to Right endpoint
     * @return Array data
     * @throws IllegalArgumentException if <code>from > to </code>
     */
    public final SortedSet<ArrayDatum> getArrayData(
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
        Comparator c = new ArrayDatum.ChromosomeLocationComparator();
        List<ArrayDatum> list = new ArrayList<ArrayDatum>(this.arrayData);
        int p = Collections.binarySearch(list, a1, c);
        int q = Collections.binarySearch(list, a2, c);
        if (p < 0) {
            p = -p - 1;
        }
        if (q < 0) {
            q = -q - 2;
        }
        assert p <= q && p >= 0 && q < list.size();
        
        // Fill set to be returned
        SortedSet<ArrayDatum> included = new TreeSet<ArrayDatum>(
                new ArrayDatum.ChromosomeLocationComparator());
        for (int i = p; i <= q; i++) {
            included.add(list.get(i));
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
    public Set<Reporter> getReporters() {
        Set<Reporter> reporters = new HashSet<Reporter>();
        for (ArrayDatum d : this.arrayData) {
            reporters.add(d.getReporter());
        }
        return reporters;
    }
}
