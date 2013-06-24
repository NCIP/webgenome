/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.webui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.SessionTimeoutException;

/**
 * Body of tag will only be displayed if the session
 * is client mode.
 * @author dhall
 *
 */
public class OnlyIfClientModeTag extends TagSupport {
	
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
			if (mode == SessionMode.CLIENT) {
				rVal = TagSupport.EVAL_BODY_INCLUDE;
			}
		} catch (SessionTimeoutException e) {
			rVal = TagSupport.SKIP_BODY;
		}
		return rVal;
	}
}
