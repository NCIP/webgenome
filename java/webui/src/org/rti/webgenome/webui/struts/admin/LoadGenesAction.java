/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.webui.struts.admin;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.io.UcscGeneReader;
import org.rti.webgenome.webui.struts.BaseAction;

/**
 * This action is responsible for uploading gene data
 * from a UCSC database dump file.  Documentation
 * of the expected file format can be found in
 * org.rti.webcgh.service.io.UcscGeneReader.
 * 
 * @author dhall
 * @see org.rti.webgenome.service.io.UcscGeneReader
 */
public final class LoadGenesAction extends BaseAction {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(LoadGenesAction.class);
	

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
    	Reader reader = new InputStreamReader(in);
    	
    	// Get organism
    	Long orgId = Long.parseLong(cForm.getOrganismId());
    	Organism org = this.getDbService().loadOrganism(orgId);
    	
    	// Load data
    	UcscGeneReader geneReader = new UcscGeneReader(reader, org);
    	while (geneReader.hasNext()) {
    		try {
    			AnnotatedGenomeFeature feat = geneReader.next();
    			this.getAnnotatedGenomeFeatureDao().save(feat);
    		} catch (Exception e) {
    			LOGGER.warn("Unable to load feature: "
    					+ e.getMessage());
    		}
    	}
    	
    	return mapping.findForward("success");
    }
}
