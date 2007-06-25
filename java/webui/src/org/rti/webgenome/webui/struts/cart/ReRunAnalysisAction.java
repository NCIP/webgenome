/*
$Revision: 1.4 $
$Date: 2007-06-25 18:41:54 $

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.job.JobManager;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.webui.util.PageContext;

/**
 * This action reruns an analytic operation on
 * an experiment using new user-supplied parameters.
 * @author dhall
 *
 */
public class ReRunAnalysisAction extends BaseAnalysisAction {
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Manages compute-intensive jobs such as--in this case--re-running
	 * an analytic operation.
	 */
	private JobManager jobManager = null;
	
	
	//
	//     SETTERS
	//
	
	/**
	 * Setter for dependency injection of job manager.
	 * @param jobManager Manages compute-intensive jobs
	 */
	public void setJobManager(final JobManager jobManager) {
		this.jobManager = jobManager;
	}
	
	
	//
	//     OVERRIDES
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response)
	throws Exception {
	
		// Get selected experiment
		ShoppingCart cart = PageContext.getShoppingCart(request);
		Long expId = Long.parseLong(request.getParameter("experimentId"));
		Experiment exp = cart.getExperiment(expId);
		if (exp == null) {
			throw new WebGenomeApplicationException(
					"Selected experiment no longer in workspace");
		}
		
		// Set properties of analytic operation to redo
		AnalyticOperation op = exp.getSourceAnalyticOperation();
		ActionErrors errors = this.setUserSpecifiedParameters(
				op, request);
		
    	// If user input is invalid, return
    	if (errors != null) {
        	errors.add("global", new ActionError("invalid.fields"));
    		this.saveErrors(request, errors);
    		return mapping.findForward("errors");
    	}
    	
    	// Redo analysis
    	SessionMode mode = PageContext.getSessionMode(request);
    	boolean reranAlready = this.jobManager.rePerformAnalyticOperation(exp, op, mode);
    	
    	// Select forward
    	ActionForward forward = null;
    	if (reranAlready) {
    		forward = mapping.findForward("non.batch");
    	}
	
		return forward;
	}
}
