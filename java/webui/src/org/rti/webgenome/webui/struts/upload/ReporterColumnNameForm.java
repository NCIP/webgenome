/*
$Revision: 1.1 $
$Date: 2007-08-22 20:03:57 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;

/**
 * Form for inputting the name of a rectangular file
 * column that contains reporter names.
 * @author dhall
 *
 */
public class ReporterColumnNameForm extends BaseForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/** Name of column containing reporter names. */
	private String reporterColumnName = null;

	/**
	 * Get name of column containing reporter names.
	 * @return Column heading.
	 */
	public String getReporterColumnName() {
		return reporterColumnName;
	}

	/**
	 * Set name of column containing reporter names.
	 * @param reporterColumnName Column heading.
	 */
	public void setReporterColumnName(final String reporterColumnName) {
		this.reporterColumnName = reporterColumnName;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionErrors validate(final ActionMapping mapping,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (this.reporterColumnName == null
				|| this.reporterColumnName.length() < 1) {
          errors.add("reporterColumnName", new ActionError("invalid.field"));  
        }
        if (errors.size() > 0) {
        	errors.add("global", new ActionError("invalid.fields"));
        }
		return errors;
	}
}
