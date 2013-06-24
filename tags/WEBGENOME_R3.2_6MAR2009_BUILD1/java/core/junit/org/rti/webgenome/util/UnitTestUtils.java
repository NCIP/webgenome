/*
$Revision: 1.3 $
$Date: 2007-04-09 22:19:50 $


*/


package org.rti.webgenome.util;

import java.io.File;
import java.util.Properties;

import org.rti.webgenome.core.WebGenomeSystemException;


/**
 * General unit test utility methods.
 */
public final class UnitTestUtils {
    
    /** Classpath-relative file containing properties for unit test classes. */
	private static final String UNIT_TEST_PROPS_FILE =
	    "unit_test.properties";

	/**
     * Private constructor to keep instances of
     * this class from being created.
     */
    private UnitTestUtils() {
        
    }
    
    
    /**
     * Create new test directory within the system-defined
     * unit test master directory.  The newly created directory
     * will be a sub-directory of the master directory.
     * @param dirName Directory name to be created within the
     * master directory.
     * @return Absolute path name of test directory.
     */
    public static String newTestDirectory(final String dirName) {
        String tempDir = UnitTestUtils.getUnitTestProperty("temp.dir");
        File dir = new File(tempDir);
        if (!dir.exists()) {
            throw new RuntimeException("Please create directory '"
                    + tempDir
                    + "' for unit testing");
        }
        String testDirName = tempDir + "/" + dirName;
        File testDir = new File(testDirName);
        if (!testDir.exists()) {
            testDir.mkdir();
        }
        return testDirName;
    }

	/**
	 * Get specified unit test property.
	 * @param key Name of property
	 * @return Property value
	 */
	public static String getUnitTestProperty(final String key) {
	    Properties props = SystemUtils.loadProperties(
	            UNIT_TEST_PROPS_FILE);
	    if (props == null) {
	        throw new WebGenomeSystemException(
	                "Cannot find 'unit_test.properties' file");
	    }
	    return props.getProperty(key);
	}


	/**
	 * Creates a temp directory for unit tests.  The directory
	 * location is a subdirector with the given name
	 * off the main unit test temp directory specified
	 * by the property 'temp.dir' in the file 'unit_test.properties.'
	 * @param dirName Name of temp directory to create.
	 * @return Directory created.
	 */
	public static File createUnitTestDirectory(final String dirName) {
		String parentPath = getUnitTestProperty("temp.dir");
		String dirPath = parentPath + "/" + dirName;
		return FileUtils.createDirectory(dirPath);
	}

}
