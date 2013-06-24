/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-02-05 23:28:35 $


*/

package org.rti.webgenome.domain;


/**
 * Handles 2-way translation between chromosome numbers
 * and String equivalents.
 * @author dhall
 *
 */
public final class ChromosomeFormatter {
	
	/**
	 * Constructor.
	 *
	 */
	private ChromosomeFormatter() {
		
	}
	
	/**
	 * Convert text-based representation of chromosome into
	 * numeric.
	 * @param chromosomeText Text-based representation of
	 * chromosome.  This may be a text-based number, X, or Y.
	 * @return Chromosome number.
	 * @throws BadChromosomeFormat if the format is not numeric,
	 * "X" or "Y" (case insensitive).
	 */
	public static short chromosomeNumber(final String chromosomeText)
	throws BadChromosomeFormat {
		short num = (short) -1;
		if ("X".equalsIgnoreCase(chromosomeText)) {
			num = 23;
		} else if ("Y".equalsIgnoreCase(chromosomeText)) {
			num = 24;
		} else {
			try {
				num = Short.parseShort(chromosomeText);
			} catch (NumberFormatException e) {
				throw new BadChromosomeFormat("Unknown chromosome: "
						+ chromosomeText);
			}
		}
		return num;
	}
}
