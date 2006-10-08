/*
$Revision: 1.3 $
$Date: 2006-10-08 01:11:28 $

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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * Represents a genome interval.
 * @author dhall
 *
 */
public class GenomeInterval {
	
	// ===========================
	//      Constants
	// ===========================
	
	/** Delimiter character between encoded genome intervals. */
	private static final String DELIMITER = ",";

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
	
	
	// =================================
	//     Business methods
	// =================================
	
	/**
	 * Decode genome intervals encoded like '1:1000-2000;2:50-150'.
	 * This string encodes two genome intervals.  The first
	 * is chromosome 1 from position 1000 to 2000.  The positions
	 * units may be base pairs or a larger multiple of base pairs.
	 * @param encoding Encoded genome interval
	 * @return Genome intervals
	 * @throws GenomeIntervalFormatException if intervals are
	 * not well-formed.
	 */
	public static final List<GenomeInterval> decode(final String encoding)
	throws GenomeIntervalFormatException {
		if (encoding == null) {
			throw new IllegalArgumentException("Genome intervals are null");
		}
		GenomeIntervalCoder coder = new GenomeIntervalCoder();
		List<GenomeInterval> intervals = new ArrayList<GenomeInterval>();
		StringTokenizer tok = new StringTokenizer(encoding, DELIMITER);
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			GenomeInterval interval = new GenomeInterval(
					coder.parseChromosome(token), coder.parseStart(token), 
					coder.parseEnd(token));
			intervals.add(interval);
		}
		return intervals;
	}
	
	
	/**
	 * Get set of chromosomes from given collection.
	 * @param intervals Genome intervals
	 * @return Chromosomes
	 */
	public static final Set<Short> getChromosomes(
			final Collection<GenomeInterval> intervals) {
		if (intervals == null) {
			throw new IllegalArgumentException("Intervals null");
		}
		Set<Short> chromosomes = new HashSet<Short>();
		for (GenomeInterval gi : intervals) {
			chromosomes.add(gi.getChromosome());
		}
		return chromosomes;
	}
	
	
	// ==============================
	//      Helper classes
	// ==============================
	
	/**
	 * Parses fields out of individual genome interval
	 * encodings like '1:100-200'.
	 */
	static final class GenomeIntervalCoder {
		
		/**
		 * Parse chromosome number.
		 * @param encoding Genome interval encoding.
		 * @return Chromosome number of -1 if the
		 * chromosome number cannot be extracted.
		 * @throws GenomeIntervalFormatException if
		 * chromosome number is not numeric.
		 */
		short parseChromosome(final String encoding)
		throws GenomeIntervalFormatException {
			short chrom = (short) -1;
			int p = 0;
			int q = encoding.indexOf(":");
			if (q < 0) {
				q = encoding.length();
			}
			if (p < q) {
				String chromStr = encoding.substring(p, q).trim();
				try {
					chrom = Short.parseShort(chromStr);
				} catch (NumberFormatException e) {
					throw new GenomeIntervalFormatException(
							"Invalid chromosome :" + chromStr);
				}
			}
			return chrom;
		}
		
		/**
		 * Parse start location from given encoded genome interval.
		 * @param encoding Encoded genome interval.
		 * @return Start location or -1 if start location missing
		 * but format valid.
		 * @throws GenomeIntervalFormatException if format is
		 * invalid.
		 */
		long parseStart(final String encoding)
		throws GenomeIntervalFormatException {
			long start = -1;
			int p = encoding.indexOf(":");
			int q = encoding.indexOf("-");
			if (p >= 0 && q < 0) {
			    throw new GenomeIntervalFormatException(
			    		"Bad genome interval format");
			}
			if (p >= 0 && q >= 0 && p + 1 < q) {
				String startStr = encoding.substring(p + 1, q).trim();
				try {
					start = Long.parseLong(startStr);
				} catch (NumberFormatException e) {
					throw new GenomeIntervalFormatException(
							"Start position is not numeric: + startStr");
				}
			}
			return start;
		}
		
		
		/**
		 * Parse end position from given genome interval encoding.
		 * @param encoding Genome interval encoding.
		 * @return End position or -1 if end position missing
		 * but interval is well-formed.
		 * @throws GenomeIntervalFormatException if format is
		 * invalid.
		 */
		long parseEnd(final String encoding)
		throws GenomeIntervalFormatException {
			long end = -2;
			int p = encoding.indexOf("-");
			int q = encoding.length();
			if (p >= 0 && p + 1 < q) {
				String endStr = encoding.substring(p + 1, q).trim();
				try {
					end = Long.parseLong(endStr);
				} catch (NumberFormatException e) {
					throw new GenomeIntervalFormatException(
							"End position not numeric: " + endStr);
				}
			}	
			return end;
		}

	}
}
