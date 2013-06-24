/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-11-28 19:51:21 $


*/

package org.rti.webgenome.service.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.ZipEntryMetaData;
import org.rti.webgenome.domain.ZipFileMetaData;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for {@code IOService}.
 * @author dhall
 *
 */
public class IOServiceTester extends TestCase {
	
    /** Name of temporary directory used for testing. */
    private static final String TEMP_DIR_NAME = "io_service_temp";
    
    /**
     * Path (relative to classpath) to directory containing
     * test files.
     */
    private static final String TEST_DIRECTORY =
        "org/rti/webgenome/service/io/io_service_test_files";
    
    
    /**
     * Test method {@code uploadZipFile}.
     * @throws Exception if something bad happens
     */
    public void testUploadZipFile() throws Exception {
    	
    	// Create temporary directory that will be used as both
    	// working directory and data storage directory
        File workingDir = UnitTestUtils.createUnitTestDirectory(TEMP_DIR_NAME);
        
        // Instantiate IOService
        DataFileManager dataFileManager = new DataFileManager(
        		workingDir.getAbsolutePath());
        IOService ioService = new IOService(workingDir.getAbsolutePath(),
        		dataFileManager);
        
        // Get stream to ZIP file
        File testFile = FileUtils.getFile(TEST_DIRECTORY, "small.zip");
        InputStream in = new FileInputStream(testFile);
        
        // Run test
        ZipFileMetaData meta = ioService.uploadZipFile(in, "small.zip",
        		RectangularTextFileFormat.CSV);
        assertNotNull(meta);
        Collection<ZipEntryMetaData> zeMeta = meta.getZipEntryMetaData();
        assertNotNull(zeMeta);
        assertEquals(2, zeMeta.size());
        
        // Clean up
        for (ZipEntryMetaData z : zeMeta) {
        	ioService.delete(z.getLocalFile().getName());
        }
    }

}
