/*
$Revision: 1.1 $
$Date: 2008-05-23 21:08:56 $

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

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.io.RectangularFileWriter;
import org.rti.webgenome.service.io.SmdFormatException;
import org.rti.webgenome.service.session.WebGenomeDbService;
import org.rti.webgenome.util.SystemUtils;

/**
 * This is a job for importing data in a file into
 * the shopping cart.  The data are typically uploaded
 * and temporarily saved in a directory.  During importing
 * the data are transformed into the domain object model.
 * @author dhall
 *
 */
public class DownloadDataJob extends AbstractJob {

	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(DataImportJob.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/** List of ArrayDatum to download. */
	List<ArrayDatum> arrDatums = null;
	
	/** Sub-context of directory containing plot images. */
	private static final String SUB_CONTEXT =
		SystemUtils.getApplicationProperty("download.data.sub.context"); 	
    
	/** BioAssay */
	private BioAssay bioAssay = null;
	
	/** Rectangular File Writer; writes data to a file */
	private RectangularFileWriter rectFileWriter = null;
		
	
	//
	//  C O N S T R U C T O R S
	//


	/**
	 * Constructor.  This should only be used by the
	 * persistence framework.
	 */
	public DownloadDataJob() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param uploadDataSourceProperties Properties for upload
	 * @param userId User login name.
	 * @param userDomain Domain in which user name applies.
	 */
	public DownloadDataJob(
			final List<ArrayDatum> arrDatums, final BioAssay bioAssay, RectangularFileWriter rectFileWriter,
			final String userId, final String userDomain) {
		super(userId, userDomain);
		this.arrDatums = arrDatums;		
		this.setDescription("Downloading bioassay data for bioassay " + bioAssay.getName());
		this.bioAssay = bioAssay;
		this.rectFileWriter = rectFileWriter;
	}
	
	//
	//  O V E R R I D E S
	//

	/**
	 * Writes bioassay data to a file for later download.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final JobServices jobServices) {
		
		try {
			LOGGER.info("Data download job starting for user " + this.getUserId());
			
			// Write data to file
			rectFileWriter.writeData2File();
			
			this.setTerminationMessage(Job.JOB_EXECUTION_SUCCESS_MESSAGE);
			LOGGER.info("Bioassay Data download job completed for user " + this.getUserId());
		} catch (Exception e) {
			this.setTerminationMessage(
					Job.JOB_EXECUTION_FAILURE_MESSAGE + ": "
							+ e.getMessage());
			LOGGER.info("Bioassay data download failed for user " + this.getUserId());
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

	//
	//  G E T T E R S / S E T T E R S
	//
	public List<ArrayDatum> getArrDatums() {
		return arrDatums;
	}


	public void setArrDatums(List<ArrayDatum> arrDatums) {
		this.arrDatums = arrDatums;
	}


	


	public BioAssay getBioAssay() {
		return bioAssay;
	}


	public void setBioAssay(BioAssay bioAssay) {
		this.bioAssay = bioAssay;
	}


	public RectangularFileWriter getRectFileWriter() {
		return rectFileWriter;
	}


	public void setRectFileWriter(RectangularFileWriter rectFileWriter) {
		this.rectFileWriter = rectFileWriter;
	}

	
}
