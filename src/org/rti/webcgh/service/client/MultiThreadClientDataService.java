/*
$Revision: 1.3 $
$Date: 2006-10-19 03:55:14 $

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

package org.rti.webcgh.service.client;

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.service.util.ServiceLocator;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.BioAssayMgr;
import org.rti.webgenome.client.BioAssayMgrHome;
import org.rti.webgenome.client.ExperimentDTO;


/**
 * Implementatation of <code>ClientDataService</code> interface
 * that uses multithreading to increase data throughput.
 */
public final class MultiThreadClientDataService
	implements ClientDataService {
	
	// ===============================
	//      Constants
	// ===============================

	/** Time interval in milliseconds between thread polling events. */
	private static final int THREAD_POLLING_WAIT_COUNT_MSEC = 10;
	
	
	// ===========================
	//       Attributes
	// ===========================
	
	/** JNDI name. This property should be injected. */
    private String jndiName = null;
    
    /** EJB service locator. */
    private final ServiceLocator serviceLocator = new ServiceLocator();
    
    
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
    	
    	// Get bioassay manager
    	BioAssayMgr mgr = null;
        try {
			BioAssayMgrHome home = (BioAssayMgrHome)
				this.serviceLocator.getLocalHome(this.jndiName);
			mgr = home.create();
		} catch (Exception e) {
			throw new WebcghSystemException("Error accessing client EJB", e);
		}
        
        // Create container for query results
        Collection<ThreadQueryResult> queryResults =
        	new ArrayList<ThreadQueryResult>();
    	
        // Create individual query threads
        for (int i = 0; i < experimentIds.length; i++) {
            String expID = experimentIds[i];
        	for (int j = 0; j < constraints.length; j++) {
                BioAssayDataConstraints constraint = constraints[j];
                ThreadQueryResult result = new ThreadQueryResult();
                queryResults.add(result);
                Thread thread = new Thread(new ClientDataThread(
                		expID, constraint, clientID, result, mgr));
                thread.start();
            }
        }
    	
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
				throw new WebcghSystemException(e);
			}
        }
        
        // Construct and return result collection
        Collection<ExperimentDTO> dtos = new ArrayList<ExperimentDTO>();
        for (ThreadQueryResult res : queryResults) {
        	dtos.add(res.getExperiment());
        }
        return Experiment.newExperiments(dtos);
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
    	// TODO: Implement this
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
				this.queryResult.setException(e);
			}
		}
	}
}
