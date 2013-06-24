/*
$Revision: 1.3 $
$Date: 2007-07-18 21:42:48 $


*/

package org.rti.webgenome.webui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.SessionTimeoutException;

/**
 * Tag used to filter out page content that should
 * only be displayed when user is logged in
 * and in stand-alone mode.
 * @author dhall
 *
 */
public class OnlyIfLoggedInAndStandAloneModeTag extends TagSupport {

	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	
	/**
	 * Do after start tag parsed.
	 * @throws JspException if anything goes wrong.
	 * @return Return value
	 */
	@Override
	public final int doStartTag() throws JspException {
		int rVal = TagSupport.SKIP_BODY;
		try {
			SessionMode mode =
				org.rti.webgenome.webui.util.PageContext.getSessionMode(
					(HttpServletRequest) pageContext.getRequest());
			Principal principal =
				org.rti.webgenome.webui.util.PageContext.getPrincipal(
						(HttpServletRequest) pageContext.getRequest());
			if (mode == SessionMode.STAND_ALONE && principal != null) {
				rVal = TagSupport.EVAL_BODY_INCLUDE;
			}
		} catch (SessionTimeoutException e) {
			rVal = TagSupport.SKIP_BODY;
		}
		return rVal;
	}
}
