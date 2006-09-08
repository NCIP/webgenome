/*
$Revision: 1.1 $
$Date: 2006-09-08 03:06:50 $

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

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.CytologicalMap;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ExperimentGenerator;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.graphics.RasterDrawingCanvas;
import org.rti.webcgh.graphics.util.
	ClassPathPropertiesFileRgbHexidecimalColorMapper;
import org.rti.webcgh.graphics.util.ColorMapper;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.service.plot.IdeogramPlotPainter;
import org.rti.webcgh.service.plot.IdeogramPlotParameters;
import org.rti.webcgh.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webcgh.units.ChromosomeIdeogramSize;
import org.rti.webcgh.util.FileUtils;
import org.rti.webcgh.util.SystemUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>IdeogramPlotPainter</code>.
 * @author dhall
 *
 */
public final class IdeogramPlotPainterTester extends TestCase {
	
	// ===============================
	//     Constants
	// ===============================
	
	/** Height of centromere in native units. */
	private static final long CENTROMERE_HEIGHT = 10;
	
	/**
	 * Name of temporary directory for storing
	 * generated ata files.  This is not an absolute
	 * path.
	 */
	private static final String TEMP_DIR_NAME =
		"ideogram_plot_painter_test_dir";
	
	/**
	 * Path to temporary directory for storing data files.
	 * It will be a subdirectory of the main
	 * unit test temporary directory specified
	 * by the property 'temp.dir' in 'unit_test.properties.'
	 */
	private static final String TEMP_DIR_PATH;
	
	// Initialize TEMP_DIR
	static {
		String tempDirParent =
			SystemUtils.getUnitTestProperty("temp.dir");
		if (tempDirParent == null) {
			throw new WebcghSystemException(
					"Unit test property 'temp.dir' must be set");
		}
		TEMP_DIR_PATH = tempDirParent + "/" + TEMP_DIR_NAME;
		FileUtils.createDirectory(TEMP_DIR_PATH);
	}
	
	/** Path to file containing color mappings for cytobands. */
	private static final String COLOR_MAPPING_FILE_PATH =
		"org/rti/webcgh/service/plot/unit_test/"
		+ "ideogram_plot_painter_test_files/color-mappings.properties";
	
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
	
	// ===================================
	//     Test cases
	// ===================================

	/**
	 * Test paintIdeogramPlot() method.
	 * @throws Exception if something bad happens
	 */
	public void testPaintIdeogramPlot() throws Exception {
		
		// Create test experiments
		ExperimentGenerator expGen = new ExperimentGenerator();
        Collection<Experiment> experiments = new ArrayList<Experiment>();
        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
        	Experiment exp = expGen.newInMemoryExperiment(NUM_BIO_ASSAYS,
	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME_IN_MEMORY);
	        experiments.add(exp);
	        for (BioAssay ba : exp.getBioAssays()) {
	        	ba.setColor(Color.BLUE);
	        }
        }
        
        // Create plot parameters
        IdeogramPlotParameters params = new IdeogramPlotParameters();
        List<Reporter> reporters = expGen.getReporters();
        short chromosome = reporters.get(0).getChromosome();
        long start = reporters.get(0).getLocation();
        long end = reporters.get(
                reporters.size() - 1).getLocation();
        params.setChromosome(chromosome);
        params.setStartLocation(start);
        params.setEndLocation(end);
        params.setIdeogramSize(ChromosomeIdeogramSize.MEDIUM);
        
        // Create plotting panel
        RasterDrawingCanvas canvas = new RasterDrawingCanvas();
        PlotPanel panel = new PlotPanel(canvas);
        
        // Create chromosome array data getter
        InMemoryChromosomeArrayDataGetter cadg =
        	new InMemoryChromosomeArrayDataGetter();
        
        // Instantiate color chooser
        ColorMapper colorMapper = new
        	ClassPathPropertiesFileRgbHexidecimalColorMapper(
        			COLOR_MAPPING_FILE_PATH);
        
        // Instantiate ideogram plot painter
        IdeogramPlotPainter painter =
        	new IdeogramPlotPainter(cadg, colorMapper);
        
        // Create cytologial map
        long centMid = (start + end) / 2;
        long centStart = centMid - CENTROMERE_HEIGHT / 2;
        long centEnd = centMid + CENTROMERE_HEIGHT / 2;
        CytologicalMap map =
        	new CytologicalMap(chromosome, centStart, centEnd);
        
        // Create plot
        painter.paintIdeogramPlot(panel, experiments, map, params);
        
        // Adjust canvas properties
        canvas.setOrigin(panel.topLeftPoint());
        canvas.setWidth(panel.width());
        canvas.setHeight(panel.height());
        
        // Output graphics to file
        String filePath = TEMP_DIR_PATH + "/plot-in-memory.png";
        BufferedImage img = canvas.toBufferedImage();
        ImageIO.write(img, "png", new File(filePath));
	}
}
