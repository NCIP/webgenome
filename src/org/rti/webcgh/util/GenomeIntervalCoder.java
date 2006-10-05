/*
$Revision: 1.1 $
$Date: 2006-10-05 22:09:05 $

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

package org.rti.webcgh.util;


/**
 * <p>
 * Codes and decodes genome intervals into the following format:
 * </p>
 * 
 * <p>
 * CHROM_NUM[:START_LOC-END_LOC] where CHROM_NUMBER is a
 * chromosome number, START_LOC is a starting location
 * on the chromosome, and END_LOC is an ending location
 * on the chromosome.  The items between '[]' are optional,
 * but either all be present or absent.
 * </p>
 * 
 * <p>
 * Example valid intervals:
 * </p>
 * 
 * <p>
 * 1:1000-2000<br>
 * 1:
 * 1
 * </p>
 * 
 * <p>
 * Example invalid intervals:
 * </p>
 * 
 * <p>
 * 1:1000
 * 1:1000-
 * 1:-2000
 * 1000-2000
 * </p>
 * 
 * @author dhall
 *
 */
public final class GenomeIntervalCoder {
	
	/**
	 * Constructor.
	 */
	private GenomeIntervalCoder() {
		
	}
	
	
	/**
	 * Parse chromosome number.
	 * @param encoding Genome interval encoding.
	 * @return Chromosome number of -1 if the
	 * chromosome number cannot be extracted.
	 * @throws GenomeIntervalFormatException if
	 * chromosome number is not numeric.
	 */
	public static short parseChromosome(final String encoding)
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
	public static long parseStart(final String encoding)
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
	public static long parseEnd(final String encoding)
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
