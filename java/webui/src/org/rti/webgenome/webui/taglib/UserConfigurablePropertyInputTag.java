/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-09-08 18:28:03 $


*/

package org.rti.webgenome.webui.taglib;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.analysis.SimpleUserConfigurableProperty;
import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.analysis.UserConfigurablePropertyWithOptions;
import org.rti.webgenome.util.SystemUtils;

/**
 * Creates an HTML input element suitable for inputting
 * user configurable parameter values.
 * @author dhall
 *
 */
public final class UserConfigurablePropertyInputTag extends TagSupport {
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	
	/** Name of a bean of type <code>UserConfigurableProperty</code>. */
	private String name = null;
	
	/**
	 * Prefix prepended to input name so that downstream actions
	 * can determine which query parameters denote user
	 * configurable properties.
	 */
	private String prefix = "";
	
	
	/**
	 * Set name of a bean of type
	 * <code>UserConfigurableProperty</code>.
	 * @param name Bean name
	 */
	public void setName(final String name) {
		this.name = name;
	}


	/**
	 * Set prefix prepended to input name so that downstream actions
	 * can determine which query parameters denote user
	 * configurable properties.
	 * @param prefix Prefix
	 */
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}



	/**
	 * Do after start tag parsed.
	 * @throws JspException if anything goes wrong.
	 * @return Return value
	 */
	@Override
	public int doStartTag() throws JspException {
		
		// Get bean
		if (this.name == null) {
			throw new JspException("Name of bean missing");
		}
		Object bean = pageContext.findAttribute(this.name);
		if (bean == null) {
			throw new JspException("Cannot find bean named '"
					+ this.name + "' in any scope");
		}
		if (!(bean instanceof UserConfigurableProperty)) {
			throw new JspException("Bean '" + this.name
					+ "' is not type UserConfigurableProperty");
		}
		UserConfigurableProperty prop = (UserConfigurableProperty) bean;
		
		// Create input
		Writer out = pageContext.getOut();
		try {
			String propName = this.prefix + prop.getName();
			if (prop instanceof SimpleUserConfigurableProperty) {
				out.write("<input type=\"text\" name=\""
						+ propName + "\" value=\""
						+ prop.getCurrentValue() + "\">");
			} else if (prop instanceof UserConfigurablePropertyWithOptions) {
				out.write("<select name=\"" + propName + "\">");
				Map<String, String> opMap = (
						(UserConfigurablePropertyWithOptions) prop)
						.getOptions();
				for (String key : opMap.keySet()) {
					String value = opMap.get(key);
					out.write("<option value=\"" + key + "\"");
					if (key.equals(prop.getCurrentValue())) {
						out.write(" selected");
					}
					out.write(">" + value + "</option>");
				}
				out.write("</select>");
			}
		} catch (IOException e) {
			throw new JspException("Error writing to page");
		}
		
		return TagSupport.SKIP_BODY;
	}
}
