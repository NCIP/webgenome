/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:38 $

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

package org.rti.webcgh.service.analysis;


import org.rti.webcgh.analysis.AnalyticOperation;
import org.rti.webcgh.analysis.AnalyticPipeline;
import org.rti.webcgh.analysis.Averager;
import org.rti.webcgh.analysis.SlidingWindowSmoother;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ExperimentGenerator;
import org.rti.webcgh.service.analysis.InMemoryDataTransformer;

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
