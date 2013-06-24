/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.5 $
$Date: 2007-08-28 17:24:13 $


*/

package org.rti.webgenome.service.io;

import java.io.File;

import junit.framework.TestCase;

import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.UnitTestUtils;

/**
 * Tester for <code>DataFileManager</code>.
 * @author dhall
 *
 */
public final class DataFileManagerTester extends TestCase {
	
	/** Heading of column containing reporter names. */
	private static final String REPORTER_NAME_COLUMN_NAME = "name";
	
	/** Heading of column containing chromosome numbers. */
	private static final String CHROMOSOME_COLUMN_NAME = "chromosome";
	
	/** Heading of column containing chromosome positions. */
	private static final String POSITION_COLUMN_NAME = "position";
	
	/** Units of chromosome position. */
	private static final BpUnits UNITS = BpUnits.KB;
	
	/** File format. */
	private static final RectangularTextFileFormat FORMAT =
		RectangularTextFileFormat.CSV;
    
    /** Name of temporary directory used for testing. */
    private static final String TEMP_DIR_NAME = "data_file_manager_temp";
    
    /**
     * Path (relative to classpath) to directory containing
     * test files.
     */
    private static final String TEST_DIRECTORY =
        "org/rti/webgenome/service/io/data_file_manager_test_files";
    
    
    /**
     * Test all methods on small file.
     * @throws Exception if there is any problem
     */
    public void testAllMethodsOnSmallFile() throws Exception {
        this.runAllMethods("small-smd.csv");
    }
    
    
    /**
     * Test all methods on medium large file.
     * @throws Exception if there is any problem
     */
    public void testAllMethodsOnMediumLargeFile() throws Exception {
        this.runAllMethods("medium-large-smd.csv");
    }
        
    
    /**
     * Run all methods on given file.
     * @param fname Name of file
     * @throws SmdFormatException If file is not in proper SMD
     * (Standord Microarray Database) format
     */
    private void runAllMethods(final String fname)
    throws SmdFormatException {
        
        // Save data
        Organism org = new Organism();
        File testFile = FileUtils.getFile(TEST_DIRECTORY, fname);
        File tempDir = UnitTestUtils.createUnitTestDirectory(TEMP_DIR_NAME);
        DataFileManager mgr = new DataFileManager(tempDir.getAbsolutePath());
        Experiment exp = new Experiment();
        SmdFileReader reader = new SmdFileReader(testFile,
        		REPORTER_NAME_COLUMN_NAME, CHROMOSOME_COLUMN_NAME,
        		POSITION_COLUMN_NAME, UNITS, FORMAT);
        DataFileMetaData meta = new DataFileMetaData();
        meta.setFormat(RectangularTextFileFormat.CSV);
        meta.setLocalFileName(fname);
        meta.setRemoteFileName(fname);
        meta.setReporterNameColumnName(REPORTER_NAME_COLUMN_NAME);
        meta.add(new DataColumnMetaData("bioassay1", "bioassay1"));
        meta.add(new DataColumnMetaData("bioassay2", "bioassay2"));
        Array array = new Array();
        mgr.convertSmdData(reader, exp, testFile, meta, org, array);
        
        // Recover some data
        DataSerializedBioAssay ba = (DataSerializedBioAssay)
        	exp.getBioAssays().iterator().next();
        short chromosome = (short) 1;
        ChromosomeArrayData cad = mgr.loadChromosomeArrayData(ba, chromosome);
        assertNotNull(cad);
        
        // Delete all data
        mgr.deleteDataFiles(exp, true);
    }
}
