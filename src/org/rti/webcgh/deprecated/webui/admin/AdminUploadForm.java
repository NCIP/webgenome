/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/webui/admin/AdminUploadForm.java,v $
$Revision: 1.1 $
$Date: 2006-10-23 02:20:39 $

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
package org.rti.webcgh.deprecated.webui.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.rti.webcgh.util.ValidationUtils;

/**
 * Base class for all administrator upload forms
 */
public class AdminUploadForm extends ActionForm {
    
    
    // ======================================================
    //     Attributes with accessors and mutators
    // ======================================================
    
    protected FormFile formFile = null;
    protected String assembly = null;
    protected String genus = "Homo";
    protected String species = "sapiens";
    protected String nameCol = null;
    protected String chromCol = null;
    protected String startCol = null;
    protected String endCol = null;
    

    /**
     * @return Returns the assembly.
     */
    public String getAssembly() {
        return assembly;
    }
    
    
    /**
     * @param assembly The assembly to set.
     */
    public void setAssembly(String assembly) {
        this.assembly = assembly;
    }
    
    
    /**
     * @return Returns the chromCol.
     */
    public String getChromCol() {
        return chromCol;
    }
    
    
    /**
     * @param chromCol The chromCol to set.
     */
    public void setChromCol(String chromCol) {
        this.chromCol = chromCol;
    }
    
    
    /**
     * @return Returns the endCol.
     */
    public String getEndCol() {
        return endCol;
    }
    
    
    /**
     * @param endCol The endCol to set.
     */
    public void setEndCol(String endCol) {
        this.endCol = endCol;
    }
    
    
    /**
     * @return Returns the formFile.
     */
    public FormFile getFormFile() {
        return formFile;
    }
    
    
    /**
     * @param formFile The formFile to set.
     */
    public void setFormFile(FormFile formFile) {
        this.formFile = formFile;
    }
    
    
    /**
     * @return Returns the genus.
     */
    public String getGenus() {
        return genus;
    }
    
    
    /**
     * @param genus The genus to set.
     */
    public void setGenus(String genus) {
        this.genus = genus;
    }
    
    
    /**
     * @return Returns the nameCol.
     */
    public String getNameCol() {
        return nameCol;
    }
    
    
    /**
     * @param nameCol The nameCol to set.
     */
    public void setNameCol(String nameCol) {
        this.nameCol = nameCol;
    }
    
    
    /**
     * @return Returns the species.
     */
    public String getSpecies() {
        return species;
    }
    
    
    /**
     * @param species The species to set.
     */
    public void setSpecies(String species) {
        this.species = species;
    }
    
    
    /**
     * @return Returns the startCol.
     */
    public String getStartCol() {
        return startCol;
    }
    
    
    /**
     * @param startCol The startCol to set.
     */
    public void setStartCol(String startCol) {
        this.startCol = startCol;
    }
    
    
    // =========================================================
    //         Overridden methods
    // =========================================================
    
    /**
     * Reset form
     * @param mapping Action mapping
     * @param request Servlet request
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        this.formFile = null;
    }
    
    
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
        ActionErrors errors = new ActionErrors();
        
        //assembly
        if (assembly == null || assembly.length() < 1)
            errors.add("assembly", new ActionError("invalid.field"));
        
        // genus
        if (genus == null || genus.length() < 1)
            errors.add("genus", new ActionError("invalid.field"));
        
        // species
        if (species == null || species.length() < 1)
            errors.add("species", new ActionError("invalid.field"));
        
        // chromCol
        if (chromCol == null || chromCol.length() < 1 || ! ValidationUtils.validPositiveInteger(chromCol))
            errors.add("chromCol", new ActionError("invalid.field"));
        
        // endCol
        if (endCol == null || endCol.length() < 1 || ! ValidationUtils.validPositiveInteger(endCol))
            errors.add("endCol", new ActionError("invalid.field"));
        
        // nameCol
        if (nameCol == null || nameCol.length() < 1 || ! ValidationUtils.validPositiveInteger(nameCol))
            errors.add("nameCol", new ActionError("invalid.field"));
        
        // startCol
        if (startCol == null || startCol.length() < 1 || ! ValidationUtils.validPositiveInteger(startCol))
            errors.add("startCol", new ActionError("invalid.field"));
        
        // formFile
        if (formFile == null || formFile.getFileName() == null || formFile.getFileName().length() < 1)
            errors.add("formFile", new ActionError("invalid.field"));
        
        if (errors.size() > 0)
        	errors.add("global", new ActionError("invalid.fields"));
        
        return errors;
    }
}
