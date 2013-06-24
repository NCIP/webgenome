/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.util;

/**
 * Utilities for dealing with numbers.
 * @author dhall
 *
 */
public final class NumericUtils {

	/**
	 * Constructor.
	 */
	private NumericUtils() {
		
	}
	
	
	/**
	 * Is given number real--i.e., not infinite or NaN?
	 * @param num Number to check
	 * @return T/F
	 */
	public static boolean isReal(final float num) {
		return !Float.isInfinite(num) && !Float.isNaN(num);
	}
}
