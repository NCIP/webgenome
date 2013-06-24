/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2008-11-11 18:09:06 $


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
	    	//naf.setName(naf.getEmail());

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
