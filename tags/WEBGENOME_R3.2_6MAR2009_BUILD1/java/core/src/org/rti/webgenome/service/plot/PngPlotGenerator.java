/*
$Revision: 1.4 $
$Date: 2007-09-13 23:42:17 $

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

package org.rti.webgenome.service.plot;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.graphics.RasterDrawingCanvas;
import org.rti.webgenome.graphics.event.EventHandlerGraphicBoundaries;
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.service.dao.AnnotatedGenomeFeatureDao;
import org.rti.webgenome.service.dao.CytologicalMapDao;
import org.rti.webgenome.service.io.ImageFileManager;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;

/**
 * Implementation of <code>PlotGenerator</code> that generates
 * plots backed by PNG files.
 * @author dhall
 *
 */
public class PngPlotGenerator implements PlotGenerator {
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(PlotGenerator.class);
	
	/** Extra padding around entire plot. */
	private static final int EXTRA_PADDING = 25;
	
	// ============================
	//      Attributes
	// ============================
	
	/** Image file manager. */
	private ImageFileManager imageFileManager = null;
	
	/** Cytlogical map data access object. */
	private CytologicalMapDao cytologicalMapDao = null;
	
	/** Annotated genome feature data access object. */
	private AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao = null;
	
	
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
	 * Set annotated genome feature data access object.
	 * @param annotatedGenomeFeatureDao Annotated genome feature
	 * data access object
	 */
	public final void setAnnotatedGenomeFeatureDao(
			final AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao) {
		this.annotatedGenomeFeatureDao = annotatedGenomeFeatureDao;
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
		
		// Initialize new plot
		Plot plot = new Plot();
		plot.setPlotParameters(plotParameters);
		for (Experiment exp : experiments) {
			plot.addExperiment(exp);
		}
		
		// Provide missing plot parameters not supplied by user
		// by derivation or using defaults
		PlotParameters completeParams = plotParameters.deepCopy();
		completeParams.deriveMissingAttributes(experiments);
		
		// Scatter plot
		if (plotParameters instanceof ScatterPlotParameters) {
			this.newScatterPlot(plot, experiments,
					(ScatterPlotParameters) completeParams,
					chromosomeArrayDataGetter);
			
		// GenomeSnapshot plot
		} else if (plotParameters instanceof GenomeSnapshopPlotParameters) {
			this.newGenomeSnapshotPlot(plot, experiments,
					(GenomeSnapshopPlotParameters) completeParams,
					chromosomeArrayDataGetter);
			
		// Ideogram plot
		} else if (plotParameters instanceof IdeogramPlotParameters) {
			this.newIdeogramPlot(plot, experiments,
					(IdeogramPlotParameters) completeParams,
					chromosomeArrayDataGetter);
			
		// Bar plot
		} else if (plotParameters instanceof BarPlotParameters) {
			this.newBarPlot(plot, experiments,
					(BarPlotParameters) plotParameters,
					chromosomeArrayDataGetter);
			
		// Annotation plot
		} else if (plotParameters instanceof AnnotationPlotParameters) {
			this.newAnnotationPlot(plot, experiments,
					(AnnotationPlotParameters) plotParameters,
					chromosomeArrayDataGetter);
		}
		
		return plot;
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
		
		// Provide missing plot parameters not supplied by user
		// by derivation or using defaults
		PlotParameters completeParams = plotParameters.deepCopy();
		completeParams.deriveMissingAttributes(experiments);
		
		// Replot
		if (plotParameters instanceof ScatterPlotParameters) {
			this.newScatterPlot(plot, experiments,
					(ScatterPlotParameters) completeParams,
					chromosomeArrayDataGetter);
		} else if (plotParameters instanceof GenomeSnapshopPlotParameters) {
			this.newGenomeSnapshotPlot(plot, experiments,
					(GenomeSnapshopPlotParameters) completeParams,
					chromosomeArrayDataGetter);
		} else if (plotParameters instanceof IdeogramPlotParameters) {
			this.newIdeogramPlot(plot, experiments,
					(IdeogramPlotParameters) completeParams,
					chromosomeArrayDataGetter); 
		} else if (plotParameters instanceof BarPlotParameters) {
			this.newBarPlot(plot, experiments,
					(BarPlotParameters) plotParameters,
					chromosomeArrayDataGetter);
		} else if (plotParameters instanceof AnnotationPlotParameters) {
			this.newAnnotationPlot(plot, experiments,
					(AnnotationPlotParameters) plotParameters,
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
	private void newGenomeSnapshotPlot(final Plot plot,
			final Collection<Experiment> experiments,
			final GenomeSnapshopPlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		LOGGER.debug("Creating new genome snapshot plot");
		
		// Instantiate plot painter
		GenomeSnapshotPlotPainter painter =
			new GenomeSnapshotPlotPainter(chromosomeArrayDataGetter);
		
		// Create default plot image
		LOGGER.debug("Creating default plot image");
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		PlotPanel panel = new PlotPanel(canvas);
		painter.paintPlot(panel, experiments, plotParameters);
		panel.paint(canvas);
		canvas.setWidth(panel.width());
		canvas.setHeight(panel.height());
		plot.setWidth(panel.width() + EXTRA_PADDING);		
		plot.setHeight(panel.height());
		String imageFileName =
			this.imageFileManager.saveImage(canvas.toBufferedImage());
		plot.setDefaultImageFileName(imageFileName);
		LOGGER.debug("Completed default plot image");
		LOGGER.debug("Completed genome snapshot plot");
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
		LOGGER.debug("Creating new scatter plot");
		
		// Instantiate plot painter
		ScatterPlotPainter painter =
			new ScatterPlotPainter(chromosomeArrayDataGetter);
		
		// Create default plot image
		LOGGER.debug("Creating default plot image");
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		PlotPanel panel = new PlotPanel(canvas);
		EventHandlerGraphicBoundaries boundaries =
			painter.paintPlot(panel, experiments, plotParameters);
		panel.paint(canvas);
		canvas.setWidth(panel.width());
		canvas.setHeight(panel.height());
		plot.setWidth(panel.width() + EXTRA_PADDING);		
		plot.setHeight(panel.height());
		String imageFileName =
			this.imageFileManager.saveImage(canvas.toBufferedImage());
		plot.setDefaultImageFileName(imageFileName);
		plot.setClickBoxes(boundaries.getClickBoxes());
		plot.setMouseOverStripes(boundaries.getMouseOverStripes());
		LOGGER.debug("Completed default plot image");
		
		// Create images of each bioassay selected
		for (Experiment exp : experiments) {
			for (BioAssay ba : exp.getBioAssays()) {
				LOGGER.debug("Creating image with bioassay "
						+ ba.getName() + " highlighted");
				ba.setSelected(true);
				canvas = new RasterDrawingCanvas();
				panel = new PlotPanel(canvas);
				painter.paintPlot(panel, experiments, plotParameters);
				panel.paint(canvas);
				canvas.setWidth(panel.width());
				canvas.setHeight(panel.height());
				imageFileName =
					this.imageFileManager.saveImage(canvas.toBufferedImage());
				plot.addImageFile(ba.getId().toString(), imageFileName);
				ba.setSelected(false);
				LOGGER.debug("Completed highlighted plot image");
			}
		}
		LOGGER.debug("Completed scatter plot");
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
		
		// Instantiate plot painter
		IdeogramPlotPainter painter =
			new IdeogramPlotPainter(chromosomeArrayDataGetter);
		painter.setCytologicalMapDao(this.cytologicalMapDao);
		
		// Create plot image
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		PlotPanel panel = new PlotPanel(canvas);
		EventHandlerGraphicBoundaries boundaries =
			painter.paintPlot(panel, experiments, plotParameters);
		panel.paint(canvas);
		canvas.setWidth(panel.width());
		canvas.setHeight(panel.height());
		plot.setWidth(panel.width());
		plot.setHeight(panel.height());
		plot.setMouseOverStripes(boundaries.getMouseOverStripes());
		String imageFileName =
			this.imageFileManager.saveImage(canvas.toBufferedImage());
		plot.setDefaultImageFileName(imageFileName);
	}
	
	
	/**
	 * Create new bar plot.
	 * @param plot Plot
	 * @param experiments Experiments to plot
	 * @param plotParameters Plot parameters
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	private void newBarPlot(final Plot plot, 
			final Collection<Experiment> experiments,
			final BarPlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		
		// Instantiate plot painter
		BarPlotPainter painter =
			new BarPlotPainter(chromosomeArrayDataGetter);
		
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
	
	
	/**
	 * Create new annotation plot.
	 * @param plot Plot
	 * @param experiments Experiments to plot
	 * @param plotParameters Plot parameters
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	private void newAnnotationPlot(final Plot plot,
			final Collection<Experiment> experiments,
			final AnnotationPlotParameters plotParameters,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		AnnotationPlotPainter painter =
			new AnnotationPlotPainter(chromosomeArrayDataGetter);
		painter.setAnnotatedGenomeFeatureDao(this.annotatedGenomeFeatureDao);
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
