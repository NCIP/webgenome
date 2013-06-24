/*
$Revision: 1.4 $
$Date: 2007-09-08 22:27:24 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticOperationFactory;
import org.rti.webgenome.analysis.AnalyticPipeline;
import org.rti.webgenome.analysis.MultiExperimentStatelessOperation;
import org.rti.webgenome.analysis.SingleExperimentStatelessOperation;
import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Setup for JSP that enables user to set analytic operation.
 * parameters
 * @author dhall
 *
 */
public final class AnalysisParamsSetupAction extends BaseAction {
	
	/** Analytic operation factory. */
	private final AnalyticOperationFactory analyticOperationFactory =
		new AnalyticOperationFactory();
	
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
    	ShoppingCart cart = this.getShoppingCart(request);
    	
    	// Get selected experiments
    	SelectedExperimentsForm seForm =
    		PageContext.getSelectedExperimentsForm(request, false);
    	if (seForm == null) {
    		throw new SessionTimeoutException(
    				"Could not find selected experiments");
    	}
    	Collection<Long> ids = seForm.getSelectedExperimentIds();
    	Collection<Experiment> experiments = cart.getExperiments(ids);
    	
    	// Determine quantitation types
    	Collection<QuantitationType> qTypes = new ArrayList<QuantitationType>();
    	QuantitationType qType =
    		Experiment.getCopyNumberQuantitationType(experiments);
    	if (qType != null) {
    		qTypes.add(qType);
    	}
    	qType = Experiment.getExpressionQuantitationType(experiments);
    	if (qType != null) {
    		qTypes.add(qType);
    	}
    	
    	// Get instance of analytic operation and attach to request
    	AnalyticOperationParametersForm aForm =
    		(AnalyticOperationParametersForm) form;
    	String opKey = aForm.getOperationKey();
    	AnalyticOperation op =
    		this.analyticOperationFactory.newAnalyticOperation(opKey);
    	request.setAttribute("op", op);
    	
    	// Get user configurable parameter characteristics and
    	// attach to request
    	List<UserConfigurableProperty> props =
    		op.getUserConfigurableProperties(qTypes);
    	request.setAttribute("props", props);
    	
    	// Get selected experiments and attach to request.
    	// This is done so that the downstream JSP can create
    	// a form enabling the user to set output experiment
    	// and bioassay names.
    	// First, retrieve selected experiments form bean.
    	// Note, this is not the form bean configured
    	// for this action in struts-config.xml.  If
    	// the operation is of type
    	// MultiExperimentToNonArrayDataAnalyticOperation,
    	// none of this will be done since the user does not
    	// set output names.
    	if (!(op instanceof MultiExperimentStatelessOperation)) {
	    	request.setAttribute("experiments", experiments);
	    	
	    	// Determine if there will be a single bioassay or multiple
	    	// produced per experiment and set an attribute
	    	if (op instanceof SingleExperimentStatelessOperation
	    			|| (op instanceof AnalyticPipeline
	    				&& ((AnalyticPipeline) op).
	    				producesSingleBioAssayPerExperiment())) {
	    		request.setAttribute("singleBioAssay", "true");
	    	}
    	}
    	
    	return mapping.findForward("success");
    }

}
