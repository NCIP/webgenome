/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.primitive.GraphicPrimitive;
import org.rti.webgenome.graphics.primitive.Rectangle;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.graphics.util.HeatMapColorFactory;
import org.rti.webgenome.units.HorizontalAlignment;

/**
 * Widget that indicates how values over a range
 * map to colors in <code>HeatMapPlot</code>s.
 * @author dhall
 *
 */
public class ColorScale implements PlotElement {
	
	//
	//       CONSTANTS
	//
	
	/** Padding between graphical elements in pixels. */
	private static final int PADDING = 5;
	
	/** Format for number text. */
	private static final NumberFormat NUMBER_FORMAT =
		new DecimalFormat("###,###.###");
	
	/** Font size. */
	private static final int FONT_SIZE = 12;
	
	/** Color of text. */
	private static final Color TEXT_COLOR = Color.BLACK;
	
	//
	//       ATTRIBUTES
	//
	
	/** Origin point. */
	private final Point origin = new Point(0, 0);
	
	/** Point with max X- and Y-coordinate. */
	private final Point maxPoint = new Point(0, 0);
	
	/** Starting X-coordinate of scale. */
	private int scaleStartX = 0;
	
	/** Ending X-coordinate of scale. */
	private int scaleEndX = 0;
	
	/** Graphic primitives. */
	private Collection<GraphicPrimitive> primitives =
		new ArrayList<GraphicPrimitive>();
	
	/** Color factory. */
	private final HeatMapColorFactory colorFactory;
	
	/** Drawing canvas. */
	private final DrawingCanvas canvas;
	
	
	//
	//       CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 * @param minValue Minimum value on scale
	 * @param maxValue Maximum value on scale
	 * @param scaleWidth Scale width in pixels
	 * @param scaleHeight Scale height in pixels
	 * @param numBins Number of color bins in scale
	 * @param canvas A drawing canvas
	 */
	public ColorScale(final float minValue, final float maxValue,
			final int scaleWidth, final int scaleHeight,
			final int numBins, final DrawingCanvas canvas) {
		this.canvas = canvas;
		this.colorFactory =
			new HeatMapColorFactory(minValue, maxValue, numBins);
		int binWidth = scaleWidth / numBins;
		this.layoutScale(minValue, maxValue, scaleWidth, scaleHeight,
				numBins, binWidth);
		this.layoutLabels(minValue, maxValue, scaleWidth,
				scaleHeight, binWidth);
	}
	
	
	/**
	 * Layout graphic scale.
	 * @param minValue Minimum value on scale
	 * @param maxValue Maximum value on scale
	 * @param scaleWidth Scale width in pixels
	 * @param scaleHeight Scale height in pixels
	 * @param numBins Number of color bins in scale
	 * @param binWidth Width of bins in pixels
	 */
	private void layoutScale(final float minValue, final float maxValue,
			final int scaleWidth, final int scaleHeight,
			final int numBins, final int binWidth) {
		
		// Lay out rectangles
		int x = this.origin.x;
		int y = this.origin.y;
		for (int i = 0; i < numBins; i++) {
			Rectangle r = new Rectangle(x, y, binWidth,
					scaleHeight, this.colorFactory.getBinColor(i));
			this.primitives.add(r);
			x += binWidth;
		}
		
		// Adjust attributes
		this.maxPoint.x = x;
		this.scaleEndX = x;
	}
	
	
	/**
	 * Layout text labels that give the values of the left
	 * and right ends.  Also, the zero point will be shown,
	 * if it is range.
	 * @param minValue Minimum value on scale
	 * @param maxValue Maximum value on scale
	 * @param scaleWidth Scale width in pixels
	 * @param scaleHeight Scale height in pixels
	 * @param binWidth Width of bins in pixels
	 */
	private void layoutLabels(final float minValue, final float maxValue,
			final int scaleWidth, final int scaleHeight,
			final int binWidth) {
		int minTextX = Integer.MAX_VALUE;
		int maxTextX = Integer.MIN_VALUE;
		int y = this.origin.y + scaleHeight + PADDING + FONT_SIZE;
		
		// Draw zero point
		if (minValue <= (float) 0.0 && maxValue >= (float) 0.0) {
			int binNum = this.colorFactory.binNumber((float) 0.0);
			int binStartPix = this.scaleStartX + binNum * binWidth;
			int zeroMidX = binStartPix + binWidth / 2;
			String text = NUMBER_FORMAT.format(0.0);
			int textWidth = this.canvas.renderedWidth(text, FONT_SIZE);
			int x = zeroMidX - textWidth / 2;
			Text textElmt = this.canvas.newText(text, x, y, FONT_SIZE,
					HorizontalAlignment.LEFT_JUSTIFIED, TEXT_COLOR);
			this.primitives.add(textElmt);
			minTextX = x;
			maxTextX = x + textWidth;
		}
		
		// Left end
		String text = NUMBER_FORMAT.format(minValue);
		int textWidth = this.canvas.renderedWidth(text, FONT_SIZE);
		int x = this.scaleStartX - textWidth / 2;
		int endX = x + textWidth;
		if ((endX + PADDING) > minTextX) {
			x -= ((endX + PADDING) - minTextX);
		}
		Text textElmt = this.canvas.newText(text, x, y, FONT_SIZE,
				HorizontalAlignment.LEFT_JUSTIFIED, TEXT_COLOR);
		this.primitives.add(textElmt);
		minTextX = x;
		
		// Right end
		text = NUMBER_FORMAT.format(maxValue);
		textWidth = this.canvas.renderedWidth(text, FONT_SIZE);
		x = this.scaleEndX - textWidth / 2;
		if ((x - PADDING) < maxTextX) {
			x += (maxTextX - (x - PADDING));
		}
		textElmt = this.canvas.newText(text, x, y, FONT_SIZE,
				HorizontalAlignment.LEFT_JUSTIFIED, TEXT_COLOR);
		this.primitives.add(textElmt);
		maxTextX = x + textWidth;
		
		// Adjust attributes
		this.maxPoint.x = maxTextX;
		this.maxPoint.y = y;
		Point origOrigin = new Point(this.origin);
		this.move(-minTextX, 0);
		this.origin.setLocation(origOrigin);
	}

	
	//
	//       PlotElement INTERFACE
	//
	
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {
    	for (GraphicPrimitive p : this.primitives) {
    		canvas.add(p);
    	}
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
    	return new Point(this.scaleStartX, this.origin.y);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
    	return new Point(this.scaleStartX, this.maxPoint.y);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
    	return new Point(this.scaleEndX, this.origin.y);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
    	return new Point(this.scaleEndX, this.maxPoint.y);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public final int width() {
    	return this.maxPoint.x - this.origin.x;
    }
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public final int height() {
    	return this.maxPoint.y - this.origin.y;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public final Point topLeftPoint() {
    	return this.origin;
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public final void move(final int deltaX,
    		final int deltaY) {
    	this.maxPoint.x += deltaX;
    	this.maxPoint.y += deltaY;
    	this.origin.x += deltaX;
    	this.origin.y += deltaY;
    	this.scaleStartX += deltaX;
    	this.scaleEndX += deltaX;
    	for (GraphicPrimitive p : this.primitives) {
    		p.move(deltaX, deltaY);
    	}
    }
}