/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.service.util;

import java.util.Iterator;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;


/**
 * Iterates over <code>ChromosomeArrayData</code>.
 * Shields client classes from the concern over
 * whether such data are in memory or serialized
 * to disk.
 * @author dhall
 *
 */
public abstract class ChromosomeArrayDataIterator {
    
    
    /** Iterator for chromosomes. */
    private final Iterator<Short> chromosomeIterator;
    
    
    /** Bioassay containing in-memory or serialized data. */
    private final BioAssay bioAssay;

    
    // =============================
    //       Constructors
    // =============================
        
    /**
     * Constructor.
     * @param bioAssay Bioassay containing in-memory or serialized data
     * to iterate over
     */
    protected ChromosomeArrayDataIterator(
            final BioAssay bioAssay) {
        this.bioAssay = bioAssay;
        this.chromosomeIterator = bioAssay.getChromosomes().iterator();
    }
    
    // ==================================
    //      Iterator interface
    // ==================================
    
    
    /**
     * Is there a next chromosome array data object?
     * @return T/F
     */
    public final boolean hasNext() {
        return this.chromosomeIterator.hasNext();
    }
    
    
    /**
     * Get next chromosome array data object.
     * @return Next chromosome array data object
     * or <code>null</code> if there is not one
     */
    public final ChromosomeArrayData next() {
        ChromosomeArrayData cad = null;
        if (this.hasNext()) {
            short chromosome = this.chromosomeIterator.next();
            cad = this.getChromosomeArrayData(this.bioAssay, chromosome);
        }
        return cad;
    }
    
    
    // ===============================
    //        Abstract methods
    // ===============================
    
    /**
     * Get chromosome array data.
     * @param bioAssay Bioassay containing data of interest
     * @param chromosome Chromosome number
     * @return Chromosome array data
     */
    protected abstract ChromosomeArrayData getChromosomeArrayData(
    		BioAssay bioAssay, short chromosome);
}
