/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.service.analysis;


import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticPipeline;
import org.rti.webgenome.analysis.Averager;
import org.rti.webgenome.analysis.SlidingWindowSmoother;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.ExperimentGenerator;
import org.rti.webgenome.service.analysis.InMemoryDataTransformer;

import junit.framework.TestCase;

/**
 * Tester for <code>InMemoryDataTransformer</code>.
 * @author dhall
 *
 */
public final class InMemoryDataTransformerTester extends TestCase {
	
	/** Number of bioassays to generate in tests. */
	private static final int NUM_BIO_ASSAYS = 3;
	
	/** Number of chromosomes in tests. */
	private static final int NUM_CHROMOSOMES = 3;
	
	/**
	 * Number of array datum per chromosome in
	 * in-memory tests.
	 */
	private static final int NUM_DATUM_PER_CHROMOSOME = 100;
	
	
	/**
	 * Test <code>ScalarToScalarAnalyticOperation</code>.
	 * @throws Exception if something bad happens
	 */
	public void testScalarToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		InMemoryDataTransformer mgr = new InMemoryDataTransformer();
		
		// Instantiate analytic operation
		AnalyticOperation op = new SlidingWindowSmoother();
		
		// Instantiate test data
		ExperimentGenerator expGen = new ExperimentGenerator();
		Experiment input = expGen.newInMemoryExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME);
		
		// Perform operation
		Experiment output = mgr.perform(input, op);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(NUM_BIO_ASSAYS, output.getBioAssays().size());
	}
	
	
	/**
	 * Test <code>ListToScalarAnalyticOperation</code>.
	 * @throws Exception if something bad happens
	 */
	public void testListToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		InMemoryDataTransformer mgr = new InMemoryDataTransformer();
		
		// Instantiate analytic operation
		AnalyticOperation op = new Averager();
		
		// Instantiate test data
		ExperimentGenerator expGen = new ExperimentGenerator();
		Experiment input = expGen.newInMemoryExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME);
		
		// Perform operation
		Experiment output = mgr.perform(input, op);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(1, output.getBioAssays().size());
	}

	
	/**
	 * Test on pipeline.
	 * @throws Exception if something bad happens
	 */
	public void testAnalyticPipeline() throws Exception {
		
		// Instantiate analytic operation manager
		InMemoryDataTransformer mgr = new InMemoryDataTransformer();
		
		// Instantiate pipeline
		AnalyticPipeline pipeline = new AnalyticPipeline();
		pipeline.add(new SlidingWindowSmoother());
		pipeline.add(new Averager());
		
		// Instantiate test data
		ExperimentGenerator expGen = new ExperimentGenerator();
		Experiment input = expGen.newInMemoryExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME);
		
		// Perform operation
		Experiment output = mgr.perform(input, pipeline);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(1, output.getBioAssays().size());
	}
}
