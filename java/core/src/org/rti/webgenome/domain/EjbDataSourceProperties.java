/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $

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

package org.rti.webgenome.domain;

/**
 * Properties of a data source that is an EJB.
 * @author dhall
 *
 */
public class EjbDataSourceProperties
extends DataSourceProperties.BaseDataSourceProperties {
	
	/** Name of JNDI service. */
	private final String jndiName;
	
	/** JNDI provider URL. */
	private final String jndiProviderURL;
	
	/**
	 * Get JNDI service name.
	 * @return JNDI service name
	 */
	public final String getJndiName() {
		return jndiName;
	}

	/**
	 * Get JNDI provider URL.
	 * @return JNDI provider URL.
	 */
	public final String getJndiProviderURL() {
		return jndiProviderURL;
	}

	/**
	 * Constructor.
	 * @param jndiName JNDI service name
	 * @param jndiProviderURL JNDI provider URL
	 * @param clientId ID of application client
	 */
	public EjbDataSourceProperties(final String jndiName,
			final String jndiProviderURL, final String clientId) {
		super(clientId);
		if (jndiName == null || jndiProviderURL == null
				|| jndiName.length() < 1 || jndiProviderURL.length() < 1) {
			throw new IllegalArgumentException(
					"Both JNDI name and provider URL must be specified");
		}
		this.jndiName = jndiName;
		this.jndiProviderURL = jndiProviderURL;
	}

	/**
	 * Equals method.
	 * @param obj An object.
	 * @return T/F
	 */
	@Override
	public final boolean equals(final Object obj) {
		boolean eq = false;
		if (obj instanceof EjbDataSourceProperties) {
			EjbDataSourceProperties props = (EjbDataSourceProperties) obj;
			eq = this.jndiName.equals(props.jndiName)
			&& this.jndiProviderURL.equals(props.jndiProviderURL)
			&& this.getClientId().equals(props.getClientId());
		}
		return eq;
	}

	/**
	 * Get hash code.
	 * @return Hash code
	 */
	@Override
	public final int hashCode() {
		return (this.jndiName + this.jndiProviderURL
				+ this.getClientId()).hashCode();
	}
}
