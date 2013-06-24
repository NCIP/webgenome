/*
$Revision: 1.5 $
$Date: 2007-12-17 18:49:04 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Setup action for displaying a plot.
 * @author dhall
 *
 */
public final class ShowPlotSetupAction extends BaseAction {
	
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
    	
    	// Get plot and attach to request, if not there already.
    	// It will be there already if this action was invoked
    	// NewPlotAction in non-batch mode.
    	Plot plot = (Plot) request.getAttribute("plot");
    	if (plot == null) {
    	
	    	// Retrieve plot ID.  Look first at request, then parameters
	    	Long plotId = null;
	    	String plotIdStr = request.getParameter("plotId");
	    	if (plotIdStr != null) {
	    		plotId = Long.parseLong(plotIdStr);
	    	}
	    	
	    	// Retrieve plot from shopping cart
	    	ShoppingCart cart = this.getShoppingCart(request);
	    	if (plotId != null) {
	    		plot = cart.getPlot(plotId);
	    	} else {
	    		plot = cart.getLastPlotIn();
	    	}
    	
	    	// Attach plot to request
	    	request.setAttribute("plot", plot);
    	}
    	
    	// See if any plotted experiments were derived from
    	// analytic operations.  If so, plant an attribute
    	// to be used by JSP to enable the user to adjust
    	// analytic parameters
    	Collection<Experiment> experiments = plot.getExperiments();
    	for (Experiment exp : experiments) {
    		if (exp != null) {
    			if (exp.isDerived()) {
    				AnalysisDataSourceProperties props =
    					(AnalysisDataSourceProperties)
    					exp.getDataSourceProperties();
    				Collection userParams =
    					props.getUserConfigurableProperties();
    				if (userParams != null && userParams.size() > 0) {
    					request.setAttribute("analysis.params", "1");
    				}
    				break;
    			}
    		}
    	}
    	
    	return mapping.findForward("success");
    }

}
