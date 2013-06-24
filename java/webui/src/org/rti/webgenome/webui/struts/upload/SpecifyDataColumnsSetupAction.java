/*
$Revision: 1.2 $
$Date: 2007-08-28 17:24:13 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.UploadedData;
import org.rti.webgenome.service.io.RectangularFileReader;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Sets up for the form that enables the user to select
 * the set of data-containing columns in an uploaded
 * data file.
 * @author dhall
 *
 */
public class SpecifyDataColumnsSetupAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		
		// Get file that was uploaded
		UploadedData data = PageContext.getUploadedData(request);
		
		// Parse column headings and attach to request
		RectangularFileReader reader =
			new RectangularFileReader(data.getFile());
		reader.setDelimiter(data.getFileFormat().getDelimiter());
		List<String> columnHeadings = reader.getColumnHeadings();
		request.setAttribute("columnHeadings", columnHeadings);
		request.setAttribute("data", data);
		return mapping.findForward("success");
	}
}
