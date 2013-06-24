/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.service.util;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataContainingBioAssay;

/**
 * Implementation of <code>ChromosomeArrayDataGetter</code>
 * for in-memory data.
 * @author dhall
 *
 */
public final class InMemoryChromosomeArrayDataGetter
	implements ChromosomeArrayDataGetter {

	/**
	 * Get chromosome array data from given bioassay
	 * and chromosome.
	 * @param bioAssay A bioassay
	 * @param chromosome A chromosome
	 * @return Chromosome array data
	 */
	public ChromosomeArrayData getChromosomeArrayData(
			final BioAssay bioAssay,
			final short chromosome) {
		if (!(bioAssay instanceof DataContainingBioAssay)) {
			throw new IllegalArgumentException(
					"Bioassay must be of type DataContainingBioAssay");
		}
		return ((DataContainingBioAssay) bioAssay).getChromosomeArrayData(
				chromosome);
	}
}
