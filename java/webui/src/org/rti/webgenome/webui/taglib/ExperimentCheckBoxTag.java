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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.util.SystemUtils;


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
		if (!(obj instanceof Experiment)) {
			throw new JspException("Bean '" + this.name + "' is "
					+ "not of type Experiment");
		}
		Experiment exp = (Experiment) obj;
		if (exp.getId() == null) {
			throw new JspException("Experiment ID is null");
		}
		
		// Write output
		Writer out = pageContext.getOut();
		String inputName =
			org.rti.webgenome.webui.util.PageContext.EXPERIMENT_ID_PREFIX
			+ exp.getId();
		try {
			out.write("<input type=\"checkbox\" name=\"" + inputName + "\">");
		} catch (IOException e) {
			throw new JspException("Error writing page output", e);
		}
		
		return TagSupport.SKIP_BODY;
	}
}
