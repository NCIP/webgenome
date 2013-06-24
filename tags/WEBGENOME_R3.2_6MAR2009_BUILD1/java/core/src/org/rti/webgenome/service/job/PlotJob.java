/*
$Revision: 1.9 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.service.job;

import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.plot.PlotParameters;
import org.rti.webgenome.service.session.WebGenomeDbService;
import org.rti.webgenome.service.util.SerializedChromosomeArrayDataGetter;

/**
 * A job that generates a plot.
 * @author dhall
 *
 */
public class PlotJob extends AbstractJob {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(PlotJob.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/**
	 * ID of plot object to populate, if this job involves re-doing a
	 * plot with new parameters.  This will only be not-null
	 * if the job involves re-creating
	 * a plot with new parameters.
	 */
	private Long plotId = null;
	
	/** Experiments to plot. */
	private Set<Experiment> experiments = null;
	
	/** Plot parameters providing drawing instructions. */
	private PlotParameters plotParameters = null;

	//
	//  G E T T E R S / S E T T E R S
	//
	
	/**
	 * Get experiments to plot.
	 * @return Experiments to plot
	 */
	public Set<Experiment> getExperiments() {
		return experiments;
	}

	/**
	 * Set experiments to plot.
	 * @param experiments Experiments to plot
	 */
	public void setExperiments(final Set<Experiment> experiments) {
		this.experiments = experiments;
	}

	/**
	 * Get ID of plot to populate.
	 * @return ID of plot whose properties will be populated by this job.
	 * This will only be not-null if the job involves re-creating
	 * a plot with new parameters.
	 */
	public Long getPlotId() {
		return plotId;
	}

	/**
	 * Set ID of plot to populate.
	 * @param plot ID of plot whose properties will be populated by
	 * this job.  This property will only be set if the job
	 * involves re-creating
	 * a plot with new parameters.
	 */
	public void setPlotId(final Long plot) {
		this.plotId = plot;
	}

	/**
	 * Get parameters of plot to populate.
	 * @return Plot parameters.
	 */
	public PlotParameters getPlotParameters() {
		return plotParameters;
	}

	/**
	 * Set parameters of plot to populate.
	 * @param plotParameters Plot parameters
	 */
	public void setPlotParameters(final PlotParameters plotParameters) {
		this.plotParameters = plotParameters;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.  This should only be used by the
	 * persistence framework.
	 */
	public PlotJob() {
		
	}
	
	/**
	 * Constructor.
	 * @param plot ID of plot whose properties will be populated.
	 * This will only be not-null if the job involves re-creating
	 * a plot with new parameters.
	 * @param experiments Experiments to plot
	 * @param plotParameters Parameters of plot to populate
	 * @param userId User login name
	 * @param userDomain Domain in which user name applies.
	 */
	public PlotJob(final Long plot, final Set<Experiment> experiments,
			final PlotParameters plotParameters,
			final Long userId, final String userDomain) {
		super(userId, userDomain);
		this.plotId = plot;
		this.experiments = experiments;
		this.plotParameters = plotParameters;
		this.setDescription("Generating plot " + plotParameters.getPlotName());
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
		SerializedChromosomeArrayDataGetter dataGetter =
			jobServices.getIoService().getSerializedChromosomeArrayDataGetter();
		LOGGER.info("Plot job starting for user "
				+ this.getUserId());
		try {
			
			// Clone plot parameters attribute so that there will
			// be a different persistent object associated with
			// plot.  This makes it possible to delete plot objects
			// without first deleting the associated jobs.
			PlotParameters newParamsObj = this.plotParameters.deepCopy();
			
			// Plot and persist
			Plot plot = null;
			if (this.plotId != null) {
				plot = cart.getPlot(this.plotId);
			}
			jobServices.getPlotService().plotExperiments(plot,
					this.experiments, newParamsObj,
					cart, dataGetter);
			this.setTerminationMessage(Job.JOB_EXECUTION_SUCCESS_MESSAGE);
			dbService.updateShoppingCart(cart);
		} catch (Exception e) {
			this.setTerminationMessage(
					Job.JOB_EXECUTION_FAILURE_MESSAGE + ": "
							+ e.getMessage());
			LOGGER.info("Plot job failed for user " + this.getUserId());
			LOGGER.info(e);
			e.printStackTrace();
		}
		LOGGER.info("Analysis job completed for user "
				+ this.getUserId());
	}
}
