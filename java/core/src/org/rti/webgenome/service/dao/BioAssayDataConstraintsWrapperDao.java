/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-07-09 22:29:43 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.domain.Experiment.BioAssayDataConstraintsWrapper;

/**
 * Data access class for
 * {@link org.rti.webgenome.domain.Experiment.BioAssayDataConstraintsWrapper}.
 * @author dhall
 *
 */
public interface BioAssayDataConstraintsWrapperDao {

	/**
	 * Save given object to persistent storage.
	 * @param wrapper Bioassay data constraints wrapper.
	 */
	void save(BioAssayDataConstraintsWrapper wrapper);
	
	/**
	 * Delete given object from persistent storage.
	 * @param wrapper Bioassay data constraints wrapper.
	 */
	void delete(BioAssayDataConstraintsWrapper wrapper);
}
