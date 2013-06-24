/*
$Revision: 1.1 $
$Date: 2007-07-06 14:41:41 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.domain.BioAssay;

/**
 * Data access class for
 * {@link org.rti.webgenome.domain.BioAssay}.
 * @author dhall
 *
 */
public interface BioAssayDao {
	
	/**
	 * Save given bioassay to persistent storage.
	 * @param bioAssay Bioassay to save.
	 */
	void save(BioAssay bioAssay);

	
	/**
	 * Delete given bioassay from persistent storage.
	 * @param bioAssay Bioassay to delete.
	 */
	void delete(BioAssay bioAssay);
}
