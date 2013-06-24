/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-10-23 16:17:18 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.client.CaArrayClient;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action that initializes an upload of data files.
 * @author dhall
 *
 */
public class InitializecaArrayUploadAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		CaArrayClient caArrayClient = PageContext.getCaArrayClient(request);
		if (caArrayClient == null) {
			return mapping.findForward("needlogin");
		}
		
		// retrieve list of experiments
		return mapping.findForward("success");
	}
}
