/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-12-05 19:59:32 $


*/

package org.rti.webgenome.service.plot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.graphics.util.GenomePositionMapper;
import org.rti.webgenome.graphics.widget.Axis;
import org.rti.webgenome.graphics.widget.Background;
import org.rti.webgenome.graphics.widget.Caption;
import org.rti.webgenome.graphics.widget.GenomeSnapshotPlot;
import org.rti.webgenome.graphics.widget.Grid;
import org.rti.webgenome.graphics.widget.Legend;
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Location;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;

/**
 * Generates genome snapshot plots.
 * @author dhall
 *
 */
public class GenomeSnapshotPlotPainter extends PlotPainter {
	
	//
	//  C O N S T A N T S
	//
	
	/** Grid color. */
	private static final Color GRID_COLOR = Color.WHITE;
	
	/** Background color. */
	private static final Color BG_COLOR = new Color(235, 235, 235);
		
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	public GenomeSnapshotPlotPainter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		super(chromosomeArrayDataGetter);
	}

	//
	//  B U S I N E S S  M E T H O D S
	//
	
    /**
     * Paints a plot on the given plot panel.
     * @param panel Plot panel to add the plot to
     * @param experiments Experiments to plot
     * @param params Plotting parameters specified
     * by user
     */
    public void paintPlot(
    		final PlotPanel panel,
    		final Collection<Experiment> experiments,
    		final GenomeSnapshopPlotParameters params) {
        
        // Check args
        if (experiments == null || panel == null) {
            throw new IllegalArgumentException(
                    "Experiments and panel cannot be null");
        }
        
        Axis copyNumberAxis = null;
        Axis expressionAxis = null;
        Axis referenceYAxis = null;
        QuantitationType referenceYAxisQType = null;
        QuantitationType copyNumberQType =
        	Experiment.getCopyNumberQuantitationType(experiments);
        String quantitationTypeLabel = Experiment.getCopyNumberQuantitationLabel(experiments);
		if (copyNumberQType != null) {
			copyNumberAxis = new Axis(
					params.getMinY(),
					params.getMaxY(),
					params.getHeight(), Orientation.VERTICAL,
					Location.LEFT_OF, panel.getDrawingCanvas());
			referenceYAxis = copyNumberAxis;
			referenceYAxisQType = copyNumberQType;
		}
		QuantitationType expressionQType =
			Experiment.getExpressionQuantitationType(experiments);
		if (expressionQType != null) {
			Location textLocation = null;
			if (copyNumberQType != null) {
				textLocation = Location.RIGHT_OF;
			} else {
				textLocation = Location.LEFT_OF;
			}
			expressionAxis = new Axis(
					params.getMinY(),
					params.getMaxY(),
					params.getHeight(), Orientation.VERTICAL,
					textLocation, panel.getDrawingCanvas());
			if (referenceYAxis == null) {
				referenceYAxis = expressionAxis;
				referenceYAxisQType = expressionQType;
			}
		}
        
        // Background
        Background bg = new Background(params.getWidth(),
        		params.getHeight(), BG_COLOR);
        panel.add(bg, true);
        
        // X-axis
        List<Double> hatchPoints = new ArrayList<Double>();
        List<String> hatchLabels = new ArrayList<String>();
        GenomePositionMapper mapper = new GenomePositionMapper(experiments);
        SortedMap<Short, Long> chromStarts =
        	mapper.getChromosomeStartLocations();
        for (Short chrom : chromStarts.keySet()) {
        	hatchLabels.add(chrom.toString());
        	hatchPoints.add(new Double(chromStarts.get(chrom)));
        }
        Axis xAxis = new Axis(params.getWidth(), Orientation.HORIZONTAL,
        		Location.BELOW, panel.getDrawingCanvas(), hatchPoints,
        		hatchLabels);
        String captionText = "Chromosome";
	    Caption xCaption = new Caption(captionText,
	            Orientation.HORIZONTAL, false, panel.getDrawingCanvas());
        
        // Grid lines
        if (params.isDrawVertGridLines()) {
        	Grid vertGrid = xAxis.newGrid(params.getWidth(),
        			params.getHeight(), GRID_COLOR, panel);
        	panel.add(vertGrid, HorizontalAlignment.LEFT_JUSTIFIED,
        			VerticalAlignment.TOP_JUSTIFIED);
        }
        if (params.isDrawHorizGridLines()) {
        	Grid horizGrid = referenceYAxis.newGrid(params.getWidth(),
        			params.getHeight(), GRID_COLOR, panel);
        	panel.add(horizGrid,  HorizontalAlignment.LEFT_JUSTIFIED,
        			VerticalAlignment.TOP_JUSTIFIED);
        }
        
        panel.add(xAxis, HorizontalAlignment.LEFT_JUSTIFIED,
	    		VerticalAlignment.BOTTOM_JUSTIFIED);
	    panel.add(xCaption, HorizontalAlignment.CENTERED,
	            VerticalAlignment.BELOW);
        
        // Add reference Y-axes
	    String yCaptionText = referenceYAxisQType.getName() ;
	    if ( quantitationTypeLabel != null ) // Will be non-null, if 'Other' label is set.
	    	yCaptionText = quantitationTypeLabel ;
        panel.add(referenceYAxis, HorizontalAlignment.LEFT_JUSTIFIED,
        		VerticalAlignment.BOTTOM_JUSTIFIED);
        Caption yCaption = new Caption(yCaptionText,
        		Orientation.HORIZONTAL, true, panel.getDrawingCanvas());
        panel.add(yCaption, HorizontalAlignment.LEFT_OF,
        		VerticalAlignment.CENTERED);
        
        // Add genome snapshot plot
        GenomeSnapshotPlot plot = new GenomeSnapshotPlot(experiments,
        		this.getChromosomeArrayDataGetter(), params.getWidth(),
        		params.getHeight(), params.getMinY(), params.getMaxY());
        plot.setDrawRawLohProbabilities(params.isDrawRawLohProbabilities());
        plot.setInterpolateLohEndpoints(params.isInterpolateLohEndpoints());
        plot.setInterpolationType(params.getInterpolationType());
        plot.setLohThreshold(params.getLohThreshold());
        panel.add(plot, HorizontalAlignment.LEFT_JUSTIFIED,
        		VerticalAlignment.BOTTOM_JUSTIFIED);

        // Add second Y-axis if necessary
        if (expressionAxis != null) {
        	panel.add(expressionAxis, HorizontalAlignment.RIGHT_JUSTIFIED,
        			VerticalAlignment.BOTTOM_JUSTIFIED);
        	yCaptionText = expressionQType.getName();
        	yCaption = new Caption(yCaptionText, Orientation.HORIZONTAL,
        			true, panel.getDrawingCanvas());
        	panel.add(yCaption, HorizontalAlignment.RIGHT_OF,
            		VerticalAlignment.CENTERED);
        }
        
        // Legend
        Legend legend = new Legend(experiments, params.getWidth());
        panel.add(legend, HorizontalAlignment.CENTERED,
        		VerticalAlignment.BELOW);
        
        // Plot title
        Caption title = new Caption(params.getPlotName(),
        		Orientation.HORIZONTAL, false, panel.getDrawingCanvas());
        panel.add(title, HorizontalAlignment.CENTERED,
        		VerticalAlignment.ABOVE);
    }
}
