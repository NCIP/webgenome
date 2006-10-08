/*
$Revision: 1.1 $
$Date: 2006-10-08 01:11:27 $

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Plot;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.plot.PlotGenerator;
import org.rti.webcgh.service.plot.PlotParameters;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.service.util.IdGenerator;
import org.rti.webcgh.webui.SessionTimeoutException;
import org.rti.webcgh.webui.struts.BaseAction;
import org.rti.webcgh.webui.util.PageContext;
import org.rti.webcgh.webui.util.SessionMode;

/**
 * Action that creates new plot.  If session mode is CLIENT,
 * the plot is created by this action.  If mode is STAND_ALONE,
 * then plot is created by a batch processing job.
 * @author dhall
 *
 */
public final class NewPlotAction extends BaseAction {
	
	/** Plot generator. */
	private PlotGenerator plotGenerator = null;
	
	/** ID generator. */
	private IdGenerator plotIdGenerator = null;
	
	/** Chromosome array data getter. */
	private ChromosomeArrayDataGetter chromosomeArrayDataGetter = null;
	
	/**
	 * Set plot generator.
	 * @param plotGenerator Plot generator.
	 */
	public void setPlotGenerator(final PlotGenerator plotGenerator) {
		this.plotGenerator = plotGenerator;
	}
	
	
	/**
	 * Set ID generator.
	 * @param idGenerator ID generator
	 */
	public void setPlotIdGenerator(final IdGenerator idGenerator) {
		this.plotIdGenerator = idGenerator;
	}


	/**
	 * Set chromosome array data getter.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	public void setChromosomeArrayDataGetter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		this.chromosomeArrayDataGetter = chromosomeArrayDataGetter;
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
    	
    	// Retrieve selected experiments form bean.
    	// Note, this is not the form bean configured
    	// for this action in struts-config.xml.
    	SelectedExperimentsForm seForm =
    		PageContext.getSelectedExperimentsForm(request, false);
    	if (seForm == null) {
    		throw new SessionTimeoutException(
    				"Could not find selected experiments");
    	}
    	
    	// Get selected experiments
    	Collection<Long> experimentIds = seForm.getSelectedExperimentIds();
    	ShoppingCart cart = PageContext.getShoppingCart(request);
    	Collection<Experiment> experiments = cart.getExperiments(experimentIds);
    
    	// Get plot parameters
    	PlotParametersForm pForm = (PlotParametersForm) form;
    	PlotParameters params = pForm.getPlotParameters();
    	
    	// Create plot
    	SessionMode mode = PageContext.getSessionMode(request);
    	Plot plot = null;
    	if (mode == SessionMode.CLIENT) {
    		Long plotId = this.plotIdGenerator.nextId();
    		String plotName = plotId.toString();
    		plot = this.plotGenerator.newPlot(experiments, params,
    				plotName, this.chromosomeArrayDataGetter);
    		cart.add(plot);
    		request.setAttribute("plotId", plotId.toString());
    	}
    	
        return mapping.findForward("success");
    }
}
