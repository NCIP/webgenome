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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Aggregate of all data from a bioassay (i.e., a single micorarray
 * treated with a single biological sample).
 * @author dhall
 *
 */
public class BioAssayData implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    // =====================================
    //         Attributes
    // =====================================
    
    /** Identifier used for persistence. */
    private Long id = null;
    
    /** Map of chromosome array data indexed by chromosome number. */
    private SortedMap<Short, ChromosomeArrayData> chromosomeArrayData = 
        new TreeMap<Short, ChromosomeArrayData>();
    
    // ==================================
    //    Getters/setters
    // ==================================

    /**
     * Get chromosome array data.
     * @return Chromosome array data
     */
    public final SortedMap<Short, ChromosomeArrayData>
        getChromosomeArrayData() {
        return chromosomeArrayData;
    }

    /**
     * Set chromosome array data.
     * @param chromosomeArrayData Chromosome array data
     */ 
    public final void setChromosomeArrayData(
            final SortedMap<Short, ChromosomeArrayData> chromosomeArrayData) {
        this.chromosomeArrayData = chromosomeArrayData;
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

    
    // =============================================
    //         Other public methods
    // =============================================
    
    /**
     * Add array datum.
     * @param arrayDatum An array datum
     */
    public final void add(final ArrayDatum arrayDatum) {
        Short chrom = arrayDatum.getReporter().getChromosome();
        ChromosomeArrayData cad = this.chromosomeArrayData.get(chrom);
        if (cad == null) {
            cad = new ChromosomeArrayData(chrom);
            this.chromosomeArrayData.put(chrom, cad);
        }
        cad.add(arrayDatum);
    }
    
    
    /**
     * Add chromosome array data.  If an existing
     * <code>ChromosomeArrayData</code> object has already
     * been added from same chromosome, the original object
     * is displaced by given object.
     * @param cad Chromosmome array data
     */
    public final void add(final ChromosomeArrayData cad) {
        this.chromosomeArrayData.put(cad.getChromosome(), cad);
    }
    
    
    /**
     * Get array data from given chromosome.
     * @param chromosome Chromosome number
     * @return Array data
     */
    public final List<ArrayDatum> getArrayData(final short chromosome) {
        List<ArrayDatum> data = null;
        ChromosomeArrayData cad = this.chromosomeArrayData.get(chromosome);
        if (cad != null) {
            data = cad.getArrayData();
        }
        return data;
    }
    
    
    /**
     * Get all reporters.
     * @return Reporters
     */
    public final Set<Reporter> getReporters() {
        Set<Reporter> reporters = new HashSet<Reporter>();
        for (ChromosomeArrayData cad : this.chromosomeArrayData.values()) {
            reporters.addAll(cad.getReporters());
        }
        return reporters;
    }
}
