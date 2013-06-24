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

import gov.nih.nci.caarray.domain.project.Experiment;

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
import org.rti.webgenome.service.client.CaArrayClient;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Action that fetches a list of experiments in a remote system
 * that the user can access given his or her credentials.
 * @author dhall
 *
 */
public class FetchCaArrayExperimentListAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(FetchCaArrayExperimentListAction.class);

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		LOGGER.info("Fetching caArray experiment list...");
		CaArrayClient client = PageContext.getCaArrayClient(request);
		Collection<Experiment> experimentList = PageContext.getCaArrayExperimentList(request);
		String refresh = request.getParameter("refresh");
		
		if (experimentList == null || refresh != null){
				experimentList = client.getExperimentsWithSamples();
				PageContext.setCaArrayExperimentList(experimentList, request);
		}
		
		
		
		return mapping.findForward("success");
	}
}
