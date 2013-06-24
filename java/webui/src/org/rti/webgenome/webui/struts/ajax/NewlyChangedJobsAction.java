/*
$Revision: 1.4 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.ajax;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.service.job.Job;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * This class identifies any jobs whose start or completion
 * status has changed since the last call.
 * @author dhall
 *
 */
public class NewlyChangedJobsAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(NewlyChangedJobsAction.class);
	

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		LOGGER.debug("Looking for newly completed jobs");
		Collection<Job> completedJobs = new ArrayList<Job>();
		Collection<Job> startedJobs = new ArrayList<Job>();
		try {
			Principal principal = PageContext.getPrincipal(request);
			completedJobs = this.getJobManager().getNewlyCompletedJobs(
					principal.getId(), principal.getDomain());
			startedJobs = this.getJobManager().getNewlyStartedJobs(
					principal.getId(), principal.getDomain());
		} catch (SessionTimeoutException e) {
			LOGGER.info("Browser requesting completed jobs "
					+ "from an expired session");
		}
		if (completedJobs.size() > 0) {
			StringBuffer jobIds = new StringBuffer();
			int count = 0;
			for (Job job : completedJobs) {
				if (count++ > 0) {
					jobIds.append(", ");
				}
				jobIds.append(job.getId());
			}
			LOGGER.info("The following jobs have recently completed: "
					+ jobIds.toString());
		} else {
			LOGGER.debug("No newly completed jobs to report");
		}
		request.setAttribute("completedJobs", completedJobs);
		request.setAttribute("startedJobs", startedJobs);
		return mapping.findForward("success");
	}
}
