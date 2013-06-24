/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-10-23 16:17:18 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.client.CaArrayClient;
import org.rti.webgenome.service.job.CaArrayDataImportJob;
import org.rti.webgenome.service.job.DataImportJob;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * Performs uploading of caArray data.
 * 
 * @author vbakalov
 */
public class UploadCAArrayAction extends BaseAction {
	
	

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		CaArrayClient client = PageContext.getCaArrayClient(request);
		
		if (client == null)
			throw new WebGenomeApplicationException("caArray client is NULL");
		
		String expId = request.getParameter("expId");
		if ( expId == null || expId.equals(""))
			throw new WebGenomeApplicationException("caArray experiment Id is missing...");
		
		// Check if already in the queue and don't process
		if (PageContext.isSelCaArrayExperimentProcessed(request, expId))
			return mapping.findForward("batch");
		
		// TODO:check if the experiment is already in the workspace
		ShoppingCart cart = this.getShoppingCart(request);
		if (cart != null){
			gov.nih.nci.caarray.domain.project.Experiment caArrayExp = PageContext.getCaArrayExperimentTitle(expId, request);			
			Experiment exp = cart.getExperimentByName(caArrayExp.getTitle());
			// the experiment is already in the workspace so don't load it again
			if (exp != null){
				return mapping.findForward("non.batch");
			}
		}
			
		//UploadDataSourceProperties upload = client.downloadExperiment2File(expId);
		
		// Set QuantitationType since had some trouble to set it inside caArrayClient
		//upload.setQuantitationType(QuantitationType.LOG_2_RATIO_COPY_NUMBER);
		
					
		//TODO: Delete this, VB added just for test
		//System.out.println(upload.print2Buff());
		
		ActionForward forward = null;
		/*if (ProcessingModeDecider.processInBackground(
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
			cart = this.getShoppingCart(request);
			Experiment exp = this.getIoService().loadSmdData(upload, cart);
			this.getDbService().addArraysAndUpdateCart(exp, cart);
			forward = mapping.findForward("non.batch");
		}*/
		
		// Batch all jobs
		Principal principal = PageContext.getPrincipal(request);
		CaArrayDataImportJob job = new CaArrayDataImportJob(client, expId, principal.getId(), principal.getDomain());
		this.getJobManager().add(job);
		ActionMessages messages = new ActionMessages();
		messages.add("global", new ActionMessage("import.job"));
		this.saveMessages(request, messages);
		forward = mapping.findForward("batch");
		
		PageContext.removeUpload(request);
		PageContext.add2SelCaArrayExperiments(request, expId);
		return forward;
	}
}
