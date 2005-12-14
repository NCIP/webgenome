/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/plot/PlotSetupAction.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/


package org.rti.webcgh.webui.plot;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;

import org.apache.log4j.Logger;

import org.rti.webcgh.analytic.AnalyticPipeline;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.array.ShoppingCart;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.graph.PlotParameters;
import org.rti.webcgh.service.ClientDataService;
import org.rti.webcgh.service.UserProfile;
import org.rti.webcgh.webui.util.Attribute;
import org.rti.webcgh.webui.util.AttributeManager;
import org.rti.webcgh.webui.util.DataAssembler;
import org.rti.webcgh.webui.util.WebUtils;


/**
 * Performs acions required to create any plot type
 */
public class PlotSetupAction extends Action {
	
	// ===============================
	//       Constants
	// ===============================
	
	private static final String DEF_PLOT_TYPE = "scatter";
	
	
	private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
	private ClientDataService clientDataService = null;
	
	
	/**
	 * Set client data service
	 * @param clientDataService
	 */
	public void setClientDataService(ClientDataService clientDataService) {
		this.clientDataService = clientDataService;
	}


	/**
	 * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
	 */
	public void setPersistentDomainObjectMgr(
			PersistentDomainObjectMgr persistentDomainObjectMgr) {
		this.persistentDomainObjectMgr = persistentDomainObjectMgr;
	}
	
	
	private static Logger logger = Logger.getLogger(PlotSetupAction.class.getName());


	/**
	 * Performs acions required to create any plot type
	 *
	 * @param mapping Routing information for downstream actions
	 * @param form Data from calling form
	 * @param request Servlet request object
	 * @param response Servlet response object
	 * @return Identification of downstream action as configured in the
	 * struts-config.xml file
	 * @throws Exception
	 */
	public ActionForward execute
	(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		logger.info("Starting action 'PlotSetupAction'");
		PlotParamsForm pform = (PlotParamsForm)form;
		PlotParameters params = pform.getPlotParameters();
		
		// Get user profile and shopping cart
		HttpSession session = request.getSession();
		UserProfile profile = AttributeManager.getUserProfile(request);
		ShoppingCart cart = AttributeManager.getShoppingCart(request);
		
		// Make sure reference genome compatible with shopping cart
		String plotType = request.getParameter("plotType");
		if (plotType == null || plotType.length() < 1) {
			plotType = DEF_PLOT_TYPE;
			pform.setPlotType(DEF_PLOT_TYPE);
		}
//		if ("annotation".equals(plotType) || "ideogram".equals(plotType)) {
//		    Long genomeAssemblyId = new Long(pform.getAssemblyId());
//		    GenomeAssembly assembly = persistentDomainObjectMgr.getPersistentGenomeAssembly(genomeAssemblyId);
//		    if (! cart.compatible(assembly)) {
//		        ActionErrors errors = new ActionErrors();
//		        errors.add("global", new ActionError("incompatible.assemblies"));
//		        errors.add("assemblyId", new ActionError("invalid.field"));
//		        this.saveErrors(request, errors);
//		        return mapping.findForward("incompatible.assemblies");
//		    }
//		}
		
		// Recover experiments from cart
		DataAssembler assembler = new DataAssembler(request);
		AnalyticPipeline anPipeline = WebUtils.getPipeline(pform.getPipelineName(),
		        this.persistentDomainObjectMgr, profile);
		Experiment[] experiments = cart.getExperiments();
		if ("scatter".equals(pform.getPlotType()) && experiments.length < 1) {
			ActionErrors errors = new ActionErrors();
			errors.add("experiments", new ActionError("no.experiments.selected"));
			saveErrors(request, errors);
			return mapping.findForward("no.data");
		}
		
		// If data from application client, update
		PlotParameters plotParameters = pform.getPlotParameters();
		GenomeIntervalDto[] dtos = plotParameters.getGenomeIntervalDtos();
		if (request.getAttribute("invocation.from.client") == null) {
			if (experiments != null && experiments.length > 0) {
				if (experiments[0].getClientId() != null) {
				    if (dtos == null || dtos.length < 1) {
				        ActionErrors errors = new ActionErrors();
				        errors.add("genomeIntervals", new ActionError("invalid.field"));
				        this.saveErrors(request, errors);
				        return mapping.findForward("no.intervals");
				    }
				    GenomeIntervalDto[] cachedDtos = AttributeManager.getGenomeIntervalDtos(request);
				    if (!(cachedDtos != null && GenomeIntervalDto.equal(dtos, cachedDtos))) {
				    	experiments = clientDataService.refresh(experiments, dtos);
				    	cart.purgeClientData();
				    	cart.add(experiments);
				    }
				}
			}
		}
	    AttributeManager.setGenomeIntervalDtos(request, dtos);
		experiments = 
			assembler.assembleExperiments(experiments, anPipeline, params);
		session.setAttribute(Attribute.DATA_SET, experiments);
		
		// Determine if pipeline produces experimental means
		String resultsInMeans = "no";
		if (anPipeline != null && anPipeline.resultsInMeans())
			resultsInMeans = "yes";
		request.setAttribute("average", resultsInMeans);
				
		// Determine forward
		ActionForward forward = null;
		if ("genome".equals(plotType) || "scatter".equals(plotType))
			forward = mapping.findForward("scatter");
		else if ("annotation".equals(plotType))
			forward = mapping.findForward("annotation");
		else if ("ideogram".equals(plotType))
			forward = mapping.findForward("ideogram");
		else if ("bar".equals(plotType))
			forward = mapping.findForward("bar");
		
		logger.info("Completed action 'PlotSetupAction'");
		return forward;
	}
}