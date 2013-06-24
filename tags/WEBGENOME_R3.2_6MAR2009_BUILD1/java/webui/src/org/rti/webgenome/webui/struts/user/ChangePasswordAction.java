/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2009/01/10 22:47:22 $


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
