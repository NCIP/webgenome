/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.7 $
$Date: 2008-02-22 18:24:43 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Setup action for showing the main upload form.
 * @author dhall
 *
 */
public class ShowMainUploadFormSetupAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		UploadDataSourceProperties upload = PageContext.getUpload(request);
		request.setAttribute("upload", upload);
		Set<String> allCols = new HashSet<String>();
		if (upload.getReporterLocalFileName() == null) {
			if (upload.getDataFileMetaData() != null) {
				allCols = this.getIoService().getColumnHeadings(
						upload.getDataFileMetaData());
			}
		} else {
			allCols = this.getIoService().getColumnHeadings(
					upload.getReporterLocalFileName(),
					upload.getReporterFileFormat());
		}
		request.setAttribute("allCols", allCols);
		List<Organism> organisms = this.getDbService().loadAllOrganisms();
		request.setAttribute("organisms", organisms);
		Collection<BpUnits> units = BpUnits.getUnits();
		request.setAttribute("units", units);
		Collection<QuantitationType> qTypes =
			QuantitationType.getQuantitationTypeIndex().values();
		request.setAttribute("qTypes", qTypes);
		return mapping.findForward("success");
	}
}
