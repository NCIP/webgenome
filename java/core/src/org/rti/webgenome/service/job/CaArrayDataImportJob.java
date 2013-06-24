/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-10-23 16:17:07 $


*/

package org.rti.webgenome.service.job;

import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.client.CaArrayClient;
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
public class CaArrayDataImportJob extends AbstractJob {

	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(CaArrayDataImportJob.class);

	//
	//  A T T R I B U T E S
	//

	/** Properties of upload. */
	private UploadDataSourceProperties upload = null;

	private CaArrayClient client = null;
	private String expId = "";
	//
	//  G E T T E R S / S E T T E R S
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
	public CaArrayDataImportJob() {

	}


	/**
	 * Constructor.
	 * @param uploadDataSourceProperties Properties for upload
	 * @param userId User login name.
	 * @param userDomain Domain in which user name applies.
	 */
	public CaArrayDataImportJob(
			final CaArrayClient client,final String expId,
			final Long userId, final String userDomain) {
		super(userId, userDomain);
		this.client = client;
		this.expId = expId;

		this.setDescription("Importing caArray data for experiment " + expId);
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
		ShoppingCart cart = dbService.loadShoppingCart(this.getUserId(), this.getUserDomain());
		try {
			LOGGER.info("caArray data import job starting for user "
					+ this.getUserId());

			UploadDataSourceProperties upload = client.downloadExperiment2File(expId);

			// Set QuantitationType since had some trouble to set it inside caArrayClient
			upload.setQuantitationType(QuantitationType.LOG_2_RATIO_COPY_NUMBER);

			// Load data into shopping cart
			Experiment exp = ioService.loadSmdData(
					new UploadDataSourceProperties(
							upload), cart);

			// Persist new array object and shopping cart changes
			Array array = this.getArray(exp);
			array.setDisposable(true);
			assert array != null;
			dbService.saveArrayAndUpdateCart(array, cart);

			this.setTerminationMessage(Job.JOB_EXECUTION_SUCCESS_MESSAGE);
			LOGGER.info("caArray Data import job completed for user "
					+ this.getUserId());
		} catch (Exception e) {
			this.setTerminationMessage(
					Job.JOB_EXECUTION_FAILURE_MESSAGE + ": "
							+ e.getMessage());
			LOGGER.info("caArray Data import failed for user " + this.getUserId());
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
