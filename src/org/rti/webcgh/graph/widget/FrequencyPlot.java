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
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Rectangle;
import org.rti.webcgh.graph.DataPoint;

/**
 * 
 */
public class FrequencyPlot implements DataPlotter {
    
    
    // ======================================
    //      Attributes
    // ======================================
    
    private final int width;
    private final double scale;
    private final static NumberFormat FORMAT = new DecimalFormat("###.###");
    
    private int maxX = 0;
    private int maxY = 0;
    private int minX = 0;
    private int minY = 0;
    private List dataPoints = new ArrayList();
    
    private final int height;
    private double minMaskValue = Double.MAX_VALUE;
    private double maxMaskValue = Double.MIN_VALUE;
    
    /**
     * @param maxMaskValue The maxMaskValue to set.
     */
    public void setMaxMaskValue(double maxMaskValue) {
        this.maxMaskValue = maxMaskValue;
    }
    
    
    /**
     * @param minMaskValue The minMaskValue to set.
     */
    public void setMinMaskValue(double minMaskValue) {
        this.minMaskValue = minMaskValue;
    }
    
    
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
     * @param minSaturation Minimum saturation value
     * @param maxSaturation Maximum saturation value
     */
    public FrequencyPlot(int width, int height, long bp) {
        this.width = width;
        this.height = height;
        this.scale = (double)width / (double)bp;
        this.setExtremes();
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
        int max = this.pixel(dataPoint.getValue1());
    	if (max > this.maxX)
    		this.maxX = max;
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
        return true;
    }
    
    
    // =============================================
    //         PlotElement interface
    // =============================================
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
    	List sorted = new ArrayList();
    	int count = 0;
    	for (Iterator it = this.dataPoints.iterator(); it.hasNext();) {
    		DataPoint dp = (DataPoint)it.next();
    		sorted.add(new DataPointIndex(dp, count++));
    	}
    	Collections.sort(sorted);
    	for (Iterator it = sorted.iterator(); it.hasNext();) {
    		DataPointIndex dpi = (DataPointIndex)it.next();
            DataPoint centerDp = dpi.dataPoint;
            DataPoint startDp = null;
            DataPoint endDp = null;
            if (! this.masked(centerDp)) {
            	if (dpi.index > 0)
            		startDp = (DataPoint)this.dataPoints.get(dpi.index - 1);
            	else
            		startDp = centerDp;
            	if (dpi.index < this.dataPoints.size() - 1)
            		endDp = (DataPoint)this.dataPoints.get(dpi.index + 1);
            	else
            		endDp = centerDp;
            	long startBp = ((long)(startDp.getValue1() + centerDp.getValue1())) / 2;
            	long endBp = ((long)(centerDp.getValue1() + endDp.getValue1())) / 2;
            	int p = this.pixel((double)startBp);
            	int q = this.pixel((double)endBp);
            	double value = centerDp.getValue2();
	            int x = 0, y = 0, width = 0, height = 0;
                width = q - p;
                x = p;
                if (height >= 0) {
	                height = (int)(value * (double)this.height);
	                y = this.height - height;
		            Rectangle rect = new Rectangle(x, y, width, height, Color.BLACK);
		            long startMb = startBp / 1000000;
		            long endMb = endBp / 1000000;
		            String mouseOver = FORMAT.format(value) + " [" + startMb + "MB-" + endMb + "MB]";
		            rect.setToolTipText(mouseOver);
		            canvas.add(rect);
                }
            }
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
    
    private void setExtremes() {
            this.maxX = this.width;
            this.maxY = this.height;
    }
    
    
    private int pixel(double bp) {
        return (int)(this.scale * bp);
    }
    
    
    private boolean masked(DataPoint dp) {
        if (Double.isNaN(this.minMaskValue) || Double.isNaN(this.maxMaskValue))
            return false;
        return dp.getValue2() >= this.minMaskValue && dp.getValue2() <= this.maxMaskValue;
    }
    
    
    // ====================================================
    //       Inner classes
    // ====================================================
    
    
    static class DataPointIndex implements Comparable {
    	
    	public DataPoint dataPoint = null;
    	public int index = -1;
    	
    	public DataPointIndex(DataPoint dataPoint, int index) {
    		this.dataPoint = dataPoint;
    		this.index = index;
    	}
    	
    	
    	public int compareTo(Object obj) {
    		int val = 0;
    		DataPointIndex dpi = (DataPointIndex)obj;
    		if (this.dataPoint.getValue2() < dpi.dataPoint.getValue2())
    			val = -1;
    		else if (this.dataPoint.getValue2() > dpi.dataPoint.getValue2())
    			val = 1;
    		return val;
    	}
    }
    
    
    

}
