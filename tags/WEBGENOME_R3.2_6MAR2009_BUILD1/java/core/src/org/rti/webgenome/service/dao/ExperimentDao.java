/*
$Revision: 1.3 $
$Date: 2007-09-08 17:17:10 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.domain.Experiment;

/**
 * Data access class for
 * {@link org.rti.webgenome.domain.Experiment}.
 * @author dhall
 *
 */
public interface ExperimentDao {

	/**
	 * Save given experiment to persistent storage.
	 * @param experiment Experiment to save
	 */
	void save(Experiment experiment);
	
	/**
	 * Update given experiment in persistent storage.
	 * @param experiment Experiment to update
	 */
	void update(Experiment experiment);
	
	/**
	 * Load experiment with given ID.
	 * @param id Experiment ID
	 * @return An experiment with given ID or
	 * {@code null} if there is no experiment with given
	 * ID.
	 */
	Experiment load(Long id);
	
	/**
	 * Delete given experiment from persistent storage.
	 * @param experiment Experiment to delete.
	 */
	void delete(Experiment experiment);
	
	/**
	 * Method determines if there are references
	 * to the given experiment object.
	 * @param experimentId An experiment identifier
	 * @return T/F
	 */
	boolean isReferenced(Long experimentId);
}
