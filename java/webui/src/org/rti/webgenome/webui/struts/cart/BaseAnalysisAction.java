/*
$Revision: 1.4 $
$Date: 2007-07-29 19:53:34 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticOperationFactory;
import org.rti.webgenome.analysis.BadUserConfigurablePropertyException;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.analysis.DataTransformer;
import org.rti.webgenome.service.analysis.InMemoryDataTransformer;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Base class for actions that perform analytic operations.
 * This class mainly provides helper methods.
 * @author dhall
 *
 */
public class BaseAnalysisAction extends BaseAction {
	
	/** Service for performing analyses. */
	private AnalysisService analysisService = null;

	/** Analytic operation factory. */
	private final AnalyticOperationFactory analyticOperationFactory =
		new AnalyticOperationFactory();
	
	/** Manages serialized data files. */
	private DataFileManager dataFileManager = null;
	
	/**
	 * Set service for performing analysis via injection.
	 * @param analysisService Service for performing analysis.
	 */
	public void setAnalysisService(final AnalysisService analysisService) {
		this.analysisService = analysisService;
	}
	
	/**
	 * Set manager for serialized data files.
	 * @param dataFileManager Data file manager
	 */
	public void setDataFileManager(final DataFileManager dataFileManager) {
		this.dataFileManager = dataFileManager;
	}
	
	
	/**
	 * Get a factory for creating anlaytic operations.
	 * @return Analytic operation factory
	 */
	protected AnalyticOperationFactory getAnalyticOperationFactory() {
		return this.analyticOperationFactory;
	}
	
	/**
	 * Get service for performing analytic operations.
	 * @return Analysis service
	 */
	protected AnalysisService getAnalysisService() {
		return this.analysisService;
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
	    	props.setSourceAnalyticOperation(op, exp.getQuantitationType());
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
    				this.dataFileManager);
    	}
		return transformer;
	}
}
