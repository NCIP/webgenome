/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/cart/MapProbesAction.java,v $
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
package org.rti.webcgh.webui.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.ExperimentProbeMappingResults;
import org.rti.webcgh.array.GenomeAssembly;
import org.rti.webcgh.array.ReporterMappingStagingArea;
import org.rti.webcgh.array.ShoppingCart;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.util.CollectionUtils;
import org.rti.webcgh.webui.util.AttributeManager;

/**
 * 
 */
public class MapProbesAction extends Action {
	
	PersistentDomainObjectMgr persistentDomainObjectMgr = null;
	
	
    /**
     * @param persistentObjectMgr The persistentObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentObjectMgr) {
        this.persistentDomainObjectMgr = persistentObjectMgr;
    }
    
    
	/**
	 * Performs action
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
		
		// Instantiate assemblies
		Collection assemblyCol = new ArrayList();
		for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
			String paramName = (String)en.nextElement();
			if (paramName.indexOf("org-") == 0) {
				String paramValue = request.getParameter(paramName);
				if ("none".equals(paramValue)) {
					ActionErrors errors = new ActionErrors();
					errors.add("global", new ActionError("no.assembly.selected"));
					this.saveErrors(request, errors);
					return mapping.findForward("noAssembly");
				}
				Long assemblyId = new Long(paramValue);
				GenomeAssembly assembly = persistentDomainObjectMgr.getPersistentGenomeAssembly(assemblyId);
				assemblyCol.add(assembly);
			}
		}
		GenomeAssembly[] assemblies = new GenomeAssembly[0];
		assemblies = (GenomeAssembly[])assemblyCol.toArray(assemblies);
		
		// Determine if user wants to use probe locations that were submitted
		// with data
		boolean useGivenLocations = 
			(request.getParameter("useGivenLocations") == null)? false : true;
		
		// Map probes
		ReporterMappingStagingArea area = 
			AttributeManager.getProbeMappingStagingArea(request);
		ExperimentProbeMappingResults[] results =
			area.mapProbes(assemblies, useGivenLocations);
		Collection resultsCol = CollectionUtils.arrayToArrayList(results);
		
		// Add genome array data sets and assemblies to shopping cart
		ShoppingCart cart = AttributeManager.getShoppingCart(request);
		Experiment[] experiments = area.getExperiments();
		cart.add(experiments);
		cart.addAssemblies(assemblies);
		
		// Attache results to request attributes
		request.setAttribute("probeMappingResults", resultsCol);
		
		return mapping.findForward("success");
	}

}
