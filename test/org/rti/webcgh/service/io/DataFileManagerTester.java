/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:34 $

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

package org.rti.webcgh.service.io;

import java.io.File;

import junit.framework.TestCase;

import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.DataSerializedBioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.service.io.DataFileManager;
import org.rti.webcgh.service.io.SmdFormatException;
import org.rti.webcgh.util.FileUtils;

/**
 * Tester for <code>DataFileManager</code>.
 * @author dhall
 *
 */
public final class DataFileManagerTester extends TestCase {
    
    /** Name of temporary directory used for testing. */
    private static final String TEMP_DIR_NAME = "data_file_manager_temp";
    
    /**
     * Path (relative to classpath) to directory containing
     * test files.
     */
    private static final String TEST_DIRECTORY =
        "org/rti/webcgh/io/unit_test/data_file_manager_test_files";
    
    
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
     * Test all methods on large file.
     * @throws Exception if there is any problem
     */
    public void testAllMethodsOnLargeFile() throws Exception {
        this.runAllMethods("large-smd.csv");
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
        File tempDir = FileUtils.createUnitTestDirectory(TEMP_DIR_NAME);
        DataFileManager mgr = new DataFileManager(tempDir.getAbsolutePath());
        Experiment exp = mgr.convertSmdData(testFile, org);
        
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
