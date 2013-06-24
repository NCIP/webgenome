/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.5 $
$Date: 2008-06-13 19:58:40 $


*/

package org.rti.webgenome.service.session;

import org.rti.webgenome.domain.Principal;

/**
 * Concrete implementation of <code>SecurityMgr</code>
 * using dependency injection.
 * @author dhall
 *
 */
public final class SecurityMgrImpl implements SecurityMgr {
	
	/** Name of webGenome credential provider domain. */
	private static final String WEBGENOME_DOMAIN = "webgenome";

	//
	//  A T T R I B U T E S
	//
	
	/** Facade for transactional database operations. */
	private WebGenomeDbService dbService = null;
	
	//
	//  I N J E C T O R S
	//
	
	/**
	 * Inject facade for transactional database operations.
	 * @param dbService Facade for database operations
	 */
	public void setDbService(final WebGenomeDbService dbService) {
		this.dbService = dbService;
	}


	// ================================
	//    Business methods
	// ================================
	
	


	/**
	 * Create a new user account with given user name
	 * and password.
	 * @param email Email
	 * @param password Password
	 * @return Principal object
	 * @throws AccountAlreadyExistsException if an account
	 * with the name given by principal already exists.
	 */
	public Principal newAccount(final String email, final String password)
		throws AccountAlreadyExistsException {
		if (isEmpty ( email) || isEmpty ( password ) ) {
			throw new IllegalArgumentException(
					"Email address and password must not be empty" );
		}
		if (this.accountExists(email)) {
			throw new AccountAlreadyExistsException( "An account with email'" + email + "' already exists");
		}
		Principal p = new Principal(email , password, WEBGENOME_DOMAIN);
		this.dbService.savePrincipal(p);
		return p;
	}
	
	/**
	 * Create a new user account with given Principal.
	 * 
	 * @param name User name
	 * @param password Password
	 * @return Principal object
	 * @throws AccountAlreadyExistsException if an account
	 * with the name given by principal already exists.
	 */
	public Principal newAccount(Principal p)
		throws AccountAlreadyExistsException {
		
		if ( isEmpty ( p.getPassword() ) || isEmpty ( p.getEmail () ) ) {
			throw new IllegalArgumentException(
					"Email and password must not be empty");
		}
		if (this.accountExists(p.getEmail())) {
			throw new AccountAlreadyExistsException("An account with the email address '"
					+ p.getEmail() + "' already exists");
		}
		
		p.setDomain(WEBGENOME_DOMAIN);
		this.dbService.savePrincipal(p);
		return p;
	}
	
	
	/**
	 * Determines if a user account associated with the
	 * given name exists.
	 * @param email Email
	 * @return T/F
	 */
	public boolean accountExists(final String email) {
		if ( isEmpty ( email ) ) {
			throw new IllegalArgumentException(
					"Email addresss cannot be empty");
		}
		return this.dbService.loadPrincipal(email) != null;
	}
	
	
	/**
	 * Update, which is used primarily for changing password.
	 * @param principal Principal whose persistent information
	 * should be updated.
	 */
	public void update(final Principal principal) {
		this.dbService.updatePrincipal(principal);
	}
	
	
	/**
	 * Delete persistently stored information associated
	 * with given principal.
	 * @param principal Principal whose persistent information
	 * should be deleted.
	 */
	public void delete(final Principal principal) {
		this.dbService.deletePrincipal(principal);
	}
	
	
	/**
	 * Login.
	 * @param name User name.
	 * @param password Password.
	 * @return Principal object or null if no principal
	 * exists with given user name and password.
	 */
	public Principal login( final String email,
							final String password) {
		return this.dbService.loadPrincipal(email, password, WEBGENOME_DOMAIN);
	}
	
	/**
	 * Get Principal by email address.
	 * 
	 * @param email 
	 * 	 
	 * @return Principal object or null if no principal
	 * exists with given e-mail address.
	 */
	public Principal getPrincipal(final String email) {
		if (email == null || email.length() < 1) {
			throw new IllegalArgumentException(
					"Account email cannot be empty");
		}
		return this.dbService.loadPrincipal(email);
	}
	
	
	/**
	 * Convenience method to check whether a value is "missing".
	 * @param value
	 * @return true, if value is empty, false otherwise.
	 */
	private boolean isEmpty ( String value ) {
		return value == null || value.length() < 1 ;
	}
	
}
