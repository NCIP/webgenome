/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/org/rti/webcgh/webui/taglib/ShowHelpTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-27 19:42:08 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package org.rti.webcgh.webui.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;


/**
 * Display help message in new window.
 */
public class ShowHelpTag extends TagSupport {
	
	private String topic = "";
	private String helpPagePath = "";
	private String helpIconPath = "";
	private static final Logger LOGGER = Logger.getLogger(ShowHelpTag.class);
	
	
	/**
	 * Setter for property topic
	 * @param topic Help topic.  Topic must correspond
	 * to named element in help page (i.e. <a name="some_name">)
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
	/**
	 * Getter for property topic
	 * @return Help topic.  Topic must correspond
	 * to named element in help page (i.e. <a name="some_name">)
	 */
	public String getTopic() {
		return topic;
	}
	
	
	/**
	 * Setter for helpPagePath property
	 * @param path Abstract path to help page
	 */
	public void setHelpPagePath(String path) {
		this.helpPagePath = path;
	}
	
	
	/**
	 * Getter for helpPagePath property
	 * @return Abstract path to help page
	 */
	public String getHelpPagePath() {
		return helpPagePath;
	}
	
	
	/**
	 * Setter for property helpIconPath
	 * @param path Abstract path to help icon file
	 */
	public void setHelpIconPath(String path) {
		this.helpIconPath = path;
	}
	
	
	/**
	 * Getter for property helpIconPath
	 * @return Abstract path to help icon file
	 */
	public String getHelpIconPath() {
		return helpIconPath;
	}
	
	
	/**
	 * Start tag
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		try {
			
			JspWriter out = pageContext.getOut();
			String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
			String helpPage = contextPath + helpPagePath;
			String icon = contextPath + helpIconPath;
			out.print(
				"<a href=\"#\" " +
				"onclick=\"window.open('" + helpPage + "#" + topic + "'" +
				", '_blank', 'width=400, height=300, menubar=no, status=no, " +
				"scrollbars=yes, resizable=yes, toolbar=yes, location=no, directories=no')\">");
			out.print("<img src=\"" + icon + "\" border=\"0\">");
			out.print("</a>");

		} catch (IOException e) {
		    LOGGER.error(e);
			throw new JspException("Error evaluating ShowHelp tag", e);
		}
		return SKIP_BODY;
	}
}