/*
$Revision: 1.1 $
$Date: 2007-06-22 22:39:50 $


*/

package org.rti.webgenome.systests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.util.IOUtils;

/**
 * General utilities for system tests.
 * @author dhall
 *
 */
public final class SystemTestUtils {
	
	//
	//     S T A T I C S
	//
	
	/** Classpath-relative path to system test properties file. */
	private static final String SYSTEM_TEST_PROPERTIES_FILE =
		"system_test.properties";
	
	
	//
	//     C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	private SystemTestUtils() {
		
	}

	
	//
	//     B U S I N E S S   M E T H O D S
	//
	
	
	/**
	 * Get system test property.  These properties are set in the
	 * file 'system_test.properties.'
	 * @param key Property name
	 * @return Property value
	 */
	public static String getSystemTestProperty(final String key) {
		Properties props = new Properties();
		InputStream in = IOUtils.getInputStream(SYSTEM_TEST_PROPERTIES_FILE);
		if (in == null) {
			throw new WebGenomeSystemException(
					"Cannot find system test properties file '"
					+ SYSTEM_TEST_PROPERTIES_FILE + "'");
		}
		try {
			props.load(in);
		} catch (IOException e) {
			throw new WebGenomeSystemException(
					"Error accessing system test properties", e);
		}
		return props.getProperty(key);
	}
}
