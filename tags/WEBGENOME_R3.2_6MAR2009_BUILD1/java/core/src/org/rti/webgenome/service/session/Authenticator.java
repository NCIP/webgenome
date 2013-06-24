/*
$Revision: 1.2 $
$Date: 2007-10-10 15:08:41 $


*/

package org.rti.webgenome.service.session;

import org.rti.webgenome.domain.Principal;

/**
 * Interface that authenticates users against some
 * credential repository.
 * @author dhall
 *
 */
public interface Authenticator {

	/**
	 * Log the user in.
	 * @param email Email
	 * @param password Password
	 * @return A principal object if the given credentials
	 * authenticate, or {@code null} otherwise.
	 */
	Principal login(String email, String password);
}
