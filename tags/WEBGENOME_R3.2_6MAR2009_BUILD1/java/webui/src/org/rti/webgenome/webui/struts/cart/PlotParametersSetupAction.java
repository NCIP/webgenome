/*
$Revision: 1.6 $
$Date: 2008-02-22 18:24:44 $


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
	    		this.getAnnotatedGenomeFeatureDao()
	    		.availableAnnotationTypes(org);
	    	request.setAttribute("annotationTypes", annotationTypes);
    	}
    	
    	// Attach plot type to request
    	request.setAttribute("plotType", plotType);
    	
    	return mapping.findForward("success");
    }
}
