/*
$Revision: 1.4 $
$Date: 2007-09-11 22:52:24 $


*/

package org.rti.webgenome.service.plot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ExperimentGenerator;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.graphics.InterpolationType;
import org.rti.webgenome.graphics.widget.RasterFileTestPlotPanel;
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
        	Experiment copyNumberExp =
        		expGen.newInMemoryExperiment(NUM_BIO_ASSAYS,
	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME);
        	Experiment expressionExp =
        		expGen.newInMemoryExperiment(NUM_BIO_ASSAYS,
	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME);
	        experiments.add(copyNumberExp);
	        experiments.add(expressionExp);
	        copyNumberExp.setQuantitationType(
	        		QuantitationType.LOG_2_RATIO_COPY_NUMBER);
	        expressionExp.setQuantitationType(
	        		QuantitationType.LOG_2_RATIO_FOLD_CHANGE);
	        for (BioAssay ba : copyNumberExp.getBioAssays()) {
	        	ba.setColor(Color.BLUE);
	        }
	        for (BioAssay ba : expressionExp.getBioAssays()) {
	        	ba.setColor(Color.RED);
	        }
        }
        
        // Create plot parameters
        ScatterPlotParameters params = new ScatterPlotParameters();
        for (short i = 1; i <= NUM_CHROMOSOMES; i++) {
        	params.add(new GenomeInterval(i, (short) 0, CHROM_LENGTH));
        }

        params.setExpressionMaxY(
        		Experiment.findMaxExpressionValue(experiments) + (float) 3.2);
        params.setExpressionMinY(
        		Experiment.findMinExpressionValue(experiments) - (float) 1.3);
        params.setCopyNumberMaxY(
        		Experiment.findMaxCopyNumberValue(experiments));
        params.setCopyNumberMinY(
        		Experiment.findMinCopyNumberValue(experiments));
        params.setUnits(BpUnits.KB);
        params.setNumPlotsPerRow(2);
        params.setWidth(WIDTH);
        params.setHeight(HEIGHT);
        params.setInterpolationType(InterpolationType.SPLINE);
        
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
