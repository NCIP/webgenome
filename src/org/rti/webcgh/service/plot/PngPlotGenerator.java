/*
$Revision: 1.10 $
$Date: 2006-10-24 01:41:08 $

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
import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.Plot;
import org.rti.webcgh.graphics.RasterDrawingCanvas;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.service.dao.CytologicalMapDao;
import org.rti.webcgh.service.io.ImageFileManager;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.webui.util.EventHandlerGraphicBoundaries;

/**
 * Implementation of <code>PlotGenerator</code> that generates
 * plots backed by PNG files.
 * @author dhall
 *
 */
public class PngPlotGenerator implements PlotGenerator {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(PlotGenerator.class);
	
	// ============================
	//      Attributes
	// ============================
	
	/** Image file manager. */
	private ImageFileManager imageFileManager = null;
	
	/** Cytlogical map data access object. */
	private CytologicalMapDao cytologicalMapDao = null;
	
	
	// =========================
	//      Setters
	// =========================
	
	/**
	 * Set image file manager.
	 * @param imageFileManager Image file manager
	 */
	public final void setImageFileManager(
			final ImageFileManager imageFileManager) {
		this.imageFileManager = imageFileManager;
	}
	
	
	/**
	 * Set cytological map data access object.
	 * @param cytologicalMapDao Cytological map data access object.
	 */
	public final void setCytologicalMapDao(
			final CytologicalMapDao cytologicalMapDao) {
		this.cytologicalMapDao = cytologicalMapDao;
	}
	
	// ===========================
	//       Constructors
	// ===========================
	


	/**
	 * Constructor.
	 */
	public PngPlotGenerator() {
		
	}
	
	
	// ==================================
	//      PlotGenerator interface
	// ==================================
	
	/**
	 * Create new plot.
	 * @param experiments Experiments containing data to plot.
	 * @param plotParameters Plot parameters.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 * @return A plot.
	 */
	public final Plot newPlot(final Collection<Experiment> experiments,
			final PlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		Plot plot = new Plot();
		plot.setPlotParameters(plotParameters);
		for (Experiment exp : experiments) {
			plot.addExperimentId(exp.getId());
		}
		if (plotParameters instanceof ScatterPlotParameters) {
			
			// Set left and right endpoints of genome intervals
			// in case user only specified chromosome locations
			this.fixGenomeIntervals(plotParameters.getGenomeIntervals(),
					experiments);
			
			this.newScatterPlot(plot, experiments,
					(ScatterPlotParameters) plotParameters,
					chromosomeArrayDataGetter);
		} else if (plotParameters instanceof IdeogramPlotParameters) {
			this.newIdeogramPlot(plot, experiments,
					(IdeogramPlotParameters) plotParameters,
					chromosomeArrayDataGetter);
		}
		return plot;
	}
	
