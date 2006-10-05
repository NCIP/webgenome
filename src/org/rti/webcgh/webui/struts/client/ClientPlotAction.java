/*
$Revision: 1.2 $
$Date: 2006-10-05 22:09:05 $

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

package org.rti.webcgh.webui.struts.client;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.client.ClientDataService;
import org.rti.webcgh.webui.struts.BaseAction;
import org.rti.webcgh.webui.util.ClientQueryParser;
import org.rti.webcgh.webui.util.PageContext;
import org.rti.webcgh.webui.util.SessionMode;
import org.rti.webgenome.client.BioAssayDataConstraints;

/**
 * Action that is invoked when a user is initially directed
 * to webGenome from a client application.
 */
public final class ClientPlotAction extends BaseAction {
    
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(ClientPlotAction.class);
	
	
	/** Client data service. This property should be injected. */
    private ClientDataService clientDataService = null;


    /**
     * Set the client data service.
     * @param clientDataService Client data service
     */
    public void setClientDataService(
    		final ClientDataService clientDataService) {
		this.clientDataService = clientDataService;
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
	public ActionForward execute(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request, 
    		final HttpServletResponse response) throws Exception {
		LOGGER.info("Starting ClientPlotAction");
		
		// Construct parameters for obtaining data through
		// the client data service
        String clientID = request.getParameter("clientID");
        String[] experimentIds = ClientQueryParser.getExperimentIds(request);
        BioAssayDataConstraints[] constraints =
        	ClientQueryParser.getBioAssayDataConstraints(request);
        
        // Retrieve data from client
        Collection<Experiment> experiments = 
        	this.clientDataService.getClientData(constraints,
        			experimentIds, clientID);
        
        // Put data in shopping cart
        ShoppingCart cart = new ShoppingCart();
        PageContext.setShoppingCart(request, cart);
        cart.add(experiments);
        
        // Set session mode
        PageContext.setSessionMode(request, SessionMode.CLIENT);
        
		return mapping.findForward("success");
	}
}
