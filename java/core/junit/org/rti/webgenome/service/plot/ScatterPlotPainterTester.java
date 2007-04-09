/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $

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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ExperimentGenerator;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.graphics.widget.RasterFileTestPlotPanel;
import org.rti.webgenome.service.plot.ScatterPlotPainter;
import org.rti.webgenome.service.plot.ScatterPlotParameters;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.util.UnitTestUtils;

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
		UnitTestUtils.createUnitTestDirectory(TEMP_DIR_NAME).getAbsolutePath();
	
	/** Number of bioassays to generate in tests. */
	private static final int NUM_BIO_ASSAYS = 3;
	
	/** Number of chromosomes in tests. */
	private static final int NUM_CHROMOSOMES = 1;
	
	/** Number of experiments in tests. */
	private static final int NUM_EXPERIMENTS = 3;
	
    /** Width of plot in pixels. */
    private static final int WIDTH = 500;
    
    /** Height of plot in pixels. */
    private static final int HEIGHT = 500;
    
    /** Gap between generated reporters. */
    private static final long GAP = 1000000;
    
    /** Number of datum per chromosome. */
    private static final int NUM_DATUM_PER_CHROMOSOME = 50;
    
    /** Length of generated chromosome. */
    private static final long CHROM_LENGTH =
    	GAP * NUM_DATUM_PER_CHROMOSOME;

	
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
//		expGen.setGap(GAP);
//        Collection<Experiment> experiments = new ArrayList<Experiment>();
//        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
//        	Experiment exp = expGen.newDataSerializedExperiment(NUM_BIO_ASSAYS,
//	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME,
//	        		dataFileManager);
//	        experiments.add(exp);
//	        for (BioAssay ba : exp.getBioAssays()) {
//	        	ba.setColor(Color.BLUE);
//	        }
//        }
//        
//        // Create plot parameters
//        ScatterPlotParameters params = new ScatterPlotParameters();
//        for (short i = 1; i <= NUM_CHROMOSOMES; i++) {
//        	params.add(new GenomeInterval(i, (short) 1, CHROM_LENGTH));
//        }
//        params.setMinY(MIN_Y);
//        params.setMaxY(MAX_Y);
//        
//        // Create plotting panel
//        RasterFileTestPlotPanel panel =
//        	new RasterFileTestPlotPanel(TEMP_DIR_PATH);
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
//        // Output graphics to file
//        panel.toPngFile("plot-serialized.png");
//        
//        // Clean up
//        for (Experiment exp : experiments) {
//        	dataFileManager.deleteDataFiles(exp, true);
//        }
//	}
//	
	
	/**
	 * Test paintScatterPlot() method on in-memory data.
	 * @throws Exception if anything bad happens
	 */
	public void testPaintScatterPlotInMemory() throws Exception {
		
		// Create test experiments
		ExperimentGenerator expGen = new ExperimentGenerator();
		expGen.setGap(GAP);
        Collection<Experiment> experiments = new ArrayList<Experiment>();
        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
        	Experiment exp = expGen.newInMemoryExperiment(NUM_BIO_ASSAYS,
	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME);
	        experiments.add(exp);
	        for (BioAssay ba : exp.getBioAssays()) {
	        	ba.setColor(Color.BLUE);
	        }
        }
        
        // Create plot parameters
        ScatterPlotParameters params = new ScatterPlotParameters();
        for (short i = 1; i <= NUM_CHROMOSOMES; i++) {
        	params.add(new GenomeInterval(i, (short) 0, CHROM_LENGTH));
        }
        params.setMinY(Experiment.findMinValue(experiments));
        params.setMaxY(Experiment.findMaxValue(experiments));
        params.setUnits(BpUnits.KB);
        params.setNumPlotsPerRow(2);
        params.setWidth(WIDTH);
        params.setHeight(HEIGHT);
        
        // Create plotting panel
        RasterFileTestPlotPanel panel =
        	new RasterFileTestPlotPanel(TEMP_DIR_PATH);
        
        // Create chromosome array data getter
        InMemoryChromosomeArrayDataGetter cadg =
        	new InMemoryChromosomeArrayDataGetter();
        
        // Run method
        ScatterPlotPainter painter =
        	new ScatterPlotPainter(cadg);
        painter.paintPlot(panel, experiments, params);
        
        // Output graphics to file
        panel.toPngFile("plot-in-memory.png");
	}
}
