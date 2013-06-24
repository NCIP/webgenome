/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $


*/

package org.rti.webgenome.webui.struts.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;

/**
 * Form for uploading annotation data.
 * @author dhall
 *
 */
public class AnnotationUploadForm extends BaseForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	// ============================
	//     Attributes
	// ============================

	/** Form file for uploading annotation data. */
	private FormFile formFile = null;
	
	/** Organism ID. */
	private String organismId = null;
	
	// ============================
	//      Getters/setters
	// ============================
	
	/**
	 * Get form file for uploading annotation data.
	 * @return Form file for uploading annotation data.
	 */
	public final FormFile getFormFile() {
		return formFile;
	}

	/**
	 * Set form file for uploading annotation data.
	 * @param cytobandFormFile Form file for uploading annotation
	 * data.
	 */
	public final void setFormFile(final FormFile cytobandFormFile) {
		this.formFile = cytobandFormFile;
	}

	/**
	 * Get organism ID.
	 * @return Organism ID.
	 */
	public final String getOrganismId() {
		return organismId;
	}

	/**
	 * Set organism ID.
	 * @param organismId Organism ID
	 */
	public final void setOrganismId(final String organismId) {
		this.organismId = organismId;
	}
	
	
	// ====================================
	//      Overrides
	// ====================================
	
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
		
		// formFile
		String fname = this.formFile.getFileName();
		if (fname == null || fname.length() < 1) {
			errors.add("formFile", new ActionError("invalid.field"));
		}
		
		// Global message
		if (errors.size() > 0) {
			errors.add("global", new ActionError("invalid.fields"));
		}
		
		return errors;
	}
}
