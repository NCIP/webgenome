/*
$Revision: 1.1 $
$Date: 2006-09-05 14:06:44 $

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


package org.rti.webcgh.plot;

import java.awt.Color;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Polygon;
import org.rti.webcgh.drawing.Polyline;
import org.rti.webcgh.drawing.GraphicPrimitive;
import org.rti.webcgh.drawing.Text;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;

/**
 * Map of genome features.
 */
public class GenomeFeatureMap implements PlotElement {
	
	
	// ======================================
	//      Attributes
	// ======================================
	
	/** Starting position of map on chromosome. */
	private final long chromosomeStart;
	
	/** Width of map in pixels. */
	private final int width;
	
	/** Scale of base pairs to pixels. */
	private final double scale;
	
	/** Orientation of graphically rendered map. */
	private final Orientation orientation;
	
	/** Height of features in map. */
	private int featureHeight = 26;
	
	/** Size of font. */
	private int fontSize = 12;
	
	/** Color of text. */
	private Color textColor = Color.blue;
	
	/** Padding between graphical elements in pixels. */
	private int padding = 5;
	
	/**
	 * Warper that gives certain maps, like
	 * chromosome ideograms, an hourglass shape.
	 */
	private Warper warper = null;
	
	/** Thickness of lines in map. */
	private int lineThickness = 2;
	
	/** Minimum X-coordinate in rendered map. */
	private int minX = 0;
	
	/** Maximum X-coordinate in rendered map. */
	private int maxX = 0;
	
	/** Minimum Y-coordinate in rendered map. */
	private int minY = 0;
	
	/** Maximum Y-coordinate in rendered map. */
	private int maxY = this.featureHeight;
	
	/** Y-coordinate in vertical center of map. */
	private int middleY = this.maxY / 2;
	
	/**
	 * Container to hold generated graphical
	 * primitives prior to rendering.
	 */
	private final List<GraphicPrimitive>
		graphicPrimitives = new ArrayList<GraphicPrimitive>();
	
	/**
	 * Container to hold text labels
	 * prior to rendering.
	 */
	private final List<Label> labels = new ArrayList<Label>();
	
	
	// ===================================
	//       Getters/setters
	// ===================================
	
	/**
	 * Set height of plotted features.
	 * @param featureHeight Height of ploted features in
	 * pixels.
	 */
	public final void setFeatureHeight(final int featureHeight) {
		this.featureHeight = featureHeight;
		this.maxY = featureHeight;
		this.middleY = this.maxY / 2;
	}
	
	
	/**
	 * Set font size.
	 * @param fontSize Font size
	 */
	public final void setFontSize(final int fontSize) {
		this.fontSize = fontSize;
	}
	
	
	/**
	 * Set padding between graphic elements.
	 * @param padding Padding in pixels
	 */
	public final void setPadding(final int padding) {
		this.padding = padding;
	}
	
	
	/**
	 * Set color of text.
	 * @param textColor Color of text
	 */
	public final void setTextColor(final Color textColor) {
		this.textColor = textColor;
	}
	
	
	/**
	 * Set warper that gives map hourglass
	 * appearance.
	 * @param warper A warper
	 */
	public final void setWarper(final Warper warper) {
		this.warper = warper;
	}
	
	
	/**
	 * Set thickness of lines in plot.
	 * @param lineThickness Line thickness in pixels
	 */
	public final void setLineThickness(final int lineThickness) {
		this.lineThickness = lineThickness;
	}
	
	
	/**
	 * Get height of mapped features.
	 * @return Height of mapped features in pixels.
	 */
	public final int getFeatureHeight() {
		return featureHeight;
	}
	
	
	/**
	 * Get scale of base pairs to pixels.
	 * @return Scale of base pairs to pixels.
	 */
	public final double getScale() {
		return scale;
	}
	
	
	/**
	 * Get widht of rendered map.
	 * @return Width in pixels.
	 */
	public final int getWidth() {
		return width;
	}
	
	
	// ================================
	//     Constructors
	// ================================
	
