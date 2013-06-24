/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $


*/


package org.rti.webgenome.webui.taglib;


import java.io.PrintWriter;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.util.Attribute;

/**
 * Print stack trace associated with exception.
 * This exception must be attached to
 * some page context by the Struts exception handler.
 */
public final class ExceptionStackTraceTag extends TagSupport {
    
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
	/**
	 * Start tag.
	 * @return Action to perform after processing
	 * @throws JspException If an error occurs
	 */
	public int doStartTag() throws JspException {
		
		// Get attached exception
		Exception ex = (Exception)
			pageContext.findAttribute(Attribute.EXCEPTION);
		
		if (ex != null) {
	
			// Print stack trace
			PrintWriter out = new PrintWriter(pageContext.getOut());
			out.print("<pre>\n");
			ex.printStackTrace(out);
			out.print("</pre>");
			out.flush();
		
		}

		return SKIP_BODY;
	}
}
