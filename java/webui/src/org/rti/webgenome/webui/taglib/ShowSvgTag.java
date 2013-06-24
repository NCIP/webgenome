/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/ShowSvgTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/
package org.rti.webgenome.webui.taglib;


import org.apache.log4j.Logger;
import org.rti.webgenome.util.XmlUtils;
import org.rti.webgenome.webui.util.Attribute;
import org.w3c.dom.Document;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;



/**
 * Custom tag that displays SVGs
 */
public class ShowSvgTag extends TagSupport {
    
    private static final Logger LOGGER = Logger.getLogger(ShowSvgTag.class);
	
	/**
	 * Start tag
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		
		try {
			Document svgDoc = 
				(Document)pageContext.findAttribute(Attribute.SVG);
			XmlUtils.forwardDocument(svgDoc, pageContext.getOut());
		} catch (Exception e) {
		    LOGGER.error(e);
		    e.printStackTrace();
			throw new JspException("Converting SVG to XML", e);
		}

		//return SKIP_BODY;
		return EVAL_BODY_INCLUDE ;
	}
}