/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $

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
