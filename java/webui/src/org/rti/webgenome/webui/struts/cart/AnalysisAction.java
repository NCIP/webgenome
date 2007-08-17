/*
$Revision: 1.10 $
$Date: 2007-08-17 19:02:16 $

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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.MultiExperimentStatelessOperation;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.analysis.DataTransformer;
import org.rti.webgenome.service.job.AnalysisJob;
import org.rti.webgenome.service.job.JobManager;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * Performs analytic operation on selected experiments.
 * If session is client-mode, this action drives the analytic
 * operation.  If stand-alone mode, the analytic operation is
 * driven by a batch process.
 * @author dhall
 *
 */
public final class AnalysisAction extends BaseAnalysisAction {
	

	/**
     * Execute action.
     * @param mapping Routing information for downstream actions
     * @param form Form data
     * @param request Servlet request object
     * @param response Servlet response object
     * @return Identification of downstream action as configured in the
     * struts-config.xml file
     * @throws Exception All exceptions thrown by classes in
     * the method are passed up to a registered exception
     * handler configured in the struts-config.xml file
     */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	
    	// Generate analytic operation instance
    	AnalyticOperationParametersForm aForm =
    		(AnalyticOperationParametersForm) form;
    	AnalyticOperation op = this.getAnalyticOperationFactory().
    		newAnalyticOperation(aForm.getOperationKey());
    	
    	// Set user specified parameters for operation
    	this.setUserSpecifiedParameters(op, request);
    	
    	// Retrieve form bean containing selected experiments.
    	// Note, this is not the form bean configured
    	// for this action in struts-config.xml.
    	SelectedExperimentsForm seForm =
    		PageContext.getSelectedExperimentsForm(request, false);
    	if (seForm == null) {
    		throw new SessionTimeoutException(
    				"Could not find selected experiments");
    	}
    	
    	// Get experiment IDs
    	Collection<Long> ids = seForm.getSelectedExperimentIds();
    	
    	// Map input to output bioassay and experiment names
    	Map<Long, String> outputBioAssayNames =
    		new HashMap<Long, String>();
    	Map<Long, String> outputExperimentNames =
    		new HashMap<Long, String>();
    	if (!(op instanceof MultiExperimentStatelessOperation)) {
	    	this.constructNameMap(request, outputExperimentNames,
	    			outputBioAssayNames);
    	}
    	
    	ActionForward forward = null;
    	ShoppingCart cart = this.getShoppingCart(request);
		Collection<Experiment> experiments = cart.getExperiments(ids);

		// Case: Perform immediately
    	if (!ProcessingModeDecider.processInBackground(
    			experiments, request)) {
    		DataTransformer transformer = this.getDataTransformer(request);
	    	this.getAnalysisService().performAnalyticOperation(
	    			experiments, op,
	    			cart, outputExperimentNames, outputBioAssayNames,
	    			transformer);
	    	this.persistShoppingCartChanges(cart, request);
	    	forward = mapping.findForward("non.batch");
	    	
	    // Case: Perform in background
    	} else {
    		Principal principal = PageContext.getPrincipal(request);
    		JobManager jobManager = this.getJobManager();
    		AnalysisJob job = new AnalysisJob(experiments, op,
    				outputBioAssayNames, outputExperimentNames,
    				principal.getName());
    		jobManager.add(job);
    		forward = mapping.findForward("batch");
    	}
    	
    	return forward;
    }
    
     
    
    /**
     * Construct map of input bioassay and experiment
     * IDs to corresponding output bioassay and
     * experiment names.  If operation is of type
     * MultiExperimentToNonArrayDataAnalyticOperation,
     * then this will not be done as the user does not
     * name output experiments/bioassays.
     * @param request Servlet request object.
     * @param outputExperimentNames Map of input experiment
     * names to output experiment names.  This parameter
     * should be empty initially; this method populates the map.
     * @param outputBioAssayNames Map of input bioassay names
     * to output bioassay names.  This parameter
     * should be empty initially; this method populates the map.
     */
    private void constructNameMap(
    		final HttpServletRequest request,
    		final Map<Long, String> outputExperimentNames,
    		final Map<Long, String> outputBioAssayNames) {
    	Map paramMap = request.getParameterMap();
    	for (Object paramNameObj : paramMap.keySet()) {
    		String paramName = (String) paramNameObj;
    		String paramValue = request.getParameter(paramName);
    		if (paramName.indexOf("eo_") == 0) {
    			Long experimentId = Long.parseLong(
    					paramName.substring("eo_".length()));
    			outputExperimentNames.put(experimentId, paramValue);
    		} else if (paramName.indexOf("bo_") == 0) {
    			Long bioAssayId = Long.parseLong(
    					paramName.substring("bo_".length()));
    			outputBioAssayNames.put(bioAssayId, paramValue);
    		}
    	}
    }
}
