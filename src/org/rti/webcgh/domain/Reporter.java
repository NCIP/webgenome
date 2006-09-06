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

/**
 * Represents a microarray reporter (i.e., a probe).
 * @author dhall
 *
 */
public class Reporter implements Serializable, Comparable {

    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    
    // ======================================
    //       Attributes
    // ======================================
    
    /** Identifier used for persisting object. */
    private Long id = null;
    
    /** Name of reporter. */
    private String name = null;
    
    /** Chromosome number. */
    private short chromosome = -1;
    
    /** Chromosome location. */
    private long location = (long) -1;

    
    /**
     * Get chromosome number.
     * @return Chromosome number
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
     * Get identifier (used for persisting object).
     * @return Identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set identifier (used for persisting object).
     * @param id Identifier
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get chromosomal location.
     * @return Chromosomal location
     */
    public final long getLocation() {
        return location;
    }

    /**
     * Set chromosomal location.
     * @param location Chromosomal location
     */
    public final void setLocation(final long location) {
        this.location = location;
    }

    /**
     * Get name of reporter.
     * @return Name of reporter
     */
    public final String getName() {
        return name;
    }

    /**
     * Set name of reporter.
     * @param name Name of reporter
     */
    public final void setName(final String name) {
        this.name = name;
    }
    
    
    // ========================================
    //        Constructors
    // ========================================
    
    /**
     * Default constructor.
     */
    public Reporter() {
        
    }

    /**
     * Constructor.
     * @param name Name of reporter
     * @param chromosome Chromosome number
     * @param location Chromosome location of reporter
     */
    public Reporter(final String name, final short chromosome,
            final long location) {
        this.name = name;
        this.chromosome = chromosome;
        this.location = location;
    }

    // ==================================
    //     Comparable interface
    // ==================================
    
    /**
     * Comparison method.  Sorts by chromosome number and
     * chromosome position.
     * @param o An object.  This object must be of type
     * <code>Reporter</code> or an IllegalArgumentException
     * will be thrown.
     * @return
     *  <ul>
     *      <li>-1 If this reporter has a lower number
     *      chromosome or same chromosome and lower location
     *      than given reporter object</li>
     *      <li>0 If this reporter on same chromosome
     *      at some location than given reporter object</li>
     *      <li>1 If this reporter on higher number
     *      chromosome or same chromosome and higher location
     *      than given reporter.
     *  </ul>
     */
    public final int compareTo(final Object o) {
        if (!(o instanceof Reporter)) {
            throw new IllegalArgumentException(
                    "Expecting object of type Reporter");
        }
        Reporter r = (Reporter) o;
        int val = 0;
        if (this.chromosome < r.chromosome) {
            val = -1;
        } else if (this.chromosome == r.chromosome) {
            if (this.location < r.location) {
                val = -1;
            } else if (this.location == r.location) {
                val = 0;
            } else if (this.location > r.location) {
                val = 1;
            }
        } else if (this.chromosome > r.chromosome) {
            val = 1;
        }
        return val;
    }
    
    
}
