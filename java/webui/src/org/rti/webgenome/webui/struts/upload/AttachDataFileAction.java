/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-12-17 23:29:23 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.domain.UploadedData;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action class that attached a data file to an upload.
 * @author dhall
 *
 */
public class AttachDataFileAction extends BaseAction {
	
	/**
	 * Suffix added to a data file column heading by the JSP
	 * 'specifyDataColumn.jsp' to indicate the parameter
	 * is a text box for specifying the name of the bioassay
	 * that is derived from data in the column.
	 */
	private static final String BIOASSAY_SUFFIX = "_bioassay";

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		ReporterColumnNameForm rForm = (ReporterColumnNameForm) form;
		UploadedData data = PageContext.getUploadedData(request);
		
		// Instantiate new data file meta data and add to upload
		DataFileMetaData meta = new DataFileMetaData();
		meta.setReporterNameColumnName(rForm.getReporterColumnName());
		meta.setFormat(data.getFileFormat());
		meta.setLocalFileName(data.getFile().getName());
		meta.setRemoteFileName(data.getRemoteFileName());
		UploadDataSourceProperties upload = PageContext.getUpload(request);
		upload.add(meta);
		
		// Add data column metadata to file metadata
		Enumeration<String> pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			if (pName.endsWith(BIOASSAY_SUFFIX)) {
				String fieldName = pName.substring(0,
						pName.length() - BIOASSAY_SUFFIX.length());
				String bioAssayName = request.getParameter(pName);
				if (bioAssayName == null || bioAssayName.length() < 1) {
					ActionErrors errors = new ActionErrors();
					errors.add("global",
							new ActionError("missing.bioassay.name"));
					this.saveErrors(request, errors);
					return mapping.findForward("errors");
				}
				DataColumnMetaData colMeta =
					new DataColumnMetaData(fieldName, bioAssayName);
				meta.add(colMeta);
			}
		}
		
		return mapping.findForward("success");
	}
}
