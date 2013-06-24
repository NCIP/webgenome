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

import java.awt.Color;

/**
 * Utility methods for manipulating colors.
 */
public final class ColorUtils {
	
	/**
	 * Constructor.
	 */
    private ColorUtils() {
    	
    }
	
    /**
     * Converts RGB hexidecimal encoding into a color.
     * @param rgbHexEncoding RGB hexidecimal encoding
     * @return A color
     */
    public static Color getColor(final String rgbHexEncoding) {
    	String encoding = rgbHexEncoding;
        if (encoding.charAt(0) == '#') {
            encoding = rgbHexEncoding.substring(1);
        }
        if (encoding.length() != 6) {
            throw new IllegalArgumentException(
            		"Color must be of form '#0011FF' or '0011FF'");
        }
        int r = Integer.parseInt(encoding.substring(0, 2), 16);
        int g = Integer.parseInt(encoding.substring(2, 4), 16);
        int b = Integer.parseInt(encoding.substring(4, 6), 16);
        return new Color(r, g, b);
    }

    
    /**
     * Convert given color into RGB hexidecimal encoding.
     * @param color Color
     * @return RGB hexidecimal encoding of color--e.g., #FFCC22.
     */
    public static String toRgbHexEncoding(final Color color) {
    	return "#"
    		+ leftPad(Integer.toHexString(color.getRed()), 2)
    		+ leftPad(Integer.toHexString(color.getGreen()), 2)
    		+ leftPad(Integer.toHexString(color.getBlue()), 2);
    }
    
    /**
     * Left pad given number string with zeros.
     * @param numStr String format number
     * @param numDigits Number of digits field should be
     * @return Left padded numeric string
     */
    private static String leftPad(final String numStr, final int numDigits) {
    	StringBuffer buff = new StringBuffer(numStr);
    	int delta = numDigits - numStr.length();
    	for (int i = 0; i < delta; i++) {
    		buff.insert(0, '0');
    	}
    	return buff.toString();
    }
}
