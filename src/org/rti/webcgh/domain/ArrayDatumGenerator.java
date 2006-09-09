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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
    
    /** Chromosome number of all generated data. */
    private static final short CHROMOSOME = (short) 1;
    
    // ========================
    //     Attributes
    // ========================
    
    /** Gap in base pairs between reporters. */
    private long gap = DEF_GAP;
    
    /** Cached reporters. */
    private final List<Reporter> reporters = new ArrayList<Reporter>();
    
    /** Iterator over cached reporters. */
    private ListIterator<Reporter> reporterIterator =
        reporters.listIterator();
    
    
    // ============================
    //       Getters/setters
    // ============================
    
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
        return reporters;
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
        this.reporterIterator = this.reporters.listIterator();
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
     * Generate a new reporter object.
     * @return A new reporter
     */
    private Reporter newReporter() {
        String name = "Reporter" + (this.reporters.size() + 1);
        long position = this.reporters.size() * this.gap;
        return new Reporter(name, CHROMOSOME, position);
    }

}
