/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/GenomeFeatureMap.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
package org.rti.webcgh.graph;

import java.awt.Color;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicLine;
import org.rti.webcgh.drawing.GraphicPolygon;
import org.rti.webcgh.drawing.GraphicPolyline;
import org.rti.webcgh.drawing.GraphicPrimitive;
import org.rti.webcgh.drawing.GraphicText;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;

/**
 * Map of genome features
 */
public class GenomeFeatureMap implements PlotElement {
	
	
	// ======================================
	//      Attributes
	// ======================================
	
	private final long chromosomeStart;
	private final long chromosomeEnd;
	private final int width;
	private final double scale;
	private final Orientation orientation;
	
	private int featureHeight = 26;
	private int fontSize = 12;
	private Color textColor = Color.blue;
	private int padding = 5;
	private Warper warper = null;
	private int lineThickness = 2;
	
	private int minX = 0;
	private int maxX = 0;
	private int minY = 0;
	private int maxY = this.featureHeight;
	private int middleY = this.maxY / 2;
	private final List graphicPrimitives = new ArrayList();
	private final List labels = new ArrayList();
	
	
	/**
	 * @param featureHeight The featureHeight to set.
	 */
	public void setFeatureHeight(int featureHeight) {
		this.featureHeight = featureHeight;
		this.maxY = featureHeight;
		this.middleY = this.maxY / 2;
	}
	
	
	/**
	 * @param fontSize The fontSize to set.
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	
	/**
	 * @param padding The padding to set.
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	
	/**
	 * @param textColor The textColor to set.
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	
	/**
	 * @param warper The warper to set.
	 */
	public void setWarper(Warper warper) {
		this.warper = warper;
	}
	
	
	/**
	 * @param lineThickness The lineThickness to set.
	 */
	public void setLineThickness(int lineThickness) {
		this.lineThickness = lineThickness;
	}
	
	
	/**
	 * @return Returns the featureHeight.
	 */
	public int getFeatureHeight() {
		return featureHeight;
	}
	
	
	/**
	 * @return Returns the scale.
	 */
	public double getScale() {
		return scale;
	}
	
	
	/**
	 * @return Returns the width.
	 */
	public int getWidth() {
		return width;
	}
	
	
	// ================================
	//     Constructors
	// ================================
	
	/**
	 * Constructor
	 * @param chromosomeStart Chromosome start point
	 * @param chromosomeEnd Chromosome end point
	 * @param width Width in pixels
	 * @param orientation Orientation
	 */
	public GenomeFeatureMap(final long chromosomeStart,
		final long chromosomeEnd, final int width, Orientation orientation) {
		this.chromosomeStart = chromosomeStart;
		this.chromosomeEnd = chromosomeEnd;
		this.width = width;
		this.maxX = width;
		this.scale = (double)width / (chromosomeEnd - chromosomeStart);
		this.orientation = orientation;
	}
	
	
	// =====================================
	//      Public methods
	// =====================================
	
	/**
	 * Plot a feature
	 * @param start Chromosome start point of feature
	 * @param end Chromosome end point of feature
	 * @param name Feature name
	 * @param url URL to additional information about feature
	 * @param drawLabel Draw a label above feature
	 * @param color Feature color
	 */
	public void plotFeature(long start, long end, String name, URL url, 
		boolean drawLabel, Color color) {
		GraphicPolygon poly = this.newPolygon(start, end, color);
		poly.setToolTipText(name);
		this.graphicPrimitives.add(poly);
		if (drawLabel) {
			int textX = this.bpToPixel((start + end) / 2);
			this.labels.add(new Label(textX, name, url));
		}
	}
	
	
	/**
	 * Plot features
	 * @param starts Chromosome start points of feature
	 * @param ends Chromosome end points of feature
	 * @param name Feature name
	 * @param url URL to additional information about feature
	 * @param drawLabel Draw a label above feature
	 * @param color Feature color
	 */
	public void plotFeatures(long[] starts, long[] ends, String name, URL url,
		boolean drawLabel, Color color) {
		if (starts == null || ends == null || starts.length < 1 || ends.length < 1)
			throw new IllegalArgumentException(
				"Parameters 'starts' and 'ends' cannot be null or empty");
		if (starts.length != ends.length)
			throw new IllegalArgumentException(
				"Arrays 'starts' and 'ends' must be of same length");
		
		// Draw features
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for (int i = 0; i < starts.length && i < ends.length; i++) {
			if (starts[i] < min)
				min = starts[i];
			if (ends[i] > max)
				max = ends[i];
			GraphicPolygon poly = this.newPolygon(starts[i], ends[i], color);
			this.graphicPrimitives.add(poly);
		}
		int minX = this.bpToPixel(min);
		int maxX = this.bpToPixel(max);
		
		// Draw connecting lines
		this.graphicPrimitives.add(new GraphicLine(minX, this.middleY, maxX, 
			this.middleY, this.lineThickness, color));
		
		// Draw text
		if (drawLabel) {
			int textX = (minX + maxX) / 2;
			this.labels.add(new Label(textX, name, url));
		}
	}
	
	
	/**
	 * Add a frame
	 * @param location Location
	 * @param lineThickness Line thickness
	 * @param color Color
	 */
	public void addFrame(Location location, int lineThickness, Color color) {
		
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
		if (this.warper != null)
			points = warper.transform(points);
		
		// Add line
		GraphicPolyline poly = new GraphicPolyline(lineThickness, color);
		for (int i = 0; i < points.length; i++)
			poly.add(points[i]);
		this.graphicPrimitives.add(poly);
	}
	
	
	/**
	 * Convert base pairs to pixel values
	 * @param bp Base pairs
	 * @return Pixel
	 */
    public int bpToPixel(long bp) {
    	return (int)((double)(bp - this.chromosomeStart) * this.scale);
    }
	
	
	// ====================================
	//  Methods in PlotElement interface
	// ====================================
	
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
        if (this.orientation == Orientation.VERTICAL)
            this.transposeGraphicPrimitives();
    	
