/*
$Revision: 1.2 $
$Date: 2008-11-11 18:09:06 $

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
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.util.Email;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Sends user their login credentials in the form of a Password Reminder.
 * 
 * @author vbakalov
 *
 */
public final class ForgotPasswordAction extends BaseAction {

	final static String EMAIL_SUBJECT = "WebGenome Password Reminder";
	
	
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
    	
    	NewAccountForm naf = (NewAccountForm) form;
    	
    	if ( naf.getEmail() == null || naf.getEmail().trim().equals("") ) {
    		
    		//
    		//    C H E C K    F O R    B L A N K    E M A I L    A D D R E S S
    		//
    		
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("account.email.missing.address"));
    		this.saveErrors(request, errors);
    		forwardTo = FORWARDTO_FAILURE ;
    	}
    	else {
    		
    		//
    		//    L O O K U P    E M A I L    A D D R E S S    S P E C I F I E D
    		//
	    	
	    	// use email as login name
	    	naf.setName(naf.getEmail());
	    	
	    	// retrieve Principal
	    	Principal p = getSecurityMgr().getPrincipal(naf.getEmail());
	    	
	    	// See if there is already a user with the same account name
	    	if (p == null) {
	    		ActionErrors errors = new ActionErrors();
	    		errors.add("global", new ActionError("account.email.does.not.exists"));
	    		this.saveErrors(request, errors);
	    		forwardTo = FORWARDTO_FAILURE ;
	    	}
	    	else {

	    		// compose email address
	    		String emailMessage =
	    			"A request was made for a password reminder to be sent to your email address.\n\n" + 
	    			"Your WebGenome User Name is your email address.\n\n" +
	    			"Your Password is " + p.getPassword() + ".\n\n" +
	    			"You may login to WebGenome at the following address:\n\n" +
	    			getLoginURL (request ) + "\n" ;
	    			
		    	// send user credentials to user's e-mail
		    	Email em = new Email();    	
		    	em.send(p.getEmail(), EMAIL_SUBJECT, emailMessage ) ;
		    			 
	    	}
    	}
    	
        return mapping.findForward( forwardTo );
    }
    
    //
    //    P R I V A T E    M E T H O D S
    //
    
    private String getLoginURL ( HttpServletRequest request ) {
        String scheme = request.getScheme();             // http
        String serverName = request.getServerName();     // hostname.com
        int serverPort = request.getServerPort();        // 80
        String contextPath = request.getContextPath();   // /mywebapp
        
        String returnValue = scheme + "://" + serverName ;
        
        if ( serverPort != 80 ) // append port, if not standard 80
        	returnValue += ":" + serverPort ;
        
        returnValue += contextPath + "/" + "user/login.do" ; // TODO: bit lame, having login.do hard-coded
        
        return returnValue ;
    }
    
   
}
