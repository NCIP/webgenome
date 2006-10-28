/*
$Revision: 1.21 $
$Date: 2006-10-28 21:02:12 $

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

package org.rti.webcgh.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

//import org.apache.log4j.Logger;
import org.rti.webcgh.domain.AnnotatedGenomeFeature;
import org.rti.webcgh.domain.AnnotatedGenomeFeatureIterator;
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.graphics.DataPoint;
import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.PlotBoundaries;
import org.rti.webcgh.graphics.primitive.Circle;
import org.rti.webcgh.graphics.primitive.Line;
import org.rti.webcgh.graphics.primitive.Polyline;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.webui.util.ClickBoxes;
import org.rti.webcgh.webui.util.MouseOverStripe;
import org.rti.webcgh.webui.util.MouseOverStripes;

/**
 * A two dimensional plotting space that renders
 * array data as points connected by lines.
 * @author dhall
 *
 */
public final class ScatterPlot implements PlotElement {
	
	//private static final Logger LOGGER = Logger.getLogger(ScatterPlot.class);
    
    // =============================
    //     Constants
    // =============================
    
    /** Default radius of data point in pixels. */
    private static final int DEF_POINT_RADIUS = 3;
    
    /** Radius of selected data points in pixels. */
    private static final int SELECTED_POINT_RADIUS = 5;
    
    /** Default width of regression line in pixels. */
    private static final int DEF_LINE_WIDTH = 1;
    
    /** Width of selected lines in pixels. */
    private static final int SELECTED_LINE_WIDTH = 3;
    
    /** LOH line width. */
    private static final int LOH_LINE_WIDTH = 5;
    
    /** Default length of error bar hatch lines in pixels. */
    private static final int DEF_ERROR_BAR_HATCH_LENGTH = 6;
    
    /**
     * Default maximum number of constituent points in
     * a regression line polyline.
     */
    private static final int DEF_MAX_NUM_POINTS_IN_LINE = 100;
    
    /** Minimum width of a region of LOH in pixels. */
    private static final int MIN_LOH_WIDTH = 10;
    
    
    // =============================
    //       Attributes
    // =============================
    
    /** Experiments to plot. */
    private final Collection<Experiment> experiments;
    
    /** Chromosome number. */
    private final short chromosome;
    
    /**
     * Gets chromosome array data making the location of those
     * data transparent.  Data may be in memory or on disk.
     */
    private final ChromosomeArrayDataGetter chromosomeArrayDataGetter;
    
    
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
    
    /** Click boxes used for providing interactivity using Javascript. */
    private final ClickBoxes clickBoxes;
    
    /**
     * Mouseover stripes used for providing interactivity
     * using Javascript.
     */
    private final MouseOverStripes mouseOverStripes;
    
    /** Draw data points. */
    private boolean drawPoints = true;
    
    /** Draw regression lines. */
    private boolean drawLines = true;
    
    /** Draw error bars. */
    private boolean drawErrorBars = false;
    
    /**
     * Threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
     */
    private float lohThreshold = (float) 0.5;
    
    /**
     * Interpolate the endpoints of LOH regions.  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
     */
    private boolean interpolateLohEndpoints = false;
    
    /** Draw raw LOH probabilities along with scored data? */
    private boolean drawRawLohProbabilities = true;
    
    /** Quantitation type. */
    private QuantitationType quantitationType = null;
    
    // =============================
    //     Getters/setters
    // =============================
    
	/**
	 * Draw raw LOH probabilities?
	 * @return T/F
	 */
	public boolean isDrawRawLohProbabilities() {
		return drawRawLohProbabilities;
	}


	/**
	 * Set whether to draw raw LOH probabilities.
	 * @param drawRawLohProbabilities Draw raw LOH probabilities?
	 */
	public void setDrawRawLohProbabilities(
			final boolean drawRawLohProbabilities) {
		this.drawRawLohProbabilities = drawRawLohProbabilities;
	}


	/**
	 * Interpolate the endpoints of LOH regions?  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @return T/F
	 */
	public boolean isInterpolateLohEndpoints() {
		return interpolateLohEndpoints;
	}


