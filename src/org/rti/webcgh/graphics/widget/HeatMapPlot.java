/*
$Revision: 1.2 $
$Date: 2006-09-15 21:21:01 $

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

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.Rectangle;
import org.rti.webcgh.graphics.primitive.Text;
import org.rti.webcgh.graphics.util.HeatMapColorFactory;
import org.rti.webcgh.service.plot.IdeogramPlotParameters;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.units.ChromosomeIdeogramSize;
import org.rti.webcgh.units.HorizontalAlignment;


/**
 * One dimensional plot of data.  The first
 * dimension corresponds to chromosme location.
 * Values of some <code>QuantitationType</code>
 * are given as colors.  Low values
 * map to hues of green.  High values map to
 * hues of red.  Intermediate values map to
 * hues of blue.
 * @see org.rti.webcgh.graphics.util.HapMapColorFactory
 * @author dhall
 *
 */
public final class HeatMapPlot implements PlotElement {
	
	// =========================
	//     Constants
	// =========================
	
	/** Padding between plot features. */
	private static final int PADDING = 5;
	
	/** Font size. */
	private static final int FONT_SIZE = 10;
	
	/** Color of text. */
	private static final Color TEXT_COLOR = Color.BLACK;

	// ============================
	//        Attributes
	// ============================
	
	/** Experiments containing data to plot. */
	private final Collection<Experiment> experiments;
	
	/** Factory for generating colors. */
    private final HeatMapColorFactory colorFactory;
    
    /** Plot parameters supplied by user. */
    private final IdeogramPlotParameters plotParameters;
    
    /**
     * Gets chromosome array data transparently regardless
     * of data location (i.e., in memory or on disk).
     */
    private final ChromosomeArrayDataGetter chromosomeArrayDataGetter;
    
//    /** Number formatter for generating mouseover text. */
//    private static final NumberFormat FORMAT =
//    	new DecimalFormat("###.###");
    
    /** Maximum X-coordinate in plot. */
    private int maxX = 0;
    
    /** Maximum Y-coordinate in plot. */
    private int maxY = 0;
    
    /** Minimum X-coordinate in plot. */
    private int minX = 0;
    
    /** Minimum Y-coordinate in plot. */
    private int minY = 0;
    
    /** Y-coordinate of tops of tracks. */
    private int trackMinY = 0;
  
    
    // ===============================
    //       Constructors
    // ===============================
    
    /**
     * Constructor.
     * @param experiments Experiments containg data to plot
     * @param colorFactory Maps data values to colors
     * @param plotParameters Plot parameters supplied through
     * user input
     * @param chromosomeArrayDataGetter Gets chromosome array data
     * from bioassays transparently without regard to where
     * data reside
     * @param drawingCanvas A drawing canvas
     */
	public HeatMapPlot(final Collection<Experiment> experiments,
			final HeatMapColorFactory colorFactory,
			final IdeogramPlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter,
			final DrawingCanvas drawingCanvas) {
		super();
		
		// Make sure args okay
		if (experiments == null) {
			throw new IllegalArgumentException("Experiments cannot be null");
		}
		
		// Set attributes
		this.experiments = experiments;
		this.colorFactory = colorFactory;
		this.plotParameters = plotParameters;
		this.chromosomeArrayDataGetter = chromosomeArrayDataGetter;
		
		// Adjust reference coordinates
		int longestString = 0;
		for (Experiment exp : experiments) {
			for (BioAssay ba : exp.getBioAssays()) {
				int candidateLongest = drawingCanvas.renderedWidth(
						ba.getName(), FONT_SIZE);
				if (candidateLongest > longestString) {
					longestString = candidateLongest;
				}
			}
		}
		this.trackMinY = PADDING + longestString;
		this.maxX = (experiments.size() * 2 - 1)
			* plotParameters.getTrackWidth();
		this.maxY = this.trackMinY
			+ plotParameters.getIdeogramSize().pixels(
				Experiment.inferredChromosomeSize(experiments,
						plotParameters.getChromosome()));
	}
	
	
	// ================================
	//       Plot element interface
	// ================================
	
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public void paint(final DrawingCanvas canvas) {
    	int x = this.minX;
    	for (Experiment exp : this.experiments) {
    		for (BioAssay ba : exp.getBioAssays()) {
    			ChromosomeArrayData cad =
    				this.chromosomeArrayDataGetter.getChromosomeArrayData(
    						ba, this.plotParameters.getChromosome());
    			this.paintTrack(canvas, x, cad);
    			this.paintTrackLabel(canvas, x, ba.getName());
    			x += 2 * this.plotParameters.getTrackWidth();
    		}
    	}
    }
    
    
    /**
     * Paint a single data track.
     * @param canvas Canvas
     * @param x X-coordinate of track
     * @param chromosomeArrayData Data to paint
     */
    private void paintTrack(final DrawingCanvas canvas, final int x,
    		final ChromosomeArrayData chromosomeArrayData) {
    	List<ArrayDatum> dataList =
    		chromosomeArrayData.getArrayData();
    	int n = dataList.size();
    	ChromosomeIdeogramSize idSize =
			this.plotParameters.getIdeogramSize();
    	int trackWidth = this.plotParameters.getTrackWidth();
    	if (n > 0) {
	    	long chromEnd = dataList.get(n - 1).getReporter().getLocation();
	    	for (int i = 0; i < n; i++) {
	    		ArrayDatum datum = dataList.get(i);
	    		float value = datum.getValue();
	    		if (value <= this.plotParameters.getMinMask()
	    				|| value >= this.plotParameters.getMaxMask()) {
		    		long start = 0;
		    		long middle = datum.getReporter().getLocation();
		    		if (i > 0) {
		    			start = (dataList.get(i - 1).getReporter().
		    				getLocation() + middle) / 2;
		    		}
		    		long end = chromEnd;
		    		if (i < n - 1) {
		    			end = (middle
		    				+ dataList.get(i + 1).getReporter().getLocation());
		    		}
		    		int y = this.trackMinY + idSize.pixels(start);
		    		int height = this.trackMinY + idSize.pixels(end) - y;
		    		Color c = this.colorFactory.getColor(value);
		    		canvas.add(new Rectangle(x, y, trackWidth, height, c));
	    		}
	    	}
    	}
    }
    
    
    /**
     * Paint label over data track.
     * @param canvas Drawing canvas
     * @param x X-coordinate of track
     * @param label Label to paint
     */
    private void paintTrackLabel(final DrawingCanvas canvas,
    		final int x, final String label) {
    	int textX = x + this.plotParameters.getTrackWidth() / 2;
    	int y = this.trackMinY - PADDING
    		- canvas.renderedWidth(label, FONT_SIZE) / 2;
    	Text text = canvas.newText(label, textX, y, FONT_SIZE,
    			HorizontalAlignment.CENTERED, TEXT_COLOR);
    	text.setRotation(1.5 * Math.PI);
    	canvas.add(text);
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
    	return new Point(this.minX, this.trackMinY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
    	return new Point(this.minX, this.maxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public Point topRightAlignmentPoint() {
    	return new Point(this.maxX, this.trackMinY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
    	return new Point(this.maxX, this.maxY);
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
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(final int deltaX, final int deltaY) {
    	this.minX += deltaX;
    	this.maxX += deltaX;
    	this.minY += deltaY;
    	this.maxY += deltaY;
    	this.trackMinY += deltaY;
    }
}
