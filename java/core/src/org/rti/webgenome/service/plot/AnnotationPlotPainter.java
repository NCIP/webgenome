/*
$Revision: 1.5 $
$Date: 2007-12-21 22:44:09 $


*/

package org.rti.webgenome.service.plot;

import java.awt.Color;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.widget.AnnotationTrack;
import org.rti.webgenome.graphics.widget.Axis;
import org.rti.webgenome.graphics.widget.Background;
import org.rti.webgenome.graphics.widget.Caption;
import org.rti.webgenome.graphics.widget.ColorScale;
import org.rti.webgenome.graphics.widget.DataTrack;
import org.rti.webgenome.graphics.widget.Grid;
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.service.dao.AnnotatedGenomeFeatureDao;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Location;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;

/**
 * Manager the painting of annotation plots.
 * @author dhall
 *
 */
public class AnnotationPlotPainter extends PlotPainter {
	
	//
	//     STATICS
	//
	
	/** Padding between graphical elements in pixels. */
	private static final int PADDING = 15;
	
	/** Color of grid. */
	private static final Color GRID_COLOR = Color.WHITE;
	
	/** Background color. */
	private static final Color BG_COLOR = new Color(200, 200, 200);
	
	/** Width of color scale in pixels. */
	private static final int COLOR_SCALE_WIDTH = 250;
	
	/** Height of color scale in pixels. */
	private static final int COLOR_SCALE_HEIGHT = 15;
	
	/** Number of color bins in color scale. */
	private static final int COLOR_SCALE_NUM_BINS = 16;
	
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Data access object for retrieving annotated genome features.
	 * This property should be injected.
	 */
	private AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao = null;
	
	
	//
	//     GETTERS/SETTERS
	//
	
	/**
	 * Get data access object for retrieving annotated genome features.
	 * This property should be injected.
	 * @return Data access object for retrieving annotated genome features
	 */
	public final AnnotatedGenomeFeatureDao getAnnotatedGenomeFeatureDao() {
		return annotatedGenomeFeatureDao;
	}


	/**
	 * Set data access object for retrieving annotated genome features.
	 * This property should be injected.
	 * @param annotatedGenomeFeatureDao Data access object for
	 * retrieving annotated genome features
	 */
	public final void setAnnotatedGenomeFeatureDao(
			final AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao) {
		this.annotatedGenomeFeatureDao =
			annotatedGenomeFeatureDao;
	}
	