	/**
	 * Set whether to interpolate the endpoints of LOH regions.  If false,
     * the endpoints will be set to the outermost
     * reporter positions in an LOH region.  If true,
     * the endpoints will be extended distally midway to the
     * next reporters.
	 * @param interpolateLohEndpoints Interpolate LOH endpoints?
	 */
	public void setInterpolateLohEndpoints(
			final boolean interpolateLohEndpoints) {
		this.interpolateLohEndpoints = interpolateLohEndpoints;
	}


	/**
	 * Get threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @return LOH threshold probability.
	 */
	public float getLohThreshold() {
		return lohThreshold;
	}


	/**
	 * Set threshold probability above which the corresponding
	 * value is considered to be indicative of LOH.
	 * @param lohThreshold LOH threshold probability.
	 */
	public void setLohThreshold(final float lohThreshold) {
		this.lohThreshold = lohThreshold;
	}
    
    /**
     * Get mouseover stripes.
     * @return Mouseover stripes.
     */
    public MouseOverStripes getMouseOverStripes() {
		return mouseOverStripes;
	}

	/**
     * Get click boxes.
     * @return Click boxes
     */
    public ClickBoxes getClickBoxes() {
    	return this.clickBoxes;
    }
    
    /**
     * Will error bars be drawn?
     * @return T/F
     */
    public boolean isDrawErrorBars() {
		return drawErrorBars;
	}

    /**
     * Set whether error bars will be drawn.
     * @param drawErrorBars Will error bars be drawn?
     */
	public void setDrawErrorBars(final boolean drawErrorBars) {
		this.drawErrorBars = drawErrorBars;
	}

	/**
	 * Will regression lines be drawn?
	 * @return T/F
	 */
	public boolean isDrawLines() {
		return drawLines;
	}

	
	/**
	 * Set whether regression lines will be drawn.
	 * @param drawLines Draw regression lines?
	 */
	public void setDrawLines(final boolean drawLines) {
		this.drawLines = drawLines;
	}

	/**
	 * Will data points be drawn?
	 * @return T/F
	 */
	public boolean isDrawPoints() {
		return drawPoints;
	}

	/**
	 * Set whether data points will be drawn.
	 * @param drawPoints Will data points be drawn?
	 */
	public void setDrawPoints(final boolean drawPoints) {
		this.drawPoints = drawPoints;
	}
    
    
    // ==============================
    //       Constructors
    // ==============================
    

	/**
     * Constructor.
     * @param experiments Experiments to plot
     * @param chromosome Chromosome number
     * @param chromosomeArrayDataGetter Getter for
     * chromosome array data
     * @param width Width of plot in pixels
     * @param height Height of plot in pixels
     * @param plotBoundaries Plot boundaries in native data units
     * (i.e., base pairs vs. some quantitation type)
     */
    public ScatterPlot(final Collection<Experiment> experiments,
    		final short chromosome,
    		final ChromosomeArrayDataGetter chromosomeArrayDataGetter,
            final int width, final int height,
            final PlotBoundaries plotBoundaries) {
    	
    	// Make sure args okay
    	if (experiments == null) {
    		throw new IllegalArgumentException("Experiments cannot be null");
    	}
    	if (chromosomeArrayDataGetter == null) {
    		throw new IllegalArgumentException(
    				"Chromosome array data getter cannot be null");
    	}
    	this.quantitationType = null;
    	for (Experiment exp : experiments) {
    		QuantitationType qt = exp.getQuantitationType();
    		if (this.quantitationType == null) {
    			this.quantitationType = qt;
    		} else if (this.quantitationType != qt) {
    			throw new IllegalArgumentException(
    					"Cannot mix quantitation types in plot");
    		}
    	}
        
        // Initialize attributes
        this.experiments = experiments;
        this.chromosome = chromosome;
        this.chromosomeArrayDataGetter = chromosomeArrayDataGetter;
        this.width = width;
        this.height = height;
        this.plotBoundaries = plotBoundaries;
        this.clickBoxes = new ClickBoxes(width, height, DEF_POINT_RADIUS * 3,
        		DEF_POINT_RADIUS * 3);
        this.mouseOverStripes = new MouseOverStripes(
        		Orientation.HORIZONTAL, width, height);
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
    	SortedSet<Reporter> reporters = new TreeSet<Reporter>();
    	
        // Paint points and lines
    	BioAssay selected = null;
        for (Experiment exp : this.experiments) {
            for (BioAssay bioAssay : exp.getBioAssays()) {
            	if (bioAssay.isSelected()) {
            		selected = bioAssay;
            	} else {
            		this.paint(canvas, bioAssay, reporters);
            	}
            }
        }
        if (selected != null) {
        	this.paint(canvas, selected, reporters);
        }
        
        // Initialize mouseover stripes
        this.initializeMouseOverStripes(reporters);
    }
    
