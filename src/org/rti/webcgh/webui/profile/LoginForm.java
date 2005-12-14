/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/profile/LoginForm.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/



package org.rti.webcgh.webui.profile;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;

/**
 * For associated with login/logout functionality
 */

public class LoginForm extends ActionForm {
	
	private String name = "";
	private String password = "";
	
	
	/**
	 * Setter for property name
	 * @param name User name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter for property name
	 * @return User name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Setter for property password
	 * @param password Password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	/**
	 * Getter for property password
	 * @return Password
	 */
	public String getPassword() {
		return password;
	}
	
	
	/**
	 * Reset form fields
	 * @param mapping Routing information
	 * @param request Servlet request object
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		name = "";
		password = "";
	}
	
	
	/**
	 * Validate form fields
	 * @param mapping Routing information
	 * @param request Servlet request object
	 * @return Errors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors= new ActionErrors();
		if (name == null || name.length() < 1)
			errors.add("name", new ActionError("invalid.field"));
		if (password == null || password.length() < 1)
			errors.add("password", new ActionError("invalid.field"));
		if (errors.size() > 0)
			errors.add("global", new ActionError("invalid.fields"));
		return errors;
	}

}
