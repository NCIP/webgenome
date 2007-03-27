/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/org/rti/webcgh/util/TimeUtils.java,v $
$Revision: 1.1 $
$Date: 2007-03-27 19:42:08 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.util;

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
