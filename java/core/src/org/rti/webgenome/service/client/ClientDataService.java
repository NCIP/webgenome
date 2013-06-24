/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:35 $


*/

package org.rti.webgenome.service.client;

import java.util.Collection;

import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.domain.Experiment;

/**
 * Interface for getting data from application client.
 *
 */
public interface ClientDataService {
	
	/**
	 * Get data from application client.
	 * @param constraints Query constraints
	 * @param experimentIds Experiment identifiers
	 * @param clientID Application client ID
	 * @return Experiments from application client
	 */
    Collection<Experiment> getClientData(
    		BioAssayDataConstraints[] constraints,
    		String[] experimentIds, String clientID);
    
    /**
     * Add data to given experiments.
     * @param experiments Experiments
     * @param constraints Query constraints
     * @param clientId Application client ID
     */
    void addData(Collection<Experiment> experiments,
    		BioAssayDataConstraints[] constraints,
    		String clientId);
}
