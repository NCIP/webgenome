/*
$Revision: 1.4 $
$Date: 2007-09-06 16:48:11 $


*/

package org.rti.webgenome.service.plot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.ExperimentDTO;
import org.rti.webgenome.client.ExperimentDTOGenerator;
import org.rti.webgenome.client.QuantitationTypes;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.graphics.widget.RasterFileTestPlotPanel;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.util.UnitTestUtils;


/**
 * Tester for <code>BarPlotPainter</code>.
 * @author dhall
 *
 */
public final class BarPlotPainterTester extends TestCase {
	
	/**
	 * Name of directory that will contain test output files.
	 * The absolute path will be a subdirectory to the
	 * main test directory defined by the property
	 * 'temp.dir' in the file 'unit_test.properties.'
	 */
	private static final String TEST_DIR_NAME = "bar-plot-painter-test";
	
	/** Directory that will hold test output files. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Gap between generated reporters. */
	private static final long GAP = 1000000;
	
	/** Number of simulated bioassays. */
	private static final int NUM_BIOASSAYS = 3;
	
	/** Number of simulated experiments. */
	private static final int NUM_EXPERIMENTS = 2;
	
	/** Chromosome number. */
	private static final String CHROMOSOME = "1";
	
	/** Starting chromosome position. */
	private static final long START_POS = 1;
	
	/** Ending chromosome position. */
	private static final long END_POS = 100000000;
	
	/**
	 * Test paintPlot() method.
	 */
	public void testPaintPlot() {
		BioAssayDataConstraints constraints = new BioAssayDataConstraints();
		constraints.setChromosome(CHROMOSOME);
		constraints.setPositions(START_POS, END_POS);
		constraints.setQuantitationType(QuantitationTypes.COPY_NUMBER);
		BioAssayDataConstraints[] constraintsArr =
			new BioAssayDataConstraints[] {constraints};
		ExperimentDTOGenerator gen =
			new ExperimentDTOGenerator(GAP, NUM_BIOASSAYS);
		Collection<Experiment> experiments = new ArrayList<Experiment>();
		for (int i = 0; i < NUM_EXPERIMENTS; i++) {
			String id = "Experiment " + i;
			ExperimentDTO dto = gen.newExperimentDTO(id, constraintsArr);
			Experiment exp = new Experiment(dto, constraintsArr);
			if (i % 2 == 0) {
				exp.setQuantitationType(QuantitationType.COPY_NUMBER);
			} else {
				exp.setQuantitationType(QuantitationType.FOLD_CHANGE);
			}
			experiments.add(exp);
		}
		BarPlotPainter painter = new BarPlotPainter(
				new InMemoryChromosomeArrayDataGetter());
		BarPlotParameters params = new BarPlotParameters();
		params.setNumPlotsPerRow(8);
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		painter.paintPlot(panel, experiments, params);
		panel.toPngFile("plot.png");
	}
}