	//
	//     CONSTRUCTORS
	//


	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome array data
	 * getter
	 */
	public AnnotationPlotPainter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		super(chromosomeArrayDataGetter);
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Paint a bar plot on given plot panel.  Only selected
	 * reporters will be represented in the plot.
	 * @see org.rti.webgenome.domain.Reporter#isSelected()
	 * @param panel Panel on which to paint
	 * @param experiments Experiments
	 * @param params Plot parameters
	 */
	public final void paintPlot(final PlotPanel panel,
			final Collection<Experiment> experiments,
			final AnnotationPlotParameters params) {
		
		// Make sure arguments okay
		if (panel == null) {
			throw new IllegalArgumentException("Plotting panel is null");
		}
		if (experiments == null) {
			throw new IllegalArgumentException("Experiments is null");
		}
		if (params == null) {
			throw new IllegalArgumentException("Plot parameters are null");
		}
		SortedSet<GenomeInterval> intervals = params.getGenomeIntervals();
		if (intervals == null || intervals.size() < 1) {
			throw new IllegalArgumentException("Genome interval undefined");
		}
		
		// Create new panel for drawing
		PlotPanel childPanel = panel.newChildPlotPanel();
		childPanel.setPadding(PADDING);
		
		// Retrieve genome interval.  If more than one given in plot
		// parameters, take first.
		GenomeInterval interval = intervals.first();
		
		// Add axis
		long start = params.getUnits().fromBp(interval.getStartLocation());
		long end = params.getUnits().fromBp(interval.getEndLocation());
		Axis axis = this.paintAxis(childPanel, params,
				interval.getChromosome(), start, end,
				childPanel.getDrawingCanvas());
		
		// Construct annotation tracks
		this.paintAnnotationTracks(childPanel, params,
				interval.getChromosome(),
				interval.getStartLocation(), interval.getEndLocation(),
				Experiment.getOrganism(experiments));
		
		// Construct data tracks
		this.paintDataTracks(childPanel, experiments, params,
				interval.getChromosome(), interval.getStartLocation(),
				interval.getEndLocation());
		
		// Add color code indicator
		QuantitationType copyNumberQT =
			Experiment.getCopyNumberQuantitationType(experiments);
		QuantitationType expressionQT =
			Experiment.getExpressionQuantitationType(experiments);
		String qtLabel = Experiment.getCopyNumberQuantitationLabel(experiments);
		this.addScales(childPanel, copyNumberQT, expressionQT, qtLabel, params);
		
		// Add grid
		Grid grid = axis.newGrid(params.getWidth(),
				childPanel.height(), GRID_COLOR, panel);
		childPanel.add(grid, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.TOP_JUSTIFIED);
		childPanel.moveToBack(grid);
		
		// Add background
		Background bg = new Background(childPanel.width(),
				childPanel.height(), BG_COLOR);
		childPanel.add(bg, HorizontalAlignment.CENTERED,
				VerticalAlignment.CENTERED);
		childPanel.moveToBack(bg);
		
		// Add child panel to parent panel
		panel.add(childPanel);
	}
	
	
	/**
	 * Paint axis on top of plot.
	 * @param panel Panel to paint on
	 * @param params Plot parameters
	 * @param chromosome Chromosome number
	 * @param startLocation Starting chromosome location
	 * in base pair units
	 * @param endLocation Ending chromosome location in
	 * base pair units
	 * @param canvas Drawing canvas this will be rendered on
	 * @return The axis that was created
	 */
	private Axis paintAxis(final PlotPanel panel,
			final AnnotationPlotParameters params,
			final short chromosome,
			final long startLocation, final long endLocation,
			final DrawingCanvas canvas) {
		
		// Axis
		Axis axis = new Axis(startLocation,
				endLocation, params.getWidth(),
				Orientation.HORIZONTAL, Location.ABOVE,
				panel.getDrawingCanvas());
		panel.add(axis, true);
		
		// Caption
		String capText = "Chromosome " + chromosome + " ("
		+ params.getUnits().toPrettyString() + ")";
		Caption caption = new Caption(capText,
				Orientation.HORIZONTAL, false, canvas);
		panel.add(caption, HorizontalAlignment.CENTERED,
				VerticalAlignment.ABOVE);
		
		return axis;
	}
	
	
	/**
	 * Paint annotation plot tracks.
	 * @param panel Panel to paint on
	 * @param params Plot parameters
	 * @param chromosome Chromosome number
	 * @param startLocation Starting chromosome location
	 * in base pair units
	 * @param endLocation Ending chromosome location in
	 * base pair units
	 * @param organism Organism
	 */
	private void paintAnnotationTracks(final PlotPanel panel,
			final AnnotationPlotParameters params,
			final short chromosome, final long startLocation,
			final long endLocation, final Organism organism) {
		Set<AnnotationType> types = params.getAnnotationTypes();
		if (types != null) {
			for (AnnotationType type : types) {
				SortedSet<AnnotatedGenomeFeature> feats =
					this.annotatedGenomeFeatureDao.load(
							chromosome, startLocation, endLocation,
							type, organism);
				AnnotationTrack track = new AnnotationTrack(feats,
						params.getWidth(), startLocation,
						endLocation, type.toString(),
						params.isDrawFeatureLabels(), panel.getDrawingCanvas());
				panel.add(track, HorizontalAlignment.LEFT_JUSTIFIED,
						VerticalAlignment.BELOW);
			}
		}
	}
	
	
	/**
	 * Paint data tracks.
	 * @param panel Panel on which to paint
	 * @param experiments Experiments
	 * @param params Plot parameters
	 * @param chromosome Chromosome number
	 * @param startLocation Starting chromosome location
	 * in base pair units
	 * @param endLocation Ending chromosome location in
	 * base pair units
	 */
	private void paintDataTracks(final PlotPanel panel,
			final Collection<Experiment> experiments,
			final AnnotationPlotParameters params,
			final short chromosome, final long startLocation,
			final long endLocation) {
		for (Experiment exp : experiments) {
			float minSat = Float.NaN;
			float maxSat = Float.NaN;
			if (exp.getQuantitationType().isExpressionData()) {
				minSat = params.getExpressionMinSaturation();
				maxSat = params.getExpressionMaxSaturation();
			} else {
				minSat = params.getCopyNumberMinSaturation();
				minSat = params.getCopyNumberMaxSaturation();
			}
			for (BioAssay bioAssay : exp.getBioAssays()) {
				ChromosomeArrayData cad = this.getChromosomeArrayDataGetter().
				getChromosomeArrayData(bioAssay, chromosome);
				DataTrack track = new DataTrack(cad,
						startLocation,
						endLocation, minSat, maxSat,
						params.getWidth(), bioAssay.getName(),
						panel.getDrawingCanvas());
				panel.add(track, HorizontalAlignment.LEFT_JUSTIFIED,
						VerticalAlignment.BELOW);
			}
		}
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
    		final String qtLabel,
    		final AnnotationPlotParameters params) {
    	PlotPanel scales = panel.newChildPlotPanel();
		if (copyNumberQT != null) {
			String name = copyNumberQT.getName() ;
			if ( qtLabel != null ) // this will be non-null for Other label
				name = qtLabel ;
			PlotPanel cnPanel = scales.newChildPlotPanel();
			ColorScale scale = new ColorScale(
					params.getCopyNumberMinSaturation(),
					params.getCopyNumberMaxSaturation(),
					COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT,
					COLOR_SCALE_NUM_BINS, panel.getDrawingCanvas());
			cnPanel.add(scale, HorizontalAlignment.CENTERED,
					VerticalAlignment.TOP_JUSTIFIED);
			cnPanel.add(new Caption(name,
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
}
