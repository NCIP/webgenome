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

import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.io.UcscCytologicalMapReader;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * Loads cytobands uploaded by user.
 * @author dhall
 *
 */
public final class LoadCytobandsAction extends BaseAction {


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
    	
    	// Get input stream to uploaded file
    	AnnotationUploadForm cForm = (AnnotationUploadForm) form;
    	InputStream in = cForm.getFormFile().getInputStream();
    	
    	// Get organism
    	Long orgId = Long.parseLong(cForm.getOrganismId());
    	Organism org = this.getDbService().loadOrganism(orgId);
    	
    	// Parse out cytological maps from form file
    	UcscCytologicalMapReader reader = new UcscCytologicalMapReader(org);
    	Collection<CytologicalMap> maps = reader.read(in);
    	
    	// Load cytological maps in database
    	for (CytologicalMap map : maps) {
    		this.getCytologicalMapDao().save(map);
    	}
    	
    	return mapping.findForward("success");
    }
}
