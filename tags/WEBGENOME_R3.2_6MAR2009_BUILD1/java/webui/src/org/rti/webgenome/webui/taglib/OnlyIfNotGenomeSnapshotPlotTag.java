/*
$Revision: 1.1 $
$Date: 2007-09-13 23:42:17 $


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
 * <code>plotTypeBeanName</code> is not a genome snapshot plot.
 * @author dhall
 *
 */
public class OnlyIfNotGenomeSnapshotPlotTag extends BasePlotTypeSwitch {

	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int doStartTag() throws JspException {
		int rval = TagSupport.SKIP_BODY;
		if (PlotType.GENOME_SNAPSHOT != this.getPlotType()) {
			rval = TagSupport.EVAL_BODY_INCLUDE;
		}
		return rval;
	}
}
