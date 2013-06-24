/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-28 22:12:17 $


*/

package org.rti.webgenome.core;

/**
 * System-wide constant values.
 * @author dhall
 *
 */
public final class Constants {
	
	/**
	 * Big double precision value. Typically, this will
	 * be used in place of Double.MAX_VALUE so that it can
	 * be persisted.
	 */
	public static final double BIG_DOUBLE = 10000000000000.0;
	
	/**
	 * Small double precsion value.  Typically, this will
	 * be used in place of Double.MIN_VALUE so that it can
	 * be persisted.*/
	public static final double SMALL_DOUBLE = -10000000000000.0;
	
	/**
	 * Big single precision value. Typically, this will
	 * be used in place of Float.MAX_VALUE so that it can
	 * be persisted.
	 */
	public static final float BIG_FLOAT = (float) 10000000000000.0;
	
	/**
	 * Small single precsion value.  Typically, this will
	 * be used in place of Float.MIN_VALUE so that it can
	 * be persisted.*/
	public static final float SMALL_FLOAT = (float) -10000000000000.0;
	
	/**
	 * A persistable substitute NaN value for Float.NaN.
	 */
	public static final float FLOAT_NAN = (float) -999999999999999.0;

	/**
	 * Constructor.
	 */
	private Constants() {
		
	}
}
