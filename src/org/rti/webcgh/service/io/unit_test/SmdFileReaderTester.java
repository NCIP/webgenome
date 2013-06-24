/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2006-10-22 03:20:46 $

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

package org.rti.webcgh.service.io.unit_test;

import java.io.File;
import java.util.List;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.BioAssayData;
import org.rti.webcgh.service.io.SmdFileReader;
import org.rti.webcgh.util.FileUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>SmdFileReader</code>.
 * @author dhall
 *
 */
public final class SmdFileReaderTester extends TestCase {
    
    /**
     * Path (relative to classpath) to directory containing
     * test files.
     */
    private static final String TEST_DIRECTORY =
        "org/rti/webcgh/io/unit_test/smd_file_reader_test_files";

    
    /**
     * Test method getBioAssayNames().
     * @throws Exception if something crashes.
     */
    public void testGetBioAssayNames() throws Exception {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "normal.csv");
        SmdFileReader reader = new SmdFileReader(file);
        List<String> bioAssayNames = reader.getBioAssayNames();
        assertEquals(2, bioAssayNames.size());
        assertEquals("bioassay1", bioAssayNames.get(0));
        assertEquals("bioassay2", bioAssayNames.get(1));
    }
    
    
    /**
     * Test getting bioassay data from a well-formed file.
     * @throws Exception if something bad happens
     */
    public void testGetBioAssayData() throws Exception {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "normal.csv");
        SmdFileReader reader = new SmdFileReader(file);
        
        // First bioassay
        BioAssayData bad = reader.getBioAssayData("bioassay1");
        assertNotNull(bad);
        List<ArrayDatum> aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(3, aData.size());
        assertEquals((float) 0.1, aData.get(0).getValue());
        assertEquals((float) 0.3, aData.get(aData.size() - 1).getValue());
        assertEquals((long) 100, aData.get(0).getReporter().getLocation());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) 0.4, aData.get(0).getValue());
        assertEquals((float) 0.5, aData.get(aData.size() - 1).getValue());
        
        
        // Second bioassay
        bad = reader.getBioAssayData("bioassay2");
        assertNotNull(bad);
        aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(3, aData.size());
        assertEquals((float) -0.1, aData.get(0).getValue());
        assertEquals((float) -0.3, aData.get(aData.size() - 1).getValue());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) -0.4, aData.get(0).getValue());
        assertEquals((float) -0.5, aData.get(aData.size() - 1).getValue());
    }
    
    
    /**
     * Test getting bioassay data from a well-formed file
     * where positions are given in units of KB (kilobases).
     * @throws Exception if something bad happens
     */
    public void testGetBioAssayDataKb() throws Exception {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "normal-kb.csv");
        SmdFileReader reader = new SmdFileReader(file);
        
        // First bioassay
        BioAssayData bad = reader.getBioAssayData("bioassay1");
        assertNotNull(bad);
        List<ArrayDatum> aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(3, aData.size());
        assertEquals((float) 0.1, aData.get(0).getValue());
        assertEquals((float) 0.3, aData.get(aData.size() - 1).getValue());
        assertEquals((long) 100000, aData.get(0).getReporter().getLocation());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) 0.4, aData.get(0).getValue());
        assertEquals((float) 0.5, aData.get(aData.size() - 1).getValue());
        
        
        // Second bioassay
        bad = reader.getBioAssayData("bioassay2");
        assertNotNull(bad);
        aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(3, aData.size());
        assertEquals((float) -0.1, aData.get(0).getValue());
        assertEquals((float) -0.3, aData.get(aData.size() - 1).getValue());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) -0.4, aData.get(0).getValue());
        assertEquals((float) -0.5, aData.get(aData.size() - 1).getValue());
    }
    
    
    /**
     * Test getting bioassay data from a file with some
     * missing reporter data.
     * @throws Exception if something bad happens
     */
    public void testGetBioAssayDataMissingReporterData() throws Exception {
    	File file = FileUtils.getFile(TEST_DIRECTORY,
    			"missing_reporter_data.csv");
        SmdFileReader reader = new SmdFileReader(file);
        
        // First bioassay
        BioAssayData bad = reader.getBioAssayData("bioassay1");
        assertNotNull(bad);
        List<ArrayDatum> aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(1, aData.size());
        assertEquals((float) 0.1, aData.get(0).getValue());
        assertEquals((float) 0.1, aData.get(aData.size() - 1).getValue());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) 0.4, aData.get(0).getValue());
        assertEquals((float) 0.5, aData.get(aData.size() - 1).getValue());
        
        
        // Second bioassay
        bad = reader.getBioAssayData("bioassay2");
        assertNotNull(bad);
        aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(1, aData.size());
        assertEquals((float) -0.1, aData.get(0).getValue());
        assertEquals((float) -0.1, aData.get(aData.size() - 1).getValue());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) -0.4, aData.get(0).getValue());
        assertEquals((float) -0.5, aData.get(aData.size() - 1).getValue());
    }
    
    
    /**
     * Test getting bioassay data from a file with some
     * missing values.
     * @throws Exception if something bad happens
     */
    public void testGetBioAssayDataMissingValues() throws Exception {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "missing_values.csv");
        SmdFileReader reader = new SmdFileReader(file);
        
        // First bioassay
        BioAssayData bad = reader.getBioAssayData("bioassay1");
        assertNotNull(bad);
        List<ArrayDatum> aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(1, aData.size());
        assertEquals((float) 0.2, aData.get(0).getValue());
        assertEquals((float) 0.2, aData.get(aData.size() - 1).getValue());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) 0.4, aData.get(0).getValue());
        assertEquals((float) 0.5, aData.get(aData.size() - 1).getValue());
        
        
        // Second bioassay
        bad = reader.getBioAssayData("bioassay2");
        assertNotNull(bad);
        aData = bad.getArrayData((short) 1);
        assertNotNull(aData);
        assertEquals(3, aData.size());
        assertEquals((float) -0.1, aData.get(0).getValue());
        assertEquals((float) -0.3, aData.get(aData.size() - 1).getValue());
        aData = bad.getArrayData((short) 2);
        assertNotNull(aData);
        assertEquals(2, aData.size());
        assertEquals((float) -0.4, aData.get(0).getValue());
        assertEquals((float) -0.5, aData.get(aData.size() - 1).getValue());
    }
}
