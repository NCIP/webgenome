/*
$Revision: 1.1 $
$Date: 2007-03-23 23:08:35 $

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

package org.rti.webcgh.service.job;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.rti.webcgh.analysis.AnalyticException;
import org.rti.webcgh.analysis.AnalyticOperation;
import org.rti.webcgh.analysis.AnalyticPipeline;
import org.rti.webcgh.analysis.MultiExperimentStatelessOperation;
import org.rti.webcgh.analysis.SingleBioAssayStatelessOperation;
import org.rti.webcgh.analysis.SingleExperimentStatelessOperation;
import org.rti.webcgh.analysis.UserConfigurableProperty;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Plot;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.graphics.util.ColorChooser;
import org.rti.webcgh.service.analysis.DataTransformer;
import org.rti.webcgh.service.analysis.InMemoryDataTransformer;
import org.rti.webcgh.service.plot.PlotGenerator;
import org.rti.webcgh.service.plot.PlotParameters;
import org.rti.webcgh.service.util.IdGenerator;
import org.rti.webcgh.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webcgh.util.StringUtils;
import org.rti.webcgh.webui.util.SessionMode;


/**
 * This class is designed to run as a singleton.  It delegates
 * compute-intensive jobs to service objects.  These
 * jobs may be run on the application server if they are
 * small enough, or be dispatched to a bath
 * processing server otherwise.
 * @author dhall
 *
 */
/**
 * @author dhall
 *
 */
public class JobManager {
	
	
	//
	//     STATICS
	//
	
	/** Colors for altered genome segments. */
	private static final List<Color> ALTERATION_COLORS =
		new ArrayList<Color>();
	static {
		ALTERATION_COLORS.add(Color.DARK_GRAY);
		ALTERATION_COLORS.add(Color.GRAY);
	}

	// TODO: Refactor id generators for dependency injection.
	// TODO: Move logic from ImportAction and NewPlotAction
	// into this class (look for references of IdGenerator)
	
	//
	//     ATTRIBUTES
	//
	
	/** Data transformer. */
	private final InMemoryDataTransformer inMemoryDataTransformer =
		new InMemoryDataTransformer();
	
    /** Experiment ID generator. */
    private IdGenerator experimentIdGenerator = null;
    
    /** Bioassay ID generator. */
    private IdGenerator bioAssayIdGenerator = null;
    
	/** Plot generator. */
	private PlotGenerator plotGenerator = null;

	/** In memory chromosome array data getter. */
	private InMemoryChromosomeArrayDataGetter
	inMemoryChromosomeArrayDataGetter = new InMemoryChromosomeArrayDataGetter();
	
	/** Plot ID generator. */
	private IdGenerator plotIdGenerator = new IdGenerator();

    
    //
    //     SETTERS
    //
    
    /**
     * Setter method used for dependency injection of generator
     * for bioassay IDs.
     * @param bioAssayIdGenerator Generator for bioassay IDs.
     */
    public void setBioAssayIdGenerator(
    		final IdGenerator bioAssayIdGenerator) {
		this.bioAssayIdGenerator = bioAssayIdGenerator;
	}


    /**
     * Setter method used for dependency injection of generator
     * for experiment IDs.
     * @param experimentIdGenerator Generator for experiment IDs.
     */
	public void setExperimentIdGenerator(
			final IdGenerator experimentIdGenerator) {
		this.experimentIdGenerator = experimentIdGenerator;
	}
	
	/**
	 * Set generator bean for plots for dependency injection.
	 * @param plotGenerator Plot generator bean.
	 */
	public void setPlotGenerator(final PlotGenerator plotGenerator) {
		this.plotGenerator = plotGenerator;
	}
    
    //
    //     BUSINESS METHODS
    //
	
	// TODO: refactor to use processing mode decider


