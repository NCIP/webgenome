/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.util;

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
