/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:09 $

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

package org.rti.webcgh.webui.util;

import org.apache.log4j.Logger;
import org.rti.webcgh.util.StringUtils;

/**
 * This class contains utility methods for working with
 * web forms.
 * @author dhall
 *
 */
public final class FormUtils {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(FormUtils.class);

	
	/**
	 * Constructor.
	 */
	private FormUtils() {
		
	}
	
	
	/**
	 * Translate the string value from a checkbox into
	 * a boolean.
	 * @param checkBoxValue String checkbox value
	 * @return Boolean equivalent
	 */
	public static boolean checkBoxToBoolean(final String checkBoxValue) {
		return "on".equals(checkBoxValue);
	}
	
	
	/**
	 * Translate the given boolean value into a checkbox
	 * string equivalent.
	 * @param value A boolean value
	 * @return Checkbox string equivalent
	 */
	public static String booleanToCheckBox(final boolean value) {
		String checkBoxVal = "";
		if (value) {
			checkBoxVal = "on";
		}
		return checkBoxVal;
	}
	
	
	/**
	 * Extract float value from given text box string.  If a number
	 * cannot be extracted, return default value.  This method
	 * traps number format exceptions.
	 * @param textBoxString String from numeric text box
	 * @param defaultValue Default value to return if a valid float
	 * cannot be parsed
	 * @return Float equivalent
	 */
	public static float textBoxToFloat(final String textBoxString,
			final float defaultValue) {
		float val = defaultValue;
		if (!StringUtils.isEmpty(textBoxString)) {
			try {
				val = Float.parseFloat(textBoxString);
			} catch (NumberFormatException e) {
				LOGGER.warn("Cannot parse float from '" + textBoxString + "'");
			}
		}
		return val;
	}
	
	
	/**
	 * Converts given float to text box value.
	 * @param value A value to convert
	 * @return Empty string if the given value is NaN,
	 * infinite, or equal to the minimum or maximum floating point
	 * number; or the String equivalent of the given argument
	 * otherwise
	 */
	public static String floatToTextBox(final float value) {
		String text = "";
		if (!Float.isNaN(value) && !Float.isInfinite(value)
				&& value != Float.MIN_VALUE && value != Float.MAX_VALUE) {
			text = String.valueOf(value);
		}
		return text;
	}
}
