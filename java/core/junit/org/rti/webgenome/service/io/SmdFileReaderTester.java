/*
$Revision: 1.3 $
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
import java.util.List;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssayData;
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
        		POSITION_COLUMN_NAME, UNITS);
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
        		POSITION_COLUMN_NAME, UNITS);
        BioAssayData data = reader.getBioAssayData(file, "bioassay1",
        		REPORTER_NAME_COLUMN_NAME);
        List<ArrayDatum> datumList = data.getArrayData((short) 1);
        assertEquals(3, datumList.size());
        ArrayDatum datum = data.getArrayData((short) 1).get(0);
        assertEquals((float) 0.1, datum.getValue());
    }
}
