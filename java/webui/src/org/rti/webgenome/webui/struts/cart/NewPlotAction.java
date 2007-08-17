/*
$Revision: 1.11 $
$Date: 2007-08-17 19:02:16 $

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

package org.rti.webgenome.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.client.ClientDataServiceManager;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.service.job.JobManager;
import org.rti.webgenome.service.job.PlotJob;
import org.rti.webgenome.service.plot.PlotParameters;
import org.rti.webgenome.service.plot.PlotService;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * Action that creates a plot.  This action may be called
 * to generate a new <code>Plot</code> instance.  It may
 * also be called to update a <code>Plot</code> instance
 * with new parameter values--i.e, replot.
 * If session mode is CLIENT,
 * then plotting is driven by this action.  If mode is STAND_ALONE,
 * then plotting is driven by a batch processing job.
 * @author dhall
 *
 */
public final class NewPlotAction extends BaseAction {
	
	//
	//     STATICS
	//
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(NewPlotAction.class);
	
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Service for creating plots.  This service is
	 * only used directly when session is CLIENT mode
	 * or the size of the data set is sufficiently small
	 * to generate the plot immediately.
	 */
	private PlotService plotService = null;
	
	/** Manages serialized data files. */
	private DataFileManager dataFileManager = null;
	
	/**
	 * Manager for creating plots in background.
	 * This service is used when the session is not
	 * CLIENT mode and there is a large amount of
	 * data to plot.
	 */
	private JobManager jobManager = null;
	
	
	//
	//     SETTERS
	//
	
	/**
	 * Set service for creating plots.
	 * @param plotService Plot service
	 */
	public void setPlotService(final PlotService plotService) {
		this.plotService = plotService;
	}
	
	/**
	 * Set manager for serialized data files.
	 * @param dataFileManager Data file manager
	 */
	public void setDataFileManager(final DataFileManager dataFileManager) {
		this.dataFileManager = dataFileManager;
	}
	
	
	/**
	 * Set manager to perform compute-intensive plotting
	 * in background.
	 * @param jobManager Job manager
	 */
	public void setJobManager(final JobManager jobManager) {
		this.jobManager = jobManager;
	}
	
	//
	//     OVERRIDES
	//

	/**
     * Execute action.
     * @param mapping Routing information for downstream actions
     * @param form Form data
     * @param request Servlet request object
     * @param response Servlet response object
     * @return Identification of downstream action as configured in the
     * struts-config.xml file
     * @throws Exception All exceptions thrown by classes in
     * the method are passed up to a registered exception
     * handler configured in the struts-config.xml file
     */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	
    	// Setup
    	ShoppingCart cart = this.getShoppingCart(request);
    	Plot plot = null;
    	PlotParametersForm pForm = (PlotParametersForm) form;
    	PlotParameters params = pForm.getPlotParameters();
    	SessionMode mode = PageContext.getSessionMode(request);
    	
    	// Get experiments to plot.  If this action is
    	// invoked to create a new <code>Plot</code> instance,
    	// the experiment IDs come from a form.  If it is invoked
    	// to update an existing plot (i.e., re-plot),
    	// the experiments are obtained
    	// from the plot instance.
    	Collection<Experiment> experiments = null;
	    if (request.getParameter("plotId") == null) {
	    	
	    	// Retrieve selected experiments form bean.
	    	// Note, this is not the form bean configured
	    	// for this action in struts-config.xml.
	    	SelectedExperimentsForm seForm =
	    		PageContext.getSelectedExperimentsForm(request, false);
	    	if (seForm == null) {
	    		throw new SessionTimeoutException(
	    				"Could not find selected experiments");
	    	}
	    	
	    	// Get selected experiments
	    	Collection<Long> experimentIds = seForm.getSelectedExperimentIds();
	    	experiments = cart.getExperiments(experimentIds);
	    } else {
	    	
	    	// Get experiment names, which are actually IDs to
	    	// the client application
	    	Long plotId = Long.parseLong(request.getParameter("plotId"));
	    	plot = cart.getPlot(plotId);
	    	experiments = plot.getExperiments();
	    	QuantitationType qType =
	    		Experiment.getQuantitationType(experiments);
	    	
	    	// TODO: If genome intervals have not changed, do not
	    	// go back to client for data.
	    	
	    	// If client mode, get data from requested genome intervals
	    	// from client and update experiments, if necessary
	    	if (mode == SessionMode.CLIENT) {
		    	Collection<GenomeInterval> genomeIntervals =
		    		params.getGenomeIntervals();
		    	BioAssayDataConstraints[] constraints =
		    		GenomeInterval.getBioAssayDataConstraints(genomeIntervals,
		    				params.getUnits(), qType);
		    	constraints = this.findNewConstraints(constraints,
		    			experiments);
		    	if (constraints.length > 0) {
		    		LOGGER.info("Getting additional data from client");
			    	ClientDataServiceManager mgr =
			    		PageContext.getClientDataServiceManager(request);
			    	mgr.addData(experiments, constraints);
		    	}
	    	}
	    }
	    
	    ActionForward forward = null;
	    
	    // Case: Plot immediately
	    if (!ProcessingModeDecider.processInBackground(experiments,
	    		params.getGenomeIntervals(), request)) {
	    	ChromosomeArrayDataGetter getter = null;
	    	if (this.dataInMemory(request)) {
	    		getter = new InMemoryChromosomeArrayDataGetter();
	    	} else {
	    		getter = new SerializedChromosomeArrayDataGetter(
	    				this.dataFileManager);
	    	}
	    	this.plotService.plotExperiments(plot, experiments, params, cart,
	    			getter);
	    	this.persistShoppingCartChanges(cart, request);
	    	forward = mapping.findForward("non.batch");
	    	
	    // Case: Generate plot in background
	    } else {
	    	Principal principal = PageContext.getPrincipal(request);
	    	PlotJob job = new PlotJob(plot,
	    			new HashSet<Experiment>(experiments), params,
	    			principal.getName());
	    	this.jobManager.add(job);
	    }
	    
        return forward;
    }
    
    
    /**
     * Find constraints in input for which no data has
     * been obtained for given experiments.
     * @param inputConstraints Input constraints
     * @param experiments Experiments
     * @return Returns constraints for which no data
     * has been obtained in the given experiments
     */
    private BioAssayDataConstraints[] findNewConstraints(
    		final BioAssayDataConstraints[] inputConstraints,
    		final Collection<Experiment> experiments) {
    	Collection<BioAssayDataConstraints> outputConstraintsCol =
    		new ArrayList<BioAssayDataConstraints>();
    	for (int i = 0; i < inputConstraints.length; i++) {
    		BioAssayDataConstraints c = inputConstraints[i];
    		boolean allContains = true;
    		for (Experiment exp : experiments) {
    			if (!exp.isDerived() && !exp.containsData(c)) {
    				allContains = false;
    				break;
    			}
    		}
    		if (allContains) {
    			LOGGER.info("Experiments already have data from "
    					+ c.getChromosome() + ":"
    					+ c.getStartPosition() + "-"
    					+ c.getEndPosition());
    		} else {
    			outputConstraintsCol.add(c);
    		}
    	}
    	BioAssayDataConstraints[] outputConstraints =
    		new BioAssayDataConstraints[0];
    	outputConstraints =
    		outputConstraintsCol.toArray(outputConstraints);
    	return outputConstraints;
    }
}
