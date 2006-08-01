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

package org.rti.webcgh.plot;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.drawing.Circle;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicEvent;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Polyline;
import org.rti.webcgh.graph.DataPoint;
import org.rti.webcgh.graph.widget.PlotElement;

/**
 * A two dimensional plotting space that renders
 * array data as points connected by lines.
 * @author dhall
 *
 */
public final class ScatterPlot implements PlotElement {
    
    // =============================
    //     Constants
    // =============================
    
    /** Default radius of data point in pixels. */
    private static final int DEF_POINT_RADIUS = 3;
    
    /** Default width of regression line in pixels. */
    private static final int DEF_LINE_WIDTH = 2;
    
    /** Default length of error bar hatch lines in pixels. */
    private static final int DEF_ERROR_BAR_HATCH_LENGTH = 6;
    
    /**
     * Default maximum number of constituent points in
     * a regression line polyline.
     */
    private static final int DEF_MAX_NUM_POINTS_IN_LINE = 100;
    
    /**
     * Name of attribute that is used by an SVG <pre><g/></pre>
     * element to indicate information about the elements
     * within that group.  This information is used by
     * Javascript code to set the properties of all
     * elements between the <g></g> tags.
     */
    public static final String GRP_ATT_NAME = "egrp";
    
    /**
     * Possible value used with the SVG <pre><g/></pre> attribute given
     * by constant GRP_ATT_NAME to indicate that elements
     * within the group tags correspond to graph points.
     */
    private static final String POINTS_GRP_ATT_VALUE = "p";
    
    /**
     * Possible value used with the SVG <pre><g/></pre> attribute given
     * by constant GRP_ATT_NAME to indicate that elements
     * within the group tags correspond to regression
     * lines.
     */
    private static final String LINES_GRP_ATT_VALUE = "l";
    
    /**
     * Possible value used with the SVG <pre><g/></pre> attribute given
     * by constant GRP_ATT_NAME to indicate that elements
     * within the group tags correspond to error bars.
     */
    private static final String ERROR_BARS_GRP_ATT_VALUE = "e";
    
    // =============================
    //       Attributes
    // =============================
    
    /**
     * Data to be plotted.  Data will not actually be
     * rendered graphically until the paint(DrawingCanvas) method
     * is called.
     */
    private final List<ChromosomeArrayData> data;
    
    /**
     * Names to be associated with data.
     * The order of this list must correspond to the
     * order of the <code>data</code> attribute.
     * The names are not actually graphically rendered, but
     * are used to provide client-side interactivity
     * (e.g., highlighting).
     */
    private final List<String> names;
    
    /**
     * Color of data points and lines associated
     * with a <code>ChromosomeArrayData</code> object.
     * The order of this list must correspond to the
     * order of the list given by the <code>data</code>.
     * attribute.
     */
    private final List<Color> colors;
    
    /** Radius of data point in pixels. */
    private int pointRadius = ScatterPlot.DEF_POINT_RADIUS;
    
    /** Width (i.e., thickness) or regression line in pixels. */
    private int lineWidth = ScatterPlot.DEF_LINE_WIDTH;
    
    /**
     * Length of hatch mark at the top and bottom of error
     * bars in pixels.
     */
    private int errorBarHatchLength = ScatterPlot.DEF_ERROR_BAR_HATCH_LENGTH;
    
    /**
     * Maximum number of constituent points in
     * an individual regression line polyline.
     * The "regression" lines drawn by this
     * plot are rendered by a set of polylines,
     * instead of a single polyline.  This is
     * because of limitations in SVG viewers,
     * which may have maximum numbers of points
     * in polylines. 
     */
    private int maxNumPointsInLine = ScatterPlot.DEF_MAX_NUM_POINTS_IN_LINE;
    
    /** Width of plot in pixels. */
    private final int width;
    
    /** Height of plot in pixels. */
    private final int height;
    
    /** X-coordinate of plot origin (i.e, upper left-most point). */
    private int x = 0;
    
    /** Y-coordinate of plot origin (i.e., upper left-most point). */
    private int y = 0;
    
    /** Boundaries of plot. */
    private final PlotBoundaries plotBoundaries;
    
