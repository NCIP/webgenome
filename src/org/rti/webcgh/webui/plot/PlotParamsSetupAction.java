/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/plot/PlotParamsSetupAction.java,v $
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

import java.util.Collection;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;

import org.rti.webcgh.analytic.AnalyticPipeline;
import org.rti.webcgh.array.GenomeAssembly;
import org.rti.webcgh.array.Pipeline;
import org.rti.webcgh.array.ShoppingCart;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.service.UserProfile;
import org.rti.webcgh.util.CollectionUtils;
import org.rti.webcgh.webui.util.AttributeManager;


/**
 * Struts action class for retrieving plotting parameters
 * for presentation
 */
public class PlotParamsSetupAction extends Action {
    
    private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
    
	
    /**
     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentDomainObjectMgr) {
        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
    }
    
    
	/**
	 * Performs action of retrieving plotting parameters
 	 * for presentation
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
		PlotParamsForm pform = (PlotParamsForm)form;
		
		// Create list of analytic pipeline names
		Collection pipelinesCol = AnalyticPipeline.loadDefaultPipelines();
		UserProfile profile = AttributeManager.getUserProfile(request);
		if (profile != null) {
			Pipeline[] pipelines = this.persistentDomainObjectMgr.getAllPersistentPipelines(profile.getName());
			for (int i = 0; i < pipelines.length; i++)
			    pipelinesCol.add(pipelines[i].toAnalyticPipeline());
		}
		request.setAttribute("pipelines", pipelinesCol);
		
		// Get list of available assemblies
		GenomeAssembly[] assemblies = this.persistentDomainObjectMgr.getAllPersistentGenomeAssemblies();
		Collection assembliesCol = CollectionUtils.arrayToArrayList(assemblies);
		request.setAttribute("assemblies", assembliesCol);
		
		// Get list of available quantitation types and set form quantitation type
		ShoppingCart cart = AttributeManager.getShoppingCart(request);
		Set qTypes = cart.quantitationTypes();
		request.setAttribute("quantitationTypes", qTypes);
		
		return mapping.findForward("success");
	}
}
