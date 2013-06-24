/*
$Revision: 1.1 $
$Date: 2007-11-28 19:51:20 $


*/

package org.rti.webgenome.service.session;

import java.util.Map;

import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.util.StringUtils;

/**
 * This is an implementation of {@link Authenticator}
 * where the username/password pairs are passed in
 * as constructor arguments.
 * @author dhall
 *
 */
public class InMemoryAuthenticator implements Authenticator {
	
	
	/** Map of username (keys) / password (values) pairs. */
	private final Map<String, String> credentials;
	
	/** Domain name for this security context. */
	private final String domain;
	
	/**
	 * Constructor.
	 * @param credentials Map of username (keys) /
	 * password (values) pairs.
	 * @param domain A domain name for this security context
	 */
	public InMemoryAuthenticator(final Map<String, String> credentials,
			final String domain) {
		this.credentials = credentials;
		this.domain = domain;
	}

	//
	//  I N T E R F A C E : Authenticator
	//
	
	/**
	 * {@inheritDoc}
	 */
	public Principal login(final String email, final String password) {
		Principal principal = null;
		if (!StringUtils.isEmpty(email)) {
			String realPassword = this.credentials.get(email);
			if (realPassword != null) {
				if (StringUtils.equal(password, realPassword)) {
					principal = new Principal(email, password, this.domain);
				}
			}
		}
		return principal;
	}
}
