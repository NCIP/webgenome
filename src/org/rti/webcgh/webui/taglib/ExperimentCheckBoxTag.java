/*
$Revision: 1.1 $
$Date: 2006-10-08 01:11:27 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.webui.taglib;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webcgh.domain.Experiment;


/**
 * This custom tag created a text box that enables
 * the user to select an experiment.  The tag expects
 * that a bean with name defined by the property 'name'
 * is available in some scope.  This bean should be
 * of type experiment.  The name of the checkbox will
 * be formed in part from the experiment ID.
 * @author dhall
 *
 */
public class ExperimentCheckBoxTag extends TagSupport {
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 1;
	
	/**
	 * Name of some bean of type <code>Experiment</code>.
	 */
	private String name = null;
	
	
	/**
	 * Set name of bean of type <code>Experiment</code>.
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
		if (name == null || name.length() < 1) {
			throw new JspException("Tag attribute '"
					+ name + "' missing or empty");
		}
		Experiment exp = (Experiment) pageContext.findAttribute(name);
		if (exp == null || !(exp instanceof Experiment)) {
			throw new JspException("Attribute with name 'name' is "
					+ "null or not of type Experiment");
		}
		if (exp.getId() == null) {
			throw new JspException("Experiment ID is null");
		}
		
		// Write output
		Writer out = pageContext.getOut();
		String inputName =
			org.rti.webcgh.webui.util.PageContext.EXPERIMENT_ID_PREFIX
			+ exp.getId();
		try {
			out.write("<input type=\"checkbox\" name=\"" + inputName + "\">");
			out.flush();
		} catch (IOException e) {
			throw new JspException("Error writing page output", e);
		}
		
		return TagSupport.SKIP_BODY;
	}
}
