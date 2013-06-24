/*
$Revision: 1.1 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.domain;

import org.rti.webgenome.domain.DataSourceProperties.BaseDataSourceProperties;

/**
 * Data source propeties for some remote data source where access is through
 * an API.
 * @author dhall
 *
 */
public class RemoteApiDataSourceProperties extends BaseDataSourceProperties {

	//
	//  A T T R I B U T E S
	//
	
	/** Login name for remote system. */
	private String userName = null;
	
	/** Password for remote system. */
	private String password = null;
	
	/**
	 * Key for data source.  Typically this will be used in
	 * a singleton map of keys to data source objects
	 * that is managed by Spring.
	 */
	private String dataSourceKey = null;

	//
	//  G E T T E R S  /  S E T T E R S
	//
	
	/**
	 * Getter for password property.
	 * @return Password for remote system
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password property.
	 * @param password Password for remote system
	 */
	public void setPassword(final String password) {
		this.password = password;
	}
	
	
	/**
	 * Getter for dataSourceKey property.
	 * @return Key for data source.  Typically this will be used in
	 * a singleton map of keys to data source objects
	 * that is managed by Spring.
	 */
	public String getDataSourceKey() {
		return dataSourceKey;
	}

	/**
	 * Setter for dataSourceKey property.
	 * @param dataSourceKey Key for data source.
	 * Typically this will be used in
	 * a singleton map of keys to data source objects
	 * that is managed by Spring.
	 */
	public void setDataSourceKey(final String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

	/**
	 * Getter for userName property.
	 * @return Login name for remote system
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter for userName property.
	 * @param userName Login name for remote system
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public RemoteApiDataSourceProperties() {
		
	}

	/**
	 * Constructor.
	 * @param userName Login name for remote system
	 * @param password Password for remote system
	 * @param dataSourceKey Key for data source.  Typically this will be used in
	 * a singleton map of keys to data source objects
	 * that is managed by Spring.
	 */
	public RemoteApiDataSourceProperties(final String userName,
			final String password, final String dataSourceKey) {
		super();
		this.userName = userName;
		this.password = password;
		this.dataSourceKey = dataSourceKey;
	}
}
