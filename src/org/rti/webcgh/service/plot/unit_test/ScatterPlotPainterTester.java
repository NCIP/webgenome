/*
$Revision: 1.3 $
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

package org.rti.webcgh.service.plot.unit_test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ExperimentGenerator;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.graphics.RasterDrawingCanvas;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.io.DataFileManager;
import org.rti.webcgh.service.plot.ScatterPlotPainter;
import org.rti.webcgh.service.plot.ScatterPlotParameters;
import org.rti.webcgh.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webcgh.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webcgh.util.FileUtils;

import junit.framework.TestCase;


/**
 * Tester for <code>ScatterPlotPainter</code>.
 * @author dhall
 *
 */
public final class ScatterPlotPainterTester extends TestCase {
	
	// ==========================
	//      Constants
	// ==========================
	
	/**
	 * Name of temporary directory for storing
	 * generated ata files.  This is not an absolute
	 * path.
	 */
	private static final String TEMP_DIR_NAME =
		"scatter_plot_painter_test_dir";
	
	/**
	 * Path to temporary directory for storing data files.
	 * It will be a subdirectory of the main
	 * unit test temporary directory specified
	 * by the property 'temp.dir' in 'unit_test.properties.'
	 */
	private static final String TEMP_DIR_PATH =
		FileUtils.createUnitTestDirectory(TEMP_DIR_NAME).getAbsolutePath();
	
	/** Number of bioassays to generate in tests. */
	private static final int NUM_BIO_ASSAYS = 2;
	
	/** Number of chromosomes in tests. */
	private static final int NUM_CHROMOSOMES = 2;
	
	/** Number of experiments in tests. */
	private static final int NUM_EXPERIMENTS = 2;
	
	/**
	 * Number of array datum per chromosome in
	 * serialized data tests.
	 */
	private static final int NUM_DATUM_PER_CHROMOSOME_SERIALIZED = 5000;
	
	/**
	 * Number of array datum per chromosome in
	 * in-memory data tests.
	 */
	private static final int NUM_DATUM_PER_CHROMOSOME_IN_MEMORY = 50;
	
    /** Minimum Y-axis value. */
    private static final float MIN_Y = (float) -2.0;
    
    /** Maximum Y-axis value. */
    private static final float MAX_Y = (float) 2.0;
    
    /** Width of plot in pixels. */
    private static final int WIDTH = 500;
    
    /** Height of plot in pixels. */
    private static final int HEIGHT = 500;

	
	// ============================
	//     Test cases
	// ============================
	
//	/**
//	 * Test paintScatterPlot() method on serialized data.
//	 * @throws Exception if anything bad happens
//	 */
//	public void testPaintScatterPlotSerialized() throws Exception {
//		
//		// Instantiate data file manager
//		DataFileManager dataFileManager = new DataFileManager(TEMP_DIR_PATH);
//		
//		// Create test experiments
//		ExperimentGenerator expGen = new ExperimentGenerator();
//        Collection<Experiment> experiments = new ArrayList<Experiment>();
//        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
//        	Experiment exp = expGen.newDataSerializedExperiment(NUM_BIO_ASSAYS,
//	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME_SERIALIZED,
//	        		dataFileManager);
//	        experiments.add(exp);
//	        for (BioAssay ba : exp.getBioAssays()) {
//	        	ba.setColor(Color.BLUE);
//	        }
//        }
//        
//        // Create plot parameters
//        ScatterPlotParameters params = new ScatterPlotParameters();
//        List<Reporter> reporters = expGen.getReporters();
//        params.setChromosome(reporters.get(0).getChromosome());
//        params.setStartLocation(reporters.get(0).getLocation());
//        params.setEndLocation(reporters.get(
//                reporters.size() - 1).getLocation());
//        params.setMinY(MIN_Y);
//        params.setMaxY(MAX_Y);
//        
//        // Create plotting panel
//        RasterDrawingCanvas canvas = new RasterDrawingCanvas();
//        PlotPanel panel = new PlotPanel(canvas);
//        
//        // Create chromosome array data getter
//        SerializedChromosomeArrayDataGetter cadg =
//        	new SerializedChromosomeArrayDataGetter();
//        cadg.setDataFileManager(dataFileManager);
//        
//        // Run method
//        ScatterPlotPainter painter =
//        	new ScatterPlotPainter(cadg);
//        painter.paintScatterPlot(panel, experiments, params, WIDTH, HEIGHT,
//                QuantitationType.LOG_2_RATIO);
//        
//        // Adjust canvas properties
//        canvas.setOrigin(panel.topLeftPoint());
//        canvas.setWidth(panel.width());
//        canvas.setHeight(panel.height());
//        
//        // Output graphics to file
//        String filePath = TEMP_DIR_PATH + "/plot-serialized.png";
//        BufferedImage img = canvas.toBufferedImage();
//        ImageIO.write(img, "png", new File(filePath));
//        
//        // Clean up
//        for (Experiment exp : experiments) {
//        	dataFileManager.deleteDataFiles(exp, true);
//        }
//	}
//	
//	
//	/**
//	 * Test paintScatterPlot() method on in-memory data.
//	 * @throws Exception if anything bad happens
//	 */
//	public void testPaintScatterPlotInMemory() throws Exception {
//		
//		// Create test experiments
//		ExperimentGenerator expGen = new ExperimentGenerator();
//        Collection<Experiment> experiments = new ArrayList<Experiment>();
//        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
//        	Experiment exp = expGen.newInMemoryExperiment(NUM_BIO_ASSAYS,
//	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME_IN_MEMORY);
//	        experiments.add(exp);
//	        for (BioAssay ba : exp.getBioAssays()) {
//	        	ba.setColor(Color.BLUE);
//	        }
//        }
//        
//        // Create plot parameters
//        ScatterPlotParameters params = new ScatterPlotParameters();
//        List<Reporter> reporters = expGen.getReporters();
//        params.setChromosome(reporters.get(0).getChromosome());
//        params.setStartLocation(reporters.get(0).getLocation());
//        params.setEndLocation(reporters.get(
//                reporters.size() - 1).getLocation());
//        params.setMinY(MIN_Y);
//        params.setMaxY(MAX_Y);
//        
//        // Create plotting panel
//        RasterDrawingCanvas canvas = new RasterDrawingCanvas();
//        PlotPanel panel = new PlotPanel(canvas);
//        
//        // Create chromosome array data getter
//        InMemoryChromosomeArrayDataGetter cadg =
//        	new InMemoryChromosomeArrayDataGetter();
//        
//        // Run method
//        ScatterPlotPainter painter =
//        	new ScatterPlotPainter(cadg);
//        painter.paintScatterPlot(panel, experiments, params, WIDTH, HEIGHT,
//                QuantitationType.LOG_2_RATIO);
//        
//        // Adjust canvas properties
//        canvas.setOrigin(panel.topLeftPoint());
//        canvas.setWidth(panel.width());
//        canvas.setHeight(panel.height());
//        
//        // Output graphics to file
//        String filePath = TEMP_DIR_PATH + "/plot-in-memory.png";
//        BufferedImage img = canvas.toBufferedImage();
//        ImageIO.write(img, "png", new File(filePath));
//	}
}
