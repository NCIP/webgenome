/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/BeanPropsToHiddenFieldsTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/
package org.rti.webgenome.webui.taglib;


import java.io.IOException;

import java.util.Properties;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.rti.webgenome.util.BeanUtils;


/**
 * Convert properties of form bean to hidden form fields
 */
public class BeanPropsToHiddenFieldsTag extends TagSupport {
	
	private String name = null;
	private String exclusions = null;
	private static final Logger LOGGER = Logger.getLogger(BeanPropsToHiddenFieldsTag.class);
	
	/**
	 * Setter for property name
	 * @param name Name of form bean
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter for property name
	 * @return Name of form bean
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Set list of excluded field names 
	 * @param exclusions Comma separated String list of excluded field names
	 */
	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}
	
	
	/**
	 * Get list of excluded field names 
	 * @return Comma separated String list of excluded field names
	 */
	public String getExclusions() {
		return exclusions;
	}
	
	
	/**
	 * Start tag
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		if (name != null) {
			try {
				
				// Get bean
				Object obj = pageContext.findAttribute(name);
				if (obj == null) {
				    String msg = "Unable to find bean '" + name + "' in any context";
				    LOGGER.error(msg);
					throw new JspException(msg);
				}
						
				// Get bean properties
				Properties props = BeanUtils.getSringProperties(obj);
				
				// Construct exclusion map
				Map exclusionMap = createExclusionMap();
					
				// Output properties as hidden HTML fields
				JspWriter out = pageContext.getOut();
				printHiddenTag(out, "version", String.valueOf(Math.random()));
				for (Iterator it = props.keySet().iterator(); it.hasNext();) {
					String key = (String)it.next();
					if (! exclusionMap.containsKey(key)) {
						String value = (String)props.get(key);
						printHiddenTag(out, key, value);
					}
				}
	
			} catch (IOException e) {
			    LOGGER.error(e);
				throw new JspException("Error evaluating BeanPropsToHiddenFields tag", e);
			}
		}
		return SKIP_BODY;
	}
	
	
	/**
	 * Pring a single hidden tag
	 * @param out Writer
	 * @param name Property name
	 * @param value Property value
	 * @throws IOException
	 */
	private void printHiddenTag(JspWriter out, String name, String value)
		throws IOException {
		out.println(
			"<input type=\"hidden\" name=\"" + name + 
			"\" value=\"" + value + "\">");
	}
	
	
	/**
	 * Convert comma separated String list of excluded properties into
	 * a map
	 * @return Map of excluded property names
	 */
	private Map createExclusionMap() {
		Map map = new HashMap();
		if (exclusions != null) {
			StringTokenizer tok = new StringTokenizer(exclusions, ",");
			while (tok.hasMoreTokens()) {
				String exclusion = (String)tok.nextToken();
				map.put(exclusion, exclusion);
			}
		}
		return map;
	}
}