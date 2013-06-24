/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-29 21:47:51 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.graphics.io.ClickBoxes;

/**
 * Data access class for
 * {@link org.rti.webgenome.graphics.io.ClickBoxes}.
 * @author dhall
 *
 */
public interface ClickBoxesDao {

	/**
	 * Save given click boxes to persistent storage.
	 * @param clickBoxes Click boxes to save
	 */
	void save(ClickBoxes clickBoxes);
	
	/**
	 * Delete given click boxes from persistent storage.
	 * @param clickBoxes Click boxes to delete
	 */
	void delete(ClickBoxes clickBoxes);
	
	/**
	 * Load click boxes with given ID from persistent storage.
	 * @param id Id of click boxes instance
	 * @return Click boxes with given ID
	 */
	ClickBoxes load(Long id);
}
