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
