/*
$Revision: 1.4 $
$Date: 2007-10-10 17:47:02 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.webui.struts.user;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.io.ImageFileManager;
import org.rti.webgenome.service.session.Authenticator;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Logs a user into the system.
 * @author dhall
 *
 */
public final class LoginAction extends BaseAction {
	
	/**
	 * Authenticator of user credentials.
	 * This property should be injected.
	 */
	private Authenticator authenticator = null;
	
	/**
	 * Shopping cart data access object.  This property should
	 * be injected.
	 */
	private ShoppingCartDao shoppingCartDao = null;
	

	/**
	 * Set authenticator of user credentials.
	 * @param authenticator An authenticator
	 */
	public void setAuthenticator(final Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
	
	/**
	 * Set shoppig cart data access object.
	 * @param shoppingCartDao Shopping cart data access object.
	 */
	public void setShoppingCartDao(final ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	
	/** Image file manager. */
    private ImageFileManager imageFileManager = null;
    
    /**
     * Set image file manager.
     * @param imageFileManager Image file manager.
     */
    public void setImageFileManager(
    		final ImageFileManager imageFileManager) {
		this.imageFileManager = imageFileManager;
	}


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
    	Principal p = this.authenticator.login(lf.getName(),
    			lf.getPassword());
    	if (p == null) {
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("invalid.user"));
    		this.saveErrors(request, errors);
    		return mapping.findForward("failure");
    	}
    	PageContext.setPrincipal(request, p);
    	
    	// Set session mode
    	PageContext.setSessionMode(request, SessionMode.STAND_ALONE);
    	
    	// Instantiate shopping cart if null
    	ShoppingCart cart = this.shoppingCartDao.load(
    			p.getName(), p.getDomain());
    	if (cart == null) {
    		cart = new ShoppingCart(p.getName(), p.getDomain());
    		this.shoppingCartDao.save(cart);
    	}
    	
        // TODO: Make this cleaner.
        // Initialize image file manager.  This manager needs
        // to know the absolute path to the directory containing
        // plot files with URL <DOMAIN>:<PORT>/<CONTEXT>/plots
        if (!this.imageFileManager.isInitialized()) {
	        String absPlotPath = this.getServlet().
	        	getServletContext().getRealPath("/plots");
	        File imageDir = new File(absPlotPath);
	        this.imageFileManager.init(imageDir);
        }
    	
        return mapping.findForward("success");
    }
}
