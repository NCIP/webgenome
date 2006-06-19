/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.array.unit_test;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.io.FileSerializer;
import org.rti.webcgh.io.Serializer;
import org.rti.webcgh.unit_test.UnitTestUtils;
import org.rti.webcgh.util.ArrayDatumGenerator;

import junit.framework.TestCase;

/**
 * Tester for <code>BioAssay</code>.
 * @author dhall
 *
 */
public final class BioAssayTester extends TestCase {
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(BioAssayTester.class);
    
    /** A big number. */
    private static final long BIG_NUM = (long) 500000;
    
    /** Number of bioassays created in testing load. */
    private static final int NUM_BIOASSAYS = 5;
    
    /**
     * Test ability of bioassay to absort
     * large amounts of data.
     * @throws Exception If there is a problem
     */
    public void testBigData() throws Exception {
        
        // Create some bioassays and write data do disk
        Collection<BioAssay> bioAssays = new ArrayList<BioAssay>();
        String testDirName = UnitTestUtils.newTestDirectory("bioassay_tests");
        Serializer serializer = new FileSerializer(testDirName);
        for (int i = 0; i < NUM_BIOASSAYS; i++) {
            LOGGER.info("Creating bioassay " + i);
            BioAssay b = new BioAssay();
            bioAssays.add(b);
            b.setSerializer(serializer);
            b.moveDataToMemory();
            ArrayDatumGenerator adg = new ArrayDatumGenerator();
            for (long j = 0; j < BIG_NUM; j++) {
                ArrayDatum datum = adg.newArrayDatum("Name", 
                        (short) 1, (long) 1);
                b.add(datum);
            }
            LOGGER.info("Writing data to disk");
            b.moveDataToDisk();
            LOGGER.info("Completed write");
        }
        
        // Iterate through bioassays and load then save data
        int count = 0;
        for (BioAssay b : bioAssays) {
            LOGGER.info("Retrieving data from bioassay " + (count++));
            b.moveDataToMemory();
            assertNotNull(b.getBioAssayData());
            assertEquals(BIG_NUM, b.getBioAssayData().numArrayDatum());
            b.moveDataToDisk();
            LOGGER.info("Relinquishing memory");
        }
        
        // Clean up
        serializer.decommissionAllObjects();
    }

}
