/*
$Revision: 1.17 $
$Date: 2006-12-05 03:19:59 $

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
import java.util.Collection;

import org.rti.webcgh.domain.Cytoband;
import org.rti.webcgh.domain.CytologicalMap;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.graphics.util.HeatMapColorFactory;
import org.rti.webcgh.graphics.widget.Caption;
import org.rti.webcgh.graphics.widget.ChromosomeEndCap;
import org.rti.webcgh.graphics.widget.ChromosomeIdeogram;
import org.rti.webcgh.graphics.widget.ColorScale;
import org.rti.webcgh.graphics.widget.HeatMapPlot;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.service.dao.CytologicalMapDao;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.units.ChromosomeIdeogramSize;
import org.rti.webcgh.units.Direction;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;
import org.rti.webcgh.webui.util.EventHandlerGraphicBoundaries;


/**
 * Generates ideogram plots by assembling and laying out
 * the necessary widgets.
 * @author dhall
 */
public class IdeogramPlotPainter extends PlotPainter {
	
	// ======================
	//    Constants
	// ======================
	
	/** Color of line that frames ideogram. */
	private static final Color FRAME_LINE_COLOR = Color.BLACK;
	
	/** Number of color bins. */
	private static final int NUM_BINS = 16;
	
	/** Width of color scale in pixels. */
	private static final int COLOR_SCALE_WIDTH = 250;
	
	/** Height of color scale in pixels. */
	private static final int COLOR_SCALE_HEIGHT = 15;
	
	/** Number of color bins in color scale. */
	private static final int COLOR_SCALE_NUM_BINS = 16;
	
	
	// ======================
	//       Attributes
	// ======================
	
	/** Cytologial map data access object.  Should be injected. */
	private CytologicalMapDao cytologicalMapDao = null;
	
	
	// ========================
	//     Getters/setters
	// ========================
	
	/**
	 * Get cytological map data access object.
	 * @return Cytological map data access object.
	 */
	public final CytologicalMapDao getCytologicalMapDao() {
		return cytologicalMapDao;
	}


	/**
	 * Set cytological map data access object.
	 * This method should be used to inject this property
	 * after instantiation.
	 * @param cytologicalMapDao Cytological map data access object.
	 */
	public final void setCytologicalMapDao(
			final CytologicalMapDao cytologicalMapDao) {
		this.cytologicalMapDao = cytologicalMapDao;
	}


	
	
