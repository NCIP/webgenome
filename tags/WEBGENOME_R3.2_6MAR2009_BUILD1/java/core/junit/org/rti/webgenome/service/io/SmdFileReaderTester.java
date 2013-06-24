/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2007-08-28 17:24:13 $


*/

package org.rti.webgenome.service.io;

import java.io.File;
import java.util.List;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssayData;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.util.FileUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>SmdFileReader</code>.
 * @author dhall
 *
 */
public final class SmdFileReaderTester extends TestCase {
	
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
    
    /**
     * Path (relative to classpath) to directory containing
     * test files.
     */
    private static final String TEST_DIRECTORY =
        "org/rti/webgenome/service/io/smd_file_reader_test_files";

    
    /**
     * Test method getReporters().
     * @throws Exception if something crashes.
     */
    public void testGetReporters() throws Exception {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "normal.csv");
        SmdFileReader reader = new SmdFileReader(file,
        		REPORTER_NAME_COLUMN_NAME, CHROMOSOME_COLUMN_NAME,
        		POSITION_COLUMN_NAME, UNITS, FORMAT);
        List<Reporter> reporters = reader.getReporters();
        assertEquals(5, reporters.size());
        Reporter r = reporters.get(3);
        assertEquals("r4", r.getName());
        assertEquals((short) 2, r.getChromosome());
        assertEquals((long) 5000, r.getLocation());
    }
    
    /**
     * Test importing of normal data.
     * @throws Exception if something crashes
     */
    public void testGetNormalData() throws Exception {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "normal.csv");
        SmdFileReader reader = new SmdFileReader(file,
        		REPORTER_NAME_COLUMN_NAME, CHROMOSOME_COLUMN_NAME,
        		POSITION_COLUMN_NAME, UNITS, FORMAT);
        BioAssayData data = reader.getBioAssayData(file, "bioassay1",
        		REPORTER_NAME_COLUMN_NAME, FORMAT);
        List<ArrayDatum> datumList = data.getArrayData((short) 1);
        assertEquals(3, datumList.size());
        ArrayDatum datum = data.getArrayData((short) 1).get(0);
        assertEquals((float) 0.1, datum.getValue());
    }
}
