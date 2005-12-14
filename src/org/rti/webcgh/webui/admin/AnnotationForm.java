/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/admin/AnnotationForm.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


/**
 * Form for adding annotation feature types
 */
public class AnnotationForm extends AdminUploadForm {
    
    // ======================================================
    //    Attributes with accessors and mutators
    // ======================================================
    
    private String featureTypeName = null;
    private String geneSwitch = null;
    private String exonStartsCol = "9";
    private String exonEndsCol = "10";
 
    
    /**
     * @return Returns the exonEndsCol.
     */
    public String getExonEndsCol() {
        return exonEndsCol;
    }
    
    
    /**
     * @param exonEndsCol The exonEndsCol to set.
     */
    public void setExonEndsCol(String exonEndsCol) {
        this.exonEndsCol = exonEndsCol;
    }
    
    
    /**
     * @return Returns the exonStartsCol.
     */
    public String getExonStartsCol() {
        return exonStartsCol;
    }
    
    
    /**
     * @param exonStartsCol The exonStartsCol to set.
     */
    public void setExonStartsCol(String exonStartsCol) {
        this.exonStartsCol = exonStartsCol;
    }
    
    
    /**
     * @return Returns the featureTypeName.
     */
    public String getFeatureTypeName() {
        return featureTypeName;
    }
    
    
    /**
     * @param featureTypeName The featureTypeName to set.
     */
    public void setFeatureTypeName(String featureTypeName) {
        this.featureTypeName = featureTypeName;
    }
    
    
    /**
     * @return Returns the geneSwitch.
     */
    public String getGeneSwitch() {
        return geneSwitch;
    }
    
    
    /**
     * @param geneSwitch The geneSwitch to set.
     */
    public void setGeneSwitch(String geneSwitch) {
        this.geneSwitch = geneSwitch;
    }
    
    
    
    // =============================================
    //        Constructors
    // =============================================
    
    /**
     * Constructor
     */
    public AnnotationForm() {
        this.nameCol = "1";
        this.chromCol = "2";
        this.startCol = "4";
        this.endCol = "5";
    }
    
    
    // =================================================
    //      Over-ridden methods
    // =================================================
    
    
    /**
     * Validate form fields
     * @param mapping Routing information
     * @param request Servlet request object
     * @return Errors
     */
    public ActionErrors validate
    (
        ActionMapping mapping, HttpServletRequest request
    ) {
        ActionErrors errors = super.validate(mapping, request);
        
        // featureTypeName
        if (featureTypeName == null || featureTypeName.length() < 1)
            errors.add("featureTypeName", new ActionError("invalid.field"));
              
        // exon parameters
        if ("on".equals(this.getGeneSwitch())) {
            if (this.exonStartsCol == null || this.exonStartsCol.length() < 1)
                errors.add("exonStartsCol", new ActionError("invalid.field"));
            if (this.exonEndsCol == null || this.exonEndsCol.length() < 1)
                errors.add("exonEndsCol", new ActionError("invalid.field"));
        }
        
        if (errors.size() > 0)
        	errors.add("global", new ActionError("invalid.fields"));
        
        return errors;
    }
}
