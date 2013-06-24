/*
$Revision: 1.1 $
$Date: 2008-10-23 16:17:18 $


*/

package org.rti.webgenome.webui.struts.user;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.client.CaArrayClient;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Logs a user into the system.
 * @author dhall
 *
 */
public final class CaArrayLoginAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(CaArrayLoginAction.class);
	

	/**
	 * {@inheritDoc}
	 */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	LoginForm lf = (LoginForm) form;
    	try{
    		//initiate caArray client by specifying the caArray credentials.
    		CaArrayClient caArrayClient = new CaArrayClient(lf.getName(), lf.getPassword());
    		PageContext.setCaArrayClient(caArrayClient, request);
    	}catch(Exception e){
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("caarray.user.not.found"));
    		this.saveErrors(request, errors);
    		return mapping.findForward("failure");
    	}
    	
    	
    	
        return mapping.findForward("success");
    }
}
