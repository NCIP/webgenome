/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.rti.webgenome.util.SystemUtils;


/**
 * Aggregate of all data from a bioassay (i.e., a single micorarray
 * treated with a single biological sample).
 * @author dhall
 *
 */
public class BioAssayData implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
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
