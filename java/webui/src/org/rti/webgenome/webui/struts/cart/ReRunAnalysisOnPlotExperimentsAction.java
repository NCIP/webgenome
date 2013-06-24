/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.18 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.analysis.DataTransformer;
import org.rti.webgenome.service.job.ReRunAnalysisOnPlotExperimentsJob;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * Rerun analytic operation on all derived
 * experiments from a plot.
 * @author dhall
 *
 */
public class ReRunAnalysisOnPlotExperimentsAction
extends BaseAnalysisAction {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response)
	throws Exception {
		
		// Recover plot
		ShoppingCart cart = this.getShoppingCart(request);
		Long plotId = Long.parseLong(request.getParameter("plotId"));
		Plot plot = cart.getPlot(plotId);
		
		// Recover derived experiments
		Collection<Experiment> derivedExperiments =
			new ArrayList<Experiment>();
		Collection<Experiment> experiments = plot.getExperiments();
		for (Experiment exp : experiments) {
			if (exp == null) {
				throw new WebGenomeApplicationException(
						"One or more experiments no longer workspace");
			}
			if (exp.isDerived()) {
				derivedExperiments.add(exp);
			}
		}
		
		// Set new analytic parameters
		ActionErrors errors = 
			this.setUserSpecifiedParameters(derivedExperiments,
					request);
		if (errors != null) {
			return mapping.findForward("errors");
		}
		
		ActionForward forward = null;
		
		// Case: Process imediately
		DataTransformer transformer = this.getDataTransformer(request);
		if (!(ProcessingModeDecider.analysisInBackground(derivedExperiments,
				request) || ProcessingModeDecider.plotInBackground(
						plot.getPlotParameters(), request))
						|| ProcessingModeDecider.plotInBackground(experiments,
								plot.getPlotParameters().getGenomeIntervals(),
								request)) {
			Set<String> replacedFiles =
				this.getAnalysisService().rePerformAnalyticOperation(
					derivedExperiments, transformer);
			if (PageContext.standAloneMode(request)) {
				this.getDbService().updateShoppingCart(cart);
			}
			this.getIoService().deleteDataFiles(replacedFiles);
			forward = mapping.findForward("non.batch");
			
		// Case: Process in background
		} else {
			Principal principal = PageContext.getPrincipal(request);
			ReRunAnalysisOnPlotExperimentsJob job =
				new ReRunAnalysisOnPlotExperimentsJob(
						new HashSet<Experiment>(experiments), plotId,
						principal.getId(), principal.getDomain());
			this.getJobManager().add(job);
			ActionMessages messages = new ActionMessages();
	    	messages.add("global", new ActionMessage("plot.job"));
	    	this.saveMessages(request, messages);
			forward = mapping.findForward("batch");
		}
	
		
		return forward;
	}
}
