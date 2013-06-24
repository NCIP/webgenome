/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-11-13 16:27:08 $


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
	
	/** Email. */
	private String email = "";
	
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
	
	
	
	// =============================
	//      Getters/setters
	// =============================
	
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
		this.email             = "";
		this.password          = "";
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
		
		// Email
		if ( isEmpty ( this.email ) || ! isValidEmail ( this.email ) )
			e.add("email", new ActionError("invalid.field"));

		// Password
		if ( isEmpty ( this.password ) )
			e.add("password", new ActionError("invalid.field"));
		
		// Confirmed Password
		if ( isEmpty ( this.confirmedPassword ))
			e.add("confirmedPassword", new ActionError("invalid.field"));
		
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
