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
import java.io.Serializable;
import java.net.URL;

import org.apache.log4j.Logger;
import org.rti.webcgh.drawing.Cursor;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Hyperlink;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.drawing.Text;
import org.rti.webcgh.graph.util.NumberFormatter;
import org.rti.webcgh.graph.util.RealNumberFormatter;
import org.rti.webcgh.graph.widget.ScalePlotElement;


/**
 * A plot axis.
 */
public final class Axis implements ScalePlotElement {
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(Axis.class);
    

    // =============================
    //       Attributes
    // =============================
    
    
    /** Minimum value on axis in the native units. */
    private final double minValue;
    
    /** Maximum value on axis in the native units. */
    private final double maxValue;
    
    /** Length of axis in pixels. */
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
    
    /** Length of major tic marks in pixels. */
    private int majorTicLength = 20;
    
    /** Length of minor tic marks in pixels. */
    private int minorTicLength = 8;
    
    /** Font size of major tic mark text labels. */
    private int fontSize = 12;
    
    /** Thickness of main axis line and hatch marks. */
    private int lineThickness = 2;
    
    /** Padding between all graphical elements in pixels. */
    private int padding = 5;
    
    /** Number formatter. */
    private NumberFormatter numberFormatter = new RealNumberFormatter(12, 4);
    
