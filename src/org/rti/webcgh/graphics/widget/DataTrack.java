/*
$Revision: 1.2 $
$Date: 2007-03-01 20:09:40 $

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
import java.util.Collection;
import java.util.List;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.GraphicPrimitive;
import org.rti.webcgh.graphics.primitive.Rectangle;
import org.rti.webcgh.graphics.primitive.Text;
import org.rti.webcgh.graphics.util.HeatMapColorFactory;
import org.rti.webcgh.units.HorizontalAlignment;

/**
 * This class plots data in a horizontal "track" where
 * the X-coordinate corresponds to chromosomal location
 * and the color to the experimental measurement (e.g. copy number).
 * @author dhall
 * @see org.rti.webcgh.graphics.util.HeatMapColorFactory
 */
public class DataTrack implements PlotElement {

	//
	//     STATICS
	//
	
	/** Height of overall track in pixels. */
	private static final int TRACK_HEIGHT = 30;
	
	/**
	 * Height of colored bar that gives experimental
	 * measurements.  Height is in units of pixels.
	 */
	private static final int BAR_HEIGHT = 15;
	
	/** Size of font for track label. */
	private static final int FONT_SIZE = 11;
	
	/** Color of track label text. */
	private static final Color TEXT_COLOR = Color.BLACK;
	
	/** Padding around graphic elements in pixels. */
	private static final int PADDING = 10;
	
	/**
	 * Number of distinct color "bins" that
	 * experimental measurements will be mapped to.
	 */
	private static final int NUM_BINS = 16;
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Graphic primitives that are instantiated by the
	 * constructor and later rendered.
	 */
	private final Collection<GraphicPrimitive> graphicPrimitives =
		new ArrayList<GraphicPrimitive>();
	
	/** X-coordinate of origin of plot in pixels. */
	private int minX = 0;
	
	/** Y-coordinate of origin of plot in pixels. */
	private int minY = 0;
	
	/** Maximum X-coordinate in plot. */
	private int maxX = 0;
	
	/**
	 * X-coordinate of the beginning of the data area, which
	 * is immediately right of the track label.
	 */
	private int trackStartX = 0;
	
	/** Scale of pixels to base pairs. */
	private final double scale;
	
	/**
	 * Start location of track relative to chromosome
	 * in base pair units.
	 */
	private final long startLoc;
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 * @param chromosomeArrayData Data to plot
	 * @param startLoc Start location of plot on chromosome
	 * in base pair units
	 * @param endLoc End location of plot on chromosome
	 * in base pair units
	 * @param minSaturation Minimum saturation value below which
	 * the corresponding color will be pure green
	 * @param maxSaturation Maximum saturation value above which
	 * the corresponding color will be pure red
	 * @param trackWidth Width of data portion of track
	 * (i.e. not including the track label) in pixels
	 * @param trackLabel Track label
	 * @param canvas Canvas that the track will be rendered on
	 */
	public DataTrack(final ChromosomeArrayData chromosomeArrayData,
			final long startLoc,
			final long endLoc, final float minSaturation,
			final float maxSaturation, final int trackWidth,
			final String trackLabel,
			final DrawingCanvas canvas) {
		
		// Set attributes
		this.trackStartX = this.minX + PADDING
			+ canvas.renderedWidth(trackLabel, FONT_SIZE);
		this.maxX = this.trackStartX + trackWidth;
		this.scale = (double) trackWidth / (double) (endLoc - startLoc);
		this.startLoc = startLoc;
		
		// Add track label
		int textY = (this.minY + TRACK_HEIGHT) / 2 + FONT_SIZE / 2;
		Text text = canvas.newText(trackLabel, this.minX,
				textY, FONT_SIZE, HorizontalAlignment.LEFT_JUSTIFIED,
				TEXT_COLOR);
		this.graphicPrimitives.add(text);
		
		// Add colored rectangles
		HeatMapColorFactory colorFac = new HeatMapColorFactory(
				minSaturation, maxSaturation, NUM_BINS);
		List<ArrayDatum> arrayData = chromosomeArrayData.getArrayData();
		int y = this.minY + TRACK_HEIGHT / 2 - BAR_HEIGHT / 2;
		for (int i = 0; i < arrayData.size(); i++) {
			ArrayDatum datum = arrayData.get(i);
			Reporter r = datum.getReporter();
			int startX = this.toPixels(r.getLocation());
			int endX = startX;
			if (i > 0) {
				ArrayDatum leftDatum = arrayData.get(i - 1);
				Reporter leftRep = leftDatum.getReporter();
				long midPoint = (r.getLocation() + leftRep.getLocation())
					/ 2;
				startX = this.toPixels(midPoint);
			}
			if (i < arrayData.size() - 1) {
				ArrayDatum rightDatum = arrayData.get(i + 1);
				Reporter rightRep = rightDatum.getReporter();
				long midPoint = (r.getLocation() + rightRep.getLocation())
					/ 2;
				endX = this.toPixels(midPoint);
			}
			if (startX < this.maxX && endX > this.trackStartX) {
				if (startX < this.trackStartX) {
					startX = this.trackStartX;
				}
				if (endX > this.maxX) {
					endX = this.maxX;
				}
				Color intensity = colorFac.getColor(datum.getValue());
				Rectangle rect = new Rectangle(startX, y,
						endX - startX, BAR_HEIGHT, intensity);
				this.graphicPrimitives.add(rect);
			}
		}
	}
	
	
	/**
	 * Convert from base pair units to X-coordinate pixel relative
	 * to the left side of the plot.
	 * @param value Value to convert
	 * @return Pixel equivalent
	 */
	private int toPixels(final long value) {
		return this.trackStartX + (int)
		(((double) value - this.startLoc) * this.scale);
	}
	
	
	//
	//     PlotElement INTERFACE
	//
	
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {
    	for (GraphicPrimitive prim : this.graphicPrimitives) {
    		canvas.add(prim);
    	}
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
    	return new Point(this.trackStartX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
    	return new Point(this.trackStartX, this.maxY());
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
    	return new Point(this.maxX, this.maxY());
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
    	return TRACK_HEIGHT;
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
    	this.minX += deltaX;
    	this.minY += deltaY;
    	this.trackStartX += deltaX;
    	for (GraphicPrimitive prim : this.graphicPrimitives) {
    		prim.move(deltaX, deltaY);
    	}
    }
    
    /**
     * Get maximum Y-coordinate.
     * @return Maximum Y-coordinate in pixels
     */
    private int maxY() {
    	return this.minY + TRACK_HEIGHT;
    }
}
