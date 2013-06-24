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
 * Print message of exception.  This exception must be attached to
 * some page context by the Struts exception handler.
 */
public final class ExceptionMessageTag extends TagSupport {
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
	/**
	 * @return Action to perform after processing
	 * @throws JspException if there is a problem writing the exception.
	 */
	public int doStartTag() throws JspException {
		
		// Get attached exception
		Exception ex = (Exception)
			pageContext.findAttribute(Attribute.EXCEPTION);
		
		if (ex != null) {
	
			// Print message
			PrintWriter out = new PrintWriter(pageContext.getOut());
			out.print("<pre>\n");
			out.print(ex.getMessage());
			out.print("</pre>");
			out.flush();
		
		}

		return SKIP_BODY;
	}
}
