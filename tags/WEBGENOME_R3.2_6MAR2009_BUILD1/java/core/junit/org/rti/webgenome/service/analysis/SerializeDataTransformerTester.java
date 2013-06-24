/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.service.analysis;

import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticPipeline;
import org.rti.webgenome.analysis.Averager;
import org.rti.webgenome.analysis.SlidingWindowSmoother;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ExperimentGenerator;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>SerializedDataTransformer</code>.
 * @author dhall
 *
 */
public final class SerializeDataTransformerTester extends TestCase {
	
	/**
	 * Name of temporary directory for storing
	 * generated ata files.  This is not an absolute
	 * path.
	 */
	private static final String TEMP_DIR_NAME =
		"serialized_data_transformer_test_dir";
	
	/**
	 * Path to temporary directory for storing data files.
	 * It will be a subdirectory of the main
	 * unit test temporary directory specified
	 * by the property 'temp.dir' in 'unit_test.properties.'
	 */
	private static final String TEMP_DIR_PATH;
	
	/** Number of bioassays to generate in tests. */
	private static final int NUM_BIO_ASSAYS = 2;
	
	/** Number of chromosomes in tests. */
	private static final int NUM_CHROMOSOMES = 1;
	
	/**
	 * Number of array datum per chromosome in
	 * serialized data tests.
	 */
	private static final int NUM_DATUM_PER_CHROMOSOME = 5000;
	
	// Initialize TEMP_DIR
	static {
		String tempDirParent =
			UnitTestUtils.getUnitTestProperty("temp.dir");
		if (tempDirParent == null) {
			throw new WebGenomeSystemException(
					"Unit test property 'temp.dir' must be set");
		}
		TEMP_DIR_PATH = tempDirParent + "/" + TEMP_DIR_NAME;
		FileUtils.createDirectory(TEMP_DIR_PATH);
	}
	
	/**
	 * Test <code>ScalarToScalarAnalyticOperation</code>.
	 * @throws Exception if something bad happens
	 */
	public void testScalarToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		SerializedDataTransformer mgr = new SerializedDataTransformer();
		DataFileManager dfm = new DataFileManager(TEMP_DIR_PATH);
		mgr.setDataFileManager(dfm);
		
		// Instantiate analytic operation
		AnalyticOperation op = new SlidingWindowSmoother();
		
		// Instantiate test data
		ExperimentGenerator expGen = new ExperimentGenerator();
		Experiment input = expGen.newDataSerializedExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME, dfm);
		
		// Perform operation
		Experiment output = mgr.perform(input, op);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(NUM_BIO_ASSAYS, output.getBioAssays().size());
		
		// Clean up
		dfm.deleteDataFiles(input, false);
		dfm.deleteDataFiles(output, true);
	}
	
	
	/**
	 * Test <code>ListToScalarAnalyticOperation</code>.
	 * @throws Exception if something bad happens
	 */
	public void testListToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		SerializedDataTransformer mgr = new SerializedDataTransformer();
		DataFileManager dfm = new DataFileManager(TEMP_DIR_PATH);
		mgr.setDataFileManager(dfm);
		
		// Instantiate analytic operation
		AnalyticOperation op = new Averager();
		
		// Instantiate test data
		ExperimentGenerator expGen = new ExperimentGenerator();
		Experiment input = expGen.newDataSerializedExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME, dfm);
		
		// Perform operation
		Experiment output = mgr.perform(input, op);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(1, output.getBioAssays().size());
		
		// Clean up
		dfm.deleteDataFiles(input, false);
		dfm.deleteDataFiles(output, true);
	}

	
	/**
	 * Test on analytic pipeline.
	 * @throws Exception if something bad happens
	 */
	public void testAnalyticPipeline() throws Exception {
		
		// Instantiate analytic operation manager
		SerializedDataTransformer mgr = new SerializedDataTransformer();
		DataFileManager dfm = new DataFileManager(TEMP_DIR_PATH);
		mgr.setDataFileManager(dfm);
		
		// Instantiate pipeline
		AnalyticPipeline pipeline = new AnalyticPipeline();
		pipeline.add(new SlidingWindowSmoother());
		pipeline.add(new Averager());
		
		// Instantiate test data
		ExperimentGenerator expGen = new ExperimentGenerator();
		Experiment input = expGen.newDataSerializedExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME, dfm);
		
		// Perform operation
		Experiment output = mgr.perform(input, pipeline);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(1, output.getBioAssays().size());
		
		// Clean up
		dfm.deleteDataFiles(input, false);
		dfm.deleteDataFiles(output, true);
	}
}
