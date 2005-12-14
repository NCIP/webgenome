/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/BeanPropsToHiddenFieldsTag.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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

import java.util.Properties;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.rti.webcgh.util.BeanUtils;


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