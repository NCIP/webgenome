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
