/*
$Revision: 1.3 $
$Date: 2007-12-04 23:06:40 $


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
 * Action that initializes an upload of data files.
 * @author dhall
 *
 */
public class InitializeUploadAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		UploadDataSourceProperties upload =
			PageContext.getUpload(request, false);
		if (upload == null) {
			upload = new UploadDataSourceProperties();
			PageContext.setUpload(upload, request);
		}
		return mapping.findForward("success");
	}
}
