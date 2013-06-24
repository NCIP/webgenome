/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.service.data;

import java.util.Map;

import org.rti.webgenome.domain.Principal;

import junit.framework.TestCase;

/**
 * Tester for <code>MockDataSource</code>.
 * @author dhall
 *
 */
public class MockDataSourceTester extends TestCase {
	
	/** Test object. */
	private MockDataSource mockDataSource = new MockDataSource();

	/**
	 * Test login() method.
	 */
	public void testLogin() {
		Principal principal = this.mockDataSource.login("user", "password");
		assertNotNull(principal);
	}
	
	/**
	 * Test getIdsAndNames() method.
	 * @throws Exception if anything bad happens
	 */
	public void testIdsAndNames() throws Exception {
		Map<String, String> idsAndNames =
			this.mockDataSource.getExperimentIdsAndNames(null);
		assertNotNull(idsAndNames);
		assertTrue(idsAndNames.size() > 0);
	}
	
	/**
	 * Test getExperiment() method.
	 * @throws Exception if anything bad happens
	 */
	public void testGetExperiment() throws Exception {
		ExperimentDto exp = this.mockDataSource.getExperimentDto("0");
		assertNotNull(exp);
	}
}
