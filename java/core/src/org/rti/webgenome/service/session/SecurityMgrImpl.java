/*
$Revision: 1.5 $
$Date: 2008-06-13 19:58:40 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
	 * @param name User name
	 * @param password Password
	 * @return Principal object
	 * @throws AccountAlreadyExistsException if an account
	 * with the name given by principal already exists.
	 */
	public Principal newAccount(final String name, final String password)
		throws AccountAlreadyExistsException {
		if (name == null || name.length() < 1
				|| password == null || password.length() < 1) {
			throw new IllegalArgumentException(
					"Both name and password must be non-null");
		}
		if (this.accountExists(name)) {
			throw new AccountAlreadyExistsException("An account with name '"
					+ name + "' already exists");
		}
		Principal p = new Principal(name, password, WEBGENOME_DOMAIN);
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
		
		if (p.getName() == null || p.getName().length() < 1 
				|| p.getPassword() == null || p.getPassword().length() < 1
				|| p.getEmail() == null || p.getEmail().length() < 1) {
			throw new IllegalArgumentException(
					"Email, name and password must be non-null");
		}
		if (this.accountExists(p.getName())) {
			throw new AccountAlreadyExistsException("An account with name '"
					+ p.getName() + "' already exists");
		}
		
		p.setDomain(WEBGENOME_DOMAIN);
		this.dbService.savePrincipal(p);
		return p;
	}
	
	
	/**
	 * Determines if a user account associated with the
	 * given name exists.
	 * @param name User name
	 * @return T/F
	 */
	public boolean accountExists(final String name) {
		if (name == null || name.length() < 1) {
			throw new IllegalArgumentException(
					"Account name cannot be empty");
		}
		return this.dbService.loadPrincipal(name) != null;
	}
	
	/**
	 * Determines if a user account associated with the
	 * given email exists.
	 * @param name User name
	 * @return T/F
	 */
	public boolean accountByEmailExists(final String email) {
		if (email == null || email.length() < 1) {
			throw new IllegalArgumentException(
					"Account email cannot be empty");
		}
		return this.dbService.loadPrincipalByEmail(email) != null;
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
	 * @param principal Principal whose persistent infromation
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
	public Principal login(final String name,
			final String password) {
		return this.dbService.loadPrincipal(name, password,
				WEBGENOME_DOMAIN);
	}
	
	/**
	 * Get Principal by email address.
	 * 
	 * @param email 
	 * 	 
	 * @return Principal object or null if no principal
	 * exists with given e-mail address.
	 */
	public Principal getPrincipalByEmail(final String email) {
		if (email == null || email.length() < 1) {
			throw new IllegalArgumentException(
					"Account email cannot be empty");
		}
		return this.dbService.loadPrincipalByEmail(email);
	}
	
}
