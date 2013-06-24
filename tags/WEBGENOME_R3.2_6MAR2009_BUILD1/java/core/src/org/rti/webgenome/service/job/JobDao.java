/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-27 12:53:56 $


*/

package org.rti.webgenome.service.job;

import java.util.Collection;

/**
 * This interface defines methods for dealing
 * with persistence of {@link Job} objects.
 * @author dhall
 *
 */
public interface JobDao {
	
	/**
	 * Persist given job if not persisted or
	 * updates if it already is.
	 * @param job Job to persist or update
	 */
	void saveOrUpdate(Job job);

	/**
	 * Load all jobs.
	 * @return All jobs
	 */
	Collection<Job> loadAll();
	
	/**
	 * Delete given job for persistence.
	 * @param job Job to delete
	 */
	void delete(Job job);
}
