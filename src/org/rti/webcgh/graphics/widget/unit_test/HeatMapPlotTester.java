/*
$Revision: 1.5 $
$Date: 2006-10-21 21:04:56 $

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

package org.rti.webcgh.graphics.widget.unit_test;

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ExperimentGenerator;
import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.util.HeatMapColorFactory;
import org.rti.webcgh.graphics.widget.HeatMapPlot;
import org.rti.webcgh.service.io.DataFileManager;
import org.rti.webcgh.service.plot.IdeogramPlotParameters;
import org.rti.webcgh.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webcgh.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webcgh.util.FileUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>HeatMapColorPlot</code>.
 * @author dhall
 *
 */
public final class HeatMapPlotTester extends TestCase {
	
	/** Chromosome number. */
	private static final short CHROMOSOME = (short) 1;
	
	/** Number of test experiments to generate. */
	private static final int NUM_EXPERIMENTS = 2;
	
	/** Number of bioassays per experiment to generate. */
	private static final int NUM_BIO_ASSAYS = 4;
	
	/** Number of chromosomes per bioassay to generate. */
	private static final int NUM_CHROMOSOMES = 1;
	
	/** Number of data points per chromosome to generate. */
	private static final int NUM_DATUM = 100;
	
	/** Size of chromosome in base pairs. */
	private static final long CHROM_SIZE = 100000000;
	
	/** Gap between generated reporters in base pairs. */
	private static final long GAP = CHROM_SIZE / NUM_DATUM;
	
	/** Number of color bins. */
	private static final int NUM_BINS = 16;
	
    /**
     * Name of directory holding graphic files produced
     * during tests.  The absolute path will be a
     * concatenation of the 'test.dir' property in
     * the file 'unit_test.properties' and this
     * constant.
     */
    private static final String TEST_DIR_NAME = "heat-map-plot-tester";
	
	
	/**
	 * Test paint() method on data in memory.
	 */
	public void testPaintInMemory() {
		
		// Instantiate plot panel
		RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(
            		FileUtils.createUnitTestDirectory(TEST_DIR_NAME));
		
		// Create experiments
		ExperimentGenerator gen = new ExperimentGenerator(GAP);
		Collection<Experiment> experiments = new ArrayList<Experiment>();
		for (int i = 0; i < NUM_EXPERIMENTS; i++) {
			Experiment exp = gen.newInMemoryExperiment(NUM_BIO_ASSAYS,
					NUM_CHROMOSOMES, NUM_DATUM);
			experiments.add(exp);
		}
		
		// Create plot parameters
		IdeogramPlotParameters params = new IdeogramPlotParameters();
		
		// Create heat map color code factory
		HeatMapColorFactory fac = new HeatMapColorFactory(
				0, params.getMaxSaturation(),
				NUM_BINS);
		
		// Create chromosome array data getter
		InMemoryChromosomeArrayDataGetter getter =
			new InMemoryChromosomeArrayDataGetter();
		
		// Plot data
		HeatMapPlot plot = new HeatMapPlot(experiments, CHROMOSOME, fac, params,
				getter, panel.getDrawingCanvas());
		panel.add(plot);
		
		// Generate PNG file
		panel.toPngFile("in-memory.png");
	}
	
	
	/**
	 * Test paint() method on data that have been
	 * serialized to disk.
	 */
	public void testPaintSerialized() {
		
		String testDirPath = 
    		FileUtils.createUnitTestDirectory(TEST_DIR_NAME).getAbsolutePath();
		
		// Instantiate plot panel
		RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(testDirPath);
		
		// Create experiments
		DataFileManager mgr = new DataFileManager(testDirPath);
		ExperimentGenerator gen = new ExperimentGenerator(GAP);
		Collection<Experiment> experiments = new ArrayList<Experiment>();
		for (int i = 0; i < NUM_EXPERIMENTS; i++) {
			Experiment exp = gen.newDataSerializedExperiment(
					NUM_BIO_ASSAYS, NUM_CHROMOSOMES, NUM_DATUM, mgr);
			experiments.add(exp);
		}
		
		// Create plot parameters
		IdeogramPlotParameters params = new IdeogramPlotParameters();
		
		// Create heat map color code factory
		HeatMapColorFactory fac = new HeatMapColorFactory(
				0, params.getMaxSaturation(),
				NUM_BINS);
		
		// Create chromosome array data getter
		SerializedChromosomeArrayDataGetter getter =
			new SerializedChromosomeArrayDataGetter();
		getter.setDataFileManager(mgr);
		
		// Plot data
		HeatMapPlot plot = new HeatMapPlot(experiments, CHROMOSOME, fac, params,
				getter, panel.getDrawingCanvas());
		panel.add(plot);
		
		// Generate PNG file
		panel.toPngFile("serialized.png");
		
		// Clean up
		for (Experiment exp : experiments) {
			mgr.deleteDataFiles(exp, true);
		}
	}
}
