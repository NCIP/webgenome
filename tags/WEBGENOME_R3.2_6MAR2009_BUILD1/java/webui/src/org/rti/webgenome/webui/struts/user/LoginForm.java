/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2009-01-10 22:49:45 $


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
	
	

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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
		this.name = trimSpaces(name);
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
		this.password = trimSpaces(password);
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
		if (this.name == null || this.name.trim().length() < 1) {
			e.add("name", new ActionError("invalid.field"));
		}
		
		// Password
		if (this.password == null || this.password.trim().length() < 1) {
			e.add("password", new ActionError("invalid.field"));
		}
		
		// Global message
		if (e.size() > 0) {
			e.add("global", new ActionError("invalid.fields"));
		}
		return e;
	}
}
