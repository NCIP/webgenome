/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-05-19 20:11:02 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.domain.Principal;

/**
 * Data access interface for <code>Principal</code>.
 * @author dhall
 *
 */
public interface PrincipalDao {
	
	/**
	 * Persist given principal.
	 * @param principal Principal to persist.
	 */
	void save(Principal principal);
	
	/**
	 * Updated persisted data on given principal.
	 * @param principal Principal whose persistent
	 * data should be updated.
	 */
	void update(Principal principal);

	/**
	 * Load principal with given name.
	 * @param email Email of principal.
	 * @return Principal with given name, or null
	 * if no such principal exists.
	 */
	Principal load(String email);
	
	
	/**
	 * Load principal with given name and password.
	 * @param email Email of principal.
	 * @param password Principal's password.
	 * @param domain Authentication domain
	 * @return Principal with given name and password
	 * or null if no such principal exists.
	 */
	Principal load(String email, String password, String domain);
	
	
	/**
	 * Delete data from given principal from persistent
	 * storage.
	 * @param principal Principal whose information
	 * should be removed from persistent storage.
	 */
	void delete(Principal principal);
}
