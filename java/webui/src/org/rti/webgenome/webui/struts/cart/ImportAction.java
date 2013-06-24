/*
$Revision: 1.8 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.service.client.ClientDataServiceManager;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Imports data from a client application and
 * deposits in shopping cart.
 * @author dhall
 *
 */
public final class ImportAction extends BaseAction {
	
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
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	
    	// Retrieve selected experiments form bean.
    	// Note, this is not the form bean configured
    	// for this action in struts-config.xml.
    	SelectedExperimentsForm seForm =
    		PageContext.getSelectedExperimentsForm(request, false);
    	if (seForm == null) {
    		throw new SessionTimeoutException(
    				"Could not find selected experiments");
    	}
    	Collection<Long> ids = seForm.getSelectedExperimentIds();
    	
    	// Get selected experiments from cart
    	ShoppingCart cart = this.getShoppingCart(request);
    	Collection<Experiment> selectedExperiments =
    		cart.getExperiments(ids);
    	
    	// Get bioassay data constraints from selected experiments
    	Collection<BioAssayDataConstraints> constraints =
    		Experiment.getBioAssayDataConstraints(selectedExperiments);
    	
    	// Set quantitation type
    	QuantitationTypeForm qForm = (QuantitationTypeForm) form;
    	String qType = qForm.getQuantitationTypeId();
    	for (BioAssayDataConstraints c : constraints) {
    		c.setQuantitationType(qType);
    	}
    	
    	// TODO: Create specific exception for not
    	// unsupported quantitation types
    	
    	// Get data from client.  If an exception is thrown,
    	// we assume that data for 
    	Collection<Experiment> experiments = null;
    	try {
	    	ClientDataServiceManager mgr =
	    		PageContext.getClientDataServiceManager(request);
	    	experiments = mgr.importData(selectedExperiments, constraints);
	    	int numBioAssays = 0;
	    	for (Experiment exp : experiments) {
	    		numBioAssays += exp.getBioAssays().size();
	    	}
	    	if (numBioAssays < 1) {
	    		throw new WebGenomeApplicationException("No bioassays found");
	    	}
    	} catch (Exception e) {
    		ActionErrors errors = new ActionErrors();
    		errors.add("global", new ActionError("data.not.found"));
    		this.saveErrors(request, errors);
    		return mapping.findForward("unsupported.quantitation.type");
    	}
    	
    	// Give each experiment a unique ID and default
        // organism.  Give each bioassay a color and ID
    	
    	// TODO: Make organism a parameter that gets passed in
        ColorChooser colorChooser = cart.getBioassayColorChooser();
        Organism org = this.getDbService().loadDefaultOrganism();
        for (Experiment exp : experiments) {
        	Long expId = this.getExperimentIdGenerator().nextId();
        	exp.setId(expId);
        	exp.setOrganism(org);
        	for (BioAssay ba : exp.getBioAssays()) {
        		ba.setColor(colorChooser.nextColor());
        		ba.setId(this.getBioAssayIdGenerator().nextId());
        	}
        }
        
        // Put data in shopping cart
        cart.add(experiments);
        if (PageContext.standAloneMode(request)) {
        	this.getDbService().updateShoppingCart(cart);
        }

    	return mapping.findForward("success");
    }
}
