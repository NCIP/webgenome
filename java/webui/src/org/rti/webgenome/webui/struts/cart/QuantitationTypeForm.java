/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/

package org.rti.webgenome.webui.struts.cart;

import org.apache.struts.action.ActionForm;
import org.rti.webgenome.util.SystemUtils;

/**
 * Form for specifying a quantitation type.
 * @author dhall
 *
 */
public class QuantitationTypeForm extends ActionForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/** ID of a quantitation type. */
	private String quantitationTypeId = "";

	/**
	 * Get ID of a quantitation type.
	 * @return Quantitation type ID property
	 */
	public final String getQuantitationTypeId() {
		return quantitationTypeId;
	}

	/**
	 * Set ID of quantitation type.
	 * @param quantitationTypeId Quantitation type ID property
	 */
	public final void setQuantitationTypeId(
			final String quantitationTypeId) {
		this.quantitationTypeId = quantitationTypeId;
	}
	
	
}
