/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-07-16 16:25:14 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.graphics.util.ColorChooser;

/**
 * Data access object for
 * {@link org.rti.webgenome.graphics.util.ColorChooser}.
 * @author dhall
 *
 */
public interface ColorChooserDao {

	/**
	 * Save or update given color chooser to persistent storage.
	 * @param colorChooser Color chooser to save or update.
	 */
	void saveOrUpdate(ColorChooser colorChooser);
	
	/**
	 * Delete given color chooser form persistent storage.
	 * @param colorChooser Color chooser to delete.
	 */
	void delete(ColorChooser colorChooser);
}
