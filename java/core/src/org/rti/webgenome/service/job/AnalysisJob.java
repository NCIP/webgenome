/*
$Revision: 1.6 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.service.job;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.MultiAnalysisDataSourceProperties;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.service.session.WebGenomeDbService;

/**
 * Job performing an analytic operation.
 * @author dhall
 */
public class AnalysisJob extends AbstractJob {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(AnalysisJob.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/** Source of data, operation to perform, and user specified params. */
	private MultiAnalysisDataSourceProperties dataSourceProperties = null;
	
	/** Map of input bioassay IDs to output bioassay names. */
	private Map<Long, String> outputBioAssayNames = null;
	
	/** Map of input experiment IDs to output experiment names. */
	private Map<Long, String> outputExperimentNames = null;

	
	//
	//  G E T T E R S / S E T T E R S
	//
	
	/**
	 * Get data source properties giving source of data,
	 * operation to perform, and user specified params.
	 * @return Data source properties
	 */
	public MultiAnalysisDataSourceProperties getDataSourceProperties() {
		return dataSourceProperties;
	}


	/**
	 * Set data source properties giving source of data,
	 * operation to perform, and user specified params.
	 * @param dataSourceProperties Data source properties
	 */
	public void setDataSourceProperties(
			final MultiAnalysisDataSourceProperties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}


	/**
	 * Get map of input bioassay IDs to output bioassay names.
	 * @return Map of input bioassay IDs to output bioassay names.
	 */
	public Map<Long, String> getOutputBioAssayNames() {
		return outputBioAssayNames;
	}


	/**
	 * Set map of input bioassay IDs to output bioassay names.
	 * @param outputBioAssayNames Map of input bioassay IDs to
	 * output bioassay names.
	 */
	public void setOutputBioAssayNames(
			final Map<Long, String> outputBioAssayNames) {
		this.outputBioAssayNames = outputBioAssayNames;
	}


	/**
	 * Get map of input experiment IDs to output experiment names.
	 * @return Map of input experiment IDs to output experiment names.
	 */
	public Map<Long, String> getOutputExperimentNames() {
		return outputExperimentNames;
	}


	/**
	 * Set map of input experiment IDs to output experiment names.
	 * @param outputExperimentNames Map of input experiment IDs to
	 * output experiment names.
	 */
	public void setOutputExperimentNames(
			final Map<Long, String> outputExperimentNames) {
		this.outputExperimentNames = outputExperimentNames;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.  This should only be used by the
	 * persistence framework and not called explicitly
	 * from business code.
	 */
	public AnalysisJob() {
	
	}
	
	/**
	 * Constructor.
	 * @param experiments Experiments on which to perform operation.
	 * @param operation Operation to perform.
	 * @param outputBioAssayNames Map of input bioassay IDs to output
	 * bioassay names.
	 * @param outputExperimentNames Map of input experiment IDs to output
	 * experiment names.
	 * @param userId User account name
	 * @param userDomain Domain in which user name applies.
	 */
	public AnalysisJob(final Collection<Experiment> experiments,
			final AnalyticOperation operation,
			final Map<Long, String> outputBioAssayNames,
			final Map<Long, String> outputExperimentNames,
			final Long userId, final String userDomain) {
		super(userId, userDomain);
		this.dataSourceProperties = new MultiAnalysisDataSourceProperties(
				new HashSet<Experiment>(experiments), operation);
		this.outputBioAssayNames = outputBioAssayNames;
		this.outputExperimentNames = outputExperimentNames;
		StringBuffer buff = new StringBuffer("Analytic operation ");
		buff.append(operation.getName());
		buff.append(" on experiments ");
		int count = 0;
		for (Experiment exp : experiments) {
			if (count++ > 0) {
				buff.append(", ");
			}
			buff.append(exp.getName());
		}
		this.setDescription(buff.toString());
	}


	//
	//  O V E R R I D E S
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final JobServices jobServices) {
		WebGenomeDbService dbService = jobServices.getWebGenomeDbService();
		ShoppingCart cart = dbService.loadShoppingCart(this.getUserId(), this.getUserDomain());
		SerializedDataTransformer transformer =
			jobServices.getIoService().getSerializedDataTransformer();
		AnalysisService aService = jobServices.getAnalysisService();
		Collection<Experiment> experiments =
			this.dataSourceProperties.getInputExperiments();
		AnalyticOperation op =
			this.dataSourceProperties.getSourceAnalyticOperation();
		try {
			LOGGER.info("Analysis job starting for user "
					+ this.getUserId());
			aService.performAnalyticOperation(experiments, op, cart,
					this.outputExperimentNames, this.outputBioAssayNames,
					transformer);
			dbService.updateShoppingCart(cart);
			this.setTerminationMessage(Job.JOB_EXECUTION_SUCCESS_MESSAGE);
			LOGGER.info("Analysis job completed for user "
					+ this.getUserId());
		} catch (AnalyticException e) {
			this.setTerminationMessage(
					Job.JOB_EXECUTION_FAILURE_MESSAGE + ": "
							+ e.getMessage());
			LOGGER.info("Analysis job failed for user " + this.getUserId());
			LOGGER.info(e);
			e.printStackTrace();
		}
	}
}
