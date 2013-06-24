/*
$Revision: 1.1 $
$Date: 2009-01-10 22:47:22 $


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
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Get An Account Form ready for Edit
 * @author djackman
 *
 */
public final class ShowEditAccountAction extends BaseAction {

	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(ShowEditAccountAction .class);

	/**
	 * {@inheritDoc}
	 */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	
    	String forwardTo = FORWARDTO_SUCCESS ;
    	
    	AccountForm accountForm = (AccountForm) form;
    	if ( accountForm == null || isEmpty ( accountForm.getEmail() ) ) {
			Principal principal = PageContext.getPrincipal ( request ) ;
			if ( principal == null ) {
	    		ActionErrors errors = new ActionErrors();
	    		errors.add("global", new ActionError("user.not.found"));
	    		this.saveErrors(request, errors);
	    		forwardTo = FORWARDTO_FAILURE ;
			}
			else {
				accountForm = new AccountForm() ;
				principal2Form ( accountForm, principal ) ;
			}
			
    	}

		request.setAttribute ( "account", accountForm ) ;
        return mapping.findForward( forwardTo );
    }
    
    //
    //    P R I V A T E    M E T H O D S
    //
    
    /**
     * Converts Principal to Account Form
     * 
     * @param form
     * @param p
     */
    private void principal2Form(AccountForm form, Principal p) {
    	form.setAddress( p.getAddress() ) ;
    	form.setDegree( p.getDegree() ) ;
    	form.setDepartment ( p.getDepartment() ) ;
    	form.setEmail( p.getEmail() ) ;
    	form.setFeedbacks( p.isFeedbacks() ) ;
    	form.setFirstName( p.getFirstName() ) ;
    	form.setLastName( p.getLastName() ) ;
    	form.setInstitution ( p.getInstitution() ) ;
    	form.setPassword( p.getPassword() ) ;
    	form.setPhone ( p.getPhone() ) ;
    	form.setPosition( p.getPosition() ) ;
    }    
}
