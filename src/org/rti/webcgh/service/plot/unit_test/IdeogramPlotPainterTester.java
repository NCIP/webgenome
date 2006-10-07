/*
$Revision: 1.10 $
$Date: 2006-10-07 15:58:52 $

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
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ExperimentGenerator;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.Caption;
import org.rti.webcgh.service.plot.IdeogramPlotPainter;
import org.rti.webcgh.service.plot.IdeogramPlotParameters;
import org.rti.webcgh.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webcgh.units.ChromosomeIdeogramSize;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;
import org.rti.webcgh.util.FileUtils;

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
	private static final String TEMP_DIR_PATH =
		FileUtils.createUnitTestDirectory(TEMP_DIR_NAME).getAbsolutePath();
	
	/** Number of bioassays to generate in tests. */
	private static final int NUM_BIO_ASSAYS = 2;
	
	/** Number of chromosomes in tests. */
	private static final int NUM_CHROMOSOMES = 5;
	
	/** Number of experiments in tests. */
	private static final int NUM_EXPERIMENTS = 2;
		
	/** Number of array datum per chromosome in. */
	private static final int NUM_DATUM_PER_CHROMOSOME = 50;
	
	/** Length of chromosome in base pairs. */
	private static final long CHROM_LENGTH = 100000000;
	
	/** Number of cytobands. */
	private static final int NUM_CYTOBANDS = 10;
	
	// ===================================
	//     Test cases
	// ===================================

	/**
	 * Test paintIdeogramPlot() method.
	 * @throws Exception if something bad happens
	 */
	public void testPaintIdeogramPlot() throws Exception {
		
		// Create test experiments
		long gap = CHROM_LENGTH / NUM_DATUM_PER_CHROMOSOME;
		ExperimentGenerator expGen = new ExperimentGenerator(gap);
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
        IdeogramPlotParameters params = new IdeogramPlotParameters();
        for (int i = 1; i <= NUM_CHROMOSOMES; i++) {
        	params.add(new GenomeInterval((short) i, 1, CHROM_LENGTH));
        }
        params.setIdeogramSize(ChromosomeIdeogramSize.MEDIUM);
        params.setNumPlotsPerRow(2);
        
        // Create plotting panel
        RasterFileTestPlotPanel panel =
        	new RasterFileTestPlotPanel(TEMP_DIR_PATH);
        
        // Create chromosome array data getter
        InMemoryChromosomeArrayDataGetter cadg =
        	new InMemoryChromosomeArrayDataGetter();
        
        // Instantiate ideogram plot painter
        IdeogramPlotPainter painter =
        	new IdeogramPlotPainter(cadg);
       
        // Create cytologial map DAO
        CytologicalMapDaoImpl cDao =
        	new CytologicalMapDaoImpl(CHROM_LENGTH, NUM_CYTOBANDS);
        painter.setCytologicalMapDao(cDao);
        
        // Create plot
        painter.paintPlot(panel, experiments, params);
        
        // Add some additional reference widgets
        panel.add(new Caption("Left", null, Orientation.HORIZONTAL, false,
        		panel.getDrawingCanvas()),
        		HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
        panel.add(new Caption("Right", null, Orientation.HORIZONTAL, false,
        		panel.getDrawingCanvas()),
        		HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
        
        // Output graphics to file
        panel.toPngFile("plot-in-memory.png");
	}
}
