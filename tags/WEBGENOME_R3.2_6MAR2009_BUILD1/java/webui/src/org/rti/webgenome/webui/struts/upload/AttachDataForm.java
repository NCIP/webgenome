/*
$Revision: 1.1 $
$Date: 2007-08-22 20:03:57 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;

/**
 * Form used for attaching data and reporter files
 * to a data upload.
 * @author dhall
 *
 */
public class AttachDataForm extends BaseForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/** File to upload. */
    private FormFile uploadFile = null;
    
    /** Format of file. */
    private String fileFormat = RectangularTextFileFormat.CSV.toString();

    /**
     * Get file format.
     * @return File format.
     */
	public String getFileFormat() {
		return fileFormat;
	}

	/**
	 * Set file format.
	 * @param fileFormat File format.
	 */
	public void setFileFormat(final String fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * Get file to upload.
	 * @return File to upload.
	 */
	public FormFile getUploadFile() {
		return uploadFile;
	}

	/**
	 * Set file to upload.
	 * @param uploadFile File to upload.
	 */
	public void setUploadFile(final FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionErrors validate(final ActionMapping mapping,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (this.uploadFile == null) {
          errors.add("uploadFile", new ActionError("invalid.field"));  
        }
        if (errors.size() > 0) {
        	errors.add("global", new ActionError("invalid.fields"));
        }
		return errors;
	}
}
