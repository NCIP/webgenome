/*
$Revision: 1.7 $
$Date: 2008-02-22 18:24:44 $


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
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Logs a user into the system.
 * @author dhall
 *
 */
public final class LoginAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(LoginAction.class);
	

	/**
	 * {@inheritDoc}
	 */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	LoginForm lf = (LoginForm) form;
    	
    	// Get principal and cache in session
    	Principal p = this.getAuthenticator().login(lf.getName(),
    			lf.getPassword());
    	LOGGER.info("User '" + lf.getName() + "' authenticated");
    	if (p == null) {
    		LOGGER.info("User '" + lf.getName()
    				+ "' could not be authenticated");
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("invalid.user"));
    		this.saveErrors(request, errors);
    		return mapping.findForward("failure");
    	}
    	PageContext.setPrincipal(request, p);
    	
    	// Set session mode
    	PageContext.setSessionMode(request, SessionMode.STAND_ALONE);
    	
    	// Instantiate shopping cart if null
    	ShoppingCart cart = this.getDbService().loadShoppingCart( p.getId(), p.getDomain());
    	if (cart == null) {
    		cart = new ShoppingCart(p.getId(), p.getDomain());
    		this.getDbService().saveShoppingCart(cart);
    	}
    	
        // TODO: Make this cleaner.
        // Initialize image file manager.  This manager needs
        // to know the absolute path to the directory containing
        // plot files with URL <DOMAIN>:<PORT>/<CONTEXT>/plots
        if (!this.getImageFileManager().isInitialized()) {
	        String absPlotPath = this.getServlet().
	        	getServletContext().getRealPath("/plots");
	        File imageDir = new File(absPlotPath);
	        this.getImageFileManager().init(imageDir);
        }
        
        // Add success message to request
        ActionMessages messages = new ActionMessages();
        messages.add("global", new ActionMessage("login.success"));
        this.saveMessages(request, messages);
    	
        return mapping.findForward("success");
    }
}
