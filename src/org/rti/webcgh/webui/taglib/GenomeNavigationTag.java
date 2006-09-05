/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/GenomeNavigationTag.java,v $
$Revision: 1.3 $
$Date: 2006-09-05 14:06:44 $

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


import java.util.Collection;
import java.util.ArrayList;

import java.io.IOException;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.units.BpUnits;
import org.rti.webcgh.webui.plot.PlotParamsForm;
import org.rti.webcgh.webui.util.WebUtils;

/**
 * Displays genome navigation buttons
 */
public class GenomeNavigationTag extends TagSupport {
	
	private String action = "";
	private String name = "";
	private static final Logger LOGGER = Logger.getLogger(GenomeNavigationTag.class);
	
	
	/**
	 * Setter for property action
	 * @param action Name of Struts action associated with buttons
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	
	/**
	 * Getter for property action
	 * @return Name of Struts action associated with buttons
	 */
	public String getAction() {
		return action;
	}
	
	
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
	 * Start tag
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		try {
			PlotParamsForm form = (PlotParamsForm)
				pageContext.findAttribute(name);
			if (form == null) {
			    String msg = "Unable to find bean '" + name + "' in any scope";
			    LOGGER.error(msg);
				throw new JspException(msg);
			}
				
			JspWriter out = pageContext.getOut();
			String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
			
			// Calculate jump intervals
			GenomeIntervalDto[] intervals = GenomeIntervalDto.parse(form.getGenomeIntervals(), BpUnits.getUnits(form.getUnits()));
			if (intervals == null || intervals.length < 1)
				throw new WebcghSystemException("Genome interval unspecified");
			GenomeIntervalDto interval = intervals[0];
			double startPoint = interval.getStart();
			double endPoint = interval.getEnd();
			double range = endPoint - startPoint;
			double smallStep = range / 8;
			double medStep = range / 4;
			double bigStep = range;
			double zoomOutStart = startPoint - medStep;
			if (zoomOutStart < 1.0)
				zoomOutStart = 1.0;
				
			// Get parameter list
			Collection exclusions = new ArrayList();
			exclusions.add("startPoint");
			exclusions.add("endPoint");
			String params = WebUtils.paramListFromProps(form, exclusions);
				
			// Rightward navigation buttons
			if (startPoint - bigStep > 0.0)
				printNavButton(out, contextPath, "<<<", startPoint - bigStep, 
					endPoint - bigStep, params);
			if (startPoint - medStep > 0.0)
				printNavButton(out, contextPath, "<<", startPoint - medStep, 
					endPoint - medStep, params);
			if (startPoint - smallStep > 0.0)
				printNavButton(out, contextPath, "<", startPoint - smallStep, 
					endPoint - smallStep, params);
				
			// Zoom buttons
			printNavButton(out, contextPath, "Zoom In", startPoint + medStep, 
				endPoint - medStep, params);
			printNavButton(out, contextPath, "Zoom Out", zoomOutStart, 
				endPoint + medStep, params);
			
			// Leftward navigation buttons
			printNavButton(out, contextPath, ">", startPoint + smallStep, 
				endPoint + smallStep, params);
			printNavButton(out, contextPath, ">>", startPoint + medStep, 
				endPoint + medStep, params);
			printNavButton(out, contextPath, ">>>", startPoint + bigStep, 
				endPoint + bigStep, params);

		} catch (Exception e) {
		    LOGGER.error(e);
			throw new JspException("Error evaluating GenomeNavigationTag", e);
		}
		return SKIP_BODY;
	}
	
	
	/**
	 * Print a single navigation button
	 * @param out Stream
	 * @param contextPath Servlet context path
	 * @param label Button label
	 * @param startPoint Chromosome start point
	 * @param endPoint Chromosome end point
	 * @param paramList '&' separated list of URL parameters
	 * @throws IOException
	 */
	private void printNavButton
	(
		JspWriter out, String contextPath, String label, 
		double startPoint, double endPoint, String paramList
	) throws IOException {
		String url = contextPath + action + paramList + "&startPoint=" + startPoint +
			"&endPoint=" + endPoint;
		out.println("<input type=\"button\" value=\"" + label + "\" onclick=\"window.location='" +
			url + "'\">");
	}
}
