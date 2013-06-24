/*
$Revision: 1.2 $
$Date: 2008-02-15 23:28:58 $


*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.service.data.DataSourceAccessException;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.struts.user.LoginForm;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Logs user into a remote system that contains array data
 * to import into their workspace.
 * @author dhall
 */
public class RemoteSysLoginAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(RemoteSysLoginAction.class);

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		LOGGER.info("Logging into remote system");
		LoginForm loginForm = (LoginForm) form;
		DataSourceSession sess = PageContext.getDataSourceSession(request);
		try {
			sess.loginToSelectedDataSource(loginForm.getName(),
					loginForm.getPassword());
		} catch (DataSourceAccessException e) {
			LOGGER.info("User not validated: " + loginForm.getName());
			ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("invalid.user"));
    		this.saveErrors(request, errors);
    		return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}
}
