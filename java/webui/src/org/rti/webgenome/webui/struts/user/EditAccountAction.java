/*
$Revision: 1.1 $
$Date: 2009-01-10 22:47:22 $

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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Logs a user into the system.
 * @author dhall
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
    	
    	System.out.println ( this.getClass().getName() + " execute() entered" ) ;
    	
    	String forwardTo = FORWARDTO_SUCCESS ; // assume everything will be fine

    	AccountForm aForm = (AccountForm) form;
    	
    	Principal principal = PageContext.getPrincipal ( request ) ;
    	String currentName = principal.getName() ;

    	//
    	//    C H E C K    I F    U S E R    E X I S T S
    	//
    	
    	boolean passwordMatches = principal.getPassword().equals( aForm.getPassword() ) ;
    	System.out.println ( this.getClass().getName() + " comparing stored password [" + principal.getPassword() + "] with [" + aForm.getPassword() + "]" ) ;
    	System.out.println ( this.getClass().getName() + " current user is [" + currentName + "]" );

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
 
    		updatePrincipal ( aForm, principal ) ;
    		
    		//    Check if account name has changed
    		if ( ! currentName.equalsIgnoreCase( principal.getName() ) ) {
    			
    			// User has changed their email address, so we need to check whether an account with the
    			// new email address already exists
    			
    			System.out.println ( this.getClass().getName() + " account name changed from [" + currentName + "] to [" + principal.getName() + "]" ) ;

	        	if ( this.getSecurityMgr().accountByEmailExists( principal.getEmail() ) ) {
	        		System.out.println ( this.getClass().getName() + " this user already exists, mate" ) ;
	        		ActionErrors errors = new ActionErrors();
	        		errors.add("global", new ActionError("account.email.already.exists"));
	        		this.saveErrors(request, errors);
	        		forwardTo = FORWARDTO_FAILURE ;
	        	}
    		}
    		
    		if ( ! forwardTo.equals ( FORWARDTO_FAILURE ) ) {
    			
    			//
    			// Save information
    			//

    			this.getSecurityMgr().update( principal );
    			PageContext.setPrincipal(request, principal ) ;
    		
	            // Add success message to request
	            ActionMessages messages = new ActionMessages();
	            messages.add("global", new ActionMessage("account.updated"));
	            this.saveMessages(request, messages);
	            /*
	            ActionErrors errors = new ActionErrors();
	    		errors.add("global", new ActionError("account.updated"));
	    		this.saveErrors(request, errors);
	    		*/
	            
	            // Save form for re-display

    		}
    	}

    	if ( forwardTo.equals ( FORWARDTO_FAILURE ) ) {
    		System.out.println ( this.getClass().getName() + " preserving form" ) ;
    		request.setAttribute ( "account", aForm ) ; // preserve form data for re-display
    	}

        return mapping.findForward( forwardTo );
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
    	p.setAddress( a.getAddress() ) ;
    	p.setDegree( a.getDegree() ) ;
    	p.setDepartment( a.getDepartment() ) ;
    	p.setEmail( a.getEmail() ) ;
    	p.setName( a.getEmail() ) ; // NAME IS SET FROM EMAIL
    	p.setFeedbacks( a.isFeedbacks() ) ;
    	p.setFirstName( a.getFirstName() ) ;
    	p.setLastName( a.getLastName() ) ;
    	p.setInstitution( a.getInstitution() ) ;
    	// p.setPassword( a.getPassword() ) ; Password must not change, because user needs to confirm this
    	// and they do this via a separate Change Password page.
    	p.setPhone( a.getPhone() ) ;
    	p.setPosition( a.getPosition() ) ;
    }    
}
