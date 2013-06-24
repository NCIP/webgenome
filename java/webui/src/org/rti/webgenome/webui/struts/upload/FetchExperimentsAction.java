/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.5 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Fetch experiments from remote system.
 * @author dhall
 *
 */
public class FetchExperimentsAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(FetchExperimentsAction.class);
	
	
	// TODO: Make the organism a parameter that gets passed in
	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		LOGGER.info("Fetching experiments from remote system");
		SelectedRemoteExperimentsForm sForm =
			(SelectedRemoteExperimentsForm) form;
		DataSourceSession sess = PageContext.getDataSourceSession(request);
		ShoppingCart cart = this.getShoppingCart(request);
		Collection<String> expIds = sForm.getSelectedExperimentIds();
		Long organismId = Long.valueOf(sForm.getOrganismId());
		Organism organism = this.getDbService().loadOrganism(organismId);
		QuantitationType qType = QuantitationType.getQuantitationType(
				sForm.getQuantitationTypeId());
		Collection<Experiment> experiments =
			sess.fetchExperiments(expIds, qType);
        ColorChooser colorChooser = cart.getBioassayColorChooser();
        for (Experiment exp : experiments) {
        	Long expId = this.getExperimentIdGenerator().nextId();
        	exp.setId(expId);
        	exp.setOrganism(organism);
        	this.getIoService().convertBioAssays(exp);
        	for (BioAssay ba : exp.getBioAssays()) {
        		ba.setColor(colorChooser.nextColor());
        		ba.setId(this.getBioAssayIdGenerator().nextId());
        		ba.setOrganism(organism);
        	}
        	cart.add(exp);
        	if (PageContext.standAloneMode(request)) {
    			this.getDbService().addArraysAndUpdateCart(exp, cart);
    		}
        }
		
		ActionForward forward = mapping.findForward("non.batch");
		return forward;
	}
}
