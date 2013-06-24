/*
$Revision: 1.5 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.plot.PlotParameters;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Sets properties of <code>PlotParameters</code> bean
 * from parameters associated with a given plot.
 * @author dhall
 *
 */
public final class SetPlotParametersAction extends BaseAction {
	
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
    	
    	// Get shopping cart
    	ShoppingCart cart = this.getShoppingCart(request);
    	
    	// Get form bean
    	PlotParametersForm ppf = (PlotParametersForm) form;
    	
    	// Get plot parameters
    	Long plotId = Long.parseLong(request.getParameter("id"));
    	Plot plot = cart.getPlot(plotId);
    	PlotParameters params = plot.getPlotParameters();
    	
    	// Bulk set form bean properties
    	ppf.bulkSet(params);
    	    	
    	if (PageContext.standAloneMode(request)) {
    		this.getDbService().updateShoppingCart(cart);
    	}
    	
    	return mapping.findForward("success");
    }

}
