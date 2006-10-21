/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/graph/BarGroupGenerator.java,v $
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

import java.awt.Color;
import java.util.List;

import org.rti.webcgh.graphics.widget.Caption;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;


/**
 * Class lays out groups of <code>Bar</code> objects on
 * a <code>PlotPanel</code>.  Bar group is a building block of
 * a <code>BarPlot</code>.  Typically a bar group will contain
 * one bar for each bioassay.  The bar will graph the value
 * and error of a single reporter.  All bars in the bar group
 * will correspond to the same reporter.
 *
 */
public class BarGroupGenerator {
	
	// ======================================
	//         Attributes
	// ======================================
	
	// Pixels of empty space between widgets
	private int padding = 10;
	
	// Width in pixels of bars
	private int barWidth = 10;
	
	// Width in pixels of error bar lines
	private int errorBarWidth = 2;
	
	// Font size of individual bar labels 
	private int barFontSize = 10;
	
	// Font size of caption for entire group
	private int captionFontSize = 12;
	
	// Color of caption text
	private Color captionColor = Color.BLACK;
	
	// Color of bars
	private Color barColor = Color.BLACK;

	/**
	 * @return Color of bars
	 */
	public Color getBarColor() {
		return barColor;
	}

	/**
	 * @param barColor Color of bars
	 */
	public void setBarColor(Color barColor) {
		this.barColor = barColor;
	}

	/**
	 * @return Font size for individual bar labels
	 */
	public int getBarFontSize() {
		return barFontSize;
	}

	/**
	 * @param barFontSize Font size for individual bar labels
	 */
	public void setBarFontSize(int barFontSize) {
		this.barFontSize = barFontSize;
	}

	/**
	 * @return Width of bars in pixels
	 */
	public int getBarWidth() {
		return barWidth;
	}

	/**
	 * @param barWidth Width of bars in pixels
	 */
	public void setBarWidth(int barWidth) {
		this.barWidth = barWidth;
	}

	/**
	 * @return Color of caption text
	 */
	public Color getCaptionColor() {
		return captionColor;
	}

	/**
	 * @param captionColor Color of caption text
	 */
	public void setCaptionColor(Color captionColor) {
		this.captionColor = captionColor;
	}

	/**
	 * @return Size of font for caption
	 */
	public int getCaptionFontSize() {
		return captionFontSize;
	}

	/**
	 * @param captionFontSize Size of font for caption
	 */
	public void setCaptionFontSize(int captionFontSize) {
		this.captionFontSize = captionFontSize;
	}

	/**
	 * @return Width of erro bar lines in pixels
	 */
	public int getErrorBarWidth() {
		return errorBarWidth;
	}

	/**
	 * @param errorBarWidth Width of erro bar lines in pixels
	 */
	public void setErrorBarWidth(int errorBarWidth) {
		this.errorBarWidth = errorBarWidth;
	}

	/**
	 * @return Size in pixels of empty space between widgets
	 */
	public int getPadding() {
		return padding;
	}

	/**
	 * @param padding Size in pixels of empty space between widgets
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	
	// ========================================
	//         Constructors
	// ========================================
	
	/**
	 * Constructor
	 */
	public BarGroupGenerator() {}
	
	
	// ==================================
	//    Public methods
	// ==================================
	
	public void addBarGroup(PlotPanel panel, List<DataPoint> dataPoints, 
			String captionText, double scale) {
		
		// Add individual bars with labels
		for (DataPoint point : dataPoints)
			this.addBar(panel, point, scale);
		
		// Add caption
//		Caption caption = new Caption(captionText, Orientation.HORIZONTAL, false);
//		panel.add(caption, HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);		
	}
	
	
	// ====================================
	//       Private methods
	// ====================================
	
	private void addBar(PlotPanel panel, DataPoint dataPoint, double scale) {
		PlotPanel child = panel.newChildPlotPanel();
		Bar bar = new Bar(dataPoint, this.barColor, scale);
//		Caption label = new Caption(dataPoint.getLabel(), Orientation.VERTICAL, false);
//		label.setFontSize(this.barFontSize);
		child.add(bar, HorizontalAlignment.CENTERED, VerticalAlignment.ON_ZERO);
//		child.add(label, HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
		panel.add(child, HorizontalAlignment.RIGHT_OF, VerticalAlignment.ON_ZERO);
	}

}
