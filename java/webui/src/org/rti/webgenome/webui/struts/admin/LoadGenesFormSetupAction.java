/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


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
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Action that sets up request attributes for
 * the JSP that shows which sets of genese have
 * been loaded.
 * @author dhall
 *
 */
public final class LoadGenesFormSetupAction extends BaseAction {

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
    	List<Organism> organisms = this.getDbService().loadAllOrganisms();
    	request.setAttribute("organisms",  organisms);
    	
    	// Get organisms that have uploaded gene data
    	Set<Organism> organismsWithGeneData =
    		this.getAnnotatedGenomeFeatureDao().organismsWithLoadedGenes();
    	
    	// Attach attributes to request
    	request.setAttribute("organisms", organisms);
    	request.setAttribute("organismsWithGeneData",
    			organismsWithGeneData);
    	
    	return mapping.findForward("success");
    }

}
