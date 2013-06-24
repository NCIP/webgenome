/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/


package org.rti.webgenome.util;


/**
 * Methods for user input validation.
 */
public final class ValidationUtils {
	
	/**
	 * Constructor.
	 */
	private ValidationUtils() {
		
	}
    
    
    /**
     * Does given string represent a valid integer?
     * @param str A string
     * @return T/F
     */
    public static boolean validPositiveInteger(final String str) {
        boolean isInteger = true;
        for (int i = 0; i < str.length() && isInteger; i++) {
            char c = str.charAt(i);
            if (!(Character.isDigit(c) || c == '.')) {
                isInteger = false;
            }
        }
        return isInteger;
    }
    
    
    /**
     * Does given string represent a valid number?
     * @param str A string
     * @return T/F
     */
    public static boolean validNumber(final String str) {
    	if (str == null || str.length() < 1) {
    		return false;
    	}
    	boolean isANum = true;
    	int i = 0;
    	if (str.charAt(0) == '-') {
    		i = 1;
    	}
    	for (; i < str.length() && isANum; i++) {
    		char c = str.charAt(i);
    		if (!(c == '.' || Character.isDigit(c))) {
    			isANum = false;
    		}
    	}
    	return isANum;
    }
    
    
    /**
     * Is <code>testNum</code> in range?
     * @param testNum Number to test.
     * @param min Left endpoint of range.
     * @param max Right endpoint of range.
     * @return T/F.
     */
    public static boolean inRange(final float testNum,
    		final float min, final float max) {
    	boolean in = false;
    	if (!Float.isNaN(testNum)) {
    		in = testNum >= min && testNum <= max;
    	}
    	return in;
    }
}
