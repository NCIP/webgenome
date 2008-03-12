/*
$Revision: 1.1 $
$Date: 2008-03-12 22:23:18 $

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
