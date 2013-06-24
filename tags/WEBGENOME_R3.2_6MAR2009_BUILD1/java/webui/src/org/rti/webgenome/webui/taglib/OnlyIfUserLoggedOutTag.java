/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $


*/

package org.rti.webgenome.webui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.SessionTimeoutException;

/**
 * Evaluate tag body only if user is logged out.
 * @author dhall
 */
public class OnlyIfUserLoggedOutTag extends TagSupport {

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
			if (org.rti.webgenome.webui.util.PageContext.getPrincipal(
					(HttpServletRequest) pageContext.getRequest())
					== null) {
				rVal = TagSupport.EVAL_BODY_INCLUDE;
			}
		} catch (SessionTimeoutException e) {
			rVal = TagSupport.EVAL_BODY_INCLUDE;
		}
		return rVal;
	}
}
