/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-07-27 22:21:19 $


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
	private String jndiName;
	
	/** JNDI provider URL. */
	private String jndiProviderURL;
	
	/** Id of client application. */
	private String clientId;

	/**
	 * Get ID of application client.
	 * @return ID of application client
	 */
	public final String getClientId() {
		return clientId;
	}
	
	
	/**
	 * Set ID of application client.
	 * @param clientId ID of application client
	 */
	public final void setClientId(final String clientId) {
		this.clientId = clientId;
	}
	
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
	 * Set JNDI service name.
	 * @param jndiName JNDI service name
	 */
	public void setJndiName(final String jndiName) {
		this.jndiName = jndiName;
	}

	/**
	 * Set JNDI provider URL.
	 * @param jndiProviderURL JNDI provider URL
	 */
	public void setJndiProviderURL(final String jndiProviderURL) {
		this.jndiProviderURL = jndiProviderURL;
	}
	
	/**
	 * Constructor.
	 */
	public EjbDataSourceProperties() {
		
	}

	/**
	 * Constructor.
	 * @param jndiName JNDI service name
	 * @param jndiProviderURL JNDI provider URL
	 * @param clientId ID of application client
	 */
	public EjbDataSourceProperties(final String jndiName,
			final String jndiProviderURL, final String clientId) {
		if (jndiName == null || jndiProviderURL == null || clientId == null
				|| jndiName.length() < 1 || jndiProviderURL.length() < 1
				|| clientId.length() < 1) {
			throw new IllegalArgumentException(
				"Client ID and JNDI name and provider URL must be specified");
		}
		this.jndiName = jndiName;
		this.jndiProviderURL = jndiProviderURL;
		this.clientId = clientId;
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
