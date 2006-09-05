/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/virtual/CreateVirtualExperimentAction.java,v $
$Revision: 1.3 $
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


package org.rti.webcgh.webui.virtual;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.persistent.PersistentBioAssay;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.array.persistent.PersistentExperiment;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.service.authentication.UserProfile;
import org.rti.webcgh.service.datasrc.WebcghArrayDataSourceSet;
import org.rti.webcgh.webui.DataSelectionForm;
import org.rti.webcgh.webui.util.AttributeManager;
import org.springframework.web.struts.ActionSupport;


/**
 * Struts action class that creates a virtual experiment
 */
public class CreateVirtualExperimentAction extends ActionSupport {
    
    private static Logger logger = Logger.getLogger(CreateVirtualExperimentAction.class.getName());
    
    PersistentDomainObjectMgr persistentDomainObjectMgr = null;
    WebcghArrayDataSourceSet webcghArrayDataSourceSet = null;
    
    
    /**
     * @param webcghArrayDataSourceSet The webcghArrayDataSourceSet to set.
     */
    public void setWebcghArrayDataSourceSet(
            WebcghArrayDataSourceSet webcghArrayDataSourceSet) {
        this.webcghArrayDataSourceSet = webcghArrayDataSourceSet;
    }
    
    
    /**
     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentDomainObjectMgr) {
        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
    }
    
    
    /**
     * Performs action of creating a virtual experiment
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
        logger.info("Starting action 'CreateVirtualExperimentAction'");
		VirtualExperimentForm vef = (VirtualExperimentForm)form;
		DataSelectionForm dsForm = (DataSelectionForm)request.getSession().getAttribute("dataSelectionForm");
        
        // Create persistent experiment
        UserProfile profile = AttributeManager.getUserProfile(request);
        String expName = vef.getExperimentName();
        String description = vef.getDescription();
        PersistentExperiment exp = persistentDomainObjectMgr.newPersistentExperiment(
        		expName, description, "Virtual", true, profile.getName());
    
        // Add bioassays
        String[] dbNames = dsForm.getDatabaseNames();
        String[] experimentIds = dsForm.getExperimentIds();
        String[] bioAssayIds = dsForm.getBioAssayIds();
        assert dbNames.length == experimentIds.length && experimentIds.length ==  bioAssayIds.length;
        for (int i = 0; i < dbNames.length; i++) {
            Experiment tempExp = this.webcghArrayDataSourceSet.loadExperiment(experimentIds[i], dbNames[i], profile);
            if (tempExp == null)
                throw new WebcghSystemException("Cannot find experiment '" + experimentIds[i] +
                        "' in data source '" + dbNames[i] + "'");
            BioAssay tempBioAssay = tempExp.getBioAssay(bioAssayIds[i]);
            if (tempBioAssay == null)
                throw new WebcghSystemException("Cannot find bioassay '" + bioAssayIds[i] +
                        "' in data source '" + dbNames[i] + "'");
        	PersistentBioAssay bioAssay = persistentDomainObjectMgr.newPersistentBioAssay(bioAssayIds[i], "", dbNames[i]);
        	bioAssay.setBioAssayDataId(tempBioAssay.getBioAssayDataId());
        	bioAssay.update();
        	exp.add(bioAssay);
        }
        exp.update();
            
        logger.info("Completed action 'CreateVirtualExperimentAction'");
        
        return mapping.findForward("success");
    }
}
