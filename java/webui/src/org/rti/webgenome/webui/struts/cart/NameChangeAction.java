/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Changes the name of an experiment, bioassay,
 * or plot.
 * @author dhall
 *
 */
public final class NameChangeAction extends BaseAction {

	
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
    	
    	// Get form
    	NameChangeForm ncForm = (NameChangeForm) form;
    	
    	// Change name
    	String type = ncForm.getType();
    	Long id = Long.parseLong(ncForm.getId());
    	String name = ncForm.getName();
    	if ("experiment".equals(type)) {
    		Experiment exp = cart.getExperiment(id);
    		exp.setName(name);
    	} else if ("bioassay".equals(type)) {
    		BioAssay ba = cart.getBioAssay(id);
    		ba.setName(name);
    	} else if ("plot".equals(type)) {
    		Plot plot = cart.getPlot(id);
    		plot.getPlotParameters().setPlotName(name);
    	}
    	
    	if (PageContext.standAloneMode(request)) {
    		this.getDbService().updateShoppingCart(cart);
    	}
    	
    	return mapping.findForward("success");
    }
}
