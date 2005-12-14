/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/FeatureTypesSelectorTag.java,v $
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

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.rti.webcgh.array.GenomeFeatureType;
import org.rti.webcgh.webui.plot.PlotParamsForm;
import org.rti.webcgh.webui.util.Attribute;

/**
 * Custom tag that displays which if any data sets in the data cart
 * have no data
 */
public class FeatureTypesSelectorTag extends TagSupport {
	
	private static final int NUM_COLS = 4;
	private static final Logger LOGGER = Logger.getLogger(FeatureTypesSelectorTag.class);
	
	
	/**
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		try {
			PlotParamsForm form = (PlotParamsForm)
				pageContext.findAttribute("plotParamsForm");
			Collection features = (Collection)
				pageContext.findAttribute(Attribute.FEATURES);
			String contextPath = ((HttpServletRequest)
				pageContext.getRequest()).getContextPath();
			if (form != null) {
				Map checkedFeats = getCheckedFeatures(form);
				JspWriter out = pageContext.getOut();
				int styleIdx = 0;
				String[] style = {"contentTD", "contentTD-odd"};
				int count = 0;
				out.println("<table cellpadding=\"0\" cellspacing=\"0\" class=\"tbl\">");
				out.println("<tr class=\"dataHeadTD\"><td colspan=\"" + 
							(NUM_COLS * 2) + "\" align=\"center\">" +
							"Genome Annotation Types</td></tr>");
				Iterator it = features.iterator();
				while (it.hasNext()) {
					GenomeFeatureType type = (GenomeFeatureType)it.next();
					String featName = type.getName();
					String checked = (checkedFeats.get(featName) == null)?
						"" : "checked";
					if ((count++ % NUM_COLS) == 0)
						out.println("<tr class=\"" + style[styleIdx++ % 2] + "\">");
//					String link = "<a href=\"" + type.getTypeInfoUrl().toString() +
//						"\" target=\"infoWin\">" + featName + "</a>";

					out.println(
						"<td>" +
						"<img class=\"pointer\" " +
						"src=\"" + contextPath + "/images/helpicon.gif" + "\"" +
						"onclick=\"window.open('" + type.getUrl().toString() +
						"', 'infoWin', '')\"" +
						">" +
						"</td>"
					);
					String link = featName;
					out.println("<td>" +
								"<input " +
								"type=\"checkbox\" " +
								"name=\"" + Attribute.PRE_FEAT_TYPE + featName + "\"" +
								checked + ">" + link + "</td>");
						
					if ((count % NUM_COLS) == 0)
						out.println("</tr>");
				}
				if ((count % NUM_COLS) != 0)
					for (; (count % NUM_COLS) != 0; count++)
						out.println("<td></td>");
				out.println("</tr>\n</table>");
			}
		} catch (IOException e) {
		    LOGGER.error(e);
			throw new JspException("Error evaluating FeatureTypesSelectorTag", e);
		}
		return SKIP_BODY;
	}
	
	
	private Map getCheckedFeatures(PlotParamsForm form) {
		Map checkedFeats = new HashMap();
		StringTokenizer tok = 
			new StringTokenizer(form.getSelectedFeatureTypes(), ",");
		while (tok.hasMoreTokens()) {
			String featName = tok.nextToken();
			checkedFeats.put(featName, featName);
		}
		return checkedFeats;
	}
}
