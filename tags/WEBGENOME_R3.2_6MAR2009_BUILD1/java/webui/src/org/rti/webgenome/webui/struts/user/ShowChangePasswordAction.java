/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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
 * Preparation action for showing the Change Password page.
 * @author djackman
 *
 */
public final class ShowChangePasswordAction extends BaseAction {

	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(ShowChangePasswordAction.class);

	/**
	 * {@inheritDoc}
	 */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	
		Principal principal = PageContext.getPrincipal ( request ) ;
		ChangePasswordForm passwordForm = new ChangePasswordForm() ;
		passwordForm.setEmail( principal.getEmail() ) ; 

		request.setAttribute ( "password", passwordForm ) ;
        return mapping.findForward( FORWARDTO_SUCCESS );
    }
    
    //
    //    P R I V A T E    M E T H O D S
    //
    
    // None
}
