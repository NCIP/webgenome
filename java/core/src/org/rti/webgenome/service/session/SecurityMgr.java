/*
$Revision: 1.4 $
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
 * Manages user account related functions for the WebGenome
 * credential database only.
 * @author dhall
 *
 */
public interface SecurityMgr extends Authenticator {

	/**
	 * Create a new user account with given user name
	 * and password.
	 * @param name User name
	 * @param password Password
	 * @return Principal object
	 * @throws AccountAlreadyExistsException if an account
	 * with the name given by principal already exists.
	 */
	Principal newAccount(String name, String password)
		throws AccountAlreadyExistsException;
	
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
		throws AccountAlreadyExistsException;
		
	/**
	 * Determines if a user account associated with the
	 * given name exists.
	 * @param name User name
	 * @return T/F
	 */
	boolean accountExists(String name);
	
	/**
	 * Determines if a user account associated with the
	 * given email exists.
	 * @param name User name
	 * @return T/F
	 */
	boolean accountByEmailExists(final String email);
	
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
	 * @param name User name.
	 * @param password Password.
	 * @return Principal object or null if no principal
	 * exists with given user name and password.
	 */
	Principal login(String name, String password);
	
	/**
	 * Get Principal by email address.
	 * 
	 * @param email 
	 * 	 
	 * @return Principal object or null if no principal
	 * exists with given e-mail address.
	 */
	Principal getPrincipalByEmail(final String email);
}
