/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.15 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.analysis.DataTransformer;
import org.rti.webgenome.service.job.ReRunAnalysisJob;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * This action reruns an analytic operation on
 * an experiment using new user-supplied parameters.
 * @author dhall
 *
 */
public class ReRunAnalysisAction extends BaseAnalysisAction {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response)
	throws Exception {
	
		// Get selected experiment
		ShoppingCart cart = this.getShoppingCart(request);
		Long expId = Long.parseLong(
				((AnalyticOperationParametersForm) form).getExperimentId());
		Experiment exp = cart.getExperiment(expId);
		if (exp == null) {
			throw new WebGenomeApplicationException(
					"Selected experiment no longer in workspace");
		}
		
		// Set properties of analytic operation to redo
		AnalysisDataSourceProperties props =
			(AnalysisDataSourceProperties) exp.getDataSourceProperties();
		AnalyticOperation op = props.getSourceAnalyticOperation();
		this.setUserSpecifiedParameters(op, request);
		
		ActionForward forward = null;
    	
		DataTransformer transformer = this.getDataTransformer(request);
		
		// Case: perform immediately
    	if (!ProcessingModeDecider.analysisInBackground(exp, request)) {
	    	Set<String> replacedFiles =
	    		this.getAnalysisService().rePerformAnalyticOperation(
	    			exp, op, transformer);
	    	if (PageContext.standAloneMode(request)) {
	    		this.getDbService().updateShoppingCart(cart);
	    	}
	    	this.getIoService().deleteDataFiles(replacedFiles);
	    	forward = mapping.findForward("non.batch");
	    	
	    // Case: perform in background
    	} else {
    		Principal principal = PageContext.getPrincipal(request);
    		ReRunAnalysisJob job = new ReRunAnalysisJob(exp, op,
    				principal.getId(), principal.getDomain());
    		this.getJobManager().add(job);
    		ActionMessages messages = new ActionMessages();
    		messages.add("global", new ActionMessage("analysis.job"));
    		this.saveMessages(request, messages);
    		forward = mapping.findForward("batch");
    	}
    	
		return forward;
	}
}
