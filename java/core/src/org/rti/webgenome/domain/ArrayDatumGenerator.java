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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * This class is used primarily for load testing.  It generates
 * a series of <code>ArrayDatum</code> objects.  While the generator
 * is creating these objects, it caches embedded <code>Reporter</code>
 * objects.  A call to the methods <code>reset()</code> causes
 * the class to cycle back to the beginning of the list of cached
 * reporters.  If the generator runs out of cached reporters before
 * the <code>reset()</code> is called, it generates new reporters
 * which are subsequently cached.
 * @author dhall
 *
 */
public final class ArrayDatumGenerator {
    
    // =====================
    //      Constants
    // =====================
    
	/** Default gap in base pairs between reporters. */
    private static final long DEF_GAP = (long) 1000;
    
    /** Default chromosome number. */
    private static final short DEF_CHROMOSOME = (short) 1;
    
    // ========================
    //     Attributes
    // ========================
    
    /** Gap in base pairs between reporters. */
    private long gap = DEF_GAP;
    
    /** Cached reporters. */
    private final Map<Short, List<Reporter>> reporters =
    	new HashMap<Short, List<Reporter>>();
    
    /** Iterator over cached reporters. */
    private ListIterator<Reporter> reporterIterator = null;
    
    /** Chromosome number. */
    private short chromosome = DEF_CHROMOSOME;
    
    /** Number of datum created for current chromosome. */
    private int count = 0;
    
    
    // ============================
    //       Getters/setters
    // ============================
    
    
    /**
     * Get current chromosome.
     * @return Current chromosome
     */
    public short getChromosome() {
		return chromosome;
	}


    /**
     * Set current chromosome.
     * @param chromosome Current chromosome
     */
	public void setChromosome(final short chromosome) {
		this.chromosome = chromosome;
		this.reporterIterator = null;
		this.count = 0;
	}


	/**
     * Get gap between reporters in base pairs.
     * @return Get gap between reporters in base pairs.
     */
    public long getGap() {
		return gap;
	}


    /**
     * Set gap between reporters in base pairs.
     * @param gap Gap between reporters in base pairs.
     */
	public void setGap(final long gap) {
		this.gap = gap;
	}


	/**
     * Get all reporters generated since object was
     * instantiated.
     * @return Reporters
     */
    public List<Reporter> getReporters() {
        List<Reporter> r = new ArrayList<Reporter>();
        for (Collection<Reporter> c : this.reporters.values()) {
        	r.addAll(c);
        }
        Collections.sort(r);
        return r;
    }
    
    
    // =================================
    //      Constructor
    // =================================


    /**
     * Constructor.
     */
    public ArrayDatumGenerator() {
        
    }
    
    
    /**
     * Constructor.
     * @param gap Gap between generated reporters
     * in base pairs.
     */
    public ArrayDatumGenerator(final long gap) {
    	this.gap = gap;
    }
    
    // ==================================
    //      Public methods
    // ==================================
    
    /**
     * Create new array datum.
     * @return Array datum
     */
    public ArrayDatum newArrayDatum() {
        ArrayDatum datum = new ArrayDatum();
        datum.setValue(this.randomFloat());
        datum.setError(this.randomFloat());
        datum.setReporter(this.getReporter());
        this.count++;
        return datum;
    }
    
    
    /**
     * Go back to the beginning of the list
     * of cached <code>Reporter</code> objects.
     * The next call to <code>newArrayDatum</code>
     * will obtain the reporter object from the first
     * element of the cached reporters, if such an element
     * exists.
     */
    public void reset() {
        this.reporterIterator = null;
    }
    
    
    /**
     * Return random floating point number.
     * @return Random floating point number
     */
    private float randomFloat() {
        return (float) Math.random();
    }
    
    
    /**
     * Get next reporter from the cache.  If we are
     * at the end of the cache list, first generate a
     * new reporter and put it at the end of the cache list.
     * @return A reporter
     */
    private Reporter getReporter() {
        Reporter r = null;
        if (this.reporterIterator == null) {
        	List<Reporter> l = this.reporters.get(this.chromosome);
        	if (l == null) {
        		l = new ArrayList<Reporter>();
        		this.reporters.put(this.chromosome, l);
        	}
        	this.reporterIterator = l.listIterator();
        }
        if (!this.reporterIterator.hasNext()) {
            this.reporterIterator.add(this.newReporter());
            r = this.reporterIterator.previous();
            this.reporterIterator.next();
        } else {
            r = this.reporterIterator.next();
        }
        return r;
    }
    
    
    /**
     * Create matrix of new array datum objects.
     * @param chromosome Chromosome number
     * @param locations Reporter chromosome locations
     * @param values Values
     * @return Array datum objects
     */
    public static ArrayDatum[][] newArrayData(
    		final short chromosome, final long[] locations,
    		final float[][] values) {
    	for (int i = 0; i < values.length; i++) {
    		if (locations.length != values[i].length) {
    			throw new IllegalArgumentException(
    					"The number of values for all arrays must "
    					+ "be equal to the number of locations");
    		}
    	}
    	
    	// Generate reporters
    	int n = locations.length;
    	Reporter[] reporters = new Reporter[n];
    	for (int i = 0; i < n; i++) {
    		reporters[i] = new Reporter("r", chromosome, locations[i]);
    	}
    	
    	// Generate array datum
    	ArrayDatum[][] newData = new ArrayDatum[values.length][];
    	for (int i = 0; i < values.length; i++) {
    		newData[i] = new ArrayDatum[n];
    		for (int j = 0; j < n; j++) {
    			newData[i][j] = new ArrayDatum(values[i][j], reporters[j]);
    		}
    	}
    	
    	return newData;
    }
    
    
    /**
     * Generate a new reporter object.
     * @return A new reporter
     */
    private Reporter newReporter() {
        String name = "Reporter" + (this.reporters.size() + 1);
        long position = this.count * this.gap;
        return new Reporter(name, this.chromosome, position);
    }

}
