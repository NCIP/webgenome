/*
$Revision: 1.2 $
$Date: 2008-02-15 23:28:58 $

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
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.service.dao.OrganismDao;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.service.util.IdGenerator;
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
	
    /** Experiment ID generator. */
    private IdGenerator experimentIdGenerator = null;
    
    /** Bioassay ID generator. */
    private IdGenerator bioAssayIdGenerator = null;
    
    /** Organism data access object. */
    private OrganismDao organismDao = null;
    
    /**
     * Set organism data access object.
     * @param organismDao Organism data access object
     */
	public void setOrganismDao(final OrganismDao organismDao) {
		this.organismDao = organismDao;
	}
	
	
	/**
     * Set bioassay ID generator.
     * @param bioAssayIdGenerator ID generator
     */
	public void setBioAssayIdGenerator(
			final IdGenerator bioAssayIdGenerator) {
		this.bioAssayIdGenerator = bioAssayIdGenerator;
	}


	/**
	 * Set experiment ID generator.
	 * @param experimentIdGenerator ID generator
	 */
	public void setExperimentIdGenerator(
			final IdGenerator experimentIdGenerator) {
		this.experimentIdGenerator = experimentIdGenerator;
	}
	
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
		Collection<Experiment> experiments = sess.fetchExperiments(expIds);
        ColorChooser colorChooser = cart.getBioassayColorChooser();
        Organism org = this.organismDao.loadDefault();
        for (Experiment exp : experiments) {
        	Long expId = this.experimentIdGenerator.nextId();
        	exp.setId(expId);
        	exp.setOrganism(org);
        	for (BioAssay ba : exp.getBioAssays()) {
        		ba.setColor(colorChooser.nextColor());
        		ba.setId(this.bioAssayIdGenerator.nextId());
        	}
        }
		cart.add(experiments);
		this.persistShoppingCartChanges(cart, request);
		ActionForward forward = mapping.findForward("non.batch");
		return forward;
	}
}
