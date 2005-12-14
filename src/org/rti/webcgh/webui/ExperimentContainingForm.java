/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/ExperimentContainingForm.java,v $
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

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionForm;



/**
 * Base class for Struts forms that contain experimental data
 * 
 */
public class ExperimentContainingForm extends ActionForm {
	
	protected String selectedExperiments = "";
	
	
	/**
	 * Setter for selected experiments property
	 * @param experiments Comma separated String list of selected experiments
	 */
	public void setSelectedExperiments(String experiments) {
		this.selectedExperiments = experiments;
	}
	
	
	/**
	 * Getter for experiments property
	 * @return Comma separated String list of selected experiments
	 */
	public String getSelectedExperiments() {
		return selectedExperiments;
	}
	
	
	/**
	 * Get list of selected experiment names as a Collection object
	 * @return List of selected experiment names as a Collection object
	 */
	public Collection getSelectedExperimentsAsCollection() {
		Collection col = new ArrayList();
		StringTokenizer tok = new StringTokenizer(selectedExperiments, ",");
		while (tok.hasMoreTokens())
			col.add(tok.nextToken());
		return col;
	}
	
	
	/**
	 * Remove given experiment from selected list
	 * @param expName Experiment name
	 */
	public void removeSelectedExperiment(String expName) {
	    Collection expCol = getSelectedExperimentsAsCollection();
	    StringBuffer buff = new StringBuffer();
	    int count = 0;
	    for (Iterator it = expCol.iterator(); it.hasNext();) {
	        String currExpName = (String)it.next();
	        if (! currExpName.equals(expName)) {
	            if (count++ > 0)
	                buff.append(",");
	            buff.append(currExpName);
	        }
	    }
	    this.selectedExperiments = buff.toString();
	}

}
