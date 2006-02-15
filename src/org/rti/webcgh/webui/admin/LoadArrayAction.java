/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/admin/LoadArrayAction.java,v $
$Revision: 1.1 $
$Date: 2006-02-15 20:54:47 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package org.rti.webcgh.webui.admin;

import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.rti.webcgh.etl.AnnotationColumnMapping;
import org.rti.webcgh.etl.ArrayEtlManager;
import org.rti.webcgh.webui.util.AdminSessionUtils;


/**
 * Loads probe set data
 */
public class LoadArrayAction extends Action {

	private ArrayEtlManager arrayEtlManager = null;
	
    
    public void setArrayEtlManager(ArrayEtlManager arrayEtlManager) {
		this.arrayEtlManager = arrayEtlManager;
	}


	/**
     * Performs actions
     *
     * @param mapping Routing information for downstream actions
     * @param form Data from calling form
     * @param request Servlet request object
     * @param response Servlet response object
     * @return Identification of downstream action as configured in the
     * struts-config.xml file
     * @throws Exception
     */
    public ActionForward execute
    (
        ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        AdminSessionUtils.ensureAdminLoggedIn(request);
        ProbeSetForm aForm = (ProbeSetForm)form;
        AnnotationColumnMapping colMapping = new AnnotationColumnMapping();
        colMapping.setNameCol(Integer.parseInt(aForm.getNameCol()) - 1);
        colMapping.setChromCol(Integer.parseInt(aForm.getChromCol()) - 1);
        colMapping.setStartCol(Integer.parseInt(aForm.getStartCol()) - 1);
        colMapping.setEndCol(Integer.parseInt(aForm.getEndCol()) - 1);
        FormFile formFile = aForm.getFormFile();
        Reader reader = new InputStreamReader(formFile.getInputStream());
        arrayEtlManager.load(reader, colMapping, aForm.getArrayName(), aForm.getArrayVendor(), aForm.getAssembly(), 
            aForm.getGenus(), aForm.getSpecies());
        return mapping.findForward("success");
    }
}
