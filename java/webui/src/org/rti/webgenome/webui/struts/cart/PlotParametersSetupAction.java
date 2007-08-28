/*
$Revision: 1.5 $
$Date: 2007-08-28 17:24:13 $

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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.core.PlotType;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.AnnotatedGenomeFeatureDao;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Routes a request to one of several pages
 * that enable the user to input plotting
 * parameters.  Routing is based on selected
 * plot type.
 * @author dhall
 *
 */
public final class PlotParametersSetupAction extends BaseAction {
	
	//
	//     ATTRIBUTES TO BE INJECTED
	//
	
	/**
	 * Data access object for obtaining annotated genome features.
	 * This propery must be injected.
	 */
	private AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao = null;
	
	//
	//     SETTERS
	//
	

	/**
	 * Set data access object for obtaining annotated genome features.
	 * This propery must be injected.
	 * @param annotatedGenomeFeatureDao Data access object
	 */
	public void setAnnotatedGenomeFeatureDao(
			final AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao) {
		this.annotatedGenomeFeatureDao = annotatedGenomeFeatureDao;
	}

	
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
    	
    	// Recover type of plot from form
    	PlotParametersForm pForm = (PlotParametersForm) form;
    	String plotTypeName = pForm.getPlotType();
    	PlotType plotType = PlotType.valueOf(plotTypeName);
    	
    	// If new plot, reset form plot name
    	String plotIdStr = request.getParameter("id");
    	if (plotIdStr == null) {
    		pForm.setName("");
    	}
    	
    	// Get experiments
    	Collection<Experiment> experiments = null;
    	ShoppingCart cart = this.getShoppingCart(request);
    	if (plotIdStr != null) {
    		Long plotId = Long.parseLong(plotIdStr);
    		Plot plot = cart.getPlot(plotId);
    		experiments = plot.getExperiments();
    	} else {
    		SelectedExperimentsForm seForm =
	    		PageContext.getSelectedExperimentsForm(request, false);
	    	if (seForm == null) {
	    		throw new SessionTimeoutException(
	    				"Could not find selected experiments");
	    	}
	    	Collection<Long> expIds = seForm.getSelectedExperimentIds();
	    	experiments = cart.getExperiments(expIds);
    	}
    	
    	// Get selected experiments and determine if
    	// any are derived from an analytic operation.
    	// The downstream JSP needs to know this because
    	// some form elements must be de-activated.
    	
    	// This code is commented out because I am not sure why
    	// it was originally needed.  It does not seem to
    	// be needed now.

//    	for (Experiment exp : experiments) {
//    		if (exp.isDerived()) {
//    			request.setAttribute("derivedFromAnalysis", "1");
//    			break;
//    		}
//    	}
    	
    	// If plot is an annotation plot, get available
    	// annotation types and attach to request
    	if (plotType == PlotType.ANNOTATION) {
	    	Organism org = Experiment.getOrganism(experiments);
	    	Set<AnnotationType> annotationTypes =
	    		this.annotatedGenomeFeatureDao.availableAnnotationTypes(org);
	    	request.setAttribute("annotationTypes", annotationTypes);
    	}
    	
    	// Attach plot type to request
    	request.setAttribute("plotType", plotType);
    	
    	return mapping.findForward("success");
    }
}
