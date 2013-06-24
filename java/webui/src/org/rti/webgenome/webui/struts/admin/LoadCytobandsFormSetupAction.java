/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Setup action for JSP that shows currently uploaded cytological maps
 * and enables the user to upload files containing cytobands.
 * @author dhall
 *
 */
public final class LoadCytobandsFormSetupAction extends BaseAction {
	

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
    	
    	// Get all organisms containing cytological maps and attach to request
    	List<CytologicalMap> cytologicalMaps =
    		this.getCytologicalMapDao().loadAll();
    	Set<Organism> organismsWithMap = new HashSet<Organism>();
    	for (CytologicalMap map : cytologicalMaps) {
    		organismsWithMap.add(map.getOrganism());
    	}
    	request.setAttribute("organismsWithMap", organismsWithMap);
    	
    	return mapping.findForward("success");
    }
}
