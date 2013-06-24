/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.8 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticOperationFactory;
import org.rti.webgenome.analysis.BadUserConfigurablePropertyException;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.service.analysis.DataTransformer;
import org.rti.webgenome.service.analysis.InMemoryDataTransformer;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Base class for actions that perform analytic operations.
 * This class mainly provides helper methods.
 * @author dhall
 *
 */
public class BaseAnalysisAction extends BaseAction {
	

	/** Analytic operation factory. */
	private final AnalyticOperationFactory analyticOperationFactory =
		new AnalyticOperationFactory();

	/**
	 * Get a factory for creating anlaytic operations.
	 * @return Analytic operation factory
	 */
	protected AnalyticOperationFactory getAnalyticOperationFactory() {
		return this.analyticOperationFactory;
	}
	

	/**
	 * Set user specified analytic operation parameters
	 * for all given derived experiments
	 * extracted from request parameters.
	 * Such parameters are passed
	 * to the action as HTTP query parameters with a prefix
	 * of 'prop_EXP-NO_' where EXP-NO is an integer value
	 * corresponding to an experiment number.
	 * @param experiments Derived experiments for which to
	 * adjust parameters.
	 * @param request Servlet request
	 * @return Action errors if there are any invalid
	 * user-supplied parameters or <code>null</code> otherwise.
	 */
	protected ActionErrors setUserSpecifiedParameters(
			final Collection<Experiment> experiments,
			final HttpServletRequest request) {
		ActionErrors errors = null;
    	Map paramMap = request.getParameterMap();
    	boolean haveErrors = false;
    	for (Experiment exp : experiments) {
    		if (!exp.isDerived()) {
    			throw new IllegalArgumentException(
    					"Experiment not derived");
    		}
    		String prefix = "prop_exp_" + exp.getId() + "_";
    		AnalysisDataSourceProperties props =
    			(AnalysisDataSourceProperties) exp.getDataSourceProperties();
    		AnalyticOperation op = props.getSourceAnalyticOperation();
	    	for (Object paramNameObj : paramMap.keySet()) {
	    		String paramName = (String) paramNameObj;
	    		if (paramName.indexOf(prefix) == 0) {
	    			String propName = paramName.substring(
	    					prefix.length());
	    			String propValue = request.getParameter(
	    					paramName);
	    			try {
	    				op.setProperty(propName, propValue);
	    			} catch (BadUserConfigurablePropertyException e) {
	    				haveErrors = true;
	    			}
	    		}
	    	}
	    	Collection<QuantitationType> qTypes =
	    		new ArrayList<QuantitationType>();
	    	qTypes.add(exp.getQuantitationType());
	    	props.setSourceAnalyticOperation(op, qTypes);
    	}
    	
    	// If user input is invalid, return
    	if (haveErrors) {
    		errors = new ActionErrors();
    	}
    	
    	return errors;
	}
	
	
	// TODO: The body of the below method contains code that was
	// coped and pasted from
	// {@code AnalyticOperationParametersForm}.  This could
	// be refactored out into a common method.
	
	/**
	 * Set user specified analytic operation parameters.
	 * Such parameters are passed
	 * to the action as HTTP query parameters with a prefix
	 * of 'prop_'.
	 * @param request Servlet request
	 * @param op An analytic operation whose user specified
	 * properties will be reset based on form input field
	 * values.
	 * @return Action errors if there are any invalid
	 * user-supplied parameters or <code>null</code> otherwise.
	 */
	protected ActionErrors setUserSpecifiedParameters(
			final AnalyticOperation op,
			final HttpServletRequest request) {
		ActionErrors errors = null;
    	Map paramMap = request.getParameterMap();
    	boolean haveErrors = false;
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
    	
    	// If user input is invalid, return
    	if (haveErrors) {
    		errors = new ActionErrors();
    	}
    	
    	return errors;
	}
		
	
	/**
	 * Get a data transformer, which may be for
	 * in-memory or serialized data.
	 * @param request Servlet request
	 * @return An appropriate data transformer
	 * @throws SessionTimeoutException If the session mode
	 * cannot be detected indicating a timeout
	 */
	protected DataTransformer getDataTransformer(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		DataTransformer transformer = null;
		if (this.dataInMemory(request)) {
    		transformer = new InMemoryDataTransformer();
    	} else {
    		transformer = new SerializedDataTransformer(
    				this.getDataFileManager());
    	}
		return transformer;
	}
}
