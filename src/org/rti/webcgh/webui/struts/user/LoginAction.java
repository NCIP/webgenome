/*
$Revision: 1.3 $
$Date: 2006-10-09 05:10:14 $

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

package org.rti.webcgh.webui.struts.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.domain.Principal;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.mgr.SecurityMgr;
import org.rti.webcgh.webui.struts.BaseAction;
import org.rti.webcgh.webui.util.PageContext;
import org.rti.webcgh.webui.util.SessionMode;

/**
 * Logs a user into the system.
 * @author dhall
 *
 */
public final class LoginAction extends BaseAction {
	
	/** Account manager. This property should be injected. */
	private SecurityMgr securityMgr = null;
	
	/**
	 * Get account manager.
	 * @return Account manager.
	 */
	public SecurityMgr getSecurityMgr() {
		return securityMgr;
	}


	/**
	 * Set account manager.
	 * @param securityMgr Security manager for user accounts.
	 */
	public void setSecurityMgr(final SecurityMgr securityMgr) {
		this.securityMgr = securityMgr;
	}


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
    	LoginForm lf = (LoginForm) form;
    	
    	// Get principal and cache in session
    	Principal p = this.securityMgr.logIn(lf.getName(),
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
    	
    	// Get shopping cart
    	// TODO: Add DAO
    	ShoppingCart cart = new ShoppingCart(p.getName());
    	PageContext.setShoppingCart(request, cart);
    	
        return mapping.findForward("success");
    }
}
