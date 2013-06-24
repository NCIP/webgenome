/*
$Revision: 1.2 $
$Date: 2008-05-28 19:39:39 $


*/

package org.rti.webgenome.service.job;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			final Long userId, final String userDomain) throws Exception{
		super(userId, userDomain);
		this.arrDatums = arrDatums;		
		this.setDescription("Downloading bioassay data for bioassay " + bioAssay.getName());
		this.bioAssay = bioAssay;
		this.rectFileWriter = rectFileWriter;
		Map params = new HashMap();
		params.put("donwload.fileName", bioAssay.getName() + ".csv");
		setParamsMap(params);
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
			
			String fileName = this.bioAssay.getName() + ".csv";
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
