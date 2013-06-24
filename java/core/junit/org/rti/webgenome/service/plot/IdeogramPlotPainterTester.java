/*
$Revision: 1.3 $
$Date: 2007-09-06 16:48:11 $


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
import org.rti.webgenome.graphics.widget.Caption;
import org.rti.webgenome.graphics.widget.RasterFileTestPlotPanel;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.units.ChromosomeIdeogramSize;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

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
		UnitTestUtils.createUnitTestDirectory(TEMP_DIR_NAME).getAbsolutePath();
	
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
        	exp.setQuantitationType(QuantitationType.COPY_NUMBER);
	        experiments.add(exp);
	        for (BioAssay ba : exp.getBioAssays()) {
	        	ba.setColor(Color.BLUE);
	        }
        }
        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
        	Experiment exp = expGen.newInMemoryExperiment(NUM_BIO_ASSAYS,
	        		NUM_CHROMOSOMES, NUM_DATUM_PER_CHROMOSOME);
        	exp.setQuantitationType(QuantitationType.FOLD_CHANGE);
	        experiments.add(exp);
	        for (BioAssay ba : exp.getBioAssays()) {
	        	ba.setColor(Color.RED);
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
