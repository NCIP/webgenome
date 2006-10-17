/*
$Revision: 1.2 $
$Date: 2006-10-17 22:49:33 $

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

package org.rti.webcgh.graphics.util;

import java.awt.Color;

import org.rti.webcgh.util.ColorUtils;

/**
 * Used to choose a series of unique colors.
 */
public class ColorChooser {
	
    
    // ============================
    //     Attributes
    // ============================
    
	/** RGB color component values. */
	private int[] components = {0, 0, 0};
	
	/** Sequence of values for R, G, and B. */
	private int[] componentValueSequence = {200, 100, 140, 245, 20};
	
	/** Count of number of colors generated so far. */
	private int count = -1;
	
	
	// ===============================
	//      Constructors
	// ===============================
	
	/**
	 * Constructor.
	 */
	public ColorChooser() {
		
	}
	
	
	
	// ==================================
	//      Public methods
	// ==================================
	
	/**
	 * Return next color.
	 * @return A color
	 */
	public final Color nextColor() {
		this.increment();
		return new Color(components[0], components[1], components[2]);
	}
	
	
	/**
	 * Reset color generation.
	 */
	public final void reset() {
	    this.count = -1;
	}
	
	
	
	// ======================================
	//        Private methods
	// ======================================
	
	/**
	 * Increment internal color state variables.
	 */
	private void increment() {
		count++;
		if (count > maxNumColors()) {
			count = 0;
		}
		int p = (int) Math.floor((double) count / 7.0);
		int component = componentValueSequence[p];
		int combination = count % 7;
		setComponents(component, combination);
	}
	
	
	/**
	 * Set values for each of the 3 color components.
	 * @param value Component value
	 * @param combinationNumber The number 0 - 5: Codes which
	 * components get set the component value.  Components that
	 * don't get set this value get set to 0.
	 */
	private void setComponents(final int value,
			final int combinationNumber) {
		switch (combinationNumber) {
			case 0 :
				this.setComponents(0, 0, value);
				break;
			case 1 :
				this.setComponents(value, 0, 0);
				break;
			case 2 :
				this.setComponents(0, value, 0);
				break;
			case 3 :
				this.setComponents(value, value, 0);
				break;
			case 4 :
				this.setComponents(value, 0, value);
				break;
			case 5 :
				this.setComponents(0, value, value);
				break;
			case 6 :
				this.setComponents(value, value, value);
				break;
			default: setComponents(0, 0, 0);
		}
	}
	
	
	/**
	 * Set individual color components.
	 * @param a Value of first component (i.e. red)
	 * @param b Value of second component (i.e. green)
	 * @param c Value of third component (i.e. blue)
	 */
	private void setComponents(final int a, final int b,
			final int c) {
		components[0] = a;
		components[1] = b;
		components[2] = c;
	}
	
	
	/**
	 * Maximum number of colors.
	 * @return Maximum number of colors
	 */
	private int maxNumColors() {
		return 6 * componentValueSequence.length;
	}

	
	/**
	 * Get matrix of available colors in hexidecimal RGB format.
	 * This method is intended
	 * to be called during the creation of a color
	 * selector web page.
	 * @return Matrix of available colors.
	 */
	public static final String[][] getWebColorPalette() {
		ColorChooser cc = new ColorChooser();
		double numColors = (double) cc.maxNumColors();
		int numRows = (int) Math.ceil(Math.sqrt(numColors));
		int numCols = (int) Math.floor(Math.sqrt(numColors));
		String[][] palette = new String[numRows][];
		int count = 0;
		for (int i = 0; i < numRows; i++) {
			palette[i] = new String[numCols];
			for (int j = 0; j < numCols; j++) {
				String color = null;
				if (count++ < numColors) {
					color = ColorUtils.toRgbHexEncoding(cc.nextColor());
				} else {
					color = "#FFFFFF";
				}
				palette[i][j] = color;
			}
		}
		return palette;
	}
}
