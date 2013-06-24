/*
$Revision: 1.1 $
$Date: 2007-07-03 17:44:00 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.graphics.event.MouseOverStripes;

/**
 * Data access class for
 * {@link org.rti.webgenome.graphics.event.MouseOverStripes}.
 * @author dhall
 *
 */
public interface MouseOverStripesDao {
	
	/**
	 * Save given mouseover stripes to persistent
	 * storage.
	 * @param stripes Mouseover stripes to persist.
	 */
	void save(MouseOverStripes stripes);
	
	/**
	 * Delete given mouseover stripes from persistent
	 * storage.
	 * @param stripes Stripes to delete.
	 */
	void delte(MouseOverStripes stripes);

}
