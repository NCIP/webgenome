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

import java.awt.Color;
import java.io.Serializable;
import java.util.SortedSet;

import org.rti.webcgh.util.StringUtils;


/**
 * Abstract base class representing
 * a bioassay (i.e., the hybridization of one microarray
 * against one biological sample).
 * @author dhall
 *
 */
public abstract class BioAssay implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    

    // =====================================
    //         Attributes
    // =====================================
    
    /** Identifier used for persistence. */
    private Long id = null;
    
    /** Name of bioassay. */
    private String name = null;
    
    /** Organism that was tested. */
    private Organism organism = null;
    
    /** Microarray model used in bioassay. */
    private Array array = null;
    
    /** Color of bioassay in plots. */
    private Color color = null;
    
    // ===============================
    //       Getters/setters
    // ===============================
    
    /**
     * Get color used in plots.
     * @return Color used in plots
     */
    public final Color getColor() {
        return color;
    }


    /**
     * Set color used in plots.
     * @param color Color used in plots
     */
    public final void setColor(final Color color) {
        this.color = color;
    }
    
    /**
     * Get microarray used in bioassay.
     * @return Array
     */
    public final Array getArray() {
        return array;
    }


    /**
     * Set microarray used in bioassay.
     * @param array An array
     */
    public final void setArray(final Array array) {
        this.array = array;
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
     * @param id Identifer
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get name of bioassay.
     * @return Name
     */
    public final String getName() {
        return name;
    }

    /**
     * Set name of bioassay.
     * @param name Name
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Get organism that was tested.
     * @return An organism
     */
    public final Organism getOrganism() {
        return organism;
    }

    /**
     * Set organism that was tested.
     * @param organism An organism
     */
    public final void setOrganism(final Organism organism) {
        this.organism = organism;
    }

    // =======================================
    //         Constructors
    // =======================================
    
    /**
     * Default constructor.
     */
    public BioAssay() {
        
    }

    /**
     * Constructor.
     * @param name Name of bioassay
     * @param organism Organism that was tested
     */
    public BioAssay(final String name, final Organism organism) {
        this.name = name;
        this.organism = organism;
    }
    
    
    /**
     * Constructor.
     * @param name Name of bioassay
     * @param organism Organism that was tested
     * @param array Array
     */
    public BioAssay(final String name, final Organism organism,
            final Array array) {
        this.name = name;
        this.organism = organism;
        this.array = array;
    }
    
    
    // ===================================
    //    Business methods
    // ===================================
    
    /**
     * Is given bioassay "synonymous" with this?
     * Synonymous means that the two bioassays share
     * common properties with the exception of
     * id.
     * @param ba A bioassay
     * @return T/F
     */
    public final boolean synonymousWith(final BioAssay ba) {
        return
            StringUtils.equal(this.name, ba.name)
            && this.organism == ba.organism;
    }
    
    
    // ==============================
    //      Abstract methods
    // ==============================

    
    /**
     * Get set of chromosomes.
     * @return Chromosomes
     */
    public abstract SortedSet<Short> getChromosomes();
}
