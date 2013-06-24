/*
$Revision: 1.2 $
$Date: 2007-08-24 21:51:57 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.domain.UploadedData;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action that attaches a reporter file to an upload.
 * @author dhall
 *
 */
public class AttachReporterFileAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		ReporterColumnNameForm rForm = (ReporterColumnNameForm) form;
		UploadDataSourceProperties upload = PageContext.getUpload(request);
		UploadedData data = PageContext.getUploadedData(request);
		upload.setReporterFile(data.getFileFormat(), data.getFile().getName(),
				data.getRemoteFileName(), rForm.getReporterColumnName());
		return mapping.findForward("success");
	}
}
