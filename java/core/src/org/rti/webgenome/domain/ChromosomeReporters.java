/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webgenome.util.SystemUtils;

/**
 * Aggregation of all reporters originating from the
 * same chromosome from a particular microarray chip.
 * @author dhall
 *
 */
public class ChromosomeReporters implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
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
    public final void add(final Reporter reporter) {
        this.reporters.add(reporter);
    }
}
