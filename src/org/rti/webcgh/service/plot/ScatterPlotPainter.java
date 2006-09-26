/*
$Revision: 1.7 $
$Date: 2006-09-26 21:10:37 $

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

package org.rti.webcgh.service.plot;

import java.util.Collection;
import java.util.List;

import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.graphics.PlotBoundaries;
import org.rti.webcgh.graphics.widget.Axis;
import org.rti.webcgh.graphics.widget.Caption;
import org.rti.webcgh.graphics.widget.Legend;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.graphics.widget.ScatterPlot;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;

/**
 * Manages the painting scatter plots by getting
 * data and assembling plot widgets.
 * @author dhall
 *
 */
public class ScatterPlotPainter extends PlotPainter {
	

	
	// ===============================
	//    Constructors
	// ===============================
	
	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	public ScatterPlotPainter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		super(chromosomeArrayDataGetter);
	}
	
	// =========================
	//    Business methods
	// =========================
    
    /**
     * Paints a scatter plot on the given drawing canvas.
     * @param panel Plot panel to add the scatter plot to
     * @param experiments Experiments to plot
     * @param plotParameters Plotting parameters specified
     * by user
     * @param width Width of plot in pixels
     * @param height Height of plot in pixels
     * @param quantitationType Quantitation type
     * interval in base pairs
     */
    public final void paintScatterPlot(
    		final PlotPanel panel,
    		final Collection<Experiment> experiments,
            final ScatterPlotParameters plotParameters,
            final int width, final int height,
            final QuantitationType quantitationType) {
        
        // Check args
        if (experiments == null || panel == null) {
            throw new IllegalArgumentException(
                    "Experiments and panel cannot be null");
        }
        if (plotParameters.getGenomeIntervals() == null
        		|| plotParameters.getGenomeIntervals().size() < 1) {
        	throw new IllegalArgumentException(
        			"No genome intervals specified");
        }
        if (!Float.isNaN(plotParameters.getMinY())
                && !Float.isNaN(plotParameters.getMaxY())
                && plotParameters.getMinY() > plotParameters.getMaxY()) {
            throw new IllegalArgumentException("Invalid plot range: "
                    + plotParameters.getMinY() + " - "
                    + plotParameters.getMaxY());
        }
        
        // Paint plot
        ScatterPlotSizer sizer =
        	new ScatterPlotSizer(plotParameters, width, height);
		int plotCount = 0;
		int rowCount = 1;
		PlotPanel row = panel.newChildPlotPanel();
		for (GenomeInterval gi : plotParameters.getGenomeIntervals()) {
			if (plotCount++ >=  plotParameters.getNumPlotsPerRow()) {
				VerticalAlignment va = null;
				if (rowCount++ == 1) {
					va = VerticalAlignment.TOP_JUSTIFIED;
				} else {
					va = VerticalAlignment.BELOW;
				}
				panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, va);
				row = panel.newChildPlotPanel();
				plotCount = 1;
			}
			row.setName("row");
	        PlotBoundaries pb = new PlotBoundaries(
	                (double) gi.getStartLocation(),
	                (double) plotParameters.getMinY(),
	                (double) gi.getEndLocation(),
	                (double) plotParameters.getMaxY());
	        ScatterPlot scatterPlot =
	            new ScatterPlot(experiments, gi.getChromosome(),
	            		this.getChromosomeArrayDataGetter(), sizer.width(gi),
	            		sizer.height(), pb);
	        
	        // Y-axis
	        if (plotCount == 1) {
		        Axis yAxis = new Axis(plotParameters.getMinY(),
		                plotParameters.getMaxY(), scatterPlot.height(),
		                Orientation.VERTICAL, Location.LEFT_OF,
		                panel.getDrawingCanvas());
		        Caption yCaption = new Caption(
		                quantitationType.getName(),
		                Orientation.HORIZONTAL, true, panel.getDrawingCanvas());
		        row.add(yAxis, HorizontalAlignment.LEFT_JUSTIFIED,
		                VerticalAlignment.BOTTOM_JUSTIFIED);
		        row.add(yCaption, HorizontalAlignment.LEFT_OF,
		                VerticalAlignment.CENTERED);
	        }
	        
	        PlotPanel col = row.newChildPlotPanel();
	        
	        // Add scatter plot
	        col.add(scatterPlot, true);
	        
	        // X-axis
	        Axis xAxis = new Axis(gi.getStartLocation(),
	                gi.getEndLocation(), scatterPlot.width(),
	                Orientation.HORIZONTAL, Location.BELOW,
	                col.getDrawingCanvas());
	        String captionText = "Chromosome " + gi.getChromosome()
	            + " (" + plotParameters.getUnits().getName() + ")";
	        Caption xCaption = new Caption(captionText,
	                Orientation.HORIZONTAL, false, panel.getDrawingCanvas());
	        col.add(xAxis, HorizontalAlignment.LEFT_JUSTIFIED,
	        		VerticalAlignment.BOTTOM_JUSTIFIED);
	        col.add(xCaption, HorizontalAlignment.CENTERED,
	                VerticalAlignment.BELOW);
	        
	        
	        row.add(col, HorizontalAlignment.RIGHT_OF,
	        		VerticalAlignment.BOTTOM_JUSTIFIED);
		}
		
		// Add final row
		VerticalAlignment va = null;
		if (rowCount == 1) {
			va = VerticalAlignment.TOP_JUSTIFIED;
		} else {
			va = VerticalAlignment.BELOW;
		}
		panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, va);
		
		// Legend
        Legend legend = new Legend(experiments, width);
        panel.add(legend, HorizontalAlignment.CENTERED,
                VerticalAlignment.BELOW);
    }
    
    
    /**
     * Class that is responsible for calculating
     * the width and height of scatter plot widgets.
     * The constructor takes parameters <code>width</code> and
     * <code>height</code>.  When there is only a single
     * genome interval to plot, the dimensions of the
     * single scatter plot widget are equal to these parameters.
     * For multiple genome intervals, the sum of widths and
     * heights of the individual scatter plot widgets are
     * less than or equal to these parameters, respectively.
     */
    static final class ScatterPlotSizer {
    	
    	/** Scale of native units to pixels. */
    	private final double scale;
    	
    	/** Height of all plots in pixels. */
    	private final int height;
    	
    	/**
    	 * Constructor.
    	 * @param params Plot parameters
    	 * @param totalWidth Total width of all plots in pixels
    	 * @param totalHeight Total height of all plots in pixels
    	 */
    	public ScatterPlotSizer(final ScatterPlotParameters params,
    			final int totalWidth, final int totalHeight) {
    		List<GenomeInterval> intervals = params.getGenomeIntervals();
    		int numRows = (int) Math.ceil((double) intervals.size()
    				/ (double) params.getNumPlotsPerRow());
    		this.height = totalHeight / numRows;
    		long longestInterval = 0;
    		for (GenomeInterval interval : intervals) {
    			long candidateLongest = interval.getEndLocation()
    				- interval.getStartLocation();
    			if (candidateLongest > longestInterval) {
    				longestInterval = candidateLongest;
    			}
    		}
    		this.scale = (double) totalWidth / (double) longestInterval;
    	}
    	
    	
    	/**
    	 * Get width for plot.
    	 * @param interval Genome interval
    	 * @return Width in pixels
    	 */
    	public int width(final GenomeInterval interval) {
    		return (int) (this.scale * (interval.getEndLocation()
    				- interval.getStartLocation()));
    	}
    	
    	/**
    	 * Get height for plot.
    	 * @return Height in pixels.
    	 */
    	public int height() {
    		return this.height;
    	}
    }
}
