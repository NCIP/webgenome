/*
$Revision: 1.1 $
$Date: 2007-06-22 22:39:50 $


*/

package org.rti.webgenome.systests;

import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;

/**
 * Performs automated system tests against
 * web application.
 * @author dhall
 *
 */
public class SystemTests extends WebTestCase {
	
	//
	//  S T A T I C S
	//
	
	/** Key for system test property specifying base URL. */
	private static final String BASE_URL_PROP_KEY = "base.url";
	
	
	//
	//  O V E R R I D E S
	//
	
	/**
	 * {@inheritDoc}
	 */
	public void setUp() {
		this.setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
		String baseUrl =
			SystemTestUtils.getSystemTestProperty(BASE_URL_PROP_KEY);
		this.getTestContext().setBaseUrl(baseUrl);
	}
	
	
	//
	//  T E S T    C A S E S
	//

	/**
	 * Test that the context is up.
	 */
	public void testContextUp() {
		this.beginAt("/home.do");
		this.assertTitleEquals("webGenome: Overview");
	}
	
	
	/**
	 * Test the creation of a basic copy number scatter
	 * plot using simulated data.
	 *
	 */
	public void testCopyNumberScatterPlot() {
		this.beginAt("/home.do");
		this.assertLinkPresentWithExactText("copy number");
		this.clickLinkWithExactText("copy number");
		this.assertTitleEquals("webGenome: Plot");
	}
}
