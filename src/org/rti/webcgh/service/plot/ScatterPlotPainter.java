/*
$Revision: 1.2 $
$Date: 2006-09-07 18:54:53 $

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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Experiment;
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
public class ScatterPlotPainter {
	
	// ==============================
	//      Attributes
	// ==============================
	
	/** Chromosome array data getter. */
	private final ChromosomeArrayDataGetter chromosomeArrayDataGetter;
	
	// ===============================
	//    Constructors
	// ===============================
	
	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	public ScatterPlotPainter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		this.chromosomeArrayDataGetter = chromosomeArrayDataGetter;
	}
    
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
        if (plotParameters.getChromosome() < 1
                || plotParameters.getStartLocation() < 0
                || plotParameters.getEndLocation() < 0
                || plotParameters.getEndLocation()
                    < plotParameters.getStartLocation()) {
            throw new IllegalArgumentException("Invalid genome interval: "
                    + plotParameters.getChromosome() + ":"
                    + plotParameters.getStartLocation() + "-"
                    + plotParameters.getEndLocation());
        }
        if (!Float.isNaN(plotParameters.getMinY())
                && !Float.isNaN(plotParameters.getMaxY())
                && plotParameters.getMinY() > plotParameters.getMaxY()) {
            throw new IllegalArgumentException("Invalid plot range: "
                    + plotParameters.getMinY() + " - "
                    + plotParameters.getMaxY());
        }
        
        // Paint plot
        List<ChromosomeArrayData> cads = new ArrayList<ChromosomeArrayData>();
        List<String> names = new ArrayList<String>();
        List<Color> colors = new ArrayList<Color>();
        for (Experiment experiment : experiments) {
            for (BioAssay ba : experiment.getBioAssays()) {
                ChromosomeArrayData cad =
                	this.chromosomeArrayDataGetter.getChromosomeArrayData(
                			ba, plotParameters.getChromosome());
                cads.add(cad);
                names.add(ba.getName());
                colors.add(ba.getColor());
            }
        }
        PlotBoundaries pb = new PlotBoundaries(
                (double) plotParameters.getStartLocation(),
                (double) plotParameters.getMinY(),
                (double) plotParameters.getEndLocation(),
                (double) plotParameters.getMaxY());
        ScatterPlot scatterPlot =
            new ScatterPlot(cads, names, colors, width, height, pb);
        panel.add(scatterPlot);
        
        // X-axis
        Axis xAxis = new Axis(plotParameters.getStartLocation(),
                plotParameters.getEndLocation(), scatterPlot.width(),
                Orientation.HORIZONTAL, Location.BELOW);
        String captionText = "Chromosome " + plotParameters.getChromosome()
            + " (" + plotParameters.getUnits().getName() + ")";
        Caption xCaption = new Caption(captionText,
                Orientation.HORIZONTAL, false);
        panel.add(xAxis, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.BOTTOM_JUSTIFIED, true);
        panel.add(xCaption, HorizontalAlignment.CENTERED,
                VerticalAlignment.BELOW);
        
        // Y-axis
        Axis yAxis = new Axis(plotParameters.getMinY(),
                plotParameters.getMaxY(), scatterPlot.height(),
                Orientation.VERTICAL, Location.LEFT_OF);
        Caption yCaption = new Caption(
                quantitationType.getName(),
                Orientation.HORIZONTAL, true);
        panel.add(yAxis, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.BOTTOM_JUSTIFIED);
        panel.add(yCaption, HorizontalAlignment.LEFT_OF,
                VerticalAlignment.CENTERED);
        
        // Legend
        Legend legend = new Legend(experiments, scatterPlot.width());
        panel.add(legend, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.BELOW);
    }
}
