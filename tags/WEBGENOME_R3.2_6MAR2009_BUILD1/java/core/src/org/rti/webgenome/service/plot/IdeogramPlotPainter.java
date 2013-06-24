/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.5 $
$Date: 2008-06-17 16:10:52 $


*/

package org.rti.webgenome.service.plot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Cytoband;
import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.graphics.event.EventHandlerGraphicBoundaries;
import org.rti.webgenome.graphics.util.HeatMapColorFactory;
import org.rti.webgenome.graphics.widget.Caption;
import org.rti.webgenome.graphics.widget.ChromosomeEndCap;
import org.rti.webgenome.graphics.widget.ChromosomeIdeogram;
import org.rti.webgenome.graphics.widget.ColorScale;
import org.rti.webgenome.graphics.widget.HeatMapPlot;
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.service.dao.CytologicalMapDao;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;
import org.rti.webgenome.units.ChromosomeIdeogramSize;
import org.rti.webgenome.units.Direction;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;


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
    		final IdeogramPlotParameters params) {
		
		// Make sure arguments okay
		if (experiments == null || experiments.size() < 1) {
			throw new IllegalArgumentException("Experiments must be > 1");
		}
		if (!(params instanceof IdeogramPlotParameters)) {
			throw new IllegalArgumentException(
					"Expecting plot parameters of type IdeogramPlotParameters");
		}
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
		
		int rowCount = 1;
		RowAdder rowAdder = new RowAdder(experiments, params,
				this.cytologicalMapDao, org, evtHandlerBoundaries,
				this.getChromosomeArrayDataGetter());
		QuantitationType copyNumberQT =
			Experiment.getCopyNumberQuantitationType(experiments);
		QuantitationType expressionQT =
			Experiment.getExpressionQuantitationType(experiments);
		while (rowAdder.hasMore()) {
			PlotPanel row = panel.newChildPlotPanel();
			rowAdder.paintRow(row);
			VerticalAlignment va = null;
			if (rowCount++ == 1) {
				va = VerticalAlignment.TOP_JUSTIFIED;
			} else {
				va = VerticalAlignment.BELOW;
			}
			panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, va);
			//this.addScales(panel, copyNumberQT, expressionQT, params);
		}
		this.addScales(panel, copyNumberQT, expressionQT, params);	// add scale on bottom

		return evtHandlerBoundaries;
	}
    
    
    /**
     * Add "scales" to given panel.  These scales give color coding
     * for copy number and/or expression data.
     * @param panel Panel on which to draw scales
     * @param copyNumberQT Quantitation type for copy number data
     * @param expressionQT Quantitation type for expression data
     * @param params Plotting parameters
     */
    private void addScales(final PlotPanel panel,
    		final QuantitationType copyNumberQT,
    		final QuantitationType expressionQT,
    		final IdeogramPlotParameters params) {
    	PlotPanel scales = panel.newChildPlotPanel();
		if (copyNumberQT != null) {
			PlotPanel cnPanel = scales.newChildPlotPanel();
			ColorScale scale = new ColorScale(
					params.getCopyNumberMinSaturation(),
					params.getCopyNumberMaxSaturation(),
					COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT,
					COLOR_SCALE_NUM_BINS, panel.getDrawingCanvas());
			cnPanel.add(scale, HorizontalAlignment.CENTERED,
					VerticalAlignment.TOP_JUSTIFIED);
			cnPanel.add(new Caption(copyNumberQT.getName(),
					Orientation.HORIZONTAL,
					false, panel.getDrawingCanvas()),
					HorizontalAlignment.CENTERED,
					VerticalAlignment.BELOW);
			scales.add(cnPanel, HorizontalAlignment.LEFT_JUSTIFIED,
					VerticalAlignment.TOP_JUSTIFIED);
		}
		if (expressionQT != null) {
			PlotPanel exPanel = scales.newChildPlotPanel();
			ColorScale scale = new ColorScale(
					params.getExpressionMinSaturation(),
					params.getExpressionMaxSaturation(),
					COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT,
					COLOR_SCALE_NUM_BINS, panel.getDrawingCanvas());
			exPanel.add(scale, HorizontalAlignment.CENTERED,
					VerticalAlignment.TOP_JUSTIFIED);
			exPanel.add(new Caption(expressionQT.getName(),
					Orientation.HORIZONTAL,
					false, panel.getDrawingCanvas()),
					HorizontalAlignment.CENTERED,
					VerticalAlignment.BELOW);
			HorizontalAlignment ha = null;
			if (copyNumberQT == null) {
				ha = HorizontalAlignment.LEFT_JUSTIFIED;
			} else {
				ha = HorizontalAlignment.RIGHT_OF;
			}
			scales.add(exPanel, ha,
					VerticalAlignment.TOP_JUSTIFIED);
		}
		panel.add(scales, HorizontalAlignment.CENTERED,
					VerticalAlignment.BELOW);
    }
	
	
	/**
	 * Helper class that adds a new "row" of genome interval
	 * plots to a larger graph.
	 * @author dhall
	 *
	 */
	private static final class RowAdder {
		
		//
		//  A T T R I B U T E S
		//
		
		/** Experiments to plot. */
		private Collection<Experiment> experiments = null;
		
		/** Genome interval to plot. */
		private List<GenomeInterval> genomeIntervals =
			new ArrayList<GenomeInterval>();
		
		/** Plotting parameters. */
		private IdeogramPlotParameters params = null;
		
		/** Index pointing to genome intervals. */
		private int idx = 0;
		
		/** Data access object for cytological maps. */
		private CytologicalMapDao cytologicalMapDao = null;
		
		/** Organism associated with data to plot. */
		private Organism organism = null;
		
		/** Boundaries of mouse-over interactive areas. */
		private EventHandlerGraphicBoundaries eventBoundaries = null;
		
		/** Data getter. */
		private ChromosomeArrayDataGetter chromosomeArrayDataGetter = null;
		
		//
		//  C O N S T R U C T O R S
		//
		
		
		/**
		 * Constructor.
		 * @param experiments Experiments to plot.
		 * @param params Plotting parameters
		 * @param cytologicalMapDao Data access object for
		 * cytological maps
		 * @param organism Organism associated with data
		 * @param eventBoundaries Mouse-over event coordinates
		 * @param chromosomeArrayDataGetter Data getter
		 */
		public RowAdder(final Collection<Experiment> experiments,
				final IdeogramPlotParameters params,
				final CytologicalMapDao cytologicalMapDao,
				final Organism organism,
				final EventHandlerGraphicBoundaries eventBoundaries,
				final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
			super();
			this.experiments = experiments;
			this.params = params;
			this.cytologicalMapDao = cytologicalMapDao;
			this.organism = organism;
			this.eventBoundaries = eventBoundaries;
			this.chromosomeArrayDataGetter = chromosomeArrayDataGetter;
			this.genomeIntervals.addAll(params.getGenomeIntervals());
		}
		
		//
		//  B U S I N E S S  M E T H O D S
		//
		

		/**
		 * Paint a row of graphical elements onto the given panel.
		 * @param row Panel on which to add elements.
		 */
		private void paintRow(final PlotPanel row) {
			if (this.hasMore()) {
				int endIdx = this.idx + this.params.getNumPlotsPerRow();
				if (endIdx > this.genomeIntervals.size()) {
					endIdx = this.genomeIntervals.size();
				}
				for (int i = this.idx; i < endIdx; i++) {
					boolean isReferencePlot = i == this.idx;
					this.addIntervalPlot(row, this.genomeIntervals.get(i),
							isReferencePlot);
				}
				this.idx = endIdx;
			}
		}
		
		/**
		 * Add a single genome interval to row panel.
		 * @param row Panel corresponding to a row of plots
		 * @param gi Genome interval to plot
		 * @param referencePlot Should this plot be a graphical
		 * "reference" element, i.e. providing layout coordinates
		 * for subsequent elements?
		 */
		private void addIntervalPlot(final PlotPanel row,
				final GenomeInterval gi, final boolean referencePlot) {
			
			// Get cytological map
			CytologicalMap cytologicalMap =
				this.cytologicalMapDao.load(this.organism, gi.getChromosome());
			if (cytologicalMap == null) {
				throw new WebGenomeSystemException(
						"Cytological map not found for chromosome "
						+ gi.getChromosome());
			}
			
			// Calculate height of ideogram
			ChromosomeIdeogramSize idSize = this.params.getIdeogramSize();
			int height = idSize.pixels(cytologicalMap.length());
			
			this.paintChromosomeIdeogram(row, cytologicalMap,
					height, "CHR " + gi.getChromosome(),
					referencePlot, this.eventBoundaries);
			
			// Add data tracks
			this.paintDataTracks(row, experiments, gi.getChromosome(),
					height, this.params, this.eventBoundaries);
		}
		
		/**
		 * Are there more rows to plot?
		 * @return T/F
		 */
		private boolean hasMore() {
			return this.idx < this.genomeIntervals.size();
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
		 * @param chromosome Chromosome name
		 * @param makeReferenceElement Make this ideogram the
		 * reference element for the given plot panel in terms
		 * of layout?
		 * @param boundaries Event handler boundaries
		 */
		private void paintChromosomeIdeogram(final PlotPanel plotPanel,
				final CytologicalMap cytologicalMap,
				final int height,
				final String chromosome, final boolean makeReferenceElement,
				final EventHandlerGraphicBoundaries boundaries) {
			PlotPanel idPanel = plotPanel.newChildPlotPanel();
			
			// Instantiate genome feature plot
			ChromosomeIdeogram plot = new ChromosomeIdeogram(1,
					cytologicalMap.length(),
					cytologicalMap.getCentromereStart(),
					cytologicalMap.getCentromereEnd(),
					height, Orientation.VERTICAL,
					this.params.getIdeogramThickness());
			
			// Add cytobands to plot
			for (Cytoband c : cytologicalMap.getCytobands()) {
				plot.add(c);
			}
			
			// Add genome feature plot
			idPanel.add(plot, true);
			
			// Add graphic event boundaries
			boundaries.add(plot.getMouseOverStripes());
			
			// Add end caps
			ChromosomeEndCap topCap = new ChromosomeEndCap(
					this.params.getIdeogramThickness(),
					FRAME_LINE_COLOR, Direction.UP);
			ChromosomeEndCap botCap = new ChromosomeEndCap(
					this.params.getIdeogramThickness(),
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
				final Collection<Experiment> experiments,
				final short chromosome,
				final int height, final IdeogramPlotParameters plotParameters,
				final EventHandlerGraphicBoundaries boundaries) {
			
			// Copy number data
			Collection<Experiment> cnExps =
				Experiment.getCopyNumberExperiments(experiments);
			if (cnExps != null && cnExps.size() > 0) {
				HeatMapColorFactory fac = new HeatMapColorFactory(
						plotParameters.getCopyNumberMinSaturation(),
						plotParameters.getCopyNumberMaxSaturation(), NUM_BINS);
				HeatMapPlot plot = new HeatMapPlot(cnExps, chromosome, fac,
						plotParameters, this.chromosomeArrayDataGetter,
						panel.getDrawingCanvas());
				boundaries.addAll(plot.getMouseOverStripes());
				panel.add(plot, HorizontalAlignment.RIGHT_OF,
						VerticalAlignment.TOP_JUSTIFIED);
			}
			
			// Expression data
			Collection<Experiment> exprExps =
				Experiment.getExpressionExperiments(experiments);
			if (exprExps != null & exprExps.size() > 0) {
				HeatMapColorFactory fac = new HeatMapColorFactory(
						plotParameters.getExpressionMinSaturation(),
						plotParameters.getExpressionMaxSaturation(), NUM_BINS);
				HeatMapPlot plot = new HeatMapPlot(exprExps, chromosome, fac,
						plotParameters, this.chromosomeArrayDataGetter,
						panel.getDrawingCanvas());
				boundaries.addAll(plot.getMouseOverStripes());
				panel.add(plot, HorizontalAlignment.RIGHT_OF,
						VerticalAlignment.TOP_JUSTIFIED);
			}
		}
	}
}
