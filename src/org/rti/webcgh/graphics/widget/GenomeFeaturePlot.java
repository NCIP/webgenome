/*
$Revision: 1.2 $
$Date: 2006-09-19 02:09:30 $

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.GraphicPrimitive;
import org.rti.webcgh.graphics.primitive.Polygon;
import org.rti.webcgh.graphics.primitive.Polyline;
import org.rti.webcgh.graphics.util.Warper;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;


/**
 * Map of genome features.
 */
public class GenomeFeaturePlot implements PlotElement {
	
	
	// ======================
	//    Constants
	// ======================
	
	// TODO: Uncomment out all of the text-related lines
//	/** Size of font. */
//	private static final int FONT_SIZE = 12;
//	
//	/** Color of text. */
//	private static final Color TEXT_COLOR = Color.blue;
//	
//	/** Padding between graphical elements in pixels. */
//	private static final int PADDING = 5;
//	
//	/** Thickness of lines in map. */
//	private static final int LINE_THICKNESS = 2;
	
	
	// ======================================
	//      Attributes
	// ======================================
	
	/** Starting position of map on chromosome. */
	private final long chromosomeStart;
	
	/**
	 * Length of map in pixels. If horizontal, this is
	 * the width.  If vertical, this is the height.
	 */
	private final int length;
	
	/** Scale of base pairs to pixels. */
	private final double scale;
	
	/** Orientation of graphically rendered map. */
	private final Orientation orientation;
	
	/**
	 * Thickness of features in map. If the orientation is
	 * horizontal, this is the height.  If the orientation
	 * is vertical, this is the width.
	 */
	private final int featureThickness;
	
	/**
	 * Warper that gives certain maps, like
	 * chromosome ideograms, an hourglass shape.
	 */
	private Warper warper = null;
	
	/** Minimum X-coordinate in rendered map. */
	private int minX = 0;
	
	/** Maximum X-coordinate in rendered map. */
	private int maxX = 0;
	
	/** Minimum Y-coordinate in rendered map. */
	private int minY = 0;
	
	/** Maximum Y-coordinate in rendered map. */
	private int maxY = 0;
	
	/**
	 * Minimum X-coordinate of data track only,
	 * i.e., without considering text.
	 */
	private int trackMinX = 0;
	
	/**
	 * Maximum X-coordinate of data track only,
	 * i.e., without considering text.
	 */
	private int trackMaxX = 0;
	
	/**
	 * Minimum Y-coordinate of data track only,
	 * i.e., without considering text.
	 */
	private int trackMinY = 0;
	
	/**
	 * Maximum Y-coordinate of data track only,
	 * i.e., without considering text.
	 */
	private int trackMaxY = 0;
	
	/**
	 * Container to hold generated graphical
	 * primitives prior to rendering.
	 */
	private final List<GraphicPrimitive>
		graphicPrimitives = new ArrayList<GraphicPrimitive>();
	
//	/**
//	 * Container to hold text labels
//	 * prior to rendering.
//	 */
//	private final List<Label> labels = new ArrayList<Label>();
	
	
	
	// ===================================
	//       Getters/setters
	// ===================================
	
	
	/**
	 * Set warper that gives map hourglass
	 * appearance.
	 * @param warper A warper
	 */
	public final void setWarper(final Warper warper) {
		this.warper = warper;
	}
	
	
	/**
	 * Get scale of base pairs to pixels.
	 * @return Scale of base pairs to pixels.
	 */
	public final double getScale() {
		return this.scale;
	}
	
	
	/**
	 * Get length of map in pixels. If horizontal, this is
	 * the width.  If vertical, this is the height.
	 * @return Length in pixels.
	 */
	public final int getLength() {
		return this.length;
	}
	
	
	// ================================
	//     Constructors
	// ================================


	/**
	 * Constructor.
	 * @param chromosomeStart Chromosome start point
	 * @param chromosomeEnd Chromosome end point
	 * @param length Length of map in pixels. If horizontal, this is
	 * the width.  If vertical, this is the height.
	 * @param orientation Orientation
	 * @param featureThickness Thickness of features in map in pixels.
	 * If the orientation is
	 * horizontal, this is the height.  If the orientation
	 * is vertical, this is the width.
	 */
	public GenomeFeaturePlot(final long chromosomeStart,
			final long chromosomeEnd, final int length,
			final Orientation orientation,
			final int featureThickness) {
		this.chromosomeStart = chromosomeStart;
		this.length = length;
		this.scale = (double) length
			/ (chromosomeEnd - chromosomeStart);
		this.featureThickness = featureThickness;
		this.orientation = orientation;
		if (orientation == Orientation.HORIZONTAL) {
			this.minX = 0;
			this.minY = 0;
			this.maxX = length;
			this.maxY = featureThickness;
			this.trackMinX = 0;
			this.trackMinY = 0;
			this.trackMaxX = length;
			this.trackMaxY = featureThickness;
		} else if (orientation == Orientation.VERTICAL) {
			this.minX = 0;
			this.minY = 0;
			this.maxX = featureThickness;
			this.maxY = length;
			this.trackMinX = 0;
			this.trackMinY = 0;
			this.trackMaxX = featureThickness;
			this.trackMaxY = length;
		}
	}
	
	
	// =====================================
	//      Business methods
	// =====================================
	
