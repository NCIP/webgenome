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
import java.text.DecimalFormat;
import java.util.Locale;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicRect;
import org.rti.webcgh.drawing.GraphicText;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.graph.util.HeatMapColorFactory;

/**
 * Renders data scale
 */
public class DataScaleRenderer {
	
	private int fontSize = 11;
	private int scaleWidth = 200;
	private int scaleHeight = 10;
	private Color labelColor = Color.black;
	private int padding = 5;
	
	private DecimalFormat fmt = null;
	
	
	/**
	 * Constructor
	 *
	 */
	public DataScaleRenderer() {
		fmt = (DecimalFormat)DecimalFormat.getInstance(Locale.US);
		fmt.applyPattern("###.#####");
	}
	
	
	/**
	 * Constructor
	 * @param width Width of graphic in pixels
	 */
	public DataScaleRenderer(int width) {
		this();
		this.scaleWidth = width;
	}
	
	
	/**
	 * Render graphic
	 * @param canvas A canvas
	 * @param scale A scale
	 */
	public void render
	(
		 DrawingCanvas canvas, DataScale scale
	) {
		renderLabel(canvas, scale);
		renderScale(canvas, scale);
		renderScaleNumbers(canvas, scale);
	}
	
	
	protected void renderLabel(DrawingCanvas canvas, DataScale scale) {
		String label = scale.getLabel();
		int x = - (canvas.renderedWidth(label, fontSize) + padding);
		int y = scaleHeight / 2 + fontSize / 2;
		if (label != null) {
			GraphicText text = canvas.newGraphicText(label, x, y, 
			   fontSize, HorizontalAlignment.LEFT_JUSTIFIED, labelColor);
			canvas.add(text);
		}
	}
	
	
	protected void renderScale(DrawingCanvas canvas, DataScale scale) {
	    double minSat = scale.getMinSat();
	    double maxSat = scale.getMaxSat();
	    int numBins = scale.getNumBins();
		HeatMapColorFactory fac = new HeatMapColorFactory(minSat, maxSat, numBins);
		double binRange = (maxSat - minSat) / (double)numBins;
		int scaleX = 0;
		int scaleY = 0;
		int binWidth = this.binWidth(scale);
		for (int i = 0; i < numBins; i++) {
			int x = scaleX + binWidth * i;
			double value = minSat + binRange * (double)i;
			Color color = fac.getColor(value);
			GraphicRect rect = new GraphicRect(x, scaleY, binWidth, 
			   scaleHeight, color);
			canvas.add(rect);
		}
	}
	
	
	protected void renderScaleNumbers(DrawingCanvas canvas, DataScale scale) {
		 int y = scaleHeight + fontSize + padding;
		 int binWidth = binWidth(scale);
		 
		// Left side
		String minSatStr = minSatStr(scale);
		int minSatX = binWidth / 2;
		canvas.add(canvas.newGraphicText(minSatStr, minSatX, y, 
		 	fontSize, HorizontalAlignment.CENTERED, labelColor));
		 	
		// Right side
		String maxSatStr = maxSatStr(scale);
		int maxSatBin = scale.binNum(scale.getMaxSat());
		int maxSatX = binWidth * maxSatBin + binWidth / 2;
		canvas.add(canvas.newGraphicText(maxSatStr, maxSatX, y, 
		 	fontSize, HorizontalAlignment.CENTERED, labelColor));
		 	
		// Zero point
		String zeroSatStr = "0";
		if (scale.getMinSat() < 0 && scale.getMaxSat() > 0) {
			int zeroSatBin = scale.binNum(0.0);
			 int zeroSatX = binWidth * zeroSatBin + binWidth / 2;
			 int minX = minSatX + canvas.renderedWidth(minSatStr, fontSize) / 2;
			 int maxX = maxSatX - canvas.renderedWidth(maxSatStr, fontSize) / 2;
			 int zeroSatStrWidth = canvas.renderedWidth(zeroSatStr, fontSize);
			 int minZeroX = zeroSatX - zeroSatStrWidth / 2 - padding;
			 int maxZeroX = zeroSatX + zeroSatStrWidth / 2 - padding;
			 if (minZeroX > minX && maxZeroX < maxX)
			 	canvas.add(canvas.newGraphicText(zeroSatStr, zeroSatX, y, 
			 		fontSize, HorizontalAlignment.CENTERED, labelColor));
		 }
	}
	
	
	
	protected String minSatStr(DataScale scale) {
		return fmt.format(scale.getMinSat());
	}
	
	
	protected String maxSatStr(DataScale scale) {
		return fmt.format(scale.getMaxSat());
	}
	
	
	protected int binWidth(DataScale scale) {
		return  scaleWidth / scale.getNumBins();
	}
	
	
	/**
	 * Width of rendered scale in pixels
	 * @param canvas A canvas
	 * @param scale A scale
	 * @return Width of rendered scale in pixels
	 */
	public int renderedWidth(DrawingCanvas canvas, DataScale scale) {
		return maxX(canvas, scale) - minX(canvas, scale);
	}
	
	
	/**
	 * Height of rendered scale in pixels
	 * @param canvas A canvas
	 * @param scale A scale
	 * @return Height of rendered scale in pixels
	 */
	public int renderedHeight(DrawingCanvas canvas, DataScale scale) {
		return maxY(canvas, scale) - minY(canvas, scale);
	}
	
	
	/**
	 * Maximum X-axis pixel coordinate
	 * @param canvas A canvas
	 * @param scale A scale
	 * @return Maximum X-axis pixel coordinate
	 */
	public int maxX(DrawingCanvas canvas, DataScale scale) {
		return scaleWidth + canvas.renderedWidth(maxSatStr(scale), fontSize) / 2;
	}
	
	
	/**
	 * Minimum X-axis pixel coordinate
	 * @param canvas A canvas
	 * @param scale A scale
	 * @return Minimum X-axis pixel coordinate
	 */
	public int minX(DrawingCanvas canvas, DataScale scale) {
		int min = - (padding + canvas.renderedWidth(scale.getLabel(), fontSize));
		int temp = - canvas.renderedWidth(minSatStr(scale), fontSize) / 2;
		if (temp < min)
			min = temp;
		return min;
	}
	
	
	/**
	 * Maximum Y-axis pixel coordinate
	 * @param canvas A canvas
	 * @param scale A scale
	 * @return Maximum Y-axis pixel coordinate
	 */
	public int maxY(DrawingCanvas canvas, DataScale scale) {
		return scaleHeight + padding + fontSize;
	}
	
	
	/**
	 * Minimum X-axis pixel coordinate
	 * @param canvas A canvas
	 * @param scale A scale
	 * @return Minimum X-axis pixel coordinate
	 */
	public int minY(DrawingCanvas canvas, DataScale scale) {
		return 0;
	}
}
