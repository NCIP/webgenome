/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/IterateOverPipelineTag.java,v $
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
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.rti.webcgh.deprecated.analytic.AnalyticOperation;

/**
 * Iterates over operations in an <code>AnalyticPipeline</code>
 */
public class IterateOverPipelineTag extends BodyTagSupport {
	
	private String name = null;
	private AnalyticOperation currentOperation = null;
	private Iterator iterator = null;
	private int count = 0;
	private boolean readOnly = false;
	private static final Logger LOGGER = Logger.getLogger(IterateOverPipelineTag.class);
	

	/**
	 * @return Current analytic operation
	 */
	public AnalyticOperation getCurrentOperation() {
		return currentOperation;
	}

	/**
	 * @return Name of bean containing pipeline
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param string Name of bean containing pipeline
	 */
	public void setName(String string) {
		name = string;
	}
	
	
	/**
	 * Getter for property count
	 * @return Iteration count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Setup for next iteration
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doAfterBody() throws JspException {
		if (! iterator.hasNext())
			return SKIP_BODY;
		currentOperation = (AnalyticOperation)iterator.next();
		count++;
		return EVAL_BODY_AGAIN;
	}
	

	/**
	 * Setup for first iteration
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		count = 0;
		
		// Make sure bean exists and is kosher
		if (name == null || name.length() < 1) {
		    String msg = "Property 'name' cannot be null string";
		    LOGGER.error(msg);
			throw new JspException(msg);
		}
		Object obj = pageContext.findAttribute(name);
		if (obj == null) {
		    String msg = "Cannot find bean '" + name + "' in any context";
		    LOGGER.error(msg);
			throw new JspException(msg);
		}
//		if (! (obj instanceof AnalyticPipeline)) {
//		    String msg = "Bean '" + name + "' not of type AnalyticPipeline";
//		    LOGGER.error(msg);
//			throw new JspException(msg);
//		}
//		AnalyticPipeline pipe = (AnalyticPipeline)obj;

		// Do not iterate if operation contains no operations
//		List ops = pipe.getOperations();
//		if (ops == null || ops.size() < 1)
//			return SKIP_BODY;
//			
//		// Set state
//		readOnly = pipe.isReadOnly();
//		iterator = ops.iterator();
//		currentOperation = (AnalyticOperation)iterator.next();
//		count++;

		return EVAL_BODY_AGAIN;
	}

	/**
	 * @return Is pipeline read only?
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param b Is pipeline read only?
	 */
	public void setReadOnly(boolean b) {
		readOnly = b;
	}
	

	/**
	 * Output buffered content
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		if (bodyContent != null) {
			try {
				JspWriter out = pageContext.getOut();
				out.print(bodyContent.getString());
			} catch (IOException e) {
			    LOGGER.error(e);
				throw new JspException("Error writing to JSP", e);
			}
		}
		return EVAL_PAGE;
	}

}
