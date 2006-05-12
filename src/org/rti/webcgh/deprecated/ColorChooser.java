/*

$Source$
$Revision$
$Date$

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

package org.rti.webcgh.deprecated;

import java.awt.Color;

/**
 * Used to choose colors
 */
public class ColorChooser {
	
    
    // ============================
    //     Attributes
    // ============================
    
	private int[] components = {0, 0, 0};
	private int[] componentValueSequence = {200, 100, 140, 245, 20};
	private int count = -1;
	
	
	// ===============================
	//      Constructors
	// ===============================
	
	/**
	 * Constructor
	 *
	 */
	public ColorChooser() {}
	
	
	
	// ==================================
	//      Public methods
	// ==================================
	
	/**
	 * Return next color
	 * @return A color
	 */
	public Color nextColor() {
		increment();
		return new Color(components[0], components[1], components[2]);
	}
	
	
	/**
	 * Reset
	 *
	 */
	public void reset() {
	    this.count = -1;
	}
	
	
	
	// ======================================
	//        Private methods
	// ======================================
	
	/**
	 * Increment internal color state variables
	 *
	 */
	private void increment() {
		count++;
		if (count > maxNumColors())
			count = 0;
		int p = (int)Math.floor((double)count / 7.0);
		int component = componentValueSequence[p];
		int combination = count % 7;
		setComponents(component, combination);
	}
	
	
	/**
	 * Set values for each of the 3 color components
	 * @param value Component value
	 * @param combinationNumber The number 0 - 5: Codes which
	 * components get set the component value.  Components that
	 * don't get set this value get set to 0.
	 */
	private void setComponents(int value, int combinationNumber) {
		switch (combinationNumber) {
			case 0 :
				setComponents(0, 0, value);
				break;
			case 1 :
				setComponents(value, 0, 0);
				break;
			case 2 :
				setComponents(0, value, 0);
				break;
			case 3 :
				setComponents(value, value, 0);
				break;
			case 4 :
				setComponents(value, 0, value);
				break;
			case 5 :
				setComponents(0, value, value);
				break;
			case 6 :
				setComponents(value, value, value);
		}
	}
	
	
	/**
	 * Set individual color components
	 * @param a Value of first component (i.e. red)
	 * @param b Value of second component (i.e. green)
	 * @param c Value of third component (i.e. blue)
	 */
	private void setComponents(int a, int b, int c) {
		components[0] = a;
		components[1] = b;
		components[2] = c;
	}
	
	
	/**
	 * Maximum number of colors
	 * @return Maximum number of colors
	 */
	private int maxNumColors() {
		return 6 * componentValueSequence.length;
	}

}
