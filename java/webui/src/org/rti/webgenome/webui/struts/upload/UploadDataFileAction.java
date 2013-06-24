/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2008-02-22 18:24:43 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.UploadedData;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action that uploads a data file to the server for
 * subsequent processing.
 * @author dhall
 *
 */
public class UploadDataFileAction extends BaseAction {
	
	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		AttachDataForm adForm = (AttachDataForm) form;
		
		// Stream file bytes to file on local disk
		File file = this.getIoService().upload(
				adForm.getUploadFile().getInputStream());
		
		// Cache reference to data
		RectangularTextFileFormat format =
			RectangularTextFileFormat.valueOf(adForm.getFileFormat());
		UploadedData data = new UploadedData(file, format,
				adForm.getUploadFile().getFileName());
		PageContext.setUploadedData(data, request);
		
		return mapping.findForward("success");
	}

}
