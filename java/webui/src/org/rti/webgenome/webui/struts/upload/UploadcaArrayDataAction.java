/*
$Revision: 1.1 $
$Date: 2008-10-23 16:17:18 $


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
 * This class will connect with caArray, retrieve experiment data and
 * initialize UploadDataSourceProperties object.
 * 
 * @author vbakalov
 *
 */
public class UploadcaArrayDataAction extends BaseAction {
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
					
		//TODO: Delete this, VB added just for test
		System.out.println(upload.print2Buff());
		
		ActionForward forward = null;
		if (ProcessingModeDecider.processInBackground(
				upload, request, this.getIoService())) {
			Principal principal = PageContext.getPrincipal(request);
			DataImportJob job = new DataImportJob(upload,
					principal.getId(), principal.getDomain());
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
