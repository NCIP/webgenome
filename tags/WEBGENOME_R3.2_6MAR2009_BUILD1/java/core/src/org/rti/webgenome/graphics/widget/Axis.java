/*
$Revision: 1.5 $
$Date: 2007-12-04 20:10:30 $

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


package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.primitive.Cursor;
import org.rti.webgenome.graphics.primitive.Hyperlink;
import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.graphics.util.NumberFormatter;
import org.rti.webgenome.graphics.util.RealNumberFormatter;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Location;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.util.SystemUtils;


/**
 * A plot axis.
 */
public final class Axis implements ScalePlotElement {
	
	// ==============================
	//     Constants
	// ==============================
	
    /**
     * Multiplication factors used in calculating how many hatch marks
     * will fit on axis.
     */
    private static final float[] MULTIPLIERS =
    	{(float) 5.0, (float) 2.0, (float) 1.0};
    
    /** Thickness of axis tic marks in pixels. */
    private static final int TIC_MARK_THICKNESS = 3;
    

    // =============================
    //       Attributes
    // =============================
    
    
    /** Minimum value on axis in the native units. */
    private final double minValue;
    
    /** Maximum value on axis in the native units. */
    private final double maxValue;
    
    /**
     * Length of axis line in pixels. If orientation
     * is horizontal, this is width. If vertical,
     * this is height.
     */
    private final int length;
    
    /** Color of axis. */
    private final Color color = Color.black;
    
    /** Orientation of axis. */
    private final Orientation orientation;
    
    /** Position of text relative to hatch marks. */
    private final Location positionTextRelativeToHatches;
    
    /**
     * Number of minor tic marks between major tic marks.
     * Major tic marks have text labels; minor tic marks
     * do not.
     */
    private int numMinorTicsBetweenMajorTics = 5;
    
    /**
     * Length of major tic marks in pixels. If orientation
     * is horizontal, this is height.  If vertical, this
     * is width
     */
    private int majorTicLength = 12;
    
    /**
     * Length of minor tic marks in pixels.  If orientation
     * is horizontal, this is height.  If vertical, this
     * is width
     */
    private int minorTicLength = 8;
    
    /** Font size of major tic mark text labels. */
    private int fontSize = 12;
    
    /** Thickness (i.e, stroke) of main axis line in pixels. */
    private int mainAxisLineThickness = 2;
    
    /** Thickness (i.e, stroke) of major hatch lines in pixels. */
    private int majorHatchLineThickness = 2;
    
    /** Thickness (i.e, stroke) of minor hatch lines in pixels. */
    private int minorHatchLineThickness = 1;
    
    /** Padding between all graphical elements in pixels. */
    private int padding = 5;
    
    /** Number formatter. */
    private NumberFormatter numberFormatter = new RealNumberFormatter(12, 4);
    
    /** Minimum x-axis coordinate. */
    private int minX = 0;
    
    /** Maximum x-axis coordinate. */
    private int maxX = 0;
    
    /** Minimum y-axis coordinate. */
    private int minY = 0;
    
    /** Maximum y-axis coordinate. */
    private int maxY = 0;
    
    /** X-axis coordinate of point representing '0' in the native units. */
    private int zeroX = 0;
    
    /** Y-axis coordinate of point representing '0' in the native units. */
    private int zeroY = 0;
    
    /** Minimum X-coordinate on main axis line. */
    private int lineMinX = 0;
    
    /** Minimum Y-coordinate on main axis line. */
    private int lineMinY = 0;
    
    /** Minimum X-coordinate on main axis line. */
    private int lineMaxX = 0;
    
    /** Maximum Y-coordinate on main axis line. */
    private int lineMaxY = 0;
      
    /** Indicator that axis spans the value '0' in the native units. */
    private final boolean spansZero;
    
    /** Range between minimum and maximum values in native units. */
    private final double range;
    
    /** Location of user-specified hatch points. */
    private List<Double> hatchPoints = null;
    
    /**
     * User specified hatch lables.  Size and order must.
     * correspond to {@code hatchPoints}.
     */
    private List<String> hatchLabels = null;
    
    
    // ============================
    //     Getters/setters
    // ============================