    private int minX = 0;
    private int maxX = 0;
    private int minY = 0;
    private int maxY = 0;
    private int zeroX = 0;
    private int zeroY = 0;
    private int deltaX = 0;
    private int deltaY = 0;
    private final boolean spansZero;
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
     */
    public Axis(double minValue, double maxValue, int length, Orientation orientation,
        Location positionTextRelativeToHatches) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.length = length;
        this.range = maxValue - minValue;
        this.orientation = orientation;
        this.positionTextRelativeToHatches = positionTextRelativeToHatches;
        double scale = (double)length / (maxValue - minValue);
        this.spansZero = maxValue >= 0 && minValue <= 0;
        if (orientation == Orientation.HORIZONTAL) {
            this.maxX = length;
            if (this.spansZero)
            	this.zeroX = (int)((- minValue) * scale);
        } else if (orientation == Orientation.VERTICAL) {
            this.maxY = length;
            if (this.spansZero)
            	this.zeroY = length - (int)((- minValue) * scale);
        }
    }
    
    
    // ===========================================
    //    Implementation of PlotElement interface
    // ===========================================
    
    
    /**
     * Move element
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(int deltaX, int deltaY) {
    	this.minX += deltaX;
    	this.minY += deltaY;
    	this.maxX += deltaX;
    	this.maxY += deltaY;
    	this.zeroX += deltaX;
    	this.zeroY += deltaY;
    	this.deltaX += deltaX;
    	this.deltaY += deltaY;
    }
    
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
	    int maxNumMajorTics = this.maxNumTicsThatFitOnOneLine(new RenderedWidthCalculator(canvas), 
	    		this.length, this.minValue, this.maxValue, this.fontSize);
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
        return new Point(this.deltaX, this.deltaY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	int x = deltaX, y = this.deltaY;
    	if (this.orientation == Orientation.VERTICAL)
    			y = this.deltaY + this.length;
    	return new Point(x, y);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	int x = deltaX, y = deltaY;
    	if (this.orientation == Orientation.HORIZONTAL)
    			x = this.deltaX + this.length;
    	return new Point(x, y);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
    	return new Point(this.deltaX, this.deltaY);
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
    
    
    // =================================
    //    Public methods
    // =================================
    
    /**
     * Create grid with grid lines that match tic marks
     * @param width Width of grid in pixels
     * @param height Height of grid in pixels
     * @param color Color
     * @param panel A plot panel
     * @return New grid
     */
    public Grid newGrid(int width, int height, Color color, PlotPanel panel) {
        Grid grid = new Grid(width, height, Orientation.opposite(this.orientation), color);
	    int maxNumMajorTics = this.maxNumTicsThatFitOnOneLine(new RenderedWidthCalculator(panel), 
	    		this.length, this.minValue, this.maxValue, this.fontSize);
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
	 * @param panel A plotpanel
	 * @param width Width of target text line
	 * @param min Minimum value in range
	 * @param max Maximum value in range
	 * @param fontSize Font size
	 * @return Number of tic marks
	 */
	private int maxNumTicsThatFitOnOneLine
	(
		RenderedWidthCalculator widthCalculator, int width, double min, double max, 
		int fontSize
	) {
	    StringBuffer template = new StringBuffer(this.numberFormatter.format(max));
        int maxWidth = widthCalculator.renderedWidth(template.toString());
	    for (int i = 0; i < this.multipliers.length; i++) {
	        template = new StringBuffer(this.numberFormatter.format(max / this.multipliers[i]));
	        int candidateMaxWidth = widthCalculator.renderedWidth(template.toString());
	        if (candidateMaxWidth > maxWidth)
	            maxWidth = candidateMaxWidth;
	    }
		maxWidth += this.padding;
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
	
	class RenderedWidthCalculator {
		
		PlotPanel panel = null;
		DrawingCanvas canvas = null;
		
		public RenderedWidthCalculator(PlotPanel panel) {
			this.panel = panel;
		}
		
		public RenderedWidthCalculator(DrawingCanvas canvas) {
			this.canvas = canvas;
		}
		
		public int renderedWidth(String text) {
			int width = 0;
			if (this.panel != null)
				width = this.panel.renderedWidth(text, fontSize);
			else if (this.canvas != null)
				width = this.canvas.renderedWidth(text, fontSize);
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
		if (this.spansZero)
			p = new Point(this.zeroX, this.zeroY);
		else
			p = new Point(0, 0);
		return p;
	}
	
	
	/**
	 * Tic mark on a plot axis
	 */
	static class AxisTicMark implements Serializable {
	    
	    
	    // ===========================
	    //    Static variables
	    // ===========================
	    
	    private static final Color HYPERLINK_COLOR = Color.blue;
		
	    
	    // ========================================
	    //        Attributes
	    // ========================================
	    
	    private final Point alignmentPoint;
		private final String label;
		private final URL link;
		private final Orientation orientation;
		private final Location labelLocation;
		
		private int lineX1 = 0;
		private int lineY1 = 0;
		private int lineX2 = 0;
		private int lineY2 = 0;
		private int textX = 0;
		private int textY = 0;
		private int minX = 0;
		private int maxX = 0;
		private int minY = 0;
		private int maxY = 0;
		
		private int lineThickness = 3;
		private int length = 20;
		private int fontSize = 12;
		private int padding = 5;
		private Color color = Color.black;
		
		
		
	    /**
	     * @param color The color to set.
	     */
	    public void setColor(Color color) {
	        this.color = color;
	    }
	    
	    
	    /**
	     * @param fontSize The fontSize to set.
	     */
	    public void setFontSize(int fontSize) {
	        this.fontSize = fontSize;
	    }
	    
	    
	    /**
	     * @param lineThickness The lineWidth to set.
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
	    
	    
	    /**
	     * @param length The length to set.
	     */
	    public void setLength(int length) {
	        this.length = length;
	    }
	    
	    
		// =======================================
		//       Constructors
		// =======================================
		
		/**
		 * Constructor
		 * @param alignmentPoint Alignment point
		 * @param label Tic mark label
		 * @param link Link associated with tic mark
		 * @param orientation Orientation of tic mark
		 * @param labelLocation Relative location of label to hatch mark
		 */
		public AxisTicMark(Point alignmentPoint, String label, URL link, Orientation orientation, 
		    Location labelLocation) {
			this.alignmentPoint = alignmentPoint;
			this.label = label;
			this.link = link;
			this.orientation = orientation;
			this.labelLocation = labelLocation;
		}
		
		
		/**
		 * Constructor
		 * @param alignmentPoint Alignment point
		 * @param label Tic mark label
		 * @param orientation Orientation of tic mark
		 * @param labelLocation Relative location of label to hatch mark
		 */
		public AxisTicMark(Point alignmentPoint, String label, Orientation orientation, 
		    Location labelLocation) {
		    this(alignmentPoint, label, null, orientation, labelLocation);
		}
		
		
		// ===================================================
		//         Public methods
		// ===================================================
		
		
		/**
		 * Draw
		 * @param canvas A drawing canvas
		 * @param drawLabel Draw label?
		 */
		public void paint(DrawingCanvas canvas, boolean drawLabel) {
		    this.setDrawingCoordinates(canvas);
		    this.drawLine(canvas);
		    if (drawLabel && this.label != null && this.label.length() > 0)
		        this.drawText(canvas);
		}
		
		
		/**
		 * Does this overlap given tic mark?
		 * @param axisTicMark An axis tic mark
		 * @param padding Padding that should be between given tic mark and this
		 * @return T/F
		 */
		public boolean overlapsWith(AxisTicMark axisTicMark, int padding) {
		    return this.overlapsHorizontally(axisTicMark, padding) && this.overlapsVertically(axisTicMark, padding);
		}
		
		
		/**
		 * Get minimum X-coordinate
		 * @return Minimum X-coordinate
		 */
		public int minX() {
		    return this.minX;
		}
		
		
		/**
		 * Get maximum X-coordinate
		 * @return Maximum X-coordinate
		 */
		public int maxX() {
		    return this.maxX;
		}
		
		
		/**
		 * Get minimum Y-coordinate
		 * @return Minimum Y-coordinate
		 */
		public int minY() {
		    return this.minY;
		}
		
		
		/**
		 * Get maximum Y-coordinate
		 * @return Maximum Y-coordinate
		 */
		public int maxY() {
		    return this.maxY;
		}
		
		
		// =============================
		//    Private methods
		// =============================
		
		
		private void setDrawingCoordinates(DrawingCanvas canvas) {
		    int textLength = 0;
		    int textHeight = 0;
		    int textPadding = 0;
		    if (this.label != null && this.label.length() > 0) {
		        textLength = canvas.renderedWidth(this.label, this.fontSize);
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
		        } else
		            throw new IllegalArgumentException("Illegal combination of orientation and label location");
		    }
		    
		    // Vertical orientation
		    else if (this.orientation == Orientation.VERTICAL) {
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
		        } else
		            throw new IllegalArgumentException("Illegal combination of orientation and label location");
		    }
		}
		
		
		private void drawLine(DrawingCanvas canvas) {
		    Line line = new Line(this.lineX1, this.lineY1, this.lineX2, this.lineY2,
		        this.lineThickness, this.color);
		    canvas.add(line);
		}
		
		
		private void drawText(DrawingCanvas canvas) {
		    Text text = canvas.newText(this.label, this.textX, this.textY,
		        this.fontSize, HorizontalAlignment.LEFT_JUSTIFIED, this.color);
		    if (this.link != null) {
		        text.setHyperlink(new Hyperlink(this.link));
		        text.setColor(HYPERLINK_COLOR);
		        text.setCursor(Cursor.POINTER);
		    }
		    canvas.add(text);
		}
		
		
		private boolean overlapsHorizontally(AxisTicMark ticMark, int padding) {
		    if (this.label == null || this.label.length() < 1 || ticMark.label == null || ticMark.label.length() < 1)
		        return false;
		    int leftEdge = this.minX - padding;
		    int rightEdge = this.maxX + padding;
		    return leftEdge <= ticMark.maxX && rightEdge >= ticMark.minX;
		}
		
		
		private boolean overlapsVertically(AxisTicMark ticMark, int padding) {
		    if (this.label == null || this.label.length() < 1 || ticMark.label == null || ticMark.label.length() < 1)
		        return false;
		    int topEdge = this.minY - padding;
		    int bottomEdge = this.maxY + padding;
		    return topEdge <= ticMark.maxY && bottomEdge >= ticMark.minY;
		}
	}

}
