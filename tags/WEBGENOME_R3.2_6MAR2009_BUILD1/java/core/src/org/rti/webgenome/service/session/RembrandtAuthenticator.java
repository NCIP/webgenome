/*
$Revision: 1.2 $
$Date: 2007-10-10 17:47:02 $

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
