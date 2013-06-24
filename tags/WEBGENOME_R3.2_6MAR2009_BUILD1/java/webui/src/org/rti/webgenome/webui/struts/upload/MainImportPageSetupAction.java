/*
$Revision: 1.2 $
$Date: 2008-02-22 18:24:43 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Action that sets up for display of main data import page.
 * @author dhall
 *
 */
public class MainImportPageSetupAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(MainImportPageSetupAction.class);

	
	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		LOGGER.info("Getting list of configured data sources");
		request.setAttribute("data.sources.index", this.getDataSourcesIndex());
		return mapping.findForward("success");
	}
}
