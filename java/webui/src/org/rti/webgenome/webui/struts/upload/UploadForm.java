/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-06-16 19:34:18 $


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
	 * Get name of column containing chromosomal position.
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
		this.validateTextBoxField("experimentName", this.experimentName, errors);
		if ( QuantitationType.Other.getName().equals(this.quantitationTypeId ) )
			this.validateTextBoxField("quantitationTypeOther", this.quantitationTypeOther, errors) ;
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
