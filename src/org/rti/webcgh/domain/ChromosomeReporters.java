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
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Aggregation of all reporters originating from the
 * same chromosome from a particular microarray chip.
 * @author dhall
 *
 */
public class ChromosomeReporters implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    // ==============================
    //        Attributes
    // ==============================
    
    /** Chromosome number. */
    private short chromosome = (short) -1;
    
    /** Reporters. */
    private SortedSet<Reporter> reporters = new TreeSet<Reporter>();

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
     * Get reporters.
     * @return Reporters
     */
    public final SortedSet<Reporter> getReporters() {
        return reporters;
    }

    /**
     * Set reporters.
     * @param reporters Reporters
     */
    public final void setReporters(final SortedSet<Reporter> reporters) {
        this.reporters = reporters;
    }
    
    
    // =================================
    //      Constructors
    // =================================
    
    /**
     * Constructor.
     */
    public ChromosomeReporters() {
        
    }

    
    /**
     * Constructor.
     * @param chromosome Chromosome number
     */
    public ChromosomeReporters(final short chromosome) {
        this.chromosome = chromosome;
    }

    
    // ===============================
    //       Business methods
    // ===============================
    
    /**
     * Add a reporter.
     * @param reporter A reporter
     */
    public void add(Reporter reporter) {
        this.reporters.add(reporter);
    }
}
