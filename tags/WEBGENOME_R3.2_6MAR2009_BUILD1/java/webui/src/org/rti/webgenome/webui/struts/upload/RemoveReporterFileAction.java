/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:43 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action that removes reporter file from upload.
 * @author dhall
 *
 */
public class RemoveReporterFileAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		UploadDataSourceProperties upload = PageContext.getUpload(request);
		String localFileName = upload.getReporterLocalFileName();
		if (localFileName != null) {
			this.getIoService().delete(localFileName);
			upload.removeRepoterFile();
		}
		return mapping.findForward("success");
	}
}
