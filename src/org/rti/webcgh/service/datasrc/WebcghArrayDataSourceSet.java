/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/datasrc/WebcghArrayDataSourceSet.java,v $
$Revision: 1.1 $
$Date: 2006-09-05 14:06:44 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package org.rti.webcgh.service.datasrc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.rti.webcgh.array.BioAssayData;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.service.authentication.AuthenticationException;
import org.rti.webcgh.service.authentication.UserProfile;


/**
 * Set of all registered array "databases."
 */
public class WebcghArrayDataSourceSet {
    
    // ===============================================
    //         Constants
    // ===============================================
    
    private static final String VIRTUAL_EXPERIMENT_DATA_SOURCE_NAME = "Virtual";
    
    
    // =================================================================
    //              Attributes with accessors and mutators
    // =================================================================
    
    
    private Map databases = new HashMap();
    private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
    
    

    /**
     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentDomainObjectMgr) {
        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
    }
    
    
    /**
     * @return Returns the databases.
     */
    public Map getDatabases() {
        return databases;
    }
    
    
    /**
     * @param databases The databases to set.
     */
    public void setDatabases(Map databases) {
        this.databases = databases;
    }
    
    
    // ====================================================
    //          Constructors
    // ====================================================
    
    
    /**
     * Constructor
     */
    public WebcghArrayDataSourceSet() {}
    
    
    // ======================================================
    //          Public methods
    // ======================================================
    
    
	/**
	 * Load metadata from all array experiments in database that
	 * are accessible to given user
	 * @param profile User profile
	 * @param loadVirtualExperiments Load virtual experiments?
	 * @return Keys are database names, values Collection objects containing
	 * Experiment objects
	 * @throws WebcghDatabaseException Thrown if problem encountered
	 * getting data from database
	 */
	public Experiment[] loadAllExperiments(UserProfile profile, boolean loadVirtualExperiments)
		throws WebcghDatabaseException {
	    Collection experimentCol = new ArrayList();
	    for (Iterator it = this.databases.keySet().iterator(); it.hasNext();) {
	        String dbName = (String)it.next();
	        WebCghArrayDataSource ds = (WebCghArrayDataSource)this.databases.get(dbName);
	        Experiment[] experiment = ds.getAllExperiments(profile);
	        for (int i = 0; i < experiment.length; i++) {
	            Experiment tempExperiment = experiment[i];
	            if (! loadVirtualExperiments && tempExperiment.isVirtual())
	                continue;
	            experimentCol.add(tempExperiment);
	            for (BioAssayIterator baIt = tempExperiment.bioAssayIterator(); baIt.hasNext();)
	                baIt.next().setDatabaseName(dbName);
	        }
	    }
	    Experiment[] experimentA = new Experiment[0];
	    experimentA = (Experiment[]) experimentCol.toArray(experimentA);
	    return experimentA;
	}
	
	
	/**
	 * Load metadata from all array experiments in database that
	 * are accessible to given user
	 * @param profile User profile
	 * @return Keys are database names, values Collection objects containing
	 * Experiment objects
	 * @throws WebcghDatabaseException Thrown if problem encountered
	 * getting data from database
	 */
	public Experiment[] loadAllExperiments(UserProfile profile)
		throws WebcghDatabaseException {
	    return this.loadAllExperiments(profile, true);
	}
		
	
	/**
	 * Load metadata from specified array experiment
	 * @param name Array experiment identifier
	 * @param dbName Database name
	 * @param profile User profile
	 * @return Array experiment metadata
	 * @throws WebcghDatabaseException Thrown if problem encountered
	 * getting data from database
	 * @throws AuthenticationException Thrown if user is not
	 * authorized to access specified data
	 */
	public Experiment loadExperiment
	(
		String name, String dbName, UserProfile profile
	)
		throws WebcghDatabaseException, AuthenticationException {
	    Experiment experiment = null;
	    WebCghArrayDataSource ds = (WebCghArrayDataSource)this.databases.get(dbName);
	    if (ds == null)
	        throw new IllegalArgumentException("The database '" + dbName + "' is not registered with the system");
	    experiment = ds.getExperiment(name, profile);
	    if (experiment != null)
	        experiment.setDatabaseName(dbName);
	    return experiment;
	}
		
	
	/**
	 * Load data from individual array
	 * @param ds Array identifier
	 * @param dbName Database name
	 * @param profile User profile
	 * @return Array data
	 * @throws WebcghDatabaseException Thrown if problem encountered
	 * getting data from database
	 * @throws AuthenticationException Thrown if user is not
	 * authorized to access specified data
	 */	
	public BioAssayData loadBioAssayData
	(
		String ds, String dbName, UserProfile profile
	)
		throws WebcghDatabaseException, AuthenticationException {
	    WebCghArrayDataSource dao = (WebCghArrayDataSource)this.databases.get(dbName);
	    if (dao == null)
	        throw new IllegalArgumentException("The database '" + dbName + "' is not registered with the system");
	    return dao.getBioAssayData(ds, profile);
	}
	
}
