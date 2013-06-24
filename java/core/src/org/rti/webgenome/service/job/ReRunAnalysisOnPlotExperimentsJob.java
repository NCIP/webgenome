/*
$Revision: 1.7 $
$Date: 2008-02-22 18:24:44 $


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
			final Long userId, final String userDomain) {
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
		ShoppingCart cart = dbService.loadShoppingCart(this.getUserId(), this.getUserDomain());
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