	// TODO: refactor actions to take different forwards based
	// on the return value
	/**
	 * Perform given analytic operation on given experiments.
	 * The operation may be performed on either the analytic
	 * server or application server, depending on how much
	 * data are contained in the experiments.
	 * @param experiments Experiments to process
	 * @param operation Operation to perform
	 * @param colorChooser Color chooser used to assign colors
	 * to new bioassays
	 * @param sessionMode Mode of user's session
	 * @param cart Shopping cart to deposit new data into
	 * @param outputExperimentNames Map of input experiment IDs to
	 * output experiment names provided by user.  These are
	 * used to name the generated experiments
	 * @param outputBioAssayNames Map of input bioassay IDs to
	 * output bioassay names provided by user.  These are
	 * used to name the generated experiments
	 * @return {@code true} if the operation was performed
	 * (i.e., on the application server), or {@code false}
	 * if the operation was offloaded to the analytic server for
	 * batch processing.
	 * @throws AnalyticException if the analytic operation is
	 * unable to complete due to improper data
	 */
	public boolean perform(
			final Collection<Experiment> experiments,
			final AnalyticOperation operation,
			final ColorChooser colorChooser,
			final SessionMode sessionMode,
			final ShoppingCart cart,
			final Map<Long, String> outputExperimentNames,
			final Map<Long, String> outputBioAssayNames)
	throws AnalyticException {
		boolean completed = false;
    	if (sessionMode == SessionMode.CLIENT) {
    		if (operation instanceof MultiExperimentStatelessOperation) {
    			Experiment output = this.
    			inMemoryDataTransformer.
    			performMultiExperimentStatelessOperation(
    					experiments,
    					(MultiExperimentStatelessOperation) operation);
    			output.setId(this.experimentIdGenerator.nextId());
    			int count = 0;
    			for (BioAssay ba : output.getBioAssays()) {
    				ba.setId(this.bioAssayIdGenerator.nextId());
    				ba.setColor(ALTERATION_COLORS.get(count++
    						% ALTERATION_COLORS.size()));
    			}
    			cart.add(output);
    		} else {
	    		for (Experiment input : experiments) {
	    			Experiment output =
	    				this.inMemoryDataTransformer.perform(input, operation);
	    			output.setId(this.experimentIdGenerator.nextId());
	    			for (BioAssay ba : output.getBioAssays()) {
	    				ba.setId(this.bioAssayIdGenerator.nextId());
	    				ba.setColor(colorChooser.nextColor());
	    			}
	    			cart.add(output);
	    			String expName = outputExperimentNames.get(input.getId());
	    			if (expName != null) {
	    				output.setName(expName);
	    			}
	    			if (operation instanceof SingleBioAssayStatelessOperation
	    					|| (operation instanceof AnalyticPipeline
	    							&& ((AnalyticPipeline) operation).
	    							producesSingleBioAssayPerExperiment())) {
	    				for (BioAssay ba : output.getBioAssays()) {
	    					String bioAssayName =
	    						outputBioAssayNames.get(
	    								ba.getParentBioAssayId());
	    					if (bioAssayName != null) {
	    						ba.setName(bioAssayName);
	    					}
	    				}
	    			} else if (operation
	    					instanceof SingleExperimentStatelessOperation) {
	    				Collection<BioAssay> bioAssays = output.getBioAssays();
	    				if (bioAssays.size() > 0) {
	    					bioAssays.iterator().next().setName(expName);
	    				}
	    			}
	    		}
    		}
    		completed = true;
    	}
    	return completed;
	}
	
	
	/**
	 * Re-run given analytic operation on given experiment.  This
	 * operation was previously run on the experiment.  This time
	 * it may have different user-configured parameter settings.
	 * The operation will either execute immediately (i.e., on the
	 * application server) or be offloaded to the analysis server
	 * for batch processing if there are enough data.
	 * @param experiment Experiment to run through operation
	 * @param operation Operation to perform.
	 * @param mode Session mode
	 * @return {@code true} if a plot was generated (i.e., on the
	 * application server}, or {@code false} if the plot generation
	 * was offloaded to the analytic server for batch processing.
	 * @throws AnalyticException if a bad data value causes the
	 * operation to fail
	 */
	public boolean rePerform(
			final Experiment experiment,
			final AnalyticOperation operation, final SessionMode mode)
	throws AnalyticException {
		boolean completed = false;
		QuantitationType qType = experiment.getQuantitationType();
    	Collection<UserConfigurableProperty> props =
    		operation.getUserConfigurableProperties(qType);
    	if (mode == SessionMode.CLIENT) {
    		DataTransformer inMemoryDataTransformer =
    			new InMemoryDataTransformer();
    		inMemoryDataTransformer.reCompute(experiment, props);
    		completed = true;
    	}
		return completed;
	}
	
	
	
