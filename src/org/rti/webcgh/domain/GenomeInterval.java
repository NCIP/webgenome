/*
$Revision: 1.1 $
$Date: 2006-09-16 04:29:21 $

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

/**
 * Represents a genome interval.
 * @author dhall
 *
 */
public class GenomeInterval {

	// =========================
	//      Attributes
	// =========================
	
	/** Chromosome number. */
	private short chromosome = (short) -1;
	
	/** Chromosomal start location of interval in base pairs. */
	private long startLocation = -1;
	
	/** Chromosomal end location of interval in base pairs. */
	private long endLocation = -1;
	
	
	// =========================
	//     Getters/setters
	// =========================

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
	 * Get chromosomal end location of interval.
	 * @return Chromosomal end location of interval.
	 */
	public final long getEndLocation() {
		return endLocation;
	}

	
	/**
	 * Set chromosomal end location of interval.
	 * @param endLocation Chromosomal end location of interval.
	 */
	public final void setEndLocation(final long endLocation) {
		this.endLocation = endLocation;
	}

	
	/**
	 * Get chromosomal start location of interval.
	 * @return Chromosomal start location of interval.
	 */
	public final long getStartLocation() {
		return startLocation;
	}

	
	/**
	 * Set chromosomal start location of interval.
	 * @param startLocation Chromosomal start location of interval.
	 */
	public final void setStartLocation(final long startLocation) {
		this.startLocation = startLocation;
	}
	
	
	// ============================
	//     Constructors
	// ============================
	
	/**
	 * Constructor.
	 */
	public GenomeInterval() {
		
	}


	/**
	 * Constructor.
	 * @param chromosome Chromosome number
	 * @param startLocation Chromosomal start location of interval
	 * in base pairs
	 * @param endLocation Chromosomal end location of interval
	 * in base pairs
	 */
	public GenomeInterval(final short chromosome, final long startLocation,
			final long endLocation) {
		super();
		this.chromosome = chromosome;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
}
