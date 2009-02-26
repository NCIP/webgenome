/*
$Revision: 1.1 $
$Date: 2009/01/10 22:47:22 $

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.util.Email;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Process Password Changes for a User
 *
 * @author djackman
 *
 */
public final class ChangePasswordAction extends BaseAction {


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

    	//System.out.println ( this.getClass().getName() + ".execute() entered" ) ;

    	String forwardTo = FORWARDTO_SUCCESS ; // assume everything will be fine

    	ChangePasswordForm passwordForm = (ChangePasswordForm) form;

    	Principal principal = PageContext.getPrincipal ( request ) ;
    	//
    	// Make sure current password entered matches the current recorded password (obtained from PageContext)
    	//

    	boolean passwordMatches = principal.getPassword().equals( passwordForm.getPassword() ) ;

    	if ( ! passwordMatches ) {
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("invalid.password"));
    		this.saveErrors(request, errors);

    		passwordForm.setEmail( principal.getEmail() ) ;
    		request.setAttribute ( "password", passwordForm ) ;

    		forwardTo = FORWARDTO_FAILURE ;
    	}
    	else {
    		// set Principal using new password
    		principal.setPassword( passwordForm.getNewPassword() ) ;
    		this.getSecurityMgr().update(principal);
    		PageContext.setPrincipal(request, principal ) ;

            // Add success message to request
            ActionMessages messages = new ActionMessages();
            messages.add("global", new ActionMessage("password.changed"));
            this.saveMessages(request, messages);
            ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("password.changed"));
    		this.saveErrors(request, errors);
    	}

        return mapping.findForward( forwardTo );
    }
}
