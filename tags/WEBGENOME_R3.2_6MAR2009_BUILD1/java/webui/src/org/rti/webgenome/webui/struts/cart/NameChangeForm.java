/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/

package org.rti.webgenome.webui.struts.cart;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;

/**
 * Form for changing the name of an experiment, bioassay, or plot.
 * @author dhall
 *
 */
public class NameChangeForm extends BaseForm {

	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	// ==============================
	//      Attributes
	// ==============================
	
	/** ID of thing whose name is being changed. */
	private String id = "";
	
	/** New name. */
	private String name = "";
	
	/** Type of thing whose name is being changed. */
	private String type = "";
	
	
	// ===================================
	//      Getters/setters
	// ===================================
	
	/**
	 * Get ID of thing whose name is being changed.
	 * @return ID
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Set ID of thing whose name is being changed.
	 * @param id ID
	 */
	public final void setId(final String id) {
		this.id = id;
	}

	/**
	 * Get new name.
	 * @return New name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Set new name.
	 * @param name New name
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get type of thing whose name is being changed
	 * (e.g., experiment, bioassay, plot).
	 * @return Type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * Set type of thing whose name is being changed
	 * (e.g., experiment, bioassay, plot).
	 * @param type Type
	 */
	public final void setType(final String type) {
		this.type = type;
	}
	
	
	// =====================================
	//         Overrides
	// =====================================
	
	/**
	 * Reset form values.
	 * @param actionMappings Action mappings
	 * @param request Servlet request
	 */
	@Override
	public final void reset(final ActionMapping actionMappings,
			final HttpServletRequest request) {
		this.id = "";
		this.name = "";
		this.type = "";
	}
	
	
	/**
	 * Validate form fields.
	 * @param actionMappings Action mappings.
	 * @param request Servlet request.
	 * @return Action errors
	 */
	@Override
	public final ActionErrors validate(final ActionMapping actionMappings,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (this.name == null) {
			errors.add("name", new ActionError("invalid.field"));
		} else {
			boolean valid = true;
			for (int i = 0; i < this.name.length() && valid; i++) {
				if (!this.validCharacter(this.name.charAt(i))) {
					valid = false;
				}
			}
			if (!valid) {
				errors.add("name", new ActionError("invalid.field"));
			}
		}
		return errors;
	}
	
	
	/**
	 * Is given character valid?
	 * @param c A character
	 * @return T/F
	 */
	private boolean validCharacter(final char c) {
		return c != '&' && c != '=' && c != '#' && c!= '\''; 
	}
}
