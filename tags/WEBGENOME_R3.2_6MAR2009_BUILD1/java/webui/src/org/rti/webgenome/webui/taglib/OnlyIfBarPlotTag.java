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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.core.PlotType;
import org.rti.webgenome.util.SystemUtils;

/**
 * Display contents of tag only if there is a bean
 * of type <code>PlotType</code> in some scope of reference
 * pointed to by the superclass property
 * <code>plotTypeBeanName</code> is a bar plot.
 * @author dhall
 *
 */
public class OnlyIfBarPlotTag extends BasePlotTypeSwitch {

	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int doStartTag() throws JspException {
		int rval = TagSupport.SKIP_BODY;
		if (PlotType.BAR == this.getPlotType()) {
			rval = TagSupport.EVAL_BODY_INCLUDE;
		}
		return rval;
	}
}
