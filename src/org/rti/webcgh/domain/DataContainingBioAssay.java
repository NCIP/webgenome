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

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
     * Get chromosome array data from given chromosome.
     * @param chromosome Chromosome number
     * @return Chromosome array data
     */
    public final ChromosomeArrayData
        getChromosomeArrayData(final short chromosome) {
        return this.chromosomeArrayDataIndex.get(chromosome);
    }

}
