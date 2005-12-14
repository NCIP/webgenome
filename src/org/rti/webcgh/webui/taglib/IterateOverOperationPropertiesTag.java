/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/IterateOverOperationPropertiesTag.java,v $
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.rti.webcgh.analytic.AnalyticOperation;
import org.rti.webcgh.webui.util.AnalyticOperationDisplayProperties;
import org.rti.webcgh.webui.util.AnalyticOperationParameterDisplayProperties;

/**
 * Iterate over properties of an analytic property.  Meant to be nested
 * within IterateOverPipelineTag tags.
 */
public class IterateOverOperationPropertiesTag extends PipelineBodyTag {
	
	private AnalyticOperationParameterDisplayProperties[] props = null;
	private AnalyticOperationParameterDisplayProperties currentProperty = null;
	private int propIdx = 0;
	private static final Logger LOGGER = Logger.getLogger(IterateOverOperationPropertiesTag.class);
	

	/**
	 * Set up for next iteration
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doAfterBody() throws JspException {
	    if (this.props == null || this.propIdx >= this.props.length)
			return SKIP_BODY;
	    this.currentProperty = this.props[this.propIdx++];
		return EVAL_BODY_AGAIN;
	}

	/**
	 * Set up for first iteration
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
	    super.doStartTag();
	    this.propIdx = 0;
	    
		if (this.bodyContent != null)
			this.bodyContent = null;
		
		// Get current operation
		IterateOverPipelineTag parent = (IterateOverPipelineTag)
			TagUtils.getParentTag(this, IterateOverPipelineTag.class);
		AnalyticOperation op = parent.getCurrentOperation();
		
		// Set state
		AnalyticOperationDisplayProperties displayProps = 
		    this.analyticOperationUIHelper.getExactAnalyticOperationDisplayProperties(op.getClass());
		this.props = displayProps.getParameterDisplayProperties();
		if (this.props == null || this.propIdx >= this.props.length)
			return SKIP_BODY;
		this.currentProperty = this.props[this.propIdx++];
		
		return EVAL_BODY_AGAIN;
	}
	
	
	/**
	 * Output buffered content
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		try {
			if (bodyContent != null) {
				JspWriter out = pageContext.getOut();
				out.print(bodyContent.getString());
			}
		} catch (IOException e) {
		    LOGGER.error(e);
			throw new JspException("Error writing to JSP", e);
		}
		return EVAL_PAGE;
	}



    /**
     * @return Returns the currentProperty.
     */
    public AnalyticOperationParameterDisplayProperties getCurrentProperty() {
        return currentProperty;
    }
}
