/*
$Revision: 1.10 $
$Date: 2006-09-17 20:27:33 $

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
import org.rti.webcgh.graphics.util.CentromereWarper;
import org.rti.webcgh.graphics.util.ColorMapper;
import org.rti.webcgh.graphics.util.HeatMapColorFactory;
import org.rti.webcgh.graphics.util.Warper;
import org.rti.webcgh.graphics.widget.Caption;
import org.rti.webcgh.graphics.widget.ChromosomeEndCap;
import org.rti.webcgh.graphics.widget.GenomeFeaturePlot;
import org.rti.webcgh.graphics.widget.HeatMapPlot;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.service.dao.CytologicalMapDao;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.units.ChromosomeIdeogramSize;
import org.rti.webcgh.units.Direction;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;


/**
 * Generates ideogram plots by assembling and laying out
 * the necessary widgets.
 * @author dhall
 */
public class IdeogramPlotPainter extends PlotPainter {
	
	// ======================
	//    Constants
	// ======================
	
	/** Thickness of line that frames ideogram. */
	private static final int FRAME_LINE_THICKNESS = 1;
	
	/** Color of line that frames ideogram. */
	private static final Color FRAME_LINE_COLOR = Color.BLACK;
	
	/** Number of color bins. */
	private static final int NUM_BINS = 16;
	
	// ======================
	//       Attributes
	// ======================
	
	/**
	 * Color mapper to map cytoband stain
	 * intensities to colors.
	 */
	private final ColorMapper colorMapper;
	
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
	 * @param colorMapper Color mapper to map cytoband stain
	 * intensities to colors.
	 */
	public IdeogramPlotPainter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter,
			final ColorMapper colorMapper) {
		super(chromosomeArrayDataGetter);
		this.colorMapper = colorMapper;
	}
	
	
	// =========================
	//    Business methods
	// =========================
	
	/**
	 * Paint ideogram plot on given plot panel.
	 * Can only plot data from one organism.
	 * @param panel Plot panel to paint on
	 * @param experiments Experiments to plot
	 * @param plotParameters Plot parameters
	 */
	public final void paintIdeogramPlot(
			final PlotPanel panel,
			final Collection<Experiment> experiments,
			final IdeogramPlotParameters plotParameters) {
		
		// Make sure arguments okay
		if (experiments == null || experiments.size() < 1) {
			throw new IllegalArgumentException("Experiments must be > 1");
		}
		
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
			if (++plotCount >=  plotParameters.getNumPlotsPerRow()) {
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
					height, idSize, "CHR " + gi.getChromosome(),
					makeReferenceElement);
			
			// Add data tracks
//			this.paintDataTracks(column, experiments, gi.getChromosome(),
//					height, plotParameters);
		}
		
		// Add final row
		VerticalAlignment va = null;
		if (rowCount == 1) {
			va = VerticalAlignment.TOP_JUSTIFIED;
		} else {
			va = VerticalAlignment.BELOW;
		}
		panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, va);
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
	 * @param chromosome Chromosome name
	 * @param makeReferenceElement Make this ideogram the
	 * reference element for the given plot panel in terms
	 * of layout?
	 */
	private void paintChromosomeIdeogram(final PlotPanel plotPanel,
			final CytologicalMap cytologicalMap,
			final int height, final ChromosomeIdeogramSize idSize,
			final String chromosome, final boolean makeReferenceElement) {
		PlotPanel idPanel = plotPanel.newChildPlotPanel();
		
		// Instantiate genome feature plot
		GenomeFeaturePlot plot = new GenomeFeaturePlot(1,
				cytologicalMap.length(), height, Orientation.VERTICAL);
		
		// Add warper to give plot hourglass shape around centromere
		int centStartPix = idSize.pixels(cytologicalMap.getCentromereStart());
		int centEndPix = idSize.pixels(cytologicalMap.getCentromereEnd());
		Warper warper = new CentromereWarper(plot.getFeatureHeight(),
				centStartPix, centEndPix);
		plot.setWarper(warper);
		
		// Add cytobands to plot
		for (Cytoband c : cytologicalMap.getCytobands()) {
			Color color = this.colorMapper.getColor(c.getStain());
			plot.plotFeature(c.getStart(), c.getEnd(), c.getName(), null,
					false, color);
		}
		
		// Add border frame around ideogram
		plot.addFrame(Location.ABOVE, FRAME_LINE_THICKNESS, FRAME_LINE_COLOR);
		plot.addFrame(Location.BELOW, FRAME_LINE_THICKNESS, FRAME_LINE_COLOR);
		
		// Add genome feature plot
		idPanel.add(plot, true);
		
		// Add end caps
		int thickness = plot.getFeatureHeight();
		ChromosomeEndCap topCap = new ChromosomeEndCap(thickness,
				FRAME_LINE_COLOR, Direction.UP);
		ChromosomeEndCap botCap = new ChromosomeEndCap(thickness,
				FRAME_LINE_COLOR, Direction.DOWN);
		idPanel.add(topCap, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.TOP_JUSTIFIED);
		idPanel.add(botCap, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.BOTTOM_JUSTIFIED);
		
		// Add chromosome number caption
		Caption caption = new Caption(chromosome, null,
				Orientation.HORIZONTAL, false);
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
	 */
	private void paintDataTracks(final PlotPanel panel,
			final Collection<Experiment> experiments, final short chromosome,
			final int height, final IdeogramPlotParameters plotParameters) {
		HeatMapColorFactory fac = new HeatMapColorFactory(
				plotParameters.getMinSaturation(),
				plotParameters.getMaxSaturation(), NUM_BINS);
		HeatMapPlot plot = new HeatMapPlot(experiments, chromosome, fac,
				plotParameters, this.getChromosomeArrayDataGetter(),
				panel.getDrawingCanvas());
		panel.add(plot, HorizontalAlignment.RIGHT_OF,
				VerticalAlignment.TOP_JUSTIFIED);
	}
}
