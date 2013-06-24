/*
$Revision: 1.4 $
$Date: 2008-06-13 19:58:40 $


*/

package org.rti.webgenome.service.session;

import org.rti.webgenome.domain.Principal;

/**
 * Manages user account related functions for the WebGenome
 * credential database only.
 * @author dhall
 *
 */
public interface SecurityMgr extends Authenticator {

	/**
	 * Create a new user account with given user name
	 * and password.
	 * @param email Email
	 * @param password Password
	 * @return Principal object
	 * @throws AccountAlreadyExistsException if an account
	 * with the name given by principal already exists.
	 */
	Principal newAccount(String email, String password) 
		throws AccountAlreadyExistsException;
	
	/**
	 * Create a new user account with given Principal.
	 * 
	 * @param Principal
	 * @return Principal object
	 * @throws AccountAlreadyExistsException if an account
	 * with the name given by principal already exists.
	 */
	public Principal newAccount(Principal p)
		throws AccountAlreadyExistsException;
		
	/**
	 * Determines if a user account associated with the
	 * given email exists.
	 * @param email Email
	 * @return true, if account with this email exists, false otherwise
	 */
	boolean accountExists(String email);
	
	/**
	 * Update, which is used primarily for changing password.
	 * @param principal Principal whose persistent information
	 * should be updated.
	 */
	void update(Principal principal);
	
	/**
	 * Delete persistently stored information associated
	 * with given principal.
	 * @param principal Principal whose persistent information
	 * should be deleted.
	 */
	void delete(Principal principal);
	
	/**
	 * Login.
	 * @param email Email
	 * @param password Password.
	 * @return Principal object or null if no principal
	 * exists with given user name and password.
	 */
	Principal login(String email, String password);
	
		
	/**
	 * Get Principal by email address.
	 * 
	 * @param email 
	 * 	 
	 * @return Principal object or null if no principal
	 * exists with given e-mail address.
	 */
	Principal getPrincipal(final String email);
}
