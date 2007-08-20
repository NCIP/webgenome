/*
$Revision: 1.6 $
$Date: 2007-08-20 22:09:37 $

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
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.ArrayDao;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.io.SmdFormatException;

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
	
	/**
	 * Name of file to be parsed.  This is not an absolute
	 * path.  The job will be able to construct the
	 */
	private String fileName = null;
	
	/**
	 * Organism associated with data.
	 */
	private Organism organism = null;
	
	
	//
	//  G E T T E R S / S E T T E R S
	//
	

	/**
	 * Get name of file to parse.
	 * @return File name, not absolute path.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set name of file to parse.
	 * @param fileName File name, not absolute path.
	 */
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Get organism associated with data.
	 * @return Organism associated with data.
	 */
	public Organism getOrganism() {
		return organism;
	}

	/**
	 * Set organism associated with data.
	 * @param organism Organism associated with data.
	 */
	public void setOrganism(final Organism organism) {
		this.organism = organism;
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
	 * @param fileName Name of data file to parse.
	 * @param organism Organism associated with data.
	 * @param userId User login name.
	 */
	public DataImportJob(final String fileName,
			final Organism organism,
			final String userId) {
		super(userId);
		this.fileName = fileName;
		this.organism = organism;
		this.setDescription("Importing file " + fileName);
	}
	
	//
	//  O V E R R I D E S
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final JobServices jobServices) {
		ShoppingCartDao sDao = jobServices.getShoppingCartDao();
		ShoppingCart cart = sDao.load(this.getUserId());
		IOService ioService = jobServices.getIoService();
		ArrayDao aDao = jobServices.getArrayDao();
		try {
			LOGGER.info("Data import job starting for user "
					+ this.getUserId());
			
			// Load data into shopping cart
			Experiment exp = ioService.loadSmdData(
					this.fileName, this.organism, cart);
			
			// Persist new array object
			Array array = this.getArray(exp);
			array.setDisposable(true);
			assert array != null;
			aDao.save(array);
			
			// Persist shopping cart changes
			sDao.update(cart);
			
			this.setTerminationMessage("Succeeded");
			LOGGER.info("Data import job completed for user "
					+ this.getUserId());
		} catch (SmdFormatException e) {
			this.setTerminationMessage("Failed: " + e.getMessage());
			LOGGER.info("Data import failed for user " + this.getUserId());
			LOGGER.info(e);
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
