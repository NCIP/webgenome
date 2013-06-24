/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-07-18 21:42:48 $


*/

package org.rti.webgenome.webui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.SessionTimeoutException;

/**
 * Shows tag body content only if there is a shopping
 * cart associated with the session.  If the session mode
 * is STAND_ALONE, the shopping cart will not be an attribute
 * of the session.  If there is a shopping cart associated
 * with the session, the session attribute sessionMode will have
 * been set, so that is used as a surrogate for determining
 * whether these is a shopping cart.
 * @author dhall
 *
 */
public class OnlyIfShoppingCartExistsTag extends TagSupport {

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
			if (org.rti.webgenome.webui.util.PageContext.getSessionMode(
					(HttpServletRequest) pageContext.getRequest())
					!= null) {
				rVal = TagSupport.EVAL_BODY_INCLUDE;
			}
		} catch (SessionTimeoutException e) {
			rVal = TagSupport.SKIP_BODY;
		}
		return rVal;
	}
}