	/**
	 * Re-run analytic operation on given derived experiments.  These
	 * operations were previously run on the experiments.  This time
	 * they may have different user-configured parameter settings.
	 * The operation will either execute immediately (i.e., on the
	 * application server) or be offloaded to the analysis server
	 * for batch processing if there are enough data.
	 * @param experiments Derived experiments to run through
	 * their operation again
	 * @param mode Session mode
	 * @return {@code true} if a plot was generated (i.e., on the
	 * application server}, or {@code false} if the plot generation
	 * was offloaded to the analytic server for batch processing.
	 * @throws AnalyticException if a bad data value causes the
	 * operation to fail
	 */
	public boolean rePerform(
			final Collection<Experiment> experiments,
			final SessionMode mode)
	throws AnalyticException {
		boolean completed = false;
		if (mode == SessionMode.CLIENT) {
			DataTransformer inMemoryDataTransformer =
				new InMemoryDataTransformer();
			for (Experiment exp : experiments) {
				AnalyticOperation op = exp.getSourceAnalyticOperation();
				QuantitationType qType = exp.getQuantitationType();
				Collection<UserConfigurableProperty> props =
					op.getUserConfigurableProperties(qType);
				inMemoryDataTransformer.reCompute(exp, props);
			}
			completed = true;
		}
		return completed;
	}

	
	// TODO: refactor actions to take alternate forwards based on the
	// return value
	

	/**
	 * Plot given experiments.  If the given {@link Plot} object is
	 * {@code null}, a new plot is generated and saved to the cart.
	 * Otherwise, the given plot is re-plotted.  This operation may
	 * be perfomed immediately (i.e., on the application server),
	 * or offloaded to the analytic server for batch processing if
	 * there are enough data.
	 * @param plot A plot to re-plot.  May be null.
	 * @param experiments Experiments to plot
	 * @param params Plotting parameters
	 * @param cart A shopping cart.  If {@code plot} is null and a new
	 * plot is generated, it will be saved into this cart.
	 * @param mode User session mode
	 * @return {@code true} if a plot was generated (i.e., on the
	 * application server}, or {@code false} if the plot generation
	 * was offloaded to the analytic server for batch processing.
	 */
	public boolean plot(final Plot plot,
			final Collection<Experiment> experiments,
			final PlotParameters params, final ShoppingCart cart,
			final SessionMode mode) {
		boolean completed = false;
    	if (mode == SessionMode.CLIENT) {
    		if (plot != null) {
    			this.plotGenerator.replot(plot, experiments, params,
    					this.inMemoryChromosomeArrayDataGetter);
    		} else {
	    		Long plotId = this.plotIdGenerator.nextId();
	    		Plot newPlot = this.plotGenerator.newPlot(experiments, params,
	    				this.inMemoryChromosomeArrayDataGetter);
	    		newPlot.setId(plotId);
	    		if (StringUtils.isEmpty(params.getPlotName())) {
	    			String plotName = "Plot " + plotId.toString();
	    			params.setPlotName(plotName);
	    		}
	    		cart.add(newPlot);
    		}
    	}
		return completed;
	}
}