	/**
	 * Fix genome intervals by setting chromosomal end points.  User may
	 * have only specified chromosome numbers.  Endpoints are set
	 * to 0 and the length of the chromosome inferred from the
	 * experimental data (i.e., the position of the right-most
	 * reporter).
	 * @param intervals Genome intervals
	 * @param experiments Experiments
	 */
	private void fixGenomeIntervals(final Collection<GenomeInterval> intervals,
			final Collection<Experiment> experiments) {
		for (GenomeInterval gi : intervals) {
			if (gi.getStartLocation() < 0) {
				gi.setStartLocation(0);
			}
			if (gi.getEndLocation() < 0) {
				long end = Experiment.inferredChromosomeSize(experiments,
						gi.getChromosome());
				gi.setEndLocation(end);
			}
		}
	}
	
	
	/**
	 * Replot data.
	 * @param plot Plot to redo.
	 * @param experiments Experiments containing data to plot.
	 * @param plotParameters Plot parameters.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	public final void replot(final Plot plot,
			final Collection<Experiment> experiments,
			final PlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		plot.setPlotParameters(plotParameters);
		
		// Get rid of image files
		for (String fname : plot.getAllImageFileNames()) {
			this.imageFileManager.deleteImageFile(fname);
		}
		plot.getImageFileMap().clear();
		
		// Replot
		if (plotParameters instanceof ScatterPlotParameters) {
			this.newScatterPlot(plot, experiments,
					(ScatterPlotParameters) plotParameters,
					chromosomeArrayDataGetter);
		} else if (plotParameters instanceof IdeogramPlotParameters) {
			this.newIdeogramPlot(plot, experiments,
					(IdeogramPlotParameters) plotParameters,
					chromosomeArrayDataGetter); 
		}
	}
	
	
	/**
	 * Create new plot.
	 * @param plot Plot
	 * @param experiments Experiments containing data to plot.
	 * @param plotParameters Plot parameters.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	private void newScatterPlot(final Plot plot,
			final Collection<Experiment> experiments,
			final ScatterPlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		LOGGER.info("Creating new scatter plot");
		
		// Make sure plot parameters are okay
		Set<Short> chromosomes = GenomeInterval.getChromosomes(
				plotParameters.getGenomeIntervals());
		if (Float.isNaN(plotParameters.getMinY())) {
			float min = Experiment.findMinValue(experiments, chromosomes);
			plotParameters.setMinY(min);
		}
		if (Float.isNaN(plotParameters.getMaxY())) {
			float max = Experiment.findMaxValue(experiments, chromosomes);
			plotParameters.setMaxY(max);
		}
		
		// Instantiate plot painter
		ScatterPlotPainter painter =
			new ScatterPlotPainter(chromosomeArrayDataGetter);
		
		// Create default plot image
		LOGGER.info("Creating default plot image");
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		PlotPanel panel = new PlotPanel(canvas);
		EventHandlerGraphicBoundaries boundaries =
			painter.paintPlot(panel, experiments, plotParameters);
		panel.paint(canvas);
		canvas.setWidth(panel.width());
		canvas.setHeight(panel.height());
		plot.setWidth(panel.width());
		plot.setHeight(panel.height());
		String imageFileName =
			this.imageFileManager.saveImage(canvas.toBufferedImage());
		plot.setDefaultImageFileName(imageFileName);
		plot.setClickBoxes(boundaries.getClickBoxes());
		plot.setMouseOverStripes(boundaries.getMouseOverStripes());
		LOGGER.info("Completed default plot image");
		
		// Create images of each bioassay selected
		for (Experiment exp : experiments) {
			for (BioAssay ba : exp.getBioAssays()) {
				LOGGER.info("Creating image with bioassay "
						+ ba.getName() + "hilighted");
				ba.setSelected(true);
				canvas = new RasterDrawingCanvas();
				panel = new PlotPanel(canvas);
				painter.paintPlot(panel, experiments, plotParameters);
				panel.paint(canvas);
				canvas.setWidth(panel.width());
				canvas.setHeight(panel.height());
				imageFileName =
					this.imageFileManager.saveImage(canvas.toBufferedImage());
				plot.addImageFile(imageFileName, imageFileName);
				ba.setSelected(false);
				LOGGER.info("Completed hilighted plot image");
			}
		}
		LOGGER.info("Completed scatter plot");
	}
	
	
	/**
	 * Create new ideogram plot.
	 * @param plot Plot
	 * @param experiments Experiments to plot
	 * @param plotParameters Plot parameters
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	private void newIdeogramPlot(final Plot plot, 
			final Collection<Experiment> experiments,
			final IdeogramPlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		
		// Make sure plot parameters okay
		Set<Short> chromosomes = GenomeInterval.getChromosomes(
				plotParameters.getGenomeIntervals());
		if (Float.isNaN(plotParameters.getMinSaturation())) {
			float min = Experiment.findMinValue(experiments, chromosomes);
			plotParameters.setMinSaturation(min);
		}
		if (Float.isNaN(plotParameters.getMaxSaturation())) {
			float max = Experiment.findMaxValue(experiments, chromosomes);
			plotParameters.setMaxSaturation(max);
		}
		
		// Instantiate plot painter
		IdeogramPlotPainter painter =
			new IdeogramPlotPainter(chromosomeArrayDataGetter);
		painter.setCytologicalMapDao(this.cytologicalMapDao);
		
		// Create plot image
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		PlotPanel panel = new PlotPanel(canvas);
		painter.paintPlot(panel, experiments, plotParameters);
		panel.paint(canvas);
		canvas.setWidth(panel.width());
		canvas.setHeight(panel.height());
		plot.setWidth(panel.width());
		plot.setHeight(panel.height());
		String imageFileName =
			this.imageFileManager.saveImage(canvas.toBufferedImage());
		plot.setDefaultImageFileName(imageFileName);
	}
}
