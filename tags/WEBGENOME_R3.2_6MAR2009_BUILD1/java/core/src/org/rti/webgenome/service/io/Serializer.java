/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:28 $


*/

package org.rti.webgenome.service.io;

import java.io.Serializable;

/**
 * Interface for serializing objects.
 *
 */
public interface Serializer {
	
	/**
	 * Serialize given serializable object and return a file
	 * name that can be used to de-serialize object at a later time.
	 * @param serializable A serializable object
	 * @return File name, but not absolute path.
	 */
	String serialize(Serializable serializable);
	
	
	/**
	 * De-serialize object from given file.
	 * @param fileName File name, but not absolute path
	 * @return A serializable object
	 */
	Serializable deSerialize(String fileName);
	
	
	/**
	 * Decommission all objects managed by serializer.
	 * After object has been decommissioned, it can
	 * no longer be de-serialized.
	 */
	void decommissionAllObjects();
	
	
	/**
	 * Decommission object that is serialized in given file.
	 * After object has been decommissioned, it can
	 * no longer be de-serialized.
	 * @param fileName Name of file (not absolute path)
	 * containing serialized object.
	 */
	void decommissionObject(String fileName);

}
