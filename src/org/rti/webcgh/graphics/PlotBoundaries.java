/*
$Revision: 1.4 $
$Date: 2006-10-21 05:35:05 $

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


package org.rti.webcgh.graphics;

import java.awt.geom.Rectangle2D;

import org.rti.webcgh.deprecated.graph.DataPoint;

/**
 * Defines region over x- and y-axes that contains plot.
 */
public class PlotBoundaries {
    
    // ================================
    //        Attributes
    // ================================
    
    /**
     * Point in native units corresponding to
     * the bottom left of the plot.
     */
    private final DataPoint bottomLeftDataPoint;
    
    /**
     * Point in native units corresponding to
     * the bottom right of the plot.
     */
    private final DataPoint bottomRightDataPoint;
    
    /**
     * Point in native units corresponding to
     * the top left of the plot.
     */
    private final DataPoint topLeftDataPoint;
    
    /**
     * Point in native units corresponding to
     * the bottom left of the plot.
     */
    private final DataPoint topRightDataPoint;
    
    
    /**
     * Difference in native units between left and
     * right side of plot.
     */
    private final double deltaX;
    
    /**
     * Difference in native units between top and
     * bottom of plot.
     */
    private final double deltaY;
    
    /**
     * Bounding box defining perimeter of plotting
     * region in the coordinate space based
     * on native units (i.e., base pairs vs.
     * some quantitation type).
     */
    private final Rectangle2D.Double boundingBox;
    
    // ===================================
    //    Constructors
    // ===================================
    
    /**
     * Constructor.
     * @param bottomLeftDataPoint Bottom left data point
     * @param topRightDataPoint Top right data point
     */
    public PlotBoundaries(final DataPoint bottomLeftDataPoint,
            final DataPoint topRightDataPoint) {
        this.bottomLeftDataPoint = bottomLeftDataPoint;
        this.bottomRightDataPoint =
            new DataPoint(topRightDataPoint.getValue1(),
                    bottomLeftDataPoint.getValue2());
        this.topLeftDataPoint = new DataPoint(
                bottomLeftDataPoint.getValue1(),
                topRightDataPoint.getValue2());
        this.topRightDataPoint = topRightDataPoint;
        this.deltaX = this.topRightDataPoint.getValue1()
            - this.bottomLeftDataPoint.getValue1();
        this.deltaY = this.topRightDataPoint.getValue2()
            - this.bottomLeftDataPoint.getValue2();
        this.boundingBox = new Rectangle2D.Double(
                this.bottomLeftDataPoint.getValue1(),
                this.bottomLeftDataPoint.getValue2(),
                this.deltaX, this.deltaY);
    }
    
    
    /**
     * Constructor.
     * @param minX Minimum X-axis value
     * @param minY Minimum Y-axis value
     * @param maxX Maximum X-axis value
     * @param maxY Maximum Y-axis value
     */
    public PlotBoundaries(final double minX, final double minY,
            final double maxX, final double maxY) {
    	this(new DataPoint(minX, minY), new DataPoint(maxX, maxY));
    }
    
    
    // ===================================
    //      Business methods
    // ===================================
    
    /**
     * Is given data point within the boundaries of this plot?
     * @param dataPoint A data point
     * @return T/F
     */
    public final boolean withinBoundaries(final DataPoint dataPoint) {
        return
			dataPoint.getValue2() >= this.bottomLeftDataPoint.getValue2()
			&& dataPoint.getValue1() >= this.bottomLeftDataPoint.getValue1()
            && dataPoint.getValue2() <= this.topRightDataPoint.getValue2()
            && dataPoint.getValue1() <= this.topRightDataPoint.getValue1();
    }
    
