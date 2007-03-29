/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $

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

package org.rti.webgenome.webui.struts.admin;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.AnnotatedGenomeFeatureDao;
import org.rti.webgenome.service.dao.OrganismDao;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Action that sets up request attributes for
 * the JSP that shows which sets of genese have
 * been loaded.
 * @author dhall
 *
 */
public final class LoadGenesFormSetupAction extends BaseAction {
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * DAO for getting annotated genome features.  This property
	 * must be set via dependency injection.
	 */
	private AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao = null;
	
	/**
	 * Organism data access object.  This property
	 * must be set via dependency injection.
	 */
	private OrganismDao organismDao = null;
	
	//
	//     SETTERS (for dependency injection)
	//
	
	
	/**
	 * Set DAO for getting annotated genome features.  This property
	 * must be set via dependency injection.
	 * @param annotatedGenomeFeatureDao DAO for getting annotated
	 * genome features
	 */
	public void setAnnotatedGenomeFeatureDao(
			final AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao) {
		this.annotatedGenomeFeatureDao = annotatedGenomeFeatureDao;
	}
	
	
	/**
	 * Set organism data access object.  This property
	 * must be set via dependency injection.
	 * @param organismDao Organims data access object.
	 */
	public void setOrganismDao(final OrganismDao organismDao) {
		this.organismDao = organismDao;
	}
	
	
	//
	//     OVERRIDDEN METHODS
	//

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
    	
    	// Get all organisms and attach to request
    	List<Organism> organisms = this.organismDao.loadAll();
    	request.setAttribute("organisms",  organisms);
    	
    	// Get organisms that have uploaded gene data
    	Set<Organism> organismsWithGeneData =
    		this.annotatedGenomeFeatureDao.organismsWithLoadedGenes();
    	
    	// Attach attributes to request
    	request.setAttribute("organisms", organisms);
    	request.setAttribute("organismsWithGeneData",
    			organismsWithGeneData);
    	
    	return mapping.findForward("success");
    }

}
