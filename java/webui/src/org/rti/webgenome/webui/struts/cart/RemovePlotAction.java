/*
$Revision: 1.7 $
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
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action to remove plot from shopping cart.
 * @author dhall
 *
 */
public final class RemovePlotAction extends BaseAction {


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
    	
    	// Get ID of plot to remove
    	long id = Long.parseLong(request.getParameter("id"));
    	
    	// If there are references to plot, deny request to delete
    	if (this.getDbService().isPlotReferenced(id)) {
    		return mapping.findForward("referenced");
    	}
    	
    	// Retrieve plot
    	Plot plot = cart.getPlot(id);
    	
    	// Remove plot
    	cart.removePlot(id);
    	if (PageContext.standAloneMode(request)) {
    		this.getDbService().updateShoppingCart(cart);
    	}
    	
    	// Get rid of image files
    	for (String fileName : plot.getAllImageFileNames()) {
    		this.getImageFileManager().deleteImageFile(fileName);
    	}
    	
    	// Get rid of plot interactivity files
    	this.getDataFileManager().deleteDataFiles(plot);
    	
    	// remove from db
    	this.dbService.deletePlot(plot);
    	
    	// TODO: Stand-alone specific actions
    	
        return mapping.findForward("success");
    }
}
