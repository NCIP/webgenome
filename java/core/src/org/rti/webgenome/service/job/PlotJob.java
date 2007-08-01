/*
$Revision: 1.2 $
$Date: 2007-08-01 23:05:01 $

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
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.plot.PlotParameters;
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
	 * Plot object to populate, if this job involves re-doing a
	 * plot with new parameters.  This will only be not-null
	 * if the job involves re-creating
	 * a plot with new parameters.
	 */
	private Plot plot = null;
	
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
	 * Get plot to populate.
	 * @return Plot whose properties will be populated by this job.
	 * This will only be not-null if the job involves re-creating
	 * a plot with new parameters.
	 */
	public Plot getPlot() {
		return plot;
	}

	/**
	 * Set plot to populate.
	 * @param plot Plot whose properties will be populated by
	 * this job.  This property will only be set if the job
	 * involves re-creating
	 * a plot with new parameters.
	 */
	public void setPlot(final Plot plot) {
		this.plot = plot;
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
	 * @param plot Plot whose properties will be populated.
	 * This will only be not-null if the job involves re-creating
	 * a plot with new parameters.
	 * @param experiments Experiments to plot
	 * @param plotParameters Parameters of plot to populate
	 * @param userId User login name
	 */
	public PlotJob(final Plot plot, final Set<Experiment> experiments,
			final PlotParameters plotParameters,
			final String userId) {
		super(userId);
		this.plot = plot;
		this.experiments = experiments;
		this.plotParameters = plotParameters;
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
		SerializedChromosomeArrayDataGetter dataGetter =
			jobServices.getIoService().getSerializedChromosomeArrayDataGetter();
		LOGGER.info("Plot job starting for user "
				+ this.getUserId());
		try {
			jobServices.getPlotService().plotExperiments(this.plot,
					this.experiments, this.plotParameters,
					cart, dataGetter);
			sDao.update(cart);
		} catch (Exception e) {
			this.setTerminationMessage("Failed: " + e.getMessage());
			LOGGER.info("Plot job failed for user " + this.getUserId());
			LOGGER.info(e);
		}
		LOGGER.info("Analysis job completed for user "
				+ this.getUserId());
	}
}
