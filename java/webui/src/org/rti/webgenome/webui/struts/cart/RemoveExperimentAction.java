/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.9 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action to remove an experiment from the shopping cart.
 * @author dhall
 *
 */
public final class RemoveExperimentAction extends BaseAction {
	
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
    	
    	// Get ID of experiment to remove
    	long id = Long.parseLong(request.getParameter("id"));
    	
    	// If experiment referenced by other objects
    	// in database, deny request to delete
    	if (this.getDbService().isExperimentReferenced(id)) {
    		return mapping.findForward("referenced");
    	}
    	
    	// Relinquish colors in experiment
    	Experiment exp = cart.getExperiment(id);
    	ColorChooser cc = cart.getBioassayColorChooser();
    	cc.relinquishColors(exp);
    	
    	// Remove experiment from cart
    	cart.removeExperiment(id);
    	
    	// Delete all serialized data files, if any
    	if (!exp.dataInMemory()) {
    		this.getIoService().deleteDataFiles(exp);
    	}
    	
    	// Update shopping cart persistent state
    	if (PageContext.standAloneMode(request)) {
    		this.getDbService().deleteArraysAndUpdateCart(exp, cart);
    	}
    	
        return mapping.findForward("success");
    }
}