    /**
     * Paint given bioassay.
     * @param canvas Canvas
     * @param bioAssay Bioassay to paint
     * @param reporters All reporters in all experiments that
     * are being painted
     */
    private void paint(final DrawingCanvas canvas, final BioAssay bioAssay,
    		final SortedSet<Reporter> reporters) {
    	
    	int pointRadius = DEF_POINT_RADIUS;
    	int lineWidth = DEF_LINE_WIDTH;
    	if (bioAssay.isSelected()) {
    		pointRadius = SELECTED_POINT_RADIUS;
    		lineWidth = SELECTED_LINE_WIDTH;
    	}
    	ChromosomeArrayData cad = this.chromosomeArrayDataGetter.
    		getChromosomeArrayData(bioAssay, this.chromosome);
    	if (cad != null) {
    		
	    		if (this.quantitationType != QuantitationType.LOH
	    				|| (this.quantitationType == QuantitationType.LOH
	    						&& this.drawRawLohProbabilities)) {
	                
	            // Points
	    		if (this.drawPoints) {
		            this.paintPoints(cad, bioAssay.getColor(), canvas,
		            		pointRadius,
		            		bioAssay.getId(), reporters);
	    		}
	            
	            // Error bars
	    		if (this.drawErrorBars) {
	    			this.paintErrorBars(cad, bioAssay.getColor(), canvas,
	    					lineWidth);
	    		}
	        
	            // Lines
	    		if (this.drawLines) {
		            this.paintLines(cad, bioAssay.getColor(), canvas,
		            		lineWidth);
	    		}
    		}
    		
    		// LOH scored lines
    		if (this.quantitationType == QuantitationType.LOH) {
    			this.paintLoh(cad, bioAssay.getColor(), canvas, LOH_LINE_WIDTH);
    		}
        }
    }
    
    
    /**
     * Initialize mouseover stripes.
     * @param reporters Reporters
     */
    private void initializeMouseOverStripes(
    		final SortedSet<Reporter> reporters) {
    	this.mouseOverStripes.getOrigin().x = this.x;
    	this.mouseOverStripes.getOrigin().y = this.y;
    	if (reporters.size() > 0) {
	    	MouseOverStripe lastStripe = null;
	    	Reporter lastReporter = null;
	    	for (Reporter currentReporter : reporters) {
	    		long currentStartBp = 0;
	    		if (lastReporter != null) {
	    			currentStartBp = (currentReporter.getLocation()
	    					+ lastReporter.getLocation()) / 2;
	    		}
	    		int currentStartPix = (int)
	    			(this.plotBoundaries.fractionalDistanceFromLeft(
	    					currentStartBp) * (double) this.width);
	    		MouseOverStripe currentStripe = new MouseOverStripe();
	    		this.mouseOverStripes.add(currentStripe);
	    		currentStripe.setText(currentReporter.getName());
	    		currentStripe.setStart(currentStartPix);
	    		if (lastStripe != null) {
	    			lastStripe.setEnd(currentStartPix - 1);
	    		}
	    		lastStripe = currentStripe;
	    		lastReporter = currentReporter;
	    	}
	    	lastStripe.setEnd(this.width);
    	}
    }
    
    /**
     * Paint all points for given chromosome array data.
     * @param cad Chromosome array data
     * @param color Color of points
     * @param drawingCanvas A drawing canvas
     * @param pointRadius Radius of data point in pixels
     * @param bioAssayId ID of bioassay datum comes from
     * @param reporters Sorted set of reporters
     */
    private void paintPoints(final ChromosomeArrayData cad, final Color color,
            final DrawingCanvas drawingCanvas, final int pointRadius,
            final Long bioAssayId,
            final SortedSet<Reporter> reporters) {
    	List<ArrayDatum> arrayData = cad.getArrayData();
    	if (arrayData != null) {
	        for (ArrayDatum datum : cad.getArrayData()) {
	            this.paintPoint(datum, color, drawingCanvas, pointRadius,
	            		bioAssayId);
	            reporters.add(datum.getReporter());
	        }
    	}
    }
    
    
    /**
     * Paint all error bars for given chromosome array data.
     * @param cad Chromosome array data
     * @param color Color of error bars
     * @param drawingCanvas A drawing canvas
     * @param lineWidth Width of lines
     */
    private void paintErrorBars(final ChromosomeArrayData cad,
            final Color color, final DrawingCanvas drawingCanvas,
            final int lineWidth) {
        for (ArrayDatum datum : cad.getArrayData()) {
            this.paintErrorBar(datum, color, drawingCanvas, lineWidth);
        }
    }
    
    
    
