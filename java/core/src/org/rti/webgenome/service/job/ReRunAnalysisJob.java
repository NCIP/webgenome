/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.7 $
$Date: 2008-02-22 03:54:09 $


*/

package org.rti.webgenome.service.job;

import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.SingleAnalysisDataSourceProperties;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;

/**
 * A job that re-runs an analytic operation on a single
 * experiment with new user-supplied parameters.
 * @author dhall
 *
 */
public class ReRunAnalysisJob extends AbstractJob {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(ReRunAnalysisJob.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/**
	 * Data source properties providing experiment and
	 * analytic operation to perform.  The operation should
	 * contain new user-supplied parameter settings.
	 */
	private SingleAnalysisDataSourceProperties dataSourceProperties = null;
	
	//
	//  G E T T E R S / S E T T E R S
	//
	
	/**
	 * Get data source properties providing experiment and
	 * analytic operation to perform.  The operation should
	 * contain new user-supplied parameter settings.
	 * @return Data source properties.
	 */
	public SingleAnalysisDataSourceProperties getDataSourceProperties() {
		return dataSourceProperties;
	}


	/**
	 * Set data source properties providing experiment and
	 * analytic operation to perform.  The operation should
	 * contain new user-supplied parameter settings.
	 * @param dataSourceProperties Data source properties
	 */
	public void setDataSourceProperties(
			final SingleAnalysisDataSourceProperties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}


	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public ReRunAnalysisJob() {
		
	}
	
	/**
	 * Constructor.
	 * @param experiment Input experiment for operation.
	 * @param operation Operation to re-run.  New user-specified
	 * parameter values must be previously set.
	 * @param userId Login name of user.
	 * @param userDomain Domain in which user name applies.
	 */
	public ReRunAnalysisJob(final Experiment experiment,
			final AnalyticOperation operation,
			final Long userId, final String userDomain) {
		super(userId, userDomain);
		this.dataSourceProperties = new SingleAnalysisDataSourceProperties(
				experiment, operation);
		this.setDescription("Re-running analytic operation "
				+ operation.getName() + " on experiment "
				+ experiment.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final JobServices jobServices) {
		SerializedDataTransformer transformer =
			jobServices.getIoService().getSerializedDataTransformer();
		AnalysisService aService = jobServices.getAnalysisService();
		Experiment experiment =
			this.dataSourceProperties.getInputExperiment();
		AnalyticOperation operation =
			this.dataSourceProperties.getSourceAnalyticOperation();
		try {
			LOGGER.info("Re-analysis job starting for user "
					+ this.getUserId());
			Set<String> replacedFiles = aService.rePerformAnalyticOperation(
					experiment, operation, transformer);
			jobServices.getWebGenomeDbService().updateExperiment(experiment);
			jobServices.getIoService().deleteDataFiles(replacedFiles);
			this.setTerminationMessage(Job.JOB_EXECUTION_SUCCESS_MESSAGE);
			LOGGER.info("Re-analysis job completed for user "
					+ this.getUserId());
		} catch (AnalyticException e) {
			this.setTerminationMessage(
					Job.JOB_EXECUTION_FAILURE_MESSAGE + ": "
							+ e.getMessage());
			LOGGER.info("Re-analysis job failed for user "
					+ this.getUserId());
			LOGGER.info(e);
			e.printStackTrace();
		}
	}	
}
