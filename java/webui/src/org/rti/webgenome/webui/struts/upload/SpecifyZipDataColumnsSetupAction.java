/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-09-14 22:14:11 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.ZipFileMetaData;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Sets up for downstream JSP that enables the user to
 * select data containing-columns in uploaded data files.
 * @author dhall
 *
 */
public class SpecifyZipDataColumnsSetupAction extends BaseAction {
	
	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		
		// Get zip file metadata and attach to request
		ZipFileMetaData meta = PageContext.getZipFileMetaData(request);
		request.setAttribute("zip", meta);
		
		return mapping.findForward("success");
	}

}
