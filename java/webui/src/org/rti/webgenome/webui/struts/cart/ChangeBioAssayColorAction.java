/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.awt.Color;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.util.ColorUtils;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Change color of a bioassay.
 * @author dhall
 *
 */
public final class ChangeBioAssayColorAction extends BaseAction {

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
    	
    	// Retrieve shopping cart
    	ShoppingCart cart = this.getShoppingCart(request);
    	
    	// Retrieve bioassay id and color
    	Long id = Long.parseLong(request.getParameter("id"));
    	String colorStr = request.getParameter("color");
    	Color color = ColorUtils.getColor(colorStr);
    	
    	// Get bioassay
    	BioAssay ba = cart.getBioAssay(id);
    	
    	// Relinquish old bioassay color
    	ColorChooser cc = cart.getBioassayColorChooser();
    	cc.decrementCount(ba.getColor());
    	
    	// Change bioassay color and register with color chooser
    	ba.setColor(color);
    	cc.incrementCount(color);
    	
    	// Persist state
    	if (PageContext.standAloneMode(request)) {
    		this.getDbService().updateShoppingCart(cart);
    	}
    	
    	return mapping.findForward("success");
    }
}
