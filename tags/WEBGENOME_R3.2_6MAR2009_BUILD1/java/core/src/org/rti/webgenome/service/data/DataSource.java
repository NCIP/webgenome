/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:17 $

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

import java.util.Map;

import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.service.session.Authenticator;

/**
 * This interface represents a source of data.  Typically, this will
 * be a remote database system with some sort of service-oriented
 * API.
 * @author dhall
 *
 */
public interface DataSource extends Authenticator {

	/**
	 * Gets a map of experiment IDs (keys) to names (values)
	 * that are accessible by the given principal.
	 * @param principal Principal requesting access to data
	 * @return Map of experiment IDs (keys) to experiment names (values)
	 * @throws DataSourceAccessException if there is a problem accessing
	 * the data
	 */
	Map<String, String> getExperimentIdsAndNames(Principal principal)
	throws DataSourceAccessException;
	
	
	/**
	 * Get experiment data transfer object with given ID.
	 * @param id ID of experiment-equivalent in remote data source
	 * @return Requested experiment data transfer object
	 * @throws DataSourceAccessException if there is a problem accessing
	 * the data
	 */
	ExperimentDto getExperimentDto(String id)
	throws DataSourceAccessException;
	
	/**
	 * Get bioassay data transfer object with given ID.
	 * @param id ID of bioassay-equivalent in remote data source
	 * @return Requested bioassay data transfer object
	 * @throws DataSourceAccessException if there is a problem accessing
	 * the data
	 */
	BioAssayDto getBioAssayDto(String id)
	throws DataSourceAccessException;
	
	/**
	 * Get array data transfer object with given ID.
	 * @param id ID of array-equivalent in remote data source
	 * @return Requested array data transfer object
	 * @throws DataSourceAccessException if there is a problem accessing
	 * the data
	 */
	ArrayDto getArrayDto(String id)
	throws DataSourceAccessException;
	
	/**
	 * Get name of data source to display in UI.
	 * @return Display name
	 */
	String getDisplayName();
}
