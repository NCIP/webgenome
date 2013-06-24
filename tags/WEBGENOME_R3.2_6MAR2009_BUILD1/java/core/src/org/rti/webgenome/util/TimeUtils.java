/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/TimeUtils.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/

package org.rti.webgenome.util;

import java.util.Date;

/**
 * Utilities for working with times
 */
public class TimeUtils {
	
	
	// ======================================
	//       Constants
	// ======================================
	
	private static final long MILLIS_IN_A_SECOND = 1000;
	private static final long MILLIS_IN_A_MINUTE = MILLIS_IN_A_SECOND * 60;
	private static final long MILLIS_IN_AN_HOUR = MILLIS_IN_A_MINUTE * 60;
	
	
	/**
	 * Print elapsed time in HH:MM:SS format
	 * @param earlierDate First date
	 * @param laterDate Second date
	 * @return Elapsed time in HH:MM:SS format
	 */
	public static String formattedElapsedTime(Date earlierDate, Date laterDate) {
		long t1 = earlierDate.getTime();
		long t2 = laterDate.getTime();
		long delta = t2 - t1;
		long hours = (long)Math.floor((double)delta / (double)MILLIS_IN_AN_HOUR);
		long remainder = delta % MILLIS_IN_AN_HOUR;
		long minutes = (long)Math.floor((double)remainder / (double)MILLIS_IN_A_MINUTE);
		remainder %= MILLIS_IN_A_MINUTE;
		long seconds = remainder / MILLIS_IN_A_SECOND;
		return hours + ":" + minutes + ":" + seconds;
	}

}
