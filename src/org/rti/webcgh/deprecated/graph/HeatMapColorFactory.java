/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/graph/HeatMapColorFactory.java,v $
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
import java.awt.Point;

import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.Rectangle;
import org.rti.webcgh.graphics.primitive.Text;
import org.rti.webcgh.graphics.widget.PlotElement;
import org.rti.webcgh.units.HorizontalAlignment;

/**
 * Generates heat map colors
 */
public class HeatMapColorFactory implements PlotElement {
    
    // ========================================
    //       Attributes
    // ========================================
    
    private final Color[] binColors;
    private final double lowSaturation;
    private final double highSaturation;
    private final int maxBinIndex;
    private final double binRange;
    
    private int maxX = 0;
    private int maxY = 0;
    private int minX = 0;
    private int minY = 0;
    
    private int scaleWidth = 200;
    private int scaleHeight = 25;
    private int padding = 5;
    private int fontSize = 12;
    private Color textColor = Color.black;
    private RealNumberFormatter formatter = new RealNumberFormatter(2, 4);
    
    
    /**
     * @param formatter The formatter to set.
     */
    public void setFormatter(RealNumberFormatter formatter) {
        this.formatter = formatter;
    }
    
    
    /**
     * @param fontSize The fontSize to set.
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    
    /**
     * @param textColor The textColor to set.
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    
    /**
     * @param padding The padding to set.
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }
    
    
    /**
     * @param height The height to set.
     */
    public void setScaleHeight(int height) {
        this.scaleHeight = height;
    }
    
    
    /**
     * @param width The width to set.
     */
    public void setScaleWidth(int width) {
        this.scaleWidth = width;
    }
    
    
    // ==================================================
    //     Constructors
    // ==================================================
    
    /**
     * Constructor
     * @param lowSaturation Lower saturation value (i.e. all values below this will produce RGB(0, 255, 0))
     * @param highSaturation Upper saturation value (i.e. all values above this will produce RGB(255, 0, 0))
     * @param numBins Number of bins
     * @throws IllegalArgumentException if <code>lowSaturation > highSaturation</code> or
     * <code>numBins <= 0</code> 
     */
    public HeatMapColorFactory(double lowSaturation, double highSaturation, int numBins) {
        if (numBins <= 0)
            throw new IllegalArgumentException("Num bins must be a positive integer");
        if (lowSaturation > highSaturation)
            throw new IllegalArgumentException("Low saturation value cannot be greater than high saturation value");
        this.binColors = new Color[numBins];
        this.lowSaturation = lowSaturation;
        this.highSaturation = highSaturation;
        this.maxBinIndex = numBins - 1;
        this.binRange = (highSaturation - lowSaturation) / numBins;
        this.initializeBinColors();
    }
    
    
    /**
     * Constructor
     * @param factory Heat map color factory
     */
    public HeatMapColorFactory(HeatMapColorFactory factory) {
        this(factory.lowSaturation, factory.highSaturation, factory.maxBinIndex - 1);
    }
    
 
    // ======================================
    //    PlotElement interface
    // ======================================
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
        
        // Add rectanbles
        int binWidth = this.scaleWidth / (this.maxBinIndex + 1);
        for (int i = 0; i <= this.maxBinIndex; i++) {
            int x = binWidth * i;
            int y = 0;
            Rectangle rect = new Rectangle(x, y, binWidth, this.scaleHeight, this.binColors[i]);
            canvas.add(rect);
        }
        
        // Set initial min and max values
        this.minX = 0;
        this.maxX = this.scaleWidth;
        this.minY = 0;
        this.maxY = this.scaleHeight;
        
