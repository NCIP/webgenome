/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.21 $
$Date: 2008-02-22 18:24:44 $


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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.client.ClientDataServiceManager;
import org.rti.webgenome.service.job.PlotJob;
import org.rti.webgenome.service.plot.PlotParameters;
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
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(NewPlotAction.class);


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
    	PlotParameters params = null;
    	SessionMode mode = PageContext.getSessionMode(request);
    	Long plotId = null;
    	
    	// Get experiments to plot.  If this action is
    	// invoked to create a new <code>Plot</code> instance,
    	// the experiment IDs come from a form.  If it is invoked
    	// to update an existing plot (i.e., re-plot) or to create
    	// a new plot from data in an existing plot,
    	// the experiments are obtained from the plot instance.
    	Collection<Experiment> experiments = null;
	    if (request.getParameter("plotId") == null) {
	    	
	    	// Generate new plot parameters
	    	params = pForm.getPlotParameters();
	    	
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
	    	plotId = Long.parseLong(request.getParameter("plotId"));
	    	plot = cart.getPlot(plotId);
	    	experiments = plot.getExperiments();
	    	
	    	// Recover plot parameters and set plot parameters bean fields
	    	if (request.getParameter("params.from.user") != null) {
	    		params = pForm.getPlotParameters();
	    	} else {
		    	params = plot.getPlotParameters();
		    	pForm.bulkSet(params);
	    	}
	    	
	    	// TODO: If genome intervals have not changed, do not
	    	// go back to client for data.
	    	
	    	// If client mode, get data from requested genome intervals
	    	// from client and update experiments, if necessary
	    	if (mode == SessionMode.CLIENT) {
		    	Collection<GenomeInterval> genomeIntervals =
		    		params.getGenomeIntervals();
		    	BioAssayDataConstraints[] constraints =
		    		GenomeInterval.getBioAssayDataConstraints(genomeIntervals,
		    				params.getUnits(),
		    				Experiment.getExpressionQuantitationType(
		    						experiments),
		    				Experiment.getCopyNumberQuantitationType(
		    						experiments));
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
	    
	    // If action is not to replot an existing plot, set Plot object to null
	    if (request.getParameter("diff.plot.type") != null) {
	    	plot = null;
	    }
	    
	    ActionForward forward = null;
	    
	    // Case: Plot immediately
	    if (!(ProcessingModeDecider.plotInBackground(experiments,
	    		params.getGenomeIntervals(), request)
	    		|| ProcessingModeDecider.plotInBackground(params, request))) {
	    	ChromosomeArrayDataGetter getter = null;
	    	if (this.dataInMemory(request)) {
	    		getter = new InMemoryChromosomeArrayDataGetter();
	    	} else {
	    		getter = new SerializedChromosomeArrayDataGetter(
	    				this.getDataFileManager());
	    	}
	    	plot = this.getPlotService().plotExperiments(
	    			plot, experiments, params, cart, getter);
	    	if (PageContext.standAloneMode(request)) {
	    		this.getDbService().updateShoppingCart(cart);
	    	}
	    	request.setAttribute("plot", plot);
	    	forward = mapping.findForward("non.batch");
	    	
	    // Case: Generate plot in background
	    } else {
	    	Principal principal = PageContext.getPrincipal(request);
	    	PlotJob job = new PlotJob(plotId,
	    			new HashSet<Experiment>(experiments), params,
	    			principal.getId(), principal.getDomain());
	    	this.getJobManager().add(job);
	    	ActionMessages messages = new ActionMessages();
	    	messages.add("global", new ActionMessage("plot.job"));
	    	this.saveMessages(request, messages);
	    	forward = mapping.findForward("batch");
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
