/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/graph/RealNumberFormatter.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:34:32 $

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
package org.rti.webcgh.deprecated.graph;

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
