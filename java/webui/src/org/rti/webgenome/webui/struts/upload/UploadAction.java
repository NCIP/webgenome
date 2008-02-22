/*
$Revision: 1.9 $
$Date: 2008-02-22 18:24:43 $

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

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.job.DataImportJob;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * Performs uploading of data.
 * @author dhall
 */
public class UploadAction extends BaseAction {
	

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		UploadDataSourceProperties upload = PageContext.getUpload(request);
		UploadForm uForm = (UploadForm) form;
		upload.setChromosomeColumnName(uForm.getChromosomeColumnName());
		upload.setExperimentName(uForm.getExperimentName());
		Long orgId = new Long(uForm.getOrganismId());
		Organism org = this.getDbService().loadOrganism(orgId);
		upload.setOrganism(org);
		upload.setPositionUnits(BpUnits.getUnits(uForm.getUnits()));
		upload.setPositionColumnName(uForm.getPositionColumnName());
		upload.setQuantitationType(
				QuantitationType.getQuantitationType(
						uForm.getQuantitationTypeId()));
		ActionForward forward = null;
		if (ProcessingModeDecider.processInBackground(
				upload, request, this.getIoService())) {
			Principal principal = PageContext.getPrincipal(request);
			DataImportJob job = new DataImportJob(upload,
					principal.getName(), principal.getDomain());
			this.getJobManager().add(job);
			ActionMessages messages = new ActionMessages();
    		messages.add("global", new ActionMessage("import.job"));
    		this.saveMessages(request, messages);
			forward = mapping.findForward("batch");
		} else {
			ShoppingCart cart = this.getShoppingCart(request);
			Experiment exp = this.getIoService().loadSmdData(upload, cart);
			this.getDbService().addArraysAndUpdateCart(exp, cart);
			forward = mapping.findForward("non.batch");
		}
		PageContext.removeUpload(request);
		return forward;
	}
}
