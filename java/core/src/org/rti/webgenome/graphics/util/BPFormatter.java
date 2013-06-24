/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/graphics/util/BPFormatter.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $



*/

package org.rti.webgenome.graphics.util;



/**
 * Formatter for base pair units
 */
public class BPFormatter implements NumberFormatter {
	
	
    // =================================
    //    Attributes
    // =================================
    
    private final RealNumberFormatter numberFormatter = new RealNumberFormatter(12, 4);
	private final int dividend;
	
	
	// ===============================
	//      Constructors
	// ===============================
	
	/**
	 * Constructor
	 * @param dividend Number that base pairs are divided by
	 * during formatting
	 */
	public BPFormatter(int dividend) {
		this(dividend, 12, 4);
	}
	
	
	/**
	 * Constructor
	 * @param dividend Number that base pairs are divided by
	 * during formatting
	 * @param digitsToLeftOfDot Digits to left of decimal point
     * @param digitsToRightOfDot Digits to right of decimal point
	 */
	public BPFormatter(int dividend, int digitsToLeftOfDot, int digitsToRightOfDot) {
	    this.dividend = dividend;
	    this.setSignificantDigits(digitsToLeftOfDot, digitsToRightOfDot);
	}
	
	
	// ============================================
	//      Methods in NumberFormatter interface
	// ============================================
	
    /**
     * Format given number
     * @param number A number
     * @return Formatted number
     */
    public String format(double number) {
        return this.numberFormatter.format(number / this.dividend);
    }
    
    
    /**
     * Format given number
     * @param number A number
     * @return Formatted number
     */
    public String format(long number) {
        return this.numberFormatter.format(number / this.dividend);
    }
	
	
	// ==================================================
	//         Public methods
	// ==================================================
	
    /**
     * Set significant digits
     * @param digitsToLeftOfDot Digits to left of decimal point
     * @param digitsToRightOfDot Digits to right of decimal point
     */
	public void setSignificantDigits(int digitsToLeftOfDot, int digitsToRightOfDot) {
	    this.numberFormatter.setSignificantDigits(digitsToLeftOfDot, digitsToRightOfDot);
	}

}