    	// Paint graphic primitives
    	for (Iterator it = this.graphicPrimitives.iterator(); it.hasNext();) {
    		GraphicPrimitive primitive = (GraphicPrimitive)it.next();
    		canvas.add(primitive);
    	}
    	
    	// Paint labels
    	int y = -this.padding;
    	if (this.labels.size() > 0)
    		this.minY = -(this.padding + this.fontSize);
    	for (Iterator it = this.labels.iterator(); it.hasNext();) {
    		Label label = (Label)it.next();
    		GraphicText text = canvas.newGraphicText(label.textValue, label.x, y, 
    		    this.fontSize, HorizontalAlignment.CENTERED, this.textColor);
    		if (label.url != null)
    			text.setUrl(label.url);
    		if (this.orientation == Orientation.VERTICAL) {
    		    DrawingCanvas tile = this.transposeText(canvas, text);
    		    canvas.add(tile);
    		} else
    		    canvas.add(text);
    		int textWidth = canvas.renderedWidth(label.textValue, this.fontSize);
    		int startX = label.x - textWidth / 2;
    		int endX = startX + textWidth;
    		if (startX < this.minX)
    			this.minX = startX;
    		if (endX > this.maxX)
    			this.maxX = endX;
    	}
    	
    	if (this.orientation == Orientation.VERTICAL)
    	    this.transposeMinAndMax();
    }
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
    	return new Point(0, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	return new Point(0, this.maxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	return new Point(this.width, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
    	return new Point(this.width, this.maxY);
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
    
    
    // ==================================
    //     Private methods
    // ==================================
    
    
    private GraphicPolygon newPolygon(long start, long end, Color color) {
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
    	if (this.warper != null)
    		points = this.warper.transform(points);
    	
    	return new GraphicPolygon(points, color);
    }
    
    
    private void transposeGraphicPrimitives() {
        for (Iterator it = this.graphicPrimitives.iterator(); it.hasNext();) {
        	GraphicPrimitive primitive = (GraphicPrimitive)it.next();
        	if (primitive instanceof GraphicLine) {
        		GraphicLine line = (GraphicLine)primitive;
        		this.transpose(line);
        	} else if (primitive instanceof GraphicPolygon) {
        		GraphicPolygon poly = (GraphicPolygon)primitive;
        		this.transpose(poly);
        	} else if (primitive instanceof GraphicPolyline) {
        		GraphicPolyline poly = (GraphicPolyline)primitive;
        		this.transpose(poly);
        	}
        }
    }
    
    
    private void transpose(GraphicLine line) {
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
    
    private void transpose(GraphicPolygon line) {
    	Point[] points = line.getPoints();
    	for (int i = 0; i < points.length; i++)
    		this.transpose(points[i]);
    }
    
    private void transpose(GraphicPolyline line) {
    	List points = line.getPoints();
    	for (Iterator it = points.iterator(); it.hasNext();) {
    		Point point = (Point)it.next();
    		this.transpose(point);
    	}
    }
    
    
    private void transpose(Point p) {
    	int x0 = p.x;
    	int y0 = p.y;
    	p.x = this.featureHeight - y0;
    	p.y = x0;
    }
    
    
    private void transposeMinAndMax() {
        this.minY = this.minX;
        this.maxY = this.maxX;
        if (this.labels.size() > 0)
        	this.minX = -(this.padding + this.fontSize);
        this.maxX = this.featureHeight;
    }
    
    
    private DrawingCanvas transposeText(DrawingCanvas canvas, GraphicText text) {
    	DrawingCanvas tile = canvas.newTile();
    	int newX = -this.padding;
    	int newY = this.width - 
			(text.getX() + canvas.renderedWidth(text.getValue(), this.fontSize));
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
     * Plot label
     *
     */
    static class Label {
    	
    	/**
    	 * X-coordinate of middle
    	 */
    	public int x;
    	
    	/**
    	 * Text value
    	 */
    	public String textValue;
    	
    	/**
    	 * URL
    	 */
    	public URL url;
    	
    	
    	// ===============================
    	//       Constructor
    	// ===============================
    	
    	/**
    	 * Constructor
    	 * @param x X-coordinate of label middle
    	 * @param textValue Label text value
    	 * @param url Label url
    	 */
    	public Label(int x, String textValue, URL url) {
    		this.x = x;
    		this.textValue = textValue;
    		this.url = url;
    	}
    }
}
