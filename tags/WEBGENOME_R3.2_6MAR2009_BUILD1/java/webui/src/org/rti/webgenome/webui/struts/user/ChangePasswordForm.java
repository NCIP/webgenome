/*
$Revision: 1.1 $
$Date: 2009/01/10 22:47:22 $


*/

package org.rti.webgenome.webui.struts.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Form bean backing change password page.
 * @author djackman
 *
 */
public class ChangePasswordForm extends BaseForm {

	/** Version ID for serialization. */
	private static final long serialVersionUID =
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	// =====================
	//    Attributes
	// =====================
	
	/** Email */
	private String email = "" ;

	/** Password. */
	private String password = "";

	/** New Password. */
	private String newPassword = "";

	/** Confirm New Password. */
	private String confirmNewPassword = "";


	// ===========================
	//        Getters/setters
	// ===========================



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public final String getEmail () { return this.email; }
	public final void   setEmail ( String email ) { this.email = trimSpaces ( email ) ; }


	/**
	 * Get password.
	 * @return Password.
	 */
	public final String getPassword() { return password; }


	/**
	 * Set password.
	 * @param password Password.
	 */
	public final void setPassword(final String password) { this.password = trimSpaces(password); }


	/**
	 * Get New Password.
	 * @return New Password.
	 */
	public final String getNewPassword() { return newPassword; }


	/**
	 * Set New Password.
	 * @param password Password.
	 */
	public final void setNewPassword(final String newPassword) { this.newPassword = trimSpaces ( newPassword ) ; }

	/**
	 * Get Confirm New Password.
	 * @return Confirm New Password.
	 */
	public final String getConfirmNewPassword() { return confirmNewPassword; }


	/**
	 * Set Confirm New Password.
	 * @param ConfirmNewPassword
	 */
	public final void setConfirmNewPassword(final String confirmNewPassword) { this.confirmNewPassword = trimSpaces ( confirmNewPassword ); }


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
		this.password = "";
		this.newPassword = "" ;
		this.confirmNewPassword = "" ;
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

		// Password
		if ( isEmpty ( this.password ) ) {
			e.add("password", new ActionError("invalid.field"));
		}

		// New Password
		if ( isEmpty ( this.newPassword ) ) {
			e.add("newPassword", new ActionError("invalid.field"));
		}

		// Confirm New Password
		if ( isEmpty ( this.confirmNewPassword ) ) {
			e.add("confirmNewPassword", new ActionError("invalid.field"));
		}
		
		// Gloabl message
		if (e.size() > 0) {
			e.add("global", new ActionError("invalid.fields"));
		}		
		
		
		// Mismatch or same value checks
		if ( ! isEmpty ( this.newPassword ) &&
		     ! isEmpty ( this.confirmNewPassword ) ) {

		     if ( ! this.newPassword.equals ( this.confirmNewPassword ) ) {
		    	 e.add("global", new ActionError("password.mismatch"));
		    	 e.add("newPassword", new ActionError("invalid.field"));
		    	 e.add("confirmNewPassword", new ActionError("invalid.field"));
		     }
		}

		return e;
	}
}
