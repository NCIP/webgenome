/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/UploadExperimentForm.java,v $
$Revision: 1.2 $
$Date: 2006-05-24 14:08:36 $

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadExperimentForm extends ActionForm {
  private String organismId;
 
  private String quantitationType;
  private String otherOrganism;
  private String genomeAssemblyId;   
  private String otherGenomeAssembly; 
  private String otherQuantitationType;  
  private String methodToCall;
  private FormFile experimentFile;
  
  
public String getMethodToCall() {
	return methodToCall;
}

public void setMethodToCall(String methodToCall) {
	this.methodToCall = methodToCall;
}


public String getGenomeAssemblyId() {
	return genomeAssemblyId;
}
public void setGenomeAssemblyId(String genomeAssemblyId) {
	this.genomeAssemblyId = genomeAssemblyId;
}

public Long getGenomeAssemblyIdAsLong(){
  return new Long(this.genomeAssemblyId);	
}

public String getOrganismId() {
	return organismId;
}
public void setOrganismId(String organismId) {
	this.organismId = organismId;
}

public Long getOrganismIdAsLong() {
	return new Long(organismId);
}

public String getOtherGenomeAssembly() {
	return otherGenomeAssembly;
}
public void setOtherGenomeAssembly(String otherGenomeAssembly) {
	this.otherGenomeAssembly = otherGenomeAssembly;
}
public String getOtherOrganism() {
	return otherOrganism;
}
public void setOtherOrganism(String otherOrganism) {
	this.otherOrganism = otherOrganism;
}
public String getOtherQuantitationType() {
	return otherQuantitationType;
}
public void setOtherQuantitationType(String otherQuantitationType) {
	this.otherQuantitationType = otherQuantitationType;
}
public String getQuantitationType() {
	return quantitationType;
}
public void setQuantitationTypeId(String quantitationTypeId) {
	this.quantitationType = quantitationTypeId;
}

public FormFile getExperimentFile() {
	return experimentFile;
}

public void setExperimentFile(FormFile experimentFile) {
	this.experimentFile = experimentFile;
}

}