    /**
     * Data point object that is reused during plot creation
     * in order to economize memory.
     */
    private final DataPoint reusableDataPoint1 = new DataPoint();
    
    /**
     * Data point object that is reused during plot creation
     * in order to economize memory.
     */
    private final DataPoint reusableDataPoint2 = new DataPoint();
    
    // ================================
    //      Getters/setters
    // ================================
    
    /**
     * Get length of hatch mark at the top and bottom of error
     * bars.
     * @return Length of hatch mark at the top and bottom of error
     * bars in pixels.
     */
    public int getErrorBarHatchLength() {
        return errorBarHatchLength;
    }

    
    /**
     * Set length of hatch mark at the top and bottom of error
     * bars.
     * @param errorBarHatchLength Length of hatch mark at the top
     * and bottom of error bars in pixels.
     */
    public void setErrorBarHatchLength(final int errorBarHatchLength) {
        this.errorBarHatchLength = errorBarHatchLength;
    }

    
    /**
     * Get width of regression line that is drawn between points.
     * @return Width of line in pixels.
     */
    public int getLineWidth() {
        return lineWidth;
    }

    
    /**
     * Set width of regression line drawn between points.
     * @param lineWidth Width of line in pixels.
     */
    public void setLineWidth(final int lineWidth) {
        this.lineWidth = lineWidth;
    }

    
    /**
     * Get maximum number of constituent points in
     * an individual regression line polyline.
     * The "regression" lines drawn by this
     * plot are rendered by a set of polylines,
     * instead of a single polyline.  This is
     * because of limitations in SVG viewers,
     * which may have maximum numbers of points
     * in polylines.
     * @return Maximum number of constituent points
     * in an individual regression line polyline.
     */
    public int getMaxNumPointsInLine() {
        return maxNumPointsInLine;
    }

    
    /**
     * Set maximum number of constituent points in
     * an individual regression line polyline.
     * The "regression" lines drawn by this
     * plot are rendered by a set of polylines,
     * instead of a single polyline.  This is
     * because of limitations in SVG viewers,
     * which may have maximum numbers of points
     * in polylines.
     * @param maxNumPointsInLine Maximum number of constituent points
     * in an individual regression line polyline.
     */
    public void setMaxNumPointsInLine(final int maxNumPointsInLine) {
        this.maxNumPointsInLine = maxNumPointsInLine;
    }

    /**
     * Get radius of rendered data points.
     * @return Radius of points in pixels.
     */
    public int getPointRadius() {
        return pointRadius;
    }

    
    /**
     * Set radius of rendered data points.
     * @param pointRadius Radius of points in pixels.
     */
    public void setPointRadius(final int pointRadius) {
        this.pointRadius = pointRadius;
    }
    
    // ==============================
    //       Constructors
    // ==============================
    
    /**
     * Constructor.
     * @param data Data to plot
     * @param names Names to be associated with data.
     * The order of this list must correspond to the
     * order of the list given by the parameter <code>data</code>.
     * The names are not actually graphically rendered, but
     * are used to provide client-side interactivity
     * (e.g., highlighting).
     * @param colors Color of data points and lines of corresponding
     * <code>ChromosomeArrayData</code>.
     * The order of this list must correspond to the
     * order of the list given by the parameter <code>data</code>.
     * @param width Width of plot in pixels
     * @param height Height of plot in pixels
     * @param plotBoundaries Plot boundaries in native data units
     * (i.e., base pairs vs. some quantitation type)
     */
    public ScatterPlot(final List<ChromosomeArrayData> data,
            final List<String> names, final List<Color> colors,
            final int width, final int height,
            final PlotBoundaries plotBoundaries) {
        
        // Check args
        if (data == null || names == null || colors == null) {
            throw new IllegalArgumentException("Lists cannot be null");
        }
        if (data.size() != names.size() || data.size() != colors.size()) {
            throw new IllegalArgumentException(
                    "All lists must have same number of element");
        }
        
        // Initialize attributes
        this.data = data;
        this.names = names;
        this.colors = colors;
        this.width = width;
        this.height = height;
        this.plotBoundaries = plotBoundaries;
    }


