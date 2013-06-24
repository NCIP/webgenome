/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $


*/

package org.rti.webgenome.webui.taglib;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.util.SystemUtils;

/**
 * Creates set of <option></option> tags for all quantitation types.
 * @author dhall
 *
 */
public class QuantitationTypeOptionsTag extends TagSupport {
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	
	/**
	 * Do after start tag parsed.
	 * @throws JspException if anything goes wrong.
	 * @return Return value
	 */
	@Override
	public final int doStartTag() throws JspException {
		Map<String, QuantitationType> index =
			QuantitationType.getQuantitationTypeIndex();
		Writer out = pageContext.getOut();
		for (String id : index.keySet()) {
			String name = index.get(id).getName();
			try {
				out.write("<option value=\"" + id + "\">" + name + "</option>");
			} catch (IOException e) {
				throw new JspException("Error writing to page.");
			}
		}
		return TagSupport.SKIP_BODY;
	}

}
