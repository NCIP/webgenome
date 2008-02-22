/*
$Revision: 1.10 $
$Date: 2008-02-22 03:54:09 $

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

package org.rti.webgenome.service.job;

import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.io.SmdFormatException;
import org.rti.webgenome.service.session.WebGenomeDbService;

/**
 * This is a job for importing data in a file into
 * the shopping cart.  The data are typically uploaded
 * and temporarily saved in a directory.  During importing
 * the data are transformed into the domain object model.
 * @author dhall
 *
 */
public class DataImportJob extends AbstractJob {

	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(DataImportJob.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/** Properties of upload. */
	private UploadDataSourceProperties uploadDataSourceProperties = null;
	
	
	//
	//  G E T T E R S / S E T T E R S
	//
	
	/**
	 * Get properties for upload.
	 * @return Properties for upload
	 */
	public UploadDataSourceProperties getUploadDataSourceProperties() {
		return uploadDataSourceProperties;
	}


	/**
	 * Set properties for upload.
	 * @param uploadDataSourceProperties Properties for upload
	 */
	public void setUploadDataSourceProperties(
			final UploadDataSourceProperties uploadDataSourceProperties) {
		this.uploadDataSourceProperties = uploadDataSourceProperties;
	}
	
	//
	//  C O N S T R U C T O R S
	//


	/**
	 * Constructor.  This should only be used by the
	 * persistence framework.
	 */
	public DataImportJob() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param uploadDataSourceProperties Properties for upload
	 * @param userId User login name.
	 * @param userDomain Domain in which user name applies.
	 */
	public DataImportJob(
			final UploadDataSourceProperties uploadDataSourceProperties,
			final String userId, final String userDomain) {
		super(userId, userDomain);
		this.uploadDataSourceProperties = uploadDataSourceProperties;
		StringBuffer buff = new StringBuffer();
		int count = 0;
		for (DataFileMetaData meta
				: uploadDataSourceProperties.getDataFileMetaData()) {
			if (count++ > 0) {
				buff.append(", ");
			}
			buff.append(meta.getLocalFileName());
		}
		this.setDescription("Importing files " + buff.toString());
	}
	
	//
	//  O V E R R I D E S
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final JobServices jobServices) {
		IOService ioService = jobServices.getIoService();
		WebGenomeDbService dbService = jobServices.getWebGenomeDbService();
		ShoppingCart cart = dbService.getShoppingCart(this.getUserId(),
					this.getUserDomain());
		try {
			LOGGER.info("Data import job starting for user "
					+ this.getUserId());
			
			// Load data into shopping cart
			Experiment exp = ioService.loadSmdData(
					new UploadDataSourceProperties(
							this.uploadDataSourceProperties), cart);
			
			// Persist new array object and shopping cart changes
			Array array = this.getArray(exp);
			array.setDisposable(true);
			assert array != null;
			dbService.saveArrayAndUpdateCart(array, cart);
			
			this.setTerminationMessage(Job.JOB_EXECUTION_SUCCESS_MESSAGE);
			LOGGER.info("Data import job completed for user "
					+ this.getUserId());
		} catch (SmdFormatException e) {
			this.setTerminationMessage(
					Job.JOB_EXECUTION_FAILURE_MESSAGE + ": "
							+ e.getMessage());
			LOGGER.info("Data import failed for user " + this.getUserId());
			LOGGER.info(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Extract array object associated with all contained bioassays.
	 * That will have been the case for data uploaded from a file.
	 * @param exp An experiment
	 * @return Array object associated with bioassays contained in
	 * the experiment.
	 */
	private Array getArray(final Experiment exp) {
		Array array = null;
		Set<BioAssay> bioAssays = exp.getBioAssays();
		if (bioAssays.size() > 0) {
			array = bioAssays.iterator().next().getArray();
		}
		return array;
	}
}
