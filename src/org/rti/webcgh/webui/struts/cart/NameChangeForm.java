/*
$Revision: 1.2 $
$Date: 2006-10-30 17:55:38 $

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

package org.rti.webcgh.webui.struts.cart;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.util.SystemUtils;
import org.rti.webcgh.webui.struts.BaseForm;

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
