/*
$Revision: 1.4 $
$Date: 2009-01-09 18:57:10 $

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
