/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.graphics.widget;

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ExperimentGenerator;
import org.rti.webgenome.graphics.util.HeatMapColorFactory;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.service.plot.IdeogramPlotParameters;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webgenome.util.UnitTestUtils;

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
            		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME));
		
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
				0, params.getCopyNumberMaxSaturation(),
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
    		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME).
    		getAbsolutePath();
		
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
				0, params.getCopyNumberMaxSaturation(),
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
