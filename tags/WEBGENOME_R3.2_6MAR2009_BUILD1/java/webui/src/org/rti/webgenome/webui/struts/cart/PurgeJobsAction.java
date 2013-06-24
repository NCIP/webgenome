/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;


/**
 * Purge all completed jobs in users list.
 * @author dhall
 *
 */
public class PurgeJobsAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		Principal principal = PageContext.getPrincipal(request);
		this.getJobManager().purge(principal.getId(), principal.getDomain());
		return mapping.findForward("success");
	}
}
