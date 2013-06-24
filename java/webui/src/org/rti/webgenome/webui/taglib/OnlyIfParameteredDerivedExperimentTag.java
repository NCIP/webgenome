/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-09-09 18:32:22 $


*/

package org.rti.webgenome.webui.taglib;

import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.util.SystemUtils;

/**
 * Show content of tag only if there is a derived experiment
 * bean in some scope with name provided by the
 * <code>name</code> property and which has
 * user configurable parameters.  A derived experiment is one
 * that results from an analytic operation.
 * @author dhall
 *
 */
public class OnlyIfParameteredDerivedExperimentTag extends TagSupport {
	
	
	//
	//     STATICS
	//
	
	/** Serlialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	
	//
	//     ATTRIBUTES
	//
	
	/** Name of experiment bean. */
	private String name = null;
	
	
	//
	//     SETTERS
	//
	
	/**
	 * Set name of experiment bean.
	 * @param name Name of experiment bean
	 */
	public void setName(final String name) {
		this.name = name;
	}


	//
	//     OVERRIDES
	//
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doStartTag() throws JspException {
		int rval = TagSupport.SKIP_BODY;
		if (name != null && name.length() > 0) {
			Object obj = pageContext.findAttribute(name);
			if (obj != null) {
				if (!(obj instanceof Experiment)) {
					throw new JspException("Bean named '"
							+ this.name + "' not of type Experiment");
				}
				Experiment exp = (Experiment) obj;
				if (exp.isDerived()) {
					AnalysisDataSourceProperties props =
						(AnalysisDataSourceProperties)
						exp.getDataSourceProperties();
					Collection<UserConfigurableProperty> userProps =
						props.getUserConfigurableProperties();
					if (userProps != null && userProps.size() > 0) {
						rval = TagSupport.EVAL_BODY_INCLUDE;
					}
				}
			}
		}
		return rval;
	}
}
