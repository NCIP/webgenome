/*
$Revision: 1.3 $
$Date: 2007-08-28 17:24:13 $

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

import org.apache.log4j.Logger;
import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.SingleAnalysisDataSourceProperties;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.service.dao.ShoppingCartDao;

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
	 */
	public ReRunAnalysisJob(final Experiment experiment,
			final AnalyticOperation operation,
			final String userId) {
		super(userId);
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
		ShoppingCartDao sDao = jobServices.getShoppingCartDao();
		ShoppingCart cart = sDao.load(this.getUserId());
		SerializedDataTransformer transformer =
			jobServices.getIoService().getSerializedDataTransformer();
		AnalysisService aService = jobServices.getAnalysisService();
		Experiment experiment =
			this.dataSourceProperties.getInputExperiment();
		AnalyticOperation operation =
			this.dataSourceProperties.getSourceAnalyticOperation();
		try {
			LOGGER.info("Re-nalysis job starting for user "
					+ this.getUserId());
			aService.rePerformAnalyticOperation(
					experiment, operation, transformer);
			sDao.update(cart);
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
