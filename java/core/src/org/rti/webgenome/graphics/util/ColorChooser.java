/*
$Revision: 1.3 $
$Date: 2007-07-26 16:45:34 $

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

package org.rti.webgenome.graphics.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.util.ColorUtils;

/**
 * Used to choose a series of unique colors.
 */
public class ColorChooser {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(ColorChooser.class);
	
	
	// ============================
	//      Attributes
	// ============================
	
	/** Primary key value for persistence. */
	private Long id = null;
	
	/** List of colors which are assigned in sequence. */
	private List<Color> colors = new ArrayList<Color>();
	
	/**
	 * Counts how many times each color is being used in
	 * a given session.  When the method nextColor() is called,
	 * it returns the color with the least number of counts.
	 * If more than one color has the same minimum number of counts,
	 * the one with the lowest index is returned.  Client classes
	 * that assign colors without getting a color via nextColor(),
	 * are expected to notify the session color chooser that
	 * the color has been assigned by using the incrementColorCount(Color)
	 * method.  
	 */
	private Map<Color, Integer> colorCounts =
		new HashMap<Color, Integer>();
	
	//
	//  G E T T E R S  /  S E T T E R S
	//
	
	/**
	 * Get counts of how many times a particular color
	 * has been assigned.
	 * @return Map of color integer equivalents
	 * (i.e. RBG value) to counts.
	 */
	public final Map<Integer, Integer> getColorCounts() {
		Map<Integer, Integer> intColorCounts =
			new HashMap<Integer, Integer>();
		for (Color c : this.colorCounts.keySet()) {
			intColorCounts.put(c.getRGB(), this.colorCounts.get(c));
		}
		return intColorCounts;
	}


	/**
	 * Set counts of how many times a particular color
	 * has been assigned.
	 * @param colorCounts Map of color integer equivalents
	 * (i.e. RGB value) to counts.
	 */
	public final void setColorCounts(final Map<Integer, Integer> colorCounts) {
		this.colorCounts = new HashMap<Color, Integer>();
		for (Integer colorInt : colorCounts.keySet()) {
			Color c = new Color(colorInt);
			this.colorCounts.put(c, colorCounts.get(colorInt));
		}
	}


	/**
	 * Get list of available colors.
	 * @return Available colors as integer equivalents (i.e., RGB values)
	 */
	public final List<Integer> getColors() {
		List<Integer> colorInts = new ArrayList<Integer>();
		for (Color c : this.colors) {
			colorInts.add(c.getRGB());
		}
		return colorInts;
	}


	/**
	 * Set list of available colors.
	 * @param colors Available colors as integer
	 * equivalents (i.e., RGB values).
	 */
	public final void setColors(final List<Integer> colors) {
		this.colors = new ArrayList<Color>();
		for (int colorInt : colors) {
			this.colors.add(new Color(colorInt));
		}
	}


	/**
	 * Get primary key value used for persistence.
	 * @return Primary key value.
	 */
	public final Long getId() {
		return id;
	}


	/**
	 * Set primary key value used for persistence.
	 * @param id Primary key value.
	 */
	public final void setId(final Long id) {
		this.id = id;
	}
	
	// ==============================
	//      Constructors
	// ==============================

	/**
	 * Constructor.
	 */
	public ColorChooser() {
		ColorGenerator gen = new ColorGenerator();
		for (int i = 0; i < gen.maxNumColors(); i++) {
			Color col = gen.nextColor();
			this.colors.add(col);
			this.colorCounts.put(col, 0);
		}
	}
	
	
	// ===============================
	//    Business methods
	// ===============================
	
