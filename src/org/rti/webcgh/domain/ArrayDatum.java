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
import java.util.Comparator;

/**
 * Represents one data point from a microarray experiment.
 * @author dhall
 *
 */
public class ArrayDatum implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    
    // ================================
    //       Attributes
    // ================================
    
    /** Identifier used for persistence. */
    private Long id = null;
    
    /** Measured experimental value. */
    private float value = Float.NaN;
    
    /** 
     * Experimental error measurement.  This value will be calculated
     * by methods in the application.
     */
    private float error = (float) 1.0;
    
    /** Reporter that took this measurement. **/
    private Reporter reporter = null;

    
    /**
     * Set experimental error measurement.
     * @return Error
     */
    public final float getError() {
        return error;
    }

    /**
     * Get experimental error measurement.
     * @param error Experimental error
     */
    public final void setError(final float error) {
        this.error = error;
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
     * Get reporter that generated this datum.
     * @return A reporter
     */
    public final Reporter getReporter() {
        return reporter;
    }

    /**
     * Set reporter that generated this datum.
     * @param reporter A reporter
     */
    public final void setReporter(final Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     * Get experimentally measured value.
     * @return Experimentally measured value
     */
    public final float getValue() {
        return value;
    }

    /**
     * Set experimentally measured value.
     * @param value Experimentally measured value
     */
    public final void setValue(final float value) {
        this.value = value;
    }

    
    
    // =======================================
    //         Constructors
    // =======================================
    
    
    /**
     * Default constructor.
     */
    public ArrayDatum() {
        
    }
    
    
    /**
     * Constructor.
     * @param value Experimentally measured value
     * @param error Experimental error
     * @param reporter Reporter that produced this measurement
     */
    public ArrayDatum(final float value, final float error,
            final Reporter reporter) {
        this.value = value;
        this.error = error;
        this.reporter = reporter;
    }
    
    
    /**
     * Constructor.
     * @param value Experimentally measured value
     * @param reporter Reporter that produced this measurement
     */
    public ArrayDatum(final float value, final Reporter reporter) {
        this(value, (float) 1.0, reporter);
    }
    
    
    /**
     * Comparator used for sorting of <code>ArrayDatum</code> objects
     * by chromosome location.
     * @author dhall
     *
     */
    public static class ChromosomeLocationComparator
    implements Comparator {

        /**
         * Compare method.
         * @param o1 First object.  Must be of type <code>ArrayDatum</code>
         * or an IllegalArgumentException will be thrown.
         * @param o2 Second object  Must be of type <code>ArrayDatum</code>
         * or an IllegalArgumentException will be thrown.
         * @return Returns -1 if o1 has lower chromosome location, 0 if
         * o1 has equal chromosome location, or 1 if o1 has greater
         * chromosome location than o2.
         */
        public final int compare(final Object o1, final Object o2) {
            if (!(o1 instanceof ArrayDatum && o2 instanceof ArrayDatum)) {
                throw new IllegalArgumentException(
                        "Both objects must be of type ArrayDatum");
            }
            return ((ArrayDatum) o1).getReporter().compareTo(
                    ((ArrayDatum) o2).getReporter());
        }
    }
}
