/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/DataSelectionForm.java,v $
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
package org.rti.webcgh.webui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.util.CollectionUtils;
import org.rti.webcgh.webui.util.DataIdEncoder;

/**
 * Form used in the data selection process
 */
public class DataSelectionForm extends ActionForm {
    
    
    // ======================================================
    //      Attributes with accessors and mutators
    // ======================================================
    
    private String[] experimentIds = new String[0];
    private String[] databaseNames = new String[0];
    private String[] bioAssayIds = new String[0];
    

    /**
     * @return Returns the selectedExperimentNames.
     */
    public String[] getExperimentIds() {
        return experimentIds;
    }
    
    
    /**
     * @return Returns the selectedDatabaseNames.
     */
    public String[] getDatabaseNames() {
        return databaseNames;
    }
    
    
    /**
     * @return Returns the bioAssayNames.
     */
    public String[] getBioAssayIds() {
        return bioAssayIds;
    }
    
    
    // ====================================================
    //         Overridden methods from ActionForm
    // ====================================================
    
    
    /**
     * Validate method is used in this class to populate dynamic fields
     * @param mapping Action mapping
     * @param request Request
     * @return null
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = null;
        
        // Populate array properties
        Collection experimentIdsCol = new ArrayList();
        Collection databaseNamesCol = new ArrayList();
        Collection bioAssayIdsCol = new ArrayList();
        int recordCount = 0;
        for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
            String paramName = (String)en.nextElement();
            if (DataIdEncoder.encodesIds(paramName)) {
                databaseNamesCol.add(DataIdEncoder.decodeDatabaseName(paramName));
                experimentIdsCol.add(DataIdEncoder.decodeExperimentId(paramName));
                bioAssayIdsCol.add(DataIdEncoder.decodeBioAssayId(paramName));
                recordCount++;
            }
        }
        if (recordCount > 0) {
            this.experimentIds = new String[0];
	        this.databaseNames = new String[0];
	        this.bioAssayIds = new String[0];
	        this.experimentIds = (String[])experimentIdsCol.toArray(this.experimentIds);
	        this.databaseNames = (String[])databaseNamesCol.toArray(this.databaseNames);
	        this.bioAssayIds = (String[])bioAssayIdsCol.toArray(this.bioAssayIds);
        }
        
        // Validate
        if (recordCount < 1) {
            errors = new ActionErrors();
            errors.add("global", new ActionError("no.experiments.selected"));
        }
        
        return errors;
    }
    
    
    // =======================================================
    //     Public methods
    // =======================================================
    
    
    /**
     * Get bioassays as a Collection object
     * @return bioassays
     */
    public Collection getBioassaysAsCollection() {
        return CollectionUtils.arrayToArrayList(this.bioAssayIds);
    }
}
