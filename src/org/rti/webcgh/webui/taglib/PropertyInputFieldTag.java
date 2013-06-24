/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/PropertyInputFieldTag.java,v $
$Revision: 1.2 $
$Date: 2006-10-21 05:34:39 $

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
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.rti.webcgh.deprecated.analytic.AnalyticOperation;
import org.rti.webcgh.webui.util.AnalyticOperationParameterDisplayProperties;
import org.rti.webcgh.webui.util.AnalyticOperationParameterValueDisplayProperties;

/**
 * Prints HTML input field for current operation property.
 * Meant to be nested within IterateOverOperationPropertiesTag tags.
 */
public class PropertyInputFieldTag extends PipelineTag {

	private String size = null;
	private static final Logger LOGGER = Logger.getLogger(PropertyInputFieldTag.class);

	/**
	 * Start tag
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		
		// Get ancestor tags
		IterateOverOperationPropertiesTag parent = (IterateOverOperationPropertiesTag)
			TagUtils.getParentTag(this, IterateOverOperationPropertiesTag.class);
		IterateOverPipelineTag grandparent = (IterateOverPipelineTag)
			TagUtils.getParentTag(parent, IterateOverPipelineTag.class);
		
		// Get operation and property
		AnalyticOperation op = grandparent.getCurrentOperation();
		AnalyticOperationParameterDisplayProperties prop = parent.getCurrentProperty();
		
		// Get property value
		Object value = null;
		try {
			PropertyUtilsBean pub = new PropertyUtilsBean();
			value = pub.getProperty(op, prop.getParameterName());
		} catch (Exception e) {
		    LOGGER.error(e);
			throw new JspException("Error getting property '" + prop + "'", e);
		}
		
		// Get optional values
		AnalyticOperationParameterValueDisplayProperties[] props =
		    prop.getOptionalValueProperties();
		
		// Write output
		try {
			JspWriter out = pageContext.getOut();
			if (props == null || props.length < 1)
				textField(out, prop, value, grandparent.getCount(), grandparent.isReadOnly());
			else {
				selectField(out, prop, props, value, grandparent.getCount(), grandparent.isReadOnly());
			}
		} catch (IOException e) {
		    LOGGER.error(e);
			throw new JspException("Error writing to JSP", e);
		}
		
		return super.doStartTag();
	}
	
	
	private void textField
	(
		JspWriter out, AnalyticOperationParameterDisplayProperties property, Object value, 
		int count, boolean readOnly
	) throws IOException {
		out.print("<input type=\"text\" size=\"" + size + "\" name=\"" +
			property.getParameterName() + "-" + count + "\" value=\"" + value + "\"");
		if (readOnly)
			out.print(" readonly=\"true\"");
		out.print(">");
	}
	
	
	private void selectField
	(
		JspWriter out, AnalyticOperationParameterDisplayProperties paramProps,
		AnalyticOperationParameterValueDisplayProperties[] optionProps, 
		Object value, int count, boolean readOnly
	) throws IOException {
		String valueClassName = "";
		if (value != null)
			valueClassName = value.getClass().getName();
		out.print("<select name=\"" + paramProps.getParameterName() + "-" + count + "\"");
		if (readOnly)
			out.print(" disabled=\"true\"");
		out.print(">");
		for (int i = 0; i < optionProps.length; i++) {
		    AnalyticOperationParameterValueDisplayProperties optionProp = optionProps[i];
			String propValue = optionProp.getValue();
			out.print("<option value=\"" + propValue.toString() + "\"");
			if (valueClassName.equals(propValue))
				out.print(" selected");
			out.print(">" + optionProp.getDisplayName() + "</option>");
		}
	}

	/**
	 * @return Size of input field
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param string Size of input field
	 */
	public void setSize(String string) {
		size = string;
	}

}
