/*
$Revision: 1.1 $
$Date: 2006-10-07 15:58:49 $

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

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Plot;
import org.rti.webcgh.graphics.RasterDrawingCanvas;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.io.ImageFileManager;
import org.rti.webcgh.service.dao.ShoppingCartDao;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.webui.util.ClickBoxes;

/**
 * Implementation of <code>PlotGenerator</code> that generates
 * plots backed by PNG files.
 * @author dhall
 *
 */
public class PngPlotGenerator implements PlotGenerator {
	
	// ==============================
	//      Constants
	// ==============================
	
	/** Reference file used for finding absolute path to image directory. */
	private static final String REF_FILE_NAME =
		"ApplicationResources.properties";
	
	
	// ============================
	//      Attributes
	// ============================
	
	/** Image file manager. */
	private final ImageFileManager imageFileManager;
	
	
	// ===========================
	//       Constructors
	// ===========================
	
	/**
	 * Constructor.
	 * @param imageDir Directory containing image files.
	 */
	public PngPlotGenerator(final File imageDir) {
		if (imageDir == null || !imageDir.exists()
				|| !imageDir.isDirectory()) {
			throw new IllegalArgumentException("Image directory '"
					+ imageDir.getAbsolutePath() + "' not valid");
		}
		this.imageFileManager = new ImageFileManager(imageDir);
	}
	
	
	/**
	 * Constructor.
	 * @param imageSubContext Subcontext of image directory.
	 * This should be the
	 * directory off the root of the web tree.
	 * @param shoppingCartDao Shopping cart data access object.
	 */
	public PngPlotGenerator(final String imageSubContext,
			final ShoppingCartDao shoppingCartDao) {
		
		// Get image directory
		File imageDir = this.getImageDirectory(imageSubContext);
		if (!imageDir.exists() || !imageDir.isDirectory()) {
			throw new WebcghSystemException("Invalid image directory: "
					+ imageDir.getAbsolutePath());
		}
		
		// Get image files to be preserved when image file manager
		// is initialized
		Collection<String> imagesToSave =
			shoppingCartDao.getAllImageFileNames();
		
		// Initialize the image file manager
		this.imageFileManager = new ImageFileManager(imageDir, imagesToSave);
	}

	
	/**
	 * Get directory containing image files.
	 * @param imageSubContext Subcontext of image directory.
	 * This should be the
	 * directory off the root of the web tree.
	 * @return Directory containing image files.
	 */
	private File getImageDirectory(final String imageSubContext) {
		URL url = ClassLoader.getSystemResource(REF_FILE_NAME);
		URI uri = null;
		try {
			uri = url.toURI();
		} catch (URISyntaxException e) {
			throw new WebcghSystemException(
					"Cannot determine absolute path to image directory", e);
		}
		File refFile = new File(uri);
		if (!refFile.exists()) {
			throw new WebcghSystemException(
					"Cannot determine absolute path to image directory.  "
					+ "Reference file '" + REF_FILE_NAME
					+ "' cannot be found.");
		}
		
		// Now move up two levels to the root of the web tree
		File parent = refFile.getParentFile();
		if (parent == null || !parent.exists()) {
			throw new WebcghSystemException("Cannot determine absolute "
					+ "path to image directory.  Unable to find absolute "
					+ "path to root of web tree.");
		}
		File grandParent = parent.getParentFile();
		if (grandParent == null || !grandParent.exists()) {
			throw new WebcghSystemException("Cannot determine absolute "
					+ "path to image directory.  Unable to find absolute "
					+ "path to root of web tree.");
		}
		
		// We should be in the root of the web tree now.  Create
		// image directory file.
		String imageDirPath = grandParent.getAbsolutePath()
			+ "/" + imageSubContext;
		
		return new File(imageDirPath);
	}
	
	// ==================================
	//      PlotGenerator interface
	// ==================================
	
	/**
	 * Create new plot.
	 * @param experiments Experiments containing data to plot.
	 * @param plotParameters Plot parameters.
	 * @param plotName Plot name
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 * @return A plot.
	 */
	public final Plot newPlot(final Collection<Experiment> experiments,
			final PlotParameters plotParameters, final String plotName,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		Plot plot = new Plot(plotName);
		if (plotParameters instanceof ScatterPlotParameters) {
			this.newScatterPlot(plot, experiments,
					(ScatterPlotParameters) plotParameters,
					chromosomeArrayDataGetter);
		}
		return plot;
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
		
		// Instantiate plot painter
		ScatterPlotPainter painter =
			new ScatterPlotPainter(chromosomeArrayDataGetter);
		
		// Create default plot image
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		PlotPanel panel = new PlotPanel(canvas);
		Collection<ClickBoxes> clickBoxes =
			painter.paintPlot(panel, experiments, plotParameters);
		String imageFileName =
			this.imageFileManager.saveImage(canvas.toBufferedImage());
		plot.setDefaultImageFileName(imageFileName);
		plot.setClickBoxes(clickBoxes);
		
		// Create images of each bioassay selected
		for (Experiment exp : experiments) {
			for (BioAssay ba : exp.getBioAssays()) {
				ba.setSelected(true);
				canvas = new RasterDrawingCanvas();
				panel = new PlotPanel(canvas);
				painter.paintPlot(panel, experiments, plotParameters);
				imageFileName =
					this.imageFileManager.saveImage(canvas.toBufferedImage());
				plot.addImageFile(imageFileName, imageFileName);
				ba.setSelected(false);
			}
		}
	}
}
