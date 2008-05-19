/*
$Revision: 1.2 $
$Date: 2008-05-19 20:11:01 $

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
 * Form backing requests for user accounts.
 * @author dhall
 *
 */
public class NewAccountForm extends BaseForm {
	
	/** Version ID for serialization. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	
	// ==============================
	//       Attributes
	// ==============================
	
	/** User name. */
	private String name = "";
	
	/** Password. */
	private String password = "";
	
	/** Password confirmation. */
	private String confirmedPassword = "";
	
	/** First Name. */
	private String firstName = "";
	
	/** Last Name. */
	private String lastName = "";
	
	/** Institution. */
	private String institution = "";
	
	/** Department. */
	private String department = "";
	
	/** Position. */
	private String position = "";
	
	/** Degree. */
	private String degree = "";
	
	/** Phone. */
	private String phone = "";
	
	/** Institution. */
	private String address = "";
	
	/** Indicates if we can contact the user for feedbacks. */
	private boolean feedbacks = false;
	
	/** Email. */
	private String email = "";
	
	
	// =============================
	//      Getters/setters
	// =============================
	
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
	
	
	/**
	 * Get password confirmation.
	 * @return Password confirmation.
	 */
	public final String getConfirmedPassword() {
		return confirmedPassword;
	}


	/**
	 * Set password confirmation.
	 * @param confirmedPassword Password confirmation.
	 */
	public final void setConfirmedPassword(final String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	
	// ==================================
	//         Overrides
	// ==================================
	
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
		this.confirmedPassword = "";
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
		/* VB commented; use email as login name 
		 * if (this.name == null || this.name.length() < 1) {
			e.add("name", new ActionError("invalid.field"));
		}*/
		
		if (this.email == null || this.email.length() < 1) {
			e.add("name", new ActionError("invalid.field"));
		}
		
		// Password
		if (this.password == null || this.password.length() < 1) {
			e.add("password", new ActionError("invalid.field"));
		}
		
		// Confirmed password
		if (this.confirmedPassword == null
				|| this.confirmedPassword.length() < 1) {
			e.add("confirmedPassword", new ActionError("invalid.field"));
		}
		
		// Global message
		if (e.size() > 0) {
			e.add("global", new ActionError("invalid.fields"));
			
		// Make sure passwords match
		} else {
			if (!this.password.equals(this.confirmedPassword)) {
				e.add("global", new ActionError("password.mismatch"));
			}
		}
		return e;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public boolean isFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(boolean feedbacks) {
		this.feedbacks = feedbacks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
