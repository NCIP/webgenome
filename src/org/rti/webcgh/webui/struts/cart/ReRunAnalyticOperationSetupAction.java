/*
$Revision: 1.1 $
$Date: 2007-03-06 02:06:28 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.analysis.AnalyticOperation;
import org.rti.webcgh.analysis.UserConfigurableProperty;
import org.rti.webcgh.core.WebcghApplicationException;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.webui.struts.BaseAction;
import org.rti.webcgh.webui.util.PageContext;

/**
 * This action class is a set up for re-running
 * an analytic operation on a an experiment that was
 * previously derived through an analytic operation.
 * @author dhall
 *
 */
public class ReRunAnalyticOperationSetupAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		
		// Get ID of experiment on which to re-run operation
		Long expId = Long.parseLong(
				request.getParameter("experimentId"));
		
		// Get experiment from shopping cart
		ShoppingCart cart = PageContext.getShoppingCart(request);
		Experiment exp = cart.getExperiment(expId);
		if (exp == null) {
			throw new WebcghApplicationException(
					"Experiment no longer in shopping cart");
		}
		
		// Retrieve analytic operation and associated
		// user configurable properties and attach to request
		AnalyticOperation op = exp.getSourceAnalyticOperation();
		Collection<UserConfigurableProperty> props =
			op.getUserConfigurableProperties(
					exp.getQuantitationType());
		request.setAttribute("analyticOperation", op);
		request.setAttribute("userConfigurableProperties", props);
		
		// Put experiment ID on request for downstream JSP
		request.setAttribute("experimentId", expId.toString());
		
		return mapping.findForward("success");
	}
}