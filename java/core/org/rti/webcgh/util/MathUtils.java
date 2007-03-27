/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:07 $

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

package org.rti.webcgh.util;

/**
 * Mathematical routines.
 */
public final class MathUtils {
    
    /**
     * Constructor.
     *
     */
    private MathUtils() {
        
    }
	
	
	/**
	 * Logarithmic function.
	 * @param value Value
	 * @param base Log base
	 * @return Log
	 */
	public static double log(final double value, final double base) {
		if (value == 0.0) {
			return value;
        }
		return Math.log(value) / Math.log(base);
	}
	
	
	/**
	 * Log base 2 function.
	 * @param value Value
	 * @return Log
	 */
	public static double log2(final double value) {
		return MathUtils.log(value, 2.0);
	}
	
	
	/**
	 * Addition that is tolerant of NaN.  NaN + x = NaN.
	 * @param x A value
	 * @param y A value
	 * @return A value
	 */
	public static float add(final float x, final float y) {
		float value = Float.NaN;
		if (!Float.isNaN(x) && !Float.isNaN(y)) {
			value = x + y;
        }
		return value;
	}
	
	
	/**
	 * Division that is tolerant of NaN.  NaN / x = NaN and x / NaN = NaN.
	 * @param dividend Dividend
	 * @param divisor Divisor
	 * @return A value
	 */
	public static float divide(final float dividend,
            final float divisor) {
		float value = Float.NaN;
		if (!Float.isNaN(dividend) && !Float.isNaN(divisor)) {
			value = dividend / divisor;
        }
		return value;
	}
    
    
    /**
     * Generate random integer over range specified by <code>min</code>
     * and <code>max</code>, inclusively.
     * @param min Low value in range
     * @param max High value in range
     * @return A random integer
     */
    public static int randomInt(final int min, final int max) {
        return min + (int) (Math.random() * (double) (max - min));
    }

    
    /**
     * Square given number.
     * @param x Number
     * @return The number squared
     */
    public static double square(final double x) {
    	return x * x;
    }
}
