/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.service.job.Job;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Setup action for showing list of a user's
 * compute jobs.
 * @author dhall
 *
 */
public class ShowJobsSetupAction extends BaseAction {

	
	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		
		// Get list of users jobs and attach to request
		Principal principal = PageContext.getPrincipal(request);
		Collection<Job> jobs = this.getJobManager().getJobs(principal.getId(), principal.getDomain());
		request.setAttribute("jobs", jobs);
		
		return mapping.findForward("success");
	}
}
