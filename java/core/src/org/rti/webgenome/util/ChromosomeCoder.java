/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $


*/

package org.rti.webgenome.util;

import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.core.DataFormatException;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Organism;

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
			throw new WebGenomeSystemException(
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
