/*
$Revision: 1.1 $
$Date: 2007-06-27 15:47:15 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.analysis.UserConfigurableProperty;

/**
 * Data access class for
 * {@link org.rti.webgenome.analysis.UserConfigurableProperty}
 * and implementing classes.
 * @author dhall
 *
 */
public interface UserConfigurablePropertyDao {

	/**
	 * Save given property.
	 * @param prop Property to save
	 */
	void save(UserConfigurableProperty prop);
	
	/**
	 * Delete given property.
	 * @param prop Property to delete.
	 */
	void delete(UserConfigurableProperty prop);
}
