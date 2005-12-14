/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/WebcghFileArrayDataSource.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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

package org.rti.webcgh.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.StringTokenizer;

import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayData;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeAssembly;
import org.rti.webcgh.array.Organism;
import org.rti.webcgh.core.WebcghSystemException;



/**
 * Implementation of ArrayExperimentDao using files
 */
public class WebcghFileArrayDataSource implements WebCghArrayDataSource {
	
	
    // ===============================================
    //    Attributes with accessors and mutators
    // ===============================================
    
	private String dataDir = null;
	private String dbName = null;
	private ArrayDatumFileReader arrayDatumFileReader = null;
	private DomainObjectFactory domainObjectFactory = new DomainObjectFactory();
	

	/**
	 * @param string Absolute path of directory containing data
	 */
	public void setDataDir(String string) {
		dataDir = string;
	}
	
	
    /**
     * @return Returns the dbName.
     */
    public String getDbName() {
        return dbName;
    }
    
    
    /**
     * @param dbName The dbName to set.
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    
	/**
	 * @return Returns the arrayDatumFileReader.
	 */
	public ArrayDatumFileReader getArrayDatumFileReader() {
		return arrayDatumFileReader;
	}
	
	
	/**
	 * @param arrayDatumFileReader The arrayDatumFileReader to set.
	 */
	public void setArrayDatumFileReader(
			ArrayDatumFileReader arrayDatumFileReader) {
		this.arrayDatumFileReader = arrayDatumFileReader;
	}
	
	
    // =============================
    //    Constructors
    // =============================
    
    /**
     * Constructor
     */
    public WebcghFileArrayDataSource() {}
    
    
    // ==============================================
    //   Methods in WebcghArrayDataSource interface
    // ==============================================
    
    
	/**
	 * Get all experiments that given user can access
	 * @param userProfile User profile
	 * @return Experiments
	 * @throws WebcghDatabaseException
	 */
	public Experiment[] getAllExperiments(UserProfile userProfile) throws WebcghDatabaseException {
		Collection metaCol = new ArrayList();
		Experiment[] experiments = new Experiment[0];
		try {
			File dir = new File(dataDir);
			if (! dir.exists() || ! dir.isDirectory())
			    throw new WebcghSystemException("Data directory '" + dir.getAbsolutePath() + 
			        "' is not a valid directory");
			String[] fnames = dir.list();
			if (fnames != null) {
				for (int i = 0; i < fnames.length; i++) {
					String fname = fnames[i];
					if (fname.indexOf(".properties") >= 0)
						metaCol.add(load(dataDir + "/" + fname));
				}
			} 
			
		} catch (IOException e) {
			throw new WebcghDatabaseException(
				"Error getting all experiment metadata", e);
		}
		experiments = (Experiment[])metaCol.toArray(experiments);
		return experiments;
	}

	
	/**
	 * Get an experiment
	 * @param id Experiment ID
	 * @param userProfile User profile
	 * @return An experiment
	 * @throws WebcghDatabaseException
	 * @throws AuthenticationException
	 */
	public Experiment getExperiment(String id, UserProfile userProfile)
		throws WebcghDatabaseException, AuthenticationException {
		Experiment experiment = null;
		try {
			String fname = dataDir + "/" + id + ".properties";
			experiment = this.load(fname); 
		} catch (Exception e) {
			throw new WebcghDatabaseException(
				"Error loading array experiment meta data", e);
		}
		return experiment;
	}
	
	
	/**
	 * Get bioassay data
	 * @param id Bioassay data ID
	 * @param userProfile User profile
	 * @return Bioassay
	 * @throws WebcghDatabaseException
	 * @throws AuthenticationException
	 */
	public BioAssayData getBioAssayData(String id, UserProfile userProfile)
		throws WebcghDatabaseException, AuthenticationException {
		BioAssayData bioAssayData = new BioAssayData();
		try {
			String fname = dataDir + "/" + id;
			this.arrayDatumFileReader.open(new File(fname), GenomeAssembly.DUMMY_GENOME_ASSEMBLY);
			ArrayDatum datum = this.arrayDatumFileReader.nextDatum();
			while (datum != null) {
				bioAssayData.add(datum);
				datum = this.arrayDatumFileReader.nextDatum();
			}
			this.arrayDatumFileReader.close();
		} catch (Exception e) {
			throw new WebcghDatabaseException("Error retrieving array data", e);
		}
		return bioAssayData;
	}
	
	
	/**
	 * Get an authenticator
	 * @return An authenticator
	 */
	public Authenticator getAuthenticator() {
	    return new PermissiveAuthenticator();
	}
	
	
	// ======================================
	//     Private methods
	// ======================================
	
	/**
	 * Load array experiment from a file
	 * @param fname Name of file
	 * @return Array experiment meta data
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private Experiment load(String fname)
		throws IOException, FileNotFoundException {
		Experiment experiment = null;
		File inFile = new File(fname);
		if (! inFile.exists())
			return null;
		InputStream in = new FileInputStream(inFile);
		Properties expProps = new Properties();
		expProps.load(in);
		int p = inFile.getName().indexOf(".properties");
		String expName = inFile.getName().substring(0, p);
		String contact = expProps.getProperty("contact");
		String description = expProps.getProperty("description");
		String genus = expProps.getProperty("genus");
		String species = expProps.getProperty("species");
		Organism organism = this.domainObjectFactory.getOrganism(genus, species);
		experiment = new Experiment(expName, description, this.dbName);
		experiment.setOrganism(organism);
		String arrays = expProps.getProperty("genomeArrayDataNames");
		StringTokenizer tok = new StringTokenizer(arrays);
		while (tok.hasMoreTokens()) {
			String bioAssayName = tok.nextToken();
			experiment.add(new BioAssay(bioAssayName, "", bioAssayName, this.dbName));
		}
		String type = expProps.getProperty("type");
		return experiment;
	}

}
