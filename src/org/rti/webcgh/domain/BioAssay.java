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
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.rti.webcgh.util.StringUtils;


/**
 * Represents a bioassay (i.e., the hybridization of one microarray
 * against one biological sample).
 * @author dhall
 *
 */
public class BioAssay implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    

    // =====================================
    //         Attributes
    // =====================================
    
    /** Identifier used for persistence. */
    private Long id = null;
    
    /** Identifier of associated <code>BioAssayData</code> object. */
    private Long bioAssayDataId = null;
    
    /** Name of bioassay. */
    private String name = null;
    
    /** Organism that was tested. */
    private Organism organism = null;
    
    /** Bioassay data. */
    private BioAssayData bioAssayData = null;
    
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
    
    /** Microarray model used in bioassay. */
    private Array array = null;
    
    // ===============================
    //       Getters/setters
    // ===============================
    
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

    /**
     * Get bioassay data.
     * @return Bioassay data
     */
    public final BioAssayData getBioAssayData() {
        return bioAssayData;
    }


    /**
     * Set bioassay data.
     * @param bioAssayData Bioassay data
     */
    public final void setBioAssayData(final BioAssayData bioAssayData) {
        this.bioAssayData = bioAssayData;
    }


    /**
     * Get identifier of associated <code>BioAssayData</code> object.
     * @return Identifier
     */
    public final Long getBioAssayDataId() {
        return bioAssayDataId;
    }


    /**
     * Set identifier of associated <code>BioAssayData</code> object.
     * @param bioAssayDataId Identifier
     */
    public final void setBioAssayDataId(final Long bioAssayDataId) {
        this.bioAssayDataId = bioAssayDataId;
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
     * @param bioAssayDataId Identifier of associated bioassay data
     */
    public BioAssay(final String name, final Organism organism,
            final Long bioAssayDataId) {
        this(name, organism);
        this.bioAssayDataId = bioAssayDataId;
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
            && this.organism == ba.organism
            && this.bioAssayDataId == ba.bioAssayDataId;
    }
    
    
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
     * Set relative name of file (i.e., not absolute path)
     * containing chromosome array data from this bioassay
     * for the given chromosome.  Clients calling
     * this method should know how to convert the relative
     * file name into an absolute path.
     * @param chromosome A chromosome number
     * @param fileName Relative name of file, not absolute path.
     */
    public final void setFileName(final short chromosome,
            final String fileName) {
        this.chromosomeArrayDataFileIndex.put(chromosome, fileName);
    }
    
    /**
     * Get set of chromosomes.
     * @return Chromosomes
     */
    public final SortedSet<Short> getChromosomes() {
        return new TreeSet<Short>(
                this.chromosomeArrayDataFileIndex.keySet());
    }
}
