/*
$Revision: 1.4 $
$Date: 2007-08-24 21:51:58 $

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

package org.rti.webgenome.service.io;

import java.io.File;

import junit.framework.TestCase;

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
        		POSITION_COLUMN_NAME, UNITS);
        DataFileMetaData meta = new DataFileMetaData();
        meta.setFormat(RectangularTextFileFormat.CSV);
        meta.setLocalFileName(fname);
        meta.setRemoteFileName(fname);
        meta.setReporterNameColumnName(REPORTER_NAME_COLUMN_NAME);
        meta.add(new DataColumnMetaData("bioassay1", "bioassay1"));
        meta.add(new DataColumnMetaData("bioassay2", "bioassay2"));
        mgr.convertSmdData(reader, exp, testFile, meta, org);
        
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
