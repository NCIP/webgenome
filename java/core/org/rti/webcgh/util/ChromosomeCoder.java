/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:07 $

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

import java.util.HashMap;
import java.util.Map;

import org.rti.webcgh.core.DataFormatException;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.Organism;

/**
 * This class is responsible for encoding and decoding
 * chromosome numbers according to various formats.
 * @author dhall
 *
 */
public final class ChromosomeCoder {
	
	//
	//     STATICS
	//
	
	/** Prefix of UCSC encoded chromosome numbers. */
	private static final String UCSC_CHROMOSOME_PREFIX = "chr";
	
	/** Human organism. */
	private static final Organism HUMAN = new Organism("Homo", "sapiens");
	
	/**
	 * Map of maps that shows how to map chromosome name
	 * aliases (e.g. 'X') to chromosome numbers.  Keys
	 * of the first dimension are organisms.  The second
	 * dimension maps aliases to chromosome numbers for the
	 * corresponding organism.
	 */
	private static final Map<Organism, Map<String, Short>>
	CHROM_ALIASES =
		new HashMap<Organism, Map<String, Short>>();
	
	// TODO: Read this in from properties file
	static {
		
		// Human aliases
		Map<String, Short> humanAliases = new HashMap<String, Short>();
		humanAliases.put("X", (short) 23);
		humanAliases.put("x", (short) 23);
		humanAliases.put("Y", (short) 24);
		humanAliases.put("y", (short) 24);
		CHROM_ALIASES.put(HUMAN, humanAliases);
	}
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 */
	private ChromosomeCoder() {
		
	}
	
	
	//
	//     BUSINESS METHODS
	//

	/**
	 * Decode chromosome number from UCSC encoding.  This
	 * coding system uses a prefix of 'chr' as well
	 * as 'X' and 'Y' to represent sex chromosomes.
	 * @param encodedChrom Encoded chromosome number
	 * @param organism Organism
	 * @return Chromosome number
	 * @throws DataFormatException if chromosome number
	 * encoding format is unparseable
	 */
	public static short decodeUcscFormat(
			final String encodedChrom, final Organism organism)
	throws DataFormatException {
		if (encodedChrom.indexOf(UCSC_CHROMOSOME_PREFIX) != 0) {
			throw new DataFormatException("Missing prefix '"
					+ UCSC_CHROMOSOME_PREFIX + "'");
		}
		String base = encodedChrom.substring(
				UCSC_CHROMOSOME_PREFIX.length());
		return decodeChromosomeNumber(base, organism);
	}
	
	
	/**
	 * Decode chromosome number from given encoding.  The encoding
	 * may be a numeric string value (e.g. "10") or a chromosome
	 * alias (e.g. "X").
	 * @param encoding Chromosome encoding
	 * @param organism Organism
	 * @return Chromosome number
	 * @throws DataFormatException if a chromosome number cannot
	 * be extracted from given encoding
	 */
	public static short decodeChromosomeNumber(
			final String encoding, final Organism organism)
	throws DataFormatException {
		short chrom = 0;
		
		// Get aliases map for organism
		Map<String, Short> aliases = CHROM_ALIASES.get(organism);
		if (aliases == null) {
			throw new WebcghSystemException(
					"Do not have chromosome aliases for "
					+ organism.getGenus() + " "
					+ organism.getSpecies());
		}
		
		// See if there is an alias for given chromosome encoding
		Short chromObj = aliases.get(encoding);
		if (chromObj != null) {
			chrom = chromObj.shortValue();
		
		// If not, try to parse number from encoding
		} else {
			try {
				chrom = Short.parseShort(encoding);
			} catch (NumberFormatException e) {
				throw new DataFormatException(
						"Unparseable chromosome number '"
						+ encoding + "'");
			}
		}
		
		return chrom;
	}
}
