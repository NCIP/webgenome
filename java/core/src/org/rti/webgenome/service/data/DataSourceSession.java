/*
$Revision: 1.1 $
$Date: 2008-02-15 20:03:50 $

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

package org.rti.webgenome.service.data;

import java.util.Collection;
import java.util.Map;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Principal;

/**
 * Represents a workflow session of interacting with a data source.
 * Also provides a facade to classes related to data sources.
 * Typically the workflow will proceed as follows:
 * <pre>
 * (1) System displays list of data sources.  User selects one.
 * (2) User logs into data source.
 * (3) System displays lists of experiments the user can access, and the user
 * selects one or more.
 * (4) System imports selected experiments into the users shopping cart.
 * </pre>
 * @author dhall
 *
 */
public class DataSourceSession {

	//
	//  A T T R I B U T E S
	//
	
	/** Principal of user associated with session. */
	private Principal principal = null;
	
	/** Data source selected by user. */
	private final DataSource selectedDataSource;
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 * @param selectedDataSource Data source selected by user
	 */
	DataSourceSession(final DataSource selectedDataSource) {
		if (selectedDataSource == null) {
			throw new WebGenomeSystemException(
					"Data source cannot be null");
		}
		this.selectedDataSource = selectedDataSource;
	}
	
	//
	//  B U S I N E S S    M E T H O D S
	//
	
	/**
	 * Log user into previously selected data source.
	 * @param user User account name
	 * @param password User password
	 * @throws DataSourceSessionException if a valid data source was not
	 * previously selected
	 * @throws DataSourceAccessException if the given credentials could
	 * not be validated by the selected data source
	 */
	public void loginToSelectedDataSource(
			final String user, final String password)
	throws DataSourceSessionException, DataSourceAccessException {
		if (this.selectedDataSource == null) {
			throw new DataSourceSessionException(
					"Valid data source has not been selected");
		}
		this.principal = this.selectedDataSource.login(user, password);
		if (this.principal == null) {
			throw new DataSourceAccessException("User could not be logged in");
		}
	}
	
	
	/**
	 * Get map of available experiment IDs (keys) and associated
	 * experiment names (values) from the previously selected
	 * data source that are available to the user.
	 * @return Map of available experiment IDs (keys) and associated
	 * experiment names (values)
	 * @throws DataSourceSessionException If a user has not successfully
	 * logged into the selected data source.
	 * @throws DataSourceAccessException If there is some problem accessing
	 * data from the data source
	 */
	public Map<String, String> getExperimentIdsAndNames()
	throws DataSourceSessionException, DataSourceAccessException {
		if (this.principal == null) {
			throw new DataSourceSessionException("This method cannot be called "
					+ "unless user has logged into a data source");
		}
		return this.selectedDataSource.getExperimentIdsAndNames(this.principal);
	}
	
	
	/**
	 * Fetch experiments with given IDs from previously selected data
	 * source.
	 * @param ids Experiment IDs.  An
	 * <code>IllegalArgumentException</code>
	 * is thrown if null
	 * @return Experiments with corresponding IDs.
	 * @throws DataSourceSessionException If the user was not
	 * previously logged into a data source
	 * @throws DataSourceAccessException If there is an error interacting
	 * with the data source
	 */
	public Collection<Experiment> fetchExperiments(
			final Collection<String> ids)
	throws DataSourceSessionException, DataSourceAccessException {
		if (ids == null) {
			throw new IllegalArgumentException(
					"Experiment IDs cannot be null");
		}
		if (this.principal == null) {
			throw new DataSourceSessionException(
					"User not logged into data source");
		}
		return this.selectedDataSource.getExperiments(ids);
	}
}
