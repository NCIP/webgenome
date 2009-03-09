/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $

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

package org.rti.webgenome.webui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.core.PlotType;
import org.rti.webgenome.util.SystemUtils;

/**
 * Abstract base class for tags that display body only
 * for certain plot types.
 * @author dhall
 *
 */
public abstract class BasePlotTypeSwitch extends TagSupport {
	
	//
	//     STATICS
	//
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Name of bean in some context that is of type
	 * <code>PlotType</code>.
	 */
	private String plotTypeBeanName = null;

	
	//
	//     GETTERS AND SETTERS
	//
	
	/**
	 * Set name of bean in some context that is of type
	 * <code>PlotType</code>.
	 * @param plotTypeBeanName Name of bean in some context that is of type
	 * <code>PlotType</code>.
	 */
	public final void setPlotTypeBeanName(final String plotTypeBeanName) {
		this.plotTypeBeanName = plotTypeBeanName;
	}
	
	
	//
	//     HELPER METHODS
	//
	
	/**
	 * Get plot type bean referenced by
	 * property <code>plotTypeBeanName</code>.
	 * @return Plot type
	 * @throws JspException if a bean of type plot type
	 * is not found
	 */
	protected final PlotType getPlotType() throws JspException {
		
		// Make sure bean name set
		if (this.plotTypeBeanName == null
				|| this.plotTypeBeanName.length() < 1) {
			throw new JspException(
					"Plot type bean name not set properly");
		}
		
		// Retrieve bean
		Object bean = this.pageContext.findAttribute(plotTypeBeanName);
		
		// Make sure bean is not null
		if (bean == null) {
			throw new JspException(
				"Cannot find plot type bean in any scope");
		}
		
		// Make sure bean of right type
		if (!(bean instanceof PlotType)) {
			throw new JspException(
				"Bean referenced by JSP attribute 'plotTypeBeanName' "
					+ "is not of type PlotType");
		}
		
		return (PlotType) bean;
	}
}
