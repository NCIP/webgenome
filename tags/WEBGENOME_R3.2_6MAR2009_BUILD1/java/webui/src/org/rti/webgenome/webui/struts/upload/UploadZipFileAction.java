/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2008-02-22 18:24:43 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.ZipFileMetaData;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Uploads a ZIP file containing individual data files
 * to server.
 * @author dhall
 *
 */
public class UploadZipFileAction extends BaseAction {
	
	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		AttachDataForm adForm = (AttachDataForm) form;
		
		// Get file format
		RectangularTextFileFormat format =
			RectangularTextFileFormat.valueOf(adForm.getFileFormat());
		
		// Stream file bytes to file on local disk
		FormFile formFile = adForm.getUploadFile();
		ZipFileMetaData meta = this.getIoService().uploadZipFile(
				formFile.getInputStream(), formFile.getFileName(),
				format);
		
		// this means the file is not if valid format
		if (!meta.getErrorFileName().equals("")){
			ActionErrors errors = new ActionErrors();
	        errors.add("global", new ActionError("error.upload.zip", meta.getErrorFileName()));
	        saveErrors(request, errors);
			return mapping.findForward("failure");
		}
		// Cache reference to data
		PageContext.setZipFileMetaData(meta, request);
		
		return mapping.findForward("success");
	}
}
