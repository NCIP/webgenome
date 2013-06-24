/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $


*/

package org.rti.webgenome.service.client;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.BioAssayMgr;
import org.rti.webgenome.client.BioAssayMgrHome;
import org.rti.webgenome.client.ExperimentDTO;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.service.util.ServiceLocator;


/**
 * Implementatation of <code>ClientDataService</code> interface
 * that obtains data from a <code>BioAssayMgr</code> EJB data source.
 */
public final class BioAssayMgrEjbClientDataService
	implements ClientDataService {
    
    // ============================
    //     Static members
    // ============================

    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(BioAssayMgrEjbClientDataService.class);

	
	// ===============================
	//      Constants
	// ===============================

	/** Time interval in milliseconds between thread polling events.
     * TODO: This might be nice to inject as a configurable property setting? */
	private static final int THREAD_POLLING_WAIT_COUNT_MSEC = 10;
	
	
	// ===========================
	//       Attributes
	// ===========================
	
	/** JNDI name. This property should be injected. */
    private String jndiName = null;
    
    /** JNDI Provider URL. This property should be injected. */
    private String jndiProviderURL = null ;
    
    /** EJB service locator. */
    private final ServiceLocator serviceLocator = new ServiceLocator();
    
    /** Bioassay manager. */
    private BioAssayMgr bioAssayMgr = null;
    
    
    // =============================
    //      Getters/setters
    // =============================
    
    /**
     * Set JNDI name.
     * @param jndiName JNDI name
     */
	public void setJndiName(final String jndiName) {
		this.jndiName = jndiName;
	}
    
    /**
     * Set JNDI Provider URL.
     * @param jndiProviderURL JNDI Provider URL
     */
    public void setJndiProviderURL(final String jndiProviderURL) {
        this.jndiProviderURL = jndiProviderURL;
        
        // Get bioassay manager
        try {
            LOGGER.debug(
            		"Getting BioAssayMgrHome interface from jndiName [" + jndiName + "] jndiProviderURL [" + jndiProviderURL + "]" ) ;
			BioAssayMgrHome home = (BioAssayMgrHome)
				this.serviceLocator.getLocalHome(this.jndiName, this.jndiProviderURL);
			this.bioAssayMgr = home.create();
		} catch (Exception e) {
			throw new WebGenomeSystemException(
                    "Error accessing client EJB using JNDI Name [" + jndiName + "] JNDI Provider URL [" + 
                    jndiProviderURL + "].", e);
		}
    }
	
	// ===================================
	//     ClientDataService interface
	// ===================================

	/**
	 * Get data from application client.
	 * @param constraints Query constraints
	 * @param experimentIds Experiment identifiers
	 * @param clientID Application client ID
	 * @return Experiments from application client
	 */
    public Collection<Experiment> getClientData(
    		final BioAssayDataConstraints[] constraints,
            final String[] experimentIds, final String clientID) {
    	Collection<ExperimentDTO> dtos = this.getExperimentDtos(
    			constraints, experimentIds, clientID);
    	return Experiment.newExperiments(dtos, constraints);
    }
    
    
    /**
     * Get experiment DTOs from client.
     * @param constraints Query constraints
	 * @param experimentIds Experiment identifiers
	 * @param clientID Application client ID
     * @return Experiment DTOs
     */
    private Collection<ExperimentDTO> getExperimentDtos(
    		final BioAssayDataConstraints[] constraints,
            final String[] experimentIds, final String clientID) {
        
        // Create container for query results
        Collection<ThreadQueryResult> queryResults =
        	new ArrayList<ThreadQueryResult>();
    	
        // Create individual query threads
        LOGGER.debug( "Creating " + experimentIds.length + " Threads for querying" ) ;
        for (int i = 0; i < experimentIds.length; i++) {
            String expID = experimentIds[i];
            LOGGER.debug( "Creating Thread for Experiment [" + expID + "]") ;
        	for (int j = 0; j < constraints.length; j++) {
                BioAssayDataConstraints constraint = constraints[j];
                ThreadQueryResult result = new ThreadQueryResult();
                queryResults.add(result);
                Thread thread = new Thread(new ClientDataThread(
                		expID, constraint, clientID, result, this.bioAssayMgr));
                thread.start();
            }
        }
        LOGGER.debug( "Dispatched all query threads - polling for results" ) ;
    	
        // Periodically iterate over query results
        // until all query threads finished.
        while (true) {
        	boolean allFinished = true;
        	for (ThreadQueryResult r : queryResults) {
        		if (r.getException() != null) {
        			throw new ClientDataServiceException(
        					"Error obtaining data from application client",
        					r.getException());
        		}
        		if (!r.isQueryFinished()) {
        			allFinished = false;
        			break;
        		}
        	}
        	if (allFinished) {
        		break;
        	}
        	try {
				Thread.sleep(THREAD_POLLING_WAIT_COUNT_MSEC);
			} catch (InterruptedException e) {
				throw new WebGenomeSystemException(e);
			}
        }
        LOGGER.debug ( "All Query Threads are finished" ) ;
        
        // Construct and return result collection
        Collection<ExperimentDTO> dtos = new ArrayList<ExperimentDTO>();
        for (ThreadQueryResult res : queryResults) {
        	dtos.add(res.getExperiment());
        }
        
        return dtos;
    }
    
    
    /**
     * Add data to given experiments.
     * @param experiments Experiments
     * @param constraints Query constraints
     * @param clientId Application client ID
     */
    public void addData(final Collection<Experiment> experiments,
    		final BioAssayDataConstraints[] constraints,
    		final String clientId) {
    	
    	// Check args
    	if (experiments == null || experiments.size() < 1) {
    		throw new IllegalArgumentException("Experiment list is empty");
    	}
    	if (constraints == null || constraints.length < 1) {
    		throw new IllegalArgumentException(
    				"Bioassay data constraints are empty");
    	}
    	if (clientId == null || clientId.length() < 1) {
    		throw new IllegalArgumentException("No client ID");
    	}
    	
    	// Get list of experiment IDs
    	Collection<String> idList = new ArrayList<String>();
    	StringBuffer msg = new StringBuffer(
    			"Retrieving additional data for experiments ");
    	for (Experiment exp : experiments) {
    		String id = exp.getSourceDbId();
    		msg.append(" " + id);
    		idList.add(id);
    	}
    	String[] sourceDbIds = new String[0];
    	sourceDbIds = idList.toArray(sourceDbIds);
    	LOGGER.info(msg.toString());
    	
    	// Get data from client
    	Collection<ExperimentDTO> dtos = this.getExperimentDtos(
    			constraints, sourceDbIds, clientId);
    	
    	// Add data to experiments
    	for (ExperimentDTO dto : dtos) {
    		for (Experiment exp : experiments) {
    			if (dto.getExperimentID().equals(exp.getSourceDbId())) {
    				LOGGER.info("Adding additional data to experiment '"
    						+ exp.getSourceDbId() + "'");
    				exp.addData(dto, constraints);
    				break;
    			}
    		}
    	}
    }
    
    
    // ====================================
    //      Helper classes
    // ====================================
    
    
    /**
     * Special class to hold the result of one query
     * by a thread.
     * @author dhall
     *
     */
    static final class ThreadQueryResult {
    	
    	/** Experiment result of query. */
    	private ExperimentDTO experiment = null;
    	
    	/** Exception thrown during query. */
    	private Exception exception = null;
    	
    	/**
    	 * Is query finished?
    	 * @return T/F
    	 */
    	public boolean isQueryFinished() {
			return
				this.experiment != null || this.exception != null;
		}

		/**
		 * Get experiment.
		 * @return Experiment
		 */
		public ExperimentDTO getExperiment() {
			return this.experiment;
		}


		/**
		 * Set experiment.
		 * @param experiment Experiment.
		 */
		public void setExperiment(final ExperimentDTO experiment) {
			this.experiment = experiment;
		}


		/**
		 * Get exception thrown during query.
		 * @return Exception thrown during query.
		 */
		public Exception getException() {
			return exception;
		}

		/**
		 * Set exception thrown during query.
		 * @param exception Exception thrown during query.
		 */
		public void setException(final Exception exception) {
			this.exception = exception;
		}
    }
    
    
    /**
     * A thread that gets a portion of requested data.
     */
	static class ClientDataThread implements Runnable {
        
        /** Logger. */
        private static final Logger LOGGER =
            Logger.getLogger(ClientDataThread.class);
        
		
		// ========================
		//    Attributes
		// ========================
		
		/** ID of experiment to retrieve. */
		private final String experimentID;
		
		/** Data query constraints. */
		private final BioAssayDataConstraints constraint;
		
		/** Application client ID. */
		private final String clientID;
		
		/** Query result. */
		private final ThreadQueryResult queryResult;
		
		/** Bioassay manager. */
		private final BioAssayMgr bioAssayMgr;
		
		/**
		 * Constructor.
		 * @param experimentID Experiment ID
		 * @param constraint Query constraints
		 * @param clientID Client application ID
		 * @param queryResult query results
		 * @param bioAssayMgr Bioassay manager
		 */
        public ClientDataThread(final String experimentID,
        		final BioAssayDataConstraints constraint,
        		final String clientID,
        		final ThreadQueryResult queryResult,
        		final BioAssayMgr bioAssayMgr) {
        	this.experimentID = experimentID;
        	this.constraint = constraint;
        	this.clientID = clientID;
        	this.queryResult = queryResult;
        	this.bioAssayMgr = bioAssayMgr;
        }

        /**
         * Start thread.
         */
		public void run() {
			try {
		        ExperimentDTO dto = this.bioAssayMgr.getExperiment(
		        		this.experimentID, this.constraint, this.clientID);
		        this.queryResult.setExperiment(dto);
			} catch (Exception e) {
                LOGGER.error( "Caught Exception getting Experiment DTO for experimentID [" +
                        this.experimentID + "] Details: " + e.getMessage() ) ;
				this.queryResult.setException(e);
			}
		}
	}
}
