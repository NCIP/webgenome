/*
$Revision: 1.4 $
$Date: 2009-01-09 18:57:10 $


*/

package org.rti.webgenome.webui.struts;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.rti.webgenome.util.StringUtils;

/**
 * Abstract base class for all Struts forms.
 * @author dhall
 *
 */
public abstract class BaseForm extends ActionForm {

	/**
	 * Validate text box field.  If the {@code fieldValue}
	 * is {@code null} or has length < 1, a new
	 * {@code ActionError} with key of {@code fieldName}
	 * and message code of {@code invalid.field}
	 * (i.e. in ApplicationResources.Properties) will be
	 * added to the given action errors.
	 * @param fieldName Name of field
	 * @param fieldValue Value of field
	 * @param actionErrors Action errors to which new
	 * error object may be added.
	 */
	protected void validateTextBoxField(final String fieldName,
			final String fieldValue, final ActionErrors actionErrors) {
		if (StringUtils.isEmpty(fieldValue)) {
			actionErrors.add(fieldName, new ActionError("invalid.field"));
		}
	}
	
	/**
	 * Convenience method for checking whether a form field is empty.
	 * 
	 * @param fieldValue
	 * @return true, if the field is empty, false if it contains some non-space characters
	 */
	protected boolean isEmpty ( final String fieldValue ) {
		return fieldValue == null || fieldValue.trim().length() < 1 ;
	}
	
	/**
	 * Convenience method for checking whether a form field contains a valid email
	 * address. 
	 * @param emailValue
	 * @return true, if the field field contains an email address, false otherwise.
	 */
	protected boolean isValidEmail ( final String emailValue ) {
		boolean isValid = false ; // assume it isn't valid

		if ( ! isEmpty ( emailValue ) ) {
			
			if ( emailValue.matches(".+@.+\\..+") )
				isValid = true ;
		}

        return isValid ;
	}
	
	/**
	 * Convenience method for stripping spaces from any form fields.
	 * Spaces are stripped from leading and trailing positions only.
	 * @param value (String)
	 * @return trimmed value (String)
	 */
	protected String trimSpaces ( final String value ) {
		String returnValue = value ;
		if ( value != null ) {
			returnValue = value.trim() ;
		}
		return returnValue ;
	}
}
