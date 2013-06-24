/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/SysadminTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/
package org.rti.webgenome.webui.taglib;

import java.io.PrintWriter;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.rti.webgenome.util.SystemUtils;


/**
 * Custom tag that displays which if any data sets in the data cart
 * have no data
 */
public class SysadminTag extends TagSupport {
    
    private static final Logger LOGGER = Logger.getLogger(SysadminTag.class);
	
	/**
	 * Start tag
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		
	    // Get email of administrator
		String address = SystemUtils.getApplicationProperty("sysadmin.email");

		// Print stack trace
		PrintWriter out = new PrintWriter(pageContext.getOut());
		out.print("<a href=\"mailto:" + address + "\">" + address + "</a>");
		out.flush();

		return SKIP_BODY;
	}
}