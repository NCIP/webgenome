/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action that fetches a list of experiments in a remote system
 * that the user can access given his or her credentials.
 * @author dhall
 *
 */
public class FetchExperimentListAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(FetchExperimentListAction.class);

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		LOGGER.info("Fetching experiment list");
		DataSourceSession sess = PageContext.getDataSourceSession(request);
		Map<String, String> idsAndNames = sess.getExperimentIdsAndNames();
		request.setAttribute("ids.and.names", idsAndNames);
		Collection<Organism> organisms = this.getDbService().loadAllOrganisms();
		request.setAttribute("organisms", organisms);
		Collection<QuantitationType> qTypes =
			QuantitationType.getQuantitationTypeIndex().values();
		request.setAttribute("qTypes", qTypes);
		return mapping.findForward("success");
	}
}
