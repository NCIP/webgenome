/*
$Revision: 1.2 $
$Date: 2007-10-10 17:47:02 $


*/

package org.rti.webgenome.service.session;

import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.exceptions.CSInputException;
import gov.nih.nci.security.exceptions.CSLoginException;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Principal;

/**
 * Authenticates user credentials against the
 * Rembrandt application credential repository.
 * @author dhall
 *
 */
public class RembrandtAuthenticator implements Authenticator {
	
	//
	//  S T A T I C S
	//
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(
			RembrandtAuthenticator.class);
	
	
	//
	//  A T T R I B U T E S
	//
	
	/** Authentication manager for Rembrandt. */
	private final AuthenticationManager authenticationManager;
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 * @param authenticationManagerName Name of authentication manager
	 * for Rembrandt
	 */
	public RembrandtAuthenticator(final String authenticationManagerName) {
		try {
			this.authenticationManager =
				SecurityServiceProvider.getAuthenticationManager(
						authenticationManagerName);
			if (this.authenticationManager == null) {
				throw new WebGenomeSystemException(
						"Cannot get authenticator for Rembrandt with name: "
						+ authenticationManagerName);
			}
		} catch (Exception e) {
			throw new WebGenomeSystemException(
					"Error getting authentication manager for "
					+ "Rembrandt with name: "
					+ authenticationManagerName, e);
		}
	}
	
	//
	//  I N T E R F A C E : Authenticator

	/**
	 * {@inheritDoc}
	 */
	public Principal login(final String email, final String password) {
		Principal principal = null;
		try {
			if (this.authenticationManager.login(email, password)) {
				principal = new Principal( email, password,
										   this.authenticationManager.getApplicationContextName());
			}
		} catch (CSLoginException e) {
			LOGGER.warn("Invalid login attempt by '" + email + "'");
		} catch (CSInputException e) {
			LOGGER.warn("Invalid login attempt by '" + email + "'");
		} catch (Exception e) {
			throw new WebGenomeSystemException(
					"Error validating user credentials", e);
		}
		return principal;
	}
}
