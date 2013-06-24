/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.service.data.DataSource;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action that initializes a session with a remote data source.
 * @author dhall
 *
 */
public class InitRemoteSessionAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(InitRemoteSessionAction.class);
	
	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		LOGGER.info("Initializing session with remote data source");
		String dataSourceKey = request.getParameter("dataSourceKey");
		DataSource dataSource = this.getDataSourcesIndex().get(dataSourceKey);
		DataSourceSession sess = new DataSourceSession(dataSource,
				this.getDataFileManager());
		PageContext.setDataSourceSession(request, sess);
		request.setAttribute("data.source.name", dataSource.getDisplayName());
		return mapping.findForward("success");
	}

}
