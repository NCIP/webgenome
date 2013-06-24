/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2006-10-28 17:09:47 $

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
import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.domain.Cytoband;
import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.GraphicPrimitive;
import org.rti.webcgh.graphics.primitive.Polygon;
import org.rti.webcgh.graphics.primitive.Polyline;
import org.rti.webcgh.graphics.util.
	ClassPathPropertiesFileRgbHexidecimalColorMapper;
import org.rti.webcgh.graphics.util.ColorMapper;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.webui.util.MouseOverStripe;
import org.rti.webcgh.webui.util.MouseOverStripes;


/**
 * Map of genome features.
 */
public class ChromosomeIdeogram implements PlotElement {
	
	
	// ======================
	//    Constants
	// ======================
	
	/** Thickness (i.e., stroke) in pixels of border around ideogram. */
	private static final int BORDER_THICKNESS = 1;
	
	/** Color of border around plot. */
	private static final Color BORDER_COLOR = Color.BLACK;
	
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
	
	/** Starting position of centromere in base pairs. */
	private final long centromereStart;
	
	/** Ending position of centromere in base pairs. */
	private final long centromereEnd;
	
	/** Scale of base pairs to pixels. */
	private final double scale;
	
	/** Orientation of graphically rendered map. */
	private final Orientation orientation;
	
	/** Minimum X-coordinate in rendered map. */
	private int minX = 0;
	
	/** Maximum X-coordinate in rendered map. */
	private int maxX = 0;
	
	/** Minimum Y-coordinate in rendered map. */
	private int minY = 0;
	
	/** Maximum Y-coordinate in rendered map. */
	private int maxY = 0;
	
	/**
	 * Pixel of the start of centromere.
	 * If orientation is horizontal, this is
	 * an x-coordinate.  If orientation is vertical,
	 * this is a y-coordinate.
	 */
	private final int centStartPix;
	
	/**
	 * Pixel of the end of centromere.
	 * If orientation is horizontal, this is
	 * an x-coordinate.  If orientation is vertical,
	 * this is a y-coordinate.
	 */
	private final int centEndPix;
	
	/**
	 * Pixel of midpoint of centromere.
	 * If orientation is horizontal, this is
	 * an x-coordinate.  If orientation is vertical,
	 * this is a y-coordinate.
	 */
	private final int centMidPix;
	
	/**
	 * Magnitude of slope of border line from
	 * the center of the centromere to either
	 * side if orientation is taken as
	 * horizontal.
	 */
	private final double centSlopeMagnitude;
	
	/**
	 * Container to hold generated graphical
	 * primitives prior to rendering.  The primitives
	 * include text, rectangles, and lines.
	 */
	private final List<GraphicPrimitive>
		graphicPrimitives = new ArrayList<GraphicPrimitive>();
	
	/** Maps stain names to colors. */
	private final ColorMapper colorMapper =
		new ClassPathPropertiesFileRgbHexidecimalColorMapper(
				"conf/color-mappings.properties");
	
