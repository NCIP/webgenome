/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/ShowHelpTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/
package org.rti.webgenome.webui.taglib;

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