/*
$Revision: 1.1 $
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
	public Principal login(final String userName, final String password) {
		Principal principal = null;
		for (Authenticator auth : this.authenticators) {
			try {
				principal = auth.login(userName, password);
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
