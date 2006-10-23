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

import org.rti.webcgh.util.SystemUtils;

/**
 * Represents an organism.
 * @author dhall
 *
 */
public class Organism implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    
    // ============================
    //       Constants
    // ============================
    
    /** Unknown organism. */
    public static final Organism UNKNOWN_ORGANISM =
    	new Organism("Unknown", "Unknown");
    
    
    // ======================================
    //         Attributes
    // ======================================
    
    /** Identifier used as primary key in persistence. */
    private Long id = null;
    
    /** Taxinomic genus. */
    private String genus = null;
    
    /** Taxinomic species. */
    private String species = null;
    
    /**
     * Get identifier (used as primary key in persistence).
     * @return Identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set identifier (used as primary key in persistence).
     * @param id Identifier
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Set taxonimic genus.
     * @param genus Genus
     */
    public final void setGenus(final String genus) {
        this.genus = genus;
    }

    /**
     * Set taxonomic species.
     * @param species Species
     */
    public final void setSpecies(final String species) {
        this.species = species;
    }

    /**
     * Get the genus.
     * @return Genus
     */
    public final String getGenus() {
        return genus;
    }

    /**
     * Get the species.
     * @return Species
     */
    public final String getSpecies() {
        return species;
    }

    
    // ==================================
    //      Constructors
    // ==================================
    
    
    /**
     * Default constructor.
     */
    public Organism() {
        
    }
    
    /**
     * Constructor.
     * @param genus The genus
     * @param species The species
     */
    public Organism(final String genus, final String species) {
        this.genus = genus;
        this.species = species;
    }
    
    
    // ===============================
    //     Other business methods
    // ===============================
    
    /**
     * Get display name.
     * @return Display name
     */
    public final String getDisplayName() {
    	return this.genus + " " + this.species;
    }
}