    /**
     * Paint a single data point.
     * @param datum An array datum
     * @param color A color
     * @param drawingCanvas A drawing canvas
     * @param pointRadius Radius of data point
     * @param bioAssayId ID of bioassay datum comes from
     */
    private void paintPoint(final ArrayDatum datum,
            final Color color, final DrawingCanvas drawingCanvas,
            final int pointRadius, final Long bioAssayId) {
        this.reusableDataPoint1.bulkSet(datum);
	    if (this.plotBoundaries.withinBoundaries(this.reusableDataPoint1)) {
	        int x = this.transposeX(this.reusableDataPoint1);
	        int y = this.transposeY(this.reusableDataPoint1);
	        
	        // Create point
	        this.drawPoint(x, y, color, datum.getReporter().getName(),
	                drawingCanvas, pointRadius);
	        
	        // Add click box command
	        x -= this.x;
	        y -= this.y;
	        String command = this.clickBoxes.getClickBoxText(x, y);
	        if (command == null) {
	        	this.clickBoxes.addClickBoxText(bioAssayId.toString(), x, y);
	        }
        }
    }
    
    
    /**
     * Paint error bars for a single data point.
     * @param datum An array datum
     * @param color A color
     * @param drawingCanvas A drawing canvas
     * @param lineWidth Width of line
     */
    private void paintErrorBar(final ArrayDatum datum,
            final Color color, final DrawingCanvas drawingCanvas,
            final int lineWidth) {
        this.reusableDataPoint1.bulkSet(datum);
        int x = this.transposeX(this.reusableDataPoint1);
        int y = this.transposeY(this.reusableDataPoint1);
        if (!Float.isNaN(datum.getError())) {
            this.drawErrorBar(x, y, datum.getError(), color, drawingCanvas,
            		lineWidth);
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
     * @param lineWidth Width of line in pixels
     */
    private void paintLines(final ChromosomeArrayData cad,
            final Color color, final DrawingCanvas drawingCanvas,
            final int lineWidth) {
        Polyline polyline = new Polyline(lineWidth,
                DEF_MAX_NUM_POINTS_IN_LINE, color);
        for (int i = 1; i < cad.getArrayData().size(); i++) {
            if (i % DEF_MAX_NUM_POINTS_IN_LINE == 0) {
                drawingCanvas.add(polyline, false);
                polyline = new Polyline(lineWidth,
                        DEF_MAX_NUM_POINTS_IN_LINE, color);
            }
            ArrayDatum d1 = cad.getArrayData().get(i - 1);
            ArrayDatum d2 = cad.getArrayData().get(i);
            boolean runsOff = false;
            this.reusableDataPoint1.bulkSet(d1);
            this.reusableDataPoint2.bulkSet(d2);
            if (this.plotBoundaries.atLeastPartlyOnPlot(
            		this.reusableDataPoint1, this.reusableDataPoint2)) {
	            if (!this.plotBoundaries.withinBoundaries(
	            		this.reusableDataPoint1)
	                    || !this.plotBoundaries.withinBoundaries(
	                            this.reusableDataPoint2)) {
	                if (!this.plotBoundaries.withinBoundaries(
	                        this.reusableDataPoint2)) {
	                    runsOff = true;
	                }
	                this.plotBoundaries.truncateToFitOnPlot(
	                        this.reusableDataPoint1,
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
	                    polyline = new Polyline(lineWidth,
	                            DEF_MAX_NUM_POINTS_IN_LINE, color);
	                }
	            }
            }
        }
        if (!polyline.empty()) {
            drawingCanvas.add(polyline, false);
        }
    }
    
    
    /**
     * Paint LOH scored lines.
     * @param cad Chromosome array data
     * @param color Color
     * @param drawingCanvas Canvas to paint on
     * @param lineWidth Width of line
     */
    private void paintLoh(final ChromosomeArrayData cad,
            final Color color, final DrawingCanvas drawingCanvas,
            final int lineWidth) {
    	
    	// Iterate over LOH segments
    	AnnotatedGenomeFeatureIterator it = cad.lohSegmentIterator(
    			this.lohThreshold, this.interpolateLohEndpoints);
    	while (it.hasNext()) {
    		AnnotatedGenomeFeature feat = it.next();
    				    				
			// Draw LOH scored segment
			int startX = this.transposeX(feat.getStartLocation());
			int endX = this.transposeX(feat.getEndLocation());
			if (startX == endX) {
				startX -= MIN_LOH_WIDTH / 2;
				if (startX < this.x) {
					startX = this.x;
				}
				endX += MIN_LOH_WIDTH / 2;
				if (endX > this.x + this.width) {
					endX = this.x + this.width;
				}
			}
			int topY = this.transposeY(feat.getQuantitation());
			drawingCanvas.add(new Line(startX, topY, endX, topY,
					lineWidth, color));
		}
    }
    
    
    /**
     * Draw single data point.
     * @param x X-coordinate of point center in pixels
     * @param y Y-coordinate of point in pixels
     * @param color Color of point
     * @param label Mouseover label for point
     * @param drawingCanvas A drawing canvas
     * @param pointRadius Radius of data point
     */
    private void drawPoint(final int x, final int y, final Color color,
            final String label, final DrawingCanvas drawingCanvas,
            final int pointRadius) {
        Circle circle = new Circle(x, y, pointRadius, color);
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
     * @param lineWidth Width of line in pixels
     */
    private void drawErrorBar(final int x, final int y, final double error,
            final Color color, final DrawingCanvas drawingCanvas,
            final int lineWidth) {
        
        // Compute reference points
        int deltaY = (int) ((double) height
                * this.plotBoundaries.fractionalHeight(error));
        int y1 = y - deltaY / 2;
        int y2 = y1 + deltaY;
        int x1 = x - (DEF_ERROR_BAR_HATCH_LENGTH / 2);
        int x2 = x1 + DEF_ERROR_BAR_HATCH_LENGTH;
        
        // Vertical line
        Line line = new Line(x, y1, x, y2, lineWidth, color);
        drawingCanvas.add(line, false);
        
        // Top horizontal line
        line = new Line(x1, y1, x2, y1, lineWidth, color);
        drawingCanvas.add(line, false);
        
        // Bottom horizontal line
        line = new Line(x1, y2, x2, y2, lineWidth, color);
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
        return this.x + (int) ((double) width
                * this.plotBoundaries.fractionalDistanceFromLeft(dataPoint));
    }
    
    
    /**
     * Transpose the given x-coordinate from
     * the native units of the plot (i.e., base pairs vs. some
     * quantitation type) to pixels.
     * @param x An x-coordinate in native units
     * @return Transposed x-coordinate in pixels
     */
    private int transposeX(final long x) {
        return this.x + (int) ((double) width
                * this.plotBoundaries.fractionalDistanceFromLeft(x));
    }
    
    
    /**
     * Transpose the y-coordinate of given data point from
     * the native units of the plot (i.e., base pairs vs. some
     * quantitation type) to pixels.
     * @param dataPoint A data point
     * @return Transposed y-coordinate in pixels
     */
    private int transposeY(final DataPoint dataPoint) {
        return this.y + height - (int) ((double) height
                * this.plotBoundaries.fractionalDistanceFromBottom(dataPoint));
    }
    
    
    /**
     * Transpose the given y-coordinate from
     * the native units of the plot (i.e., base pairs vs. some
     * quantitation type) to pixels.
     * @param y A y-coordinate in native units
     * @return Transposed y-coordinate in pixels
     */
    private int transposeY(final float y) {
        return this.y + height - (int) ((double) height
                * this.plotBoundaries.fractionalDistanceFromBottom(y));
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
        this.clickBoxes.getOrigin().x += deltaX;
        this.clickBoxes.getOrigin().y += deltaY;
        this.mouseOverStripes.getOrigin().x += deltaX;
        this.mouseOverStripes.getOrigin().y += deltaY;
    }
}
