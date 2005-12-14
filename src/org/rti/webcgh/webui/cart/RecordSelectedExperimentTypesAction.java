/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/cart/RecordSelectedExperimentTypesAction.java,v $
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
import org.rti.webcgh.array.BioAssayType;
import org.rti.webcgh.array.ReporterMappingStagingArea;
import org.rti.webcgh.util.CollectionUtils;
import org.rti.webcgh.webui.util.AttributeManager;
import org.rti.webcgh.webui.util.DataIdEncoder;


/**
 * Records selected experiment types
 */
public class RecordSelectedExperimentTypesAction extends Action {
	
	
	/**
	 * Performs acion
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
		
		// Get probe mapping staging area
		ReporterMappingStagingArea area = AttributeManager.getProbeMappingStagingArea(request);
		
		// Set probe sets
		for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
			String paramName = (String)en.nextElement();
			if (DataIdEncoder.encodesIds(paramName)) {
			    String dbName = DataIdEncoder.decodeDatabaseName(paramName);
				String expId = DataIdEncoder.decodeExperimentId(paramName);
				String bioassayId = DataIdEncoder.decodeBioAssayId(paramName);
				if (! "null".equals(bioassayId)) {
					String paramValue = request.getParameter(paramName);
					if ("none".equals(paramValue)) {
						ActionErrors errors = new ActionErrors();
						errors.add("global", new ActionError("no.experiment.types.selected"));
						this.saveErrors(request, errors);
						return mapping.findForward("noExperimentTypes");
					} else {
						BioAssayType type = BioAssayType.getBioAssayType(paramValue);
						area.setBioAssayType(dbName, expId, bioassayId, type);
					}
				}
			}
		}
		
		String[] splits = area.splitExperimentsOnBioAssayType();
		if (splits.length > 0) {
			Collection splitsCol = CollectionUtils.arrayToArrayList(splits);
			request.setAttribute("splits", splitsCol);
			return mapping.findForward("splits");
		}
		
		return mapping.findForward("success");
	}

}
