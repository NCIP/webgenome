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
package org.rti.webcgh.graph.widgit;

import java.awt.Color;
import java.awt.Point;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.graph.PlotElement;
import org.rti.webcgh.graph.util.NumberFormatter;
import org.rti.webcgh.graph.util.RealNumberFormatter;


/**
 * A plot axis
 */
public class Axis implements PlotElement {
    
    
    // ===================================
    //     Constants
    // ===================================
    
    private final static int PIXELS_BETWEEN_NUMBERS = 5;
    
    
    // =============================
    //       Attributes
    // =============================
    
    
    private final double minValue;
    private final double maxValue;
    private final int length;
    private final Color color = Color.black;
    private final Orientation orientation;
    private final Location positionTextRelativeToHatches;
    
    private int numMinorTicsBetweenMajorTics = 5;
    private int majorTicLength = 20;
    private int minorTicLength = 8;
    private int fontSize = 12;
    private int lineThickness = 3;
    private int padding = 5;
    private NumberFormatter numberFormatter = new RealNumberFormatter(12, 4);
    
    private int minX = 0;
    private int maxX = 0;
    private int minY = 0;
    private int maxY = 0;
    private final double range;
    private final float[] multipliers = {(float)5.0, (float)2.0, (float)1.0};
    
    
    
