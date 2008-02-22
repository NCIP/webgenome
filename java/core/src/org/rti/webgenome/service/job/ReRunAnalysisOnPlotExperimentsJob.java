/*
$Revision: 1.7 $
$Date: 2008-02-22 18:24:44 $

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
import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.service.session.WebGenomeDbService;
import org.rti.webgenome.service.util.SerializedChromosomeArrayDataGetter;

/**
 * A job to re-run all producing analytic operations on
 * all derived experiments in a single plot.
 * @author dhall
 *
 */
public class ReRunAnalysisOnPlotExperimentsJob extends AbstractJob {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(
			ReRunAnalysisOnPlotExperimentsJob.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/**
	 * Experiments that will be re-generated.  All
	 * experiments must be derived from an analytic
	 * operation.  Furthermore, the data source property
	 * in each experiment should include new user-specified
	 * analytic operation parameter values.
	 */
	private Set<Experiment> experiments = null;
	
	/** ID of plot to re-create. */
	private Long plotId = null;
	
	
	//
	//  G E T T E R S / S E T T E R S
	//
	
	
	/**
	 * Get experiments that will be re-generated.  All
	 * experiments must be derived from an analytic
	 * operation.  Furthermore, the data source property
	 * in each experiment should include new user-specified
	 * analytic operation parameter values.
	 * @return Experiments that will be re-generated.
	 */
	public Set<Experiment> getExperiments() {
		return experiments;
	}


	/**
	 * Set experiments that will be re-generated.  All
	 * experiments must be derived from an analytic
	 * operation.  Furthermore, the data source property
	 * in each experiment should include new user-specified
	 * analytic operation parameter values.
	 * @param experiments Experiments that will be re-generated
	 */
	public void setExperiments(final Set<Experiment> experiments) {
		this.experiments = experiments;
	}
	
	/**
	 * Get ID of plot to regenerate.
	 * @return Plot primary key ID
	 */
	public Long getPlotId() {
		return plotId;
	}


	/**
	 * Set ID of plot to regenerate.
	 * @param plotId Primary key ID
	 */
	public void setPlotId(final Long plotId) {
		this.plotId = plotId;
	}


	//
	//  C O N S T R U C T O R S
	//


	/**
	 * Constructor.  This should only be used by the
	 * persistence framework.
	 */
	public ReRunAnalysisOnPlotExperimentsJob() {
		
	}
	
	/**
	 * Constructor.
	 * @param experiments Experiments that will be re-generated.  All
	 * experiments must be derived from an analytic
	 * operation.  Furthermore, the data source property
	 * in each experiment should include new user-specified
	 * analytic operation parameter values.
	 * @param plotId Primary key ID of plot to regenerate
	 * @param userId User login name
	 * @param userDomain Domain in which user name applies.
	 */
	public ReRunAnalysisOnPlotExperimentsJob(
			final Set<Experiment> experiments,
			final Long plotId,
			final String userId, final String userDomain) {
		super(userId, userDomain);
		this.experiments = experiments;
		this.plotId = plotId;
		StringBuffer buff = new StringBuffer(
				"Regenerating plot with experiments ");
		int count = 0;
		for (Experiment exp : experiments) {
			if (count++ > 0) {
				buff.append(" ,");
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
		SerializedDataTransformer transformer =
			jobServices.getIoService().getSerializedDataTransformer();
		AnalysisService aService = jobServices.getAnalysisService();
		WebGenomeDbService dbService = jobServices.getWebGenomeDbService();
		ShoppingCart cart = dbService.loadShoppingCart(this.getUserId(),
				this.getUserDomain());
		SerializedChromosomeArrayDataGetter dataGetter =
			jobServices.getIoService().getSerializedChromosomeArrayDataGetter();
		try {
			LOGGER.info("Plot re-analysis job starting for user "
					+ this.getUserId());
			
			// Re-do analytic operation
			Set<String> replacedFiles =
				aService.rePerformAnalyticOperation(
					this.experiments, transformer);
			
			// Plot
			Plot plot = cart.getPlot(this.plotId);
			jobServices.getPlotService().plotExperiments(plot,
					this.experiments, plot.getPlotParameters(),
					cart, dataGetter);
			
			// Persist
			dbService.updateExperimentsAndCart(this.experiments, cart);
			jobServices.getIoService().deleteDataFiles(replacedFiles);
			
			this.setTerminationMessage(Job.JOB_EXECUTION_SUCCESS_MESSAGE);
			LOGGER.info("Plot re-analysis job completed for user "
					+ this.getUserId());
		} catch (AnalyticException e) {
			this.setTerminationMessage(
					Job.JOB_EXECUTION_FAILURE_MESSAGE + ": "
							+ e.getMessage());
			LOGGER.info("Plot re-analysis job failed for user "
					+ this.getUserId());
			LOGGER.info(e);
			e.printStackTrace();
		}
	}
}
