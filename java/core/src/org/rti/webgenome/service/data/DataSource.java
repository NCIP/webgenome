/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:17 $


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
