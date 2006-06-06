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
package org.rti.webcgh.graph.widget;

import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Rectangle;
import org.rti.webcgh.graph.DataPoint;

/**
 * 
 */
public class FrequencyPlot implements DataPlotter {
	
	private static final int DEFAULT_BAR_WIDTH = 10;
    
    
    // ======================================
    //      Attributes
    // ======================================
    
    private final int width;
    private final double xScale;
    private final double yScale;
    private final double minFrequency;
    private final double maxFrequency;
    private final long startBp;
    private final long endBp;
    
    private final static NumberFormat FORMAT = new DecimalFormat("###.###");
    
    private int maxX = 0;
    private int maxY = 0;
    private int minX = 0;
    private int minY = 0;
    private List<DataPoint> dataPoints = new ArrayList<DataPoint>();
    
    private final int height;
    private boolean sorted = false;
    
    
    /**
     * @return Returns the thickness.
     */
    public int getHeight() {
        return height;
    }
   
    
    
    // =======================================
    //        Constructor
    // =======================================
    
    /**
     * Constructor
     * @param width Width in pixels
     * @param height Height in pixels
     * @param startBp Starting point in base pairs of plot
     * @param endBp Ending point in base pairs of plot
     * @param minFequency Minimum frequency in plot
     * @param maxFrequency Maximum frequency in plot
     */
    public FrequencyPlot(int width, int height, long startBp, long endBp, double minFrequency, 
    		double maxFrequency) {
    	
    	// Check arguments
    	if (width < 1)
    		throw new IllegalArgumentException("Width of frequency plot must be a positive integer");
    	if (height < 1)
    		throw new IllegalArgumentException("Height of frequency plot must be a positive integer");
    	if (startBp < (long)0)
    		throw new IllegalArgumentException("Starting base pair in frequency plot must be a positive integer or zero");
    	if (endBp < (long)0)
    		throw new IllegalArgumentException("End base pair in frequency plot must be a positive integer or zero");
    	if (startBp >= endBp)
    		throw new IllegalArgumentException("Starting base pair in frequency plot must be smaller than end base pair");
    	if (minFrequency >= maxFrequency)
    		throw new IllegalArgumentException("Minimum frequency plot value must be smaller than maximum value");
    	
    	// Initialize properties
        this.width = width;
        this.height = height;
        this.maxX = this.width;
        this.maxY = this.height;
        this.minFrequency = minFrequency;
        this.maxFrequency = maxFrequency;
        this.startBp = startBp;
        this.endBp = endBp;
        this.xScale = (double)width / (double)(endBp - startBp);
        this.yScale = (double)height / (maxFrequency - minFrequency);
    }
    
    
    // =============================================
    //        DataPlotter interface
    // =============================================
    
    
    /**
     * Graph a data point
     * @param dataPoint A data point
     * @param pointGroupKey Key identifying a group of data points to which
     * given data point should be associated
     */
    public void graphPoint(DataPoint dataPoint, Object pointGroupKey) {
        this.dataPoints.add(dataPoint);
        this.sorted = false;
    }
    
    
    /**
     * Graph a line between given data points
     * @param dataPoints Data points
     * @param key Key identifying group of lines specified by given data points
     */
    public void graphLines(DataPoint[] dataPoints, Object key) {
        
    }
    
    
    /**
     * Set color of group of pints
     * @param pointGroupKey Key identifying group of points
     * @param color Color
     */
    public void setGroupColor(Object pointGroupKey, Color color) {
        
    }
    
    
    /**
     * Is data point in plot?
     * @param dataPoint A data point
     * @return T/F
     */
    public boolean inPlotRange(DataPoint dataPoint) {
        return
        	dataPoint.getValue1() >= (double)this.startBp &&
        	dataPoint.getValue1() <= (double)this.endBp &&
        	dataPoint.getValue2() >= this.minFrequency &&
        	dataPoint.getValue2() <= this.maxFrequency;
    }
    
    
    // =============================================
    //         PlotElement interface
    // =============================================
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
    	
    	// Boundary case: no data points
    	if (this.dataPoints.size() < 1)
    		return;
    	
    	// Sort data points
    	if (! this.sorted)
    		Collections.sort(this.dataPoints, new DataPointComparator());
    	
    	// Iterate over data points and draw
    	for (int i = 0; i < this.dataPoints.size(); i++) {
    		
    		// Determine start and end base pairs for bar
            DataPoint centerDp = this.dataPoints.get(i);
            DataPoint startDp = null;
            DataPoint endDp = null;
        	if (i > 0)
        		startDp = this.dataPoints.get(i - 1);
        	else
        		startDp = centerDp;
        	if (i < this.dataPoints.size() - 1)
        		endDp = this.dataPoints.get(i + 1);
        	else
        		endDp = centerDp;
        	long startBp = ((long)(startDp.getValue1() + centerDp.getValue1())) / 2;
        	long endBp = ((long)(centerDp.getValue1() + endDp.getValue1())) / 2;
        	
        	// Calculate bar position and dimensions
        	int x = 0, width = 0;
        	if (startBp == endBp) { // This should only occur if there is only 1 data point
        		x = this.xPixel((double)startBp) - DEFAULT_BAR_WIDTH / 2;
        		width = DEFAULT_BAR_WIDTH;
        	} else {
        		x = this.xPixel((double)startBp);
        		width = this.xPixel((double)endBp) - x;
        	}
        	int barHeight = (int)(centerDp.getValue2() * this.yScale);
        	int y = this.height - barHeight;
        	
        	// Draw bar
            Rectangle rect = new Rectangle(x, y, width, barHeight, Color.BLACK);
            long startMb = startBp / 1000000;
            long endMb = endBp / 1000000;
            String mouseOver = FORMAT.format(centerDp.getValue2()) + " [" + startMb + "MB-" + endMb + "MB]";
            rect.setToolTipText(mouseOver);
            canvas.add(rect);
        }
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
        return new Point(this.minX, this.maxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.minX, this.maxX);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.maxX, this.maxY);
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
    	this.minX += deltaX;
    	this.maxX += deltaX;
    	this.minX += deltaY;
    	this.maxY += deltaY;
    }
    
    
    // =============================================
    //           Private methods
    // =============================================
    
    
    private int xPixel(double bp) {
        return (int)(this.xScale * bp);
    }
    
    
    // ====================================================
    //       Inner classes
    // ====================================================
    
    
    static class DataPointComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			if (! (o1 instanceof DataPoint) || ! (o2 instanceof DataPoint))
				throw new IllegalArgumentException("All objects must be of type DataPoint");
			int result = 0;
			DataPoint dp1 = (DataPoint)o1;
			DataPoint dp2 = (DataPoint)o2;
			if (dp1.getValue1() < dp2.getValue1())
				result = -1;
			else if (dp1.getValue1() == dp2.getValue1())
				result = 0;
			else if (dp1.getValue1() > dp2.getValue1())
				result = 1;
			return result;
		}
    	
    }
    
    
    

}
