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

import java.io.File;
import java.util.Collection;

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
import org.rti.webgenome.service.job.Job;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Process an edit request for a registered user.
 * @author djackman
 *
 */
public final class EditAccountAction extends BaseAction {

	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(EditAccountAction .class);
	
	/**
	 * {@inheritDoc}
	 */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	
    	String forwardTo = FORWARDTO_SUCCESS ; // assume everything will be fine

    	AccountForm aForm = (AccountForm) form;
    	
    	Principal principal = PageContext.getPrincipal ( request ) ;
    	String currentEmail  = principal.getEmail() ;

    	//
    	//    C H E C K    I F    U S E R    E X I S T S
    	//
    	
    	boolean passwordMatches = principal.getPassword().equals( aForm.getPassword() ) ;
    	
    	if ( ! passwordMatches ) {
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("invalid.password"));
    		this.saveErrors(request, errors);
    		forwardTo = FORWARDTO_FAILURE ;
    	}
    	else {
    		
    		//
    		//    E X I S T I N G    U S E R    F O U N D
    		//
 
    		//    Check if email address  has changed
    		if ( ! currentEmail.equalsIgnoreCase( aForm.getEmail() ) ) {
    			
    			// User has changed their email address, so we need to check whether an account with the
    			// new email address already exists
    			
	        	if ( this.getSecurityMgr().accountExists( aForm.getEmail() ) ) {
	        		ActionErrors errors = new ActionErrors();
	        		errors.add("global", new ActionError("account.email.already.exists", aForm.getEmail() ));
	        		aForm.setEmail( currentEmail ) ;
	        		this.saveErrors(request, errors);
	        		forwardTo = FORWARDTO_FAILURE ;
	        	}
	        	else
	    			LOGGER.info( "Account name changed from [" + currentEmail+ "] to [" + aForm.getEmail() + "]" ) ;
    		}
    		
    		if ( ! forwardTo.equals ( FORWARDTO_FAILURE ) ) {

    			updatePrincipal ( aForm, principal ) ;

    			//
    			// Save Principal
    			//
    			updateRegistrationSettings ( request, principal, currentEmail ) ;

    			//
    			// Log user back in (TODO: Not 100% sure this is needed, but it's here anyway)
    			//

    			this.getAuthenticator().login( principal.getEmail(), principal.getPassword() );

    	    	PageContext.setPrincipal(request, principal) ;

	            // Add success message to request
	            ActionMessages messages = new ActionMessages();
	            messages.add("global", new ActionMessage("account.updated"));
	            this.saveMessages(request, messages);

    		}
    	}

    	if ( forwardTo.equals ( FORWARDTO_FAILURE ) ) {
    		request.setAttribute ( "account", aForm ) ; // preserve form data for re-display
    	}
    	
        return mapping.findForward( forwardTo ) ;
    }
    
    //
    //    P R I V A T E    M E T H O D S
    //
    
    /**
     * Converts Account Form to a Principal
     * 
     * @param form
	 * @return Principal
     */
    private void updatePrincipal (AccountForm a, Principal p ) {
    	// We don't update Id - we keep it as-is, i.e. the value populated from the db retrieve is preserved
    	p.setAddress( a.getAddress() ) ;
    	p.setDegree( a.getDegree() ) ;
    	p.setDepartment( a.getDepartment() ) ;
    	p.setEmail( a.getEmail() ) ;
    	p.setFeedbacks( a.isFeedbacks() ) ;
    	p.setFirstName( a.getFirstName() ) ;
    	p.setLastName( a.getLastName() ) ;
    	p.setInstitution( a.getInstitution() ) ;
    	// p.setPassword( a.getPassword() ) ; Password must not change, because user needs to confirm this
    	// and they do this via a separate Change Password page - i.e. not this StrutsAction.
    	p.setPhone( a.getPhone() ) ;
    	p.setPosition( a.getPosition() ) ;
    }
    
    /*
     * Take care of the updates required for a registered user.
     */
    private void updateRegistrationSettings ( HttpServletRequest request, Principal principal, String previousEmail ) {
		this.getSecurityMgr().update( principal );
    }
}
