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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Circle;
import org.rti.webcgh.drawing.GraphicEvent;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Polyline;
import org.rti.webcgh.graph.DataPoint;
import org.rti.webcgh.graph.PlotBoundaries;

/**
 * 
 */
public class ScatterPlot implements DataPlotter {
    
    
    // ================================
    //     Constants
    // ================================
    
    private static final Color DEFAULT_COLOR = Color.black;
    public static final String GRP_ATT_NAME = "egrp";
    private static final String POINTS_GRP_ATT_VALUE = "p";
    private static final String LINES_GRP_ATT_VALUE = "l";
    private static final String ERROR_BARS_GRP_ATT_VALUE = "e";
    
    
    // ====================================
    //      Attributes
    // ====================================
    
    private int pointRadius = 3;
    private int lineWidth = 2;
    private int errorBarHatchLength = 6;
    private int maxNumPointsInLine = 100;
    private final int width;
    private final int height;
    private final Map pointsIndex = new HashMap();
    private final Map linesIndex = new HashMap();
    private final Map groupColorIndex = new HashMap();
    private final PlotBoundaries plotBoundaries;
    
    
    /**
     * @return Returns the maxNumPointsInLine.
     */
    public int getMaxNumPointsInLine() {
        return maxNumPointsInLine;
    }
    
    
    /**
     * @param maxNumPointsInLine The maxNumPointsInLine to set.
     */
    public void setMaxNumPointsInLine(int maxNumPointsInLine) {
        this.maxNumPointsInLine = maxNumPointsInLine;
    }
    
    
    /**
     * @param errorBarHatchLength The errorBarHatchLength to set.
     */
    public void setErrorBarHatchLength(int errorBarHatchLength) {
        this.errorBarHatchLength = errorBarHatchLength;
    }
    
    
    /**
     * @param lineWidth The lineWidth to set.
     */
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
    
    
    /**
     * @param pointRadius The pointRadius to set.
     */
    public void setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
    }
    
    
    // ========================================
    //         Constructors
    // ========================================
    
    /**
     * Constructor
     * @param plotBoundaries Boundaries of plot
     * @param width Width in pixels
     * @param height Height in pixels
     */
    public ScatterPlot(PlotBoundaries plotBoundaries, int width, int height) {
        this.plotBoundaries = plotBoundaries;
        this.width = width;
        this.height = height;
    }
    
    
    // =======================================
    //   Methods in DataPlotter interface
    // =======================================
    
    /**
     * Is data point in plot?
     * @param dataPoint A data point
     * @return T/F
     */
    public boolean inPlot(DataPoint dataPoint) {
        return
        	this.plotBoundaries.withinBoundaries(dataPoint);
    }
    
    /**
     * Graph a data point
     * @param dataPoint A data point
     * @param pointGroupKey Key identifying a group of data points to which
     * given data point should be associated
     */
    public void graphPoint(DataPoint dataPoint, Object pointGroupKey) {
        List points = (List)this.pointsIndex.get(pointGroupKey);
        if (points == null) {
            points = new ArrayList();
            this.pointsIndex.put(pointGroupKey, points);
        }
        points.add(dataPoint);
    }
    
    
    /**
     * Graph a line between given data points
     * @param dataPoints Data points
     * @param key Key identifying group of lines specified by given data points
     */
    public void graphLines(DataPoint[] dataPoints, Object key) {
        List lines = (List)this.linesIndex.get(key);
        if (lines == null) {
            lines = new ArrayList();
            this.linesIndex.put(key, lines);
        }
        lines.add(dataPoints);
    }
    
    
    /**
     * Set color of group of pints
     * @param pointGroupKey Key identifying group of points
     * @param color Color
     */
    public void setGroupColor(Object pointGroupKey, Color color) {
        this.groupColorIndex.put(pointGroupKey, color);
    }
    
    
    
    // =======================================
    //      Methods in PlotElement interface
    // =======================================
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(0, 0);
    }
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
    	
    	// Generate list of keys
    	Set keys = this.pointsIndex.keySet();
    	
    	// Paint points and lines
    	for (Iterator it = keys.iterator(); it.hasNext();) {
    		Object key = it.next();
    		Color color = (Color)this.groupColorIndex.get(key);
            if (color == null)
                color = DEFAULT_COLOR;
            DrawingCanvas tile = canvas.newTile();
            tile.setAttribute(GRP_ATT_NAME, key.toString());
            tile.setLineWidth(1);
            canvas.add(tile);
            List points = (List)this.pointsIndex.get(key);
            
            if (points != null) {
            	
            	// Points
            	DrawingCanvas pointsTile = tile.newTile();
            	tile.add(pointsTile);
            	pointsTile.setAttribute(GRP_ATT_NAME, POINTS_GRP_ATT_VALUE);
            	this.paintPoints(points, color, pointsTile);
            	
            	// Error bars
            	DrawingCanvas errorBarsTile = tile.newTile();
            	tile.add(errorBarsTile);
            	errorBarsTile.setAttribute(GRP_ATT_NAME, ERROR_BARS_GRP_ATT_VALUE);
            	this.paintErrorBars(points, color, errorBarsTile);
            }
        
	    	// Lines
	    	List lines = (List)this.linesIndex.get(key);
	    	if (lines != null) {
	    		DrawingCanvas linesTile = tile.newTile();
	    		tile.add(linesTile);
	    		linesTile.setAttribute(GRP_ATT_NAME, LINES_GRP_ATT_VALUE);
	    		String command = "highlight('" + key + "')";
	    		linesTile.addGraphicEventResponse(GraphicEvent.mouseClickEvent, command);
	    		this.paintLines(lines, color, linesTile);
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
        return new Point(0, this.height);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.width, 0);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.width, this.height);
    }
    
    
    /**
     * Width in pixels
     * @return Width in pixels
     */
    public int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels
     * @return Height in pixels
     */
    public int height() {
        return this.height;
    }
    
    
    // ======================================
    //     Private methods
    // ======================================
    
    
    private void paintPoints(List dataPoints, Color color, DrawingCanvas drawingCanvas) {
        for (Iterator it = dataPoints.iterator(); it.hasNext();) {
            DataPoint point = (DataPoint)it.next();
            this.paintPoint(point, color, drawingCanvas);
        }
    }
    
    
    private void paintErrorBars(List dataPoints, Color color, DrawingCanvas drawingCanvas) {
        for (Iterator it = dataPoints.iterator(); it.hasNext();) {
            DataPoint point = (DataPoint)it.next();
            this.paintErrorBars(point, color, drawingCanvas);
        }
    }
    
    
    
    /**
     * Paint a data point
     * @param dataPoint A data point
     * @param color A color
     * @param drawingCanvas A drawing canvas
     */
    private void paintPoint(DataPoint dataPoint, Color color, 
    		DrawingCanvas drawingCanvas) {
        int x = this.transposeX(dataPoint);
        int y = this.transposeY(dataPoint);
        this.drawPoint(x, y, color, dataPoint.getLabel(), drawingCanvas);
    }
    
    
    /**
     * Paint error bars
     * @param dataPoint A data point
     * @param color A color
     * @param drawingCanvas A drawing canvas
     */
    private void paintErrorBars(DataPoint dataPoint, Color color, 
    		DrawingCanvas drawingCanvas) {
        int x = this.transposeX(dataPoint);
        int y = this.transposeY(dataPoint);
        double error = dataPoint.getError();
        if (! Double.isNaN(error))
            this.drawErrorBars(x, y, error, color, drawingCanvas);
    }
    
    
    
    private void paintLines(List dataPointsArrayList, Color color, DrawingCanvas drawingCanvas) {
        for (Iterator it = dataPointsArrayList.iterator(); it.hasNext();) {
            DataPoint[] points = (DataPoint[])it.next();
            this.paintLines(points, color, drawingCanvas);
        }
    }
    
    
    /**
     * Paint a line between given data points
     * @param dataPoints Data points
     * @param color A color
     * @param drawingCanvas A drawing canvas
     */
    private void paintLines(DataPoint[] dataPoints, Color color, DrawingCanvas drawingCanvas) {
        Polyline polyline = new Polyline(this.lineWidth, this.maxNumPointsInLine, color);
        for (int i = 1; i < dataPoints.length; i++) {
            if (i % this.maxNumPointsInLine == 0) {
                drawingCanvas.add(polyline, false);
                polyline = new Polyline(this.lineWidth, this.maxNumPointsInLine, color);
            }
            DataPoint point1 = dataPoints[i - 1];
            DataPoint point2 = dataPoints[i];
            boolean runsOff = false;
            if (! this.plotBoundaries.withinBoundaries(point1) || ! this.plotBoundaries.withinBoundaries(point2)) {
                if (! this.plotBoundaries.withinBoundaries(point2))
                    runsOff = true;
                point1 = new DataPoint(point1);
                point2 = new DataPoint(point2);
                this.plotBoundaries.truncateToFitOnPlot(point1, point2);
            }
            int x1 = this.transposeX(point1);
            int y1 = this.transposeY(point1);
            int x2 = this.transposeX(point2);
            int y2 = this.transposeY(point2);
            polyline.add(x1, y1, x2, y2);
            if (runsOff) {
                if (! polyline.empty()) {
                    drawingCanvas.add(polyline, false);
                    polyline = new Polyline(this.lineWidth, this.maxNumPointsInLine, color);
                }
            }
        }
        if (! polyline.empty())
            drawingCanvas.add(polyline, false);
    }
    
    private void drawPoint(int x, int y, Color color, String label, DrawingCanvas drawingCanvas) {
        Circle circle = new Circle(x, y, this.pointRadius, color);
        circle.setToolTipText(label);
        drawingCanvas.add(circle, false);
    }
    
    
    private void drawErrorBars(int x, int y, double error, Color color, DrawingCanvas drawingCanvas) {
        
        // Compute reference points
        int deltaY = (int)((double)height * this.plotBoundaries.fractionalHeight(error));
        int y1 = y - deltaY / 2;
        int y2 = y1 + deltaY;
        int x1 = x - (this.errorBarHatchLength / 2);
        int x2 = x1 + this.errorBarHatchLength;
        
        // Vertical line
        Line line = new Line(x, y1, x, y2, this.lineWidth, color);
        drawingCanvas.add(line, false);
        
        // Top horizontal line
        line = new Line(x1, y1, x2, y1, this.lineWidth, color);
        drawingCanvas.add(line, false);
        
        // Bottom horizontal line
        line = new Line(x1, y2, x2, y2, this.lineWidth, color);
        drawingCanvas.add(line, false);
    }
    
    
    private int transposeX(DataPoint dataPoint) {
        return (int)((double)width * this.plotBoundaries.fractionalDistanceFromLeft(dataPoint));
    }
    
    
    private int transposeY(DataPoint dataPoint) {
        return height - (int)((double)height * this.plotBoundaries.fractionalDistanceFromBottom(dataPoint));
    }
}
