/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/plot/PlotAction.java,v $
$Revision: 1.2 $
$Date: 2006-03-29 22:26:30 $

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

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.array.DataSet;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.array.ShoppingCart;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.drawing.SvgDrawingCanvas;
import org.rti.webcgh.graph.PlotGenerator;
import org.rti.webcgh.graph.PlotParameters;
import org.rti.webcgh.webui.util.Attribute;
import org.rti.webcgh.webui.util.AttributeManager;
import org.rti.webcgh.webui.util.SvgUtils;
import org.rti.webcgh.webui.util.WebUtils;
import org.w3c.dom.Document;

/**
 * 
 */
public class PlotAction extends Action {
    
    
    private static final Logger LOGGER = Logger.getLogger(PlotAction.class);
    
    
    private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
    private PlotGenerator plotGenerator = null;
    
    /**
     * @param plotGenerator The plotGenerator to set.
     */
    public void setPlotGenerator(PlotGenerator plotGenerator) {
        this.plotGenerator = plotGenerator;
    }
    
    
    /**
     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentDomainObjectMgr) {
        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
    }
    
    
    /**
     * Performs action of creating scatter plot
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
        LOGGER.info("Starting 'PlotAction'");
        
		HttpSession session = request.getSession();
        
        try {
        	
            // Get plotting parameters
        	PlotParamsForm pform = (PlotParamsForm)form;
        	PlotParameters plotParameters = pform.getPlotParameters();
        	
			//Recover data set which was assembled by the PlotSetupAction
			Experiment[] experiments =
				WebUtils.recoverDataSet(pform, request, this.persistentDomainObjectMgr,
						plotParameters);
			DataSet dataSet = new DataSet(experiments);
			
			// Get quantitation type
			ShoppingCart cart = AttributeManager.getShoppingCart(request);
			QuantitationType qType = null;
			String quantTypeKey = pform.getQuantitationTypeId();
			if (quantTypeKey == null || quantTypeKey.length() < 1) {
			    Set qTypes = cart.quantitationTypes();
				if (qTypes.size() > 0)
				    qType = (QuantitationType)qTypes.iterator().next();
			} else
			    qType = cart.getQuantitationType(quantTypeKey);
			
			// Create plot
			SvgDrawingCanvas canvas = SvgDrawingCanvas.newPlottingCanvas();
			this.plotGenerator.createPlot(dataSet, plotParameters, qType, canvas);
			
			// Attach plot
			request.setAttribute(Attribute.SVG, canvas.getDocument());
	        
        } catch (Exception e) {
            Document errorMsg = SvgUtils.getSvgException();
        	session.setAttribute(Attribute.SVG, errorMsg);
        	LOGGER.error(e);
        	e.printStackTrace();
        }
                        
        LOGGER.info("Completed action 'ScatterPlotAction'");
        return mapping.findForward("success");
    }
    

}