    /**
     * @param numberFormatter The numberFormatter to set.
     */
    public void setNumberFormatter(NumberFormatter numberFormatter) {
        this.numberFormatter = numberFormatter;
    }
    
    
    /**
     * @param fontSize The fontSize to set.
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    
    /**
     * @param majorTicLength The majorTicLength to set.
     */
    public void setMajorTicLength(int majorTicLength) {
        this.majorTicLength = majorTicLength;
    }
    
    
    /**
     * @param minorTicLength The minorTicLength to set.
     */
    public void setMinorTicLength(int minorTicLength) {
        this.minorTicLength = minorTicLength;
    }
    
    
    /**
     * @param numMinorTicsBetweenMajorTics The numMinorTicsBetweenMajorTics to set.
     */
    public void setNumMinorTicsBetweenMajorTics(int numMinorTicsBetweenMajorTics) {
        this.numMinorTicsBetweenMajorTics = numMinorTicsBetweenMajorTics;
    }
    
    
    /**
     * @param lineThickness The lineThickness to set.
     */
    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }
    
    
    /**
     * @param padding The padding to set.
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }
    
    
    // ======================================
    //     Constructors
    // ======================================
    
    /**
     * @param minValue Minimum value
     * @param maxValue Maximum value
     * @param length Length in pixels
     * @param orientation Orientation
     * @param positionTextRelativeToHatches Position of hatch mark labels
     * relative to hatch marks
     */
    public Axis(double minValue, double maxValue, int length, Orientation orientation,
        Location positionTextRelativeToHatches) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.length = length;
        this.range = maxValue - minValue;
        this.orientation = orientation;
        this.positionTextRelativeToHatches = positionTextRelativeToHatches;
        if (orientation == Orientation.HORIZONTAL)
            this.maxX = length;
        else if (orientation == Orientation.VERTICAL)
            this.maxY = length;
    }
    
    
    // ===========================================
    //    Implementation of PlotElement interface
    // ===========================================
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
        
        // Paint main axis line
        Line line = null;
        if (this.orientation == Orientation.HORIZONTAL)
            line = new Line(0, 0, this.length, 0, this.lineThickness, this.color);
        else if (this.orientation == Orientation.VERTICAL)
            line = new Line(0, 0, 0, this.length, this.lineThickness, this.color);
        canvas.add(line);
        
        // Set up for generating tic marks
	    int maxNumMajorTics = this.maxNumTicsThatFitOnOneLine(canvas, this.length, this.minValue, 
	            this.maxValue, this.fontSize);
        double majorTicInterval = this.computeTicInterval(this.minValue, this.maxValue, maxNumMajorTics);
        double minorTicInterval = majorTicInterval / numMinorTicsBetweenMajorTics;
        double startingMajorTic = this.computeStartTic(this.minValue, majorTicInterval);
        
        // Create tic marks
        for (double i = startingMajorTic - majorTicInterval; i <= this.maxValue; i+= majorTicInterval) {
            
            // Major tic mark
            if (i >= this.minValue) {
                AxisTicMark tic = this.newAxisTicMark(i, true);
                tic.paint(canvas, true);
                this.adjustMinAndMax(tic);
            }
            
            // Minor tic marks
            for (double j = i + minorTicInterval; j < i + majorTicInterval; j += minorTicInterval) {
                if (j >= this.minValue && j <= this.maxValue) {
	                AxisTicMark tic = this.newAxisTicMark(j, false);
	                tic.paint(canvas, false);
	                this.adjustMinAndMax(tic);
                }
            }
        }
    }
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return new Point(0, 0);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        Point point = null;
        if (this.orientation == Orientation.HORIZONTAL)
            point = new Point(0, 0);
        else if (this.orientation == Orientation.VERTICAL)
            point = new Point(0, this.length);
        return point;
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        Point point = null;
        if (this.orientation == Orientation.HORIZONTAL)
            point = new Point(this.length, 0);
        else if (this.orientation == Orientation.VERTICAL)
            point = new Point(0, 0);
        return point;
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        Point point = null;
        if (this.orientation == Orientation.HORIZONTAL)
            point = new Point(0, 0);
        else if (this.orientation == Orientation.VERTICAL)
            point = new Point(0, this.length);
        return point;
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
        return new Point(minX, minY);
    }
    
    
    // =================================
    //    Public methods
    // =================================
    
    /**
     * Create grid with grid lines that match tic marks
     * @param width Width of grid in pixels
     * @param height Height of grid in pixels
     * @param color Color
     * @param canvas A drawing canvas
     * @return New grid
     */
    public Grid newGrid(int width, int height, Color color, DrawingCanvas canvas) {
        Grid grid = new Grid(width, height, Orientation.opposite(this.orientation), color);
	    int maxNumMajorTics = this.maxNumTicsThatFitOnOneLine(canvas, this.length, this.minValue, 
	            this.maxValue, this.fontSize);
        double majorTicInterval = this.computeTicInterval(this.minValue, this.maxValue, maxNumMajorTics);
        double startingMajorTic = this.computeStartTic(this.minValue, majorTicInterval);
        for (double x = startingMajorTic; x <= this.maxValue; x += majorTicInterval) {
            int p = this.nativeUnitsToPixel(x);
            if (x == 0.0)
            	grid.setZeroPointLocation(p);
            else
            	grid.addGridMarkPosition(p);
        }
        return grid;
    }
    
    
    // =======================================
    //      Private methods
    // =======================================
    
    
	/**
	 * Compute value of first tic mark
	 * @param min Minimum value
	 * @param interval Interval between tic marks
	 * @return Value of first tic mark
	 */
	private double computeStartTic
	(
		double min, double interval
	) {
	    float minFloat = (float)min;
	    float intervalFloat = (float)interval;
		return (double)(intervalFloat * Math.ceil(minFloat / intervalFloat));
	}

	/**
	 * Compute the interval between tic marks
	 * @param min Minimum value
	 * @param max Maximum value
	 * @param maxNumTics Maximum number of tic marks
	 * @return Interval between tic marks
	 */
	private double computeTicInterval
	(
		double min, double max, int maxNumTics
	) {
		// Special case: min == max
		if (min == max)
			return 1.0;
			
		float range = (float)Math.abs(max - min);
		float ticInterval = Float.NaN;
		float power = (float)Math.pow((float)10.0, Math.ceil(Math.log(range) / 
			Math.log((float)10.0)) + (float)1.0);
		boolean done = false;
		while (! done) {
		    for (int i = 0; i < this.multipliers.length && ! done; i++) {
					float temp = this.multipliers[i] * power;
				if (Math.ceil(range / temp) > maxNumTics)
					done = true;
				else if (range / temp > 0)
					ticInterval = temp;
		    }
			power /= (float)10.0;
		}
		return (double)ticInterval;
	}
	
	
	/**
	 * Heuristically determine how many tic marks 
	 * over given range could fit
	 * on one text line of given width.
	 * @param canvas A canvas
	 * @param width Width of target text line
	 * @param min Minimum value in range
	 * @param max Maximum value in range
	 * @param fontSize Font size
	 * @return Number of tic marks
	 */
	private int maxNumTicsThatFitOnOneLine
	(
		DrawingCanvas canvas, int width, double min, double max, 
		int fontSize
	) {
	    StringBuffer template = new StringBuffer(this.numberFormatter.format(max));
        int maxWidth = canvas.renderedWidth(template.toString(), fontSize);
	    for (int i = 0; i < this.multipliers.length; i++) {
	        template = new StringBuffer(this.numberFormatter.format(max / this.multipliers[i]));
	        int candidateMaxWidth = canvas.renderedWidth(template.toString(), fontSize);
	        if (candidateMaxWidth > maxWidth)
	            maxWidth = candidateMaxWidth;
	    }
		maxWidth += PIXELS_BETWEEN_NUMBERS;
		return (int)Math.floor((double)width / (double)maxWidth);
	}
	
	
	private int nativeUnitsToPixel(double value) {
	    return (int)(((value - this.minValue) / this.range) * (double)this.length);
	}
	
	
	private AxisTicMark newAxisTicMark(double value, boolean isMajor) {
	    String label = this.numberFormatter.format(value);
	    int pixel = this.nativeUnitsToPixel(value);
	    Point point = null;
	    if (this.orientation == Orientation.HORIZONTAL)
	        point = new Point(pixel, 0);
	    else if (this.orientation == Orientation.VERTICAL)
	        point = new Point(0, length - pixel);
	    AxisTicMark tic = new AxisTicMark(point, label, Orientation.opposite(this.orientation), 
	        this.positionTextRelativeToHatches);
	    tic.setColor(this.color);
	    tic.setFontSize(this.fontSize);
	    tic.setLineThickness(this.lineThickness);
	    tic.setPadding(this.padding);
	    if (isMajor)
	        tic.setLength(this.majorTicLength);
	    else
	        tic.setLength(this.minorTicLength);
	    return tic;
	}
	
	
	private void adjustMinAndMax(AxisTicMark ticMark) {
	    if (ticMark.minX() < this.minX)
	        this.minX = ticMark.minX();
	    if (ticMark.maxX() > this.maxX)
	        this.maxX = ticMark.maxX();
	    if (ticMark.minY() < this.minY)
	        this.minY = ticMark.minY();
	    if (ticMark.maxY() > this.maxY)
	        this.maxY = ticMark.maxY();
	}

}