    // ===================================
    //      PlotElement interface
    // ===================================
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(this.x, this.y);
    }
    
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public void paint(final DrawingCanvas canvas) {
        
        // Paint points and lines
        Iterator<ChromosomeArrayData> dataIt = this.data.iterator();
        Iterator<String> nameIt = this.names.iterator();
        Iterator<Color> colorIt = this.colors.iterator();
        while (dataIt.hasNext() && nameIt.hasNext() && colorIt.hasNext()) {
            ChromosomeArrayData cad = dataIt.next();
            String name = nameIt.next();
            Color color = colorIt.next();
            DrawingCanvas tile = canvas.newTile();
            tile.setAttribute(GRP_ATT_NAME, name);
            tile.setLineWidth(1);
            canvas.add(tile);
                
            // Points
            DrawingCanvas pointsTile = tile.newTile();
            tile.add(pointsTile);
            pointsTile.setAttribute(GRP_ATT_NAME, POINTS_GRP_ATT_VALUE);
            this.paintPoints(cad, color, pointsTile);
            
            // Error bars
//            DrawingCanvas errorBarsTile = tile.newTile();
//            tile.add(errorBarsTile);
//            errorBarsTile.setAttribute(GRP_ATT_NAME, ERROR_BARS_GRP_ATT_VALUE);
//            this.paintErrorBars(cad, color, errorBarsTile);
//        
//            // Lines
//            DrawingCanvas linesTile = tile.newTile();
//            tile.add(linesTile);
//            linesTile.setAttribute(GRP_ATT_NAME, LINES_GRP_ATT_VALUE);
//            String command = "highlight('" + name + "')";
//            linesTile.addGraphicEventResponse(GraphicEvent.mouseClickEvent,
//                    command);
//            this.paintLines(cad, color, linesTile);
        }
    }
    
    /**
     * Paint all points for given chromosome array data.
     * @param cad Chromosome array data
     * @param color Color of points
     * @param drawingCanvas A drawing canvas
     */
    private void paintPoints(final ChromosomeArrayData cad, final Color color,
            final DrawingCanvas drawingCanvas) {
        for (ArrayDatum datum : cad.getArrayData()) {
            this.paintPoint(datum, color, drawingCanvas);
        }
    }
    
    
    /**
     * Paint all error bars for given chromosome array data.
     * @param cad Chromosome array data
     * @param color Color of error bars
     * @param drawingCanvas A drawing canvas
     */
    private void paintErrorBars(final ChromosomeArrayData cad,
            final Color color, final DrawingCanvas drawingCanvas) {
        for (ArrayDatum datum : cad.getArrayData()) {
            this.paintErrorBar(datum, color, drawingCanvas);
        }
    }
    
    
    
    /**
     * Paint a single data point.
     * @param datum An array datum
     * @param color A color
     * @param drawingCanvas A drawing canvas
     */
    private void paintPoint(final ArrayDatum datum,
            final Color color, final DrawingCanvas drawingCanvas) {
        this.reusableDataPoint1.bulkSet(datum);
        int x = this.transposeX(this.reusableDataPoint1);
        int y = this.transposeY(this.reusableDataPoint1);
        this.drawPoint(x, y, color, datum.getReporter().getName(),
                drawingCanvas);
    }
    
    
    /**
     * Paint error bars for a single data point.
     * @param datum An array datum
     * @param color A color
     * @param drawingCanvas A drawing canvas
     */
    private void paintErrorBar(final ArrayDatum datum,
            final Color color, final DrawingCanvas drawingCanvas) {
        this.reusableDataPoint1.bulkSet(datum);
        int x = this.transposeX(this.reusableDataPoint1);
        int y = this.transposeY(this.reusableDataPoint1);
        if (!Float.isNaN(datum.getError())) {
            this.drawErrorBar(x, y, datum.getError(), color, drawingCanvas);
        }
    }
    
    
    /**
     * Paint all lines for given chromosme array data.
     * This method ultimately uses the SVG <polyline/> element.
     * Due to limitations in SVG viewers regarding the
     * maximum number of individual points in a polyline,
     * the points are broken up into separate polylines
     * that contain no more than some maximum number of
     * ponts.
     * @param cad ChromosomeArrayData
     * @param color A color
     * @param drawingCanvas A drawing canvas
     */
    private void paintLines(final ChromosomeArrayData cad,
            final Color color, final DrawingCanvas drawingCanvas) {
        Polyline polyline = new Polyline(this.lineWidth,
                this.maxNumPointsInLine, color);
        for (int i = 1; i < cad.getArrayData().size(); i++) {
            if (i % this.maxNumPointsInLine == 0) {
                drawingCanvas.add(polyline, false);
                polyline = new Polyline(this.lineWidth,
                        this.maxNumPointsInLine, color);
            }
            ArrayDatum d1 = cad.getArrayData().get(i - 1);
            ArrayDatum d2 = cad.getArrayData().get(i);
            boolean runsOff = false;
            this.reusableDataPoint1.bulkSet(d1);
            this.reusableDataPoint2.bulkSet(d2);
            if (!this.plotBoundaries.withinBoundaries(this.reusableDataPoint1)
                    || !this.plotBoundaries.withinBoundaries(
                            this.reusableDataPoint2)) {
                if (!this.plotBoundaries.withinBoundaries(
                        this.reusableDataPoint2)) {
                    runsOff = true;
                }
                this.plotBoundaries.truncateToFitOnPlot(this.reusableDataPoint1,
                        this.reusableDataPoint2);
            }
            int x1 = this.transposeX(this.reusableDataPoint1);
            int y1 = this.transposeY(this.reusableDataPoint1);
            int x2 = this.transposeX(this.reusableDataPoint2);
            int y2 = this.transposeY(this.reusableDataPoint2);
            polyline.add(x1, y1, x2, y2);
            if (runsOff) {
                if (!polyline.empty()) {
                    drawingCanvas.add(polyline, false);
                    polyline = new Polyline(this.lineWidth,
                            this.maxNumPointsInLine, color);
                }
            }
        }
        if (!polyline.empty()) {
            drawingCanvas.add(polyline, false);
        }
    }
    
    
    /**
     * Draw single data point.
     * @param x X-coordinate of point center in pixels
     * @param y Y-coordinate of point in pixels
     * @param color Color of point
     * @param label Mouseover label for point
     * @param drawingCanvas A drawing canvas
     */
    private void drawPoint(final int x, final int y, final Color color,
            final String label, final DrawingCanvas drawingCanvas) {
        Circle circle = new Circle(x, y, this.pointRadius, color);
        //circle.setToolTipText(label);
        drawingCanvas.add(circle, false);
    }
    
    
    /**
     * Draw single error bar.
     * @param x X-axis position of bar in pixels
     * @param y Y-axis position of center of bar in pixels
     * @param error Error factor which determines the height of bar
     * @param color Color of bar
     * @param drawingCanvas A drawing canvas
     */
    private void drawErrorBar(final int x, final int y, final double error,
            final Color color, final DrawingCanvas drawingCanvas) {
        
        // Compute reference points
        int deltaY = (int) ((double) height
                * this.plotBoundaries.fractionalHeight(error));
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
    
    
    /**
     * Transpose the x-coordinate of given data point from
     * the native units of the plot (i.e., base pairs vs. some
     * quantitation type) to pixels.
     * @param dataPoint A data point
     * @return Transposed x-coordinate in pixels
     */
    private int transposeX(final DataPoint dataPoint) {
        return (int) ((double) width
                * this.plotBoundaries.fractionalDistanceFromLeft(dataPoint));
    }
    
    
    /**
     * Transpose the y-coordinate of given data point from
     * the native units of the plot (i.e., base pairs vs. some
     * quantitation type) to pixels.
     * @param dataPoint A data point
     * @return Transposed y-coordinate in pixels
     */
    private int transposeY(final DataPoint dataPoint) {
        return height - (int) ((double) height
                * this.plotBoundaries.fractionalDistanceFromBottom(dataPoint));
    }
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return new Point(this.x, this.y);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return new Point(this.x, this.y + this.height);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.x + this.width, this.y);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.x + this.width, this.y + this.height);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public int height() {
        return this.height;
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(final int deltaX, final int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
}
