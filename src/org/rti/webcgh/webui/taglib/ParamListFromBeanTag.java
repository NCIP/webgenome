/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/ParamListFromBeanTag.java,v $
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

import java.util.Collection;
import java.util.ArrayList;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.rti.webcgh.webui.util.WebUtils;


/**
 * Writes parameters with name/values corresponding to bean properties
 */
public class ParamListFromBeanTag extends TagSupport {
	
	private String name = null;
	private String excludedField = null;
	private static final Logger LOGGER = Logger.getLogger(ParamListFromBeanTag.class);
	
	
	/**
	 * Setter for property name
	 * @param name Name of bean
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter for property name
	 * @return Name of bean
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Setter for property excluded field
	 * @param excludedField Name of field to be excluded
	 * from parameter list
	 */
	public void setExcludedField(String excludedField) {
		this.excludedField = excludedField;
	}
	
	
	/**
	 * Getter for property excluded field
	 * @return Name of field to be excluded
	 * from parameter list
	 */
	public String getExcludedField() {
		return excludedField;
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
						
				// Get param list
				Collection exclusions = null;
				if (excludedField != null) {
					exclusions = new ArrayList();
					exclusions.add(excludedField);
				}
				String params = WebUtils.paramListFromProps(obj, exclusions);
					
				// Output properties as hidden HTML fields
				JspWriter out = pageContext.getOut();
				out.print(params);
	
			} catch (IOException e) {
			    LOGGER.error(e);
				throw new JspException("Error evaluating BeanPropsToHiddenFields tag", e);
			}
		}
		return SKIP_BODY;
	}
}