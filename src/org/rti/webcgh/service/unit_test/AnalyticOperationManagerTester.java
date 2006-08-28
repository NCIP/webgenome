/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.service.unit_test;


import org.rti.webcgh.analysis.AnalyticOperation;
import org.rti.webcgh.analysis.Averager;
import org.rti.webcgh.analysis.ScalarToScalarAnalyticOperation;
import org.rti.webcgh.analysis.SlidingWindowSmoother;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ExperimentGenerator;
import org.rti.webcgh.io.DataFileManager;
import org.rti.webcgh.service.AnalyticOperationManager;
import org.rti.webcgh.util.FileUtils;
import org.rti.webcgh.util.SystemUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>AnalyticOperationManager</code>.
 * @author dhall
 *
 */
public final class AnalyticOperationManagerTester extends TestCase {
	
	/**
	 * Name of temporary directory for storing
	 * generated ata files.  This is not an absolute
	 * path.
	 */
	private static final String TEMP_DIR_NAME =
		"analytic_operation_manager_test_dir";
	
	/**
	 * Path to temporary directory for storing data files.
	 * It will be a subdirectory of the main
	 * unit test temporary directory specified
	 * by the property 'temp.dir' in 'unit_test.properties.'
	 */
	private static final String TEMP_DIR_PATH;
	
	/** Number of bioassays to generate in tests. */
	private static final int NUM_BIO_ASSAYS = 3;
	
	/** Number of chromosomes in tests. */
	private static final int NUM_CHROMOSOMES = 3;
	
	/**
	 * Number of array datum per chromosome in
	 * in-memory tests.
	 */
	private static final int NUM_DATUM_PER_CHROMOSOME_IN_MEMORY = 100;
	
	/**
	 * Number of array datum per chromosome in
	 * serialized data tests.
	 */
	private static final int NUM_DATUM_PER_CHROMOSOME_SERIALIZED = 5000;
	
	// Initialize TEMP_DIR
	static {
		String tempDirParent =
			SystemUtils.getUnitTestProperty("temp.dir");
		if (tempDirParent == null) {
			throw new WebcghSystemException(
					"Unit test property 'temp.dir' must be set");
		}
		TEMP_DIR_PATH = tempDirParent + "/" + TEMP_DIR_NAME;
		FileUtils.createDirectory(TEMP_DIR_PATH);
	}
	
	/**
	 * Test <code>ScalarToScalarAnalyticOperation</code>
	 * on in-memory data.
	 * @throws Exception if something bad happens
	 */
	public void testInMemoryScalarToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		AnalyticOperationManager mgr = new AnalyticOperationManager();
		DataFileManager dfm = new DataFileManager(TEMP_DIR_PATH);
		mgr.setDataFileManager(dfm);
		
		// Instantiate analytic operation
		AnalyticOperation op = new SlidingWindowSmoother();
		
		// Instantiate test data
		Experiment input = ExperimentGenerator.newInMemoryExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME_IN_MEMORY);
		
		// Perform operation
		Experiment output = mgr.perform(input, op);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(NUM_BIO_ASSAYS, output.getBioAssays().size());
	}
	
	
	/**
	 * Test <code>ListToScalarAnalyticOperation</code>
	 * on in-memory data.
	 * @throws Exception if something bad happens
	 */
	public void testInMemoryListToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		AnalyticOperationManager mgr = new AnalyticOperationManager();
		DataFileManager dfm = new DataFileManager(TEMP_DIR_PATH);
		mgr.setDataFileManager(dfm);
		
		// Instantiate analytic operation
		AnalyticOperation op = new Averager();
		
		// Instantiate test data
		Experiment input = ExperimentGenerator.newInMemoryExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME_IN_MEMORY);
		
		// Perform operation
		Experiment output = mgr.perform(input, op);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(1, output.getBioAssays().size());
	}

	
	/**
	 * Test <code>ScalarToScalarAnalyticOperation</code>
	 * on serialized data.
	 * @throws Exception if something bad happens
	 */
	public void testSerializedScalarToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		AnalyticOperationManager mgr = new AnalyticOperationManager();
		DataFileManager dfm = new DataFileManager(TEMP_DIR_PATH);
		mgr.setDataFileManager(dfm);
		
		// Instantiate analytic operation
		AnalyticOperation op = new SlidingWindowSmoother();
		
		// Instantiate test data
		Experiment input = ExperimentGenerator.newDataSerializedExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME_SERIALIZED, dfm);
		
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
	 * Test <code>ListToScalarAnalyticOperation</code>
	 * on serialized data.
	 * @throws Exception if something bad happens
	 */
	public void testSerializedListToScalar() throws Exception {
		
		// Instantiate analytic operation manager
		AnalyticOperationManager mgr = new AnalyticOperationManager();
		DataFileManager dfm = new DataFileManager(TEMP_DIR_PATH);
		mgr.setDataFileManager(dfm);
		
		// Instantiate analytic operation
		AnalyticOperation op = new Averager();
		
		// Instantiate test data
		Experiment input = ExperimentGenerator.newDataSerializedExperiment(
				NUM_BIO_ASSAYS, NUM_CHROMOSOMES,
				NUM_DATUM_PER_CHROMOSOME_SERIALIZED, dfm);
		
		// Perform operation
		Experiment output = mgr.perform(input, op);
		
		// Peform tests
		assertNotNull(output);
		assertEquals(1, output.getBioAssays().size());
		
		// Clean up
		dfm.deleteDataFiles(input, false);
		dfm.deleteDataFiles(output, true);
	}
}
