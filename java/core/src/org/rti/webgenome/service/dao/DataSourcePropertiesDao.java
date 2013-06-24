/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-27 17:51:51 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.domain.DataSourceProperties;

/**
 * Data access class for
 * {@link org.rti.webgenome.domain.DataSourceProperties}
 * and implementing classes.
 * @author dhall
 *
 */
public interface DataSourcePropertiesDao {

	/**
	 * Save given properties to persistent storage.
	 * @param props Properties to persist.
	 */
	void save(DataSourceProperties props);
	
	/**
	 * Delete given properties from persistent storage.
	 * @param props Properties to delete.
	 */
	void delete(DataSourceProperties props);
}
