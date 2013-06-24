/*
$Revision: 1.3 $
$Date: 2007-07-29 19:53:34 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticOperationFactory;
import org.rti.webgenome.analysis.BadUserConfigurablePropertyException;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;

/**
 * Form for capturing user-inputted configurable parameters
 * for an analytic operation.
 * @author dhall
 *
 */
public class AnalyticOperationParametersForm extends BaseForm {

	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	
	// =====================================
	//      Attributes
	// =====================================
	
	/** Analytic operation key. */
	private String operationKey = "";
	
	/**
	 * Experiment primary key identifier. This property is
	 * only set when an operation is being redone on an
	 * experiment.
	 */
	private String experimentId = "";
	
	/**
	 * Map containing user-input configurable parameter
	 * name-value pairs.
	 */ 
	private Map<String, Object> params = new HashMap<String, Object>();
	
	/** Analytic operation factory. */
	private final AnalyticOperationFactory analyticOperationFactory =
		new AnalyticOperationFactory();
	
	
	// ==================================
	//     Getters/setters
	// ==================================
	
	/**
	 * Get analytic operation key.
	 * @return Analytic operation key.
	 */
	public final String getOperationKey() {
		return operationKey;
	}

	
	/**
	 * Set analytic operation key.
	 * @param operationKey Analytic operation key.
	 */
	public final void setOperationKey(final String operationKey) {
		this.operationKey = operationKey;
	}
	
	
	/**
	 * Set configurable parameter name and value.
	 * @param name Name of configurable parameter.
	 * @param value Value of configurable parameter.
	 */
	public final void setParamValue(final String name, final Object value) {
		this.params.put(name, value);
	}
	
	
	/**
	 * Get configurable parameter name and value.
	 * @param name Name of configurable parameter.
	 * @return Value of configurable parameter.
	 */
	public final Object getParamValue(final String name) {
		return this.params.get(name);
	}
	
	
	/**
	 * Get primary key ID of an experiment.  This property
	 * is only used if operation is being rerun on an
	 * experiment.
	 * @return Experiment ID.
	 */
	public final String getExperimentId() {
		return experimentId;
	}


	/**
	 * Set primary key ID of an experiment.  This property
	 * is only used if operation is being rerun on an
	 * experiment.
	 * @param experimentId Experiment ID
	 */
	public final void setExperimentId(final String experimentId) {
		this.experimentId = experimentId;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionErrors validate(final ActionMapping mapping,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		AnalyticOperation op =
			this.analyticOperationFactory.newAnalyticOperation(
					this.operationKey);
		Map paramMap = request.getParameterMap();
		for (Object paramNameObj : paramMap.keySet()) {
    		String paramName = (String) paramNameObj;
    		if (paramName.indexOf("prop_") == 0) {
    			String propName = paramName.substring("prop_".length());
    			String propValue = request.getParameter(paramName);
    			try {
    				op.setProperty(propName, propValue);
    			} catch (BadUserConfigurablePropertyException e) {
    				errors.add("global",
    						new ActionError("invalid.fields"));
    				break;
    			}
    		}
    	}
		return errors;
	}
}