	/**
     * Set number formatter used to render major hatch labels.
     * @param numberFormatter A number formatter
     */
    public void setNumberFormatter(final NumberFormatter numberFormatter) {
        this.numberFormatter = numberFormatter;
    }
    
    
    /**
     * Set font size of major hatch labels.
     * @param fontSize Font size
     */
    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }
    
    
    /**
     * Set length of major hatch marks.
     * @param majorTicLength Length of major hatch marks in pixels.
     */
    public void setMajorTicLength(final int majorTicLength) {
        this.majorTicLength = majorTicLength;
    }
    
    
    /**
     * Set length of minor hatch marks.
     * @param minorTicLength Length of minor hatch marks in pixels.
     */
    public void setMinorTicLength(final int minorTicLength) {
        this.minorTicLength = minorTicLength;
    }
    
    
    /**
     * Set the number of minor hatch marks between pairs of adjacent
     * major hatch marks.
     * @param numMinorTicsBetweenMajorTics Number of minor hatch
     * marks between pairs of adjacent major hatch marks
     */
    public void setNumMinorTicsBetweenMajorTics(
            final int numMinorTicsBetweenMajorTics) {
        this.numMinorTicsBetweenMajorTics = numMinorTicsBetweenMajorTics;
    }
    
    
    /**
     * Set thickness of main axis line.
     * @param mainAxisLineThickness Thickness of main axis line
     * in pixels
     */    
    public void setMainAxisLineThickness(final int mainAxisLineThickness) {
        this.mainAxisLineThickness = mainAxisLineThickness;
    }


    /**
     * Set thickness of major hatch lines.
     * @param majorHatchLineThickness Thickness of major hatch lines
     * in pixels.
     */
    public void setMajorHatchLineThickness(final int majorHatchLineThickness) {
        this.majorHatchLineThickness = majorHatchLineThickness;
    }


    /**
     * Set thickness of minor hatch lines.
     * @param minorHatchLineThickness Thickness of minor hatch lines
     * in pixels.
     */
    public void setMinorHatchLineThickness(final int minorHatchLineThickness) {
        this.minorHatchLineThickness = minorHatchLineThickness;
    }

	/**
     * Set padding between graphic elements.
     * @param padding Padding in pixels.
     */
    public void setPadding(final int padding) {
        this.padding = padding;
    }
    
    
    // ======================================
    //     Constructors
    // ======================================
    
    
    /**
     * Constructor.
     * @param minValue Minimum value in native units
     * @param maxValue Maximum value in native units
     * @param length Length in pixels
     * @param orientation Orientation
     * @param positionTextRelativeToHatches Position of hatch mark labels
     * relative to hatch marks
     * @param canvas Canvas the axis will be rendered to
     */
    public Axis(final double minValue, final double maxValue,
            final int length, final Orientation orientation,
            final Location positionTextRelativeToHatches,
            final DrawingCanvas canvas) {
    	
    	// Set attributes not dependent on orientation and position
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.length = length;
        this.range = maxValue - minValue;
        this.orientation = orientation;
        this.positionTextRelativeToHatches = positionTextRelativeToHatches;
        this.spansZero = maxValue >= 0 && minValue <= 0;
        this.minX = 0;
        this.minY = 0;
        
        // Calculate common reference coordinates
        int maxNumMajorTics = this.maxNumTicsThatFitOnOneLine(
                new RenderedWidthCalculator(canvas), 
	    		this.length, this.minValue, this.maxValue, this.fontSize);
        double majorTicInterval = this.computeTicInterval(
                this.minValue, this.maxValue, maxNumMajorTics);
        double startingMajorTic = this.computeStartTic(this.minValue,
                majorTicInterval);
        double endingMajorTic = this.computeEndTic(startingMajorTic,
        		majorTicInterval);
        
        // Set attributes distinct for horizontal orientation
        if (orientation == Orientation.HORIZONTAL) {
        	
        	// Calculate reference coordinates
        	String ticStr = this.numberFormatter.format(startingMajorTic);
        	int textWidth = canvas.renderedWidth(ticStr, this.fontSize);
        	int relativeTextMinX = this.nativeUnitsToPixel(startingMajorTic)
        		- textWidth / 2;
        	ticStr = this.numberFormatter.format(endingMajorTic);
        	textWidth = canvas.renderedWidth(ticStr, this.fontSize);
        	int relativeTextMaxX = this.nativeUnitsToPixel(endingMajorTic)
        		+ textWidth / 2;
        	
        	// Attributes that are same regardless of text location
        	this.lineMinX = 0;
        	this.lineMaxX = length;
        	this.maxX = length;
        	this.maxY = this.fontSize + this.padding + this.majorTicLength;
        	if (this.spansZero) {
        		this.zeroX = this.nativeUnitsToPixel(0.0);
        	}
        	if (relativeTextMinX < 0) {
        		this.lineMinX -= relativeTextMinX;
        		this.lineMaxX -= relativeTextMinX;
        		this.maxX -= relativeTextMinX;
        		if (this.spansZero) {
        			this.zeroX -= relativeTextMinX;
        		}
        		relativeTextMaxX -= relativeTextMinX;
        	}
        	if (relativeTextMaxX > this.maxX) {
        		this.maxX = relativeTextMaxX;
        	}
        	this.maxY = this.fontSize + this.padding + this.majorTicLength;
        	
        	// Text above
        	if (positionTextRelativeToHatches == Location.ABOVE) {
        		this.zeroY = this.maxY - this.majorTicLength / 2;
        		
        	// Text below
        	} else if (positionTextRelativeToHatches == Location.BELOW) {
        		this.zeroY = this.majorTicLength / 2;
        		
        	// Invalid text position
        	} else {
        		throw new IllegalArgumentException("Illegal combination of "
        				+ "orientation and positionTextRelativeToHatches");
        	}
        	
        	this.lineMinY = this.zeroY;
    		this.lineMaxY = this.zeroY;
        	
        // Set attributes distinct for vertical orientation
        } else if (orientation == Orientation.VERTICAL) {
        	
        	// Calculate reference coordinates
        	int relativeTextMinY = length
        		- this.nativeUnitsToPixel(endingMajorTic)
        		- this.fontSize / 2;
        	int relativeTextMaxY = length
        		- this.nativeUnitsToPixel(startingMajorTic)
        		+ this.fontSize / 2;
        	int maxTextWidth = this.maxTextWidth(startingMajorTic,
        			majorTicInterval, canvas);
        	
        	// Set attributes common to all text placements
        	this.maxX = maxTextWidth + this.padding + this.majorTicLength;
        	this.maxY = length;
        	this.lineMinY = 0;
        	this.lineMaxY = length;
        	if (this.spansZero) {
        		this.zeroY = this.length - this.nativeUnitsToPixel(0.0);
        	}
        	if (relativeTextMinY < 0) {
        		this.maxY -= relativeTextMinY;
        		this.lineMinY -= relativeTextMinY;
        		this.lineMaxY -= relativeTextMinY;
	        	if (this.spansZero) {
	        		this.zeroY -= relativeTextMinY;
	        	}
        	}
        	if (relativeTextMaxY > length) {
        		this.maxY += relativeTextMaxY - length;
        	}
        	
        	// Text to left
        	if (positionTextRelativeToHatches == Location.LEFT_OF) {
        		this.zeroX = maxTextWidth + this.padding
        			+ this.majorTicLength / 2;
        		
        	// Text to right
        	} else if (positionTextRelativeToHatches == Location.RIGHT_OF) {
        		this.zeroX = this.majorTicLength / 2;
        		
        	// Invalid text position
        	} else {
        		throw new IllegalArgumentException("Illegal combination of "
        				+ "orientation and positionTextRelativeToHatches");
        	}
        	
        	this.lineMinX = this.zeroX;
        	this.lineMaxX = this.zeroX;
        	
        // Invalid orientation
        } else {
        	throw new IllegalArgumentException("Invalid orientation");
        }
    }
    
    
    /**
     * Constructor.
     * @param length Length in pixels
     * @param orientation Orientation
     * @param positionTextRelativeToHatches Position of hatch mark labels
     * relative to hatch marks
     * @param canvas Canvas the axis will be rendered to
     * @param hatchPoints Relative location of hatch marks
     * @param hatchLabels Label for corresponding hatch point
     * (i.e. the size and order of the two lists must be the same)
     */
    public Axis(final int length, final Orientation orientation,
            final Location positionTextRelativeToHatches,
            final DrawingCanvas canvas, final List<Double> hatchPoints,
            final List<String> hatchLabels) {
    	
    	if (hatchPoints == null || hatchLabels == null
    			|| hatchPoints.size() != hatchLabels.size()) {
    		throw new IllegalArgumentException("hatchPoints and "
    				+ "hatchLabels cannot be null and must be of "
    				+ "same length");
    	}
    	
    	// Set attributes not dependent on orientation and position
    	this.hatchPoints = hatchPoints;
    	this.hatchLabels = hatchLabels;
    	if (hatchPoints.size() > 0) {
	        this.minValue = hatchPoints.get(0);
	        this.maxValue = hatchPoints.get(hatchPoints.size() - 1);
    	} else {
    		this.minValue = (float) 0.0;
    		this.maxValue = (float) 0.0;
    	}
        this.length = length;
        this.range = maxValue - minValue;
        this.orientation = orientation;
        this.positionTextRelativeToHatches = positionTextRelativeToHatches;
        this.spansZero = maxValue >= 0 && minValue <= 0;
        this.minX = 0;
        this.minY = 0;
        
        if (hatchPoints.size() > 0 && hatchLabels.size() > 0) {
        
	        // Set attributes distinct for horizontal orientation
	        if (orientation == Orientation.HORIZONTAL) {
	        	
	        	// Calculate reference coordinates
	        	String ticStr = hatchLabels.get(0);
	        	int textWidth = canvas.renderedWidth(ticStr, this.fontSize);
	        	int relativeTextMinX = this.nativeUnitsToPixel(
	        			hatchPoints.get(0)) - textWidth / 2;
	        	ticStr = hatchLabels.get(hatchLabels.size() - 1);
	        	textWidth = canvas.renderedWidth(ticStr, this.fontSize);
	        	int relativeTextMaxX = this.nativeUnitsToPixel(
	        			hatchPoints.get(hatchPoints.size() - 1))
	        		+ textWidth / 2;
	        	
	        	// Attributes that are same regardless of text location
	        	this.lineMinX = 0;
	        	this.lineMaxX = length;
	        	this.maxX = length;
	        	this.maxY = this.fontSize + this.padding + this.majorTicLength;
	        	if (this.spansZero) {
	        		this.zeroX = this.nativeUnitsToPixel(0.0);
	        	}
	        	if (relativeTextMinX < 0) {
	        		this.lineMinX -= relativeTextMinX;
	        		this.lineMaxX -= relativeTextMinX;
	        		this.maxX -= relativeTextMinX;
	        		if (this.spansZero) {
	        			this.zeroX -= relativeTextMinX;
	        		}
	        		relativeTextMaxX -= relativeTextMinX;
	        	}
	        	if (relativeTextMaxX > this.maxX) {
	        		this.maxX = relativeTextMaxX;
	        	}
	        	this.maxY = this.fontSize + this.padding + this.majorTicLength;
	        	
	        	// Text above
	        	if (positionTextRelativeToHatches == Location.ABOVE) {
	        		this.zeroY = this.maxY - this.majorTicLength / 2;
	        		
	        	// Text below
	        	} else if (positionTextRelativeToHatches == Location.BELOW) {
	        		this.zeroY = this.majorTicLength / 2;
	        		
	        	// Invalid text position
	        	} else {
	        		throw new IllegalArgumentException("Illegal combination of "
	        				+ "orientation and positionTextRelativeToHatches");
	        	}
	        	
	        	this.lineMinY = this.zeroY;
	    		this.lineMaxY = this.zeroY;
	        	
	        // Set attributes distinct for vertical orientation
	        } else if (orientation == Orientation.VERTICAL) {
	        	
	        	// Calculate reference coordinates
	        	int relativeTextMinY = length
	        		- this.nativeUnitsToPixel(hatchPoints.get(
	        				hatchPoints.size() - 1)) - this.fontSize / 2;
	        	int relativeTextMaxY = length
	        		- this.nativeUnitsToPixel(hatchPoints.get(
	        				hatchPoints.size() - 1))
	        		+ this.fontSize / 2;
	        	double majorTicInterval = 0.0;
	        	if (hatchLabels.size() == 1) {
	        		majorTicInterval = hatchPoints.get(0);
	        	} else {
	        		majorTicInterval = hatchPoints.get(
	        				hatchPoints.size() - 1)
	        				- hatchPoints.get(hatchPoints.size() - 2);
	        	}
	        	int maxTextWidth = this.maxTextWidth(hatchPoints.get(
        				hatchPoints.size() - 1),
	        			majorTicInterval, canvas);
	        	
	        	// Set attributes common to all text placements
	        	this.maxX = maxTextWidth + this.padding + this.majorTicLength;
	        	this.maxY = length;
	        	this.lineMinY = 0;
	        	this.lineMaxY = length;
	        	if (this.spansZero) {
	        		this.zeroY = this.length - this.nativeUnitsToPixel(0.0);
	        	}
	        	if (relativeTextMinY < 0) {
	        		this.maxY -= relativeTextMinY;
	        		this.lineMinY -= relativeTextMinY;
	        		this.lineMaxY -= relativeTextMinY;
		        	if (this.spansZero) {
		        		this.zeroY -= relativeTextMinY;
		        	}
	        	}
	        	if (relativeTextMaxY > length) {
	        		this.maxY += relativeTextMaxY - length;
	        	}
	        	
	        	// Text to left
	        	if (positionTextRelativeToHatches == Location.LEFT_OF) {
	        		this.zeroX = maxTextWidth + this.padding
	        			+ this.majorTicLength / 2;
	        		
	        	// Text to right
	        	} else if (positionTextRelativeToHatches == Location.RIGHT_OF) {
	        		this.zeroX = this.majorTicLength / 2;
	        		
	        	// Invalid text position
	        	} else {
	        		throw new IllegalArgumentException("Illegal combination of "
	        				+ "orientation and positionTextRelativeToHatches");
	        	}
	        	
	        	this.lineMinX = this.zeroX;
	        	this.lineMaxX = this.zeroX;
	        	
	        // Invalid orientation
	        } else {
	        	throw new IllegalArgumentException("Invalid orientation");
	        }
        }
    }
    
    
    // ===========================================
    //    Implementation of PlotElement interface
    // ===========================================
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(final int deltaX, final int deltaY) {
    	this.minX += deltaX;
    	this.minY += deltaY;
    	this.maxX += deltaX;
    	this.maxY += deltaY;
    	this.zeroX += deltaX;
    	this.zeroY += deltaY;
    	this.lineMinX += deltaX;
    	this.lineMinY += deltaY;
    	this.lineMaxX += deltaX;
    	this.lineMaxY += deltaY;
    }
    
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public void paint(final DrawingCanvas canvas) {
        
        // Paint main axis line
        Line line = null;
        line = new Line(this.lineMinX, this.lineMinY, this.lineMaxX,
        		this.lineMaxY, this.mainAxisLineThickness,
                this.color);
        canvas.add(line);
        
        // Case: User did not specify precise hatch locations and labels
        if (this.hatchPoints == null) {
        
	        // Set up for generating tic marks
		    int maxNumMajorTics = this.maxNumTicsThatFitOnOneLine(
	                new RenderedWidthCalculator(canvas), 
		    		this.length, this.minValue, this.maxValue, this.fontSize);
	        double majorTicInterval = this.computeTicInterval(
	                this.minValue, this.maxValue, maxNumMajorTics);
	        double minorTicInterval = majorTicInterval
	            / numMinorTicsBetweenMajorTics;
	        double startingMajorTic = this.computeStartTic(this.minValue,
	                majorTicInterval);
	        
	        // Create tic marks
	        for (double i = startingMajorTic - majorTicInterval;
	            i <= this.maxValue; i += majorTicInterval) {
	            
	            // Major tic mark
	            if (i >= this.minValue) {
	            	String label = this.numberFormatter.format(i);
	                AxisTicMark tic = this.newAxisTicMark(i, label, true);
	                tic.paint(canvas, true);
	            }
	            
	            // Minor tic marks
	            for (double j = i + minorTicInterval; j < i + majorTicInterval;
	                j += minorTicInterval) {
	                if (j >= this.minValue && j <= this.maxValue) {
		                AxisTicMark tic = this.newAxisTicMark(j, null, false);
		                tic.paint(canvas, false);
	                }
	            }
	        }
	        
	    // Case: user specified precise hatch locations and labels
        } else {
        	System.out.print("Axis:");
        	AxisTicMark lastTic = null;
        	for (int i = 0; i < this.hatchPoints.size(); i++) {
        		double point = this.hatchPoints.get(i);
        		String label = this.hatchLabels.get(i);
	        	AxisTicMark tic = this.newAxisTicMark(point, label, true);
	        	if (lastTic != null) {
	        		if (tic.overlapsWith(lastTic, padding, canvas)) {
	        			tic = this.newAxisTicMark(point, null, true);
	        		}
	        	}
	            tic.paint(canvas, true);
	            lastTic = tic;
	            System.out.print(" " + tic.lineX1);
        	}
        	System.out.println();
        }
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return new Point(this.lineMinX, this.lineMinX);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	return new Point(this.lineMinX, this.lineMaxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	return new Point(this.lineMaxX, this.lineMinY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
    	return new Point(this.lineMaxX, this.lineMaxY);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public int width() {
        return this.maxX - this.minX;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public int height() {
        return this.maxY - this.minY;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    // =================================
    //    Other business methods
    // =================================
    
    /**
     * Create grid with grid lines that match tic marks.
     * @param width Width of grid in pixels
     * @param height Height of grid in pixels
     * @param color Color
     * @param panel A plot panel
     * @return New grid
     */
    public Grid newGrid(final int width, final int height,
            final Color color, final PlotPanel panel) {
    	Grid grid = new Grid(width, height,
                Orientation.opposite(this.orientation), color);
    	if (this.hatchPoints == null) {
		    int maxNumMajorTics = this.maxNumTicsThatFitOnOneLine(
	                new RenderedWidthCalculator(panel), 
		    		this.length, this.minValue, this.maxValue, this.fontSize);
	        double majorTicInterval = this.computeTicInterval(
	                this.minValue, this.maxValue, maxNumMajorTics);
	        double startingMajorTic = this.computeStartTic(
	                this.minValue, majorTicInterval);
	        for (double x = startingMajorTic; x <= this.maxValue;
	            x += majorTicInterval) {
	            int p = this.nativeUnitsToPixel(x);
	            if (x == 0.0) {
	            	grid.setZeroPointLocation(p);
	            } else {
	            	grid.addGridMarkPosition(p);
	            }
	        }
    	} else {
    		for (Double hatchPoint : this.hatchPoints) {
    			int p = this.nativeUnitsToPixel(hatchPoint);
    			grid.addGridMarkPosition(p);
    		}
    	}
        return grid;
    }
    
    
	/**
	 * Compute value of first tic mark.
	 * @param min Minimum value
	 * @param interval Interval between tic marks
	 * @return Value of first tic mark
	 */
	private double computeStartTic(
            final double min, final double interval) {
	    float minFloat = (float) min;
	    float intervalFloat = (float) interval;
		return (double) (intervalFloat * Math.ceil(minFloat / intervalFloat));
	}
	
	
	/**
	 * Calculate maximum width of axis labels text.
	 * @param minMajorTic Value of minimum major tic mark.
	 * @param interval Interval between major tic marks
	 * @param canvas Drawing canvas where axis will be rendered
	 * @return Maximum width of axis labels text in pixels
	 */
	private int maxTextWidth(final double minMajorTic, final double interval,
			final DrawingCanvas canvas) {
		int max = 0;
		for (double d = minMajorTic; d <= this.maxValue; d += interval) {
			String label = this.numberFormatter.format(d);
			int candidateMax = canvas.renderedWidth(label, this.fontSize);
			if (candidateMax > max) {
				max = candidateMax;
			}
		}
		return max;
	}
	
	/**
	 * Compute value of last major tic mark.
	 * @param startTic Value of first major tic mark
	 * @param interval Interval between major tic marks
	 * @return Value of last major tic mark
	 */
	private double computeEndTic(final double startTic, final double interval) {
		double endTic = startTic;
		for (double i = startTic; i <= this.maxValue; i += interval) {
			endTic = i;
		}
		return endTic;
	}

	/**
	 * Compute the interval between tic marks.
	 * @param min Minimum value
	 * @param max Maximum value
	 * @param maxNumTics Maximum number of tic marks
	 * @return Interval between tic marks
	 */
	private double computeTicInterval(final double min, final double max,
            final int maxNumTics) {
        
		// Special case: min == max
		if (min == max) {
			return 1.0;
        }
			
		float range = (float) Math.abs(max - min);
		float ticInterval = Float.NaN;
		float power = (float) Math.pow((float) 10.0, Math.ceil(Math.log(range)
                / Math.log((float) 10.0)) + (float) 1.0);
		boolean done = false;
		while (!done) {
		    for (int i = 0; i < MULTIPLIERS.length && !done; i++) {
					float temp = MULTIPLIERS[i] * power;
				if (Math.ceil(range / temp) > maxNumTics) {
					done = true;
                } else if (range / temp > 0) {
					ticInterval = temp;
                }
		    }
			power /= (float) 10.0;
		}
		return (double) ticInterval;
	}
	
	
	/**
	 * Heuristically determine how many tic marks 
	 * over given range could fit
	 * on one text line of given width.
	 * @param widthCalculator Width calculator
	 * @param width Width of target text line
	 * @param min Minimum value in range
	 * @param max Maximum value in range
	 * @param fontSize Font size
	 * @return Number of tic marks
	 */
	private int maxNumTicsThatFitOnOneLine(
		final RenderedWidthCalculator widthCalculator,
		final int width, final double min, final double max, 
		final int fontSize
	) {
	    StringBuffer template =
	    	new StringBuffer(this.numberFormatter.format(max));
        int maxWidth = widthCalculator.renderedWidth(template.toString());
	    for (int i = 0; i < MULTIPLIERS.length; i++) {
	        template = new StringBuffer(
	        		this.numberFormatter.format(max / MULTIPLIERS[i]));
	        int candidateMaxWidth =
	        	widthCalculator.renderedWidth(template.toString());
	        if (candidateMaxWidth > maxWidth) {
	            maxWidth = candidateMaxWidth;
	        }
	    }
		maxWidth += this.padding;
		return (int) Math.floor((double) width / (double) maxWidth);
	}
	
	
	/**
	 * Convert native units to pixel equivalents.
	 * @param value Value to convert
	 * @return Pixel equivalent of given native values
	 */
	private int nativeUnitsToPixel(final double value) {
	    return (int) (((value - this.minValue) / this.range)
	    		* (double) this.length);
	}
	
	
	/**
	 * Create new axis tic mark.
	 * @param value Value
	 * @param label Label of tic
	 * @param isMajor Is a major tic mark?
	 * @return A tic mark
	 */
	private AxisTicMark newAxisTicMark(final double value,
			final String label,
			final boolean isMajor) {
	    int pixel = this.nativeUnitsToPixel(value);
	    Point point = null;
	    if (this.orientation == Orientation.HORIZONTAL) {
	        point = new Point(this.lineMinX + pixel, this.lineMinY);
	    } else if (this.orientation == Orientation.VERTICAL) {
	        point = new Point(this.lineMinX, this.lineMaxY - pixel);
	    }
	    AxisTicMark tic = new AxisTicMark(point, label,
	    		Orientation.opposite(this.orientation), 
	    		this.positionTextRelativeToHatches);
	    tic.setColor(this.color);
	    tic.setFontSize(this.fontSize);
        if (isMajor) {
            tic.setLineThickness(this.majorHatchLineThickness);
        } else {
            tic.setLineThickness(this.minorHatchLineThickness);
        }
	    tic.setPadding(this.padding);
	    if (isMajor) {
	        tic.setLength(this.majorTicLength);
	    } else {
	        tic.setLength(this.minorTicLength);
	    }
	    return tic;
	}
	

	/**
	 * Helper class to compute widths.
	 * @author dhall
	 *
	 */
	final class RenderedWidthCalculator {
		
		/** Plot panel. */
		private PlotPanel panel = null;
		
		/** Drawing canvas used to determine text size. */
		private DrawingCanvas canvas = null;
		
		/**
		 * Constuctor.
		 * @param panel Plot panel
		 */
		public RenderedWidthCalculator(final PlotPanel panel) {
			this.panel = panel;
		}
		
		/**
		 * Constructor.
		 * @param canvas A drawing canvas
		 */
		public RenderedWidthCalculator(final DrawingCanvas canvas) {
			this.canvas = canvas;
		}
		
		/**
		 * Get rendered width of text.
		 * @param text Some text
		 * @return Rendered width in pixels
		 */
		public int renderedWidth(final String text) {
			int width = 0;
			if (this.panel != null) {
				width = this.panel.renderedWidth(text, fontSize);
			} else if (this.canvas != null) {
				width = this.canvas.renderedWidth(text, fontSize);
			}
			return width;
		}
	}
	
	
	// ========================================
	//       ScalePlotElement
	// ========================================
	
	/**
	 * Return point in pixels corresponding to the zero point
	 * in the native units of measurement represented by
	 * element.
	 * @return A point or <code>null</code> if the element
	 * does not contain a zero point
	 */
	public Point zeroPoint() {
		Point p = null;
		if (this.spansZero) {
			p = new Point(this.zeroX, this.zeroY);
		} else {
			p = new Point(0, 0);
		}
		return p;
	}
	
	
	/**
	 * Tic mark on a plot axis.
	 */
	static final class AxisTicMark implements Serializable {
		
		/** Serial version ID. */
		private static final long serialVersionUID = 
			SystemUtils.getLongApplicationProperty("serial.version.uid");
	    
	    
	    // ===========================
	    //    Static variables
	    // ===========================
	    
		/** Color of hyperlinks. */
	    private static final Color HYPERLINK_COLOR = Color.blue;
		
	    
	    // ========================================
	    //        Attributes
	    // ========================================
	    
	    /** Have the drawing coordinates been set? */
	    private boolean drawingCoordinatesSet = false;
	    
	    /** Alignment point of tic mark. */
	    private final Point alignmentPoint;
	    
	    /** Label of tic mark. */
		private final String label;
		
		/** URL of hyperlink associated with tic mark. */
		private final URL link;
		
		/** Orientation of axis. */
		private final Orientation orientation;
		
		/** Orientation of tic mark. */
		private final Location labelLocation;
		
		/** X-coordinate of first end of tic mark line. */
		private int lineX1 = 0;
		
		/** Y-coordinate of first end of tic mark line. */
		private int lineY1 = 0;
		
		/** X-coordinate of second end of tic mark line. */
		private int lineX2 = 0;
		
		/** Y-coordinate of second end of tic mark line. */
		private int lineY2 = 0;
		
		/** X-coordinate of tic mark label. */
		private int textX = 0;
		
		/** Y-coordinate of tic mark label. */
		private int textY = 0;
		
		/** Minimum X-coordinate in tic mark. */
		private int minX = 0;
		
		/** Maximum X-coordinate in tic mark. */
		private int maxX = 0;
		
		/** Minimum Y-coordinate in tic mark. */
		private int minY = 0;
		
		/** Maximum Y-coordinate in tic mark. */
		private int maxY = 0;
		
		/** Thicknes of tick mark line. */
		private int lineThickness = TIC_MARK_THICKNESS;
		
		/** Length of tic mark line. */
		private int length = 20;
		
		/** Font size of label. */
		private int fontSize = 12;
		
		/** Padding between graphical elements. */
		private int padding = 5;
		
		/** Color of graphical elements. */
		private Color color = Color.black;
		
		
		
	    /**
	     * @param color The color to set.
	     */
	    public void setColor(final Color color) {
	        this.color = color;
	    }
	    
	    
	    /**
	     * @param fontSize The fontSize to set.
	     */
	    public void setFontSize(final int fontSize) {
	        this.fontSize = fontSize;
	    }
	    
	    
	    /**
	     * @param lineThickness The lineWidth to set.
	     */
	    public void setLineThickness(final int lineThickness) {
	        this.lineThickness = lineThickness;
	    }
	    
	    
	    /**
	     * @param padding The padding to set.
	     */
	    public void setPadding(final int padding) {
	        this.padding = padding;
	    }
	    
	    
	    /**
	     * @param length The length to set.
	     */
	    public void setLength(final int length) {
	        this.length = length;
	    }
	    
	    
		// =======================================
		//       Constructors
		// =======================================
		
		/**
		 * Constructor.
		 * @param alignmentPoint Alignment point
		 * @param label Tic mark label
		 * @param link Link associated with tic mark
		 * @param orientation Orientation of tic mark
		 * @param labelLocation Relative location of label to hatch mark
		 */
		public AxisTicMark(final Point alignmentPoint,
				final String label, final URL link,
				final Orientation orientation, 
				final Location labelLocation) {
			this.alignmentPoint = alignmentPoint;
			this.label = label;
			this.link = link;
			this.orientation = orientation;
			this.labelLocation = labelLocation;
		}
		
		
		/**
		 * Constructor.
		 * @param alignmentPoint Alignment point
		 * @param label Tic mark label
		 * @param orientation Orientation of tic mark
		 * @param labelLocation Relative location of label to hatch mark
		 */
		public AxisTicMark(final Point alignmentPoint,
				final String label, final Orientation orientation, 
				final Location labelLocation) {
		    this(alignmentPoint, label, null, orientation, labelLocation);
		}
		
		
		// ===================================================
		//         Public methods
		// ===================================================
		
		
		/**
		 * Draw tic mark.
		 * @param canvas A drawing canvas
		 * @param drawLabel Draw label?
		 */
		public void paint(final DrawingCanvas canvas,
				final boolean drawLabel) {
		    this.setDrawingCoordinates(canvas);
		    this.drawLine(canvas);
		    if (drawLabel && this.label != null && this.label.length() > 0) {
		        this.drawText(canvas);
		    }
		}
		
		
		/**
		 * Does this overlap given tic mark?
		 * @param axisTicMark An axis tic mark
		 * @param padding Padding that should be between given tic mark and this
		 * @param canvas The canvas this will be rendered on
		 * @return T/F
		 */
		public boolean overlapsWith(final AxisTicMark axisTicMark,
				final int padding, final DrawingCanvas canvas) {
			this.setDrawingCoordinates(canvas);
			axisTicMark.setDrawingCoordinates(canvas);
		    return this.overlapsHorizontally(axisTicMark, padding)
		    	&& this.overlapsVertically(axisTicMark, padding);
		}
		
		
		/**
		 * Get minimum X-coordinate.
		 * @return Minimum X-coordinate
		 */
		public int minX() {
		    return this.minX;
		}
		
		
		/**
		 * Get maximum X-coordinate.
		 * @return Maximum X-coordinate
		 */
		public int maxX() {
		    return this.maxX;
		}
		
		
		/**
		 * Get minimum Y-coordinate.
		 * @return Minimum Y-coordinate
		 */
		public int minY() {
		    return this.minY;
		}
		
		
		/**
		 * Get maximum Y-coordinate.
		 * @return Maximum Y-coordinate
		 */
		public int maxY() {
		    return this.maxY;
		}
		
		
		// =============================
		//    Private methods
		// =============================
		
		
		/**
		 * Set drawing coordinates of tic mark.
		 * @param canvas Canvas that will be drawn upon
		 */
		private void setDrawingCoordinates(final DrawingCanvas canvas) {
			if (!this.drawingCoordinatesSet) {
			    int textLength = 0;
			    int textHeight = 0;
			    int textPadding = 0;
			    if (this.label != null && this.label.length() > 0) {
			        textLength = canvas.renderedWidth(
			        		this.label, this.fontSize);
			        textHeight = this.fontSize;
			        textPadding = this.padding;
			    }
			    
			    // Horizontal orientation
			    if (this.orientation == Orientation.HORIZONTAL) {
			        this.lineX1 = this.alignmentPoint.x - this.length / 2;
			        this.lineY1 = this.alignmentPoint.y;
			        this.lineX2 = this.lineX1 + length;
			        this.lineY2 = this.lineY1;
			        this.textY = this.lineY1 + this.fontSize / 3;
			        this.minY = this.lineY1 - textHeight / 2;
			        this.maxY = this.lineY1 + textHeight / 2;
			        if (this.labelLocation == Location.LEFT_OF) {
			            this.textX = this.lineX1 - textLength - textPadding;
			            this.minX = this.textX;
			            this.maxX = this.lineX2;
			        } else if (this.labelLocation == Location.RIGHT_OF) {
			            this.textX = this.lineX2 + textPadding;
			            this.minX = this.lineX1;
			            this.maxX = this.lineX2 + textPadding + textLength;
			        } else {
			            throw new IllegalArgumentException(
			            		"Illegal combination of orientation "
			            		+ "and label location");
			        }
			    
			    // Vertical orientation
			    } else if (this.orientation == Orientation.VERTICAL) {
			        this.lineX1 = this.alignmentPoint.x;
			        this.lineY1 = this.alignmentPoint.y - this.length / 2;
			        this.lineX2 = this.lineX1;
			        this.lineY2 = this.lineY1 + this.length;
			        this.textX = this.lineX1 - textLength / 2;
			        this.minX = this.lineX1 - textLength / 2;
			        this.maxX = this.lineX2 + textLength / 2;
			        if (this.labelLocation == Location.ABOVE) {
			            this.textY = this.lineY1 - textPadding;
			            this.minY = this.lineY1 - textPadding - textHeight;
			            this.maxY = this.lineY2;
			        } else if (this.labelLocation == Location.BELOW) {
			            this.textY = this.lineY2 + textPadding + textHeight;
			            this.minY = this.lineY1;
			            this.maxY = this.lineY2 + textPadding + textHeight;
			        } else {
			            throw new IllegalArgumentException(
			            		"Illegal combination of orientation "
			            		+ "and label location");
			        }
			    }
			    this.drawingCoordinatesSet = true;
			}
		}
		
		
		/**
		 * Draw tic mark line.
		 * @param canvas A canvas
		 */
		private void drawLine(final DrawingCanvas canvas) {
		    Line line = new Line(this.lineX1, this.lineY1, this.lineX2,
		    		this.lineY2,
		        this.lineThickness, this.color);
		    canvas.add(line);
		}
		
		
		/**
		 * Draw tic mark text.
		 * @param canvas A canvas
		 */
		private void drawText(final DrawingCanvas canvas) {
		    Text text = canvas.newText(this.label, this.textX, this.textY,
		        this.fontSize, HorizontalAlignment.LEFT_JUSTIFIED, this.color);
		    if (this.link != null) {
		        text.setHyperlink(new Hyperlink(this.link));
		        text.setColor(HYPERLINK_COLOR);
		        text.setCursor(Cursor.POINTER);
		    }
		    canvas.add(text);
		}
		
		
		/**
		 * Does given tic mark overlap horizontally with this?
		 * @param ticMark A tic mark
		 * @param padding Padding in pixels
		 * @return T/F
		 */
		private boolean overlapsHorizontally(final AxisTicMark ticMark,
				final int padding) {
		    if (this.label == null || this.label.length() < 1
		    		|| ticMark.label == null || ticMark.label.length() < 1) {
		        return false;
		    }
		    int leftEdge = this.minX - padding;
		    int rightEdge = this.maxX + padding;
		    return leftEdge <= ticMark.maxX && rightEdge >= ticMark.minX;
		}
		
		
		/**
		 * Does given tic mark overlap vertically with this?
		 * @param ticMark A tic mark
		 * @param padding Padding in pixels
		 * @return T/F
		 */
		private boolean overlapsVertically(final AxisTicMark ticMark,
				final int padding) {
		    if (this.label == null || this.label.length() < 1
		    		|| ticMark.label == null || ticMark.label.length() < 1) {
		        return false;
		    }
		    int topEdge = this.minY - padding;
		    int bottomEdge = this.maxY + padding;
		    return topEdge <= ticMark.maxY && bottomEdge >= ticMark.minY;
		}
	}

}
