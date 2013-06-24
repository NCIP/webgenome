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

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.util.SystemUtils;

/**
 * Tag that generates HTML text to display
 * the initial plot image defined in class <code>Plot</code>.
 * This tage expects an attribute 'name' that gives
 * the name of a bean in some scope of type <code>Plot</code>.
 * @author dhall
 *
 */
public class ShowPlotTag extends TagSupport {
	
	/** Sub-context of directory containing plot images. */
	private static final String SUB_CONTEXT =
		SystemUtils.getApplicationProperty("image.sub.context"); 
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/**
	 * Name of some bean of type <code>Plot</code>.
	 */
	private String name = null;
	
	/**
	 * Set name of bean of type <code>Plot</code>.
	 * @param name Name of bean.
	 */
	public final void setName(final String name) {
		this.name = name;
	}


	/**
	 * Do after start tag parsed.
	 * @throws JspException if anything goes wrong.
	 * @return Return value
	 */
	@Override
	public final int doStartTag() throws JspException {
		
		// Make sure bean is in good form
		if (this.name == null || this.name.length() < 1) {
			throw new JspException("Tag attribute '"
					+ this.name + "' missing or empty");
		}
		Object obj = pageContext.findAttribute(this.name);
		if (obj == null) {
			throw new JspException("Bean '" + this.name + "' is null");
		}
		if (!(obj instanceof Plot)) {
			throw new JspException("Bean '" + this.name
					+ "' is not of type Plot");
		}
		Plot plot = (Plot) obj;
		String fName = plot.getDefaultImageFileName();
		if (fName == null) {
			throw new JspException("Default file name missing");
		}
		
		// Write output
		String imagePath = ((HttpServletRequest)
				this.pageContext.getRequest()).getContextPath()
				+ SUB_CONTEXT + "/" + fName;
		Writer out = this.pageContext.getOut();
		try {
			out.write(
					"<img "
					+ "src=\"" + imagePath + "\" "
					+ "width=\"" + plot.getWidth() + "\""
					+ "height=\"" + plot.getHeight() + "\""
					+ ">");
		} catch (IOException e) {
			throw new JspException("Error writing to JSP output");
		}
		
		return TagSupport.SKIP_BODY;
	}
}
