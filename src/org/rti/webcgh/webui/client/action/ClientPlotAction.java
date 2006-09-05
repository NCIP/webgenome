/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/client/action/ClientPlotAction.java,v $
$Revision: 1.4 $
$Date: 2006-09-05 14:06:46 $

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

package org.rti.webcgh.webui.client.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.array.ShoppingCart;
import org.rti.webcgh.deprecated.Units;
import org.rti.webcgh.service.util.ClientDataService;
import org.rti.webcgh.webui.client.util.ClientQueryParser;
import org.rti.webcgh.webui.plot.PlotParamsForm;
import org.rti.webcgh.webui.util.AttributeManager;
import org.rti.webgenome.client.BioAssayDataConstraints;

/**
 * 
 */
public class ClientPlotAction extends Action {
    
	private static final Logger LOGGER = Logger.getLogger(ClientPlotAction.class);
	
    private ClientDataService clientDataService = null;


    public void setClientDataService(ClientDataService clientDataService) {
		this.clientDataService = clientDataService;
	}


	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, 
    		HttpServletResponse response) throws Exception {
		LOGGER.info("Starting ClientPlotAction");
		PlotParamsForm pform = (PlotParamsForm)form;
        String clientID = request.getParameter("clientID");
        
        String[] experimentIDs = ClientQueryParser.getExperimentIds(request);
        BioAssayDataConstraints[] constraints = ClientQueryParser.getBioAssayDataConstraints(request);
        
        // Add constraints to form bean
        pform.setGenomeIntervals(GenomeIntervalDto.encode(constraints));
        pform.setUnits(Units.BP.getName());
        
        // TODO: Remove before deployment
//        String[] firstExpArray = new String[1];
//        firstExpArray[0] = experimentIDs[0];
//        experimentIDs = firstExpArray;
        
        Experiment[] experiments = clientDataService.getClientData(constraints, experimentIDs, clientID);
        
        ShoppingCart cart = AttributeManager.getShoppingCart(request);
        cart.purgeClientData();
        cart.add(experiments);
        
        request.setAttribute("invocation.from.client", "true");
        
		return mapping.findForward("success");
	}
}