	/**
	 * Increment the count of how many times the
	 * given color is currently being used in the
	 * session.  The color should be one of the colors
	 * that would be generated by this color chooser.
	 * @param color A color
	 */
	public final void incrementCount(final Color color) {
		if (this.colorCounts.containsKey(color)) {
			int newCount = this.colorCounts.get(color) + 1;
			this.colorCounts.put(color, newCount);
		} else {
			LOGGER.warn("Color unknown by color chooser");
		}
	}
	
	
	/**
	 * Decrement the count of how many times the
	 * given color is currently being used in the
	 * session.  The color should be one of the colors
	 * that would be generated by this color chooser.
	 * The count should never fall below zero.
	 * @param color A color
	 */
	public final void decrementCount(final Color color) {
		if (this.colorCounts.containsKey(color)) {
			int newCount = this.colorCounts.get(color) - 1;
			if (newCount >= 0) {
				this.colorCounts.put(color, newCount);
			} else {
				LOGGER.warn("Color count decremented below 0");
			}
		} else {
			LOGGER.warn("Color unknown by color chooser");
		}
	}
	
	
	/**
	 * Get next color.
	 * @return Next color.
	 */
	public final Color nextColor() {
		Color c = null;
		
		// Get minimum color count
		int min = Integer.MAX_VALUE;
		for (Integer i : this.colorCounts.values()) {
			if (i < min) {
				min = i;
			}
			if (i == 0) {
				break;
			}
		}
		
		// Get color with minimum count and lowest index
		for (Color col : this.colors) {
			if (this.colorCounts.get(col) == min) {
				c = col;
				this.incrementCount(col);
				break;
			}
		}
		
		return c;
	}
	
	
	/**
	 * Get matrix of available colors in hexidecimal RGB format.
	 * This method is intended
	 * to be called during the creation of a color
	 * selector web page.
	 * @return Matrix of available colors.
	 */
	public static String[][] getWebColorPalette() {
		return ColorGenerator.getWebColorPalette();
	}
	
	
	/**
	 * Relinquish colors in given experiment, i.e.,
	 * decrement color counts for all embeded bioassays.
	 * @param exp An experiment
	 */
	public void relinquishColors(final Experiment exp) {
		for (BioAssay ba : exp.getBioAssays()) {
			this.decrementCount(ba.getColor());
		}
	}
	
	
	// ===========================
	//    Helper classes
	// ===========================
	
	/**
	 * Generates colors.
	 */
	static final class ColorGenerator {
	
    
		/** RGB color component values. */
		private int[] components = {0, 0, 0};
		
		/** Sequence of values for R, G, and B. */
		private int[] componentValueSequence = {200, 100, 140, 245, 20};
		
		/** Count of number of colors generated so far. */
		private int count = -1;
		
		
		/**
		 * Return next color.
		 * @return A color
		 */
		Color nextColor() {
			this.increment();
			return new Color(components[0], components[1], components[2]);
		}
		
		/**
		 * Get matrix of available colors in hexidecimal RGB format.
		 * This method is intended
		 * to be called during the creation of a color
		 * selector web page.
		 * @return Matrix of available colors.
		 */
		static String[][] getWebColorPalette() {
			ColorGenerator cg = new ColorGenerator();
			double numColors = (double) cg.maxNumColors();
			int numRows = (int) Math.ceil(Math.sqrt(numColors));
			int numCols = (int) Math.floor(Math.sqrt(numColors));
			String[][] palette = new String[numRows][];
			int count = 0;
			for (int i = 0; i < numRows; i++) {
				palette[i] = new String[numCols];
				for (int j = 0; j < numCols; j++) {
					String hexColor = null;
					if (count++ < numColors) {
						hexColor = ColorUtils.toRgbHexEncoding(cg.nextColor());
					} else {
						hexColor = "#FFFFFF";
                    }
					palette[i][j] = hexColor;
				}
			}
			return palette;
		}
		
		
		/**
		 * Maximum number of colors.
		 * @return Maximum number of colors
		 */
		int maxNumColors() {
			return 6 * componentValueSequence.length;
		}
		
		
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
			
			// "All 245s" too light
			if (combination == 6 && component >= 245) {
				component = 180;
			}
			
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
		 * @param redValue Value of first component (i.e. red)
		 * @param greenValue Value of second component (i.e. green)
		 * @param blueValue Value of third component (i.e. blue)
		 */
		private void setComponents(final int redValue,
                                   final int greenValue,
                                   final int blueValue) {
			components[0] = redValue;
			components[1] = greenValue;
			components[2] = blueValue;
		}
	}
}
