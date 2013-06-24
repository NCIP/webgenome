/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/graphics/util/RealNumberFormatter.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $



*/
package org.rti.webgenome.graphics.util;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Formats real numbers
 */
public class RealNumberFormatter implements NumberFormatter {
    
    
    // ====================================
    //      Attributes
    // ====================================
    
    private DecimalFormat dForm = 
    	(DecimalFormat)DecimalFormat.getInstance(Locale.US);
    
    
    // ==========================================
    //     Constructors
    // ==========================================
    
    /**
     * Constructor
     * @param digitsToLeftOfDot Digits to left of decimal point
     * @param digitsToRightOfDot Digits to right of decimal point
     */
    public RealNumberFormatter(int digitsToLeftOfDot, int digitsToRightOfDot) {
        this.setSignificantDigits(digitsToLeftOfDot, digitsToRightOfDot);
    }
    
    
    // ===============================================
    //     Methods in NumberFormatter interface
    // ===============================================
    
    /**
     * Format given number
     * @param number A number
     * @return Formatted number
     */
    public String format(double number) {
        return this.dForm.format(number);
    }
    
    
    /**
     * Format given number
     * @param number A number
     * @return Formatted number
     */
    public String format(long number) {
        return this.dForm.format(number);
    }
    
    
    // =============================================
    //     Public methods
    // =============================================
    
    /**
     * Set significant digits
     * @param digitsToLeftOfDot Digits to left of decimal point
     * @param digitsToRightOfDot Digits to right of decimal point
     */
    public void setSignificantDigits(int digitsToLeftOfDot, int digitsToRightOfDot) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < digitsToLeftOfDot; i++) {
            if (i % 3 == 0 && i > 0)
                buff.insert(0, ",");
            buff.insert(0, "#");
        }
        if (digitsToRightOfDot > 0) {
            buff.append(".");
            for (int i = 0; i < digitsToRightOfDot; i++)
                buff.append("#");
        }
        this.dForm.applyPattern(buff.toString());
    }

}
