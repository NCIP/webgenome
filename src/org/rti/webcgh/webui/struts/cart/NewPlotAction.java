/*
$Revision: 1.15 $
$Date: 2007-03-23 23:08:34 $

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

package org.rti.webcgh.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.domain.DataSourceProperties;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.Plot;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.client.ClientDataServiceManager;
import org.rti.webcgh.service.job.JobManager;
import org.rti.webcgh.service.plot.PlotParameters;
import org.rti.webcgh.webui.SessionTimeoutException;
import org.rti.webcgh.webui.struts.BaseAction;
import org.rti.webcgh.webui.util.PageContext;
import org.rti.webcgh.webui.util.SessionMode;
import org.rti.webgenome.client.BioAssayDataConstraints;

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
	
	/** Oversees execution of plotting job. */
	private JobManager jobManager = null;
	
	
	//
	//     SETTERS
	//
	
	/**
	 * Setter for the job manager used for dependency injection.
	 * @param jobManager Manager for compute-intensive jobs.
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
    	ShoppingCart cart = PageContext.getShoppingCart(request);
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
	    	Collection<Long> experimentIds = plot.getExperimentIds();
	    	experiments = cart.getExperiments(experimentIds);
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
    	
    	// Create plot
	    boolean plotCompleted =
	    	this.jobManager.plot(plot, experiments, params, cart, mode);
    	
	    // Determine forward
	    ActionForward forward = null;
	    if (plotCompleted) {
	    	forward = mapping.findForward("non.batch");
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
    			if (exp.getDataSourceProperties()
    					!= DataSourceProperties.ANALYTIC_OPERATION
    					&& !exp.containsData(c)) {
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
