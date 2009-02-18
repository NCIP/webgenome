/*
$Revision: 1.3 $
$Date: 2008-06-16 19:34:18 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.webui.struts.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;

/**
 * Form for uploading data.
 * @author dhall
 */
public class UploadForm extends BaseForm {

	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	//
	//  A T T R I B U T E S
	//
	
	/** Name of column containing chromosome numbers. */
	private String chromosomeColumnName = null;
	
	/** Name of colum containing chromosomal position. */
	private String positionColumnName = null;
	
	/** Name of colum containing start chromosomal position. */
	private String startPositionColumnName = null;
	
	/** Name of colum containing end chromosomal position. */
	private String endPositionColumnName = null;
	
	/** Units of chromosomal position. */
	private String units = BpUnits.KB.toString();
	
	/** Organism associated with data. */
	private String organismId = null;
	
	/** Name of experiment that will contain uploaded data. */
	private String experimentName = null;
	
	/** ID of quantitation type of all data. */
	private String quantitationTypeId =
		QuantitationType.COPY_NUMBER.getId();
	
	private String quantitationTypeOther = "";

	public String getQuantitationTypeOther() {
		return quantitationTypeOther;
	}

	public void setQuantitationTypeOther(String quantitationTypeOther) {
		this.quantitationTypeOther = quantitationTypeOther;
	}

	/**
	 * Get name of colum containing chromosomal position.
	 * @return Column name
	 */
	public String getChromosomeColumnName() {
		return chromosomeColumnName;
	}

	/**
	 * Set name of colum containing chromosomal position.
	 * @param chromosomeColumnName Name of colum containing
	 * chromosomal position.
	 */
	public void setChromosomeColumnName(final String chromosomeColumnName) {
		this.chromosomeColumnName = chromosomeColumnName;
	}

	/**
	 * Get quantitation type ID of all data.
	 * @return Quantitation type ID
	 */
	public String getQuantitationTypeId() {
		return quantitationTypeId;
	}

	/**
	 * Set quantitation type ID of all data.
	 * @param quantitationTypeId Quantitation type
	 * ID
	 */
	public void setQuantitationTypeId(
			final String quantitationTypeId) {
		this.quantitationTypeId = quantitationTypeId;
	}

	/**
	 * Get name of experiment that will contain uploaded data.
	 * @return Name of experiment that will contain uploaded data.
	 */
	public String getExperimentName() {
		return experimentName;
	}

	/**
	 * Set name of experiment that will contain uploaded data.
	 * @param experimentName Name of experiment that will contain
	 * uploaded data
	 */
	public void setExperimentName(final String experimentName) {
		this.experimentName = experimentName;
	}

	/**
	 * Get primary key ID of organism associated with data.
	 * @return ID of organism associated with data.
	 */
	public String getOrganismId() {
		return organismId;
	}

	/**
	 * Set primary key ID of organism associated with data.
	 * @param organism ID of organism associated with data.
	 */
	public void setOrganismId(final String organism) {
		this.organismId = organism;
	}

	/**
	 * Get name of colum containing chromosomal position.
	 * @return Name of colum containing chromosomal position.
	 */
	public String getPositionColumnName() {
		return positionColumnName;
	}

	/**
	 * Set name of colum containing chromosomal position.
	 * @param positionColumnName Name of colum containing
	 * chromosomal position.
	 */
	public void setPositionColumnName(final String positionColumnName) {
		this.positionColumnName = positionColumnName;
	}

	/**
	 * Get units of chromosomal position.
	 * @return Units of chromosomal position.
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * Set units of chromosomal position.
	 * @param units Units of chromosomal position.
	 */
	public void setUnits(final String units) {
		this.units = units;
	}
	
	//
	//  O V E R R I D E S
	//
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionErrors validate(final ActionMapping mapping,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		this.validateTextBoxField("experimentName",
				this.experimentName, errors);
		if (errors.size() > 0) {
			errors.add("global", new ActionError("invalid.fields"));
		}
		return errors;
	}

	public String getStartPositionColumnName() {
		return startPositionColumnName;
	}

	public void setStartPositionColumnName(String startPositionColumName) {
		this.startPositionColumnName = startPositionColumName;
	}

	public String getEndPositionColumnName() {
		return endPositionColumnName;
	}

	public void setEndPositionColumnName(String endPositionColumName) {
		this.endPositionColumnName = endPositionColumName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
