/*
$Revision: 1.3 $
$Date: 2007-04-09 22:19:50 $

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