	/**
	 * Plot a feature.
	 * @param start Chromosome start point of feature
	 * @param end Chromosome end point of feature
	 * @param name Feature name
	 * @param url URL to additional information about feature
	 * @param drawLabel Draw a label above feature
	 * @param color Feature color
	 */
	public final void plotFeature(final long start,
			final long end, final String name, final URL url, 
			final boolean drawLabel, final Color color) {
		Polygon poly = this.newPolygon(start, end, color);
		poly.setToolTipText(name);
		this.graphicPrimitives.add(poly);
//		if (drawLabel) {
//			int pix = this.bpToPixel((start + end) / 2);
//			int x = 0, y = 0;
//			if (this.orientation == Orientation.HORIZONTAL) {
//				
//			}
//			this.labels.add(new Label(x, y, name, url));
//		}
	}
	
	
	/**
	 * Plot features.
	 * @param starts Chromosome start points of feature
	 * @param ends Chromosome end points of feature
	 * @param name Feature name
	 * @param url URL to additional information about feature
	 * @param drawLabel Draw a label above feature
	 * @param color Feature color
	 */
	public final void plotFeatures(final long[] starts,
			final long[] ends, final String name, final URL url,
			final boolean drawLabel, final Color color) {
		if (starts == null || ends == null || starts.length < 1
				|| ends.length < 1) {
			throw new IllegalArgumentException(
				"Parameters 'starts' and 'ends' cannot "
					+ "be null or empty");
		}
		if (starts.length != ends.length) {
			throw new IllegalArgumentException(
				"Arrays 'starts' and 'ends' must "
					+ "be of same length");
		}
		
		// Draw features
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for (int i = 0; i < starts.length && i < ends.length; i++) {
			if (starts[i] < min) {
				min = starts[i];
			}
			if (ends[i] > max) {
				max = ends[i];
			}
			Polygon poly = this.newPolygon(starts[i], ends[i], color);
			this.graphicPrimitives.add(poly);
		}
//		int minX = this.bpToPixel(min);
//		int maxX = this.bpToPixel(max);
//		
//		// Draw connecting lines
//		if (this.orientation == Orientation.HORIZONTAL) {
//			int middleY = 
//			this.graphicPrimitives.add(new Line(minX, this.middleY, maxX, 
//				this.middleY, this.LINE_THICKNESS, color));
//		}
//		
//		// Draw text
//		if (drawLabel) {
//			int textX = (minX + maxX) / 2;
//			this.labels.add(new Label(textX, name, url));
//		}
	}
	
	
	/**
	 * Add a graphical frame around part of map.
	 * @param location Location of frame
	 * @param lineThickness Line thickness of frame
	 * @param color Color of frame
	 */
	public final void addFrame(final Location location,
			final int lineThickness, final Color color) {
		
		// Set line endpoints
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		if (location == Location.ABOVE) {
			x1 = this.trackMinX;
			y1 = this.trackMinY;
			x2 = this.trackMaxX;
			y2 = this.trackMinY;
		} else if (location == Location.BELOW) {
			x1 = this.trackMinX;
			y1 = this.trackMaxY;
			x2 = this.trackMaxX;
			y2 = this.trackMaxY;
		} else if (location == Location.LEFT_OF) {
			x1 = this.trackMinX;
			y1 = this.trackMinY;
			x2 = this.trackMinX;
			y2 = this.trackMaxY;
		} else if (location == Location.RIGHT_OF) {
			x1 = this.trackMaxX;
			y1 = this.trackMinY;
			x2 = this.trackMaxX;
			y2 = this.trackMaxY;
		}
		Point[] points = new Point[] {new Point(x1, y1), new Point(x2, y2)};
		
		// Warp line
		if (this.warper != null) {
			points = warper.transform(points);
		}
		
		// Add line
		Polyline poly = new Polyline(lineThickness, color);
		for (int i = 0; i < points.length; i++) {
			poly.add(points[i]);
		}
		this.graphicPrimitives.add(poly);
	}
	
	
	/**
	 * Convert base pairs to pixel values.
	 * @param bp Base pairs
	 * @return Pixel equivalent
	 */
    public final int bpToPixel(final long bp) {
    	return (int) ((double) (bp - this.chromosomeStart) * this.scale);
    }
	
	
	// ====================================
	//  Methods in PlotElement interface
	// ====================================
	
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {

    	// Paint graphic primitives
    	for (GraphicPrimitive primitive : this.graphicPrimitives) {
    		canvas.add(primitive);
    	}
    	
    	// Paint labels
//    	int y = -this.PADDING;
//    	if (this.labels.size() > 0) {
//    		this.minY = -(this.PADDING + this.FONT_SIZE);
//    	}
//    	for (Label label : this.labels) {
//    		Text text = canvas.newText(label.textValue, label.x, y, 
//    		    this.FONT_SIZE, HorizontalAlignment.CENTERED, this.TEXT_COLOR);
//    		if (label.url != null) {
//    			text.setUrl(label.url);
//    		}
//    		if (this.orientation == Orientation.VERTICAL) {
//    		    DrawingCanvas tile = this.transposeText(canvas, text);
//    		    canvas.add(tile);
//    		} else {
//    		    canvas.add(text);
//    		}
//    		int textWidth = canvas.renderedWidth(label.textValue,
//    				this.FONT_SIZE);
//    		int startX = label.x - textWidth / 2;
//    		int endX = startX + textWidth;
//    		if (startX < this.minX) {
//    			this.minX = startX;
//    		}
//    		if (endX > this.maxX) {
//    			this.maxX = endX;
//    		}
//    	}
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
    	return new Point(this.trackMinX, this.trackMinY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
    	return new Point(this.trackMinX, this.trackMaxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
    	return new Point(this.trackMaxX, this.trackMinY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
    	return new Point(this.trackMaxX, this.trackMaxY);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public final int width() {
    	return this.maxX - this.minX;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public final int height() {
    	return this.maxY - this.minY;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public final Point topLeftPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public final void move(final int deltaX, final int deltaY) {
    	this.maxX += deltaX;
    	this.maxY += deltaY;
    	this.minX += deltaX;
    	this.minY += deltaY;
    	this.trackMinX += deltaX;
    	this.trackMinY += deltaY;
    	this.trackMaxX += deltaX;
    	this.trackMaxY += deltaY;
    	for (GraphicPrimitive p : this.graphicPrimitives) {
    		p.move(deltaX, deltaY);
    	}
    }
    
    // ==================================
    //     Private methods
    // ==================================
    
    
    /**
     * Create new polygon.
     * @param start Start location of polygon
     * in native units.
     * @param end End location of polygon in
     * native units.
     * @param color Color of polygon
     * @return A polygon
     */
    private Polygon newPolygon(final long start, final long end,
    		final Color color) {
    	assert start <= end;
    	
    	// Instantiate points
    	int startPix = this.bpToPixel(start);
    	int endPix = this.bpToPixel(end);
    	Point[] points = new Point[4];
    	if (this.orientation == Orientation.HORIZONTAL) {
	    	points[0] = new Point(startPix, 0);
	    	points[1] = new Point(endPix, 0);
	    	points[2] = new Point(endPix, this.featureThickness);
	    	points[3] = new Point(startPix, this.featureThickness);
    	} else if (this.orientation == Orientation.VERTICAL) {
    		points[0] = new Point(0, startPix);
	    	points[1] = new Point(0, endPix);
	    	points[2] = new Point(this.featureThickness, endPix);
	    	points[3] = new Point(this.featureThickness, startPix);
    	}
    	
    	// Warp points
    	if (this.warper != null) {
    		points = this.warper.transform(points);
    	}
    	
    	return new Polygon(points, color);
    }

    
    // ===================================
    //       Inner classes
    // ===================================
    
    /**
     * Plot label.
     *
     */
    static final class Label {
    	
    	// ===============================
    	//         Attributes
    	// ===============================
    	
    	/** X-coordinate of middle of label. */
    	private int x = -1;
    	
//    	/** Y-coordinate of label */
//    	private int y = -1;
    	
    	/** Text value of label. */
    	private String textValue = null;
    	
    	/** URL of label. */
    	private URL url = null;
    	
    	
    	// =========================
    	//       Getters/setters
    	// =========================
    	
    	
    	/**
    	 * Get text value of label.
    	 * @return Text value of label
    	 */
    	public String getTextValue() {
			return textValue;
		}


    	/**
    	 * Set text value of label.
    	 * @param textValue Text value of label
    	 */
		public void setTextValue(final String textValue) {
			this.textValue = textValue;
		}


		/**
		 * Get label URL.
		 * @return Label URL
		 */
		public URL getUrl() {
			return url;
		}


		/**
		 * Set label URL.
		 * @param url Label URL
		 */
		public void setUrl(final URL url) {
			this.url = url;
		}


		/**
		 * Get x-coordinate of label.
		 * @return X-coordinate of label
		 */
		public int getX() {
			return x;
		}


		/**
		 * Set x-coordinate of label.
		 * @param x X-coordinate of label
		 */
		public void setX(final int x) {
			this.x = x;
		}

//		/**
//		 * Get Y-coordinate.
//		 * @return Y-coordinate
//		 */
//		private int getY() {
//			return y;
//		}
//
//
//		/**
//		 * Set Y-coordinate.
//		 * @param y Y-coordinate
//		 */
//		private void setY(final int y) {
//			this.y = y;
//		}
		
    	// ===============================
    	//       Constructor
    	// ===============================


		/**
    	 * Constructor.
    	 * @param x X-coordinate of label middle
    	 * @param y Y-coordinate of label
    	 * @param textValue Label text value
    	 * @param url Label url
    	 */
    	public Label(final int x, final int y, final String textValue,
    			final URL url) {
    		this.x = x;
//    		this.y = y;
    		this.textValue = textValue;
    		this.url = url;
    	}
    }
}