	/** Mouseover stripes. */
	private final MouseOverStripes mouseOverStripes;
		
	
	// ===================================
	//       Getters/setters
	// ===================================
	
	
	/**
	 * Get scale of base pairs to pixels.
	 * @return Scale of base pairs to pixels.
	 */
	public final double getScale() {
		return this.scale;
	}
	
	
	/**
	 * Get mouseover stripes.
	 * @return Mouseover stripes.
	 */
	public final MouseOverStripes getMouseOverStripes() {
		return mouseOverStripes;
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
	 * @param centromereStart Centromere start point
	 * @param centromereEnd Centromere end point
	 * @param length Length of map in pixels. If horizontal, this is
	 * the width.  If vertical, this is the height.
	 * @param orientation Orientation
	 * @param cytobandThickness Thickness of features in map in pixels.
	 * If the orientation is
	 * horizontal, this is the height.  If the orientation
	 * is vertical, this is the width.
	 */
	public ChromosomeIdeogram(final long chromosomeStart,
			final long chromosomeEnd, final long centromereStart,
			final long centromereEnd, final int length,
			final Orientation orientation,
			final int cytobandThickness) {
		
		// Check args
		if (chromosomeStart > chromosomeEnd || chromosomeStart < 0
				|| chromosomeEnd < 0) {
			throw new IllegalArgumentException("Invalid chromosome end points");
		}
		if (centromereStart > centromereEnd || centromereStart < 0
				|| centromereEnd < 0 || centromereStart < chromosomeStart
				|| centromereEnd > chromosomeEnd) {
			throw new IllegalArgumentException("Invalid centromere boundaries");
		}
			
		this.chromosomeStart = chromosomeStart;
		this.centromereStart = centromereStart;
		this.centromereEnd = centromereEnd;
		this.length = length;
		this.scale = (double) length
			/ (chromosomeEnd - chromosomeStart);
		this.orientation = orientation;
		if (orientation == Orientation.HORIZONTAL) {
			this.minX = 0;
			this.minY = 0;
			this.maxX = length;
			this.maxY = cytobandThickness;
			this.addBorderLine(Location.ABOVE);
			this.addBorderLine(Location.BELOW);
		} else if (orientation == Orientation.VERTICAL) {
			this.minX = 0;
			this.minY = 0;
			this.maxX = cytobandThickness;
			this.maxY = length;
			this.addBorderLine(Location.LEFT_OF);
			this.addBorderLine(Location.RIGHT_OF);
		}
		this.centStartPix = this.bpToPixel(centromereStart);
		this.centEndPix = this.bpToPixel(centromereEnd);
		this.centMidPix = (this.centStartPix + this.centEndPix) / 2;
		this.centSlopeMagnitude = ((double) cytobandThickness / 2.0)
			/ (double) (this.centEndPix - this.centMidPix);
		this.mouseOverStripes = new MouseOverStripes(
				this.orientation, this.width(), this.height());
	}
	
	
	// =====================================
	//      Business methods
	// =====================================
	

	/**
	 * Add a cytoband to ideogram.
	 * @param cytoband A cytoband
	 */
	public final void add(final Cytoband cytoband) {
		if (this.orientation == Orientation.VERTICAL) {
			this.addVertical(cytoband);
		} else if (this.orientation == Orientation.HORIZONTAL) {
			this.addHorizontal(cytoband);
		}
	}
	
	
	/**
	 * Add cytoband when this has vertical orientation.
	 * @param cytoband A cytoband
	 */
	private void addVertical(final Cytoband cytoband) {
		
		// Calculate X-coordinate reference points and constants
		int strokeMid = (int) Math.ceil(BORDER_THICKNESS / 2.0);
		int leftX = this.minX + strokeMid + 1;
		int rightX = this.maxX - strokeMid;
		int midX = (leftX + rightX) / 2;
		
		
		// Instantiate new polygon
		Color c = this.colorMapper.getColor(cytoband.getStain());
		Polygon p = new Polygon(c);
		
		// Calculate top and bottom Y-coordinates
		int topY = this.bpToPixel(cytoband.getStart());
		int bottomY = this.bpToPixel(cytoband.getEnd());
		
		// Add mouseover stripe
		this.mouseOverStripes.add(new MouseOverStripe(topY, bottomY,
				cytoband.getName()));
		
		// Create top left point and add to polygon
		Point tl = new Point(leftX, topY);
		if (topY > this.centStartPix && topY < this.centEndPix) {
			tl.x += this.centromereCorrection(topY) + strokeMid - 2;
		}
		p.addPoint(tl);
		
		// Create bottom left point
		Point bl = new Point(leftX, bottomY);
		if (bottomY > this.centStartPix && bottomY < this.centEndPix) {
			bl.x += this.centromereCorrection(bottomY);
		}
		
		// If line between top and bottom left point enters
		// centromere, add inflection point
		if (topY < this.centStartPix && bottomY > this.centStartPix) {
			p.addPoint(leftX, this.centStartPix + strokeMid + 1);
		}
		
		// If line between top and bottom left points crosses
		// centromere middle, add inflection point
		if (topY < this.centMidPix && bottomY > this.centMidPix) {
			p.addPoint(midX, this.centMidPix);
		}
		
		// If line between top and bottom left points exits
		// centromere, add inflection point
		if (topY < this.centEndPix && bottomY > this.centEndPix) {
			p.addPoint(leftX, this.centEndPix);
		}
		
		// Add bottom left point
		p.addPoint(bl);
		
		// Create and add bottom right point
		Point br = new Point(rightX, bottomY);
		if (bottomY > this.centStartPix && bottomY < this.centEndPix) {
			br.x -= this.centromereCorrection(bottomY);
		}
		p.addPoint(br);
		
		// Create top right point
		Point tr = new Point(rightX, topY);
		if (topY > this.centStartPix && topY < this.centEndPix) {
			tr.x -= this.centromereCorrection(topY) + strokeMid;
		}
		
		// If line between bottom and top right points enters
		// centromere, add inflection point
		if (bottomY > this.centEndPix && topY < this.centEndPix) {
			p.addPoint(rightX, this.centEndPix);
		}
		
		// If line between bottom and top right points crosses
		// middle of centromere, add inflection point
		if (bottomY > this.centMidPix && topY < this.centMidPix) {
			p.addPoint(midX, this.centMidPix);
		}
		
		// If line between bottom and top right points exits
		// centromere, add inflection point
		if (bottomY > this.centStartPix && topY < this.centStartPix) {
			p.addPoint(rightX, this.centStartPix + strokeMid + 1);
		}
		
		// Add top right point
		p.addPoint(tr);
		
		// Add polygon
		this.graphicPrimitives.add(p);
	}
	
	
	/**
	 * This method is used to create an hourglass shape
	 * in an ideogram.
	 * Given value <code>val</code> is either an X or Y
	 * coordinate of some point on the ideogram
	 * border in the centromere region.
	 * If orientation is horizontal, value
	 * is taken to be an X-coordinate.  If orientation
	 * is vertical, value is taken to be a Y-coordinate.
	 * The method returns a correction that
	 * is either added to or subtracted from
	 * the opposite coordinate.
	 * @param val An X- or Y- coordinate of some point on
	 * the border of the ideogram in the centromere region.
	 * @return A correction that
	 * is either added to or subtracted from
	 * the opposite coordinate.
	 */
	private int centromereCorrection(final int val) {
		int correctedVal = -1;
		if (val < this.centMidPix) {
			correctedVal = (int) ((double) (val - this.centStartPix)
					* this.centSlopeMagnitude);
		} else {
			correctedVal = (int) ((double) (this.centEndPix - val)
					* this.centSlopeMagnitude);
		}
		return correctedVal;
	}
	
	
	/**
	 * Add cytoband when this has horizontal orientation.
	 * @param cytoband A cytoband
	 */
	private void addHorizontal(final Cytoband cytoband) {
		
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
    	return new Point(this.maxX, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
    	return new Point(this.maxX, this.maxY);
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
    	for (GraphicPrimitive p : this.graphicPrimitives) {
    		p.move(deltaX, deltaY);
    	}
    	this.mouseOverStripes.getOrigin().x += deltaX;
    	this.mouseOverStripes.getOrigin().y += deltaY;
    }
    
    // ==================================
    //     Private methods
    // ==================================
    
    
	/**
	 * Add a graphical border line on one side of ideogram.
	 * @param location Location of frame
	 */
	private void addBorderLine(final Location location) {	
		Polyline p = new Polyline(BORDER_THICKNESS, BORDER_COLOR);
		if (location == Location.ABOVE) {
			this.addTopBorder();
		} else if (location == Location.BELOW) {
			this.addBottomBorder();
		} else if (location == Location.LEFT_OF) {
			this.addLeftBorder();
		} else if (location == Location.RIGHT_OF) {
			this.addRightBorder();
		}
		this.graphicPrimitives.add(p);
	}
	
	
	/**
	 * Add border to top of ideogram.
	 */
	private void addTopBorder() {
		
	}
	
	
	/**
	 * Add border to bottom of ideogram.
	 */
	private void addBottomBorder() {
		
	}
	
	
	/**
	 * Add border to left of ideogram.
	 */
	private void addLeftBorder() {
		int leftX = this.minX;
		int centX = (this.maxX + this.minX) / 2;
		int centStartY = this.bpToPixel(this.centromereStart);
		int centEndY = this.bpToPixel(this.centromereEnd);
		int centMidY = (centStartY + centEndY) / 2;
		Polyline p = new Polyline(BORDER_THICKNESS, BORDER_COLOR);
		p.add(leftX, this.minY);
		p.add(leftX, centStartY);
		p.add(centX, centMidY);
		p.add(leftX, centEndY);
		p.add(leftX, this.maxY);
		this.graphicPrimitives.add(p);
	}
	
	
	/**
	 * Add border to right of ideogram.
	 */
	private void addRightBorder() {
		int rightX = this.maxX;
		int centX = (this.maxX + this.minX) / 2;
		int centStartY = this.bpToPixel(this.centromereStart);
		int centEndY = this.bpToPixel(this.centromereEnd);
		int centMidY = (centStartY + centEndY) / 2;
		Polyline p = new Polyline(BORDER_THICKNESS, BORDER_COLOR);
		p.add(rightX, this.minY);
		p.add(rightX, centStartY);
		p.add(centX, centMidY);
		p.add(rightX, centEndY);
		p.add(rightX, this.maxY);
		this.graphicPrimitives.add(p);
	}
}
