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

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.util.ColorUtils;
import org.rti.webgenome.util.SystemUtils;

/**
 * Outputs color of bioassay in hexadecimal RGB
 * form.  Tag expects an attribute 'name' that is
 * the name of a bean of type <code>BioAssay</code>.
 * @author dhall
 *
 */
public class BioAssayColorTag extends TagSupport {
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
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
		if (this.name == null || this.name.length() < 1) {
			throw new JspException("Tag attribute '"
					+ this.name + "' missing or empty");
		}
		Object obj = pageContext.findAttribute(this.name);
		if (obj == null) {
			throw new JspException("Cannot find bean named '"
					+ this.name + "'");
		}
		if (!(obj instanceof BioAssay)) {
			throw new JspException("Bean '" + this.name + "' is "
					+ "not of type Experiment");
		}
		BioAssay ba = (BioAssay) obj;
		Color c = ba.getColor();
		if (c == null) {
			throw new JspException("Bioassay '" + ba.getName()
					+ "' does not have a color");
		}
		
		// Generate output
		String colorEncoding = ColorUtils.toRgbHexEncoding(c);
		Writer out = this.pageContext.getOut();
		try {
			out.write(colorEncoding);
		} catch (IOException e) {
			throw new JspException("Error writing to JSP page");
		}
		return TagSupport.SKIP_BODY;
	}
}