    /**
     * Is data point represented by <code>x</code> and <code>y</code>
     * within plot?
     * @param x X-coordinate value
     * @param y Y-coordinate value
     * @return T/F
     */
    public final boolean withinBoundaries(final double x, final double y) {
        return x >= this.bottomLeftDataPoint.getValue1()
            && x <= this.topRightDataPoint.getValue1()
            && y >= this.bottomLeftDataPoint.getValue2()
            && y <= this.topRightDataPoint.getValue1();
    }
    
    
    /**
     * Get fractional distance from left edge of plot.
     * @param dataPoint A data point
     * @return Fractional distance from left edge of plot
     * (i.e. over range 0 - 1.0)
     */
    public final double fractionalDistanceFromLeft(final DataPoint dataPoint) {
        return (dataPoint.getValue1()
                - this.bottomLeftDataPoint.getValue1()) / this.deltaX;
    }
    
    
    /**
     * Get fractional distance from left edge of plot.
     * @param value A value
     * @return Fractional distance from left edge of plot
     * (i.e. over range 0 - 1.0)
     */
    public final double fractionalDistanceFromLeft(final double value) {
        return (value
                - this.bottomLeftDataPoint.getValue1()) / this.deltaX;
    }
    
    
    /**
     * Get fractional distance from bottom edge of plot.
     * @param dataPoint A data point
     * @return Fractional distance from bottom edge of plot
     * (i.e. over range 0 - 1.0)
     */
    public final double fractionalDistanceFromBottom(
            final DataPoint dataPoint) {
        return (dataPoint.getValue2()
                - this.bottomLeftDataPoint.getValue2()) / this.deltaY;
    }
    
    
    /**
     * What fraction of plot heigh is given height?
     * @param height Height
     * @return Fraction of plot height (i.e. over range 0.0 - 1.0)
     */
    public final double fractionalHeight(final double height) {
        return height / this.deltaY;
    }
    
    
    /**
     * Is at least part of line on plot?
     * @param point1 First line endpoint
     * @param point2 Second line endpoint
     * @return T/F
     */
    public final boolean atLeastPartlyOnPlot(final DataPoint point1,
            final DataPoint point2) {
        return this.boundingBox.intersectsLine(point1.getValue1(),
                point1.getValue2(), point2.getValue1(), point2.getValue2());
    }
    
    
    /**
     * Is data point to left of plot?
     * @param dataPoint Data point
     * @return T/F
     */
    public final boolean leftOfPlot(final DataPoint dataPoint) {
    	return dataPoint.getValue1()
            < this.bottomLeftDataPoint.getValue1();
    }
    
    
    /**
     * Is data point to right of plot?
     * @param dataPoint Data point
     * @return T/F
     */
    public final boolean rightOfPlot(final DataPoint dataPoint) {
    	return dataPoint.getValue1() > this.topRightDataPoint.getValue1();
    }
    
    
    /**
     * Is data point above plot?
     * @param dataPoint Data point
     * @return T/F
     */
    public final boolean abovePlot(final DataPoint dataPoint) {
    	return dataPoint.getValue2() > this.topRightDataPoint.getValue2();
    }
    
    
    /**
     * Is data point below plot?
     * @param dataPoint Data point
     * @return T/F
     */
    public final boolean belowPlot(final DataPoint dataPoint) {
    	return dataPoint.getValue2() < this.bottomLeftDataPoint.getValue2();
    }
    
    
    /**
     * Modify points so that entire line on plot.  The contract for this
     * method is that neither points are to left or right of plot.
     * @param point1 First endpoint of line
     * @param point2 Second endpoint of line
     */
    public final void truncateToFitOnPlot(final DataPoint point1,
            final DataPoint point2) {
        double slope = DataPoint.slope(point1, point2);
        if (!this.withinBoundaries(point1)) {
            this.moveDataPointToBorder(point1, slope);
        }
        if (!this.withinBoundaries(point2)) {
            this.moveDataPointToBorder(point2, slope);
        }
    }
    
    /**
     * Union given plot boundary with this. (i.e. add additional
     * space to this.)
     * @param plotBoundaries Plot boundaries
     */
    public final void union(final PlotBoundaries plotBoundaries) {

    	// Adjust bottom left
    	DataPoint bLeft = plotBoundaries.bottomLeftDataPoint;
    	if (bLeft.getValue1() < this.bottomLeftDataPoint.getValue1()) {
    		this.bottomLeftDataPoint.setValue1(bLeft.getValue1());
        }
    	if (bLeft.getValue2() < this.bottomLeftDataPoint.getValue2()) {
    		this.bottomLeftDataPoint.setValue2(bLeft.getValue2());
        }
    	
    	// Adjust top left
    	DataPoint tLeft = plotBoundaries.topLeftDataPoint;
    	if (tLeft.getValue1() < this.topLeftDataPoint.getValue1()) {
    		this.topLeftDataPoint.setValue1(tLeft.getValue1());
        }
    	if (tLeft.getValue2() > this.topLeftDataPoint.getValue2()) {
    		this.topLeftDataPoint.setValue2(tLeft.getValue2());
        }
    	
    	// Adjust bottom right
    	DataPoint bRight = plotBoundaries.bottomRightDataPoint;
    	if (bRight.getValue1() > this.bottomRightDataPoint.getValue1()) {
    		this.bottomRightDataPoint.setValue1(bRight.getValue1());
        }
    	if (bRight.getValue2() < this.bottomRightDataPoint.getValue2()) {
    		this.bottomRightDataPoint.setValue2(bRight.getValue2());
        }
    	
    	// Adjust top right
    	DataPoint tRight = plotBoundaries.topRightDataPoint;
    	if (tRight.getValue1() > this.topRightDataPoint.getValue1()) {
    		this.topRightDataPoint.setValue1(tRight.getValue1());
        }
    	if (tRight.getValue2() > this.topRightDataPoint.getValue2()) {
    		this.topRightDataPoint.setValue2(tRight.getValue2());
        }
    }
    
    
    /**
     * Get minimum value1.
     * @return Minimum value1
     */
    public final double getMinValue1() {
    	return this.bottomLeftDataPoint.getValue1();
    }
    
    
    /**
     * Get maximum value1.
     * @return Maximum value1
     */
    public final double getMaxValue1() {
    	return this.bottomRightDataPoint.getValue1();
    }
    
    
    // =========================================
    //       Private methods
    // =========================================
    
    /**
     * Move given data point to border of drawing area along
     * a line of given slope containing given data point.
     * @param point Data point to move
     * @param slope Slope of line upon which to move data point
     */
    private void moveDataPointToBorder(final DataPoint point,
            final double slope) {
        double y = 0.0;
        if (this.abovePlot(point)) {
            y = this.topRightDataPoint.getValue2();
        } else if (this.belowPlot(point)) {
            y = this.bottomLeftDataPoint.getValue2();
        }
        double x = point.getValue1() + (y - point.getValue2()) / slope;
        point.setValue1(x);
        point.setValue2(y);
    }

}
