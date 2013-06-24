/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.domain.Array;

/**
 * Data access object for persisting {@link org.rti.webgenome.domain.Array}
 * objects.
 * @author dhall
 *
 */
public interface ArrayDao {

	/**
	 * Persist or update given array.
	 * @param array An array
	 */
	void saveOrUpdate(Array array);
	
	/**
	 * Delete given array from persistent storage.
	 * @param array An array
	 */
	void delete(Array array);
	
	/**
	 * Determines if the given array object is referenced by
	 * any other persistent objects.
	 * @param array An array
	 * @return T/F
	 */
	boolean isReferenced(Array array);
}
