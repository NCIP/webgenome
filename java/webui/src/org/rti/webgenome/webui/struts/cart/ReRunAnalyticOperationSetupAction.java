/*
$Revision: 1.7 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticOperationFactory;
import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * This action class is a set up for re-running
 * an analytic operation on a an experiment that was
 * previously derived through an analytic operation.
 * @author dhall
 *
 */
public class ReRunAnalyticOperationSetupAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		
		// Get ID of experiment on which to re-run operation
		Long expId = Long.parseLong(
				request.getParameter("experimentId"));
		
		// Get experiment from shopping cart
		ShoppingCart cart = this.getShoppingCart(request);
		Experiment exp = cart.getExperiment(expId);
		if (exp == null) {
			throw new WebGenomeApplicationException(
					"Experiment no longer in shopping cart");
		}
		
		// Retrieve analytic operation and associated
		// user configurable properties and attach to request
		AnalysisDataSourceProperties dsProps =
			(AnalysisDataSourceProperties) exp.getDataSourceProperties();
		AnalyticOperation op = dsProps.getSourceAnalyticOperation();
		Collection<QuantitationType> qTypes = new ArrayList<QuantitationType>();
		qTypes.add(exp.getQuantitationType());
		Collection<UserConfigurableProperty> props =
			op.getUserConfigurableProperties(qTypes);
		request.setAttribute("analyticOperation", op);
		request.setAttribute("userConfigurableProperties", props);
		
		// Put experiment ID and anlaytic operation key
		// on request for downstream JSP
		String opKey = AnalyticOperationFactory.getKey(op.getClass());
		request.setAttribute("experimentId", expId.toString());
		request.setAttribute("operationKey", opKey);
		
		return mapping.findForward("success");
	}
}