	/**
	 * Constructor.
	 * @param chromosomeStart Chromosome start point
	 * @param chromosomeEnd Chromosome end point
	 * @param width Width in pixels
	 * @param orientation Orientation
	 */
	public GenomeFeatureMap(final long chromosomeStart,
			final long chromosomeEnd, final int width,
			final Orientation orientation) {
		this.chromosomeStart = chromosomeStart;
		this.width = width;
		this.maxX = width;
		this.scale = (double) width
			/ (chromosomeEnd - chromosomeStart);
		this.orientation = orientation;
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
		if (drawLabel) {
			int textX = this.bpToPixel((start + end) / 2);
			this.labels.add(new Label(textX, name, url));
		}
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
		int minX = this.bpToPixel(min);
		int maxX = this.bpToPixel(max);
		
		// Draw connecting lines
		this.graphicPrimitives.add(new Line(minX, this.middleY, maxX, 
			this.middleY, this.lineThickness, color));
		
		// Draw text
		if (drawLabel) {
			int textX = (minX + maxX) / 2;
			this.labels.add(new Label(textX, name, url));
		}
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
			x1 = 0;
			y1 = 0;
			x2 = this.width;
			y2 = 0;
		} else if (location == Location.BELOW) {
			x1 = 0;
			y1 = this.featureHeight;
			x2 = this.width;
			y2 = this.featureHeight;
		} else if (location == Location.LEFT_OF) {
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = this.maxY;
		} else if (location == Location.RIGHT_OF) {
			x1 = this.width;
			y1 = 0;
			x2 = this.width;
			y2 = this.maxY;
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
        if (this.orientation == Orientation.VERTICAL) {
            this.transposeGraphicPrimitives();
        }
        
    	// Paint graphic primitives
    	for (GraphicPrimitive primitive : this.graphicPrimitives) {
    		canvas.add(primitive);
    	}
    	
    	// Paint labels
    	int y = -this.padding;
    	if (this.labels.size() > 0) {
    		this.minY = -(this.padding + this.fontSize);
    	}
    	for (Label label : this.labels) {
    		Text text = canvas.newText(label.textValue, label.x, y, 
    		    this.fontSize, HorizontalAlignment.CENTERED, this.textColor);
    		if (label.url != null) {
    			text.setUrl(label.url);
    		}
    		if (this.orientation == Orientation.VERTICAL) {
    		    DrawingCanvas tile = this.transposeText(canvas, text);
    		    canvas.add(tile);
    		} else {
    		    canvas.add(text);
    		}
    		int textWidth = canvas.renderedWidth(label.textValue,
    				this.fontSize);
    		int startX = label.x - textWidth / 2;
    		int endX = startX + textWidth;
    		if (startX < this.minX) {
    			this.minX = startX;
    		}
    		if (endX > this.maxX) {
    			this.maxX = endX;
    		}
    	}
    	
    	if (this.orientation == Orientation.VERTICAL) {
    	    this.transposeMinAndMax();
    	}
    	
    	canvas.setOrigin(new Point(this.minX, this.minY));
    	canvas.setWidth(this.width());
    	canvas.setHeight(this.height());
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
    	return new Point(this.minX, this.maxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
    	return new Point(this.minX + this.width(), this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
    	return new Point(this.minX + this.width(), this.maxY);
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
    	int startX = this.bpToPixel(start);
    	int endX = this.bpToPixel(end);
    	Point[] points = new Point[4];
    	points[0] = new Point(startX, 0);
    	points[1] = new Point(endX, 0);
    	points[2] = new Point(endX, this.featureHeight);
    	points[3] = new Point(startX, this.featureHeight);
    	
    	// Warp points
    	if (this.warper != null) {
    		points = this.warper.transform(points);
    	}
    	
    	return new Polygon(points, color);
    }
    
    
    /**
     * Transpos graphic primitives from a horizontal
     * to vertical orientation of map.
     */
    private void transposeGraphicPrimitives() {
        for (GraphicPrimitive primitive : this.graphicPrimitives) {
        	if (primitive instanceof Line) {
        		Line line = (Line) primitive;
        		this.transpose(line);
        	} else if (primitive instanceof Polygon) {
        		Polygon poly = (Polygon) primitive;
        		this.transpose(poly);
        	} else if (primitive instanceof Polyline) {
        		Polyline poly = (Polyline) primitive;
        		this.transpose(poly);
        	}
        }
    }
    
    
    /**
     * Transpose given line from horizontal
     * to vertical orientation.
     * @param line A line
     */
    private void transpose(final Line line) {
    	int x0 = 0, y0 = 0;
    	
    	// First end point
    	x0 = line.getX1();
    	y0 = line.getY1();
    	line.setX1(this.featureHeight - y0);
    	line.setY1(x0);
    	
    	// Second end point
    	x0 = line.getX2();
    	y0 = line.getY2();
    	line.setX2(this.featureHeight - y0);
    	line.setY2(x0);
    }
    
    /**
     * Tranpost given polygon from horizontal
     * to vertical orientation.
     * @param polygon Polygon to transpost
     */
    private void transpose(final Polygon polygon) {
    	Point[] points = polygon.getPoints();
    	for (int i = 0; i < points.length; i++) {
    		this.transpose(points[i]);
    	}
    }
    
    /**
     * Transpost given line from horizontal to
     * vertical orientation.
     * @param line Line to transpose
     */
    private void transpose(final Polyline line) {
    	List<Point> points = line.getPoints();
    	for (Point point : points) {
    		this.transpose(point);
    	}
    }
    
    
    /**
     * Transpost given point from horizontal
     * to vertical orientation.
     * @param p Point to transpost
     */
    private void transpose(final Point p) {
    	int x0 = p.x;
    	int y0 = p.y;
    	p.x = this.featureHeight - y0;
    	p.y = x0;
    }
    
    
    /**
     * Transpost minimum and maximum coordinate
     * values from horizontal to vertical alignment.
     */
    private void transposeMinAndMax() {
        this.minY = this.minX;
        this.maxY = this.maxX;
        if (this.labels.size() > 0) {
        	this.minX = -(this.padding + this.fontSize);
        }
        this.maxX = this.featureHeight;
    }
    
    
    /**
     * Transpost text from horizontal to vertical
     * alignment.
     * @param canvas A drawing canvas
     * @param text Text to transpose
     * @return Drawing canvas containing transposted
     * text
     */
    private DrawingCanvas transposeText(final DrawingCanvas canvas,
    		final Text text) {
    	DrawingCanvas tile = canvas.newTile();
    	int newX = -this.padding;
    	int newY = this.width - (text.getX()
    			+ canvas.renderedWidth(text.getValue(), this.fontSize));
    	text.setX(newX);
    	text.setY(newY);
    	tile.add(text);
    	tile.rotate(270, newX, newY);
    	canvas.add(tile);
        return tile;
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

		
    	// ===============================
    	//       Constructor
    	// ===============================


		/**
    	 * Constructor.
    	 * @param x X-coordinate of label middle
    	 * @param textValue Label text value
    	 * @param url Label url
    	 */
    	public Label(final int x, final String textValue,
    			final URL url) {
    		this.x = x;
    		this.textValue = textValue;
    		this.url = url;
    	}
    }
}