	// ========================
	//     Constructors
	// ========================
	
	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome array
	 * data getter
	 */
	public IdeogramPlotPainter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		super(chromosomeArrayDataGetter);
	}
	
	
	// =========================
	//    Business methods
	// =========================
	
    /**
     * Paints a plot on the given plot panel.
     * @param panel Plot panel to add the scatter plot to
     * @param experiments Experiments to plot
     * @param params Plotting parameters specified
     * by user
     * @return Boundaries of event handler regions
     */
    public final EventHandlerGraphicBoundaries  paintPlot(final PlotPanel panel,
    		final Collection<Experiment> experiments,
    		final PlotParameters params) {
		
		// Make sure arguments okay
		if (experiments == null || experiments.size() < 1) {
			throw new IllegalArgumentException("Experiments must be > 1");
		}
		if (!(params instanceof IdeogramPlotParameters)) {
			throw new IllegalArgumentException(
					"Expecting plot parameters of type IdeogramPlotParameters");
		}
		IdeogramPlotParameters plotParameters = (IdeogramPlotParameters) params;
		EventHandlerGraphicBoundaries evtHandlerBoundaries =
			new EventHandlerGraphicBoundaries();
		
		// Get organism
		Organism org = experiments.iterator().next().getOrganism();
		for (Experiment exp : experiments) {
			if (exp.getOrganism() != org) {
				throw new IllegalArgumentException(
						"Can only plot one organism as a time");
			}
		}
		
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
				ColorScale scale = new ColorScale(
						plotParameters.getMinSaturation(),
						plotParameters.getMaxSaturation(),
						COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT,
						COLOR_SCALE_NUM_BINS, panel.getDrawingCanvas());
				panel.add(scale, HorizontalAlignment.CENTERED,
						VerticalAlignment.BELOW);
				row = panel.newChildPlotPanel();
				plotCount = 1;
			}
			row.setName("row");
			
			// Get cytological map
			CytologicalMap cytologicalMap =
				this.cytologicalMapDao.load(org, gi.getChromosome());
			
			// Calculate height of ideogram
			ChromosomeIdeogramSize idSize = plotParameters.getIdeogramSize();
			int height = idSize.pixels(cytologicalMap.length());
			
			// Paint chromosome ideogram
			boolean makeReferenceElement = false;
			if (plotCount == 1) {
				makeReferenceElement = true;
			}
			this.paintChromosomeIdeogram(row, cytologicalMap,
					height, idSize, plotParameters.getIdeogramThickness(),
					"CHR " + gi.getChromosome(),
					makeReferenceElement, evtHandlerBoundaries);
			
			// Add data tracks
			this.paintDataTracks(row, experiments, gi.getChromosome(),
					height, plotParameters, evtHandlerBoundaries);
		}
		
		// Add final row
		VerticalAlignment va = null;
		if (rowCount == 1) {
			va = VerticalAlignment.TOP_JUSTIFIED;
		} else {
			va = VerticalAlignment.BELOW;
		}
		panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, va);
		ColorScale scale = new ColorScale(
				plotParameters.getMinSaturation(),
				plotParameters.getMaxSaturation(),
				COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT,
				COLOR_SCALE_NUM_BINS, panel.getDrawingCanvas());
		panel.add(scale, HorizontalAlignment.CENTERED,
				VerticalAlignment.BELOW);
		
		return evtHandlerBoundaries;
	}
	
	
	/**
	 * Paint chromosome ideogram on given plot panel.
	 * @param plotPanel Plot panel to paint on
	 * @param cytologicalMap Cytological map to convert graphically
	 * into an ideogram
	 * @param height Height of ideogram in pixels.  This
	 * does not include chromosome end caps, which are a fixed
	 * height, but rather the height of the region that can be
	 * plotted against.
	 * @param idSize Chromosome ideogram size
	 * @param ideogramThickness Ideogram thickness in pixels
	 * @param chromosome Chromosome name
	 * @param makeReferenceElement Make this ideogram the
	 * reference element for the given plot panel in terms
	 * of layout?
	 * @param boundaries Event handler boundaries
	 */
	private void paintChromosomeIdeogram(final PlotPanel plotPanel,
			final CytologicalMap cytologicalMap,
			final int height, final ChromosomeIdeogramSize idSize,
			final int ideogramThickness,
			final String chromosome, final boolean makeReferenceElement,
			final EventHandlerGraphicBoundaries boundaries) {
		PlotPanel idPanel = plotPanel.newChildPlotPanel();
		
		// Instantiate genome feature plot
		ChromosomeIdeogram plot = new ChromosomeIdeogram(1,
				cytologicalMap.length(), cytologicalMap.getCentromereStart(),
				cytologicalMap.getCentromereEnd(),
				height, Orientation.VERTICAL,
				ideogramThickness);
		
		// Add cytobands to plot
		for (Cytoband c : cytologicalMap.getCytobands()) {
			plot.add(c);
		}
		
		// Add genome feature plot
		idPanel.add(plot, true);
		
		// Add graphic event boundaries
		boundaries.add(plot.getMouseOverStripes());
		
		// Add end caps
		ChromosomeEndCap topCap = new ChromosomeEndCap(ideogramThickness,
				FRAME_LINE_COLOR, Direction.UP);
		ChromosomeEndCap botCap = new ChromosomeEndCap(ideogramThickness,
				FRAME_LINE_COLOR, Direction.DOWN);
		idPanel.add(topCap, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.TOP_JUSTIFIED);
		idPanel.add(botCap, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.BOTTOM_JUSTIFIED);
		
		// Add chromosome number caption
		Caption caption = new Caption(chromosome, null,
				Orientation.HORIZONTAL, false, idPanel.getDrawingCanvas());
		idPanel.add(caption, HorizontalAlignment.CENTERED,
				VerticalAlignment.BELOW);
		
		// Add new panel to parent
		if (makeReferenceElement) {
			plotPanel.add(idPanel, true);
		} else {
			plotPanel.add(idPanel, HorizontalAlignment.RIGHT_OF,
					VerticalAlignment.TOP_JUSTIFIED);
		}
	}
			
	
	/**
	 * Add data tracks to plot.
	 * @param panel A plot panel
	 * @param experiments Experiments to plot
	 * @param chromosome Chromosome number
	 * @param height Height of data tracks
	 * @param plotParameters Plot parameters
	 * @param boundaries Event handler boundaries
	 */
	private void paintDataTracks(final PlotPanel panel,
			final Collection<Experiment> experiments, final short chromosome,
			final int height, final IdeogramPlotParameters plotParameters,
			final EventHandlerGraphicBoundaries boundaries) {
		HeatMapColorFactory fac = new HeatMapColorFactory(
				plotParameters.getMinSaturation(),
				plotParameters.getMaxSaturation(), NUM_BINS);
		HeatMapPlot plot = new HeatMapPlot(experiments, chromosome, fac,
				plotParameters, this.getChromosomeArrayDataGetter(),
				panel.getDrawingCanvas());
		boundaries.addAll(plot.getMouseOverStripes());
		panel.add(plot, HorizontalAlignment.RIGHT_OF,
				VerticalAlignment.TOP_JUSTIFIED);
	}
}
