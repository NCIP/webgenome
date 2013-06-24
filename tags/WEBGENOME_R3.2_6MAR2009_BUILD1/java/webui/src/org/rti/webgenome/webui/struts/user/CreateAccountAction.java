/*
$Revision: 1.4 $
$Date: 2008/05/19 20:11:02 $


*/

package org.rti.webgenome.webui.struts.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.struts.user.NewAccountForm;

/**
 * Creates a new user account.
 * @author dhall
 *
 */
public final class CreateAccountAction extends BaseAction {

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

    	NewAccountForm naf = (NewAccountForm) form;
    	
    	// See if there is already a user with the same account name
    	if (this.getSecurityMgr().accountExists(naf.getEmail())) {
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("account.email.already.exists", naf.getEmail() ));
    		this.saveErrors(request, errors);
    		return mapping.findForward("failure");
    	}
    	
    	Principal p = new Principal();
    	form2Principal(naf, p);
    	
    	// Create new account
    	this.getSecurityMgr().newAccount(p);
    	
    	// Add new account name to request for downstream confirmation JSP
    	request.setAttribute("account.email", naf.getEmail());
    	
        return mapping.findForward("success");
    }
    
    /**
     * Converts NewAccountForm to Principal.
     * 
     * @param form
     * @param p
     */
    private void form2Principal(NewAccountForm form, Principal p){
    	p.setAddress(form.getAddress());
    	p.setDegree(form.getDegree());
    	p.setDepartment(form.getDepartment());
    	p.setEmail(form.getEmail());
    	p.setFeedbacks(form.isFeedbacks());
    	p.setFirstName(form.getFirstName());
    	p.setInstitution(form.getInstitution());
    	p.setLastName(form.getLastName());
    	p.setFirstName(form.getFirstName());
    	p.setPassword(form.getPassword());
    	p.setPhone(form.getPhone());
    	p.setPosition(form.getPosition());
    	
    }
}
