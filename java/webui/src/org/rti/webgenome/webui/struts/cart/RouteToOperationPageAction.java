/*
$Revision: 1.4 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Determines which operation a user has selected
 * to perform on selected experiments and forwards
 * to the appropriate action.  Possible operations
 * are creating a new plot and performing an analytic
 * operation on data.
 * @author dhall
 *
 */
public final class RouteToOperationPageAction extends BaseAction {

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
    	SelectedExperimentsForm seForm = (SelectedExperimentsForm) form;
    	
    	// Make sure selected experiments not of different
		// quantitation types
		ShoppingCart cart = this.getShoppingCart(request);
		Collection<Experiment> exps = cart.getExperiments(
				seForm.getSelectedExperimentIds());
		QuantitationType qType = null;
		QuantitationType expressionQType = null;
		boolean mixedTypes = false;
		for (Experiment exp : exps) {
			QuantitationType eqt = exp.getQuantitationType();
			if (eqt.isExpressionData()) {
				if (expressionQType == null) {
					expressionQType = eqt;
				} else if (expressionQType != eqt) {
					mixedTypes = true;
				}
			}
			if (!eqt.isExpressionData()) {
				if (qType == null) {
					qType = eqt;
				} else if (qType != eqt) {
					mixedTypes = true;
				}
			}
			
			if (mixedTypes) {
				ActionErrors errors = new ActionErrors();
				errors.add("global",
						new ActionError("mixed.quantitation.types"));
				this.saveErrors(request, errors);
				return mapping.findForward("error");
			}
		}
    	
    	// Recover which operation the user has selected
    	String operation = seForm.getOperation();
    	
    	// Determine forward
    	ActionForward forward = null;
    	if ("plot".equals(operation)) {
    		forward = mapping.findForward("plot");
    	} else if ("analysis".equals(operation)) {
    		Collection<Long> ids = seForm.getSelectedExperimentIds();
    		for (Long id : ids) {
    			Experiment exp = cart.getExperiment(id);
    			if (exp.isTerminal()) {
    				ActionErrors errors = new ActionErrors();
    				errors.add("global",
    						new ActionError("terminal.experiment"));
    				this.saveErrors(request, errors);
    				return mapping.findForward("cart");
    			}
    		}
    		forward = mapping.findForward("analysis");
    	} else if ("import".equals(operation)) {
    		forward = mapping.findForward("import");
    	}
    	
    	return forward;
    }
}
