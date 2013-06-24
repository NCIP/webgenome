/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * This action is reponsible for deleting all gene data from
 * a particular organism.
 * @author dhall
 *
 */
public final class DeleteGenesAction extends BaseAction {
	
	
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
    	
    	// Get organism
    	Long orgId = new Long(request.getParameter("orgId"));
    	Organism org = this.getDbService().loadOrganism(orgId);
    	
    	// Delete genes
    	this.getAnnotatedGenomeFeatureDao().deleteAll(AnnotationType.GENE, org);
    	
    	return mapping.findForward("success");
    }

}
