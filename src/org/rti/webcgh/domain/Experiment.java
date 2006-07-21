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
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.util.StringUtils;

/**
 * Represents a microarray experiment.  Essentially this class is
 * an aggregation of <code>BioAssay</code> objects.
 * @author dhall
 *
 */
public class Experiment implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    // ======================================
    //         Attributes
    // ======================================
    
    /** Identifier used for persistence. */
    private Long id = null;
    
    /** Name of experiment. */
    private String name = null;
    
    /** Bioassays performed during experiment. */
    private Set<BioAssay> bioAssays = new HashSet<BioAssay>();

    
    /**
     * Get bioassays performed during experiment.
     * @return Bioassays
     */
    public final Set<BioAssay> getBioAssays() {
        return bioAssays;
    }

    /**
     * Set bioassays performed during experiment.
     * @param bioAssays Bioassays
     */
    public final void setBioAssays(final Set<BioAssay> bioAssays) {
        this.bioAssays = bioAssays;
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

    /**
     * Get experiment name.
     * @return Name of experiment
     */
    public final String getName() {
        return name;
    }

    /**
     * Set experiment name.
     * @param name Name of experiment.
     */
    public final void setName(final String name) {
        this.name = name;
    }
    
    // ==================================
    //       Constructors
    // ==================================
    
    /**
     * Default constructor.
     */
    public Experiment() {
        
    }

    /**
     * Constructor.
     * @param name Name of experiment
     */
    public Experiment(final String name) {
        this.name = name;
    }
        
    
    // ====================================
    //        Business methods
    // ====================================
    
    /**
     * Add a bioassay to this experiment.
     * @param bioAssay A bioassay
     */
    public final void add(final BioAssay bioAssay) {
        this.bioAssays.add(bioAssay);
    }
    
    
    /**
     * Is given experiment "synonymous" with this?  Synonymous
     * is like "equals," except the id properties do not have
     * to match.
     * @param exp An experiment
     * @return T/F
     */
    public final boolean synonymousWith(final Experiment exp) {
        boolean match = StringUtils.equal(this.name, exp.name);
        for (Iterator<BioAssay> it1 = this.bioAssays.iterator();
            it1.hasNext() && match;) {
            match = false;
            BioAssay ba1 = it1.next();
            for (Iterator<BioAssay> it2 = exp.bioAssays.iterator();
                it2.hasNext() && !match;) {
                BioAssay ba2 = it2.next();
                if (ba1.synonymousWith(ba2)) {
                    match = true;
                }
            }
        }
        return match;
    }
    
    
    /**
     * Get set of chromosomes within this experiment.
     * @return Chromosomes
     */
    public final SortedSet<Short> getChromosomes() {
        SortedSet<Short> chroms = new TreeSet<Short>();
        for (BioAssay ba : this.bioAssays) {
            chroms.addAll(ba.getChromosomes());
        }
        return chroms;
    }
}