        // Draw text
        int y = this.scaleHeight + this.padding + this.fontSize;
        int left = this.addText(this.lowSaturation, canvas, y) + 
        	canvas.renderedWidth(String.valueOf(this.lowSaturation), this.fontSize) + this.padding;
        int right = this.addText(this.highSaturation, canvas, y) -
        	canvas.renderedWidth(String.valueOf(this.highSaturation), this.fontSize) - this.padding;
        this.addText(0.0, canvas, y, left, right);
    }
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return new Point(this.minX, this.minY + this.scaleHeight);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.minX + this.scaleWidth, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.minX + this.scaleWidth, this.minY + this.scaleHeight);
    }
    
    
    /**
     * Width in pixels
     * @return Width in pixels
     */
    public int width() {
        return this.maxX - this.minX;
    }
    
    
    /**
     * Height in pixels
     * @return Height in pixels
     */
    public int height() {
        return this.maxY - this.minY;
    }
    
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Move element
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(int deltaX, int deltaY) {
    	this.maxX += deltaX;
    	this.maxY += deltaY;
    	this.minX += deltaX;
    	this.minY += deltaY;
    }
    
    
    // ==============================================
    //         Private methods
    // ==============================================
    
    
    /**
     * Initialize bin colors
     *
     */
    private void initializeBinColors() {
        
        // Generate colors from green to blue
        Color startColor = Color.green;
        Color endColor = Color.blue;
        int startBin = 0;
        int endBin = 0;
        if (this.lowSaturation < 0.0 && this.highSaturation > 0.0)
            endBin = this.binNumber(0.0);
        else
            endBin = this.binNumber((this.highSaturation - this.lowSaturation) / 2.0);
        this.initializeBinColors(startBin, endBin, startColor, endColor);
        
        // Generate colors blue to red
        startColor = Color.blue;
        endColor = Color.red;
        startBin = endBin;
        endBin = this.maxBinIndex;
        this.initializeBinColors(startBin, endBin, startColor, endColor);
    }
    
    
    /**
     * Initialize bin colors
     * @param startBin Starting bin index
     * @param endBin Ending bin index
     * @param startColor Starting color
     * @param endColor Ending color
     */
    private void initializeBinColors(int startBin, int endBin, Color startColor, Color endColor) {
    	//TODO:VB-COMMENTED TO COMPILE
        //assertTrue endBin >= startBin && startColor != null && endColor != null;
        int r = startColor.getRed();
        int g = startColor.getGreen();
        int b = startColor.getBlue();
        double range = (double)(endBin - startBin);
        double rSlope = (double)(endColor.getRed() - r) / range;
        double gSlope = (double)(endColor.getGreen() - g) / range;
        double bSlope = (double)(endColor.getBlue() - b) / range;
        for (int i = startBin; i <= endBin; i++) {
            int relativeBinNum = i - startBin;
            int newR = r + (int)(rSlope * relativeBinNum);
            int newG = g + (int)(gSlope * relativeBinNum);
            int newB = b + (int)(bSlope * relativeBinNum);
            this.binColors[i] = new Color(newR, newG, newB);
        }
    }
    
    
    /**
     * Get color associated with given value
     * @param value A value
     * @return A color
     * @throws IllegalArgumentException if <code>value</code> is not a valid real number
     * (i.e. is NaN or infinite)
     */
    public Color getColor(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Value must be real number");
        int binNum = this.binNumber(value);
        return this.binColors[binNum];
    }
    
    
    /**
     * Get number of bin associated with value
     * @param value A value
     * @return Bin number
     */
    private int binNumber(double value) {
        int binNum = (int)Math.floor((value - this.lowSaturation) / this.binRange);
        if (binNum < 0)
            binNum = 0;
        if (binNum > this.maxBinIndex)
            binNum = this.maxBinIndex;
        return binNum;
    }
    
    
    private int addText(double value, DrawingCanvas canvas, int y) {
        if (value < this.lowSaturation || value > this.highSaturation)
            return Integer.MIN_VALUE;
        int x = this.pixel(value);
        String txt = this.formatter.format(value);
        int width = canvas.renderedWidth(txt, this.fontSize);
        Text graphic = canvas.newText(txt, x, y, this.fontSize, HorizontalAlignment.CENTERED, this.textColor);
        canvas.add(graphic);
        int candidateMin = x - width / 2;
        if (candidateMin < this.minX)
            this.minX = candidateMin;
        int candidateMax = x + width / 2;
        if (candidateMax > this.maxX)
            this.maxX = candidateMax;
        this.maxY = y;
        return x;
    }
    
    
    private void addText(double value, DrawingCanvas canvas, int y, int left, int right) {
    	int x = this.pixel(value);
    	int width = canvas.renderedWidth(String.valueOf(value), this.fontSize);
    	int minX = x - width / 2;
    	int maxX = x + width / 2;
    	if (minX > left && maxX < right)
    		this.addText(value, canvas, y);
    }
    
    
    private int pixel(double value) {
    	return (int)((double)this.scaleWidth / (this.highSaturation - this.lowSaturation) * (value - this.lowSaturation));
    }

}
