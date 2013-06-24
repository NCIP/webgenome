/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-08-17 20:05:05 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.domain.Plot;

/**
 * Data access class for
 * {@link org.rti.webgenome.domain.Plot}.
 * @author dhall
 *
 */
public interface PlotDao {

	/**
	 * Save given plot to persistent storage.
	 * @param plot Plot to persist.
	 */
	void save(Plot plot);
	
	/**
	 * Delete given plot from persistent storage.
	 * @param plot Plot to delete.
	 */
	void delete(Plot plot);
	
	/**
	 * Determines if a plot with the given ID is
	 * referenced by another persitent object.
	 * @param plotId Primary key ID of plot
	 * @return T/F
	 */
	boolean isReferenced(Long plotId);
}
