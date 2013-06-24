/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/

package org.rti.webgenome.webui.struts.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Setup action prior to displaying a color
 * selector window.
 * @author dhall
 *
 */
public final class ColorChooserSetupAction extends BaseAction {
	
	
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
    	
    	// Generate color palette and attach to request
    	String[][] palette = ColorChooser.getWebColorPalette();
    	request.setAttribute("palette", palette);
    	
    	// Recover bioassay id and attach to request
    	String id = request.getParameter("id");
    	request.setAttribute("id", id);
    	
    	return mapping.findForward("success");
    }
}
