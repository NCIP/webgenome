/*
$Revision: 1.2 $
$Date: 2007-07-18 21:42:48 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Action that retrieves the available types of quantitation
 * types.
 * @author dhall
 *
 */
public final class GetQuantitationTypesAction extends BaseAction {

	
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
    	Map<String, QuantitationType> typeMap =
    		QuantitationType.getQuantitationTypeIndex();
    	Collection<QuantitationType> types = typeMap.values();
    	request.setAttribute("quantitationTypes", types);
    	return mapping.findForward("success");
    }
}
