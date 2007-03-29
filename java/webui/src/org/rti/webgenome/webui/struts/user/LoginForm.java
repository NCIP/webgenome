/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $

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

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;

/**
 * Form bean backing login screen.
 * @author dhall
 *
 */
public class LoginForm extends BaseForm {

	/** Version ID for serialization. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	// =====================
	//    Attributes
	// =====================
	
	/** User name. */
	private String name = "";
	
	/** Password. */
	private String password = "";
	
	// ===========================
	//        Getters/setters
	// ===========================
	
	/**
	 * Get user name.
	 * @return User name.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Set user name.
	 * @param name User name.
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get password.
	 * @return Password.
	 */
	public final String getPassword() {
		return password;
	}

	
	/**
	 * Set password.
	 * @param password Password.
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}
	
	// ===============================
	//       Overrides
	// ===============================

	/**
	 * Reset state of this form.
	 * @param actionMapping Action mapping
	 * @param request Servlet request
	 */
	@Override
	public final void reset(final ActionMapping actionMapping,
			final HttpServletRequest request) {
		this.name = "";
		this.password = "";
	}

	/**
	 * Validate form contents.
	 * @param actionMapping Action mapping
	 * @param request Servlet request
	 * @return Action errors.  This will be empty
	 * if there are no errors.
	 */
	@Override
	public final ActionErrors validate(final ActionMapping actionMapping,
			final HttpServletRequest request) {
		ActionErrors e = new ActionErrors();
		
		// Name
		if (this.name == null || this.name.length() < 1) {
			e.add("name", new ActionError("invalid.field"));
		}
		
		// Password
		if (this.password == null || this.password.length() < 1) {
			e.add("password", new ActionError("invalid.field"));
		}
		
		// Gloabl message
		if (e.size() > 0) {
			e.add("global", new ActionError("invalid.fields"));
		}
		return e;
	}
}
