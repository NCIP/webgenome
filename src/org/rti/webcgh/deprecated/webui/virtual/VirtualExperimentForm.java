/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/webui/virtual/VirtualExperimentForm.java,v $
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




package org.rti.webcgh.deprecated.webui.virtual;


import java.util.StringTokenizer;
import java.util.Collection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.rti.webcgh.deprecated.webui.ExperimentContainingForm;



/**
 * Struts form for capturing user input when creating a new group
 */
public class VirtualExperimentForm extends ExperimentContainingForm {
    
    private String experimentName = "";
    private String description = "";
    private String selectedBioAssays = "";
    private String organism = "";
    
    /**
     * Get name of experiment
     * @return Experiment name
     */
    public String getExperimentName() {return experimentName;}
    
    
    /**
     * Set the group name
     * @param groupName Name of group
     */
    public void setExperimentName(String groupName) {
        this.experimentName = groupName;
    }
    
    
    /**
     * Setter for property description
     * @param description Description of virtual experiment
     */
    public void setDescription(String description) {
    	this.description = description;
    }
    
    
    /**
     * Getter for property description
     * @return Description of virtual experiment
     */
    public String getDescription() {
    	return description;
    }
    
    
    /**
     * Setter for property organism
     * @param organism
     */
    public void setOrganism(String organism) {
    	this.organism = organism;
    }
    
    
    /**
     * Getter for property organism
     * @return Organism
     */
    public String getOrganism() {
    	return organism;
    }
    
    
    /**
     * Setter for property selectedBioAssays
     * @param selectedBioAssays Comma separated String list of selected bioassays
     */
    public void setSelectedBioAssays(String selectedBioAssays) {
    	this.selectedBioAssays = selectedBioAssays;
    }
    
    
    /**
     * Getter for property selectedBioAssays
     * @return Comma separated String list of selected bioassays
     */
    public String getSelectedBioAssays() {
    	return selectedBioAssays;
    }
    
    
    /**
     * Get list of selected bioassays as a Collection
     * @return List of selected bioassays as a Collection
     */
    public Collection getSelectedBioAssaysAsCollection() {
    	Collection assays = new ArrayList();
    	StringTokenizer tok = new StringTokenizer(selectedBioAssays, ",");
    	while (tok.hasMoreTokens())
    		assays.add(tok.nextToken());
    	return assays;
    }
    
    
    /**
     * Reset form fields
     * @param mapping Routing information
     * @param request Servlet request object
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {}
    
    
    /**
     * Validate form fields
     * @param mapping Routing information
     * @param request Servlet request object
     * @return Errors
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors= new ActionErrors();
		
		// Experiment name
		if (experimentName == null || experimentName.length() < 1)
			errors.add("experimentName", new ActionError("invalid.field"));
		boolean bad = false;
		for (int i = 0; i < experimentName.length() && ! bad; i++) {
			char c = experimentName.charAt(i);
			if (
				(! Character.isLetter(c)) &&
				(! Character.isDigit(c)) &&
				(c != '-') && (c != '_')
			   ) {
				bad = true;
				errors.add("experimentName", 
					new ActionError("invalid.virtual.experiment.name"));
			   }
		}
		
		if (errors.size() > 0)
			errors.add("global", new ActionError("invalid.fields"));
		return errors;
    }
}
