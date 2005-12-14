/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/pipeline/PipelineForm.java,v $
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




package org.rti.webcgh.webui.pipeline;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import java.util.Collection;
import java.util.Iterator;

import org.rti.webcgh.analytic.AnalyticPipeline;
import org.rti.webcgh.core.WebcghRuntimeException;


/**
 * Contains parameters for the creation of a new analytic pipeline
 */
public class PipelineForm extends ActionForm {
	
	private String name = "";
	private String opType = "";
	private String dataFilterOp = "";
	private String normalizationOp = "";
	private String summaryStatisticOp = "";
	
	
	/**
	 * Setter for property name
	 * @param name Pipeline name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter for property name
	 * @return Pipeline name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Setter for property opType
	 * @param opType Operation type
	 */
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	
	/**
	 * Getter for property opType
	 * @return Operation type
	 */
	public String getOpType() {
		return opType;
	}
	
	
    /**
     * @return Returns the dataFilterOp.
     */
    public String getDataFilterOp() {
        return dataFilterOp;
    }
    
    
    /**
     * @param dataFilterOp The dataFilterOp to set.
     */
    public void setDataFilterOp(String dataFilterOp) {
        this.dataFilterOp = dataFilterOp;
    }
    
    
    /**
     * @return Returns the normalizationOp.
     */
    public String getNormalizationOp() {
        return normalizationOp;
    }
    
    
    /**
     * @param normalizationOp The normalizationOp to set.
     */
    public void setNormalizationOp(String normalizationOp) {
        this.normalizationOp = normalizationOp;
    }
    
    
    /**
     * @return Returns the summaryStatisticOp.
     */
    public String getSummaryStatisticOp() {
        return summaryStatisticOp;
    }
    
    
    /**
     * @param summaryStatisticOp The summaryStatisticOp to set.
     */
    public void setSummaryStatisticOp(String summaryStatisticOp) {
        this.summaryStatisticOp = summaryStatisticOp;
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
	public ActionErrors validate
	(
		ActionMapping mapping, HttpServletRequest request
	) {
		ActionErrors errors= new ActionErrors();
		
		// Make sure name not missing
		boolean validName = name != null && name.length() > 0;
		if (! validName) {
			errors.add("name", new ActionError("invalid.field"));
			errors.add("global", new ActionError("pipeline.name.missing"));
		}
		
		// Make sure name okay
		if (validName) {
			for (int i = 0; i < name.length() && validName; i++) {
				char c = name.charAt(i);
				if (
					! Character.isLetter(c) && 
					! Character.isDigit(c) &&
					c != '_' &&
					c != '-' )
					validName = false;
			}
			if (! validName) {
				errors.add("name", new ActionError("invalid.field"));
				errors.add("global", new ActionError("invalid.pipeline.name"));
			}
		}
		
		// Make sure name not same as a default pipeline
		if (validName) {
			try {
				Collection pipes = AnalyticPipeline.loadDefaultPipelines();
				for (Iterator it = pipes.iterator(); it.hasNext() && validName;) {
					AnalyticPipeline pipe = (AnalyticPipeline)it.next();
					if (pipe.getName().equals(name))
						validName = false;
				}
			} catch (Exception e) {
				throw new WebcghRuntimeException("Error validating pipeline name", e);
			}
			if (! validName) {
				errors.add("name", new ActionError("invalid.field"));
				errors.add("global", new ActionError("duplicate.pipeline.name"));
			}
		}
		
		return errors;
	}

}
