/*
$Revision: 1.1 $
$Date: 2007-10-10 17:47:02 $


*/

package org.rti.webgenome.service.session;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Principal;

/**
 * This class is an authenticator that checks against multiple
 * credential providers.  The user credentials will be authenticated
 * if at least one of the configured credential providers
 * authenticates.
 * @author dhall
 *
 */
public class MultiCredentialProviderAuthenticator implements Authenticator {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(
			MultiCredentialProviderAuthenticator.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/** Credential providers to check against. */
	private Set<Authenticator> authenticators =
		new HashSet<Authenticator>();
	
	//
	//  C O N S T R U C T O R S
	//
	
	
	/**
	 * Constructor.
	 * @param authenticators Credential providers to check against
	 */
	public MultiCredentialProviderAuthenticator(
			final Set<Authenticator> authenticators) {
		super();
		this.authenticators = authenticators;
	}
	
	//
	//  I N T E R F A C E : Authenticator
	//

	/**
	 * {@inheritDoc}
	 * @see org.rti.webgenome.service.session.Authenticator#login(
	 * java.lang.String, java.lang.String)
	 */
	public Principal login(final String email, final String password) {
		Principal principal = null;
		for (Authenticator auth : this.authenticators) {
			try {
				principal = auth.login(email, password);
			} catch (Exception e) {
				LOGGER.error(
						"A credential provider is out of commission", e);
			}
			if (principal != null) {
				break;
			}
		}
		return principal;
	}
}
