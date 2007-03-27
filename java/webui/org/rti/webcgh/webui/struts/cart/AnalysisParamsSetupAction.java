/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:10 $

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

package org.rti.webcgh.webui.struts.cart;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.analysis.AnalyticOperation;
import org.rti.webcgh.analysis.AnalyticOperationFactory;
import org.rti.webcgh.analysis.AnalyticPipeline;
import org.rti.webcgh.analysis.SingleExperimentStatelessOperation;
import org.rti.webcgh.analysis.MultiExperimentStatelessOperation;
import org.rti.webcgh.analysis.UserConfigurableProperty;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.webui.SessionTimeoutException;
import org.rti.webcgh.webui.struts.BaseAction;
import org.rti.webcgh.webui.util.PageContext;

/**
 * Setup for JSP that enables user to set analytic operation.
 * parameters
 * @author dhall
 *
 */
public final class AnalysisParamsSetupAction extends BaseAction {
	
	/** Analytic operation factory. */
	private final AnalyticOperationFactory analyticOperationFactory =
		new AnalyticOperationFactory();
	
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
    	ShoppingCart cart = PageContext.getShoppingCart(request);
    	
    	// Get selected experiments
    	SelectedExperimentsForm seForm =
    		PageContext.getSelectedExperimentsForm(request, false);
    	if (seForm == null) {
    		throw new SessionTimeoutException(
    				"Could not find selected experiments");
    	}
    	Collection<Long> ids = seForm.getSelectedExperimentIds();
    	Collection<Experiment> experiments = cart.getExperiments(ids);
    	
    	// Determine quantitation type
    	QuantitationType qType = Experiment.getQuantitationType(experiments);
    	
    	// Get instance of analytic operation and attach to request
    	AnalyticOperationParametersForm aForm =
    		(AnalyticOperationParametersForm) form;
    	String opKey = aForm.getOperationKey();
    	AnalyticOperation op =
    		this.analyticOperationFactory.newAnalyticOperation(opKey);
    	request.setAttribute("op", op);
    	
    	// Get user configurable parameter characteristics and
    	// attach to request
    	List<UserConfigurableProperty> props =
    		op.getUserConfigurableProperties(qType);
    	request.setAttribute("props", props);
    	
    	// Get selected experiments and attach to request.
    	// This is done so that the downstream JSP can create
    	// a form enabling the user to set output experiment
    	// and bioassay names.
    	// First, retrieve selected experiments form bean.
    	// Note, this is not the form bean configured
    	// for this action in struts-config.xml.  If
    	// the operation is of type
    	// MultiExperimentToNonArrayDataAnalyticOperation,
    	// none of this will be done since the user does not
    	// set output names.
    	if (!(op instanceof MultiExperimentStatelessOperation)) {
	    	request.setAttribute("experiments", experiments);
	    	
	    	// Determine if there will be a single bioassay or multiple
	    	// produced per experiment and set an attribute
	    	if (op instanceof SingleExperimentStatelessOperation
	    			|| (op instanceof AnalyticPipeline
	    				&& ((AnalyticPipeline) op).
	    				producesSingleBioAssayPerExperiment())) {
	    		request.setAttribute("singleBioAssay", "true");
	    	}
    	}
    	
    	return mapping.findForward("success");
    }

}
