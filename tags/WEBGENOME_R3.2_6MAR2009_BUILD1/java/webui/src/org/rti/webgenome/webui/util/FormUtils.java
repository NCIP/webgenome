/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-06-28 22:12:17 $


*/

package org.rti.webgenome.webui.util;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.Constants;
import org.rti.webgenome.util.StringUtils;

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
				&& value != Float.MIN_VALUE && value != Float.MAX_VALUE
				&& value != Constants.SMALL_FLOAT
				&& value != Constants.BIG_FLOAT
				&& value != Constants.FLOAT_NAN) {
			text = String.valueOf(value);
		}
		return text;
	}
}
